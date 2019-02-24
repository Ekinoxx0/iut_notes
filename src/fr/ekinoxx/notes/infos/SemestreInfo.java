package fr.ekinoxx.notes.infos;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.rapidoid.log.Log;

import fr.ekinoxx.web.WebApp;
import lombok.Getter;

@Getter
public class SemestreInfo {
	
	public static SemestreInfo create(String username, ChromeDriver driver) {
		try {
			if(driver == null) {
				Log.error("Null driver for " + username);
				return null;
			}
			driver.findElement(By.cssSelector("h1"));
		} catch(NullPointerException e) {
			Log.error("NullPointerException for " + username);
			return null;
		} catch(NoSuchElementException e) {
			Log.error("NoSuchElementException for " + username);
			return null;
		}
		
		return new SemestreInfo(username, driver);
	}
	
	private final String semestre;
	private final ArrayList<UEInfo> ues = new ArrayList<>();
	
	private SemestreInfo(String username, ChromeDriver driver) {
        String[] unparsed_l2 = driver.findElement(By.cssSelector("h2")).getText().split(" - ");
        
        semestre = unparsed_l2[1];
        
        for(WebElement ue : driver.findElements(By.xpath("/html/body/ul"))) {
        	ues.add(new UEInfo(ue));
        }
	}
	
	//
	
	public boolean isSemestreValided() {
		boolean valided = true;
		
		for(UEInfo ue : ues) {
			if(valided) {
				valided = ue.isValided();
			}
		}
		
		return valided;
	}
	
	public boolean isInWorstCaseSemestreValided() {
		boolean valided = true;
		
		for(UEInfo ue : ues) {
			if(valided) {
				valided = ue.inWorstCaseValided();
			}
		}
		
		return valided;
	}
	
	public boolean isInBetterCaseSemestreValided() {
		boolean valided = true;
		
		for(UEInfo ue : ues) {
			if(valided) {
				valided = ue.inBetterCaseValided();
			}
		}
		
		return valided;
	}
	
	public boolean isInAvgCaseSemestreValided() {
		boolean valided = true;
		
		for(UEInfo ue : ues) {
			if(valided) {
				valided = ue.inAvgCaseValided();
			}
		}
		
		return valided;
	}
	
	/*
	 * 
	 */
	
	public double getCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(UEInfo ue : ues) {
			total += ue.getCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	@Deprecated
	public double getAvgNotes() {
		double total = 0;
		int n = 0;
		
		for(UEInfo ue : ues) {
			total += ue.getAvgNotes();
			n++;
		}
		
		return total / n;
	}
	
	//
	
	public String toJson() {
		return WebApp.getGson().toJson(this);
	}

}
