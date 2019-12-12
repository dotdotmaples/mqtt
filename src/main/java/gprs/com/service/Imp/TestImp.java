package gprs.com.service.Imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import gprs.com.mapper.*;
import gprs.com.service.TestInt;


@Service
public class TestImp implements TestInt{

	@Resource
	TestMapper TestMapper;
	
	@Override
	public Object xx() {
		return TestMapper.xx();
	}

	

}
