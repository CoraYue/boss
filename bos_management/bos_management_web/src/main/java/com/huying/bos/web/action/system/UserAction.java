package com.huying.bos.web.action.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.huying.bos.domain.system.User;
import com.huying.bos.web.action.CommonAction;

@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("struts-default")
public class UserAction extends CommonAction<User>{

	public UserAction( ) {
		super(User.class);
	}

	//使用属性驱动获得客户输入的验证码
	private String checkCode;
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	
	//用户登录
	@Action(value="userAction_login",results= 
		{@Result(name="success",location="/index.html",type = "redirect")
	, @Result(name="login",location="/login.html",type = "redirect")})
	public String login() {
		
		System.out.println("login2.......");
		
		String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验验证码
		  if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkCode) && serverCode.equals(checkCode)) {
			
			  // 主体，获取当前用户
			  Subject subject = SecurityUtils.getSubject();
			  //创建令牌
			  AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
			  //执行登录
			  try {
				subject.login(token);
				//获取user存到session
				//方法的返回值由Realm中doGetAuthenticationInfo方法定义SimpleAuthenticationInfo对象的时候,第一个参数决定的
				User user = (User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				
				 return SUCCESS;
			} catch (UnknownAccountException e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("密码错误");
            } catch (Exception e) {
                // 用户名写错了
                e.printStackTrace();
                System.out.println("其他错误");
            }
			 
		}
		return LOGIN;
		
	}

	/**
	 * 退出登录
	 */
	@Action(value="userAction_logout",results= {@Result(name="success",location="/login.html",type="redirect")})
	public String logout() {
		
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		//清空session
		ServletActionContext.getRequest().getSession().removeAttribute("user");
		return SUCCESS;
	}
	
	
}
