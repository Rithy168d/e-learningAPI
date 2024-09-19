package istad.co.eleariningapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// POJO pattern
@NoArgsConstructor
@Getter
@Setter

// JPA
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private String name;

    private String description;

    private Boolean status;

    @OneToMany(mappedBy = "category")
    private List<Course> courses;
}
