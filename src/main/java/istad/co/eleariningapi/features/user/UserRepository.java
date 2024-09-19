package istad.co.eleariningapi.features.user;

import istad.co.eleariningapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);


}
