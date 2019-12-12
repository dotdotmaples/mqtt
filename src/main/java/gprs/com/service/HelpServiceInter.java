package gprs.com.service;


import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Help;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-06-01
 */
public interface HelpServiceInter extends IService<Help> {

	Object helpDdetails(Integer id);

	Object addOrUpdateHelp(Integer id, String content);
	
}
