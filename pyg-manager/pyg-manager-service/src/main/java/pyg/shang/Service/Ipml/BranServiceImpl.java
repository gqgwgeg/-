package pyg.shang.Service.Ipml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbBrand;

import org.springframework.beans.factory.annotation.Autowired;
import pyg.com.utis.PageResult;
import pyg.shang.Service.BranService;
import pyg.shang.mapper.BrandMapper;


import java.util.List;
import java.util.Map;

@Service
public class BranServiceImpl implements BranService {
    @Autowired
    private BrandMapper brandMapper;

    //查询所有品牌数据
    @Override
    public List<TbBrand> findAll() {
        return brandMapper.findAll();
    }


    //添加品牌数据
    @Override
    public void add(TbBrand tbBrand) {
        brandMapper.insert(tbBrand);
    }

    //品牌的分页查询
    @Override
    public PageResult findPage(Integer pageNum, Integer rows) {
        PageHelper.startPage(pageNum, rows);
        Page<TbBrand> page = (Page<TbBrand>) brandMapper.findAll();
        return new PageResult(page.getTotal(), page.getResult());
    }

    //品牌数据修改
    @Override
    public void update(TbBrand tbBrand) {
        brandMapper.update(tbBrand);
    }

    //根据id查询品牌数据
    @Override
    public TbBrand findOne(Long id) {


        TbBrand brand = brandMapper.findAOne(id);
        return brand;
    }

    //批量删除
    @Override
    public void del(Long[] ids) {

        for (long id : ids) {
            brandMapper.del(id);
        }
    }

    @Override
    public List<Map> findBrandList() {
        return brandMapper.findBrandList();
    }


}
