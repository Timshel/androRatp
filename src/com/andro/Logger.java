package com.andro;

import android.util.Log;

public class Logger {
	public static String TAG = "AndroRatp";
	public static boolean onAndroid = true;
	
	public static void debug( String msg ){
		debug( msg, null );
	}
	
	public static void debug( Throwable error ){
		debug( error.getMessage(), error);
	}
	
	public static void debug( String msg, Throwable error ){
		if( onAndroid ){
			if( error != null )
				Log.d( TAG , msg, error);
			else
				Log.d( TAG , msg);
		}else{
			System.out.println( msg );
			if( error != null )
				error.printStackTrace();
		}
	}
	
	public static void error( String msg ){
		error( msg, null );
	}
	
	public static void error( Throwable error ){
		error( error.getMessage(), error );
	}

	public static void error(String msg, Throwable error){
		if( onAndroid ){
			if( error != null )
				Log.e( TAG, msg, error );
			else
				Log.e( TAG, msg );
		}else{
			System.out.println( msg );
			if( error != null )
				error.printStackTrace();
		}
	}
	
}
