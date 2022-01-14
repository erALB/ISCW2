import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class SecretKeyGen {
	private Cipher cipher;
	private SecretKey secretKey;

	// Constructor
	public SecretKeyGen(String algorithm, int keysize, String secret)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException {
		super();
		cipher = Cipher.getInstance(algorithm);
		secretKey = new SecretKeySpec(fixSecretLength(secret, keysize), algorithm);
	}

	// Getters
	public Cipher getCipher() {
		return cipher;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	// Helper Function: make sure specified password has proper length
	private byte[] fixSecretLength(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += "!";
			}
		}
		return s.substring(0, length).getBytes("UTF-8");
	}
	

	// Helper Function: write content to desired file path
	private void writeToFile(String path, byte[] content) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(content);
		fos.flush();
		fos.close();
	}

	// Tester application
	public static void main(String[] args) {
		SecretKeyGen keygen = null;
		try {
			keygen = new SecretKeyGen("AES", 16, "MySecr3tPassw0rd1234");
			keygen.writeToFile("keys/secret", keygen.getSecretKey().getEncoded());
		} catch (Exception e) {
			System.err.println("Writing key to disk failed: " + e.getMessage());
		}
		System.out.print(
	            "The Symmetric Key is: "
	            + DatatypeConverter.printHexBinary(
	                  keygen.getSecretKey().getEncoded()));
		
		

	}
}
