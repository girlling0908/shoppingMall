package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.EasyUITreeNode;

public interface ItemCatService {
      List<EasyUITreeNode> getItemCatList(Long parentId);
      //根据ID查询显示
      EasyUITreeNode showItemCat(Long id);
}
