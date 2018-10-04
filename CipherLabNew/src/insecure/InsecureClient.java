package insecure; /**
 * 
 */

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;

/**
 * @author tosindo
 *
 */
public class InsecureClient implements InsecureIParent {

    private Key myDESKey;
	
	public static void main(String[] args){
		InsecureClient insecureClient = new InsecureClient();
		insecureClient.sendAndReceive();

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

			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
			myDESKey = keygenerator.generateKey();

			System.out.println(myDESKey);

			oos.writeObject(myDESKey);
			oos.flush();
	    	
	    	// send a plaintext message to server
	    	String plaintxt = "end key";
	    	
//	    	 send message to server
	    	oos.writeObject(plaintxt.getBytes());
	    	oos.flush();
	    	
	    	// receive response from server
	    	byte[] response = (byte[]) ois.readObject();
	    	
//	    	System.out.println("Response from server: "+ new String(response, "ASCII"));
			System.out.println(new String(decryptMessage(response), "UTF-8"));

			oos.writeObject(encryptMessage("Check connection message".getBytes()));
			oos.flush();

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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

	}

	@Override
	public byte[] encryptMessage(byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher desCipher;
		desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		desCipher.init(Cipher.ENCRYPT_MODE, myDESKey);
		byte[] textEncrypted = desCipher.doFinal(plaintext);

		return textEncrypted;
	}

	@Override
	public byte[] decryptMessage(byte[] ciphertext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher desCipher;
		desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		desCipher.init(Cipher.DECRYPT_MODE, myDESKey);
		byte[] textDecrypted = desCipher.doFinal(ciphertext);

		return textDecrypted;
	}

}
