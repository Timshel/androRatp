package com.andro.scrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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

	protected String getHtmlAsString(URL url) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
		} catch (Exception e){
			Logger.error( e );
		}
		return sb.toString();
	}

	public TagNode scrapCleanHtml(URL url) {
		try {
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
			props.setAllowHtmlInsideAttributes(true);
			props.setAllowMultiWordAttributes(true);
			props.setRecognizeUnicodeChars(true);
			props.setOmitComments(true);

			return cleaner.clean(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			Logger.error( e );
		}
		return null;
	}
	
	protected abstract Horaires fillData( Object[] nodes );
	
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
		URL url = buildUrl();
		Logger.debug( url.toString() );
		TagNode node = scrapCleanHtml(url);
		return extractData(node);
	}
}
