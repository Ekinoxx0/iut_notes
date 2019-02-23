package fr.ekinoxx.web;

import org.rapidoid.log.Log;
import org.rapidoid.setup.App;
import org.rapidoid.setup.My;

import com.google.gson.Gson;

import fr.ekinoxx.web.routes.RoutesDeployment;
import fr.ekinoxx.web.routes.RoutesLimiter;

public class WebApp {

	private static Gson gson = new Gson();
	
	public static void main(String[] args) {
		Log.info("Starting application");
		App.bootstrap(args).jpa().adminCenter().auth();
		My.loginProvider(new IUTLoginProvider());
		
		new RoutesDeployment();
		new RoutesLimiter();
	}

	public static Gson getGson() {
		return gson;
	}

}
