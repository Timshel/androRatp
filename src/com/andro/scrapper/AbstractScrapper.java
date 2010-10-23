package com.andro.scrapper;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilderFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.andro.Logger;
import com.andro.data.Horaires;

public abstract class AbstractScrapper {
	protected static String xPath = 
		"//div[@id='prochains_passages']/fieldset/table/tbody/tr/td";
	
	protected static String urlRoot = 
		"http://www.ratp.info/horaire/fr/ratp/";

	protected StringBuilder sb;

	public AbstractScrapper() {
		sb = new StringBuilder(urlRoot);
	}
	
	protected abstract URL buildUrl();
	
	protected Document getDocument(URL url) {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(url.openStream()));
		}catch( Exception e ){
			Logger.error( e );
		}
		return null;
	}

	public TagNode scrapCleanHtml(URL url){
		try {
			URLConnection conn = url.openConnection();
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(true);
			props.setOmitComments(true);

			return cleaner.clean(
					new InputStreamReader( conn.getInputStream() ) );
		}catch( UnknownHostException uhe ){
			Logger.error("Start the fucking connection : " + uhe);
		} catch (Exception e) {
			Logger.error( e );
			
		}
		return null;
	}
	
	protected Horaires fillData(Object[] nodes){
		Horaires horaires = new Horaires();
		if( nodes.length > 2 && nodes.length % 2 == 0 ){
			for( int index = 0; index < nodes.length; index +=2 )
				horaires.add( ( (TagNode)nodes[index] ).getText().toString(),
						( (TagNode)nodes[index+1] ).getText().toString() );
			Logger.debug( horaires.toString() );
		}
		return horaires;
	}
	
	public Horaires extractData(TagNode node) {
		try {
			Object[] tags = node.evaluateXPath( xPath );
			return fillData( tags );
		}catch( XPatherException e ){
			Logger.error( e);
		}
		return null;
	}

	public Horaires getHtml(){
		Logger.debug("-----------START---------------");
		URL url = buildUrl();
		Logger.debug( url.toString() );
		TagNode node = scrapCleanHtml(url);
		return extractData(node);
	}
}
