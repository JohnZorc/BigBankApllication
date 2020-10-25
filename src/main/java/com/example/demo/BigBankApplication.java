package com.example.demo;

import com.example.demo.schemas.Log;
import com.example.demo.schemas.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@SpringBootApplication
@RestController
public class BigBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigBankApplication.class, args);
	}

	// Setting up DB
	CodecRegistry pojoCodecRegistry = fromRegistries(
			MongoClientSettings.getDefaultCodecRegistry(),
			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	MongoClientSettings settings = MongoClientSettings.builder()
			.codecRegistry(pojoCodecRegistry)
			.build();
	MongoClient mongoClient = MongoClients.create(settings); // Connects to mongoDB deamon running on port 27017
	MongoDatabase database = mongoClient.getDatabase("big-bank-db"); // Gets db from deamon, creates it if not found.


	@PostMapping("/SimpleSavings")
	public String SimpleSavings(@RequestBody String SScalc) {
		final JSONObject obj = new JSONObject(SScalc);

		//Get all of the values via the keys
		double deposit = obj.getDouble("deposit");
		double monthly = obj.getDouble("monthly");
		double yearPeriods = obj.getDouble("yearPeriods");
		double interestRate = obj.getDouble("interestRate");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		AddLog( SScalc,  APIKey,  "/SimpleSavings");

		//Get your JSON object of values from the SSCalculator class
		final JSONObject testObject = SimpleSavingsCalculator.SSCalculator(deposit,monthly,yearPeriods,interestRate);

		String returnString = testObject.toString();
		return returnString;
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

		return MortgageCalculator.calculate(homePrice, downPaymentAsPercent, loanLength, interestRate); //TODO: Change return value to double and input data into your function
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

		return CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(CCBalance, CCInterestRate, minimumPaymentPercentage);
	}

	@PostMapping("/CCPayoffCalculator")
	public String CCPayoffCalculator(@RequestBody String CreditPayoff) {
		final JSONObject obj = new JSONObject(CreditPayoff);

		//Get all of the values via the keys
		double ccBalance = obj.getDouble("CCBalance");
		double ccInterest = obj.getDouble("CCInterest");
		int months = obj.getInt("Months");
		int APIKey = obj.getInt("APIKey");
		//Log this request
		AddLog( CreditPayoff,  APIKey,  "/CCPayoffCalculator");

		//Get your JSON object of values from the SSCalculator class
		final JSONObject testObject = CCPayoff.printPayOff(ccBalance,ccInterest,months);

		String returnString = testObject.toString();
		return returnString;

	}

	/*
	Your program should include an open endpoint that accepts JSON containing information about the requesting entity:
	organization name,
	industry,
	point of contact full name,
	POC email

	and return an API key.
	 */
	@PostMapping("/AddKey")
	public int AddKey(@RequestBody String entityInfo) {
		final JSONObject entityInfoJSON = new JSONObject(entityInfo);
		MongoCollection<User> users = database.getCollection("user", User.class);
		int apiKey;

		//Get all of the values via the keys
		String orgName = entityInfoJSON.getString("Organization_Name");
		String industry = entityInfoJSON.getString("Industry");
		String POCname = entityInfoJSON.getString("PointOfContact_name");
		String POCemail = entityInfoJSON.getString("PointOfContact_email");

		//create key
		apiKey = (orgName+industry+POCname+POCemail).hashCode();

		User newUser = new User(
				orgName,
				industry,
				POCname,
				POCemail,
				apiKey
		);

		//check if apikey already exists, if not -> log key and user data into db
		if(users.find(eq("APIKey", apiKey)).first()==null)
			users.insertOne(newUser);
		else
			apiKey = -1;

		return apiKey;
	}

	//Receives key and deletes it from DB.
	@DeleteMapping("/RevokeKey")
	public String RevokeKey(@RequestBody String apiKey) {
		MongoCollection<User> users = database.getCollection("user", User.class);
		final JSONObject keyJSON = new JSONObject(apiKey);

		//find user based on apiKey
		Bson filter = eq("APIKey", keyJSON.getInt("APIKey"));

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

	public void AddLog(String body, int APIKey, String EndPoint) {
		//Returns collection or view object. Will create one if there is not one yet specified
		MongoCollection<Log> logs = database.getCollection("requestHistory", Log.class);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Log insert = new Log(timestamp.toString(), body, EndPoint, APIKey);

		//inserts the new log document into the log collection in the big-bank-db database
		logs.insertOne(insert);
	}
}