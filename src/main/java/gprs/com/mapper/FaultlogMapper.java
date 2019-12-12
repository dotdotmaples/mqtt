package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Faultlog;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface FaultlogMapper extends BaseMapper<Faultlog> {

	List<Faultlog> faulyloglist(@Param("start")int start, @Param("pageSize")Integer pageSize, @Param("equipmentNumber")String equipmentNumber);
	List<Faultlog> faulylogListByUserId(@Param("userId") Integer userId);
	int faulyloglistPage(@Param("equipmentNumber")String equipmentNumber);
	//删除
	void deletfaulylog(Integer id);
	//更改为已读状态
	void updatefaulyIsread(@Param("equipmentNumber")String equipmentNumber);
	//未读数量
	int getNumberFault(@Param("mac")String mac);
	//根据mac清楚故障
	void deletfaulylogbyMac(@Param("mac")String mac);
	
}