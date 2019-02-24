package fr.ekinoxx.notes.infos;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import fr.ekinoxx.notes.Coef;
import lombok.Getter;

@Getter
public class MatInfo {

	private final String code;
	private final String titre;
	private final ArrayList<ExamInfo> exams = new ArrayList<>();
	
	protected MatInfo(WebElement we_mat) {
    	String[] unparsedUe = we_mat.findElement(By.xpath("./b")).getText().split(" - ");
    	code = unparsedUe[0];
    	titre = unparsedUe[1];
    	
    	for(WebElement exam : we_mat.findElements(By.xpath("./ul/table/tbody/tr"))) {
    		exams.add(new ExamInfo(exam));
    	}
	}
	
	public double getCoef() {
		return Coef.getMatCoefFromCode(getCode());
	}
	
	public boolean isValidCoef() {
		return getCoef() > 0;
	}
	
	public double getCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(ExamInfo exam : exams) {
			total += exam.getCoefNote();
			n++;
		}
		
		return total / n;
	}
	
	@Deprecated
	public double getAvgNotes() {
		double total = 0;
		int n = 0;
		
		for(ExamInfo exam : exams) {
			total += exam.getNote();
			n++;
		}
		
		return total / n;
	}

	@Deprecated
	public double getAvgNoteCoefed() {
		return getCoef() * this.getAvgNotes();
	}
	
	/*
	 * 
	 */
	
	public double getInWorstCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(ExamInfo exam : exams) {
			total += exam.getInWorstCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	public double getInAvgCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(ExamInfo exam : exams) {
			total += exam.getInAvgCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
	public double getInBetterCaseCoefNotes() {
		double total = 0;
		int n = 0;
		
		for(ExamInfo exam : exams) {
			total += exam.getInBetterCaseCoefNotes();
			n++;
		}
		
		return total / n;
	}
	
}
