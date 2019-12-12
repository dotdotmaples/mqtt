package gprs.com.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public class Feedback extends Model<Feedback> {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈记录表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 内容
     */
	private String content;
    /**
     * 用户姓名
     */
	private String userName;
    /**
     * 电话
     */
	private String userTel;
    /**
     * 邮箱
     */
	private String eMail;
    /**
     * 回复
     */
	private String reply;
	/**
	 * 用户id
	 */
	private Integer userId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
