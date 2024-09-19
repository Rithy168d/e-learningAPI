package istad.co.eleariningapi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// POJO pattern
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "reviews")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private BigDecimal rating;

    private String comment;

    private LocalDateTime creationDate;

//    has a relationship
    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;
}
