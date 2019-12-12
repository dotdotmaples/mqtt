package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import gprs.com.entity.GprsOnlineInfo;

@Mapper
public interface GprsOnlineInfoMapper {

    /**
     * @method insertOrUpdateOne
     * @apply 设备等待匹配，存在则修改，不存在则插入，imei主键
     * @param imei 设备编号
     * @param startTime 开始时间
     * @param state 匹配状态，1是0否
     * @return
     */
    int insertOrUpdateOne(@Param("imei")String imei, @Param("startTime")String startTime, @Param("state")Integer state);
    
    /**
     * @method selectImeiList
     * @apply gprs所有已匹配设备编号
     * @param state 匹配状态，1是0否
     * @param time 开始时间
     * @return
     */
    List<String> selectImeiList(@Param("state")Integer state, @Param("time")String time, @Param("userId")Integer userId);
    
    /**
     * @method selectListByImei
     * @apply 根据设备编码查询绑定信息(正常情况只会有一个)
     * @param imei
     * @return
     */
    List<GprsOnlineInfo> selectListByImei(@Param("imei")String imei);

	int updateStateByImei(@Param("imei")String imei, @Param("state")int state);
    
}
