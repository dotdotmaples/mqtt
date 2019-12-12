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
public class Faultlog extends Model<Faultlog> {

    private static final long serialVersionUID = 1L;

    /**
     * 故障记录
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 记录时间
     */
	private String recordTime;
    /**
     * 设备名称
     */
	private String equipmentName;
    /**
     * 设备编号
     */
	private String equipmentNumber;
    /**
     * 故障内容
     */
	private String content;
	/**
	 * 是否查看
	 */
	private Integer isread;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
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

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

}
