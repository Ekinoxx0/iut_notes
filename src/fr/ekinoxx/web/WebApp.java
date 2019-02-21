package fr.ekinoxx.web;

import org.rapidoid.http.MediaType;
import org.rapidoid.log.Log;
import org.rapidoid.security.Role;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;

import com.google.gson.Gson;

import fr.ekinoxx.notes.NoteRetriever;

public class WebApp {

	private static Gson gson = new Gson();
	
	public static void main(String[] args) {
		Log.info("Starting application");
		App.bootstrap(args).jpa().adminCenter().auth();
		My.loginProvider(new IUTLoginProvider());
		On.get("/notes").contentType(MediaType.JSON).roles(Role.LOGGED_IN).serve((req) -> {
			return NoteRetriever.noteQueue.getInfo(req.session().get("username").toString());
		});
	}

	public static Gson getGson() {
		return gson;
	}

}
