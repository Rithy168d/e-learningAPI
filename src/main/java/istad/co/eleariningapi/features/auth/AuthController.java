package istad.co.eleariningapi.features.auth;

import istad.co.eleariningapi.features.auth.dto.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    //  inject AuthService
    private final AuthService authService;


    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    //  endpoint resendVerification
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/resend-verification")
    void resendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.resendVerification(sendVerificationRequest.email());
    }

    //  endpoint register
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    //  endpoint send verification
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/send-verification")
    void sendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.sendVerification(sendVerificationRequest.email());
    }

    //  endpoint verify
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/verify")
    void verify(@Valid @RequestBody VerificationRequest verificationRequest) {
        authService.verify(verificationRequest);
    }

    @PostMapping("/refresh-token")
    AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }


}
