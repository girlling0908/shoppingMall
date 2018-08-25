package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/item/cat/list")
	@ResponseBody
	private List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}

   //显示商品类目的时候，由id找到其数据
	@RequestMapping("/item/cat/showItemCat")
	@ResponseBody
	private EasyUITreeNode showItemCat(Long id){
		EasyUITreeNode node = itemCatService.showItemCat(id);
		return node;
	}

}
