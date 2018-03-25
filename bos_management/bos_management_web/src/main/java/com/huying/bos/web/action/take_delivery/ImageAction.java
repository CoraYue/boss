package com.huying.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport{

	//属性驱动获取文件
	private File imgFile;
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	
	//属性驱动获取文件名
	private String imgFileFileName;
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	@Action(value="imageAction_upload")
	public String upload() throws IOException {
		
		Map<String, Object> map=new HashMap<>();
		
		try {
			//定义一个文件夹放图片
			String dirPath="/upload";
			//获取文件的绝对磁盘路径
			ServletContext context = ServletActionContext.getServletContext();
			String dirRealPath = context.getRealPath(dirPath);
			
			//获取文件名的后缀
			//a.jpg  --> 不加1--.jpg  ,, 加1-- jpg  
			String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
			//使用uuid生成文件名
			String fileName = UUID.randomUUID().toString().replaceAll("-", "")+suffix;
			
			//绝对磁盘路径
			
			File destFile = new File(dirRealPath+"/"+fileName);
			FileUtils.copyFile(imgFile, destFile);
			
		String contextPath = context.getContextPath();
			
			map.put("error", 0);
			map.put("url",contextPath+"/upload/"+fileName );
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", e.getMessage());
		}
		
		//向客户端写回数据
		  String json = JSONObject.fromObject(map).toString();
	        HttpServletResponse response = ServletActionContext.getResponse();

	        response.setContentType("application/json;charset=utf-8");
	        response.getWriter().write(json);

		
		return NONE;
	}
}
