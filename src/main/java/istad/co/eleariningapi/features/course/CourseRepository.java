package istad.co.eleariningapi.features.course;

import istad.co.eleariningapi.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByAlias (String alias);

    Optional<Course> findByAlias (String alias);
}
