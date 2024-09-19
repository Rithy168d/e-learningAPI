package istad.co.eleariningapi.features.enrollment;

import istad.co.eleariningapi.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
   Boolean existsByAlias (String alias);

   Optional<Enrollment> findByAlias (String alias);
}
