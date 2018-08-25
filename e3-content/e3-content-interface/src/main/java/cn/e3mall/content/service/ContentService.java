package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
     //查找出内容
	EasyUIDataGridResult getItemList(Integer categoryId, Integer page, Integer rows);
     //新增内容
	E3Result saveContent(TbContent content);
	
	//根据Id来显示content
	TbContent showContent(Integer id);
	
	//更新数据
	E3Result updateContent(TbContent content);
	
	//刪除数据
	E3Result deleteContent(Long id);
	
	//根据分类Id查询列表
	List<TbContent> getContentList(Long categoryId);

}
