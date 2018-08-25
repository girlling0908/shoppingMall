package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Controller
**/
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
    //显示商品信息
	@RequestMapping("/item/list")
	@ResponseBody
	private EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	// 新增商品信息
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	private E3Result saveItem(TbItem item, String desc) {
		E3Result result = itemService.addItem(item, desc);
		return result;
	}

	// 加载商品描述
	@RequestMapping("/item/desc/{itemdescid}")
	@ResponseBody
	private E3Result findDataDesc(@PathVariable Long itemdescid) {
		E3Result e3 = itemService.findDataDesc(itemdescid);
		return e3;
	}

	// 修改商品信息
	@RequestMapping(value = "/item/update", method = RequestMethod.POST)
	@ResponseBody
	private E3Result updateItem(TbItem item, String desc) {
		E3Result e3 = itemService.updateItem(item, desc);
		return e3;
	}

	// 删除商品信息，就是将这些商品信息的标识修改一下
	@RequestMapping("/item/delete")
	@ResponseBody
	private E3Result deleteItem(String ids) {
		E3Result e3 = itemService.deleteItem(ids);
		return e3;

	}

	// 下架商品
	@RequestMapping("/item/instock")
	@ResponseBody
	private E3Result instock(String ids) {
		E3Result e3 = itemService.updateItemState(ids, 2);
		return e3;
	}

	// 上架商品
	@RequestMapping("/item/reshelf")
	@ResponseBody
	private E3Result reshelf(String ids) {
		E3Result e3 = itemService.updateItemState(ids,1);
		return e3;
	}

}
