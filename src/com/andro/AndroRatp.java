package com.andro;

import com.andro.data.Horaires;
import com.andro.scrapper.AbstractScrapper;
import com.andro.scrapper.BusScrapper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroRatp extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        
        AbstractScrapper scrapper  = new BusScrapper( 24, "24_409", "A" );
        Horaires html = scrapper.getHtml();
        
        tv.setText( html.toString() );
        setContentView(tv);
    }
}