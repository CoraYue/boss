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

import com.huying.bos.domain.base.FixedArea;
import com.huying.bos.domain.system.Menu;
import com.huying.bos.service.system.MenuService;
import com.huying.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("struts-default")
public class MenuAction  extends CommonAction<Menu>{

	public MenuAction() {
		super(Menu.class);
	}
	
	@Autowired
	private MenuService menuService;
	
	//查询所有菜单目录结构
	@Action(value="menuAction_findLevelOne")
	public String findLevelOne() throws IOException {
		List<Menu> list= menuService.findLevelOne();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "roles", "childrenMenus", "parentMenu" });
		list2json(list, jsonConfig);

		return NONE;
	}
	
	//新增菜单保存
	@Action(value = "menuAction_save", results = {@Result(name="success",location="/pages/system/menu.html",type="redirect")})
   public String save() {
		menuService.save(getModel());
		return SUCCESS;
	}
	
	//分页查询菜单
	@Action("menuAction_pageQuery")
	public String pageQuery() throws IOException {
		
		// Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
		Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) - 1, rows);

		// 进行分页查询
		Page<Menu> page = menuService.pageQuery(pageable);

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "childrenMenus","parentMenu","roles" });
		page2json(page, config);
		return NONE;

	}
	

}
