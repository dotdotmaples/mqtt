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
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 手机号
     */
	private String tel;
    /**
     * 密码
     */
	private String password;
    /**
     * 昵称
     */
	private String nickName;
    /**
     * 邮箱
     */
	private String eMail;
    /**
     * 头像
     */
	private String img;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
