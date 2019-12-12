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
public class Electricitylog extends Model<Electricitylog> {

    private static final long serialVersionUID = 1L;

    /**
     * 用电量
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 设备名称
     */
	private String equipmentName;
    /**
     * 设备编号
     */
	private String equipmentNumber;
    /**
     * 记录时间
     */
	private String logTime;
    /**
     * 用电量
     */
	private Integer electricNumber;
	/**
	 * 电压
	 */
	private Integer voltage;
	/**
	 * 电能
	 */
	private Integer energy;
	/**
	 * 漏电
	 */
	private Integer leakage;
	/**
	 * 谐波
	 */
	private Integer harmonic;
	/**
	 * 谐波
	 */
	private Integer temperature;
	/**
	 * gprs信号强度
	 */
	private Integer signal;


	public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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



	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public Integer getElectricNumber() {
		return electricNumber;
	}

	public void setElectricNumber(Integer electricNumber) {
		this.electricNumber = electricNumber;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Integer getLeakage() {
		return leakage;
	}

	public void setLeakage(Integer leakage) {
		this.leakage = leakage;
	}

	public Integer getHarmonic() {
		return harmonic;
	}

	public void setHarmonic(Integer harmonic) {
		this.harmonic = harmonic;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "Electricitylog [id=" + id + ", equipmentName=" + equipmentName + ", equipmentNumber=" + equipmentNumber
				+ ", logTime=" + logTime + ", electricNumber=" + electricNumber + ", voltage=" + voltage + ", energy="
				+ energy + ", leakage=" + leakage + ", harmonic=" + harmonic + ", temperature=" + temperature
				+ ", signal=" + signal + "]";
	}
	
	

}
