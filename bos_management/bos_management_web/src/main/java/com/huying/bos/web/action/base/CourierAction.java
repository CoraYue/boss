package com.huying.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.Courier;
import com.huying.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
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

}
