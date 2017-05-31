package ua.nure.nikonova;

import java.io.*;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA {
	public static final String ALGORITHM = "RSA";
	public static Map<String, String> map = new HashMap<String, String>();

	public static Map<String, String> generateKey() {
		String privateK = null;
		String publicK = null;
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(ALGORITHM);
			keyGen.initialize(1024, new SecureRandom());
			final KeyPair key = keyGen.generateKeyPair();
			privateK = byte2Hex(key.getPrivate().getEncoded());
			publicK = byte2Hex(key.getPublic().getEncoded());
			map.put("private", privateK);
			map.put("public", publicK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(dectyptedText);
	}

	static PrivateKey restorePrivate(String key) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(hex2Byte(key));
		return keyFactory.generatePrivate(privateKeySpec);
	}

	public static String byte2Hex(byte b[]) {
		java.lang.String hs = "";
		java.lang.String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = java.lang.Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toLowerCase();
	}

	public static byte hex2Byte(char a1, char a2) {
		int k;
		if (a1 >= '0' && a1 <= '9')
			k = a1 - 48;
		else if (a1 >= 'a' && a1 <= 'f')
			k = (a1 - 97) + 10;
		else if (a1 >= 'A' && a1 <= 'F')
			k = (a1 - 65) + 10;
		else
			k = 0;
		k <<= 4;
		if (a2 >= '0' && a2 <= '9')
			k += a2 - 48;
		else if (a2 >= 'a' && a2 <= 'f')
			k += (a2 - 97) + 10;
		else if (a2 >= 'A' && a2 <= 'F')
			k += (a2 - 65) + 10;
		else
			k += 0;
		return (byte) (k & 0xff);
	}

	public static byte[] hex2Byte(String str) {
		int len = str.length();
		if (len % 2 != 0)
			return null;
		byte r[] = new byte[len / 2];
		int k = 0;
		for (int i = 0; i < str.length() - 1; i += 2) {
			r[k] = hex2Byte(str.charAt(i), str.charAt(i + 1));
			k++;
		}
		return r;
	}
}
