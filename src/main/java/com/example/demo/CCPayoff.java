package com.example.demo;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class CCPayoff {

	public static JSONObject printPayOff(double ccBalance, double ccInterest, int months) {
		final JSONObject returnObject = new JSONObject();

		//split cuts the decimals values out so we can check for appropriate num of digits
		String[] split = ((Double)ccBalance).toString().split("\\.");

		if(ccBalance>0&&ccInterest>=0&&months>0&&split[1].length()<=2) {
			double totalInterest = calcInterest(ccBalance,ccInterest);
			DecimalFormat df = new DecimalFormat("0.##");

			returnObject.put("CCTotalBalance", Double.parseDouble(df.format(ccBalance)));
			returnObject.put("CCTotalInterest", Double.parseDouble(df.format(totalInterest)));
			returnObject.put("MonthlyPayment", calcMonthlyPayment(ccBalance,totalInterest,months));
		}else
			return null;

		return returnObject;

	}
	
	public static double calcMonthlyPayment(double totalPrinciple, double totalInterest, int months) {
		//format to have 2 dec points
		DecimalFormat df = new DecimalFormat("0.##");
		String result=df.format((totalPrinciple+totalInterest)/months);
		return Double.parseDouble(result);
	}
	
	public static double calcInterest(double ccBalance, double ccInterest) {
		return ccBalance*(ccInterest/100);
	}

}
