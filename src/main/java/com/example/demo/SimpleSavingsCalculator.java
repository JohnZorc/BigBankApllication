package com.example.demo;
import org.json.JSONObject;

public class SimpleSavingsCalculator {

    public static JSONObject SSCalculator (double deposit, double monthly, double yearPeriods, double interestRate) {
        double totalContributions= 0.0; // = (double)12 * yearPeriods * monthlyyyy;
        double savingsTotal = deposit;
        double yearsInterestGains;
        double totalInterestGains = 0.0;
        final JSONObject returnObject = new JSONObject();
        if(deposit < 0.0 || monthly < 0.0 || yearPeriods < 0.0 || interestRate < 0.0 ) {
            System.out.println("Invalid input, please input only positive numbers.");
            return null;
        }

        while(yearPeriods >= 1.0) {
            yearPeriods -= 1;
            totalContributions += (double) 12 * monthly;
            yearsInterestGains = (totalContributions + deposit) * interestRate * .01;
            totalInterestGains += yearsInterestGains;
        }
        totalContributions += (double) 12 * monthly * yearPeriods;
        yearsInterestGains = (totalContributions + deposit) * interestRate * .01;
        totalInterestGains += yearsInterestGains * yearPeriods;
        savingsTotal = deposit + totalContributions + totalInterestGains;

        returnObject.put("TotalContributions", totalContributions);
        returnObject.put("TotalInterestEarned", totalInterestGains);
        returnObject.put("TotalSavings", savingsTotal);

        return returnObject;
    }
}
