package fr.ekinoxx.web.routes;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import org.rapidoid.jdbc.JDBC;

@Controller
public class RoutesDeployment {
	
	private static final String R = "/r/";
	
	/*
	 * ROUTES
	 */

    @GET(R + "users")
    public Object users(Req req, Resp resp, String username) {
		return JDBC.api().query("SELECT * FROM users").count();
    }

    @GET(R + "user/{username}")
    public Object user(Req req, Resp resp, String username) {
		return JDBC.api().query("SELECT username FROM users WHERE username = ?", username).count() > 0 ? true : false;
    }
    
}
