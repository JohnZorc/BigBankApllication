package com.example.demo;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class BigBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigBankApplication.class, args);
	}

	@PostMapping("/SimpleSavings")
	public String SimpleSavings(@RequestBody String SScalc) {
		final JSONObject obj = new JSONObject(SScalc);

		//Get all of the values via the keys
		double deposit = obj.getDouble("deposit");
		double monthly = obj.getDouble("monthly");
		double yearPeriods = obj.getDouble("yearPeriods");
		double interestRate = obj.getDouble("interestRate");

		//Get your JSON object of values from the SSCalculator class
		final JSONObject testObject = SimpleSavingsCalculator.SSCalculator(deposit,monthly,yearPeriods,interestRate);

		String returnString = testObject.toString();
		return returnString;
	}

	@PostMapping("/MortgageCalculator")
	public String MortgageCalculator(@RequestBody String MortCalc) throws Exception {
		final JSONObject obj = new JSONObject(MortCalc);

		// Get all values form request body.
		double homePrice = obj.getDouble("homePrice");
		double downPaymentAsPercent = obj.getDouble("downPaymentAsPercent");
		int loanLength = obj.getInt("loanLength");
		double interestRate = obj.getDouble("interestRate");

		return MortgageCalculator.calculate(homePrice, downPaymentAsPercent, loanLength, interestRate); //TODO: Change return value to double and input data into your function
	}

	@PostMapping("/CCMinCalculator")
	public String CreditCardMinCalculator(@RequestBody String CreditMin) {
		return CreditMin; //TODO: Change return value to double and input data into your function
	}

	@PostMapping("/CCPayoffCalculator")
	public String CCPayoffCalculator(@RequestBody String CreditMin) {
		return CreditMin; //TODO: Change return value to double and input data into your function
	}
}