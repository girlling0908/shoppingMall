package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;

@Controller
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;

     //显示内容节点
	@RequestMapping("/content/category/list")
	@ResponseBody
	private List<EasyUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	//插入节点
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	private E3Result createContentCategory(Long parentId,String name){
		E3Result e3=contentCategoryService.saveContentCategory(parentId,name);
		return e3;
	}
	
	//更新节点
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	@ResponseBody
	private E3Result updateContentCategory(Long id,String name){
		E3Result e3=contentCategoryService.updateContentCategory(id,name);
		return e3;
	}
	
	//刪除節點
	@RequestMapping(value="/content/category/delete/",method=RequestMethod.POST)
	@ResponseBody
	private E3Result deleteContentCategory(Long id){
		E3Result e3=contentCategoryService.deleteContentCategory(id);
		return e3;
	}
}
