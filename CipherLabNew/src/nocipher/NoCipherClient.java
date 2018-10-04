package nocipher; /**
 * 
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author tosindo
 *
 */
public class NoCipherClient implements NoCipherIParent {
	
	public static void main(String[] args){
		NoCipherClient noCipherClient = new NoCipherClient();
		noCipherClient.sendAndReceive();

	}
	
	public void sendAndReceive() {
		Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try {
            client = new Socket("localhost",PORT);

            System.out.println("Connected to insecure.NoCipherServer on "+ client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
	    	
	    	// send a plaintext message to server
	    	String plaintxt = "Hi from the client";
	    	
//	    	 send message to server
	    	oos.writeObject(plaintxt.getBytes());
	    	oos.flush();
	    	
	    	// receive response from server
	    	byte[] response = (byte[]) ois.readObject();
	    	
//	    	System.out.println("Response from server: "+ new String(response, "ASCII"));
			System.out.println(new String(response, "UTF-8"));

			// close client socket
	    	client.close();
	    	ois.close();
	    	oos.close();
	    	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
