package com.huying.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.huying.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

	private T model;
	private Class<T> clazz;

	public CommonAction(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getModel() {
		if(model==null) {
			try {
				model = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	//提取到父类之后，子类读取不到改成 把 private改成protected
	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page; 
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void page2json(Page<T> page,JsonConfig config) throws IOException {
		// 获取总Area数据条数
				long totalElements = page.getTotalElements();
				// 获取当前页面要显示的数据
				List<T> list = page.getContent();
				// 由于目标页面需要的数据并不是page对象,素以需要手动封装json数据
				Map<String, Object> map = new HashMap<>();
				map.put("total", totalElements);
				map.put("rows", list);

				String json;
				if(config!=null) {
				// 生成json数据
				 json = JSONObject.fromObject(map, config).toString();
				}else {
					 json = JSONObject.fromObject(map).toString();
				}
				// 设置输出内容类型
				HttpServletResponse response = ServletActionContext.getResponse();

				response.setContentType("application/json;charset=UTF-8");
				// 写出内容
				response.getWriter().write(json);
				
		
	}

}
