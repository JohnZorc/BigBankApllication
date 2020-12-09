package com.example.demo.schemas;

import org.bson.types.ObjectId;

public class Customer
{

  public ObjectId id;

  public String firstName;
  public String lastName;
  public String emailAddress;
  public String homeAddress;
  public int  password;
  public int APIKey;

  public Customer(String firstName, String lastName, String emailAddress, String homeAddress, int password, int APIKey) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.homeAddress = homeAddress;
    this.password = password;
    this.APIKey = APIKey;
  }

  public Customer() {

  }
  public String getID() {
    return id.toString();
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[firstname = %s, lastname = %s, emailAddress = %s, homeaddress = %s, password = %s, APIKey = %s]",
        this.firstName, this.lastName, this.emailAddress, this.homeAddress, this.password, this.APIKey);
  }

  public int getAPIKey() {
    return APIKey;
  }
}
