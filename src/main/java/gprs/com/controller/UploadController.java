package gprs.com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gprs.com.exception.MyException;
import gprs.tool.resulte.MyResult;
import gprs.tool.upload.PreservationFile;


@Controller
public class UploadController {

	
	/**
	 * @method  图片上传
	 * @apply 
	 * @param imgs base64位图片，引文逗号隔开（一张就行）
	 * @return
	 */
	@RequestMapping(value="/uploadImgbs")
	@ResponseBody
	private Object uploadImgbs(HttpServletRequest request,String imgs) {
		List<String> imageList = null; 
		if(imgs!=null){
			try {
				imageList = PreservationFile.uplodeBase64Image(request, "/upload/img/", imgs);
				return MyResult.success("上传成功", imageList.get(0));
			} catch (MyException e) {
				return  MyResult.error("上传失败");
			}
	       }else{
	    	   return  MyResult.error("上传失败");
	       }
	}
}
