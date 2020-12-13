package com.example.demo;

import com.example.demo.schemas.Customer;
import com.example.demo.schemas.User;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static com.mongodb.client.model.Filters.eq;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BigBankApplicationTest {

    @Autowired
    private static MockMvc mvc;
    private static int APIKey;

    @BeforeAll
    //Checks if the end point correctly creates APIKey, uses the returned response as this test suite's APIKey
    public static void init() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new BigBankApplication()).build();

        //create input obj for endpoint
        final JSONObject obj = new JSONObject();
        obj.put("firstName", "John");
        obj.put("lastName", "Zorc");
        obj.put("emailAddress", "joe@shmoe");
        obj.put("homeAddress", "1234 mystreet SW");
        obj.put("password", "ffffffff");

        /*final JSONObject responseObj = new JSONObject();
        APIKey = ("organization"+"comp sci"+"Jerry Wu"+"j.wu@ufl.edu").hashCode();

        responseObj.put("APIKey",APIKey);*/

        //addKey should return the API Key through a jsonobj format, this checks if the endpoint works
        MvcResult response = mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(obj.toString())
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andReturn();

    }

    /*@AfterAll
    //Destroying API Key
    //revokeKey should return the deleted user
    public static void terminate() throws Exception {

        //create expected return
        final User resUser = new User(
            "organization",
            "comp sci",
            "Jerry Wu",
            "j.wu@ufl.edu",
            APIKey,
            "random remote address"
        );

        mvc.perform( MockMvcRequestBuilders.delete("/RevokeKey/{apikey}", APIKey) )
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(resUser.toString())));
    }*/

    @Test
    void simpleSavings() throws Exception {
        int hashedPassword = "ffffffff".hashCode();
        int apiKey = ("John"+"Zorc"+"joe@shmoe"+"1234 mystreet SW"+hashedPassword).hashCode();
        final JSONObject obj = new JSONObject();
        obj.put("deposit", 100.00);
        obj.put("monthly", 10.0);
        obj.put("yearPeriods", 3.00);
        obj.put("interestRate", 10.00);
        obj.put("APIKey", apiKey);

        final JSONObject responseObj = SimpleSavingsCalculator.SSCalculator(100.00, 10.00, 3.00, 10.00);

        mvc.perform(post("/SimpleSavings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(obj.toString())
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(responseObj.toString())))
            .andReturn();

    }

    @Test
    void testMortgageCalculatorEndpoint() throws Exception {
        int hashedPassword = "ffffffff".hashCode();
        int apiKey = ("John"+"Zorc"+"joe@shmoe"+"1234 mystreet SW"+hashedPassword).hashCode();
        // Mock request body
        final JSONObject obj = new JSONObject();
        obj.put("homePrice", 250000.00);
        obj.put("downPaymentAsPercent", 8.5);
        obj.put("loanLength", 20);
        obj.put("interestRate", 5.00);
        obj.put("APIKey",apiKey);

        // Expected respone:
        JSONObject response = MortgageCalculator.calculate(250000.00, 8.5, 20, 5.00);

        mvc.perform(post("/MortgageCalculator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(obj.toString())
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(response.toString())))
            .andReturn();
    }

    @Test
    void testCreditCardMinCalculator() throws Exception
    {
        int hashedPassword = "ffffffff".hashCode();
        int apiKey = ("John"+"Zorc"+"joe@shmoe"+"1234 mystreet SW"+hashedPassword).hashCode();
        final JSONObject obj = new JSONObject();
        obj.put("CCBalance", 75);
        obj.put("CCInterestRate", 1);
        obj.put("minimumPaymentPercentage", 3);
        obj.put("APIKey",apiKey);

        // Expected response:
        JSONObject response = CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(75, 1, 3);

        mvc.perform(post("/CCMinCalculator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(obj.toString())
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(response.toString())))
            .andReturn();
    }

    @Test
    void CCPayoffCalculator() throws Exception {
        int hashedPassword = "ffffffff".hashCode();
        int apiKey = ("John"+"Zorc"+"joe@shmoe"+"1234 mystreet SW"+hashedPassword).hashCode();
        // Mock request body
        final JSONObject obj = new JSONObject();
        obj.put("CCBalance", 320.63);
        obj.put("CCInterest", 7.5);
        obj.put("Months", 25);
        obj.put("APIKey",apiKey);

        // Expected response:
        JSONObject response = CCPayoff.printPayOff(320.63, 7.5, 25);

        mvc.perform(post("/CCPayoffCalculator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(obj.toString())
            .characterEncoding("utf-8"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(response.toString())))
            .andReturn();
    }
}