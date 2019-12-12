package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Equipmentlist;
import gprs.com.entity.UserEquipmentModle;
import gprs.com.entity.Wifipas;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-06-11
 */
public interface EquipmentlistMapper extends BaseMapper<Equipmentlist> {

	List<Equipmentlist> selectByuserId(@Param("userId")Integer userId,@Param("wifimac")String wifimac);

	void updateEquip(@Param("mac")String mac, @Param("deviceName")String deviceName);

	Equipmentlist selectbyMac(String mac);

	void upadteEleset(@Param("mac")String mac,@Param("eleset") Double eleset);

	List<Equipmentlist> selectByMilde(Integer userId);
	
	List<Equipmentlist> listByMac(String mac);

	void deletebymilde(Integer userId);

	void insertMidel(@Param("userId")Integer userId,@Param("id")Integer id,@Param("name")String name);

	UserEquipmentModle selectBymidel(@Param("userId")Integer userId,@Param("id")Integer id);

	void updateModel(@Param("userId")Integer userId,@Param("id")Integer id,@Param("name")String name);

	void deleteEquipment(@Param("userId")Integer userId,@Param("id")Integer id);

	void updateUmengtoken(@Param("userId")Integer userId, @Param("umToken")String umToken);

	void updatebymactype(@Param("mac")String mac, @Param("type")Integer type);

	void deleteEquipmentbyId(@Param("id")Integer id, @Param("userId") Integer userId);

	Wifipas selectBywifiMac(@Param("wifiMac")String wifiMac);

	void insertWifiMac(@Param("wifiMac")String wifiMac, @Param("wifipas")String wifipas);

	void updateWifiMac(@Param("wifipas")String wifipas, @Param("id")Integer id);

	/**
	 * @method selectMacList
	 * @apply 查询所有已绑定的设备（wifi、gprs）
	 * @return List<String>
	 */
	List<String> selectMacList();
}