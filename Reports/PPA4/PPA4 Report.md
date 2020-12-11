##End-To-End Test Descriptions
###Unit tests for CFI function endpoints
There are a total of 17 unit tests for the CFI function endpoints that test invalid inputs
such as negative numbers and zero values. The unit tests then assert that with valid input the 
methods return the expected values. 

###Integration Tests
The integration test uses an mvcperform in an @beforeAll annotated init method that calls the 
/register endpoint. This will register a customer into the database and return a valid APIKey. 
The integration test will then use the APIKey to do 4 more mvc.perfoms() that will simulate 
endpoint calls to /simpleSavings, /testMortgageCalculatorEndpoint, /testCreditCardMinCalculator,
and /CCPayoffCalculator. These are all individual tests that will ensure the correct response is 
given from the endpoints. 
