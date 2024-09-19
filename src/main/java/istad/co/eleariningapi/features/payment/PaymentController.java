package istad.co.eleariningapi.features.payment;

import istad.co.eleariningapi.features.payment.dto.PaymentCreateRequest;
import istad.co.eleariningapi.features.payment.dto.PaymentResponse;
import istad.co.eleariningapi.features.payment.dto.PaymentUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    //  inject paymentServices
    private final PaymentServices paymentServices;

    //  endpoint createNew payment
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    PaymentResponse createNew(@Valid @RequestBody PaymentCreateRequest paymentCreateRequest){
        return paymentServices.createNew(paymentCreateRequest);
    }

    //  endpoint findList
    @GetMapping
    Page<PaymentResponse> findList(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "25") int pageSize){
        return paymentServices.findList(pageNumber, pageSize);
    }

    //  endpoint updateByAlias
    @PatchMapping("/{alias}")
    PaymentResponse updateByAlias(@PathVariable String alias,
                                  @Valid @RequestBody PaymentUpdateRequest paymentUpdateRequest){
        return paymentServices.updateByAlias(alias, paymentUpdateRequest);
    }

    //  hide payment
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{alias}/hide")
    void hidePayment(@PathVariable String alias){
        paymentServices.hidePayment(alias);
    }
}
