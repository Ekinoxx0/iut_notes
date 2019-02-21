package fr.ekinoxx.notes;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

import org.openqa.selenium.chrome.ChromeDriver;

import fr.ekinoxx.notes.infos.SemestreStudentProfile;

public class NoteRetriever {
	
	public static abstract class NoteCallback implements Callback {
		public abstract void call(SemestreStudentProfile ni);
		public abstract void error(Exception e);
	}
	
	public static NoteRetriever noteQueue = new NoteRetriever();
	
	//
	
	private HashMap<String, String> logins = new HashMap<>();
	private HashMap<String, SemestreStudentProfile> cachedInformation = new HashMap<>();
	
	/*
	 * 
	 */
	
	public void addToLogins(String username, String password) {
		logins.put(username, password);
	}
	
	public void request(String username, String password, NoteCallback nc) throws IllegalAccessException {
		if(logins.containsKey(username) && logins.containsValue(password)) {
			if(cachedInformation.containsKey(username)) {
				nc.call(cachedInformation.get(username));
			} else {
				nc.error(new IllegalAccessException("Valided logins but no cached informations..."));
			}
			return;
		}
		
		noteQueue.addToLogins(username, password);
		
		try {
			ChromeDriver driver = ChromeAPI.setup(username, password);
			
	        SemestreStudentProfile ni = new SemestreStudentProfile(username, driver);
	        cachedInformation.put(username, ni);
			
	        nc.call(ni);
			driver.close();
		} catch (IllegalAccessException e) {
			nc.error(e);
		} catch (Exception e) {
			nc.error(e);
			e.printStackTrace();
		}
	}

	public SemestreStudentProfile getInfo(String username) {
		if(logins.containsKey(username)) {
			if(cachedInformation.containsKey(username)) {
				return cachedInformation.get(username);
			}
		}
		
		return null;
	}
	
	
}
