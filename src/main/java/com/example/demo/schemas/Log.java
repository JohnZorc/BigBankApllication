package com.example.demo.schemas;

import org.bson.types.ObjectId;

public class Log {

    private ObjectId id;

    // Properties
    public String timestamp;
    public String requestOrResponse;
    public String endpoint;
    public int APIKeyUsed;



    // Constructor (you can have multiple)
    public Log(String timestamp, String requestOrResponse, String endpoint, int APIKeyUsed){
        this.timestamp = timestamp;
        this.requestOrResponse = requestOrResponse;
        this.endpoint = endpoint;
        this.APIKeyUsed = APIKeyUsed;
    }

    public Log(){}



    @Override
    public String toString() {
        return String.format(
                "Log[id=%s, timestamp='%s', requestOrResponse='%s', endpoint='%s', APIKeyUsed='%s']",
                id, timestamp, requestOrResponse, endpoint, APIKeyUsed);
    }

}
