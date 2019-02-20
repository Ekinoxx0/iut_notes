package fr.ekinoxx.notes;

import java.util.HashMap;
import java.util.Map.Entry;

public class Coef {
	
	private static final HashMap<String, Double> mat = new HashMap<>();
	private static final HashMap<String, Double> exams = new HashMap<>();
	
	static {
		mat.put("1101", 3.5);
		mat.put("1102", 3.5);
		mat.put("1103", 2.5);
		mat.put("1104", 3.5);
		mat.put("1105", 2.5);
		mat.put("1106", 1.5);
		
		mat.put("1201", 2.5);
		mat.put("1202", 2.0);
		mat.put("1203", 1.5);		
		mat.put("1204", 2.5);
		mat.put("1205", 2.0);
		mat.put("1206", 1.5);
		mat.put("1207", 1.0);
		
		mat.put("2101", 1.5);
		mat.put("2102", 1.5);
		mat.put("2103", 3.5);
		mat.put("2104", 2.5);
		mat.put("2105", 2.5);
		mat.put("2106", 2.5);
		mat.put("2107", 2.0);
		
		mat.put("2201", 2.5);
		mat.put("2202", 2.0);
		mat.put("2203", 3.0);
		mat.put("2204", 1.5);
		mat.put("2205", 1.5);
		mat.put("2206", 2.5);
		mat.put("2207", 1.0);
		
		mat.put("3101", 2.5);
		mat.put("3102", 1.5);
		mat.put("3103", 1.5);
		mat.put("3104", 2.5);
		mat.put("3105", 2.5);
		mat.put("3106C", 1.5);
		
		mat.put("3201", 2.5);
		mat.put("3202C", 1.5);
		mat.put("3203", 1.5);
		mat.put("3204", 2.5);
		mat.put("3205", 1.5);
		mat.put("3206", 2.5);
		
		mat.put("3301", 3.0);
		mat.put("3302", 2.0);
		mat.put("3303", 1.0);
		
		mat.put("4101C", 1.5);
		mat.put("4102C", 1.5);
		mat.put("4103C", 1.5);
		mat.put("4104C", 1.5);
		mat.put("4105C", 1.5);
		mat.put("4106", 2.5);
		
		mat.put("4201C", 2.0);
		mat.put("4202C", 2.0);
		mat.put("4203", 2.0);
		mat.put("4204", 2.0);
		
		mat.put("4301", 12.0);
	}
	
	public static double getMatCoefFromCode(String code) {
		for(Entry<String, Double> entry : mat.entrySet()) {
			if(code.toUpperCase().contains(entry.getKey())) {
				return entry.getValue();
			}
		}
		
		return 0;
	}

	public static double getExamCoefFromCode(String code) {
		for(Entry<String, Double> entry : exams.entrySet()) {
			if(code.toUpperCase().contains(entry.getKey())) {
				return entry.getValue();
			}
		}
		
		return 0;
	}
	
}
