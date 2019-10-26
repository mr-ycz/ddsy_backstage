package com.ycz.dao;

import com.ycz.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    List<Goods> selectAllGoods();

    Integer insertGoods(Goods goods);

    Goods selectById(@Param("id") Integer id);

    List<Goods> selectByTypeid(@Param("typeid") Integer id, @Param("on") String on);

    void deleteGoodsById(@Param("gid") Integer gid);

    void updateGoods(Goods goods);

}