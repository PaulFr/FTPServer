package util;

import java.io.BufferedReader;
import client.Client;
import java.io.InputStreamReader;


public class Console implements Runnable{
	
	private BufferedReader reader;
	private boolean clientMode = false;

	public Console(){
		reader = new BufferedReader(new InputStreamReader(System.in));
		Thread t = new Thread(this);
		t.start();
	}
	
	public Console(boolean clientMode){
		this();
		this.clientMode = clientMode;
	}
	
	@Override
	public void run() {
		try{
			
			String commande;
			
			while((commande = reader.readLine()) != null){
				if (commande.equalsIgnoreCase("quit")) 
			          System.exit(0);
				else if(clientMode == true){
					Client.send(commande);
				}
					
			}
		}catch(Exception e){
			
		}
		
	}

}
