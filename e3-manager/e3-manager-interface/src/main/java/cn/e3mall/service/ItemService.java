package cn.e3mall.service;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	 TbItem getItemById(long id);

	EasyUIDataGridResult getItemList(Integer page, Integer rows);

	//保存数据
	E3Result addItem(TbItem item,String desc);
    //加载商品描述表中的信息
	E3Result findDataDesc(Long itemdescid);
    //修改数据
	E3Result updateItem(TbItem item, String desc);
     //批量删除
	E3Result deleteItem(String ids);

	
	 //更新状态，设置为1要去上架，设置为2为要去下架
	E3Result updateItemState(String ids, Integer i);
	
	//获得ItemDesc
	TbItemDesc getItemDescById(long itemId);
	

}
