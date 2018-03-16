package com.huying.bos.web.action.base;

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

import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.base.SubArea;
import com.huying.bos.service.base.FixedAreaService;
import com.huying.bos.service.base.SubAreaService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea>{

	public FixedAreaAction() {
		super(FixedArea.class);
	}

	@Autowired
	private FixedAreaService fixedAreaService;

	@Action(value="fixedAreaAction_save",results= {@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
	public String save() {
		fixedAreaService.save(getModel());
		return SUCCESS;
	}
	
	//分页查询
		@Action("fixedAreaAction_pageQuery")
		public String pageQuery() throws IOException  {
			Pageable pageable= new PageRequest(page - 1, rows);
			
			//进行分页查询
			Page<FixedArea> page=fixedAreaService.pageQuery(pageable);
			
			// 忽略字段
					JsonConfig config = new JsonConfig();
					config.setExcludes(new String[] {"subareas" });
			page2json(page, config);
			return NONE;
			
		}


}
