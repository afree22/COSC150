package blab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class BlabClient {
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	String nick;
	int portNum;

	public BlabClient(String host, int port, String nick) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.nick = nick;
		portNum = port;

		new Thread(new Runnable() {
			public void run() {
				String intext = "";
				try {
					while ((intext = reader.readLine()) != null) {
						if(intext.contains("/EXIT")){
							System.exit(-1);
						}
						System.out.println(intext);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				String intext = "";
				PrintWriter w = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				try {
					w = new PrintWriter(socket.getOutputStream(), true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					while ((intext = reader.readLine()) != null) {
						if (intext.trim().isEmpty()) {
							continue;}
						w.println(nick + ": " + intext);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
