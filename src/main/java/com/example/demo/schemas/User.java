package com.example.demo.schemas;

import org.bson.types.ObjectId;

public class User {

    private ObjectId id;

    // Properties
    public String organizationName;
    public String industry;
    public String pocFullName;
    public String pocEmail;
    public String APIKey;


    // Constructor (you can have multiple)
    public User(String organizationName, String industry, String pocFullName, String pocEmail, String APIKey){
        this.organizationName = organizationName;
        this.industry = industry;
        this.pocFullName = pocFullName;
        this.pocEmail = pocEmail;
        this.APIKey = APIKey;
    }




    @Override
    public String toString() {
        return String.format(
                "User[id=%s, organizationName='%s', industry='%s', pocFullName='%s', pocEmail='%s', APIKey='%s']",
                id, organizationName, industry, pocFullName, pocEmail, APIKey);
    }
}
