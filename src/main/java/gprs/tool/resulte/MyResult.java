package gprs.tool.resulte;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: MyResult 
 * @Description: 功能作用：返回数据消息模型
 * @author: zlw
 * @date: 2018年4月10日 上午9:55:34
 */
public class MyResult {
	
	
	/**
	 * 默认返回码名称
	 */
	private static final String CODE= "code";
	/**
	 * 默认成功返回码
	 */
	private static final int CODE_SUCCESS = 100;
	/**
	 * 默认失败返回码
	 */
	private static final int CODE_ERROR = 500;
	/**
	 * 登录状态丢失返回码
	 */
	private static final int LOGIN_ERROR = 300;
	
	/**
	 * 默认返回消息名称
	 */
	private static final String MSG= "msg";
	/**
	 * 默认成功返回消息
	 */
	private static final String MSG_SUCCESS = "请求成功!";
	/**
	 * 默认失败返回消息
	 */
	private static final String MSG_ERROR = "请求失败!";
	
	/**
	 * 默认返回状态名称
	 */
	private static final String SUCCESS= "success";
	/**
	 * 默认成功返回状态
	 */
	private static final boolean SUCCESS_SUCCESS = true;
	/**
	 * 默认失败返回状态
	 */
	private static final boolean SUCCESS_ERROR = false;
	
	/**
	 * 返回码
	 */
	private int code; 
	/**
	 * 返回消息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private Object data;
	
	
	public MyResult(int code, String msg, Object data) {
        this.code = code;
        this.msg  = msg;
        this.data = data;
    }
	public int getCode(){return code;}
	public void setCode(int code){this.code = code;}
	public String getMsg() {return msg;}
	public void setMsg(String msg){this.msg = msg;}
	public Object getData() {return data;}
	public void setData(Object data){this.data = data;}
	@Override
	public String toString() {
		return "MyResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
	
	//----------------------成功错误返回 MyResult(Objct) code:"100" ------------
	/**
	 * <P>方法名称：success</P>
	 * <P>方法描述：成功 code:"100" msg:"请求成功!" data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult success(){
		return new MyResult(CODE_SUCCESS,MSG_SUCCESS, null);
	}
	/**
	 * <P>方法名称：success</P>
	 * <P>方法描述：成功 code:"100" msg:传入 data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult success(String msg) {
        return new MyResult(CODE_SUCCESS, msg, null);
    }
	/**
	 * <P>方法名称：success</P>
	 * <P>方法描述：成功 code:"100" msg:null data:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult success(Object data) {
		return new MyResult(CODE_SUCCESS, MSG_SUCCESS, data);
	}
	/**
	 * <P>方法名称：success</P>
	 * <P>方法描述：成功 code:"100" msg:传入 data:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult success(String msg, Object data) {
        return new MyResult(CODE_SUCCESS, msg, data);
    }
	/**
	 * <P>方法名称：success</P>
	 * <P>方法描述：code:传入 msg:传入 data:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult success(int code, String msg, Object data) {
		return new MyResult(code, msg, data);
	}
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:"500" msg:"请求失败!" data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult error() {
		return new MyResult(CODE_ERROR, MSG_ERROR, null);
	}
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:"500" msg:传入 data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult error(String msg) {
        return new MyResult(CODE_ERROR, msg, null);
    }
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:"500" msg:传入 data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult loginError(String msg) {
		return new MyResult(LOGIN_ERROR, msg, null);
	}
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:"500" msg:"请求失败!" data:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult error(Object data) {
		return new MyResult(CODE_ERROR, MSG_ERROR, data);
	}
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:传入 msg:传入 data:null</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult error(int code, String msg) {
        return new MyResult(code, msg, null);
    }
	/**
	 * <P>方法名称：error</P>
	 * <P>方法描述：失败 code:传入 msg:传入 data:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年6月15日</P>
	 */
	public static MyResult error(int code, String msg, Object data) {
		return new MyResult(code, msg, data);
	}
	
	
	//=============返回成功错误消息Map success:true code:"100" msg:"请求成功！"===========================
	/**
	 * <P>方法名称：getTrue</P>
	 * <P>方法描述：成功消息 msg:请求成功！ code:"100" success：true</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getTrue(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS,SUCCESS_SUCCESS);
		map.put(CODE, CODE_SUCCESS);
		map.put(MSG, MSG_SUCCESS);
		return map;
	}
	/**
	 * <P>方法名称：getTrue</P>
	 * <P>方法描述：成功消息 msg:传入 code:"100" success：true</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getTrue(String msg){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, SUCCESS_SUCCESS);
		map.put(CODE, CODE_SUCCESS);
		map.put(MSG, msg);
		return map;
	}
	/**
	 * <P>方法名称：getFalse</P>
	 * <P>方法描述：success：false code:"500" msg:请求失败</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getFalse(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, SUCCESS_ERROR);
		map.put(CODE, CODE_ERROR);
		map.put(MSG, MSG_ERROR);
		return map;
	}
	/**
	 * <P>方法名称：getFalse</P>
	 * <P>方法描述：success：false code:"500"  msg:传入</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getFalse(String msg){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, SUCCESS_ERROR);
		map.put(CODE, CODE_ERROR);
		map.put(MSG, msg);
		return map;
	}
	/**
	 * <P>方法名称：getCode</P>
	 * <P>方法描述：自定义消息</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getCode(String msg,String code){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(CODE, code);
		map.put(MSG, msg);
		return map;
	}
	/**
	 * <P>方法名称：getCustom</P>
	 * <P>方法描述：自定义消息</P>
	 * <P>创 建 人：zlw</P>
	 * <P>创建时间：2017年5月22日</P>
	 */
	public static Map<String, Object>  getCustom(String msg,boolean success){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(SUCCESS, success);
		map.put(MSG, msg);
		if(success){
			map.put(CODE,CODE_SUCCESS);
		}else {
			map.put(CODE,CODE_ERROR);
		}
		return map;
	}
	
	
}
