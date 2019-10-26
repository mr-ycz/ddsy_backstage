package com.ycz.service;

import com.ycz.dao.GoodsMapper;
import com.ycz.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public Goods queryById(Integer id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public void deleteGoodsById(Integer gid) {
        goodsMapper.deleteGoodsById(gid);
    }

    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateGoods(goods);
    }
}
