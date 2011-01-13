package com.andro;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andro.data.StoredDestination;
import com.andro.scrapper.BusScrapper;
import com.andro.scrapper.MetroScrapper;

public class AndroRatp extends Activity{
	private static final String TEXT_BUNDLE = "key.text"; 
	private final Handler handler = new Handler();
	
	public StoredDestination[] loadConfig(){
		return new StoredDestination[]{ 
				new StoredDestination(1, R.drawable.bus24, new BusScrapper( 24, "24_409", "A") ),
				new StoredDestination(2, R.drawable.bus24, new BusScrapper( 24, "24_383_401", "R") ),
				new StoredDestination(3, R.drawable.bus86, new BusScrapper( 86, "86_16", "R") ),
				new StoredDestination(4, R.drawable.bus87, new BusScrapper( 87, "87_20", "A") ),
				new StoredDestination(5, R.drawable.metro10, new MetroScrapper( 10, "Cardinal+Lemoine", "A") )
		}; 
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle textStore = ( savedInstanceState != null ? savedInstanceState.getBundle( TEXT_BUNDLE ) : null );
        setContentView( R.layout.main );

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for( StoredDestination stored : loadConfig() ){
        	RelativeLayout lineLayout = (RelativeLayout) inflater.inflate(R.layout.line, null);
        	ImageView imageView =  (ImageView) lineLayout.getChildAt( 0 );
        	TextView textView = (TextView) lineLayout.getChildAt( 2 );
        	Button button = (Button) lineLayout.getChildAt( 1 );
        	
        	imageView.setBackgroundResource( stored.image );
        	textView.setId( stored.id );
        	textView.setText( textStore != null ? textStore.getString( String.valueOf( stored.id ) ) : null );
        	button.setOnClickListener( new Updater( this, button, textView, stored.scrapper ) );
        	
        	linearLayout.addView( lineLayout );
        }
    }
    
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Bundle textStore = new Bundle();
		outState.putBundle( TEXT_BUNDLE, textStore );
		for( StoredDestination stored : loadConfig() ){
			TextView textView = (TextView) findViewById( stored.id );
			if( textView != null && textView.getText() != null )
				textStore.putString( String.valueOf( stored.id ), textView.getText().toString() );
		}
	}
    
    /** Getters */
	
    public Handler getHandler(){
    	return this.handler;
    }
}