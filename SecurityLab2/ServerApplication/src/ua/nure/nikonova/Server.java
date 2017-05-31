package ua.nure.nikonova;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Server {

	public static void main(String[] ar) {
		int port = 6666;
		try {
			ServerSocket socketServer = new ServerSocket(port);
			System.out.println("WAITING FOR A CLIENT...");

			Socket socket = socketServer.accept();
			System.out.println("...CONNECTED");
			System.out.println();

			Map<String, String> keyMap = RSA.generateKey();


			InputStream socketInput = socket.getInputStream();
			OutputStream socketOutput = socket.getOutputStream();


			DataInputStream in = new DataInputStream(socketInput);
			DataOutputStream out = new DataOutputStream(socketOutput);

			out.writeUTF(keyMap.get("public"));

			String line = null;
			line = in.readUTF();
			System.out.println("ENCRYPTED MESSAGE : " + line);

			String plainText = RSA.decrypt(RSA.hex2Byte(line),RSA.restorePrivate(keyMap.get("private")));

			System.out.println();

			Map map = getData(plainText);
			System.out.println("LOGIN - " + map.get("login"));
			System.out.println("PASSWORD - " + map.get("password"));
			System.out.println();

			System.out.println("SENDING DATA BACK...");
			out.writeUTF(plainText);
			out.flush();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	private static Map<String, String> getData(String string) {
		Map<String, String> map = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("login:");
		Matcher matcher = pattern.matcher(string);
		Pattern pattern2 = Pattern.compile("password:");
		Matcher matcher2 = pattern2.matcher(string);
		int start = 0;
		int end = 0;
		int start2 = 0;
		while (matcher.find()) {
			end = matcher.end();
		}
		while (matcher2.find()) {
			start = matcher2.start();
			start2 = matcher2.end();
		}

		String login = string.substring(end, start);
		map.put("login", login);

		String password = string.substring(start2, string.length());
		map.put("password", password);
		return map;
	}

}