package com.huying.bos.web.action.take_delivery;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.SubArea;
import com.huying.bos.domain.take_delivery.WayBill;
import com.huying.bos.service.take_delivery.WayBillService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class WayBillAction extends CommonAction<WayBill>{

	@Autowired
	private WayBillService wayBillService;
	
	public WayBillAction() {
		super(WayBill.class);
	}
	
	@Action(value="waybillAction_save")
	public String save() throws IOException {
		String result="1";
		try {
			wayBillService.save(getModel());
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		//向客户端写或操作结果
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(result);
		return NONE;
	}
	
	@Action(value="waybillAction_pageQuery")
	public String pageQuery() throws IOException {
Pageable pageable= new PageRequest(page - 1, rows);
		
		//进行分页查询
		Page<WayBill> page=wayBillService.pageQuery(pageable);
		page2json(page, null);
		return NONE;
	}

	

}
