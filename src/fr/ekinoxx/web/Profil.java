package fr.ekinoxx.web;

import java.util.HashMap;
import java.util.Map;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;
import org.rapidoid.security.annotation.LoggedIn;

@Controller
public class Profil {

	@LoggedIn
	@Page("/")
	public Object index(final Req req, final HandlerInvocation invocation) throws Exception {
		// ssp = IUTRetriever.instance.getInfo(req.session().get("username").toString());

		HashMap<String, Map<?, ?>> m = new HashMap<>();
		
		/*for(UEInfo ue : ssp.getUes()) {
			HashMap<String, Double> ueM = new HashMap<>();
			for(MatInfo mi : ue.getMats()) {
				ueM.put(mi.getTitre(), mi.getAvgNotes());
			}
			
			m.put(ue.getTitre(), ueM);
		}*/
		
		return GUI.page(GUI.grids(m)).brand("Notes IUT Informatique");
	}
	
}