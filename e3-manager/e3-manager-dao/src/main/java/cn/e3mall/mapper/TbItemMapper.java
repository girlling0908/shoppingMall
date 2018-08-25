package cn.e3mall.mapper;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbItemMapper {
    int countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);
    
    //逻辑上删除项目
    int deleteByPrimaryKey1(Long id);

    //更新商品状态 1为上架，2为下架
	void updateItemStateById(@Param("status")Integer i,@Param("id")long parseLong);
}