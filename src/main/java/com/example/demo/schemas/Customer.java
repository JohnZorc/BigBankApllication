package com.example.demo.schemas;

import org.bson.types.ObjectId;

public class Customer
{

  private ObjectId id;

  public String firstName;
  public String lastName;
  public String emailAddress;
  public String homeAddress;
  public int password;

  public Customer(String firstName, String lastName, String emailAddress, String homeAddress, int password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.homeAddress = homeAddress;
    this.password = password;
  }

  public Customer() {

  }

  @Override
  public String toString() {
    return String.format(
        "Customer[firstname = %s, lastname = %s, emailAddress = %s, homeaddress = %s, password = %s]",
        this.firstName, this.lastName, this.emailAddress, this.homeAddress, this.password);
  }
}
