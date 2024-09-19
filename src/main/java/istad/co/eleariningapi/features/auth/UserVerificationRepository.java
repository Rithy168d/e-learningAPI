package istad.co.eleariningapi.features.auth;

import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    Optional<UserVerification> findByUserAndVerifiedCode(User user, String verifiedCode);

    Optional<UserVerification> findByUser(User user);
}
