package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Electrical;
import gprs.com.entity.UserEquipmentModle;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
public interface ElectricalMapper extends BaseMapper<Electrical> {
	
	Integer deleteByClientId(@Param("clientId") String clientId);
	
	Integer updateState(@Param("entity") Electrical entity);

	Electrical selectbyclientid(@Param("clientid")String clientid, @Param("type")Integer type);

	void updateByState(@Param("state")int state,@Param("clientid")String clientid);

	List<Electrical> selecteleList(@Param("start")int start, @Param("pageSize")Integer pageSize, @Param("clientid")String clientid, @Param("state")Integer state);
	
	int selecteleListPage(@Param("clientid")String clientid, @Param("state")Integer state);

	Electrical selectbyclientidandstate(@Param("clientid")String clientid);

	List<UserEquipmentModle> getByMac(@Param("mac")String mac);

	void updateByMaconline(@Param("mac")String mac);
	void updateByMaconline1(@Param("mac")String mac);
	
	List<Electrical> selectlistbystate();

	Electrical selectbyclientMac(String mac);
	
	/**
	 * @method 全部下线
	 * @apply 
	 * @return
	 */
	void unOnline();
	/**
	 * @method 批量上线
	 * @apply 
	 * @param clientIds
	 * @return
	 */
	void online(@Param("clientIds")String clientIds);
	
}