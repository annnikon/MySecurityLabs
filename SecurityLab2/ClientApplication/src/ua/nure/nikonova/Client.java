package ua.nure.nikonova;
import java.math.BigInteger;
import java.net.*;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.io.*;

import javax.crypto.Cipher;

public class Client {

	public static void main(String[] ar) {

		int serverPort = 6666;
		String address = "192.168.1.1";

		try {
			InetAddress ipAddress = InetAddress.getByName(address);
			Socket socket = new Socket(ipAddress, serverPort);


			InputStream socketInput = socket.getInputStream();
			OutputStream soketOutput = socket.getOutputStream();

			DataInputStream in = new DataInputStream(socketInput);
			DataOutputStream out = new DataOutputStream(soketOutput);


			BufferedReader keyboard = new BufferedReader(new InputStreamReader(
					System.in));
			String login = null;
			String password = null;
			String str = null;
			String openKey = in.readUTF();
			System.out.println("OPEN KEY");
			System.out.println(openKey);

			System.out.println("ENTER LOGIN");
			System.out.println();
			login = keyboard.readLine();

			System.out.println("ENTER PASSWORD");
			System.out.println();
			password = keyboard.readLine();

			str = "login: " + login + " " + "password: " + password;
			byte[] encryptedText = RSA.encrypt(str, RSA.restorePublic(openKey));

			System.out.println("SENDING...");
			out.writeUTF(RSA.byte2Hex(encryptedText));
			out.flush();
			str = in.readUTF();
			System.out.println("SERVER REPLY : " + str);

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}