package gprs.com.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;

import gprs.com.entity.Token;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ejar
 * @since 2018-05-28
 */
public interface TokenMapper extends BaseMapper<Token> {

	
	Token queryByUserId(Integer id);

	Integer getUserIdbyToken(String token);
}