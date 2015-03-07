package com.jobplanner.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

	private String date;
	private List<String> e;
	private List<String> l;
	private List<String> d;
	private List<String> n;
	private List<String> dh;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<String> getE() {
		return e;
	}

	public void setE(List<String> e) {
		this.e = e;
	}

	public List<String> getL() {
		return l;
	}

	public void setL(List<String> l) {
		this.l = l;
	}

	public List<String> getD() {
		return d;
	}

	public void setD(List<String> d) {
		this.d = d;
	}

	public List<String> getN() {
		return n;
	}

	public void setN(List<String> n) {
		this.n = n;
	}

	public List<String> getDh() {
		return dh;
	}

	public void setDh(List<String> dh) {
		this.dh = dh;
	}

	public Schedule() {
		this.e = new ArrayList<String>();
		this.d = new ArrayList<String>();
		this.dh= new ArrayList<String>();
		this.l= new ArrayList<String>();
		this.n= new ArrayList<String>();;
		
	}
	
	
}
