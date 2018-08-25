package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.EasyUIDataGridResult;
import cn.e3mall.common.IDUtils;
import cn.e3mall.common.JsonUtils;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Resource
	private Destination topicDestination;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public TbItem getItemById(long itemId) {

		// 查询缓存
		try {
			String string = jedisClient.get("ITEM-INFO:" + itemId + ":BASE");
			if (StringUtils.isNotBlank(string)) {
				TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据库
		// 根据主键查询
		// TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andIdEqualTo(itemId);
		// 执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			// 把结果添加到缓存
			jedisClient.set("ITEM-INFO:" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
			// 设置过期时间
			jedisClient.expire("ITEM-INFO:" + itemId + ":BASE", 3600);
			return list.get(0);
		}
		return null;
	}

	// 返回商品详情页面
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		// 取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	public E3Result addItem(TbItem item, String desc) {
		// 1.生成商品id
		final long itemid = IDUtils.genItemId();
		// 2.补全TbItem对象的属性
		item.setId(itemid);
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 向商品表中插入数据
		itemMapper.insert(item);
		// 创建一个TbItemDesc
		TbItemDesc itemDesc = new TbItemDesc();

		itemDesc.setItemId(itemid);
		itemDesc.setCreated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);

		// 向商品详情表中插入数据
		itemDescMapper.insert(itemDesc);

		// 发送商品添加成功的消息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message createTextMessage = session.createTextMessage(itemid + "");
				return createTextMessage;
			}

		});
		return E3Result.ok();
	}

	// 根据商品id查询商品描述
	public E3Result findDataDesc(Long itemdescid) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemdescid);
		E3Result e3 = new E3Result(itemDesc);
		return e3;
	}

	// 修改数据
	public E3Result updateItem(TbItem item, String desc) {
		// 更新日期
		Date date = new Date();
		item.setUpdated(date);
		item.setStatus((byte) 1);
		TbItem key = itemMapper.selectByPrimaryKey(item.getId());
		item.setCreated(key.getCreated());
		itemMapper.updateByPrimaryKey(item);
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(item.getId());
		// 更新数据
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return E3Result.ok();
	}

	// 批量删除数据
	public E3Result deleteItem(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			itemMapper.deleteByPrimaryKey1(Long.parseLong(id));
		}
		return E3Result.ok();
	}

	// 更新商品状态，1为上架，2为下架
	public E3Result updateItemState(String ids, Integer i) {
		String[] split = ids.split(",");
		for (String id : split) {
			itemMapper.updateItemStateById(i, Long.parseLong(id));
		}
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		try {
			String string = jedisClient.get("ITEM-INFO:" + itemId + ":Desc");
			if (StringUtils.isNotBlank(string)) {
				TbItemDesc item = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 把结果添加到缓存
		jedisClient.set("ITEM-INFO:" + itemId + ":Desc", JsonUtils.objectToJson(itemDesc));
		// 设置过期时间
		jedisClient.expire("ITEM-INFO:" + itemId + ":Desc", 3600);
		return itemDesc;
	}
}
