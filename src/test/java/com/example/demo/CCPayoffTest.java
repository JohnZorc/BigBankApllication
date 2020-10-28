package com.example.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CCPayoffTest {

	@Test
	public void testInvalidInputs(){
		JSONObject nullObject = new JSONObject();
		assertEquals(null, CCPayoff.printPayOff(-234.45, 10, 8), "A negative credit card balance input will return an error message.");
		assertEquals(null, CCPayoff.printPayOff(234.45, -10, 8), "A negative interest input will return an error message.");
		assertEquals(null, CCPayoff.printPayOff(234.45, 10, -8), "A negative month input will return an error message.");
		assertEquals(null, CCPayoff.printPayOff(234.4, 10, -8), "An invalid balance input will return an error message.");
		assertEquals(null, CCPayoff.printPayOff(234.4555, 10, -8), "An invalid balance input will return an error message.");


		assertEquals(null, CCPayoff.printPayOff(234.45, 10, 0), "A 0 month input will return an error message.");
		assertEquals(null, CCPayoff.printPayOff(0, 10, 8), "A 0 credit card balance input will return an error message.");
	}

	@Test
	public void testCorrectOutput(){
		//checking monthly payment
		assertEquals("$32.24", CCPayoff.printPayOff(234.45, 10,8).get("MonthlyPayment"), "Calculation and format of monthly payment should be correct.");
		assertEquals("$15.60", CCPayoff.printPayOff(108.5, 15,8).get("MonthlyPayment"), "Calculation and format of monthly payment should be correct.");

		//checking total interest
		assertEquals("$35.17", CCPayoff.printPayOff(234.45, 15,8).get("CCTotalInterest"), "Calculation and format of total interest should be correct.");

	}

}
