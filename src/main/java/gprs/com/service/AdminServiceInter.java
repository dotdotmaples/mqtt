package gprs.com.service;


import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Admin;

/**
 * {@link gprs.com.service.Imp.AdminServiceImp}
 */
public interface AdminServiceInter extends IService<Admin>{

	/**
	 * <b>warnings：</b>
	 * <pre> 密码允许空字符</pre>
	 * <pre> 加密后的密码字段可以复用</pre>
	 * <pre> 未考虑密码首尾为空字符</pre>
	 */
	Object login(String account, String password);

	/**
	 * <b>warnings：</b>
	 * <pre> 密码允许空字符 </pre>
	 * <pre> 密码未被加密 </pre>
	 * <pre> 未考虑密码首尾为空字符 </pre>
	 * <pre>
	 */
	Object addAdmin(String account, String password);

	Object adminlist();

	/**
	 * <b>warnings：</b>
	 * <pre> 密码允许空字符</pre>
	 * <pre> 未考虑密码首尾为空字符 </pre>
	 */
	Object updatePassword(Integer id, String password);

	Object deleteAdmin(Integer id);

}
