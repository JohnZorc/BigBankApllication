package com.example.demo;

import org.json.JSONObject;
import java.text.DecimalFormat;

public class CreditCardMinimumPaymentCalculator
{

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String CreditCardMinimumPaymentCalculator(double CCBalance, double CCInterestRate, double minimumPaymentPercentage) throws Exception
    {
        if ( CCBalance <= 0.0 || CCInterestRate <= 0.0 || minimumPaymentPercentage <= 0.0)
        {
            throw new Exception("Invalid input, please input only positive numbers.");
        }

        double monthlyPayment = CCBalance*(minimumPaymentPercentage/100.0);
        int months = 0;
        double total = CCBalance;
        double paid = total;

        if (monthlyPayment <= total*(CCInterestRate/100.0))
        {
            throw new Exception("Payment cannot be completed; minimumPaymentPercentage is too low");
        }

        while( total > 0.0)
        {
            total = total + CCBalance*(CCInterestRate/100.0) - monthlyPayment;
            paid = paid + CCBalance*(CCInterestRate/100.0);
            months++;
        }

        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("monthlyPayment", df.format(monthlyPayment));
        jsonObject.put("months", months);
        jsonObject.put("totalAmountPaid", df.format(paid));

        /** Former Printed Out Info
        System.out.println("Monthly Payment: " + df.format(monthlyPayment));
        System.out.println("# of Months to Pay Off Balance: " + months);
        System.out.println("Total $ Amount Paid: " + df.format(paid));*/

        return jsonObject.toString();

    }
}
