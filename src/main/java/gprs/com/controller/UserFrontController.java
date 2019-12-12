package gprs.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.ExceptionController;
import gprs.com.exception.MyException;
import gprs.com.service.UserServiceInter;
import gprs.tool.resulte.MyResult;
import gprs.tool.verifyCode.VerifyCode;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Controller
public class UserFrontController extends ExceptionController{
	
	@Autowired
	UserServiceInter userServiceInter;
	
	
//	/**
//	 * 发送短信验证码
//	 * @param tel
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "user/getCode")
//	@ResponseBody
//	public Object getCode(String tel) throws MyException {
//		try {   
//            VerifyCode.sendVerifyCode(tel);
//            return MyResult.success("请在120秒内，输入短信验证码");
//        } catch (MyException e) {
//            return MyResult.getFalse(e.getMessage());
//        }
//	}
	
	/**
	 * 用户注册 
	 * @param tel 电话
	 * @param code 验证码
	 * @param name 昵称
	 * @param password 密码
	 * @param rePassword 确认密码
	 * @param eMail 邮箱
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("user/regUser")
	@ResponseBody
	public Object regUser(String tel,String code,String name,String password,String rePassword,String eMail) throws MyException{
		return  userServiceInter.regUser(tel,code,name,password,rePassword,eMail);
	}
	
	
	/**
	 *  用户登陆
	 * @param account 账号
	 * @param password 密码
	 * @return
	 */
	@RequestMapping("user/userLogin")
	@ResponseBody
	public Object userLogin(String account,String password){
		return userServiceInter.userLogin(account,password);
	}
	
	/**
	 * @method 用户信息
	 * @apply 
	 * @param token
	 * @return
	 */
	@RequestMapping("front/user/userInfo")
	@ResponseBody
	public Object userInfo(String token){
		return userServiceInter.userInfo(token);
	}

	
	/**
	 * @method 修改头像或昵称
	 * @apply 
	 * @param img 图片地址
	 * @param nickName 昵称
	 * @param eMail 邮箱
	 * @param token
	 * @return
	 */
	@RequestMapping("front/user/updateUser")
	@ResponseBody
	public Object upadteUser(String img,String nickName,String eMail,String token){
		return userServiceInter.upadteUser(img,nickName,eMail,token);
	}
	
	/**
	 * @method 修改密码
	 * @apply 
	 * @param password 密码
	 * @param newpassword 新密码
	 * @param repassword 重复
	 * @param token
	 * @return
	 */
	@RequestMapping("front/user/updatePassword")
	@ResponseBody
	public Object updatePassword(String password,String newpassword,String repassword,String token){
		return userServiceInter.updatePassword(password,newpassword,repassword,token);
	}

	
	/**
	 * @method 用户列表
	 * @apply 
	 * @param token
	 * @param pageNo
	 * @param pageSize
	 * @param search
	 * @return
	 */
	@RequestMapping("back/user/userlist")
	@ResponseBody
	public Object userlist(String token,Integer pageNo,Integer pageSize,String search){
		return userServiceInter.userlist(pageNo,pageSize,search);
	}
	
	
	@RequestMapping("back/user/updatePassword")
	@ResponseBody
	public Object updatePassword(String newpassword,String repassword,Integer uid){
		return userServiceInter.backupdatePassword(newpassword,repassword,uid);
	}
	
}
