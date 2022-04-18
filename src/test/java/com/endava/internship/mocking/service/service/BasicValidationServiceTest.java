package com.endava.internship.mocking.service.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.service.BasicValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicValidationServiceTest {

    BasicValidationService basicValidationService;

    @BeforeEach
    void prepare(){
        basicValidationService = new BasicValidationService();
    }

    @Test
    void validateAmountWithNullValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()->basicValidationService.validateAmount(null));
        assertThat(exception.getMessage()).isEqualTo("Amount must not be null");
    }

    @Test
    void validateAmountWithValueLessThanZero(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()->basicValidationService.validateAmount(-20.0));
        assertThat(exception.getMessage()).isEqualTo("Amount must be greater than 0");
    }

    @Test
    void validateAmountWithCorrectValue(){
        assertDoesNotThrow(()-> basicValidationService.validateAmount(5.0));
    }

    @Test
    void validatePaymentIdIsNullValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> basicValidationService.validatePaymentId(null));
        assertThat(exception.getMessage()).isEqualTo("Payment id must not be null");

    }

    @Test
    void validateUserIdIsNullValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> basicValidationService.validateUserId(null));
        assertThat(exception.getMessage()).isEqualTo("User id must not be null");
    }

    @Test
    void validateUserWithStatusNotActive(){
        User user = new User(1,"Ivan", Status.INACTIVE);

        Exception exception = assertThrows(IllegalArgumentException.class, ()-> basicValidationService.validateUser(user));
        assertThat(exception.getMessage()).isEqualTo("User with id " + 1 + " not in ACTIVE status");
    }

    @Test
    void validateMessageWithNullValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> basicValidationService.validateMessage(null));
        assertThat(exception.getMessage()).isEqualTo("Payment message must not be null");

    }
}
