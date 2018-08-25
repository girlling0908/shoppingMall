package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

public class Item extends TbItem {

	
	public Item(TbItem tbItem){
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
		this.setImage(tbItem.getImage());
	}
	public String[] getImages(){
		String image2 = this.getImage();
		if(image2!=null&&!"".equals(image2))
		{
			String[] string=image2.split(",");
			return string;
		}
		return null;
	}
}
