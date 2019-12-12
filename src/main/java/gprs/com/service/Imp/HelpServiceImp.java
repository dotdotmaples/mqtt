package gprs.com.service.Imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Help;
import gprs.com.mapper.HelpMapper;
import gprs.com.service.HelpServiceInter;
import gprs.tool.resulte.MyResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ejar
 * @since 2018-06-01
 */
@Service
public class HelpServiceImp extends ServiceImpl<HelpMapper, Help> implements HelpServiceInter {

	@Autowired
	HelpMapper helpMapper;
	
	@Override
	public Object helpDdetails(Integer id) {
		return MyResult.success(helpMapper.selectById(id));
	}

	@Override
	public Object addOrUpdateHelp(Integer id, String content) {
		if(id==null || id>2 || id<1){return MyResult.error("参数错误！");}
		if(content==null || "".equals(content)){return MyResult.error("请输入内容！");}
		Help help = helpMapper.selectById(id);
		if(help!=null){
			help.setContent(content);
			helpMapper.updateById(help);
		}else{
			Help help1 = new Help();
			help1.setId(id);
			help1.setContent(content);
			helpMapper.insert(help1);
		}
		return MyResult.success("操作成功！");
	}
	
}
