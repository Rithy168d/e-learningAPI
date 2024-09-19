package istad.co.eleariningapi.domain;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// POJO pattern 
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private String title;

    private String description;

    private BigDecimal price;

    private Integer rating;

    private String language;

    private String level;

    private Boolean status;

//    has a relationship
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<Payment> payments;

    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;


}
