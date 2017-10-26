package blab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ConnectedBlabClient {
	public Socket socket;
	public BlabServer server;
	public String nick;
	int portNum;
	
	public ConnectedBlabClient(Socket sock, BlabServer ser, int port){
		socket = sock;
		server = ser;
		portNum = port;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String intext = "";
					while((intext = reader.readLine())!= null)
					{
						server.broadcast(intext);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
