package com.pyg.vo;

import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {
    //spu表
    private TbGoods tbGoods;
    //商品描述表
    private TbGoodsDesc tbGoodsDesc;
    //sku表
    private List<TbItem> items;

    public Goods() {
    }

    public Goods(TbGoods tbGoods, TbGoodsDesc tbGoodsDesc, List<TbItem> items) {
        this.tbGoods = tbGoods;
        this.tbGoodsDesc = tbGoodsDesc;
        this.items = items;
    }

    public TbGoods getTbGoods() {
        return tbGoods;
    }

    public void setTbGoods(TbGoods tbGoods) {
        this.tbGoods = tbGoods;
    }

    public TbGoodsDesc getTbGoodsDesc() {
        return tbGoodsDesc;
    }

    public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
        this.tbGoodsDesc = tbGoodsDesc;
    }

    public List<TbItem> getItems() {
        return items;
    }

    public void setItems(List<TbItem> items) {
        this.items = items;
    }
}
