package gprs.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.ExceptionController;
import gprs.com.service.FeedbackServiceInter;

/**
 * <p>
 *  前端控制器--反馈
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
@Controller
public class FeedbackFrontController extends ExceptionController{
	
	@Autowired
	FeedbackServiceInter feedbackServiceInter;
	/**
	 * 添加反馈
	 * @param content 内容
	 * @param userName 用户姓名
	 * @param userTel 电话
	 * @param eMail 邮箱
	 * @param token 
	 * @return
	 */
	@RequestMapping("front/feedback/addFeedback")
	@ResponseBody
	public Object addFeedback(String content,String userName,String userTel,String eMail,String token){
		return feedbackServiceInter.addFeedback(content,userName,userTel,eMail,token);
	}
	
	/**
	 * 前台管理 - 反馈列表
	 * @param search 用户名 内容模糊搜索，电话邮件详细搜索
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("front/feedback/userFeedbackList")
	@ResponseBody
	public Object userFeedbackList(String search,Integer pageNo,Integer pageSize,String token){
		return feedbackServiceInter.userFeedbackList(search,pageNo,pageSize,token);
	}
	
	/**
	 * 后台管理 - 反馈列表
	 * @param search 用户名 内容模糊搜索，电话邮件详细搜索
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("back/feedback/feedbackList")
	@ResponseBody
	public Object feedbackList(String search,Integer pageNo,Integer pageSize){
		return feedbackServiceInter.feedbackList(search,pageNo,pageSize);
	}

	/**
	 * 回复
	 * @param reply
	 * @param id
	 * @return
	 */
	@RequestMapping("back/feedback/reFeedback")
	@ResponseBody
	public Object reFeedback(String reply,Integer id){
		return feedbackServiceInter.reFeedback(reply,id);
	}
	
	
	
}
