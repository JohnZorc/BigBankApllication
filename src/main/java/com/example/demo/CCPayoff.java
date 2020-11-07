package com.example.demo;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CCPayoff
{

	public static JSONObject printPayOff(double ccBalance, double ccInterest, int months) {
		final JSONObject returnObject = new JSONObject();

		//split cuts the decimals values out so we can check for appropriate num of digits
		String[] split = ((Double)ccBalance).toString().split("\\.");

		if(ccBalance>0&&ccInterest>=0&&months>0&&split[1].length()<=2) {
			double totalInterest = calcInterest(ccBalance,ccInterest);
			// Format values to return them
			Locale locality = new Locale("en", "US");
			NumberFormat df = NumberFormat.getCurrencyInstance(locality);

			returnObject.put("CCTotalBalance", df.format(ccBalance));
			returnObject.put("CCTotalInterest", df.format(calcInterest(ccBalance,ccInterest)));
			returnObject.put("MonthlyPayment", calcMonthlyPayment(ccBalance,totalInterest,months));
		}else
			return null;

		return returnObject;

	}
	
	public static String calcMonthlyPayment(double totalPrinciple, double totalInterest, int months) {
		//format to have 2 dec points
		Locale locality = new Locale("en", "US");
		NumberFormat df = NumberFormat.getCurrencyInstance(locality);

		String result=df.format((totalPrinciple+totalInterest)/months);
		return result;
	}
	
	public static double calcInterest(double ccBalance, double ccInterest) {
		return ccBalance*(ccInterest/100);
	}

}
