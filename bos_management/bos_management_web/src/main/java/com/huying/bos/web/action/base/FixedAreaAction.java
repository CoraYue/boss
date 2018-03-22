package com.huying.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;
import com.huying.bos.service.base.FixedAreaService;
import com.huying.bos.service.base.SubAreaService;
import com.huying.bos.web.action.CommonAction;
import com.huying.crm.domain.Customer;

import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

	public FixedAreaAction() {
		super(FixedArea.class);
	}

	@Autowired
	private FixedAreaService fixedAreaService;

	@Action(value = "fixedAreaAction_save", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String save() {
		fixedAreaService.save(getModel());
		return SUCCESS;
	}

	// 分页查询
	@Action("fixedAreaAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);

		// 进行分页查询
		Page<FixedArea> page = fixedAreaService.pageQuery(pageable);

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "subareas","couriers" });
		page2json(page, config);
		return NONE;

	}

	@Action(value = "fixedAreaAction_findUnAssociatedCustomers")
	public String findUnAssociatedCustomers() throws IOException {
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
				.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		list2json(list, null);
		return NONE;
	}

	@Action(value = "fixedAreaAction_findAssociatedCustomers")
	public String findAssociatedCustomers() throws IOException {
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.query("fixedAreaId", getModel().getId())
				.getCollection(Customer.class);
		list2json(list, null);
		return NONE;
	}
	
	private Long[] customerIds;
	public void setCustomerIds(Long[] customerIds) {
		this.customerIds = customerIds;
	}
	private Long[] uncustomerIds;
	public void setUncustomerIds(Long[] uncustomerIds) {
		this.uncustomerIds = uncustomerIds;
	}
	
	//关联客户
	@Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
 
	public String assignCustomers2FixedArea() throws IOException {
		if(customerIds!=null) {
		WebClient
				.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.query("fixedAreaId", getModel().getId())
				.query("customerIds", customerIds)
				.put(null);
		}
		if(uncustomerIds!=null) {
			WebClient
			.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea2")
			.type(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.query("uncustomerIds", uncustomerIds)
			
			.put(null);
		}
		return SUCCESS;
	}
	//使用属性驱动获取分区id
	private Long[] subAreaIds;
	public void setSubAreaIds(Long[] subAreaIds) {
		this.subAreaIds = subAreaIds;
	}
	
	 // 关联分区
    @Action(value = "fixedAreaAction_assignSubAreas2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String assignSubAreas2FixedArea() throws IOException {
        fixedAreaService.assignSubAreas2FixedArea(getModel().getId(),
                subAreaIds);

        return SUCCESS;
    }
	
   //接收页面传过来的参数
	private Long courierId;
	private Long takeTimeId;
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Long takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	
	//关联快递员
	@Action(value = "fixedAreaAction_associationCourierToFixedArea",results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String associationCourierToFixedArea() throws IOException {
		fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
		return SUCCESS;
	}
}
