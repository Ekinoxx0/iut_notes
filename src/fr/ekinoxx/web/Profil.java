package fr.ekinoxx.web;

import java.util.HashMap;
import java.util.Map;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;
import org.rapidoid.security.annotation.LoggedIn;

import fr.ekinoxx.notes.NoteRetriever;
import fr.ekinoxx.notes.infos.MatInfo;
import fr.ekinoxx.notes.infos.SemestreStudentProfile;
import fr.ekinoxx.notes.infos.UEInfo;

@Controller
public class Profil {

	@SuppressWarnings("deprecation")
	@LoggedIn
	@Page("/")
	public Object index(final Req req, final HandlerInvocation invocation) throws Exception {
		SemestreStudentProfile ssp = NoteRetriever.noteQueue.getInfo(req.session().get("username").toString());

		HashMap<String, Map<?, ?>> m = new HashMap<>();
		
		for(UEInfo ue : ssp.getUes()) {
			HashMap<String, Double> ueM = new HashMap<>();
			for(MatInfo mi : ue.getMats()) {
				ueM.put(mi.getTitre(), mi.getAvgNotes());
			}
			
			m.put(ue.getTitre(), ueM);
		}
		
		return GUI.page(GUI.grids(m)).brand("Notes IUT Informatique");
	}
	
}