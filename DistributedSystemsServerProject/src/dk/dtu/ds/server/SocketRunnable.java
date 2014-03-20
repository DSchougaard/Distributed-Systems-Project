package dk.dtu.ds.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;

import org.json.*;

import dk.dtu.ds.server.*;

public class SocketRunnable implements Runnable {

	/*
	 * Thread for each of the incoming connections
	 */
	
	private SSLSocket socket = null;
	
	public SocketRunnable(SSLSocket socket){
		System.out.println("New SocketRunnable initizlized!");
		this.socket = socket;
		
	}
		
	@Override
	public void run() {
		System.out.println("SocketRunnable was run!");
	
		ObjectOutputStream output = null;
		ObjectInputStream input = null;		
		
		try {
			output = new ObjectOutputStream(this.socket.getOutputStream());
			input = new ObjectInputStream(this.socket.getInputStream());
			
			JSONObject in = (JSONObject) input.readObject();
			
			
			switch(in.getInt("command")){
			case Commands.Client.LOGIN:
				String username = in.getString("username");
				String password = in.getString("password");
				
				JSONObject out = new JSONObject();
				if( username.equals("tyksak") && password.equals("secret1234")){
					out.put("command", Commands.Server.LOGIN_OK);
				}else{
					out.put("command", Commands.Server.LOGIN_FAIL);
				}
				
				break;
			case Commands.Client.REGISTER:
				break;
			default:
				System.err.println("[COMMAND] Error in recieved command code, abandoning transmission.");
				this.socket.close();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	
	}

}
