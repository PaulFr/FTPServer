package net;

public class Messages {

	public final static int REQ_OK = 200;
	public final static int COPY_OK = 201;
	public final static int DEL_OK = 202;
	public final static int TRANSF_OK = 203;
	public final static int REQ_ERR = 400;
	public final static int COPY_ERR = 401;
	public final static int DEL_ERR = 402;
	public final static int TRANSF_ERR = 403;
	
	
	public static String get(int code){
		String message = null;
		
		switch(code){
			case Messages.REQ_OK:
				message = "OK - Requête bien reçue";
				break;
			case Messages.COPY_OK:
				message = "OK - Fin copie fichier";
				break;
			case Messages.DEL_OK:
				message = "OK - Fin suppression fichier";
				break;
			case Messages.TRANSF_OK:
				message = "OK - Fin transfert fichier";
				break;
			case Messages.REQ_ERR:
				message = "ERREUR - Requête refusée";
				break;
			case Messages.COPY_ERR:
				message = "ERREUR - Fichier non copié";
				break;
			case Messages.DEL_ERR:
				message = "ERREUR - Suppression non faite";
				break;
			case Messages.TRANSF_ERR:
				message = "ERREUR - Fichier non transféré";
				break;
			default:
				message = "ERREUR - Requête refusée";
				break;
		}
		
		return code+" "+message;
	}
}
