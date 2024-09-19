package istad.co.eleariningapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// POJO pattern
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "letures")

public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private String title;

    private String description;

     // "video," "article," or "quiz."
    private String content;

    private String duration;

    private Boolean status;

//    has a relationship
    @ManyToOne
    private Course course;
}
