package gprs.com.service.Imp;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Token;
import gprs.com.entity.User;
import gprs.com.exception.MyException;
import gprs.com.mapper.TokenMapper;
import gprs.com.mapper.UserMapper;
import gprs.com.service.UserServiceInter;
import gprs.tool.md5Tool.Md5Password;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserServiceInter {

	@Autowired
	UserMapper userMapper;
	@Autowired
	TokenMapper tokenMapper;
	
	/**
	 * 注册
	 * @throws MyException 
	 */
	@Override
	public Object regUser(String tel, String code, String name, String password, String rePassword, String eMail) throws MyException {
		Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		Matcher m = p.matcher(tel);
		if (m.matches() == false) {return MyResult.getFalse("手机格式错误!");}
		if(password==null){return MyResult.getFalse("请输入密码");}
		if(!password.equals(rePassword)){return MyResult.getFalse("两次输入密码不一致");}
//		if(eMail==null || "".equals(eMail)){
//			throw new MyException("请输入邮箱！");
//		}
//		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//		Pattern p1 = Pattern.compile(regEx1);
//		Matcher m1 = p1.matcher(eMail);
//		if (!m1.matches()){
//			MyResult.getFalse ("邮箱格式错误！");
//		}
		//验证码验证
//		VerifyCode.checkVerifyCode(code,tel);
		User user1 = userMapper.getUserByPhone(tel);
		if(user1!=null){
			return MyResult.getFalse("该账号已经注册");
		}
		
		User user = new User();
		try {
			String pwd = Md5Password.md5Encode(password);
			user.setPassword(pwd);
		} catch (UnsupportedEncodingException e) {
			return MyResult.getFalse("内部错误");
		}
		user.setTel(tel);
		user.setNickName(name);
		user.setEMail(eMail);
		userMapper.insert(user);
		return MyResult.success();
	}


	/**
	 * 登录
	 */
	@Override
	public Object userLogin(String account, String password) {
		if(account==null || "".equals(account)){return MyResult.error("请输入账号！");}
		if(password==null || "".equals(password)){return MyResult.error("请输入密码！");}
		User user = userMapper.getUserByPhone(account);
		if(user==null){
			return MyResult.error("账号不存在！");
		}
		try {
			String pas = Md5Password.md5Encode(password);
			if(!pas.equals(user.getPassword())){
				return MyResult.error("密码错误");
			}
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("内部错误");
		}
		Token token = tokenMapper.queryByUserId(user.getId());
		String token2 = UUID.randomUUID().toString();
		if(token==null){
			Token token1 = new Token();
			token1.setToken(token2);
			token1.setUserId(user.getId());
			tokenMapper.insert(token1);
		}else{
			token.setToken(token2);
			tokenMapper.updateById(token);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("token", token2);
		return MyResult.success("登录成功", map);
	}


	/**
	 * 修改头像或昵称
	 */
	@Override
	public Object upadteUser(String img, String nickName,String eMail,String token) {
		if(img==null && nickName==null && eMail==null){
			return MyResult.error("参数错误！");
		}
		Integer userId = tokenMapper.getUserIdbyToken(token);
		User user = userMapper.selectById(userId);
		if(img!=null){
			user.setImg(img);
		}
		if(nickName!=null){
			user.setNickName(nickName);
		}
		if(eMail!=null){
			String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern p1 = Pattern.compile(regEx1);
			Matcher m1 = p1.matcher(eMail);
			if (!m1.matches()){
				return MyResult.error("邮箱格式错误！");
			}
			user.setEMail(eMail);
		}
		userMapper.updateById(user);
		return MyResult.success("修改成功！");
	}


	/**
	 * 修改密码
	 */
	@Override
	public Object updatePassword(String password, String newpassword, String repassword, String token) {
		if(password==null){return MyResult.error("请输入密码！");}
		if(newpassword==null){return MyResult.error("请输入新密码！");}
		if(!newpassword.equals(repassword)){return MyResult.getFalse("两次输入密码不一致");}
		Integer userId = tokenMapper.getUserIdbyToken(token);
		User user = userMapper.selectById(userId);
		try {
			String pwd = Md5Password.md5Encode(password);
			if(!pwd.equals(user.getPassword())){
				return MyResult.error("密码错误！");
			}
			String newpwd = Md5Password.md5Encode(newpassword);
			user.setPassword(newpwd);
			userMapper.updateById(user);
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("加密错误！");
		}
		return MyResult.success("修改成功！");
	}


	@Override
	public Object userInfo(String token) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		User user = userMapper.selectById(userId);
		return MyResult.success(user);
	}


	@Override
	public Object userlist(Integer pageNo, Integer pageSize, String search) {
		pageNo=pageNo==null?1:pageNo;
		pageSize=pageSize==null?10:pageSize;
		int start = (pageNo-1)*pageSize;
		List<User> users = userMapper.selectall(start,pageSize,search);
		int t = 0;
		if(users.size()>0){
			t = userMapper.selectallpage(search);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("users", users);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("total", t);
		map.put("pageTotal", (t-1+pageSize)/pageSize);
		return MyResult.success(map);
	}

	@Override
	public Object backupdatePassword(String newpassword,String repassword,Integer uid){
		if(newpassword==null){return MyResult.error("请输入新密码！");}
		if(!newpassword.equals(repassword)){return MyResult.getFalse("两次输入密码不一致");}
		User user = userMapper.selectById(uid);
		try {
			String newpwd = Md5Password.md5Encode(newpassword);
			user.setPassword(newpwd);
			userMapper.updateById(user);
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("加密错误！");
		}
		return MyResult.success("修改成功！");
		
	}
	
}
