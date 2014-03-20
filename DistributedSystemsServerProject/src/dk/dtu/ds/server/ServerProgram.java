package dk.dtu.ds.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServerProgram {
	
	private static final int DEFAULT_MAX_THREADS 	= 2;
	private static final int DEFAULT_PORT 			= 10101;
	
	public static void main( String [ ] args ) {
		// Variables, set at a default
		int port 			= DEFAULT_PORT;
		int maxThreads 		= DEFAULT_MAX_THREADS;
		
		
		// Commandline argument parsing
		int argsCount = 0;
		while( argsCount < args.length ){
			if( args[argsCount] == "-p" ){
				port = Integer.parseInt(args[argsCount + 1]);
			}else if( args[argsCount] == "-t" ){
				maxThreads = Integer.parseInt(args[argsCount + 1]);
			}
			argsCount = argsCount + 2;
		}
		
		// Server essentials
		SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

		try{
			// Create a new server socket for accepting incoming transmissions
			SSLServerSocket socket = (SSLServerSocket) socketFactory.createServerSocket(port);
	        
			System.out.println("Server started!");
			
	        // Actual server loop, creating a new incoming socket
			// and handing it over to a handler
	        while( true ){
		        SSLSocket clientSocket = (SSLSocket) socket.accept();
		        SocketRunnable sr = new SocketRunnable(clientSocket);
		        executor.execute(sr);
	        }
	        
	        

		}catch( Exception E ){
			System.err.println("Unknown error occured!");
		}
		
	}

}
