package secure; /**
 * 
 */

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author tosindo
 *
 */
public interface SecureIParent {
	
	public static final int PORT = 9092;

	public byte[] encryptMessage(byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

	public byte[] decryptMessage(byte[] ciphertext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
	
}
