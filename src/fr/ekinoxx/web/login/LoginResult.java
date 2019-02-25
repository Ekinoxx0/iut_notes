package fr.ekinoxx.web.login;

public enum LoginResult {
	
	DIRECT,
	RELOADING,
	REGISTRATING,
	INVALID_DATA,
	REFUSED;

	public static final String LRESULT_KEY = "lresult";
	
}
