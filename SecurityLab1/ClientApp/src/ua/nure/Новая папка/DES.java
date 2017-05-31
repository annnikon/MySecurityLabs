package ua.nure.nikonova;
import java.io.*;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class DES {
	private static Tables tables = new Tables();
	private static byte[][] K;

	private static void setBit(byte[] data, int pos, int val) {
		int posByte = pos / 8;
		int posBit = pos % 8;
		byte tmpB = data[posByte];
		tmpB = (byte) (((0xFF7F >> posBit) & tmpB) & 0x00FF);
		byte newByte = (byte) ((val << (8 - (posBit + 1))) | tmpB);
		data[posByte] = newByte;
	}

	private static int extractBit(byte[] data, int pos) {
		int posByte = pos / 8;
		int posBit = pos % 8;
		byte tmpB = data[posByte];
		int bit = tmpB >> (8 - (posBit + 1)) & 0x0001;
		return bit;
	}

	private static byte[] rotateLeft(byte[] input, int len, int pas) {
		int nrBytes = (len - 1) / 8 + 1;
		byte[] out = new byte[nrBytes];
		for (int i = 0; i < len; i++) {
			int val = extractBit(input, (i + pas) % len);
			setBit(out, i, val);
		}
		return out;
	}

	private static byte[] extractBits(byte[] input, int pos, int n) {
		int numOfBytes = (n - 1) / 8 + 1;
		byte[] out = new byte[numOfBytes];
		for (int i = 0; i < n; i++) {
			int val = extractBit(input, pos + i);
			setBit(out, i, val);
		}
		return out;

	}

	private static byte[] permutFunc(byte[] input, int[] table) {
		int nrBytes = (table.length - 1) / 8 + 1;
		byte[] out = new byte[nrBytes];
		for (int i = 0; i < table.length; i++) {
			int val = extractBit(input, table[i] - 1);
			setBit(out, i, val);
		}
		return out;

	}

	private static byte[] xorFunc(byte[] a, byte[] b) {
		byte[] out = new byte[a.length];
		for (int i = 0; i < a.length; i++) {
			out[i] = (byte) (a[i] ^ b[i]);
		}
		return out;

	}

	private static byte[] encrypt64Bloc(byte[] bloc, byte[][] subkeys, boolean isDecrypt) {
		byte[] tmp = new byte[bloc.length];
		byte[] R = new byte[bloc.length / 2];
		byte[] L = new byte[bloc.length / 2];

		tmp = permutFunc(bloc, tables.IP);

		L = extractBits(tmp, 0, tables.IP.length / 2);
		R = extractBits(tmp, tables.IP.length / 2, tables.IP.length / 2);

		for (int i = 0; i < 16; i++) {
			byte[] tmpR = R;
			if (isDecrypt)
				R = eFunc(R, subkeys[15 - i]);
			else
				R = eFunc(R, subkeys[i]);
			R = xorFunc(L, R);
			L = tmpR;
		}

		tmp = concatBits(R, tables.IP.length / 2, L, tables.IP.length / 2);

		tmp = permutFunc(tmp, tables.invIP);
		return tmp;
	}

	private static byte[] eFunc(byte[] R, byte[] K) {
		byte[] tmp;
		tmp = permutFunc(R, tables.expandTbl);
		tmp = xorFunc(tmp, K);
		tmp = sFunc(tmp);
		tmp = permutFunc(tmp, tables.P);
		return tmp;
	}

	private static byte[] sFunc(byte[] in) {
		in = separateBytes(in, 6);
		byte[] out = new byte[in.length / 2];
		int halfByte = 0;
		for (int b = 0; b < in.length; b++) {
			byte valByte = in[b];
			int r = 2 * (valByte >> 7 & 0x0001) + (valByte >> 2 & 0x0001);
			int c = valByte >> 3 & 0x000F;
			int val = tables.sboxes[b][r][c];
			if (b % 2 == 0)
				halfByte = val;
			else
				out[b / 2] = (byte) (16 * halfByte + val);
		}
		return out;
	}

	private static byte[] separateBytes(byte[] in, int len) {
		int numOfBytes = (8 * in.length - 1) / len + 1;
		byte[] out = new byte[numOfBytes];
		for (int i = 0; i < numOfBytes; i++) {
			for (int j = 0; j < len; j++) {
				int val = extractBit(in, len * i + j);
				setBit(out, 8 * i + j, val);
			}
		}
		return out;
	}

	private static byte[] concatBits(byte[] a, int aLen, byte[] b, int bLen) {
		int numOfBytes = (aLen + bLen - 1) / 8 + 1;
		byte[] out = new byte[numOfBytes];
		int j = 0;
		for (int i = 0; i < aLen; i++) {
			int val = extractBit(a, i);
			setBit(out, j, val);
			j++;
		}
		for (int i = 0; i < bLen; i++) {
			int val = extractBit(b, i);
			setBit(out, j, val);
			j++;
		}
		return out;
	}

	private static byte[][] generateSubKeys(byte[] key) {
		byte[][] tmp = new byte[16][];
		byte[] tmpK = permutFunc(key, tables.PC1);

		byte[] C = extractBits(tmpK, 0, tables.PC1.length / 2);
		byte[] D = extractBits(tmpK, tables.PC1.length / 2, tables.PC1.length / 2);

		for (int i = 0; i < 16; i++) {

			C = rotateLeft(C, 28, tables.keyShift[i]);
			D = rotateLeft(D, 28, tables.keyShift[i]);

			byte[] cd = concatBits(C, 28, D, 28);

			tmp[i] = permutFunc(cd, tables.PC2);
		}

		return tmp;
	}

	public static byte[] encrypt(byte[] data, byte[] key) {

		byte[] bloc;

		K = generateSubKeys(key);
		bloc = encrypt64Bloc(data, K, false);

		return bloc;
	}

	public static byte[] decrypt(byte[] data, byte[] key) {
		byte[] bloc;

		K = generateSubKeys(key);
		bloc = encrypt64Bloc(data, K, true);

		return bloc;
	}
}

