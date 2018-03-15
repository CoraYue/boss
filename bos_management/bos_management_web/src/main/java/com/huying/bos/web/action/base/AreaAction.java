package com.huying.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.base.Area;
import com.huying.bos.service.base.AreaService;
import com.huying.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller 
public class AreaAction extends ActionSupport implements ModelDriven<Area>{

	private Area model;
	@Override
	public Area getModel() {
		if(model==null) {
			model = new Area();
		}
		return model;
	}
	
	@Autowired
	private AreaService areaService;
	
	
	//属性驱动接收文件
	private  File  file;
	
	public void setFile(File file) {
		this.file = file;
	}
	
	@Action(value="areaAction_importXLS",results= {@Result(name="success",location="/pages/base/area.html", type="redirect")})
	public String importXLS() {
		
		try {
			HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(file));
			//读取第一个工作簿
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			//用来装Area的集合
			List<Area> list=new ArrayList<Area>();
			
			for (Row row : sheet) {
				//跳过第一行
				if(row.getRowNum() == 0) {
					continue;
				}
				
				//读取数据
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//截掉最后一个字符
				province=province.substring(0, province.length()-1);
				city=city.substring(0, city.length()-1);
				district=district.substring(0, district.length()-1);
				
				String citycode = PinYin4jUtils.hanziToPinyin(city,"");
				String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
				
				//数组转字符
				String shortcode = PinYin4jUtils.stringArrayToString(headByString);
				
				
				//封装area对象
				Area area=new Area();
				area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);
                
				list.add(area);
			}
			
			//执行保存
			areaService.save(list);
			
			 
			//释放资源
			hssfWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS ;
	}

}
