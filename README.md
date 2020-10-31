# big-bank-cfi-suite-sw-testing-team-9
big-bank-cfi-suite-sw-testing-team-9 created by GitHub Classroom

## Test Documentation:

### Unit Tests
**CCPayoffTest**
* A negative credit card balance input will return an error message.
* A negative interest input will return an error message.
* A negative month input will return an error message.
* An invalid balance input will return an error message.
* An invalid balance input will return an error message.
* A 0 month input will return an error message.
* A 0 credit card balance input will return an error message.
* Calculation and format of monthly payment should be correct.
* Calculation and format of total interest should be correct.

**CreditCardMinimumPaymentCalculatorTests**
* Any negative inputs should throw an error.
* An invalid Credit Card balance should throw an error.
* An invalid interest rate should throw an error.
* An invalid minimum payment percentage should throw an error.
* A minimum payment that is too low should throw an error.
* When all inputs are valid, the function should return the correct credit card payments.

**MortgageCalculatorTest**
* A negative price should throw an error.
* A home price of zero should throw an error.
* A negative percentage down-payment should throw an error.
* A percentage down-payment of zero should throw an error.
* A negative loan length should throw an error.
* A loan length of zero should throw an error.
* A negative interest rate should throw an error.
* An interest rate of zero should throw an error.
* Valid values for all parameters should return the correct monthly payments.

**SimpleSavingsCalculatorTests**
* A negative monthly value should return an error.
* A negative deposit value should return an error.
* A negative period of years should return an error.
* A negative interest rate should return an error.
* When all parameters are valid, we should return the correct contribution, interest earned, and total savings. 

### Integration Tests
**BigBankApplicationTest**
* HTTP POST Request mock to verify */SimpleSavings* endpoint.
* HTTP POST Request mock to verify */MortgageCalculator* endpoint.
* HTTP POST Request mock to verify */CCMinCalculator* endpoint.
* HTTP POST Request mock to verify */CCPayoffCalculator* endpoint.


## API Endpoints
**POST /AddKey** - Endpoint to register a user which returns an API key. 
Requires the following parameters in the JSON body of the request:
* orgName (String)
* industry (String)
* POCname (String)
* POCemail (String)
* ip (String)
    
**DELETE /RevokeKey** - Endpoint to revoke an API Key.
Requires the following parameters in the JSON body of the request:
* APIKey (Int)

**POST /CCPayoffCalculator** - Endpoint for Credit Card Payoff Calculator.
Requires the following parameters in the JSON body of the request:
* CCBalance (Double)
* CCInterest (Double)
* Months (Int)
* APIKey (Int)

**POST /CCMinCalculator** - Endpoint for CCMinCalculator.
Requires the following parameters in the JSON body of the request:
* CCBalance (double)
* CCInterestRate (double)
* minimumPaymentPercentage (double)
* APIKey (Int)


**POST /MortgageCalculator** - Endpoint for Mortgage Calculator Functionality.
Requires the following parameters in the JSON body of the request:
* homePrice (double)
* downPaymentAsPercent (double)
* loanLength (int)
* interestRate (double)
* APIKey (int)

*POST /SimpleSavings* - Endpoint for Simple Savings Calculator.
Requires the following parameters in the JSON body of the request:
* deposit (double)
* monthly (double)
* yearPeriods (double)
* interestRate (double)
* APIKey (int)

*GET /GetAllLogs* - Endpoint that returns all logs in the database.

## Database Schema Documentation
**Log Schema:**
```
{
    id (Object Id),
    timestamp (String),
    requestOrResponse (String),
    endpoint (String),
    APIKeyUsed (Int)
}
```
**User Schema**
```
{
    id (Object Id),
    organizationName (String),
    industry (String),
    pocFullName (String),
    pocEmail (String),
    ip (String),
    APIKey (Int),

}
```


## Execution instructions:
### Docker:
__After compiling the project__, run the following: 

1. `gradle build -x test`
2. `docker build -t bbapp .`
3. `docker-compose up`

### Vagrant:  
#### Setting up Vagrant 
Download and install our repository. The Vagrantfile includes commands for setting up the 
virtual machine and installing Java JDK8.
#### Commands
##### Vagrant init
Initializes Vagrant
##### Vagrant up
Starts the virtual machine and runs the vagrantfile
##### Vagrant ssh 
Use to log into your vitual machine.

##### Running Tests
All of the tests will be included in a tests folder. Navigate to the test folder and compile the test file you wish to run using javac, then run that test file using the java command. 

## Link to deployed application:
....


