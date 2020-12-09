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

	MongoCollection<Customer> customers = database.getCollection("customers", Customer.class);

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
		AddLog( SScalc,  APIKey,  "/SimpleSavings");
//		if (APIKeyInterceptor(APIKey))
//		{
			final JSONObject testObject = SimpleSavingsCalculator.SSCalculator(deposit,monthly,yearPeriods,interestRate);

			String returnString = testObject.toString();
			return returnString;
//		}
//		else
//		{
//			return "Invalid API Key";
//		}

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
		AddLog( MortCalc,  APIKey,  "/MortgageCalculator");
//		if (APIKeyInterceptor(APIKey))
//		{
			return MortgageCalculator.calculate(homePrice, downPaymentAsPercent, loanLength, interestRate).toString();
//		}
//		else
//		{
//			return "Invalid API Key";
//		}
	}

	@PostMapping("/CCMinCalculator")
	public String CreditCardMinCalculator(@RequestBody String CreditMin) throws Exception {

		final JSONObject obj = new JSONObject(CreditMin);

		double CCBalance = obj.getDouble("CCBalance");
		double CCInterestRate = obj.getDouble("CCInterestRate");
		double minimumPaymentPercentage = obj.getDouble("minimumPaymentPercentage");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		AddLog( CreditMin,  APIKey,  "/CCMinCalculator");

//		if (APIKeyInterceptor(APIKey))
//		{
			return CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(CCBalance,
					CCInterestRate, minimumPaymentPercentage).toString();

//		}
//		else
//		{
//			return "Invalid API Key";
//		}
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
		AddLog( CreditPayoff,  APIKey,  "/CCPayoffCalculator");

//		if (APIKeyInterceptor(APIKey))
//		{
			//Get your JSON object of values from the SSCalculator class
			final JSONObject testObject = CCPayoff.printPayOff(ccBalance,ccInterest,months);

			String returnString = testObject.toString();
			return returnString;
//		}
//		else
//		{
//			return "Invalid API Key";
//		}

	}

	//Receives user data and returns key
	@PostMapping("/AddKey")
	public User AddKey(@RequestBody String entityInfo) {

		//get ip
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();

		final JSONObject entityInfoJSON = new JSONObject(entityInfo);
		MongoCollection<User> users = database.getCollection("user", User.class);
		int apiKey;

		//Get all of the values via the keys
		String orgName = entityInfoJSON.getString("Organization_Name");
		String industry = entityInfoJSON.getString("Industry");
		String POCname = entityInfoJSON.getString("PointOfContact_name");
		String POCemail = entityInfoJSON.getString("PointOfContact_email");
		String ip = request.getRemoteAddr();


		//create key
		apiKey = (orgName+industry+POCname+POCemail).hashCode();

		User newUser = new User(
				orgName,
				industry,
				POCname,
				POCemail,
				apiKey,
				ip
		);

		//check if apikey already exists, if not -> log key and user data into db
		if(users.find(eq("APIKey", apiKey)).first()==null)
			users.insertOne(newUser);
		else
			apiKey = -1;

		//recreate into a jsonobject
		return newUser;
	}

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
		Customer newCustomer = new Customer(firstName, lastName, emailAddress, homeAddress, hashedPassword);

		// Save it to db.
		//check if customer already exists, if not -> log new customer and add to db
		String message;
		if(customers.find(eq("emailAddress", emailAddress)).first()==null){
			customers.insertOne(newCustomer);
			message = "User was successfully created.";
		} else {
			message = "A user with this email already exists.";
		}

		//recreate into a jsonobject
		JSONObject response = new JSONObject();
		response.put("message",message);
		return message;
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
			return encodedJWT;
		} else {
			// if they don't return error
			return "Invalid email/password.";
		}

	}

	@GetMapping("/dashboard")
	public String dashboard(@RequestHeader("Authorization") String token ) {
		System.out.println(token);

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

	@GetMapping("/GetAllLogs")
	public List<String> GetAllLogs() {
		MongoCollection<Document> logs = database.getCollection("requestHistory");
		List<String> response = new ArrayList<String>();

		Consumer<Document> addLogToList = new Consumer<Document>() {
			@Override
			public void accept(final Document document) {
				response.add(document.toJson());
			}
		};


		logs.find().forEach(addLogToList);

		System.out.println(response);



		return response;
	}

	public boolean APIKeyInterceptor(int APIKey)
	{
		MongoCollection<User> users = database.getCollection("user", User.class);
		return (users.find(eq("APIKey", APIKey)).first()!=null);
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

	public void AddLog(String body, int APIKey, String EndPoint) throws Exception {
		//Returns collection or view object. Will create one if there is not one yet specified
		MongoCollection<Log> logs = database.getCollection("requestHistory", Log.class);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Log insert = new Log(timestamp.toString(), body, EndPoint, APIKey);

		//inserts the new log document into the log collection in the big-bank-db database
		logs.insertOne(insert);
	}


}