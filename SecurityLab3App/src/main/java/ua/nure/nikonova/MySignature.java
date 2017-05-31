package ua.nure.nikonova;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MySignature {
	
	

public static String getHash(File file) throws IOException, NoSuchAlgorithmException
{
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    FileInputStream fis = new FileInputStream(file);

    byte[] dataBytes = new byte[1024];

    int nread = 0; 
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };
    byte[] mdbytes = md.digest();

    //convert the byte to hex format
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    
    fis.close();
    writeToFile(file,sb.toString());
    return sb.toString();
}

public static void writeToFile(File original, String checksum) {
	String  destName = original.getAbsolutePath()+".sha";
	try (BufferedWriter br = new BufferedWriter(new FileWriter(destName))) {
		br.write(checksum);
		System.out.println("Writed to file:" + destName);
		br.flush();
	}
		
	
	catch (IOException e) {
		e.printStackTrace();
	}
}
}
