package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import util.Console;

public class Client {

	private static Socket socket;
	private static Console console;
	private static PrintWriter output;
	private static BufferedReader input;
	
	public static void send(String msg){
		output.println(msg);
		output.flush();
	}
	public static void main(String[] args) {
		int port;
	    if(args.length<=0) 
	    	port=12458;
	    else 
	    	port = new Integer(args[0]);
	    
	    console = new Console(true);
	    try {
			socket = new Socket("localhost", port);
			output = new PrintWriter(socket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			try {
				String message = "";
				char charCur[] = new char[1];
				while (input.read(charCur, 0, 1) != -1) {
					if (charCur[0] != '\u0000' && charCur[0] != '\n'
							&& charCur[0] != '\r')
						message += charCur[0];
					else if (!message.equalsIgnoreCase("")) {
						System.out.println(message);
						message = "";
					}
				}
			}catch(Exception e){
				
			}
		} catch (IOException e1) {
			System.err.println("Une erreur est survenue.");
			e1.printStackTrace();
		} 
	}

}
