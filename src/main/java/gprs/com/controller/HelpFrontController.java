package gprs.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.service.HelpServiceInter;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ejar
 * @since 2018-06-01
 */
@Controller
public class HelpFrontController {
	
	@Autowired
	HelpServiceInter helpServiceInter;
	
	/**
	 * id 1 操作指南 2 使用说明
	 * @param id
	 * @return
	 */
	@RequestMapping("help/helpDetails")
	@ResponseBody
	public Object helpDetails(Integer id){
		return helpServiceInter.helpDdetails(id);
	}
	
	/**
	 * 添加或修改
	 * @param id
	 * @param content
	 * @return
	 */
	@RequestMapping("back/help/addOrUpdateHelp")
	@ResponseBody
	public Object addOrUpdateHelp(Integer id,String content){
		return helpServiceInter.addOrUpdateHelp(id,content);
	}
}
