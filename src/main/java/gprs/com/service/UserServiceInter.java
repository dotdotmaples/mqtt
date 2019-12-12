package gprs.com.service;


import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.User;
import gprs.com.exception.MyException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface UserServiceInter extends IService<User> {

	//注册
	Object regUser(String tel, String code, String name, String password, String rePassword, String eMail) throws MyException;

	//登录
	Object userLogin(String account, String password);
	//修改用户头像或者昵称
	Object upadteUser(String img, String nickName,String eMail,String token);
	//修改密码
	Object updatePassword(String password, String newpassword, String repassword, String token);

	Object userInfo(String token);

	Object userlist(Integer pageNo, Integer pageSize, String search);

	Object backupdatePassword(String newpassword, String repassword, Integer uid);

	
}
