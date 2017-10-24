package blab;

import java.net.*;
import java.io.*;

public class BlabClient
{
	public static void main(String[] args) throws UnknownHostException
	{
		InetAddress inet = InetAddress.getByName(args[0]);
		int portNum = Integer.parseInt(args[1]);
		System.out.println("Connecting to host: "+args[0]+" on port: "+ portNum);
		try
		{
			Socket sock = new Socket(inet,portNum);
			System.out.println("Successfully Connected! - Enter EXIT to exit");
			while(true)
			{
					DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
					System.out.print("Client: ");
					
					BufferedReader bin =new BufferedReader(new InputStreamReader(System.in));
					String chat = bin.readLine();
					
					//create way to exit from the chat
					if(chat.equals("EXIT")) break;
					
					dout.writeUTF(chat);
					DataInputStream din =new DataInputStream(sock.getInputStream());
					System.out.println("Server:"+din.readUTF());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
