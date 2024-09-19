package istad.co.eleariningapi.features.payment;

import istad.co.eleariningapi.features.payment.dto.PaymentCreateRequest;
import istad.co.eleariningapi.features.payment.dto.PaymentResponse;
import istad.co.eleariningapi.features.payment.dto.PaymentUpdateRequest;
import org.springframework.data.domain.Page;

public interface PaymentServices {

    /**
     * Create new payment
     * @param paymentCreateRequest {@link PaymentCreateRequest}
     * @return {@link PaymentResponse}
     */
    PaymentResponse createNew(PaymentCreateRequest paymentCreateRequest);

    /**
     * find List
     * @param pageNumber is required
     * @param pageSize is required
     * @return {@link PaymentResponse}
     */
    Page<PaymentResponse> findList(int pageNumber, int pageSize);

    /**
     * Update payment by alias
     * @param paymentUpdateRequest {@link PaymentUpdateRequest}
     * @return {@link PaymentResponse}
     */
    PaymentResponse updateByAlias(String alias, PaymentUpdateRequest paymentUpdateRequest);

    /**
     * Hide payment by alias
     * @param alias is required
     */
    void hidePayment(String alias);

}
