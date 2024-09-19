package istad.co.eleariningapi.features.payment;

import istad.co.eleariningapi.domain.Course;
import istad.co.eleariningapi.domain.Payment;
import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.course.CourseRepository;
import istad.co.eleariningapi.features.payment.dto.PaymentCreateRequest;
import istad.co.eleariningapi.features.payment.dto.PaymentResponse;
import istad.co.eleariningapi.features.payment.dto.PaymentUpdateRequest;
import istad.co.eleariningapi.features.user.UserRepository;
import istad.co.eleariningapi.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PaymentServicesImpl implements PaymentServices {

    //  inject paymentRepository
    private final PaymentRepository paymentRepository;

    //  inject courseRepository
    private final CourseRepository courseRepository;

    //  inject userUuid
    private final UserRepository userRepository;

    //inject paymentMapper
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createNew(PaymentCreateRequest paymentCreateRequest) {

        //  validate course alias
        Course course = courseRepository.findByAlias(paymentCreateRequest.courseAlias())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "course alias is not found"
                ));

        //  validate user uuid
        User user = userRepository.findByUuid(paymentCreateRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user uuid is not found"
                ));

        //  validate is payment is exit
        if (paymentRepository.existsByAlias(paymentCreateRequest.alias())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "payment already exists"
            );
        }

        //  map from dto to domain model
        Payment payment = paymentMapper.fromPaymentCreateRequest(paymentCreateRequest);

        //  system generate
        payment.setCourse(course);
        payment.setUser(user);
        payment.setStatus("Completed");

        //  save record to table database
        payment = paymentRepository.save(payment);

        //  transfer from domain model to dto
        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public Page<PaymentResponse> findList(int pageNumber, int pageSize) {

        //  sort record by alias
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //  page request from client
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        //  find record after client request
        Page<Payment> payments = paymentRepository.findAll(pageRequest);

        return payments.map(paymentMapper::toPaymentResponse);
    }

    @Override
    public PaymentResponse updateByAlias(String alias, PaymentUpdateRequest paymentUpdateRequest) {

        //  validate alias is not exit
        Payment payment = paymentRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "course alias is not found"
                ));

        //  transfer from dto to domain
        paymentMapper.fromPaymentUpdateRequest(paymentUpdateRequest, payment);

        //  update
        payment = paymentRepository.save(payment);

        //  transfer from domain to dto
        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public void hidePayment(String alias) {
        //  validate payment's alias
        Payment payment = paymentRepository.findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Payment has not been found!"
                ));

        //  chang status to fail
        payment.setStatus("Failed");

        //  save to table database
        paymentRepository.save(payment);
    }

}
