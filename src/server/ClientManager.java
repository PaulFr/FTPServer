package server;

import java.net.Socket;
import java.util.ArrayList;

import net.Client;

public class ClientManager {

	private ArrayList<Client> clients;
	
	public ClientManager(){
		this.clients = new ArrayList<Client>();
	}
	
	public void add(Socket socket){
		this.clients.add(new Client(socket, this));
	}
	
	public void remove(Client c){
		this.clients.remove(c);
	}
	
}
