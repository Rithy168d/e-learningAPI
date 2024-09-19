package istad.co.eleariningapi.features.lecture;

import istad.co.eleariningapi.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    Boolean existsByAlias(String alias);

    Optional<Lecture> findByAlias(String alias);
}
