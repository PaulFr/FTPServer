package net;


import io.Transfer;
import server.ClientManager;
import server.Server;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

	private static int COUNT = 0;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private ClientManager clientManager;
	private boolean storageState = false;
	private String storagePath = "";
	private int storageSize = -1;
	private int id;

	public Client(Socket socket, ClientManager cm) {
		this.socket = socket;
		this.clientManager = cm;
		this.id = ++Client.COUNT;

		try {
			output = new PrintWriter(socket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
		}

		Thread t = new Thread(this);
		t.start();
	}

	public void send(String message) {
		output.println(message);
		output.flush();
	}


	@Override
	public void run() {

		System.out.println("Un nouveau client (" + this.id
				+ ") vient de se connecter.");
		String message = "";

		try {

			char charCur[] = new char[1];
			while (input.read(charCur, 0, 1) != -1) {
				if (charCur[0] != '\u0000' && charCur[0] != '\n'
						&& charCur[0] != '\r')
					message += charCur[0];
				else if (!message.equalsIgnoreCase("")) {
					String commande = "";
					String req = "";
					try {
						commande = message.substring(0, 4);
						req = message.substring(5);
					} catch (Exception e) {
					}
					
					if(this.storageState){
						if (storageSize == -1) {
							storageSize = Integer.valueOf(message);
							System.out.println("Fichier de taille " + storageSize);
						} else {
							System.out.println(message);
							PrintWriter writer = new PrintWriter(storagePath, "UTF-8");
							writer.print(message);
							writer.close();
							storageSize = -1;
							storageState = false;
							this.send(Messages.get(201));
						}
						/*DataInputStream dis = new DataInputStream(socket.getInputStream());
						int size = dis.readInt();
						byte b[] = new byte[2048];
						dis.read(b, 0, size);
						Transfer.go(socket.getInputStream(), new FileOutputStream(this.storagePath) ,true);
						this.storageState = false;*/
						message = "";
						continue;
					}
					switch (commande) {
						case "RETR":
							FileInputStream fis = null;
							try {
								fis = new FileInputStream(Server.getCleanPath()
										+ "/" + req);
								this.send(Messages.get(200));
								this.send(String.valueOf(fis.getChannel().size()));
								Transfer.go(fis, socket.getOutputStream(), false);
								this.send(Messages.get(203));
							} catch (Exception e) {
								this.send(Messages.get(403));
							}
							break;
						case "STOR":
							//verif de secu
							this.storageState = true;
							this.storagePath = Server.getCleanPath()
									+ "/" + req;
							this.send(Messages.get(200));
							break;
						case "DELE":
							File f = new File(Server.getCleanPath() + "/" + req);
							System.gc();
							if (f.exists()) {
								this.send(Messages.get(200));
								if (f.delete())
									this.send(Messages.get(202));
								else {
									this.send(Messages.get(402));
								}
							} else {
								this.send(Messages.get(402));
								break;
							}

							break;
						case "QUIT":
							this.socket.close();
							break;
						default:
							this.send(Messages.get(400));
							break;
					}
					message = "";
				}

			}

		} catch (Exception e) {

		} finally {
			System.out.println("Client (" + this.id + ") déconnecté.");
			try {
				this.clientManager.remove(this);
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
