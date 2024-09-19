package istad.co.eleariningapi.features.payment;

import istad.co.eleariningapi.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Boolean existsByAlias (String alias);

    Optional<Payment> findByAlias (String alias);

}
