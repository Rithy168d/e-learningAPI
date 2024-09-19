package istad.co.eleariningapi.features.auth;

import istad.co.eleariningapi.features.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {

    /**
     * Refresh Token
     * @param refreshTokenRequest {@link RefreshTokenRequest}
     * @return {@link AuthResponse}
     */
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    /**
     * Login
     * @param loginRequest {@link LoginRequest}
     * @return {@link AuthResponse}
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * ResendVerification
     * @param email is required
     */
    void resendVerification(String email) throws MessagingException;

    /**
     * Verify
     * @param verificationRequest {@link VerificationRequest}
     */
    void verify(VerificationRequest verificationRequest);

    /**
     * Send Verification
     * @param email is required
     */
    void sendVerification(String email) throws MessagingException;

    /**
     * Register
     * @param registerRequest {@link RegisterRequest}
     * @return {@link RegisterResponse}
     */
    RegisterResponse register(RegisterRequest registerRequest);
}
