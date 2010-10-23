package com.andro.scrapper;

import java.net.MalformedURLException;
import java.net.URL;

import com.andro.Logger;

public class BusScrapper extends AbstractScrapper{
	protected final static String urlBusRoot = "bus/prochains_passages/PP";
	protected int numeroBus;
	protected String station;
	protected String direction;
	
	public BusScrapper(){
		super();
		sb.append( urlBusRoot );
	}
	
	public BusScrapper(int numeroBus, String station, String direction){
		this();
		reset(numeroBus, station, direction);
	}
	
	public void reset(int numeroBus, String station, String direction){
		this.numeroBus = numeroBus;
		this.station = station;
		this.direction = direction;
	}
	
	protected URL buildUrl(){
		try {
			sb.setLength(urlRoot.length() + urlBusRoot.length() );
			sb.append("/B").append(numeroBus).append("/")
				.append(station).append("/").append(direction);
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			Logger.error( e );
		}
		return null;
	}

}
