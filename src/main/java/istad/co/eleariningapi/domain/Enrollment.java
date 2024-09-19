package istad.co.eleariningapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


// POJO pattern
@Getter
@Setter
@NoArgsConstructor

// JPA
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private String enrollmentDate;

    private BigDecimal progress;

    private Boolean completionStatus;

//    has a relationship
    @ManyToOne
    private Course course;

    @ManyToOne
    @JoinTable(
            name = "user_enrollment",
            joinColumns = @JoinColumn(name = "enrollment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private User user;

}
