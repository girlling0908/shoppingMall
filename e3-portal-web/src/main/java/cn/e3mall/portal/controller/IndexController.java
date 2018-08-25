package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class IndexController {

	@Value("${FIRST_PICTURE_ID}")
	private Long FIRST_PICTURE_ID;
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		List<TbContent> list = contentService.getContentList(FIRST_PICTURE_ID);
		model.addAttribute("ALList", list);
		return "index";
	}
}
