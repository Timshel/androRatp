package com.andro;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.andro.data.Horaires;
import com.andro.scrapper.AbstractScrapper;

public class Updater extends Thread implements OnClickListener, Cloneable{
	private TextView text;
	private Button button;
	private AbstractScrapper scrapper;
	
	public Updater(Button button, TextView text, AbstractScrapper scrapper){
		this.button = button;
		this.text = text;
		this.scrapper = scrapper;
	}
	
	public void onClick(View view) {
		this.clone().run();
	}
	
	public Updater clone(){
		try {
			return (Updater) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void run(){
		button.setPressed( true );
		Horaires html = scrapper.getHtml();    
	    text.setText( html.toString() );
	    button.setPressed( false );
	}
}
