package com.huying.bos.web.action.system;

import java.io.IOException;
import java.util.List;

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

import com.huying.bos.domain.system.Permission;
import com.huying.bos.service.system.PermissionService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("struts-default")
public class PermissionAction extends CommonAction<Permission> {

	public PermissionAction() {
		super(Permission.class);
	}

	@Autowired
	private PermissionService permissionService;

	// 权限管理分页查询
	@Action("permissionAction_pageQuery")
	public String pageQuery() throws IOException {

		
		Pageable pageable = new PageRequest(page - 1, rows);

		// 进行分页查询
		Page<Permission> page = permissionService.pageQuery(pageable);

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "roles" });
		page2json(page, config);
		return NONE;

	}

	// 权限的保存
	@Action(value="permissionAction_save",results= {@Result(name="success",location="/pages/system/permission.html",type="redirect")})
	public String save() throws IOException {
		permissionService.save(getModel());
		return SUCCESS;

	}
	
	//查询所有权限
	@Action(value="permissionAction_findAll")
	public String findAll() throws IOException {
		
		
		// 进行分页查询
		Page<Permission> page = permissionService.pageQuery(null);
		List<Permission> list = page.getContent();

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "roles" });
		list2json(list, config);
		return NONE;
	}

}
