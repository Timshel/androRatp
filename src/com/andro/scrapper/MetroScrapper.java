package com.andro.scrapper;

import java.net.MalformedURLException;
import java.net.URL;

import com.andro.Logger;

public class MetroScrapper extends AbstractScrapper{
	private static final String urlMetroRoot = "metro/prochains_passages/PP"; 
	
	private int numeroMetro;
	private String station;
	private String direction;
	
	public MetroScrapper(){
		super();
		sb.append( urlMetroRoot );
	}
	
	public MetroScrapper(int numeroMetro, String station, String direction){
		this();
		reset(numeroMetro, station, direction);
	}
	
	public void reset(int numeroMetro, String station, String direction){
		this.numeroMetro = numeroMetro;
		this.station = station;
		this.direction = direction;
	}
	
	protected URL buildUrl(){
		try {
			sb.setLength(urlRoot.length() + urlMetroRoot.length() );
			sb.append("/").append(station).append("/")
				.append(numeroMetro).append("/").append(direction);
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			Logger.error( e );
		}
		return null;
	}
}
