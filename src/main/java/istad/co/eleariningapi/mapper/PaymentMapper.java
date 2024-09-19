package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Payment;
import istad.co.eleariningapi.features.payment.dto.PaymentCreateRequest;
import istad.co.eleariningapi.features.payment.dto.PaymentResponse;
import istad.co.eleariningapi.features.payment.dto.PaymentUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    //  map from dto to domain
    Payment fromPaymentCreateRequest(PaymentCreateRequest paymentCreateRequest);

    // map from domain to dto
    PaymentResponse toPaymentResponse(Payment payment);

    //  Partially mapper
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromPaymentUpdateRequest(PaymentUpdateRequest paymentUpdateRequest,
                                  @MappingTarget Payment payment);
}
