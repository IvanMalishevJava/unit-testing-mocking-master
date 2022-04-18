package com.endava.internship.mocking.service.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.repository.InMemPaymentRepository;
import com.endava.internship.mocking.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemPaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;
    private Payment payment3;


    @BeforeEach
    void setUp() {
        paymentRepository = new InMemPaymentRepository();
        payment1 = new Payment(1, 100.0, "some string.");
        payment2 = new Payment(2, 500.0, "some string.");
        payment3 = new Payment(3, 20000.0, "some string.");
    }

    @Test
    void findByIdTestNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> { paymentRepository.findById(null);});
    }

    @Test
    void findByIdTestNotNull() {
        UUID id = payment1.getPaymentId();

        paymentRepository.save(payment1);

        assertTrue(paymentRepository.findById(id).isPresent());
        assertThat(paymentRepository.findById(id)).contains(payment1);
    }

    @Test
    void findAllTest() {
        List<Payment> list = Arrays.asList(payment1, payment2, payment3);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        paymentRepository.save(payment3);

        assertTrue(paymentRepository.findAll().containsAll(list));
    }

    @Test
    void saveTestNullPayment() {
        assertThrows(
                IllegalArgumentException.class,
                () -> paymentRepository.save(null));
    }

    @Test
    void saveTestNonExistentId() {
        paymentRepository.save(payment1);
        assertThrows(IllegalArgumentException.class,
                () -> paymentRepository.save(payment1)
                );
    }

    @Test
    void saveTestWorksCorrectly() {
        assertThat(paymentRepository.save(payment1)).isEqualTo(payment1);
    }

    @Test
    void editMessageTestNullPayment() {
        assertThrows(NoSuchElementException.class, () -> {
            paymentRepository.editMessage(payment1.getPaymentId(), "String");
        });
    }

    @Test
    void editMessagePaymentTest() {
        paymentRepository.save(payment1);

        assertThat(paymentRepository.editMessage(payment1.getPaymentId(), "String"))
                .isEqualTo(payment1);

        assertThat(paymentRepository.editMessage(payment1.getPaymentId(), "String").
                getMessage()).isEqualTo("String");
}
}
