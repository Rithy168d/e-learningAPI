package istad.co.eleariningapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //  inject bean passwordEncoder
    private final PasswordEncoder passwordEncoder;

    // inject userDetailsService
    private final UserDetailsService userDetailsService;

    @Bean
    JwtAuthenticationProvider configJwtAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder")JwtDecoder refreshTokenJwtDecoder) {
        JwtAuthenticationProvider auth = new JwtAuthenticationProvider(refreshTokenJwtDecoder);
        return auth;
    }

    //  authentication with Dao layer (Data access object)
    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    //  create bean for make stateless Session
    @Bean
    SecurityFilterChain configApiSecurity(HttpSecurity http,
                                          @Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception {

        //  Endpoint security config (Apply security following endpoint)
        http.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers("/api/v1/auth/**", "/api/v1/upload/**", "/upload/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").hasAuthority("SCOPE_USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/categories/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAuthority("SCOPE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").hasAuthority("SCOPE_USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/courses/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAuthority("SCOPE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/v1/payments/**").hasAuthority("SCOPE_USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/payments/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/payments/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/payments/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/payments/**").hasAuthority("SCOPE_ADMIN")

                .anyRequest().authenticated());

        //  Security Mechanism (HTTP Basic Auth)
        //  Username and Password
        //  http.httpBasic(Customizer.withDefaults());

        //  Security Mechanism (JWT)
        http.oauth2ResourceServer(jwt -> jwt
                .jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder))
        );


        //  Disable CSRF token
        http.csrf(AbstractHttpConfigurer::disable);

        //  Make stateless session
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
