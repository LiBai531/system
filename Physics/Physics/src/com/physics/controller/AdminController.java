package com.physics.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.entity.Attachment;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.MenuFirst;
import com.physics.entity.MenuSecond;
import com.physics.entity.User;
import com.physics.service.base.AttachmentService;
import com.physics.service.base.ExperimentInfoService;
import com.physics.service.base.MenuFirstService;
import com.physics.service.base.MenuSecondService;
import com.physics.service.base.UserService;
import com.physics.util.JsonConvertor;
import com.physics.vm.PageVM;
import com.physics.vm.ZtreeVM;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource(name="experimentInfoService")
	private ExperimentInfoService experimentInfoService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="menuFirstService")
	private MenuFirstService menuFirstService;
	@Resource(name="menuSecondService")
	private MenuSecondService menuSecondService;
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	
	@RequestMapping("/getExperimentInfo")
	public String getExperimentInfo(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("3".equals(user.getUserType())) {
			return "experimentA/experimentInfoManager";
		}
		return "error";
	}
	
	@RequestMapping("/userMenuManager")
	public String userMenuManager(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String userType = request.getParameter("type");
		List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(userType);
		List<ZtreeVM> ztreeVMs = new ArrayList<ZtreeVM>();
		for (MenuFirst menuFirst : menuFirsts) {
			ZtreeVM ztreeVM = new ZtreeVM(menuFirst);
			ztreeVMs.add(ztreeVM);
			for (MenuSecond menuSecond : menuFirst.getMenuSeconds()) {
				ZtreeVM ztreeVM2 = new ZtreeVM(menuSecond);
				ztreeVMs.add(ztreeVM2);
			}
		}
		String ztreeNode = JsonConvertor.obj2JSON(ztreeVMs);
		if ("3".equals(user.getUserType())) {
			model.addAttribute("ztreeNode", ztreeNode);
			model.addAttribute("userType", userType);
			return "experimentA/userMenuManager";
		}
		return "error";
	}
	
	@RequestMapping("/editExpermentInfo")
	public String editExpermentInfo(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String id = request.getParameter("id");
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(id);
		if ("3".equals(user.getUserType())) {
			model.addAttribute("experimentInfo", experimentInfo);
			return "experimentA/editExperimentInfo";
		}
		return "error";
	}
	
	@RequestMapping("/getExperimentInfoList")
	@ResponseBody
	public String getExperimentInfoList(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		
		PageVM<ExperimentInfo> pageVM = experimentInfoService.getExperimentInfo(pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/saveExperimentInfo")
	@ResponseBody
	public String saveExperimentInfo(HttpSession session,HttpServletRequest request, Model model){
		String name = request.getParameter("expName");
		String address = request.getParameter("expAddress");
		String limit = request.getParameter("expLimit");
		String info = request.getParameter("expInfo");
		String id = request.getParameter("expInfoId");
		
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(id);
		experimentInfo.setAddress(address);
		experimentInfo.setInfo(info);
		experimentInfo.setName(name);
		experimentInfo.setPersonLimit(Integer.valueOf(limit));
		
		experimentInfoService.saveExperimentInfo(experimentInfo);
		return "";
	}
	
	@RequestMapping("/saveExperimentInfo_")
	@ResponseBody
	public String saveExperimentInfo_(HttpSession session,HttpServletRequest request, Model model){
		String name = request.getParameter("expName");
		String address = request.getParameter("expAddress");
		String limit = request.getParameter("expLimit");
		String info = request.getParameter("expInfo");
		String id = request.getParameter("expInfoId");
		
		ExperimentInfo experimentInfo = new ExperimentInfo();
		experimentInfo.setAddress(address);
		experimentInfo.setInfo(info);
		experimentInfo.setName(name);
		experimentInfo.setPersonLimit(Integer.valueOf(limit));
		experimentInfo.setState("1");
		
		experimentInfoService.saveExperimentInfo(experimentInfo);
		return "";
	}
	
	@RequestMapping("/deleteExperimentInfo")
	@ResponseBody
	public String deleteExperimentInfo(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("expInfoId");
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(id);
		experimentInfo.setState("0");
		experimentInfoService.saveExperimentInfo(experimentInfo);
		return "";
	}
	
	@RequestMapping("/getTypeUsers")
	@ResponseBody
	public String getTypeUsers(HttpSession session,HttpServletRequest request, Model model){
		String userType = request.getParameter("type");//排序方式
		
		PageVM<User> pageVM = userService.getTypeUsers(userType);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/getUserMenuByType")
	@ResponseBody
	public String getUserMenuByType(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String userType = request.getParameter("id");
		List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(userType);
		List<ZtreeVM> ztreeVMs = new ArrayList<ZtreeVM>();
		for (MenuFirst menuFirst : menuFirsts) {
			ZtreeVM ztreeVM = new ZtreeVM(menuFirst);
			ztreeVMs.add(ztreeVM);
			for (MenuSecond menuSecond : menuFirst.getMenuSeconds()) {
				ZtreeVM ztreeVM2 = new ZtreeVM(menuSecond);
				ztreeVMs.add(ztreeVM2);
			}
		}
		return JsonConvertor.obj2JSON(ztreeVMs);
		
	}
	
	@RequestMapping("saveMenuManager/submit/{ids}")
	@ResponseBody
	public String submit(@PathVariable(value="ids")Set<String> ids,HttpServletRequest request){
		String userType = request.getParameter("userType");
		List<MenuFirst> menuFirsts = menuFirstService.getMenusByUsertype(userType);
		for (MenuFirst menuFirst : menuFirsts) {
			if (ids.contains(menuFirst.getId())) {
				menuFirst.setState("1");
				List<MenuSecond> menuSeconds = menuFirst.getMenuSeconds();
				for (MenuSecond menuSecond : menuSeconds) {
					if (ids.contains(menuSecond.getId())) {
						menuSecond.setState("1");
					} else {
						menuSecond.setState("0");

					}
				}
				menuFirstService.saveMenus(menuFirst);
				
			} else {
				menuFirst.setState("0");
				List<MenuSecond> menuSeconds = menuFirst.getMenuSeconds();
				for (MenuSecond menuSecond : menuSeconds) {
					menuSecond.setState("0");
				}
				menuFirstService.saveMenus(menuFirst);
			}
		}
		
		return "";
	}
	
	@RequestMapping("videoManager")
	public String videoManager(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		if (user.getUserType().equals("3")) {
			return "experimentA/videoManager";
		} else {
			return "experimentS/videoManager";
		}
	}
	
	
	@RequestMapping("/getVideoList")
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
		
		List<Attachment> attachments = attachmentService.getVideoAttachments(pageIndex, pageSize, asc, "id", "2");
		PageVM<Attachment> pageVM = new PageVM<Attachment>(pageIndex, attachments.size(), pageSize, attachments);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	
	@RequestMapping("/deleteVideo")
	@ResponseBody
	public String deleteVideo(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		attachmentService.deleteAttachment(id);
		return "删除成功";
	}
}
