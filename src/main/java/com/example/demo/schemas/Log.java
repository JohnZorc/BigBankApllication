package com.example.demo.schemas;

import org.bson.types.ObjectId;

public class Log
{

    private ObjectId id;

    // //Record a human readable timestamp, the type of transaction (create, transfer, deposit),
    // // the customer ID (not email), account numbers impacted, and any dollar amount recorded for the transaction.
    // // Properties
     public String timestamp;
     public String transactionType;
     public String customerID;
     public String account1;
     public String account2;
     public double dollarAmount;




    public Log(String timestamp, String transactionType, String customerID, String account1, String account2, double dollarAmount) {
         this.timestamp = timestamp;
         this.transactionType = transactionType;
         this.customerID = customerID;
         this.account1 = account1;
         this.account2 = account2;
         this.dollarAmount = dollarAmount;
    }


    public Log(){}


    @Override
    public String toString() {
        return this.timestamp;
        // return "TimeStamp: " + this.timestamp + ", transactionType: " + this.transactionType + ", customerID: " + this.customerID + ", account1: " + this.account1
        //     + ", account2: " + this.account2 + ", dollarAmount: " + this.dollarAmount;
//        return this.log;
    }

}
