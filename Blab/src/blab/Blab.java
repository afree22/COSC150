package blab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public class Blab {
	public static void main(String[] argv) throws UnknownHostException, IOException{
		String host = argv[0];
		int portNum = Integer.parseInt(argv[1]);

		if (isLocalPortInUse(portNum) == true)
		{
			String nick = askUser("Nickname:");
			new BlabClient(host, portNum, nick);
			System.out.println("connected");
		}
		// if local port is not being used, set up server
		else
		{
			new BlabServer(portNum);
			String nick2 = askUser("Nickname:");
			new BlabClient(host, portNum, nick2);
			System.out.println("connected");
		}
	}
	
	private static String askUser(String question) throws IOException{
		System.out.print(question);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String intext = "";
		while((intext = reader.readLine()) != null){
			return intext;
		}
		return "Anonymous";
	}
	

	private static boolean isLocalPortInUse(int pNum) {
		try {
			new ServerSocket(pNum).close();
			return false;

		} catch (IOException e) {
			return true;
		}
	}
}