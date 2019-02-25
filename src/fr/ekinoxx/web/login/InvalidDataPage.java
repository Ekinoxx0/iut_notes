package fr.ekinoxx.web.login;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.html.Tag;
import org.rapidoid.http.Req;
import org.rapidoid.http.handler.HandlerInvocation;

@Controller
public class InvalidDataPage {
	
	@Page("/invalidData")
	public Object index(final Req req, final HandlerInvocation invocation) throws Exception {
        Tag msg = GUI
        		.span("Une erreur est survenue à propos de vos données")
        		.class_("label label-danger")
        		.style("font-size: 180%; display: block;");
		return GUI.page(msg, GUI.spaced("Merci de contacter le service bugs@ekinoxx.ovh")).brand("Notes IUT Informatique");
	}
	
}
