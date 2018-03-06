package com.physics.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.entity.Content;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.News;
import com.physics.entity.User;
import com.physics.service.base.NewsService;
import com.physics.util.JsonConvertor;
import com.physics.vm.NewsVM;
import com.physics.vm.PageVM;


@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Resource(name="newsService")
	private NewsService newsService;
	
	@RequestMapping("/getNewsMananger")
	public String getExperimentInfo(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("3".equals(user.getUserType())) {
			return "news/getNewsManangerList";
		}
		return "error";
	}
	
	@RequestMapping("/newsPage")
	public String newsPage(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		News news = newsService.getNews(id);
		NewsVM newsVM = new NewsVM(news);
		model.addAttribute("newsVM", newsVM);
		return "common/news";
	}
	
	@RequestMapping("/editNews")
	public String editNews(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		News news = newsService.getNews(id);
		User user = (User)session.getAttribute("userSession");
		if (news.getUserId().equals(user.getId()) || "3".equals(user.getUserType())) {
			Content content = news.getContent();
			model.addAttribute("content", content);
			return "news/editNews";
		}
		return "error";
		
	}
	
	@RequestMapping("/addNews")
	public String addNews(HttpSession session,HttpServletRequest request, Model model){
		
		return "news/editNews";
	}
	
	@RequestMapping("/getNewsManagerList")
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
		
		PageVM<NewsVM> pageVM = newsService.getNewsManager(pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/saveNews")
	@ResponseBody
	public String saveNews(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String newsHead = request.getParameter("newsHead");
		String newsContent = request.getParameter("newsContent");
		String id = request.getParameter("id");
		
		News news = newsService.getNews(id);
		if (!news.getUserId().equals(user.getId()) && !"3".equals(user.getUserType())) {
			return "error";
		}
		Content content = news.getContent();
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	    String s = format1.format(new Date());
		news.setCalendar(s);
		content.setHead(newsHead);
		content.setContent(newsContent);
		
		newsService.updateOrSaveNews(news);
		
		return "success";
		
	}
	
	@RequestMapping("/saveAddNews")
	@ResponseBody
	public String saveAddNews(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String newsHead = request.getParameter("newsHead");
		String newsContent = request.getParameter("newsContent");
		
		News news = new News();
		Content content = new Content();
		
		news.setType(1);
		news.setUser(user);
		news.setUserId(user.getId());
		content.setContent(newsContent);
		content.setHead(newsHead);
		news.setContent(content);
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	    String s = format1.format(new Date());
		news.setCalendar(s);
		
		newsService.updateOrSaveNews(news);
		
		return "success";
		
	}
	
	@RequestMapping("/deleteNews")
	@ResponseBody
	public String deleteNews(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String id = request.getParameter("id");
		if (!"3".equals(user.getUserType())) {
			return "error";
		}		
		newsService.deleteNews(id);
		return "success";
	}
}
