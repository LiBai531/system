package com.physics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ws.security.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.util.EmailMessage;
import com.physics.util.MySessionContext;
import com.physics.util.VerificationCode;
import com.physics.util.JsonConvertor;
import com.physics.util.RandomPassword;
import com.physics.util.UserSession;
import com.physics.vm.NewsVM;
import com.physics.vm.PageVM;
import com.physics.vm.UserVM;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.Mail;
import com.physics.entity.MenuFirst;
import com.physics.entity.MenuSecond;
import com.physics.entity.News;
import com.physics.entity.User;
import com.physics.service.base.ExperimentInfoService;
import com.physics.service.base.MenuFirstService;
import com.physics.service.base.MenuSecondService;
import com.physics.service.base.NewsService;
import com.physics.service.base.UserService;

@Controller
@RequestMapping("/rbac")
public class UserController {

	@Resource(name="userService")
	private UserService userService;
	@Resource(name="experimentInfoService")
	private ExperimentInfoService experimentInfoService;
	@Resource(name="newsService")
	private NewsService newsService;
	@Resource(name="menuFirstService")
	private MenuFirstService menuFirstService;
	@Resource(name="menuSecondService")
	private MenuSecondService menuSecondService;
	
	
	/**
	 * 登陆系统
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/user_login")
	public String UserLogin(HttpSession session,HttpServletRequest request, Model model){
		
		String inputCode = request.getParameter("userCheckNum");	//用户输入的验证码
		String sRand = (String) session.getAttribute("randCheckCode");	//正确的验证码
		if(StringUtils.isEmpty(sRand)){
			model.addAttribute("theError", "请刷新验证码！");
			return "state/1";
		}else if(!sRand.equalsIgnoreCase(inputCode)){
			model.addAttribute("theError", "验证码错误！");
			return "state/2";
		}else{
			session.removeAttribute("randCheckCode");
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		List<News> newsList = newsService.getAllNews();
		List<NewsVM> newsVMs = new ArrayList<NewsVM>();
		for (News news : newsList) {
			if (news.getType() == 2) {
				continue;
			}
			NewsVM newsVM = new NewsVM(news);
			newsVMs.add(newsVM);
		}
		model.addAttribute("newsList", newsVMs);
		
		User user = userService.getUserByLoginId(username);
		if (user != null) {
			if (user.getState().equals("1")) {
				if (password.equals(user.getInitPassword())) {
					String ip = request.getRemoteAddr();
					java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
				    String s = format1.format(new Date());
				    session.setAttribute("loginId", user.getLoginIP());
				    session.setAttribute("loginTime", user.getLoginTime());
				    user.setLoginIP(ip);
				    user.setLoginTime(s);
				    
				    userService.saveUser(user);
				    
				    //获得菜单栏信息
				    List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(user.getUserType());
				    session.setAttribute("menu", menuFirsts);
				    
				    session.setAttribute("userSession", user);
					session.setMaxInactiveInterval(60*40);
					model.addAttribute("state", user.getState());
					
					
					return "/userMain/userMain";
					
				} else {
					model.addAttribute("theError", "密码错误");
					return "state/4";
				}
			} else if(user.getState().equals("2")){
				if (password.equals(user.getPassword())) {
					String ip = request.getRemoteAddr();
					java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
				    String s = format1.format(new Date());
				    
				    user.setLoginIP(ip);
				    user.setLoginTime(s);
				    
				    userService.saveUser(user);
				    
				    //获得菜单栏信息
				    List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(user.getUserType());
				    session.setAttribute("menu", menuFirsts);
				    
					session.setAttribute("userSession", user);
					session.setMaxInactiveInterval(60*40);
					model.addAttribute("state", user.getState());
					
					return "/userMain/userMain";
				} else {
					model.addAttribute("theError", "密码错误");
					return "state/4";
				}
			}
			model.addAttribute("theError", "密码错误");
			return "state/4";
		}
		session.setAttribute("errorCode", 2);
		model.addAttribute("theError", "用户不存在！");
		return "state/3";
	}
	
	/**
	 * 登出系统
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/logout")
	public String userLogout(HttpSession session,HttpServletRequest request, Model model){
		
		session.setAttribute("userSession", null);
		return "";
	}
	
	/**
	 * 管理员  用户管理页面
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUserManager")
	public String getUserManager(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String userType = user.getUserType();
		if ("3".equals(userType)) {
			return "rbac/userManager";
		} else {
			return "error";
		}
		
	}
	
	
	/**
	 * 获取用户信息
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	public String getUserInfo(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		UserVM userVM = new UserVM(user);
		model.addAttribute("user", userVM);
		
		return "rbac/userInfo";
		
	}
	
	@RequestMapping("/main")
	public String getMainPage(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		if (user != null) {
			UserVM userVM = new UserVM(user);
			model.addAttribute("user", userVM);
		}
		return "theMain";
		
	}
	
	@RequestMapping("/isAlreadyLogin")
	public String isAlreadyLogin(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		if (user != null) {
			
		    userService.saveUser(user);
		    
		    //获得菜单栏信息
		    List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(user.getUserType());
		    session.setAttribute("menu", menuFirsts);
		    
		    session.setAttribute("userSession", user);
			session.setMaxInactiveInterval(60*40);
			model.addAttribute("state", user.getState());
			
			
			return "/userMain/userMain";
		}
		return "error";
		
	}
	
	@RequestMapping("/AlreadyLogin")
	@ResponseBody
	public String AlreadyLogin(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		if (user != null) {
			return "yes";
		}
		return "no";
		
	}
	
	@RequestMapping("/resetUser")
	@ResponseBody
	public String resetUser(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String userType = user.getUserType();
		if ("3".equals(userType)) {
			String id = request.getParameter("id");
			User user2 = userService.getUser(id);
			String pString = RandomPassword.getRandomPassword();
			user2.setInitPassword(pString);
			user2.setState("1");
			
			userService.saveUser(user2);
			
			return "success";
		} else {
			return "error";
		}
		
	}
	
	/**
	 * 修改用户信息
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/changeUserInfo")
	@ResponseBody
	public String changeUserInfo(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String id = request.getParameter("user_loginId");
		String email = request.getParameter("user_Email");
		String name = request.getParameter("user_name");
		
		if (id.equals(user.getLoginId())) {
			//TODO  邮箱唯一性验证
			user.setEmail(email);
			user.setName(name);
			
			userService.saveUser(user);
			return "修改成功";
		}
		return "非法操作";
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public String addUser(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String userType = user.getUserType();
		if ("3".equals(userType)) {
			User user2 = new User();
			
			String userName = request.getParameter("userName");
			String loginId = request.getParameter("loginId");
			String userType2 = request.getParameter("userType");
			
			user2.setInitPassword(RandomPassword.getRandomPassword());
			user2.setLoginId(loginId);
			user2.setName(userName);
			user2.setUserType(userType2);
			user2.setState("1");
			user2.setPassword("test");
			
			userService.saveUser(user2);
			
			return "success";
		} else {
			return "error";
		}
		
	}
	
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String userType = user.getUserType();
		if ("3".equals(userType)) {
			String id = request.getParameter("id");
			
			userService.deleteUser(id);
			
			return "success";
		} else {
			return "error";
		}
		
	}
	
	@RequestMapping("/tologin")
	public String tologin(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		model.addAttribute("userName", user.getName());
		model.addAttribute("userId", user.getId());
		String userType = user.getUserType();
		if ("1".equals(userType)) {
			return "studentMain";
		} else {
			return "teacherMain";
		}
		
	}
	
	
	@RequestMapping("/changePasswordPage")
	public String changePasswordPage(HttpSession session,HttpServletRequest request, Model model){
			return "rbac/password";
	}
	
	@RequestMapping("/leadPage")
	public String leadPage(HttpSession session,HttpServletRequest request, Model model){
		List<News> newsList = newsService.getAllNews();
		List<NewsVM> newsVMs = new ArrayList<NewsVM>();
		for (News news : newsList) {
			if (news.getType() == 2) {
				continue;
			}
			NewsVM newsVM = new NewsVM(news);
			newsVMs.add(newsVM);
		}
		model.addAttribute("list", newsVMs);	
		return "leadPage";
	}
	
	@RequestMapping("/expNews")
	public String expNews(HttpSession session,HttpServletRequest request, Model model){
		List<News> newsList = newsService.getAllNews();
		List<NewsVM> newsVMs = new ArrayList<NewsVM>();
		for (News news : newsList) {
			if (news.getType() == 2) {
				continue;
			}
			NewsVM newsVM = new NewsVM(news);
			newsVMs.add(newsVM);
		}
		model.addAttribute("list", newsVMs);
			return "expNews";
	}
	@RequestMapping("/experimentInfo")
	public String experimentInfo(HttpSession session,HttpServletRequest request, Model model){
		List<ExperimentInfo> experimentInfos = experimentInfoService.getAllExperimentInfos();
		model.addAttribute("list", experimentInfos);
		
		return "experimentInfo";
	}
	@RequestMapping("/addUserPage")
	public String addUserPage(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		String userType = user.getUserType();
		if ("3".equals(userType)) {
			return "rbac/addUser";
		} else {
			return "error";
		}
		
	}
	
	@RequestMapping("/getUserManagerList")
	@ResponseBody
	public String getNewsManagerList(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		
		PageVM<User> pageVM = userService.getAllPageVMUser(pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("check_password")
	@ResponseBody
	private boolean checkPassword(String old_password,HttpSession session, HttpServletRequest request) {
		
		User user = (User)session.getAttribute("userSession");
		String state = user.getState();
		if (state.equals("1")) {
			if (old_password.equals(user.getInitPassword())) {
				return true;
			}
			return false;
		} else {
			if (old_password.equals(user.getPassword())) {
				return true;
			}
			return false;
		}
	}
	
	@RequestMapping("savePassword")
	@ResponseBody
	public String savePassword(HttpSession session, HttpServletRequest request) {
		
		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		User user = (User) session.getAttribute("userSession");
		
		if (check_Password(old_password, user)) {
			user.setPassword(new_password);
			user.setState("2");
			user.setInitPassword("");
			userService.saveUser(user);
			
			return "success";
		}
		
		return "error";
	}
	
	private boolean check_Password(String old_password,User user) {
		
		String state = user.getState();
		if (state.equals("1")) {
			if (old_password.equals(user.getInitPassword())) {
				return true;
			}
			return false;
		} else {
			if (old_password.equals(user.getPassword())) {
				return true;
			}
			return false;
		}
	}
	
	
	//验证码   
	@RequestMapping("generatecode")
	public void generateCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		
		VerificationCode.verify(request, response);
	}
	
	
	//getExperimentInfoandNews
	@RequestMapping("PageGetExperimentInfo")
	@ResponseBody
	public String PageGetExperimentInfo(HttpSession session, HttpServletRequest request) {
		
		List<ExperimentInfo> experimentInfos = experimentInfoService.getAllExperimentInfos();
		return JsonConvertor.obj2JSON(experimentInfos);
	}
	
	@RequestMapping("PageGetNews")
	@ResponseBody
	public String PageGetNews(HttpSession session, HttpServletRequest request) {
		
		List<News> news = newsService.getAllNews();
		return JsonConvertor.obj2JSON(news);
	}
	
	@RequestMapping("forgetPassword")
	@ResponseBody
	public String forgetPassword(HttpSession session, HttpServletRequest request) throws MessagingException, IOException{
		
		String loginId = request.getParameter("loginId");
		User user = userService.getUserByLoginId(loginId);
		if (user != null) {
			String email = user.getEmail();
			if (!StringUtils.isEmpty(email)) {
				Address[] to = new Address[1];
				to[0] = new InternetAddress(email);
				
				EmailMessage emailMessage = new EmailMessage();
				Mail mail = new Mail();
				mail.setMessage("您的登陆密码是："+user.getPassword());
				mail.setSubject("找回密码");
				mail.setReceiver(email);
				emailMessage.send(mail);
			}
			return "未填写邮箱，请联系管理员重置密码";
		}
		return "登录名不存在！";
	}
	
	@RequestMapping("forgetPasPage")
	public String forgetPasPage(HttpSession session, HttpServletRequest request) throws MessagingException, IOException{
		
		return "common/forgetPassword";
	}
	
	
}
