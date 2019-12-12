package gprs.com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.service.AdminServiceInter;

@Controller
public class AdminController {

	@Resource
	AdminServiceInter adminServiceInter;
	
	/**
	 * 登录
	 */
	@RequestMapping("admin/login")
	@ResponseBody
	public Object login(String account,String password){
		return adminServiceInter.login(account,password);
	}
	
	/**
	 * 添加账号
	 */
	@RequestMapping("back/admin/addAdmin")
	@ResponseBody
	public Object addAdmin(String account,String password){
		return adminServiceInter.addAdmin(account,password);
	}
	
	/**
	 * 管理员列表
	 */
	@RequestMapping("back/admin/adminlist")
	@ResponseBody
	public Object adminlist(){
		return adminServiceInter.adminlist();
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("back/admin/updatePassword")
	@ResponseBody
	public Object updatePassword(Integer id,String password){
		return adminServiceInter.updatePassword(id,password);
	}
	
	/**
	 * 删除账号
	 */
	@RequestMapping("back/admin/deleteAdmin")
	@ResponseBody
	public Object deleteAdmin(Integer id){
		return adminServiceInter.deleteAdmin(id);
	}
	
	
	@RequestMapping("/abcdef")
	@ResponseBody
	public Object test() {
		return "test";
	}
	
}
