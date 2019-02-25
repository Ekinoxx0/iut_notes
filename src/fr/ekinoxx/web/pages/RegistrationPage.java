package fr.ekinoxx.web.pages;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;
import org.rapidoid.security.annotation.LoggedIn;

@Controller
public class RegistrationPage {
	
	@Page("/registration")
	@LoggedIn
	public Object registration(final Req req, final HandlerInvocation invocation) throws Exception {
		return "";
	}
	
}
