package fr.ekinoxx.web;

import org.rapidoid.http.MediaType;
import org.rapidoid.log.Log;
import org.rapidoid.security.Role;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;

import com.google.gson.Gson;

import fr.ekinoxx.notes.NoteRetriever;

public class Main {

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

	//On.get("/").roles(Role.LOGGED_IN).html((Req req) -> req.response().redirect("/menu"));

	/*
	 * On.get("/").mvc(new ReqHandler() { private static final long serialVersionUID
	 * = -8695419313024250331L;
	 * 
	 * @Override public Object execute(Req req) throws Exception { return
	 * req.isAsync(); } });
	 * Admin.get("/adminpage").mvc("Hello admin !");
	 */

}
