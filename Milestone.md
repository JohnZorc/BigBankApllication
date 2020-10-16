# Milestone Doc

## Function #1 Credit Card Payoff
__Valid Input for Months__ - The input for months should be a positive integer bigger than 0. 

__Valid Input for Interest Rate__ - The input for interest rate should be a positive decimal.

__Valid Input for CC Balance__ - The input for CC Balance should be a positive decimal bigger than 0.

__Valid Output Formats__ - The output for monthly payment, total principle paid, and total interest paid should only have 2 decimal places.

__Valid Output Values__ - The calculations for monthly payment, total principle paid, and total interest paid should be correct based on the equations set.

## Function #2 Simple Savings Calculator

The simple savings calculator should return the total savings growth over a given number of periods rounded to two decimals to represent the nearest cent. If any of the inputs are invalid (Ex. Negative initial deposit value) The function will return a -1.
### List of tests that implementation will pass:

Total growth test: The total growth in savings should be equal to the total contributions added to the (interest rate * (total contributions + initial deposit)). Note: This is simple interest calculator, so we do not apply the interest rate to previous gains made by interest (compounding interest).

No Initial Deposit test: The total savings growth in this case should be equal to the total contributions multiplied by the interest rate.

No Monthly Growth test: The total savings growth in this case should be equal to the initial deposit multiplied by the interest rate P period number of times. 

Interest rate is 0 test: If the interest rate is set to zero the total savings growth should just be equal to the total contributions.

## Function #3

__Valid Input for Minimum Payment Percentage__ - The input for minimum payment percentage should be a positive non-zero floating-point number. Otherwise, it should throw an error.

__Valid Input for Interest Rate__ - The input for interest rate should be a positive non-zero floating-point number. Otherwise, it should throw an error.

__Valid Input for CC Balance__ - The input for CC Balance should be a positive non-zero floating-point number bigger than 0. Otherwise, it should throw an error.

__Valid Output Formats__ - The output for monthly payment and total amount paid should only have 2 decimal places, while # of months should be a positive integer.

__Valid Output Values__ - The calculations for monthly payment, # of months to pay of balance, and total amount paid should be correct based on the equations set.


## Function #4 Mortgage calculator

__Valid Home Price Test__ - The home price parameter should be a positive float greater than $0. If it is not, the method should throw an error.

__Valid Down Payment Test__ - The down payment percentage parameter should be a floating-point number greater than or equal to 0.00% . IF it is not, the method should throw an error.

__Valid Loan Length Test__ - The loan length parameter should be an integer greater than 0. If it is not, the method should throw an error. 

__Valid Interest Rate Test__ - The interest rate parameter should be a floating-point number greater than or equal to 0.00%. If it is not, the method should throw an error.

__Correct Monthly Mortgage Test__ - Given a valid home price, down payment, loan length, and interest rate, the method should return the correct monthly payment, amount paid in interest, amount paid in principle, total amount paid.
   

