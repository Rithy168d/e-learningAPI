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
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;

    private BigDecimal amount;

    // Apple Pay, Google Pay, or PayPal.
    private String paymentMethod;

    // 'Completed', 'Pending', 'Failed'
    private String status;

//    has a relationship
    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;
}
