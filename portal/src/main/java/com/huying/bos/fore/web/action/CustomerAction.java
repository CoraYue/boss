package com.huying.bos.fore.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.bouncycastle.cms.RecipientInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.huying.crm.domain.Customer;
import com.huying.utils.MailUtils;
import com.huying.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	private Customer model;

	@Override
	public Customer getModel() {
		if (model == null) {
			model = new Customer();
		}
		return model;
	}
	


	//发送验证码
	@Action(value="customerAction_sendSMS")
	public String sendSMS() {
		//随机生成验证码
		String code = RandomStringUtils.randomNumeric(6);
		System.out.println(code);
		//缓存验证码
		ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
	/*	try {
			SmsUtils.sendSms(model.getTelephone(), code);
		} catch (ClientException e) {
			e.printStackTrace();
		}*/
		return NONE;
	}
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	//使用属性驱动获得客户输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	@Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html",
                    type = "redirect")})
	public String regist() {
		
		//1校验验证码
		String  serverCode= (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
		if(StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(serverCode) && serverCode.equals(checkcode)) {
			//注册
			WebClient.create(
                    "http://localhost:8180/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).post(model);
			//生成的验证码
			String activeCode = RandomStringUtils.randomNumeric(32);
			//存储验证码
		redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);
			
			String emailBody="感谢您激活本网站，请在24小时内点击链接<a href='http://localhost:8280/portal/customerAction_active.action?activeCode="+activeCode+"&telephone="+model.getTelephone()+"'>点此激活</a>完成激活";
			//发送激活邮件
			MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);
			
			return SUCCESS;
		}
		
		return ERROR;
	}
	
	//使用属性驱动获取激活码
	private String activeCode;
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	
	 @Action(value = "customerAction_active",
	            results = {
	                    @Result(name = "success", location = "/login.html",
	                            type = "redirect"),
	                    @Result(name = "error", location = "/signup-fail.html",
	                            type = "redirect")})

	public String active() {
		//对比激活码
		String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
		if(StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(activeCode) && serverCode.equals(activeCode)) {
			
		
			//激活
			WebClient.create(
                    "http://localhost:8180/crm/webService/customerService/active")
                    .type(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .accept(MediaType.APPLICATION_JSON).put(null);


			return SUCCESS;
			
		
				
		}
		return ERROR;
	}
	 
	 
	 @Action(value = "customerAction_login",
	            results = {
	                    @Result(name = "success", location = "/index.html",
	                            type = "redirect"),
	                    @Result(name = "error", location = "/login.html",
	                            type = "redirect"),
	                    @Result(name = "unactived", location = "/login.html",
	                            type = "redirect")})
	 public String login() {
		 String  serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		 if(StringUtils.isNotEmpty(serverCode)
	                && StringUtils.isNotEmpty(checkcode)
	                && serverCode.equals(checkcode)){
			 //校验用户是否激活
		     Customer customer = WebClient.create(
	                    "http://localhost:8180/crm/webService/customerService/isActived")
	                    .type(MediaType.APPLICATION_JSON)
	                    .query("telephone", model.getTelephone())
	                    .accept(MediaType.APPLICATION_JSON).get(Customer.class);
		     if(customer!=null && customer.getType() != null) {
		    	 if(customer.getType() ==1) {
		    		 //已激活
		    		 //登录
		    		Customer c=  WebClient.create(
	                            "http://localhost:8180/crm/webService/customerService/login")
	                            .type(MediaType.APPLICATION_JSON)
	                            .query("telephone", model.getTelephone())
	                            .query("password", model.getPassword())
	                            .accept(MediaType.APPLICATION_JSON)
	                            .get(Customer.class);
		    		
		    		if(c!=null) {
		    			ServletActionContext.getRequest().getSession().setAttribute("user", c);
		    			return SUCCESS;
		    		}else {
		    			return ERROR;
		    		}

		    	 }else {
		    		 //用户已注册但是没有激活
		    		 ServletActionContext.getRequest().getSession().setAttribute("msg", "您还没有激活");
		    		 return "unactived";
		    	 }
		     }

		 }
		 
		 return  ERROR;
	 }
	 
	 @Action("customerAction_checkTelephone")
	 public  String checkTelephone() throws IOException {
		 
		 Customer customer = WebClient.create(
                 "http://localhost:8180/crm/webService/customerService/findByTelephone")
                 .type(MediaType.APPLICATION_JSON)
                 .query("telephone", getModel().getTelephone())
                 .accept(MediaType.APPLICATION_JSON)
                 .get(Customer.class);
		 
		 PrintWriter writer = ServletActionContext.getResponse().getWriter();

		 if(customer!=null) {
			 writer.write("true");
		 }else {
			 writer.write("false");
		 }
		 return NONE;
	 }
	
}
