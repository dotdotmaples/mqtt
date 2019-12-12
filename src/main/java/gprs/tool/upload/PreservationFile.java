package gprs.tool.upload;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gprs.com.exception.MyException;
import gprs.com.mapper.FaultlogMapper;



/**
 *@description  TODO 类
 *@date  2017年12月25日
 *@author  geYang
 **/
@Service
public class PreservationFile {
	
	/**
	 * @description TODO BASE64图片上传(多图)
	 * @param request
	 * @param path  上传地址
	 * @param imgDatas 图片
	 * @return
	 * @date 2017年12月25日下午2:17:41
	 * @author geYang
	 * @throws MyException 
	 **/
	public static List<String> uplodeBase64Image(HttpServletRequest request,String path,String imgDatas) throws MyException{
	    String[] imgArr = imgDatas.split(",");
	    List<String> list = new ArrayList<String>();
	    if(imgDatas==null){
	        System.out.println("BASE64图片上传为空");
	    } else {
	        if(imgArr.length>5){
	            throw new MyException("BASE64单次上传不能超过5个文件");
	        } else {
	            for(String imgData : imgArr){
	                list.add(UploadUtil.uplodeBase64Image(request, path, imgData));
	            }
	        }
	    }
	    return list;
	}
	
}
