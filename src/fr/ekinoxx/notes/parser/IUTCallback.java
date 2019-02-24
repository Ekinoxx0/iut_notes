package fr.ekinoxx.notes.parser;

import javax.security.auth.callback.Callback;

import fr.ekinoxx.notes.infos.SemestreInfo;

public abstract class IUTCallback implements Callback {
	public abstract void call(SemestreInfo ni);
	public abstract void error(Exception e);
}
