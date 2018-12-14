package pyg.shang.mapper;

import com.pyg.pojo.TbBrand;


import java.util.List;
import java.util.Map;

public interface BrandMapper {
  //查询所有品牌数据
    public List<TbBrand> findAll();

    //添加品牌数据
    public void insert(TbBrand tbBrand);

    //修改品牌数据
    public void update(TbBrand tbBrand);

   //根据id查询批改数据
    public TbBrand findAOne(Long id);

    //删除品牌数据
    public void  del(Long id);
//品牌下拉查询
    public List<Map>findBrandList();
}
