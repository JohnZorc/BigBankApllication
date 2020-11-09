package com.example.demo;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MortgageCalculator
{

    // INPUTS:
    //  home price,
    //  down payment as a percentage (e.g., 15.5%),
    //  length of loan in years (whole number),
    //  interest rate


    // OUTPUTS:
    //  monthly payment,
    //  amount paid in interest,
    //  amount paid in principle,
    //  total amount paid


    public static JSONObject calculate(double homePrice, double downPaymentAsPercent, int loanLength, double interestRate) throws Exception {

        // Check inputs are valid
        if(homePrice <= 0){
            throw new Exception("User provided a non-positive and non-zero home price.");
        }

        if(downPaymentAsPercent <= 0){
            throw new Exception("User provided a non-positive and non-zero down payment percentage.");
        }

        if(loanLength <= 0){
            throw new Exception("User provided a non-positive and non-zero loan length.");
        }

        if(interestRate <= 0){
            throw new Exception("User provided a non-positive and non-zero interest rate.");
        }

        // Perform calculations
        double amountPaidInInterest = (interestRate / 100) * (homePrice);
        double amountPainInPrinciple = homePrice - ((downPaymentAsPercent / 100) * homePrice);
        double totalAmountPaid = amountPaidInInterest + amountPainInPrinciple;
        double monthlyPayment = totalAmountPaid / (12 * loanLength);

        // Format values to return them
        Locale locality = new Locale("en", "US");
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(locality);

        // Create JSON to return and populate it.
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("monthlyPayment", numberFormatter.format(monthlyPayment));
        jsonObject.put("amountPainInPrinciple", numberFormatter.format(amountPainInPrinciple));
        jsonObject.put("amountPaidInInterest", numberFormatter.format(amountPaidInInterest));
        jsonObject.put("totalAmountPaid", numberFormatter.format(totalAmountPaid));

        return jsonObject;
    }




}
