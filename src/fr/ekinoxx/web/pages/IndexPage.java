package fr.ekinoxx.web.pages;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;
import org.rapidoid.log.Log;

import fr.ekinoxx.web.login.LoginResult;

@Controller
public class IndexPage {
	
	@Page("/")
	public Object index(final Req req, final HandlerInvocation invocation) throws Exception {
		if(!req.session().containsKey(LoginResult.LRESULT_KEY)) {
			Log.info("Redirecting unknown session (no key) user from / to /login");
			req.response().redirect("/login");
		} else {
			LoginResult lr = LoginResult.valueOf(req.session().get(LoginResult.LRESULT_KEY).toString());
			
			if(lr == null) {
				Log.info("Redirecting unknown session (null key) user from / to /login");
				req.session().clear();
				req.token().clear();
				req.response().redirect("/login");
				return "";
			}
			
			switch(lr) {
			
			case INVALID_DATA:
			case REFUSED:
				req.session().clear();
				req.token().clear();
				req.response().redirect("/login");
				Log.info("Redirecting unknown session (" + lr.toString() + ") user from / to /login");
				return "";
				
			case DIRECT:
				req.response().redirect("/profil");
				break;
				
			case REGISTRATING:
				req.response().redirect("/registration");
				break;
				
			case RELOADING:
				req.response().redirect("/reloading");
				break;
				
			}
			
		}
		
		return "";
	}
	
}
