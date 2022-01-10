import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class AsymmetricKeyGen {
	private Cipher cipher;
	private KeyPairGenerator keyPairGenerator;
	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	// Constructor
	public AsymmetricKeyGen(String algorithm, int keysize) 
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		cipher = Cipher.getInstance(algorithm);
		keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
		keyPairGenerator.initialize(keysize);
		keyPair = keyPairGenerator.genKeyPair();
		publicKey = keyPair.getPublic();
		privateKey = keyPair.getPrivate();
	}

	// Getters
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public Cipher getCipher() {
		return cipher;
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

	// Tester Application
	public static void main(String[] args) {
		AsymmetricKeyGen keygen;
		try {
			keygen = new AsymmetricKeyGen("RSA", 1024);
			keygen.writeToFile("keys/public", keygen.getPublicKey().getEncoded());
			keygen.writeToFile("keys/private", keygen.getPrivateKey().getEncoded());
		} catch (Exception e) {
			System.err.println("Writing keys to disk failed: " + e.getMessage());
		}

	}
}
