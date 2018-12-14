package pyg.shang.Service;

import com.pyg.pojo.TbBrand;
import pyg.com.utis.PageResult;

import java.util.List;
import java.util.Map;

public interface BranService {

    //查询所有品牌数据
    public List<TbBrand> findAll();

    //添加品牌数据
    public void add(TbBrand tbBrand);

    //品牌数据的分页
    public PageResult findPage(Integer pageNum, Integer rows);


    //修改品牌数据
    public void update(TbBrand tbBrand);

    //根据id查询批改数据
    public TbBrand findOne(Long id);

    //批量删除
        public void del(Long[] ids);


        //品牌下拉查询
    public List<Map>findBrandList();
}
