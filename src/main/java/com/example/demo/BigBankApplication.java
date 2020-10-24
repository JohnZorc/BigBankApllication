package com.example.demo;

import com.example.demo.schemas.Log;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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

		return MortgageCalculator.calculate(homePrice, downPaymentAsPercent, loanLength, interestRate); //TODO: Change return value to double and input data into your function
	}

	@PostMapping("/CCMinCalculator")
	public String CreditCardMinCalculator(@RequestBody String CreditMin) throws Exception {

		final JSONObject obj = new JSONObject(CreditMin);

		double CCBalance = obj.getDouble("CCBalance");
		double CCInterestRate = obj.getDouble("CCInterestRate");
		double minimumPaymentPercentage = obj.getDouble("minimumPaymentPercentage");

		return CreditCardMinimumPaymentCalculator.CreditCardMinimumPaymentCalculator(CCBalance, CCInterestRate, minimumPaymentPercentage);
	}

	@PostMapping("/CCPayoffCalculator")
	public String CCPayoffCalculator(@RequestBody String CreditPayoff) {
		final JSONObject obj = new JSONObject(CreditPayoff);

		//Get all of the values via the keys
		double ccBalance = obj.getDouble("CCBalance");
		double ccInterest = obj.getDouble("CCInterest");
		int months = obj.getInt("Months");

		//Get your JSON object of values from the SSCalculator class
		final JSONObject testObject = CCPayoff.printPayOff(ccBalance,ccInterest,months);

		String returnString = testObject.toString();
		return returnString;

	}

	@PostMapping("/AddLog")
	public String AddLog() {
		/*
		* Team here are the instructions on how to use the DB
		* 	1. Get your collection
		* 	2. Perform CRUD operations on it (look at link for example).
		* */

		// Here is how to create a `Log` object and save it on the db.
		// Step 1:
		MongoCollection<Log> logs = database.getCollection("log", Log.class);

		// Step 2:
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Log example = new Log(timestamp.toString(), "request","/DBExample", "sampleAPIkey");
		logs.insertOne(example);

		return example.toString();
	}

	@GetMapping("/GetAllLogs")
	public List<String> GetAllLogs() {
		MongoCollection<Document> logs = database.getCollection("log");
		List<String> response = new ArrayList<String>();

		Consumer<Document> addLogToList = new Consumer<Document>() {
			@Override
			public void accept(final Document document) {
				response.add(document.toJson());
			}
		};


		logs.find().forEach(addLogToList);



		return response;
	}
}