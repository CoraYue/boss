package com.huying.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.Courier;
import com.huying.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Namespace("/")
@ParentPackage("struts-default")
@Scope("protortype")
@Controller
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

	private Courier model=new Courier();
	
	@Autowired
	private CourierService courierService;
	
	@Override
	public Courier getModel() {
		return model;
	}
	
	//保存快递员
	@Action(value="courierAction_save",results= {
			@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String save() {
		courierService.save(model);
		return SUCCESS;
	}
	
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	@Action("courierAction_pageQuery")
	public String pageQuery() throws IOException {
		 //封装分页条件
		Pageable pageable=new PageRequest(page - 1, rows);
		//进行分页查询
		Page<Courier> page= courierService.pageQuery(pageable);
		//获取总条数
		long totalElements = page.getTotalElements();
		//获取每页显示的数据
		List<Courier> list = page.getContent();
		//由于目标页面不需要page对象,所以要手动封装数据
		Map<String, Object> map=new HashMap<>();
		map.put("total", totalElements);
		map.put("rows", list);
		
		//把数据转化成json字符串
		JsonConfig config=new JsonConfig();
		//设置序列化时忽略的字段
	config.setExcludes(new String[] {"fixedAreas","takeTime"});
		
		String json = JSONObject.fromObject(map,config).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		return NONE;
	}

}
