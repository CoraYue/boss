package com.huying.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
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

import com.huying.bos.domain.base.Area;
import com.huying.bos.domain.base.Standard;
import com.huying.bos.service.base.AreaService;
import com.huying.bos.web.action.CommonAction;
import com.huying.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class AreaAction extends CommonAction<Area> {

	// spring创造的时候调用的是无参构造，光写无参也不行，里面写个方法，调用父类的方法
	public AreaAction() {
		super(Area.class);
	}

	// private Area model;
	// @Override
	// public Area getModel() {
	// if(model==null) {
	// model = new Area();
	// }
	// return model;
	// }

	@Autowired
	private AreaService areaService;

	// 属性驱动接收文件
	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action(value = "areaAction_importXLS", results = {
			@Result(name = "success", location = "/pages/base/area.html", type = "redirect") })
	public String importXLS() {

		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
			// 读取第一个工作簿
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			// 用来装Area的集合
			List<Area> list = new ArrayList<Area>();

			for (Row row : sheet) {
				// 跳过第一行
				if (row.getRowNum() == 0) {
					continue;
				}

				// 读取数据
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				// 截掉最后一个字符
				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);

				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
				String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);

				// 数组转字符
				String shortcode = PinYin4jUtils.stringArrayToString(headByString);

				// 封装area对象
				Area area = new Area();
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				area.setCitycode(citycode);
				area.setShortcode(shortcode);

				list.add(area);
			}

			// 执行保存
			areaService.save(list);

			// 释放资源
			hssfWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}*/

	@Action("areaAction_pageQuery")
	public String pageQuery() throws IOException {

		// 封装分页查询的条件
		Pageable pageable = new PageRequest(page - 1, rows);
		// 进行分页查询
		Page<Area> page = areaService.pageQuery(pageable);
		/*// 获取总Area数据条数
		long totalElements = page.getTotalElements();
		// 获取当前页面要显示的数据
		List<Area> list = page.getContent();
		// 由于目标页面需要的数据并不是page对象,素以需要手动封装json数据
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalElements);
		map.put("rows", list);*/

		// 忽略字段
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "subareas" });
		
		page2json(page, config);
		
		/*// 生成json数据
		String json = JSONObject.fromObject(map, config).toString();

		// 设置输出内容类型
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("application/json;charset=UTF-8");
		// 写出内容
		response.getWriter().write(json);
		*/
		return NONE;
	}
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	
	/**
	 * 增加分区里面的区域下拉框查找
	 * @return
	 * @throws IOException 
	 */
	@Action("areaAction_findAll")
	public String findAll() throws IOException {
		List<Area> list;
		if (StringUtils.isNotEmpty(q)) {
			//按条件查询
			list=areaService.finsByQ(q);
		}else {
			//查询所有
			Page<Area> page=areaService.pageQuery(null);
			list = page.getContent();
			
		}
		
		
		// 忽略字段
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "subareas" });
			
			list2json(list, config);
			 
			
			
		return NONE;
	}
	
	
	

}
