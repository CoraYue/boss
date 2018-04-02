package com.huying.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.take_delivery.Promotion;
import com.huying.bos.service.take_delivery.PromotionService;
import com.huying.bos.web.action.CommonAction;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends CommonAction<Promotion> {

	public PromotionAction() {
		super(Promotion.class);
	}

	@Autowired
	private PromotionService promotionService;

	// 属性驱动获取文件
	private File titleImgFile;
	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	// 属性驱动获取文件名
	private String titleImgFileFileName;



	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}
	@Action(value = "promotionAction_save", results = {
			@Result(name = "success", location = "/pages/take_delivery/promotion.html", type = "redirect"), @Result(name = "error", location = "/error.html",
                    type = "redirect")
 })
	public String save() {
		Promotion promotion = getModel();

		try {
			if (titleImgFile != null) {

				// 定义一个文件夹放图片
				String dirPath = "/upload";
				// 获取文件的绝对磁盘路径
				ServletContext context = ServletActionContext.getServletContext();
				String dirRealPath = context.getRealPath(dirPath);

				// 获取文件名的后缀
				// a.jpg --> 不加1--.jpg ,, 加1-- jpg
				String suffix = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
				// 使用uuid生成文件名
				String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

				// 绝对磁盘路径

				File destFile = new File(dirRealPath + "/" + fileName);
				FileUtils.copyFile(titleImgFile, destFile);
				promotion.setTitleImg("/upload/" + fileName);

			}

			promotion.setStatus("1");
			promotionService.save(promotion);
			return SUCCESS;

		} catch (IOException e) {
			e.printStackTrace();
			promotion.setTitleImg(null);
		}
		return ERROR;
	}
}
