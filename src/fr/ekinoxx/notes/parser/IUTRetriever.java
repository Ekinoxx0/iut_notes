package fr.ekinoxx.notes.parser;

import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.rapidoid.log.Log;

import fr.ekinoxx.notes.chrome.ChromeAPI;
import fr.ekinoxx.notes.infos.SemestreInfo;

public class IUTRetriever {
	
	private static IUTRetriever instance = new IUTRetriever();
	
	public static IUTRetriever api() {
		return instance;
	}
	
	//
	
	private HashMap<String, Boolean> gainedState = new HashMap<>();
	private HashMap<String, SemestreInfo> cachedInformation = new HashMap<>();
	
	/*
	 * 
	 */

	public boolean fromWeb(String username, String password) {
		
		IUTRetriever.instance.request(username, password, new IUTCallback() {
			
			@Override
			public void error(Exception e) {
				Log.error(e != null ? e.getMessage() != null ? e.getMessage() : "null" : "Unknown error");
				gainedState.put(username, false);
			}
			
			@Override
			public void call(SemestreInfo ni) {
				gainedState.put(username, true);
			}
		});
		
		while (!gainedState.containsKey(username)) {}
		
		return gainedState.get(username);
	}
	
	private void request(String username, String password, IUTCallback callback) {
		try {
			ChromeDriver driver = ChromeAPI.setup(username, password);
			
	        SemestreInfo ni = SemestreInfo.create(username, driver);
	        cachedInformation.put(username, ni);
	        callback.call(ni);
			driver.close();
		} catch (IllegalAccessException e) {
			callback.error(e);
		} catch (Exception e) {
			callback.error(e);
			e.printStackTrace();
		}
	}

}
