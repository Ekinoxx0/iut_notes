package fr.ekinoxx.web;

import java.util.ArrayList;
import java.util.HashMap;

import org.rapidoid.http.Req;
import org.rapidoid.http.customize.LoginProvider;
import org.rapidoid.security.Auth;

import fr.ekinoxx.notes.NoteRetriever;
import fr.ekinoxx.notes.NoteRetriever.NoteCallback;
import fr.ekinoxx.notes.infos.SemestreStudentProfile;

public class IUTLoginProvider implements LoginProvider {

	private ArrayList<String> logged = new ArrayList<>();
	private HashMap<String, Boolean> gainedState = new HashMap<>();

	@Override
	public boolean login(Req req, String username, String password) throws Exception {
		req.session().put("username", username);
		req.session().put("password", password);
		
		NoteRetriever.noteQueue.request(username, password, new NoteCallback() {
			
			@Override
			public void error() {
				gainedState.put(username, true);
			}
			
			@Override
			public void call(SemestreStudentProfile ni) {
				gainedState.put(username, true);
				logged.add(username);
			}
		});
		
		while (!gainedState.containsKey(username)) {}
		
		return logged.remove(username) || Auth.login(username, password);
	}
	
}
