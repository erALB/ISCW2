import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class CryptoFunctions {

	// Encrypt file with cipher and provided key
	public static void encryptFile(File file, Cipher cipher, Key key)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("Encrypting file: " + file.getName());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		writeToFile(file, cipher);
	}

	// Decrypt file with cipher and provided key
	public static void decryptFile(File file, Cipher cipher, Key key)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		System.out.println("Decrypting file: " + file.getName());
		cipher.init(Cipher.DECRYPT_MODE, key);
		writeToFile(file, cipher);
	}

	// Helper function to save the processed file
	private static void writeToFile(File file, Cipher cipher)
			throws IOException, IllegalBlockSizeException, BadPaddingException {
		FileInputStream in = new FileInputStream(file);
		byte[] input = new byte[(int) file.length()];
		in.read(input);
		FileOutputStream out = new FileOutputStream(file);
		byte[] output = cipher.doFinal(input);
		out.write(output);
		out.flush();
		out.close();
		in.close();
	}

}
