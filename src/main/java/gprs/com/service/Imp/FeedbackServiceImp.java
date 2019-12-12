package gprs.com.service.Imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import gprs.com.entity.Feedback;
import gprs.com.mapper.FeedbackMapper;
import gprs.com.mapper.TokenMapper;
import gprs.com.service.FeedbackServiceInter;
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
public class FeedbackServiceImp extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackServiceInter {

	@Autowired
	FeedbackMapper feedbackMapper;
	@Autowired
	TokenMapper tokenMapper;
	
	
	/**
	 * 添加反馈
	 * @return 
	 */
	@Override
	public Object addFeedback(String content, String userName, String userTel, String eMail, String token) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		if(content==null){return MyResult.error("请输入反馈内容");}
		if(userName==null){return MyResult.error("请输入姓名");}
		if(userTel==null){return MyResult.error("请输入电话");}
		if(eMail==null){return MyResult.error("请输入邮箱");}
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p1 = Pattern.compile(regEx1);
		Matcher m1 = p1.matcher(eMail);
		if (!m1.matches()){
			return MyResult.error("邮箱格式错误！");
		}
		Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		Matcher m = p.matcher(userTel);
		if (m.matches() == false) {return MyResult.getFalse("手机格式错误!");}
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.seteMail(eMail);
		feedback.setUserName(userName);
		feedback.setUserId(userId);
		feedback.setUserTel(userTel);
		feedbackMapper.insert(feedback);
		return MyResult.success("添加成功！");
	}
	
	/**
	 * 反馈列表 -前端
	 */
	@Override
	public Object userFeedbackList(String search, Integer pageNo, Integer pageSize, String token) {
		Integer userId = tokenMapper.getUserIdbyToken(token);
		return feedbacklist(search,pageNo,pageSize,userId);
	}
	

	/**
	 * 反馈列表
	 */
	@Override
	public Object feedbackList(String search, Integer pageNo, Integer pageSize) {
		return feedbacklist(search,pageNo,pageSize,null);
	}

	/**
	 * 反馈列表
	 * @param search
	 * @param pageNo
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Object feedbacklist(String search, Integer pageNo, Integer pageSize,Integer userId){
		pageNo=pageNo==null?1:pageNo;
		pageSize=pageSize==null?10:pageSize;
		int start = (pageNo-1)*pageSize;
		List<Feedback> feedbacks = feedbackMapper.getfeedbackList(start,pageSize,search,userId);
		int t =0;
		if(feedbacks.size()>0){
			t = feedbackMapper.getfeedbackListPage(search,userId);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("pageTotal", (t-1+pageSize)/pageSize);
		map.put("total", t);
		map.put("feedbacks", feedbacks);
		return MyResult.success(map);
	}

	/**
	 * 管理员回复
	 */
	@Override
	public Object reFeedback(String reply, Integer id) {
		if(reply==null || "".equals(reply)){return MyResult.error("请输入回复内容");}
		if(id==null || id<1){return MyResult.error("参数错误");}
		Feedback feedback = feedbackMapper.selectById(id);
		if(feedback==null){
			return MyResult.error("该评论不存在！");
		}
		feedback.setReply(reply);
		feedbackMapper.updateById(feedback);
		return MyResult.success("回复成功！");
	}


	
}
