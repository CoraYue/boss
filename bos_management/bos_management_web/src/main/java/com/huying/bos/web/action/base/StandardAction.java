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

import com.huying.bos.domain.base.Standard;
import com.huying.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("/")
@ParentPackage("struts-default")
@Scope("protortype")
@Controller
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
	private Standard standard =new Standard();

	@Autowired
	private StandardService standardService;

	@Override
	public Standard getModel() {
		
		return standard;
	}
	
	// 保存派送标准
    //Action中的value等价于以前struts.xml中<action>节点的name
    //Result中的name等价于以前struts.xml中<result>节点的name
    //Result中的location等价于以前struts.xml中<result>节点之间的内容
	@Action(value="standardAction_save",results= {@Result(name="success",location="/pages/base/standard.html",type="redirect")})
	public String save() {
		standardService.save(standard);
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
	@Action("standardAction_pageQuery")
	public String pageQuery() throws IOException {
		
		//封装分页查询的条件
		Pageable pageable=new PageRequest(page - 1, rows);
		//进行分页查询
		Page<Standard> page= standardService.pageQuery(pageable);
		//获取总数据条数
		long totalElements = page.getTotalElements();
		//获取当前页面要显示的数据
		List<Standard> list = page.getContent();
		//由于目标页面需要的数据并不是page对象,素以需要手动封装json数据
		Map<String, Object> map=new HashMap<>();
		map.put("total", totalElements);
		map.put("rows", list);
		//生成json数据
		String json = JSONObject.fromObject(map).toString();
		
		//设置输出内容类型
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset=UTF-8");
		//写出内容
		response.getWriter().write(json);
		return NONE;
	}
	
	//查询取派标准下拉框值
	@Action("standardAction_findAll")
	public String findAll() throws IOException {
		
		 // 调用业务层查询数据
	    List<Standard> list = standardService.findAll();
	    // 将数据转化为json字符串
	    String json = JSONArray.fromObject(list).toString();
	    // 设置输出内容类型
	    ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
	    // 输出内容
	    ServletActionContext.getResponse().getWriter().write(json);
		return NONE;
	}

}
