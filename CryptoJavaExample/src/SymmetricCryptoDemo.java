import java.io.File;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class SymmetricCryptoDemo {

	// Read "files" directory and ask user to encrypt/decrypt all files
	public static void main(String[] args) {

		// Get list of files in the directory
		File dir = new File("files");
		File[] filelist = dir.listFiles();

		try {
			// Instantiate the secret key generator
			SecretKeyGen keygen = new SecretKeyGen("AES", 16, "MySecr3tPassw0rd");

			// Show choice window for user
			int choice = -2;
			while (choice != -1) {
				String[] options = { "Encrypt All", "Encrypt Selected", "Decrypt All", "Decrypt Selected", "Exit" };
				choice = JOptionPane.showOptionDialog(null, "Select an option", "Options", 0,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				switch (choice) {

				// Encrypt all files option
				case 0:
					Arrays.asList(filelist).forEach(file -> {
						try {
							CryptoFunctions.encryptFile(file, keygen.getCipher(), keygen.getSecretKey());
						} catch (Exception e) {
							System.err.println("Couldn't encrypt " + file.getName() + ": " + e.getMessage());
						}
					});
					System.out.println("File encryption finished");
					break;

				// Encrypt a specific file in the user's PC option
				case 1:
					JFileChooser fileToEncrypt = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

					int encryptValue = fileToEncrypt.showOpenDialog(null);

					if (encryptValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileToEncrypt.getSelectedFile();
						System.out.println(selectedFile.getAbsolutePath());
						try {
							CryptoFunctions.encryptFile(selectedFile, keygen.getCipher(), keygen.getSecretKey());
						} catch (Exception e) {
							System.err.println("Couldn't encrypt " + selectedFile.getName() + ": " + e.getMessage());
						}
					}
					System.out.println("File encryption finished");
					break;

				// Decrypt all files option
				case 2:
					Arrays.asList(filelist).forEach(file -> {
						try {
							CryptoFunctions.decryptFile(file, keygen.getCipher(), keygen.getSecretKey());
						} catch (Exception e) {
							System.err.println("Couldn't decrypt " + file.getName() + ": " + e.getMessage());
						}
					});
					System.out.println("File decryption finished");
					break;
					
				// Decrypt a specific file in the user's PC option
				case 3:
					JFileChooser fileToDecrypt = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

					int decryptValue = fileToDecrypt.showOpenDialog(null);
					
					if (decryptValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileToDecrypt.getSelectedFile();
						System.out.println(selectedFile.getAbsolutePath());
						try {
							CryptoFunctions.decryptFile(selectedFile, keygen.getCipher(), keygen.getSecretKey());
						} catch (Exception e) {
							System.err.println("Couldn't decrypt " + selectedFile.getName() + ": " + e.getMessage());
						}
					}
					System.out.println("File encryption finished");
					break;

				// Exit application
				default:
					choice = -1;
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Incorrect Algorithm Parameters: " + e.getMessage());
		}
	}
}
