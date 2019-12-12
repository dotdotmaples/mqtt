package gprs.com.service.Imp;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Admin;
import gprs.com.mapper.AdminMapper;
import gprs.com.service.AdminServiceInter;
import gprs.tool.md5Tool.Md5Password;
import gprs.tool.resulte.MyResult;

@Service
public class AdminServiceImp extends ServiceImpl<AdminMapper, Admin> implements AdminServiceInter {

	@Autowired
	AdminMapper adminMapper;

	@Override
	public Object login(String account, String password) {
		if (account == null || "".equals(account)) {
			return MyResult.error("请输入账号");
		}
		if (password == null || "".equals(password)) {
			return MyResult.error("请输入密码");
		}
		Admin admin = adminMapper.selectByAccount(account);
		if (admin == null) {
			return MyResult.error("账号不存在");
		}
		try {
			String newPas = Md5Password.md5Encode(password);
			if (admin.getPassword().equals(newPas)) {
				Map<String, Object> map = new HashMap<>();
				String token = UUID.randomUUID().toString();
				admin.setToken(token);
				adminMapper.updateById(admin);
				map.put("token", token);
				map.put("account", account);
				return MyResult.success(map);
			} else {
				return MyResult.error("密码错误");
			}
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("内部错误");
		}
	}

	@Override
	public Object addAdmin(String account, String password) {
		if (account == null || "".equals(account)) {
			return MyResult.error("请输入账号");
		}
		if (password == null || "".equals(password)) {
			return MyResult.error("请输入密码");
		}
		Admin admin = adminMapper.selectByAccount(account);
		if (admin != null) {
			return MyResult.error("账号已存在");
		}
		try {
			String newPas = Md5Password.md5Encode(password);
			Admin admin1 = new Admin();
			admin1.setAccount(account);
			admin1.setPassword(password);
			admin1.setRoleId(1);
			adminMapper.insert(admin1);
			return MyResult.success();
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("内部错误");
		}
	}

	@Override
	public Object adminlist() {
		List<Admin> admins = adminMapper.selectList(null);
		return MyResult.success(admins);
	}

	@Override
	public Object updatePassword(Integer id, String password) {
		if (password == null || "".equals(password)) {
			return MyResult.error("请输入新密码！");
		}
		if (id == null) {
			return MyResult.error("参数错误！");
		}
		Admin admin = adminMapper.selectById(id);
		if (admin == null) {
			return MyResult.error("参数错误！");
		}
		try {
			String newPas = Md5Password.md5Encode(password);
			admin.setPassword(newPas);
			adminMapper.updateById(admin);
			return MyResult.success();
		} catch (UnsupportedEncodingException e) {
			return MyResult.error("内部错误！");
		}
	}

	@Override
	public Object deleteAdmin(Integer id) {
		if (id == null) {
			return MyResult.error("参数错误！");
		}
		adminMapper.deleteById(id);
		return MyResult.success();
	}

}
