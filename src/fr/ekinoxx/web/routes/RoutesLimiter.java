package fr.ekinoxx.web.routes;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.rapidoid.http.HttpStatus;
import org.rapidoid.job.Jobs;
import org.rapidoid.setup.On;
import org.rapidoid.u.U;

public class RoutesLimiter {

	private HashMap<String, Integer> access = new HashMap<>();
	
	public RoutesLimiter() {
		On.defaults().wrappers((req, next) -> {
			if(!access.containsKey(req.clientIpAddress())) access.put(req.clientIpAddress(), 0);
			access.put(req.clientIpAddress(), access.get(req.clientIpAddress()) + 1);
			
			if(access.get(req.clientIpAddress()) > 2) {
				req.response().result(U.map("error", "Too many request, please make less request!", "code", 429, "status", "429 Too Many Requests", "requests", access.get(req.clientIpAddress())));
				req.done();
				return HttpStatus.ASYNC;
			}
			
		    return next.invoke();
		});
		
		Jobs.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				RoutesLimiter.this.access.clear();
			}
		}, 0, 5, TimeUnit.SECONDS);
	}
	
}
