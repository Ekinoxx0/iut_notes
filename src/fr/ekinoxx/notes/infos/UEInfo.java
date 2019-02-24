package fr.ekinoxx.notes.infos;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;

@Getter
public class UEInfo {
	
	private final String code;
	private final String ue;
	private final int ue_n;
	private final String titre;
	
	private final ArrayList<MatInfo> mats = new ArrayList<>();
	
	protected UEInfo(WebElement we_ue) {
    	String[] unparsedUe = we_ue.findElement(By.xpath("./b")).getText().split(" - ");
    	code = unparsedUe[0];
    	ue = unparsedUe[1];
    	ue_n = Integer.parseInt(ue.replace("UE", ""));
    	titre = unparsedUe[2];
    	
    	for(WebElement mat : we_ue.findElements(By.xpath("./ul"))) {
    		if(mat.getText().length() <= 3) {
    			continue;
    		}
    		
    		mats.add(new MatInfo(mat));
    	}
	}
	
	public boolean isValided() {
		return this.getCoefNotes() >= 8;
	}
	
	public double getCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(MatInfo mat : mats) {
			total += mat.getCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	@Deprecated
	public double getAvgNotes() {
		double total = 0;
		int n = 0;
		
		for(MatInfo mat : mats) {
			total += mat.getAvgNotes();
			n++;
		}
		
		return total / n;
	}
	
	/*
	 *  Others
	 */
	
	public boolean inWorstCaseValided() {
		return this.getInWorstCaseCoefNotes() >= 8;
	}
	
	public boolean inAvgCaseValided() {
		return this.getInAvgCaseCoefNotes() >= 8;
	}
	
	public boolean inBetterCaseValided() {
		return this.getInBetterCaseCoefNotes() >= 8;
	}
	
	public double getInWorstCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(MatInfo mat : mats) {
			total += mat.getInWorstCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	public double getInAvgCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(MatInfo mat : mats) {
			total += mat.getInAvgCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	public double getInBetterCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(MatInfo mat : mats) {
			total += mat.getInBetterCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
}
