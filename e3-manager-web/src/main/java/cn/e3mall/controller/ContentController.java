package cn.e3mall.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	//根据内容分类展示内容
	@RequestMapping("/content/query/list")
	@ResponseBody
	private EasyUIDataGridResult contentList(Integer categoryId,Integer page, Integer rows){
		EasyUIDataGridResult result = contentService.getItemList(categoryId,page, rows);
		return result;
	}
	
	//新增内容
	@RequestMapping("/content/save")
	@ResponseBody
	private E3Result saveContent(TbContent content){
		E3Result e3=contentService.saveContent(content);
		return e3;
	}
	
	
	//为了更新而显示content
	@RequestMapping("/content/showContent")
	@ResponseBody
	private TbContent showContent(Integer id)
	{
		return contentService.showContent(id);
	}
	
	//更新content
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	private E3Result updateContent(TbContent content)
	{
		return contentService.updateContent(content);
	}
	
	
	//删除内容
	@RequestMapping("/content/delete")
	@ResponseBody
	private E3Result deleteContent(Long ids)
	{
		return contentService.deleteContent(ids);
	}
	

	
}
