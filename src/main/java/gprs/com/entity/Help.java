package gprs.com.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author ejar
 * @since 2018-06-01
 */
public class Help extends Model<Help> {

    private static final long serialVersionUID = 1L;

    /**
     * 1 操作指南 2 使用说明
     */
	private Integer id;
    /**
     * 内容
     */
	private String content;


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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
