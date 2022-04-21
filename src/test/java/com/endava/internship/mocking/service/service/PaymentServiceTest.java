package com.endava.internship.mocking.service.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import com.endava.internship.mocking.service.PaymentService;
import com.endava.internship.mocking.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ValidationService validationService;
    @InjectMocks
    PaymentService paymentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createPayment() {
        User user = new User(1, "Ivan", Status.ACTIVE);
        int userId = user.getId();
        double amount = 200.0;

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(userId);
        when(paymentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        paymentService.createPayment(userId,amount);

        verify(userRepository).findById(userId);
        verify(paymentRepository).save(any());
    }


    @Test
    void editMessage() {
        UUID paymentId = UUID.randomUUID();
        String stringForPayment = "String for payment";

        paymentService.editPaymentMessage(paymentId, stringForPayment);

        verify(validationService).validatePaymentId(notNull());
        verify(paymentRepository).editMessage(paymentId, stringForPayment);
    }

    @Test
    void getAllByAmountExceeding() {
        double myAmount = 60.0;

        Payment first_payment = new Payment(1,150.0, "First");
        Payment second_payment = new Payment(2,50.0, "Second");
        Payment third_payment = new Payment(3,100.0, "third");

        List<Payment> paymentList = Arrays.asList(
          first_payment,second_payment,third_payment
        );

        List<Payment> returnList = Arrays.asList(
          first_payment,third_payment
        );

        when(paymentRepository.findAll()).thenReturn(paymentList);

        assertThat(paymentService.getAllByAmountExceeding(myAmount)).isEqualTo(returnList);
        verify(paymentRepository).findAll();
    }
}
