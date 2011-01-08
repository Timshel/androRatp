package com.andro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.andro.data.Horaires;
import com.andro.scrapper.AbstractScrapper;

public class Updater implements OnClickListener, Cloneable{
	private final AndroRatp activity;
	private final TextView text;
	private final Button button;
	private final AbstractScrapper scrapper;
	
	public Updater(AndroRatp activity, Button button, TextView text, AbstractScrapper scrapper){
		this.activity = activity;
		this.button = button;
		this.text = text;
		this.scrapper = scrapper;
	}
	
	public void onClick(View view){
		if( isAvailable() ){
			button.setPressed( true );
			Thread thread = new Thread(){
				public void run(){
					final Horaires html = scrapper.getHtml();
					activity.getHandler().post(
							new Runnable() {					
								public void run() {
									text.setText( html.toString() );
								    button.setPressed( false );
								}
							} );
				}
			};
			thread.start();
		}
	}
	
	public void run(){
		button.setPressed( true );
		Horaires html = scrapper.getHtml();    
	    text.setText( html.toString() );
	    button.setPressed( false );
	}
	
	public boolean isAvailable(){
		Context context = this.activity.getApplicationContext();
		ConnectivityManager manager = (ConnectivityManager) 
				context.getSystemService( Context.CONNECTIVITY_SERVICE );
		Logger.debug( "Check Connected : " );
		try{
			for( NetworkInfo network : manager.getAllNetworkInfo() ){
				Logger.debug( "network : " + network.getTypeName() );
				Logger.debug( "Connected : " + network.isConnected() );
				if( network.isConnected() )
					return true;
			}
		}catch(Exception e ){
			Logger.debug( e.getMessage() );
		}
		return false;
	}
}
