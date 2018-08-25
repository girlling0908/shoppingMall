package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUITreeNode;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(Long parentId);

	//添加分类
	E3Result saveContentCategory(Long parentId, String name);
    //更新分类
	E3Result updateContentCategory(Long id, String name);
    //删除分类内容
	E3Result deleteContentCategory(Long id);
}
