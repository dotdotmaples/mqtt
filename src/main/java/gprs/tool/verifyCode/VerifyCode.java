package gprs.tool.verifyCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gprs.com.entity.PhoneVerifyCode;
import gprs.com.exception.MyException;
import gprs.tool.cache.CacheUtils;


@SuppressWarnings("unused")
public class VerifyCode {
	
    private static final String URL = "http://sms.ejar.com.cn/JcSms/send"; // 请求地址
    private static final String KEY = "T4QRHryF9CB2jC6fzXQtjipMJxvcwrdd";  // 密令
    
	/**
	 * 短信发送验证码
	 * @param phone
	 * @param openid
	 * @return
	 * @throws MyExceptionUser
	 */
	public static String sendVerifyCode(String phone) throws MyException {
		
		//验证手机号
		 Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false;  
	     p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$"); // 验证手机号  
	     m = p.matcher(phone);  
	     b = m.matches();  
	     if(!b){
	    	 throw new MyException("手机号错误！");
	     }
		
		// 缓存拿验证码
		Object object = CacheUtils.get("phoneVerifyCodeCache",phone);
		// 得到验证码信息,防止重复发送短信
		if (object != null) {
			// 判断是否过期
			PhoneVerifyCode pvc = (PhoneVerifyCode) object;
			if (pvc.getEndTime() > System.currentTimeMillis()) {
				// 验证码未过期,不需重复发送
				throw new MyException("不要频繁发送验证码");
			}
			// 清除原始,重新发送
			CacheUtils.remove("phoneVerifyCodeCache",phone);
		}
		// 得到验证码
		String verifyCode = RandomUtils.getRandomCode(6);
		String content = "验证码是" + verifyCode;
		System.out.println(content);
		
//		发送验证码
//		try {
//			String sendGet = sendGet(URL,"key=" + KEY + "&mob=" + phone + "&msg=" + URLEncoder.encode(content, "utf-8") + "&sign=");
//			System.out.println("发送验证码返回信息:" + sendGet);
//		} catch (UnsupportedEncodingException e) {
//			throw new MyException("发送失败");
//		}
		// 保存验证码
		long startTime = System.currentTimeMillis();
		PhoneVerifyCode phoneVerifyCode = new PhoneVerifyCode();
		phoneVerifyCode.setVerifyCode(verifyCode);
		phoneVerifyCode.setPhone(phone);
		phoneVerifyCode.setStartTime(startTime);
		phoneVerifyCode.setEndTime(startTime + 120 * 1000);// 结束毫秒时间
		CacheUtils.put("phoneVerifyCodeCache",phone,phoneVerifyCode);
		return verifyCode;

	}
	
	/**
	 * 验证验证码
	 * 2017年7月12日 上午9:56:38
	 * @param verifyCode2
	 * @param phone
	 * @param request
	 * @throws MyExceptionUser
	 */
	public static void checkVerifyCode(String verifyCode2, String phone) throws MyException {
		// 缓存拿验证码
		Object object = CacheUtils.get("phoneVerifyCodeCache",phone);
		if (object == null) {
			// 已过期,或则未发送验证码
			throw new MyException("验证码已失效");
		}
		// 判断是否过期
		PhoneVerifyCode pvc = (PhoneVerifyCode) object;
		Date date = new Date();
		if (pvc.getEndTime() < date.getTime()) {
			// 验证码已过期
			// 清除缓存
			CacheUtils.remove("phoneVerifyCodeCache", phone);
			throw new MyException("验证码已过期");
		}
		if (!pvc.getVerifyCode().equalsIgnoreCase(verifyCode2)) {// 忽略大小写
			throw new MyException("验证码有误");
		}
		// 检查是否是同一手机
		if (!pvc.getPhone().equals(phone)) {
			throw new MyException("发送验证码的手机和当前手机不一致");
		}
		// 清除缓存
		CacheUtils.remove("phoneVerifyCodeCache", phone);

		System.out.println("花费时间" + (date.getTime() - pvc.getStartTime()) + "毫秒");
		System.out.println("verifyCode从缓存拿到生成的验证码" + pvc.getVerifyCode());
	}
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// System.out.println("realUrl::::::::::"+realUrl);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			System.out.println("VerifyCode|sendGet响应头字段=="+map);
			
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println("result>>>>>" + result);
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
}
