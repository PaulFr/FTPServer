package server;

import util.Console;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private static Console console;
	private static ClientManager clientManager;
	private static ServerSocket serv;

	public static void main(String[] args) {
		//Determination du port (21 par défaut)
		int port;
	    if(args.length<=0) 
	    	port=12458;
	    else 
	    	port = new Integer(args[0]);
	    
	    console = new Console();
	    try {
			serv = new ServerSocket(port);
		} catch (IOException e1) {
			System.err.println("Une erreur est survenue.");
			e1.printStackTrace();
		} 
	    clientManager = new ClientManager();
	    
	try{
	    while (true) 
	      {
	        clientManager.add(serv.accept());
	      }
	    } catch (Exception e) {
	}

	}
	
	public static String getCleanPath() {
		return Server.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	}
	

}
