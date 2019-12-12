package gprs.com.mapper;



import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Admin;

public interface AdminMapper extends BaseMapper<Admin>{

	Admin selectByAccount(String account);

	Admin getAdminbyToken(String token);

}
