package fr.ekinoxx.notes;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.ekinoxx.notes.infos.SemestreStudentProfile;

public class NoteRetriever {
	
	public static abstract class NoteCallback implements Callback {
		public abstract void call(SemestreStudentProfile ni);
		public abstract void error();
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
	
	public void request(String username, String password, NoteCallback nc) {
		if(logins.containsKey(username) && logins.containsValue(password)) {
			if(cachedInformation.containsKey(username)) {
				nc.call(cachedInformation.get(username));
			} else {
				nc.error();
			}
			return;
		}
		
		noteQueue.addToLogins(username, password);
		
		try {
			ChromeDriver driver = ChromeAPI.setup(username, password);
			
			try {
		        if(!driver.findElement(By.cssSelector("a")).getText().equals("Se déconnecter")) {
		        	nc.error();
		        	return;
		        }
			} catch (Exception e) {
				nc.error();
				return;
			}
			
	        SemestreStudentProfile ni = new SemestreStudentProfile(username, driver);
	        cachedInformation.put(username, ni);
			
	        nc.call(ni);
			driver.close();
		}catch (Error e) {
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
