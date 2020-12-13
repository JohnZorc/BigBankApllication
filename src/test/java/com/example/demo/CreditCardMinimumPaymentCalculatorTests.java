package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CreditCardMinimumPaymentCalculatorTests
{

    @Test
    void testAllValidInputs() throws Exception {
        String expected = "{\"months\":50,\"monthlyPayment\":\"2.25\",\"totalAmountPaid\":\"112.50\"}";

        assertEquals(50, CreditCardMinimumPaymentCalculator.
            CreditCardMinimumPaymentCalculator(75, 1, 3).get("months")
        );
        assertEquals("2.25", CreditCardMinimumPaymentCalculator.
            CreditCardMinimumPaymentCalculator(75, 1, 3).get("monthlyPayment")
        );
        assertEquals("112.50", CreditCardMinimumPaymentCalculator.
            CreditCardMinimumPaymentCalculator(75, 1, 3).get("totalAmountPaid")
        );
    }


    @Test
    void testInvalidCCBalance() {

        Exception exception = assertThrows( Exception.class, () ->
                CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(0, 10, 3)
        );
        assertEquals("Invalid input, please input only positive numbers.", exception.getMessage());
    }

    @Test
    void testInvalidCCInterestRate() {

        Exception exception = assertThrows( Exception.class, () ->
                CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(1000, -10, 3)
        );
        assertEquals("Invalid input, please input only positive numbers.", exception.getMessage());
    }

    @Test
    void testInvalidMinPaymentPercentage() {

        Exception exception = assertThrows( Exception.class, () ->
                CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(1000, 10, -1)
        );
        assertEquals("Invalid input, please input only positive numbers.", exception.getMessage());
    }

    @Test
    void testMinimumPaymentTooLow() {

        Exception exception = assertThrows( Exception.class, () ->
                CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(10, 10, 2)
        );
        assertEquals("Payment cannot be completed; minimumPaymentPercentage is too low", exception.getMessage());
    }
}
