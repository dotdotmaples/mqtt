package gprs.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.User;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface UserMapper extends BaseMapper<User> {

	//根据手机号查询用户
	User getUserByPhone(String tel);

	List<User> selectall(@Param("start")int start, @Param("pageSize")Integer pageSize, @Param("search")String search);
	int selectallpage(@Param("search")String search);

}