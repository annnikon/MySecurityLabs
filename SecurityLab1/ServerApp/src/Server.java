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


			InputStream socketInput = socket.getInputStream();
			OutputStream socketOutput = socket.getOutputStream();


			DataInputStream in = new DataInputStream(socketInput);
			DataOutputStream out = new DataOutputStream(socketOutput);


			//BufferedReader br = new BufferedReader(new InputStreamReader(socketInput));

			System.out.println("Received message from client.");
			String strmessage = in.readUTF();
			
			System.out.println("Received key from client.");
			String strkey = in.readUTF();
			
			
			System.out.println("CODING WITH DES....");

			if (strkey.length() > 8) strkey=strkey.substring(0, 8);
			byte[] bytemessage = stringToByte(strmessage);
			byte[] bytekey = stringToByte(strkey);

			

			if(bytemessage.length<8)
			{
				byte[] newB=new byte[8];
				for(int i=0;i<bytemessage.length;i++)
					newB[i] = bytemessage[i];
				bytemessage=newB;
			}

			ArrayList<Long> listMessage = new ArrayList<Long>();

			for(int i=0;i<bytemessage.length;i+=8){
				byte[]messagePart=new byte[8];
				for(int j=0,counter=i;j<8&&counter<bytemessage.length;counter++,j++){
					messagePart[j]=bytemessage[counter];
				}
				long lpart=Long.parseLong(byteToString(messagePart), 2);
				listMessage.add(lpart);
			}

			long lkey = Long.parseLong(byteToString(bytekey), 2);
			byte[] key = new byte[8];

			String binaryMessage="";
			String encryptedStr="";
			String hexStr="";
			String symbStr="";
			String binaryDecr="";
			String symbDecr="";

			for(Long lmessage:listMessage) {
				byte[] message = new byte[8];
				byte[] encrypted;
				byte[] decrypted;

				for (int i = 0; i < 8; i++) {
					key[7 - i] = (byte) (lkey >>> 8 * i);
					message[7 - i] = (byte) (lmessage >>> 8 * i);
				}

				binaryMessage+=byteToString(message);
				encrypted = DES.encrypt(message, key);
				String s = byteToString(encrypted);
				String s1 = s.substring(1);
				long l = Long.parseLong(s1, 2);
				l |= Long.MIN_VALUE;

				encryptedStr+=byteToString(encrypted);
				hexStr+=Long.toHexString(l);
				symbStr+=byteToCharacters(encrypted);

				decrypted = DES.decrypt(encrypted, key);
				binaryDecr+=byteToString(decrypted);
				symbDecr+=byteToCharacters(decrypted);
			}

			System.out.println("Binary key");
			System.out.println(byteToString(key));
			System.out.println();

			System.out.println("Binary message");
			System.out.println(binaryMessage);
			System.out.println();

			System.out.println("Binary encrypted");
			System.out.println(encryptedStr);
			System.out.println();

			System.out.println("Hex");
			System.out.println(hexStr);
			System.out.println();

			System.out.println("Symbolic encrypted");
			System.out.println(symbStr);
			System.out.println();

			System.out.println("Binary decrypted");
			System.out.println(binaryDecr);
			System.out.println();

			System.out.println("Symbolic decrypted");
			System.out.println(symbDecr);

			System.out.println("SENDING DATA BACK.....");
			out.writeUTF(symbDecr);
			out.flush();

			//out.writeUTF(keyMap.get("public"));
/*
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


			*/

		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static byte[] stringToByte(String line) {
		byte[] result = new byte[line.length()];
		for (int i = 0; i < line.length(); i++) {
			result[i] = (byte) line.charAt(i);
		}
		return result;
	}

	public static String byteToString(byte[] data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < 8; j++) {
				stringBuffer.append(Math.abs((data[i] >>> (7 - j)) % 2));
			}
		}
		return stringBuffer.toString();
	}

	public static String byteToCharacters(byte[] data) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			stringBuffer.append((char) data[i]);
		}
		return stringBuffer.toString();
	}

	public static float countOneChance(byte[] data, int position) {
		int oneCount = 0;
		for (int i = 0; i < 8; i++) {
			oneCount += Math.abs((data[i] >>> position) % 2);
		}

		return (float) oneCount / 8.0f;
	}




}