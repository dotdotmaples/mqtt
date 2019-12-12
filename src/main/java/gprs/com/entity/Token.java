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
public class Token extends Model<Token> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String token;
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
