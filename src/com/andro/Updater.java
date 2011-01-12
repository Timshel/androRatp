package com.andro;

import android.app.AlertDialog;
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
	private final Worker worker;
	
	private class Worker implements Runnable{
		public void run(){
			Horaires horaires = scrapper.getHtml();
			activity.getHandler().post( new UIRefresher(horaires) );
		}
	}
	
	private class UIRefresher implements Runnable{
		private Horaires horaires;
		
		public UIRefresher( Horaires horaires ){
			this.horaires = horaires;
		}
		
		public void run() {
			text.setText( horaires.toString() );
		    button.setEnabled( true );
		}
	}
	
	public Updater(AndroRatp activity, Button button, TextView text, AbstractScrapper scrapper){
		this.activity = activity;
		this.button = button;
		this.text = text;
		this.scrapper = scrapper;
		this.worker = new Worker();
	}
	
	public void onClick(View view){
		if( button.isEnabled() ){
			if( isAvailable() ){
				button.setEnabled( false );
				new Thread( this.worker ).start();
			}else{
				new AlertDialog.Builder( this.activity )
					.setMessage("Pas de reseau. Try again :D.").show();
			}
		}
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
