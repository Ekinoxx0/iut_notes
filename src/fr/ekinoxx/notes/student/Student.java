package fr.ekinoxx.notes.student;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.rapidoid.datamodel.Results;
import org.rapidoid.jdbc.JDBC;
import org.rapidoid.u.U;

import fr.ekinoxx.notes.annotation.SQL;
import fr.ekinoxx.notes.infos.SemestreInfo;
import lombok.Getter;

@Getter
public class Student {
	
	private static HashMap<String, Student> students = new HashMap<>();
	
	public static Student getStudent(String username) {
		if(students.containsKey(username)) {
			return students.get(username);
		}
		
		Student s = new Student(username);
		return s.reload() ? s : null;
	}
	
	//
	
	private @SQL int id = Integer.MIN_VALUE;
	private @SQL String username;
	private @SQL String password;
	private @SQL String nom;
	private @SQL String prenom;
	private @SQL int apogee;
	private ArrayList<SemestreInfo> semestres = new ArrayList<>();
	
	private Student(String username) {
		this.username = username;
		this.reload();
	}
	
	public void parseDriver(ChromeDriver driver) throws NumberFormatException {
        String[] unparsed_l1 = driver.findElement(By.cssSelector("h1")).getText().split(" ");
        
        prenom = unparsed_l1[0];
        nom = unparsed_l1[1];
        apogee = Integer.parseInt(unparsed_l1[2].replace("(", "").replace(")", "").replace(".", ""));
	}
	
	/*
	 * SQL Relative
	 */
	
	public boolean insert() throws IllegalArgumentException {
		U.must(this.isValid(), "Already has ID");
		U.notNull(username, "Username not specified");
		U.notNull(password, "Password not specified");
		
		int rowChanged = JDBC.api().execute(
				"INSERT INTO `users`(`id`, `username`, `password`, `nom`, `prenom`, `apogee`) VALUES (?, ?, ?, ?, ?, ?)",
			(Object[]) U.array(this.id, this.username, this.password, this.nom, this.prenom, this.apogee));
		
		return rowChanged == 1;
	}
	
	public boolean reload() {
		try {
			Results<Map<String, Object>> query = JDBC.api().query("SELECT * FROM users WHERE username = ?", username);
			
			if(query.isSingle()) {
				Map<String, Object> queriedObject = query.first();
				
				for(Field f : this.getClass().getDeclaredFields()) {
					if(!f.isAnnotationPresent(SQL.class)) {
						continue;
					}
					
					if(!queriedObject.containsKey(f.getName())) {
						throw new InternalError("Unable to find field " + f.getName() + " in sql query for " + getClass().getName());
					}
					
					f.set(this, queriedObject.get(f.getName()));
				}
				
				if(this.isValid()) Student.students.put(this.username, this);
				
				return this.isValid();
			} else {
				if(query.count() == 0) {
					return false;
				} else {
					throw new IllegalStateException("Multiple line queried for user " + this.username);
				}
			}
		} catch(Error | Exception e) {
			id = -1;
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isValid() {
		return id > 0;
	}
	
}
