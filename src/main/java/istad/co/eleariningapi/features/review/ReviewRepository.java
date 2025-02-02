package istad.co.eleariningapi.features.review;

import istad.co.eleariningapi.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Boolean existsByAlias(String alias);

    Optional<Review> findByAlias(String alias);
}
