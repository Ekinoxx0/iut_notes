package fr.ekinoxx.notes.infos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import fr.ekinoxx.notes.Coef;
import lombok.Getter;

@Getter
public class ExamInfo {

	private final String code;
	private final String titre;
	private final double note;
	
	public ExamInfo(WebElement exam) {
    	String[] unparsedUe = exam.findElement(By.xpath("./td")).getText().split(" - ");
    	code = unparsedUe[0];
    	titre = unparsedUe[1];
    	
    	String noteString = exam.findElement(By.xpath("./td/em")).getText();
    	if(noteString.equals("n/a (non disponible)")) {
    		note = -1;
    	} else {
    		note = Double.parseDouble(noteString);
    	}
	}

	public boolean hasNote() {
		return note >= 0;
	}

	public double getCoefNote() {
		return this.getNote() * Coef.getExamCoefFromCode(this.getCode());
	}
	
	/*
	 * 
	 */
	
	public double getInWorstCaseCoefNotes() {
		if(this.hasNote()) {
			return this.getCoefNote();
		} else {
			return 0 * Coef.getExamCoefFromCode(this.getCode());
		}
	}
	
	public double getInAvgCaseCoefNotes() {
		if(this.hasNote()) {
			return this.getCoefNote();
		} else {
			return 10 * Coef.getExamCoefFromCode(this.getCode());
		}
	}
	
	public double getInBetterCaseCoefNotes() {
		if(this.hasNote()) {
			return this.getCoefNote();
		} else {
			return 20 * Coef.getExamCoefFromCode(this.getCode());
		}
	}
	
}
