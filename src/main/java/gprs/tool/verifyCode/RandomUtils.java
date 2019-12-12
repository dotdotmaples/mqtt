package gprs.tool.verifyCode;

import java.util.Random;

/**
 * 生成随机数
 * <p> 2017年7月5日 上午10:36:31 </p>
 */
public class RandomUtils {
	
	private static final char[] CADESEQUENCE = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	
	/**
	 * 生成指定位数的随机数(0-9数字)
	 * <p>王叶东wyd
	 * <p>2017年7月5日 上午10:46:58
	 * @param codeCount 位数
	 * @return
	 */
	public static String getRandomCode (int codeCount) {
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount个字符的验证码
		Random random = new Random();
        for (int i = 0; i < codeCount; i++) {
        	char code = CADESEQUENCE[random.nextInt(CADESEQUENCE.length)];
            //组合
            randomCode.append(code);
        }
        return randomCode.toString();
	}
	
	/*public static void main(String[] args) {
	 // 统计出现的频率
		for (int i = 0; i < 100; i++) {
			System.out.println(RandomUtils.getRandomCode(1));
		}
	}*/

}
