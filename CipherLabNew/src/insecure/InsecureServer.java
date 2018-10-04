package insecure; /**
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
public class InsecureServer implements InsecureIParent {
    private Key key;
	/**
	   * Main Method
	   * 
	   * @param args
	   */
	  public static void main(String args[])
	  {
		  InsecureServer noCipherServer = new InsecureServer();
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

			//key accept
			key = (Key) ois.readObject();

            System.out.println("Key is: " + key.toString());

	        // Receive message from the client
	        byte[] clientMsg = (byte[]) ois.readObject();
	        String endKey = new String(clientMsg, "UTF-8");
	        // Print the message in UTF-8 format
	        System.out.println("Message from insecure.NoCipherClient: "+ endKey);

            if(endKey.equalsIgnoreCase("end key"))
            {
                oos.writeObject(encryptMessage("Key accepted".getBytes()));
                oos.flush();
            }

            clientMsg = (byte []) ois.readObject();
            System.out.println(new String(decryptMessage(clientMsg)));

	        // Close insecure.NoCipherClient and insecure.NoCipherServer sockets
	        client.close();
	        server.close();
	        oos.close();
	        ois.close();
	        
	    }catch(IOException | ClassNotFoundException e){
	    	e.printStackTrace();
	    } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] encryptMessage(byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher desCipher;
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        desCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] textEncrypted = desCipher.doFinal(plaintext);

        return textEncrypted;
    }

    @Override
    public byte[] decryptMessage(byte[] ciphertext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher desCipher;
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        desCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] textDecrypted = desCipher.doFinal(ciphertext);

        return textDecrypted;
    }

}
