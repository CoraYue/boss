package com.huying.bos.web.action.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.Courier;
import com.huying.bos.domain.base.Standard;
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
		
		Specification<Courier> specification=new Specification<Courier>() {
/*
			创建一个查询的where语句
			@param root : 根对象.可以简单的认为就是泛型对象
            * @param cb : 构建查询条件
            * @return a {@link Predicate}, must not be {@literal null}.*/
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				//取出客户端输入的数据，都被模型驱动收起来了，
				String courierNum = model.getCourierNum();
				String company = model.getCompany();
				String type = model.getType();
				Standard standard = model.getStandard();
				
				List<Predicate> list=new ArrayList<>();
				if (StringUtils.isNotEmpty(courierNum)) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class),courierNum);
					list.add(p1);
				}
				if (StringUtils.isNotEmpty(company)) {
					Predicate p2 = cb.like(root.get("company").as(String.class),"%"+company+"%");
					list.add(p2);
				}
				if (StringUtils.isNotEmpty(type)) {
					Predicate p3 = cb.equal(root.get("type").as(String.class),type);
					list.add(p3);
				}
				if (standard != null) {
					String name = standard.getName();
					if (StringUtils.isNotEmpty(name)) {
						Join<Object, Object> join = root.join("standrd");
						Predicate p4 = cb.equal(join.get("name").as(String.class), name);
						list.add(p4);
					}
				}
				//y用户没有输入查询条件
				if (list.size() ==0) {
					return null;
				}
				
				//用户输入了多条件查询，将多个条件进行组合
				Predicate[] arr= new Predicate[list.size()];
				list.toArray(arr);
				
				//用户输入了多少个条件,就让多少个条件同时都满足
				Predicate predicate = cb.and(arr);
				return predicate;
			}
		};
		
		
		
		
		
		
		 //封装分页条件
		Pageable pageable=new PageRequest(page - 1, rows);
		//进行分页查询
		Page<Courier> page= courierService.pageQuery(specification, pageable);
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
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	//作废快递员
	@Action(value="courierAction_batchDel",results= {
			@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String batchDel() {
		courierService.batchDel(ids);
		return SUCCESS;
	}

}
