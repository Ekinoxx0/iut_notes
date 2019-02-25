package fr.ekinoxx.web.pages;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;
import org.rapidoid.log.Log;
import org.rapidoid.security.annotation.LoggedIn;

@Controller
public class LoginPage {
	
	@Page("/login")
	@LoggedIn
	public Object login(final Req req, final HandlerInvocation invocation) throws Exception {
		Log.info("Redirecting logged user from /login to /");
		req.response().redirect("/");
		return "";
	}
	
}
