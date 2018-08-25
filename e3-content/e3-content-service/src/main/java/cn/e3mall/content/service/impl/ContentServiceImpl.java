package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.common.JsonUtils;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	
	@Autowired
	private TbContentMapper contentMapper;
	// jedis设置
	@Autowired
	private JedisClient jedisClient;

	// 根据内容分类查找出内容
	public EasyUIDataGridResult getItemList(Integer categoryId, Integer page, Integer rows) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId.longValue());
		List<TbContent> list = contentMapper.selectByExample(example);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		// 取分页结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		// 取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}

	// 插入数据
	public E3Result saveContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		return E3Result.ok();
	}

	// 根据id来显示content
	public TbContent showContent(Integer id) {
		return contentMapper.selectByPrimaryKey(id.longValue());
	}

	// 更新内容
	public E3Result updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		return E3Result.ok();
	}

	// 删除数据
	public E3Result deleteContent(Long id) {
		contentMapper.deleteByPrimaryKey(id);
		return E3Result.ok();
	}

	// 首页轮播图的展示
	public List<TbContent> getContentList(Long categoryId) {
		// 先从缓存中查询
		try {
			String string = jedisClient.hget("CONTENT_LIST", categoryId + "");
			System.out.println("get" + string);
			if (StringUtils.isNotBlank(string)) {
				List<TbContent> list = JsonUtils.jsonToList(string, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		// 根据分类id查询内容列表
		// 设置查询条件
		List<TbContent> list = contentMapper.selectByExample(example);
		// 向缓存中添加数据
		System.out.println("set");
		try {
			jedisClient.hset("CONTENT_LIST", categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
