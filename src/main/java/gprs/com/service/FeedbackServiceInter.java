package gprs.com.service;


import com.baomidou.mybatisplus.service.IService;

import gprs.com.entity.Feedback;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface FeedbackServiceInter extends IService<Feedback> {

	/**
	 * 添加反馈
	 */
	Object addFeedback(String content, String userName, String userTel, String eMail, String token);
	/**
	 * 反馈列表-前端
	 */
	Object userFeedbackList(String search, Integer pageNo, Integer pageSize, String token);
	/**
	 * 反馈列表-后台
	 */
	Object feedbackList(String search, Integer pageNo, Integer pageSize);
	/**
	 * 管理员回复
	 */
	Object reFeedback(String reply, Integer id);
	
}
