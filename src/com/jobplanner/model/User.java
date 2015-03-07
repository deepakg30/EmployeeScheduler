package com.jobplanner.model;

public class User {
private int id;
private String uid;
private String first;
private String last;
private String email;
private long phone;
private boolean sms;
private String pass;
private int actype;
private int contract;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getFirst() {
	return first;
}
public void setFirst(String first) {
	this.first = first;
}
public String getLast() {
	return last;
}
public void setLast(String last) {
	this.last = last;
}

public long getPhone() {
	return phone;
}
public void setPhone(long phone) {
	this.phone = phone;
}
public boolean isSms() {
	return sms;
}
public void setSms(boolean sms) {
	this.sms = sms;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public int getActype() {
	return actype;
}
public void setActype(int actype) {
	this.actype = actype;
}
public int getContract() {
	return contract;
}
public void setContract(int contract) {
	this.contract = contract;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

}
