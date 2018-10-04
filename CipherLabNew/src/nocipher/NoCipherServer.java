package nocipher; /**
 * 
 */

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

/**
 * @author tosindo
 *
 */
public class NoCipherServer implements NoCipherIParent {
	/**
	   * Main Method
	   * 
	   * @param args
	   */
	  public static void main(String args[])
	  {
		  NoCipherServer noCipherServer = new NoCipherServer();
		  noCipherServer.setup();
		  // Wait for requests
		  while (true) {
			  noCipherServer.receiveAndSend();
		  }
	  }
	  
	private void setup(){
		
	}
	
	public void receiveAndSend() {
		ServerSocket server;
	    Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try{
	    	server = new ServerSocket(PORT);
	    	System.out.println("Waiting for requests from client...");
	    	client = server.accept();
	    	System.out.println("Connected to client at the address: "+client.getInetAddress());
	    	
	    	oos = new ObjectOutputStream(client.getOutputStream());
	        ois = new ObjectInputStream(client.getInputStream());

	        // Receive message from the client
	        byte[] clientMsg = (byte[]) ois.readObject();

	        // Print the message in UTF-8 format
	        System.out.println("Message from Client: "+ new String(clientMsg, "UTF-8"));

			String response = "Message received.";
			oos.writeObject(response.getBytes());
			oos.flush();

	        // Close Client and Server sockets
	        client.close();
	        server.close();
	        oos.close();
	        ois.close();
	        
	    }catch(IOException | ClassNotFoundException e){
	    	e.printStackTrace();
	    }

    }

    @Override
    public byte[] encryptMessage(byte[] plaintext) {
        return null;
    }

    @Override
    public byte[] decryptMessage(byte[] ciphertext) {
        return null;
    }

}
