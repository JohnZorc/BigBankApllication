package com.example.demo;

import com.example.demo.schemas.Log;
import com.example.demo.schemas.User;
import com.example.demo.schemas.Customer;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.security.Signer;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@CrossOrigin(origins = "*")
@SpringBootApplication
@RestController
public class BigBankApplication
{
	String BAMStoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2Y" +
			"iOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ";
	public static void main(String[] args) {
		SpringApplication.run(BigBankApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//
//				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//
//			}
//		};
//	}

	// Setting up DB
	ConnectionString connectionString = new ConnectionString("mongodb://myUserAdmin:pp29softTest@35.188.134.30:27017/");
	CodecRegistry pojoCodecRegistry = fromRegistries(
			MongoClientSettings.getDefaultCodecRegistry(),
			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(connectionString)
			.codecRegistry(pojoCodecRegistry)
			.build();
	MongoClient mongoClient = MongoClients.create(settings); // Connects to mongoDB deamon running on port 27017
	MongoDatabase database = mongoClient.getDatabase("big-bank-db"); // Gets db from deamon, creates it if not found.

	ConnectionString connectionString2 = new ConnectionString("mongodb://myUserAdmin:pp29softTest@34.121.219.161:27017/");
	CodecRegistry pojoCodecRegistry2 = fromRegistries(
			MongoClientSettings.getDefaultCodecRegistry(),
			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	MongoClientSettings settings2 = MongoClientSettings.builder()
			.applyConnectionString(connectionString2)
			.codecRegistry(pojoCodecRegistry2)
			.build();
	MongoClient mongoClient2 = MongoClients.create(settings2); // Connects to mongoDB deamon running on port 27017
	MongoDatabase database2 = mongoClient2.getDatabase("big-bank-db"); // Gets db from deamon, creates it if not found.

	MongoCollection<Customer> customers = database.getCollection("customers", Customer.class);
	MongoCollection<Log> BAMLogs = database2.getCollection("logs", Log.class);
	//JWT Ticket setup
	String JWTSecret = "cis4930-group9-jtw-secret";
	// Build an HMAC signer using a SHA-256 hash
	HMACSigner signer = HMACSigner.newSHA256Signer(JWTSecret);


	@PostMapping("/SimpleSavings")
	public String SimpleSavings(@RequestBody String SScalc) throws Exception {
		final JSONObject obj = new JSONObject(SScalc);

		//Get all of the values via the keys
		double deposit = obj.getDouble("deposit");
		double monthly = obj.getDouble("monthly");
		double yearPeriods = obj.getDouble("yearPeriods");
		double interestRate = obj.getDouble("interestRate");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		//AddLog( SScalc,  APIKey,  "/SimpleSavings");
		if (APIKeyInterceptor(APIKey))
		{
			final JSONObject testObject = SimpleSavingsCalculator.SSCalculator(deposit,monthly,yearPeriods,interestRate);

			String returnString = testObject.toString();
			return returnString;
		}
		else
		{
			return "Invalid API Key";
		}

		//Get your JSON object of values from the SSCalculator class

	}

	@PostMapping("/MortgageCalculator")
	public String MortgageCalculator(@RequestBody String MortCalc) throws Exception {
		final JSONObject obj = new JSONObject(MortCalc);

		// Get all values form request body.
		double homePrice = obj.getDouble("homePrice");
		double downPaymentAsPercent = obj.getDouble("downPaymentAsPercent");
		int loanLength = obj.getInt("loanLength");
		double interestRate = obj.getDouble("interestRate");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		//AddLog( MortCalc,  APIKey,  "/MortgageCalculator");
		if (APIKeyInterceptor(APIKey))
		{
			return MortgageCalculator.calculate(homePrice, downPaymentAsPercent, loanLength, interestRate).toString();
		}
		else
		{
			return "Invalid API Key";
		}
	}

	@PostMapping("/CCMinCalculator")
	public String CreditCardMinCalculator(@RequestBody String CreditMin) throws Exception {

		final JSONObject obj = new JSONObject(CreditMin);

		double CCBalance = obj.getDouble("CCBalance");
		double CCInterestRate = obj.getDouble("CCInterestRate");
		double minimumPaymentPercentage = obj.getDouble("minimumPaymentPercentage");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		//AddLog( CreditMin,  APIKey,  "/CCMinCalculator");

		if (APIKeyInterceptor(APIKey))
		{
			return CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(CCBalance,
					CCInterestRate, minimumPaymentPercentage).toString();

		}
		else
		{
			return "Invalid API Key";
		}
	}

	@PostMapping("/CCPayoffCalculator")
	public String CCPayoffCalculator(@RequestBody String CreditPayoff) throws Exception {
		final JSONObject obj = new JSONObject(CreditPayoff);

		//Get all of the values via the keys
		double ccBalance = obj.getDouble("CCBalance");
		double ccInterest = obj.getDouble("CCInterest");
		int months = obj.getInt("Months");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		//AddLog( CreditPayoff,  APIKey,  "/CCPayoffCalculator");

		if (APIKeyInterceptor(APIKey))
		{
			//Get your JSON object of values from the SSCalculator class
			final JSONObject testObject = CCPayoff.printPayOff(ccBalance,ccInterest,months);

			String returnString = testObject.toString();
			return returnString;
		}
		else
		{
			return "Invalid API Key";
		}

	}

	//Receives Customer data and returns key
	@PostMapping("/register")
	public String register(@RequestBody String registerInfo) {

		final JSONObject registerInfoJSON = new JSONObject(registerInfo);

		String firstName = registerInfoJSON.getString("firstName");
		String lastName = registerInfoJSON.getString("lastName");
		String emailAddress = registerInfoJSON.getString("emailAddress");
		String homeAddress = registerInfoJSON.getString("homeAddress");
		String clearTextPassword = registerInfoJSON.getString("password");

		// hash password.
		int hashedPassword = clearTextPassword.hashCode();

		// create a new customer
		Customer newCustomer;
		try {
			newCustomer =
					AddKey(firstName, lastName, emailAddress, homeAddress, hashedPassword);
		}
		catch(Exception e) {
			return "A Customer with this email already exists";
		}

		// Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
		JWT jwt = new JWT().setIssuer("www.acme.com")
				.setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
				.setSubject(emailAddress)
				.setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(30));

		// Sign and encode the JWT to a JSON string representation
		String token = JWT.getEncoder().encode(jwt, signer);

		//recreate into a jsonobject
		JSONObject response = new JSONObject();
		response.put("token",token);
		response.put("APIKey", newCustomer.APIKey);
		response.put("emailAddress", newCustomer.emailAddress);
		response.put("firstName", newCustomer.firstName);
		response.put("lastName", newCustomer.lastName);
		response.put("homeAddress", newCustomer.homeAddress);
		response.put("customerID", newCustomer.getID());
		return response.toString();
	}

	@PostMapping("/login")
	public String login(@RequestBody String loginInfo) {
		// create JSON from loginInfo
		final JSONObject obj = new JSONObject(loginInfo);

		// extract email & password
		String email = obj.getString("email");
		int hashedPassword = obj.getString("password").hashCode();

		// check that they exist in db

		if(customers.find(and(eq("emailAddress", email), eq("password", hashedPassword))).first() != null){

			// Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
			JWT jwt = new JWT().setIssuer("www.acme.com")
					.setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
					.setSubject(email)
					.setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(30));

			// Sign and encode the JWT to a JSON string representation
			String encodedJWT = JWT.getEncoder().encode(jwt, signer);
			JSONObject response = new JSONObject();
			Customer currentCustomer = customers.find(eq("emailAddress", email)).first();
			response.put("APIKey", currentCustomer.APIKey);
			response.put("emailAddress", currentCustomer.emailAddress);
			response.put("firstName", currentCustomer.firstName);
			response.put("lastName", currentCustomer.lastName);
			response.put("homeAddress", currentCustomer.homeAddress);
			response.put("customerID", currentCustomer.id);
			response.put("token",encodedJWT);
			return response.toString();
		} else {
			// if they don't return error
			return "Invalid email/password.";
		}

	}

	@GetMapping("/dashboard")
	public String dashboard(@RequestHeader("Authorization") String token ) {
//		final JSONObject obj = new JSONObject(token);
//		obj.getString("token")
		if(TokenInterceptor(token)){
			return "Hello from the Dashboard page";
		} else {
			return "You do not have access to access this page.";
		}

	}

	@DeleteMapping(value = "/RevokeKey/{apikey}")
	public String RevokeKey(@PathVariable("apikey") int apiKey) {
		MongoCollection<User> users = database.getCollection("user", User.class);

		//find user based on apiKey
		Bson filter = eq("APIKey", apiKey);

		//delete from db
		User deletedUser = users.findOneAndDelete(filter);

		//return deleted user
		return deletedUser.toString();
	}

	@PostMapping("/addAccount")
	public String addAccount(@RequestBody String accountInfo) {
		try {
			return "OK";
		}
		catch(Exception e) {
			return "There was an error creating the account";
		}
	}

	//Record a human readable timestamp, the type of transaction (create, transfer, deposit),
	// the customer ID (not email), account numbers impacted, and any dollar amount recorded for the transaction.

	@PostMapping("/createLog")
	public void LogBAMS(@RequestBody String logInfo) {
		final JSONObject obj = new JSONObject(logInfo);

		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		String transactionType = obj.getString("transactionType");
		String customerID = obj.getString("customerID");
		String account1 = obj.getString("account1");
		String account2 = obj.getString("account2");
		double dollarAmount = obj.getDouble("dollarAmount");

		Log newLog = new Log(timestamp, transactionType, customerID, account1, account2, dollarAmount);
		BAMLogs.insertOne(newLog);
	}

	@GetMapping("/GetAllLogs")
	public List<String> GetAllLogs() {
		List<String> response = new ArrayList<String>();

		for (Log log: BAMLogs.find()) {
			response.add(log.toString());
		}

		//BAMLogs.find().forEach(addLogToList);

		return response;
	}

	public boolean APIKeyInterceptor(int APIKey)
	{
		return (customers.find(eq("APIKey", APIKey)).first()!=null);
	}

	public boolean TokenInterceptor(String encodedJWT){
		boolean response = false;
		try {
			// Create verifier
			Verifier verifier = HMACVerifier.newVerifier(JWTSecret);

			// Verify and decode the encoded string JWT to a rich object
			JWT jwt = JWT.getDecoder().decode(encodedJWT, verifier);

			// Extract email from token
			String email = jwt.subject;

			// Verify email exists in db.
			if (customers.find(eq("emailAddress", email)).first() != null) {
				response = true;
			}
		} catch (Exception e){
			response = false;
		} finally {
			return response;
		}

	}

	/*public void AddLog(String body, int APIKey, String EndPoint) throws Exception {
		//Returns collection or view object. Will create one if there is not one yet specified
		MongoCollection<Log> logs = database.getCollection("requestHistory", Log.class);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Log insert = new Log(timestamp.toString(), body, EndPoint, APIKey);

		//inserts the new log document into the log collection in the big-bank-db database
		logs.insertOne(insert);
	}*/

	public Customer AddKey(String firstName, String lastName, String emailAddress, String homeAddress, int hashedPassword) throws Exception {
		int apiKey;
		//create key
		apiKey = (firstName+lastName+emailAddress+homeAddress+hashedPassword).hashCode();
		Customer newCustomer = new Customer(
				firstName,
				lastName,
				emailAddress,
				homeAddress,
				hashedPassword,
				apiKey
		);
		//check if apikey already exists, if not -> log key and user data into db
		if(customers.find(eq("emailAddress", emailAddress)).first()==null) {
			customers.insertOne(newCustomer);
			return customers.find(eq("emailAddress", emailAddress)).first();
		}
		else {
			throw new Exception("Customer exists");
		}
	}

}