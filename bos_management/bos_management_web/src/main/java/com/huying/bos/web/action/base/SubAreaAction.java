package com.huying.bos.web.action.base;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.IOException;

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

import com.huying.bos.domain.base.SubArea;
import com.huying.bos.service.base.SubAreaService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea>{

	public SubAreaAction() {
		super(SubArea.class);
	}
	
	@Autowired
	private SubAreaService subAreaService;

	@Action(value="subareaAction_save",results= {@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
	public String save() {
		subAreaService.save(getModel());
		return SUCCESS;
	}
	
	//分页查询
	@Action("subareaAction_pageQuery")
	public String pageQuery() throws IOException  {
		Pageable pageable= new PageRequest(page - 1, rows);
		
		//进行分页查询
		Page<SubArea> page=subAreaService.pageQuery(pageable);
		
		// 忽略字段
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[] {"fixedArea" });
		page2json(page, config);
		return NONE;
		
	}

}
