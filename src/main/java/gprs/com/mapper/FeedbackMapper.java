package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Feedback;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

	List<Feedback> getfeedbackList(@Param("start")int start, @Param("pageSize")Integer pageSize, @Param("search")String search,@Param("userId")Integer userId);

	int getfeedbackListPage(@Param("search")String search,@Param("userId")Integer userId);

}