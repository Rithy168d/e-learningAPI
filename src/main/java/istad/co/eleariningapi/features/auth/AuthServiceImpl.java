package istad.co.eleariningapi.features.auth;

import istad.co.eleariningapi.domain.Role;
import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.domain.UserVerification;
import istad.co.eleariningapi.features.auth.dto.*;
import istad.co.eleariningapi.features.user.RoleRepository;
import istad.co.eleariningapi.features.user.UserRepository;
import istad.co.eleariningapi.mapper.UserMapper;
import istad.co.eleariningapi.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    //  inject userRepository
    private final UserRepository userRepository;

    //  inject userMapper
    private final UserMapper userMapper;

    //  inject roleRepository
    private final RoleRepository roleRepository;

    // inject passwordEncoder
    private final PasswordEncoder passwordEncoder;

    //  inject javaMailSender
    private final JavaMailSender javaMailSender;

    //  inject userVerificationRepository
    private final UserVerificationRepository userVerificationRepository;

    //  inject DaoAuthenticationProvider
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    //  inject JwtAuthenticationProvider
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    //  inject JwtEncoder
    private final JwtEncoder accessTokenJwtEncoder;

    //  inject JwtEncoder
    private final JwtEncoder refreshTokenJwtEncoder;


    @Value("${spring.mail.username}")
    private String adminEmail;


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();

        // Authenticate client with refresh token
        Authentication auth = new BearerTokenAuthenticationToken(refreshToken);
        auth = jwtAuthenticationProvider.authenticate(auth);

        log.info("Auth: {}", auth.getPrincipal());

        Jwt jwt = (Jwt) auth.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access APIs")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.SECONDS))
                .claim("isAdmin", true)
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        // Get expiration of refresh token
        Instant expiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, expiresAt).toDays();
        log.info("remainingDays: {}", remainingDays);
        if (remainingDays <= 1) {
            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .subject("Refresh Token")
                    .issuer(auth.getName())
                    .issuedAt(now)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .audience(List.of("NextJS", "Android", "iOS"))
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();
            refreshToken = refreshTokenJwtEncoder
                    .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                    .getTokenValue();
        }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        // Authentication client with userName (email) and password
        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        //  ROLE_USER ROLE_ADMIN
        String scope = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        //  Generate JWT token by JwtEncoder
        //  Define ClaimsSet (Payload)

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIs")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .claim("isAdmin", true)
                .claim("scope", scope)
                .build();

        JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Refresh token")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .claim("scope", scope)
                .build();

        //  Generate token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                .getTokenValue();


        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void resendVerification(String email) throws MessagingException {

        //  validate email
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                ));

        //  system generate userVerification
        UserVerification userVerification = userVerificationRepository
                .findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                ));


        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);


        //  Prepare for sending mail
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);
    }

    @Override
    public void verify(VerificationRequest verificationRequest) {

        // Validate email
        User user = userRepository
                .findByEmail(verificationRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User has not been found"));

        // Validate verified code
        UserVerification userVerification = userVerificationRepository
                .findByUserAndVerifiedCode(user, verificationRequest.verifiedCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User verification has not been found"));

        // Is verified code expired?
        if (LocalTime.now().isAfter(userVerification.getExpiryTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Verified code has expired");
        }

        user.setIsVerified(true);
        userRepository.save(user);

        userVerificationRepository.delete(userVerification);

    }

    @Override
    public void sendVerification(String email) throws MessagingException {

        //  validate email
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                ));

        //  system generate userVerification
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);


        //  Prepare for sending mail
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        //  validate user's email
        if (userRepository.existsByEmail(registerRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already in use"
            );
        }

        //  validate user's password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords do not match"
            );
        }

        //  validate term and policy
        if (!registerRequest.acceptTerm()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You must accept term"
            );
        }

        //  transfer from dto to domain model
        User user = userMapper.fromRegisterRequest(registerRequest);

        //system generate
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfile("profile/default-img.png");
        user.setIsDeleted(false);
        user.setIsVerified(false);

        Role roleUser = roleRepository.findRoleUser();
        Role roleCustomer = roleRepository.findRoleCustomer();
        List<Role> roles = List.of(roleUser, roleCustomer);

        //  set user role
        user.setRoles(roles);

        //  save record to table database
        userRepository.save(user);


        return RegisterResponse
                .builder()
                .message("You have registered successfully, please verify an email!")
                .email(user.getEmail())
                .build();
    }

}
