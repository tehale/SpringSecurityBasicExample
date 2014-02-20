package com.spring.security;

import java.util.Iterator;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	private static final String USER_OAUTH_APPROVAL = "user_oauth_approval";
	private static final String SESSION_IDENTIFIER = "AUTHENTICATION_PRINCIPAL";
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(Model model,HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		System.out.println("Inside the welcome");
		if(session !=null ){
			Object username= session.getAttribute(SESSION_IDENTIFIER);
			request.setAttribute("auth",false);
			System.out.println(username);
			model.addAttribute("username",username.toString());			
			Object approval= session.getAttribute(USER_OAUTH_APPROVAL);
			if(approval==null || approval.toString().isEmpty()){
			 //redirect to user consent filter
			 return "forward:/userconsent";
			}
		}
		model.addAttribute("message", "Spring Security says Hello !");
		return "hello";
	}

	
	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		model.addAttribute("userData",new UserCredential());
		return "login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLoginSuccesPage(Model model,HttpServletRequest request,@ModelAttribute UserCredential userData) {
		if(userData.getusername().equals("user")&&userData.getPassword().equals("user")){
			HttpSession httpSession=request.getSession(false);
			String principal=request.getParameter("j_username");
			httpSession.setAttribute("SESSION_IDENTIFIER", principal);
			//TODO - forward to original request
			return null;
		}else{
			return "login";
		}
		
	}*/
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogoutPage(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "logout";
	}
	@RequestMapping(value="/userconsent",method=RequestMethod.GET)
	public String getUserconsent(HttpServletRequest req,HttpServletResponse res,Model model){
		System.out.println("Inside the userConsent");
		HttpSession session=req.getSession(false);
		String userauthen= (String) session.getAttribute("SESSION_IDENTIFIER");
		boolean approval=(boolean) req.getAttribute("auth");
		System.out.println(userauthen+approval);
		model.addAttribute("scope",userauthen);
		model.addAttribute("auth", approval);
		return "userconsent";
		
	}
}
