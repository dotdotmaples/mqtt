package gprs.com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Electricitylog;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface ElectricitylogMapper extends BaseMapper<Electricitylog> {

	
	//List<Electricitylog> selectElectricityloglist(@Param("equipmentNumber")String equipmentNumber,@Param("newdate")String newdate,@Param("yesterday")String yesterday);
	List<Electricitylog> selectElectricityloglist(@Param("equipmentNumber")String equipmentNumber,@Param("searchTime")String searchTime);
	int selectElectricityloglistPage(@Param("equipmentNumber")String equipmentNumber,@Param("searchTime")String searchTime);
	
	List<Map<String, Object>> selectSignalByMac(@Param("mac")List<String> mac, @Param("type")Integer type);
	
}