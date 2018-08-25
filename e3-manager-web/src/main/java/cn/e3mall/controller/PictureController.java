package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.FastDFSClient;
import cn.e3mall.common.JsonUtils;

@Controller
public class PictureController {

	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	private String saveMultipicture(MultipartFile uploadFile) {
		try {
			//1.获取文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String ExtName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		    //2.创建一个FastDFS的客户端
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
		    //3.执行批量上传
			String string = fastDFSClient.uploadFile(uploadFile.getBytes(),ExtName);
			String url = IMAGE_SERVER_URL + string;
			
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			
			//将java对象转换成字符串
			String string2 = JsonUtils.objectToJson(result);
			return string2;
		} catch (Exception e) {
			e.printStackTrace();
			//5、返回map
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			//将java对象转换成字符串
			String string2 = JsonUtils.objectToJson(result);
			return string2;

		}
	}
}
