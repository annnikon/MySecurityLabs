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

			String message=null;
			String key=null;
String str = null;
			System.out.println("ENTER MESSAGE:");
			System.out.println();
			message = keyboard.readLine();
			out.writeUTF(message);
			out.flush();

			System.out.println("ENTER KEY:");
			System.out.println();
			key = keyboard.readLine();
			out.writeUTF(key);
			out.flush();

			System.out.println("SENDING...");

			str = in.readUTF();
			System.out.println("SERVER REPLY : " + str);

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}