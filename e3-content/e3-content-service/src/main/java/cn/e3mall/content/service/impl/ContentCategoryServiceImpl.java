package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCatMapper;

	public List<EasyUITreeNode> getContentCatList(Long parentId) {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> result=new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbcontent:list)
		{
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbcontent.getId());
			node.setText(tbcontent.getName());
			node.setState(tbcontent.getIsParent()?"closed":"open");
			result.add(node);
		}
		
		return result;
	}

	public E3Result saveContentCategory(Long parentId, String name) {
		//插入节点
		TbContentCategory tcc=new TbContentCategory();
		tcc.setName(name);
		tcc.setStatus(1);
		tcc.setSortOrder(1);
		Date date=new Date();
		tcc.setCreated(date);
		tcc.setUpdated(date);
		tcc.setParentId(parentId);
		tcc.setIsParent(false);
		contentCatMapper.insert(tcc);
		
		//对该父节点进行判断
		TbContentCategory primaryKey = contentCatMapper.selectByPrimaryKey(parentId);
		if(!primaryKey.getIsParent())
		{
			primaryKey.setIsParent(true);
			contentCatMapper.updateByPrimaryKey(primaryKey);
		}
		
		E3Result e3=new E3Result(tcc);
		return e3;
	}

	//更新节点
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory tcc=new TbContentCategory();
		tcc.setId(id);
		tcc.setName(name);
		tcc.setUpdated(new Date());
		contentCatMapper.updateByPrimaryKeySelective(tcc);
		return E3Result.ok();
	}

     //删除节点内容，只能删除叶子节点，叶子节点删除之后，要判断其父节点还有没有叶子节点了
	public E3Result deleteContentCategory(Long id) {
		TbContentCategory category = contentCatMapper.selectByPrimaryKey(id);
		if (category.getParentId()==0)
		{
			//表示你要删除的父亲节点还有叶子节点,则不能删除
			
		}
		else
		{
			//表示删除的就是叶子节点，则删除之后，还要判断是否还有其父节点会不会变成叶子节点
		}
		return null;
	}

}
