package fr.ekinoxx.web.login;

import org.rapidoid.http.Req;
import org.rapidoid.http.customize.LoginProvider;

import fr.ekinoxx.notes.parser.IUTRetriever;
import fr.ekinoxx.notes.student.Student;

public class SQLLoginProvider implements LoginProvider {
	
	public LoginResult identifyStudent(String username, String password) {
		Student s = Student.getStudent(username);
		
		if(s == null) {
			if(IUTRetriever.api().fromWeb(username, password)) {
				return LoginResult.REGISTRATING;
			} else {
				return LoginResult.REFUSED;
			}
		}
		
		if(!s.isValid()) {
			return LoginResult.INVALID_DATA;
		}
		
		if(s.getPassword().equals(password)) {
			return LoginResult.DIRECT;
		} else {
			return LoginResult.REFUSED;
		}
	}

	@Override
	public boolean login(Req req, final String username, final String password) throws Exception {
		req.session().put("username", username);
		req.session().put("password", password);
		
		switch(identifyStudent(username, password)) {
		
		case DIRECT:
			return true;
			
		case INVALID_DATA:
			return false;
			
		case REFUSED:
			return false;
			
		case REGISTRATING:
			return true;
			
		case RELOADING:
			return true;
			
		}
		
		return false;
	}
	
}
