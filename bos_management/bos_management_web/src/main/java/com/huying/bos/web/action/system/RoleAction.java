package com.huying.bos.web.action.system;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.system.Permission;
import com.huying.bos.domain.system.Role;
import com.huying.bos.service.system.RoleService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("struts-default")
public class RoleAction extends CommonAction<Role>{

	public RoleAction() {
		super(Role.class);
	}
	
	@Autowired
	private RoleService roleService;
	
	@Action(value="roleAction_pageQuery")
	public String pageQuery() throws IOException {

		// Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
		Pageable pageable = new PageRequest(page - 1, rows);

		// 进行分页查询
		Page<Role> page = roleService.pageQuery(pageable);

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "users","permissions","menus" });
		page2json(page, config);
		return NONE;

	} 

}
