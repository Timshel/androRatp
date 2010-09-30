package com.andro.data;

import java.util.ArrayList;
import java.util.List;

public class Destination {
	private String destination;
	private List<String> times;
	
	public Destination(String destination, String time ){
		this.times = new ArrayList<String>();
		this.destination = destination;
		this.times.add( time );
	}
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void addTime( String time ){
		this.times.add( time );
	}
	
	public List<String> getTimes(){
		return times;
	}
}
