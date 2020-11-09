package com.example.demo;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        obj.put("Organization_Name", "organization");
        obj.put("Industry", "comp sci");
        obj.put("PointOfContact_name", "Jerry Wu");
        obj.put("PointOfContact_email", "j.wu@ufl.edu");
        String ip = "random remote address";

        final JSONObject responseObj = new JSONObject();
        APIKey = ("organization"+"comp sci"+"Jerry Wu"+"j.wu@ufl.edu").hashCode();

        obj.put("APIKey",APIKey);

        //addKey should return the API Key through a jsonobj format, this checks if the endpoint works
        MvcResult response = mvc.perform(post("/AddKey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toString())
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("APIKey").value(APIKey))
//                .andExpect(content().string(containsString(responseObj.toString())))
                .andReturn();
        System.out.println(response.getResponse().getContentAsString());
    }

    @AfterAll
    //Destroying API Key
    //revokeKey should return the deleted user
    public static void terminate() throws Exception {

        //create expected return
        final JSONObject resObj = new JSONObject();
        resObj.put("organizationName","organization");
        resObj.put("industry","comp sci");
        resObj.put("pocFullName","Jerry Wu");
        resObj.put("pocEmail","j.wu@ufl.edu");
        resObj.put("ip","random remote address");
        resObj.put("APIKey",APIKey);

        //should we check after deletion that the other functions won't work anymore? (we know it won't, but for the purpose of it being established)
        mvc.perform(post("/RevokeKey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""+APIKey)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(resObj.toString())))
                .andReturn();
    }

    @Test
    void simpleSavings() throws Exception {
        final JSONObject obj = new JSONObject();
        obj.put("deposit", 100.00);
        obj.put("monthly", 10.0);
        obj.put("yearPeriods", 3.00);
        obj.put("interestRate", 10.00);
        obj.put("APIKey",this.APIKey);

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
        // Mock request body
        final JSONObject obj = new JSONObject();
        obj.put("homePrice", 250000.00);
        obj.put("downPaymentAsPercent", 8.5);
        obj.put("loanLength", 20);
        obj.put("interestRate", 5.00);
        obj.put("APIKey",this.APIKey);

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
        final JSONObject obj = new JSONObject();
        obj.put("CCBalance", 75);
        obj.put("CCInterestRate", 1);
        obj.put("minimumPaymentPercentage", 3);
        obj.put("APIKey",this.APIKey);

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
        // Mock request body
        final JSONObject obj = new JSONObject();
        obj.put("CCBalance", 320.63);
        obj.put("CCInterest", 7.5);
        obj.put("Months", 25);
        obj.put("APIKey",this.APIKey);

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