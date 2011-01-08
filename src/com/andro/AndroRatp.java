package com.andro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.andro.scrapper.BusScrapper;
import com.andro.scrapper.MetroScrapper;

public class AndroRatp extends Activity{
	private static final String TEXT_BUNDLE = "key.text"; 
	private Set<Integer> textIds;
	private final Handler handler = new Handler();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.main );
        initSet();
        
        Button button = (Button) findViewById(R.id.button_1);
        button.setOnClickListener( new Updater( this, button,
        		(TextView) findViewById(R.id.text_1), 
        		new BusScrapper( 24, "24_409", "A" ) ) );
        
        button = (Button) findViewById(R.id.button_2);
        button.setOnClickListener( new Updater( this, button,
        		(TextView) findViewById(R.id.text_2),
        		new BusScrapper( 24, "24_383_401", "R" ) ) );
        
        button = (Button) findViewById(R.id.button_3);
        button.setOnClickListener( new Updater( this, button,
        		(TextView) findViewById(R.id.text_3),
        		new BusScrapper( 86, "86_16", "R" ) ) );
        
        button = (Button) findViewById(R.id.button_4);
        button.setOnClickListener( new Updater( this, button,
        		(TextView) findViewById(R.id.text_4),
        		new BusScrapper( 87, "87_20", "A" ) ) );
        
        button = (Button) findViewById(R.id.button_5);
        button.setOnClickListener( new Updater( this, button,
        		(TextView) findViewById(R.id.text_5),
        		new MetroScrapper( 10, "Cardinal+Lemoine", "A" ) ) );
        
        restaureText( savedInstanceState );
    }
    
    private void initSet(){
    	textIds = new HashSet<Integer>();
    	Collections.addAll( textIds, new Integer[]{
    			R.id.text_1, R.id.text_2, R.id.text_3, R.id.text_4, R.id.text_5} );
    }
    
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Bundle textStore = new Bundle();
		outState.putBundle( TEXT_BUNDLE, textStore );
		for( Integer textId : textIds ){
			TextView textView = (TextView)findViewById(textId);
			if( textView != null && textView.getText() != null )
				textStore.putString( textId.toString(), textView.getText().toString() );
		}
	}
    
    private void restaureText(Bundle bundle){
    	if( bundle != null ){
	    	Bundle textStore = bundle.getBundle( TEXT_BUNDLE );
	    	if( textStore != null && !textStore.isEmpty() ){
	    		for( Integer textId : textIds  ){
	    			String text = textStore.getString( textId.toString() );
	    			if( text != null )
	    				((TextView)findViewById(textId)).setText(text);
	    		}
	    	}
    	}
    }
    
    /** Getters */
    
    public Handler getHandler(){
    	return this.handler;
    }
}