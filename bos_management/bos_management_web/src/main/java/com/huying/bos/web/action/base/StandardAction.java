package com.huying.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.Standard;
import com.huying.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

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
	@Action(value="standardAction_save",results= {@Result(name="success",location="/pages/base/standard.html")})
	public String save() {
		standardService.save(standard);
		return SUCCESS;
	}

}
