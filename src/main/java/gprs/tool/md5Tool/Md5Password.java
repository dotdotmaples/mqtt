package gprs.tool.md5Tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class Md5Password {
	
	
	//加密工具
	
	public static String md5Encode(String inStr) throws UnsupportedEncodingException{
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
	}
	public static char[] convertMD5(String inStr){  
		
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');
        }  
          
        return a;  
  
    }  
	
}
