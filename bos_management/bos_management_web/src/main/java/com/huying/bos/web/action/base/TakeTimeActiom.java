package com.huying.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.TakeTime;
import com.huying.bos.service.base.TakeTimeService;
import com.huying.bos.web.action.CommonAction;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeActiom extends CommonAction<TakeTime>{

	public TakeTimeActiom() {
		super(TakeTime.class);
	}
	
	@Autowired
	private TakeTimeService takeTimeService;

	//查询所有的时间
	@Action("takeTimeAction_listajax")
	public String listajax() throws IOException {
		List<TakeTime> list=takeTimeService.findAll();
		list2json(list, null);
		return NONE;
	}
	
}
