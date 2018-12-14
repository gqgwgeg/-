package pyg.shang.Service.Ipml;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.*;
import com.pyg.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import pyg.com.utis.PageResult;
import pyg.shang.Service.GoodsService;
import pyg.shang.mapper.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;
    //品牌对象
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private TbSellerMapper sellerMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Goods goods) {
        // 1保存spu的数据 ,获取spu对象
        TbGoods Goods = goods.getTbGoods();
        goodsMapper.insertSelective(Goods);
        // 2.保存spu描述表的信息
        TbGoodsDesc GoodsDesc = goods.getTbGoodsDesc();
        //手动维护关系1对1
        GoodsDesc.setGoodsId(Goods.getId());
        goodsDescMapper.insertSelective(GoodsDesc);
        //获得sku对象集合
        List<TbItem> itemsList = goods.getItems();
        if (goods.getTbGoods().getIsEnableSpec().equals("1")) {


            for (TbItem tbItem : itemsList) {
                //设置 标题spec:{'网络':'联通4G',内存:'16G'}
                String spec = tbItem.getSpec();
                //json字符串转换成map对象,注意jar包
                Map<String, String> specMap = (Map) JSON.parse(spec);
                //循环map
                String specStc = "";
                for (String key : specMap.keySet()) {
                    specStc += " " + specMap.get(key);
                }
                //设置sku表里面的标题
                tbItem.setTitle(Goods.getGoodsName() + specStc);
                //设置买点
                tbItem.setSellPoint(Goods.getCaption());
                //从描述对象中获取图片地址
                //存储的是json数组
                String itemImages = GoodsDesc.getItemImages();
                //判断图片地址是否为空
                if (itemImages != null && !"".equals(itemImages)) {

                    List<Map> mapList = JSON.parseArray(itemImages, Map.class);
                    tbItem.setImage((String) mapList.get(0).get("url"));
                }
                //设置三级节点id
                tbItem.setCategoryid(Goods.getCategory3Id());
                //设置创建时间
                Date date = new Date();
                tbItem.setCreateTime(date);
                tbItem.setUpdateTime(date);
                //设置货品id
                tbItem.setGoodsId(Goods.getId());
                tbItem.setSellerId(Goods.getSellerId());
                //查询分类名称,根据 节点id

                TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(Goods.getCategory3Id());
                tbItem.setCategory(tbItemCat.getName());
                //查询品牌名称
                TbBrand brand = brandMapper.findAOne(Goods.getBrandId());
                tbItem.setBrand(brand.getName());
                //商家名称
                TbSeller Seller = sellerMapper.selectByPrimaryKey(Goods.getSellerId());
                tbItem.setSellerId(Seller.getNickName());
                //保存sku数据
                itemMapper.insertSelective(tbItem);
            }
        } else {
            //没有启用规格选项
            TbItem tbItem = new TbItem();
            //设置sku表里面的标题
            tbItem.setTitle(Goods.getGoodsName());
            //设置买点
            tbItem.setSellPoint(Goods.getCaption());
            //从描述对象中获取图片地址
            //存储的是json数组
            String itemImages = GoodsDesc.getItemImages();
            //判断图片地址是否为空
            if (itemImages != null && !"".equals(itemImages)) {

                List<Map> mapList = JSON.parseArray(itemImages, Map.class);
                tbItem.setImage((String) mapList.get(0).get("url"));
            }
            //设置三级节点id
            tbItem.setCategoryid(Goods.getCategory3Id());
            //设置创建时间
            Date date = new Date();
            tbItem.setCreateTime(date);
            tbItem.setUpdateTime(date);
            //设置货品id
            tbItem.setGoodsId(Goods.getId());
            tbItem.setSellerId(Goods.getSellerId());
            //查询分类名称,根据 节点id

            TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(Goods.getCategory3Id());
            tbItem.setCategory(tbItemCat.getName());
            //查询品牌名称
            TbBrand brand = brandMapper.findAOne(Goods.getBrandId());
            tbItem.setBrand(brand.getName());
            //商家名称
            TbSeller Seller = sellerMapper.selectByPrimaryKey(Goods.getSellerId());
            tbItem.setSellerId(Seller.getNickName());
            //保存sku数据
            itemMapper.insertSelective(tbItem);

        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();
        //商家名称
        if (goods != null) {
            if (goods.getSellerId() != null && !"".equals(goods.getSellerId())) {
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            //状态比较
            if(goods.getAuditStatus()!=null&&!"".equals(goods.getAuditStatus())){
                criteria.andAuditStatusEqualTo(goods.getAuditStatus());
            }
            //商品名称
            if(goods.getGoodsName()!=null&& !"".equals(goods.getGoodsName())){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            }
        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

}
