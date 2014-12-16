package client;

import io.Transfer;
import util.Console;

import java.io.*;
import java.net.Socket;

public class Client {

	private static Socket socket;
	private static Console console;
	private static PrintWriter output;
	private static BufferedReader input;
	private static int retrMode = 0;
	private static String retrPath = "";

	public static void send(String msg){
		String commande = "", req = "";
		try {
			commande = msg.substring(0, 4);
			req = msg.substring(5);
		} catch (Exception e) {

		}

		switch (commande) {
			case "STOR":
				try {
					FileInputStream fis = new FileInputStream(req);
					output.println(msg);
					output.println(String.valueOf(fis.getChannel().size()));
					Transfer.go(fis, socket.getOutputStream(), false);

				} catch (Exception e) {
					System.err.println("Le fichier " + req + " n'existe pas !");
					return;
				}
				break;
			case "RETR":
				output.println(msg);
				retrMode = 1;
				retrPath = req;
				break;
			default:
				output.println(msg);
				break;
		}

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
						if (retrMode == 1) {
							System.out.println(message);
							if (message.substring(0, 3) != "200") retrMode = 0;
							retrMode = 2;
						} else if (retrMode == 2) {
							retrMode = 3;
							System.out.println("Taille du fichier : " + message);
						} else if (retrMode == 3) {
							PrintWriter writer = new PrintWriter(retrPath, "UTF-8");
							writer.print(message);
							writer.close();
							retrMode = 0;
						} else {
							System.out.println(message);
						}
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
