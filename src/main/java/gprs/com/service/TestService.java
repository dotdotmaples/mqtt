package gprs.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gprs.com.exception.MyException;
import gprs.com.mapper.GprsOnlineInfoMapper;

@Service
public class TestService {

    @Autowired
    GprsOnlineInfoMapper gprsOnlineInfoMapper;
    
    @Transactional(rollbackFor = MyException.class)
    public void tt() throws MyException {
        try {
            gprsOnlineInfoMapper.insertOrUpdateOne("tt", "tt", 1);
            int i = 1 / 0;
        } catch(Exception e) {
            throw new MyException("嗯 出错了");
        }
       
    }
    
    
    
}
