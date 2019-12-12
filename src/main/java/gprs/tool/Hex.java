package gprs.tool;

public class Hex {
    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    
	
	public static void main(String[] args) {
		String a = Hex.hexStringToString("3333372f3836353337333034343537353937302f323031392f372f32342f31332f33382f35372f332f3331");
		System.out.println(a);
		byte[] bs = new byte[1];
		bs[0] = (byte) (0xff & Integer.parseInt("38", 16));
		System.out.println(Integer.parseInt("38", 16));
		System.out.println(bs[0]);
		System.out.println(new String(bs));
	}
	
	public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
    
}