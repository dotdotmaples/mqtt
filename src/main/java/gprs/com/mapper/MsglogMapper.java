package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Msglog;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface MsglogMapper extends BaseMapper<Msglog> {

	Msglog newmsglog(String topic);

	List<Msglog> selectByall(@Param("start")int start, @Param("pageSize")Integer pageSize, @Param("searchdate")String searchdate, 
			@Param("topic1")String topic1, @Param("topic2")String topic2);

	int selectByallPage(@Param("searchdate")String searchdate, 
			@Param("topic1")String topic1, @Param("topic2")String topic2);

	void deletemsglog(@Param("id")int ids);

	/**
	 * 根据时间删除一个月之前的记录
	 * @param uppermouth
	 */
	void deletebydate(String uppermouth);

	Msglog newmsglogbydate(@Param("topic")String clientid, @Param("creatTime")String creatTime);

}