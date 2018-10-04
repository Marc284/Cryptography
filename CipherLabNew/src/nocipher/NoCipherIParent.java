package nocipher; /**
 * 
 */

/**
 * @author tosindo
 *
 */
public interface NoCipherIParent {
	
	public static final int PORT = 9091;

	public byte[] encryptMessage(byte[] plaintext);

	public byte[] decryptMessage(byte[] ciphertext);
	
}
