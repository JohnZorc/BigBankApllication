package com.example.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BigBankApplicationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void simpleSavings() {
    }

    @Test
    void testMortgageCalculatorEndpoint() throws Exception {
        // Mock request body
        final JSONObject obj = new JSONObject();
        obj.put("homePrice", 250000.00);
        obj.put("downPaymentAsPercent", 8.5);
        obj.put("loanLength", 20);
        obj.put("interestRate", 5.00);

        // Expected respone:
        String response = MortgageCalculator.calculate(250000.00, 8.5, 20, 5.00);

        mvc.perform(post("/MortgageCalculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toString())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(response)))
            .andReturn();
    }

    @Test
    void testCreditCardMinCalculator() throws Exception
    {
        final JSONObject obj = new JSONObject();
        obj.put("CCBalance", 75);
        obj.put("CCInterestRate", 1);
        obj.put("minimumPaymentPercentage", 3);

        // Expected response:
        String response = CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(75, 1, 3);

        mvc.perform(post("/CCMinCalculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toString())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(response)))
                .andReturn();
    }

    @Test
    void CCPayoffCalculator() {
    }
}