package com.example.mikel.dbtest.EntityTable;

/**
 * Class for SQL DB compensate the work of Personal Graph
 */


public class Person {
    private long pid;
    private long contactId; // The contact id
    private String personName;
    private String phoneNumber;
    private String emailAddress;

    // Empty Constructor
    public Person(){}
    public Person(long pid, long contactId, String personName, String phoneNumber, String emailAddress){
        this.pid = pid;
        this.contactId = contactId;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Getters
    public long getPid(){
        return pid;
    }
    public long getContactId(){
        return contactId;
    }
    public String getPersonName(){
        return personName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmailAddress(){
        return emailAddress;
    }
    // Update
    public void setPid(long pid){
        this.pid = pid;
    }
    public void setContactId(long contactId){
        this.contactId = contactId;
    }
    public void setPersonName(String personName){
        this.personName = personName;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

}
