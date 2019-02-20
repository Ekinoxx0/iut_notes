package fr.ekinoxx.notes.infos;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.ekinoxx.web.Main;
import lombok.Getter;

@Getter
public class SemestreStudentProfile {
	
	private final String username;
	
	private final String prenom;
	private final String nom;
	private final String apogee;
	private final String semestre;
    
	private final ArrayList<UEInfo> ues = new ArrayList<>();
	
	public SemestreStudentProfile(String username, ChromeDriver driver) {
		this.username = username;
        String[] unparsed_l1 = driver.findElement(By.cssSelector("h1")).getText().split(" ");
        String[] unparsed_l2 = driver.findElement(By.cssSelector("h2")).getText().split(" - ");
        
        prenom = unparsed_l1[0];
        nom = unparsed_l1[1];
        apogee = unparsed_l1[2].replace("(", "").replace(")", "").replace(".", "");
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
		return Main.getGson().toJson(this);
	}

}
