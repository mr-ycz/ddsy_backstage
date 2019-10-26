package com.ycz.service;

import com.ycz.pojo.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> queryAllGoods();

    Goods queryById(Integer id);

    void deleteGoodsById(Integer gid);

    void updateGoods(Goods goods);

}
