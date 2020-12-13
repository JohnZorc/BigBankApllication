package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MortgageCalculatorTest {

    @Test
    void testHomePriceIsPositive() {

        Exception exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(0.0,23.3,15,4.5)
        );
        assertEquals("User provided a non-positive and non-zero home price.", exception.getMessage());

        exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(-23000.43,23.3,15,4.5)
        );
        assertEquals("User provided a non-positive and non-zero home price.", exception.getMessage());
    }

    @Test
    void testDownPaymentPercentageIsPositive() {
        Exception exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,-23.3,15,4.5)
        );
        assertEquals("User provided a non-positive and non-zero down payment percentage.", exception.getMessage());

        exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,0.00,15,4.5)
        );
        assertEquals("User provided a non-positive and non-zero down payment percentage.", exception.getMessage());
    }

    @Test
    void testLoanLengthIsPositive() {
        Exception exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,8.5,0,4.5)
        );
        assertEquals("User provided a non-positive and non-zero loan length.", exception.getMessage());



        exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,9.4,-23,4.5)
        );
        assertEquals("User provided a non-positive and non-zero loan length.", exception.getMessage());
    }

    @Test
    void testInterestRateIsPositive() {
        Exception exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,8.5,20,0.00)
        );
        assertEquals("User provided a non-positive and non-zero interest rate.", exception.getMessage());



        exception = assertThrows( Exception.class, () ->
                MortgageCalculator.calculate(250000.00,9.4,20,-4.5)
        );
        assertEquals("User provided a non-positive and non-zero interest rate.", exception.getMessage());
    }

    @Test
    void testMortgageCalculatesCorrectly() throws Exception {
        /*String expected = "{\"monthlyPayment\":\"$1,005.21\",\"amountPaidInInterest\":\"$12,500.00\"" +
            ",\"amountPainInPrinciple\":\"$228,750.00\",\"totalAmountPaid\":\"$241,250.00\"}";*/

        assertEquals("$1,005.21", MortgageCalculator.calculate(250000.00,8.5,20,5.00).get("monthlyPayment"),
            "Tests all valid, non-zero numbers");
        assertEquals("$228,750.00", MortgageCalculator.calculate(250000.00,8.5,20,5.00).get("amountPainInPrinciple"),
            "Tests all valid, non-zero numbers");
        assertEquals("$12,500.00", MortgageCalculator.calculate(250000.00,8.5,20,5.00).get("amountPaidInInterest"),
            "Tests all valid, non-zero numbers");
        assertEquals("$241,250.00", MortgageCalculator.calculate(250000.00,8.5,20,5.00).get("totalAmountPaid"),
            "Tests all valid, non-zero numbers");
    }
}