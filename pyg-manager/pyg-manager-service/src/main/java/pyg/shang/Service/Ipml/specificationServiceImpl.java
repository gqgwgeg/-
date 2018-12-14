package pyg.shang.Service.Ipml;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import pyg.com.utis.PageResult;
import pyg.shang.Service.specificationService;
import pyg.shang.mapper.TbSpecificationMapper;
import pyg.shang.mapper.TbSpecificationOptionMapper;

import java.util.List;
import java.util.Map;


@Service
public class specificationServiceImpl implements specificationService {
    //注入规格数据
    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;
    //注入规格选项数据
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;


    /**
     * 规格分页查询
     *
     * @param pageNum
     * @param rows
     * @return
     */
    public PageResult findPage(Integer pageNum, Integer rows) {
        PageHelper.startPage(pageNum, rows);
        Page<TbSpecification> page = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Specification specification) {
//        获取规格数据
        TbSpecification tbSpecification = specification.getTbSpecification();
        //添加规格数据
        tbSpecificationMapper.insertSelective(tbSpecification);
        //添加规格选项数据
        List<TbSpecificationOption> tbSpecificationOptions = specification.getTbSpecificationOptions();
        //遍历集合,获得集合中的每条规格选项数据
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
            //设置规格选项数据的id来维护规格和规格选表的关系1对多
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            tbSpecificationOptionMapper.insertSelective(tbSpecificationOption);
        }

    }

    //删除规格数据
    @Override
    public void del(Long[] ids) {
        for (Long id : ids) {

            tbSpecificationMapper.deleteByPrimaryKey(id);
            TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
            criteria.andSpecIdEqualTo(id);
            tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        }
    }

    //根据id查询
    @Override
    public Specification findOne(Long id) {
        //查询规格数据
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        //查询规格选项数据
        List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);
        specification.setTbSpecificationOptions(tbSpecificationOptions);

        return specification;
    }

    @Override
    public void update(Specification specification) {
        //规格对象
        TbSpecification tbSpecification = specification.getTbSpecification();
        List<TbSpecificationOption> tbSpecificationOptions = specification.getTbSpecificationOptions();
        //修改
            tbSpecificationMapper.updateByPrimaryKeySelective(tbSpecification);
        //规格选项对象
        //创建条件查询
        TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        //先删除再插入=修改  满足修改动态添加行需求
        tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptions) {
            //设置外键
            tbSpecificationOption.setSpecId(tbSpecification.getId());
            //添加规格选项数据
            tbSpecificationOptionMapper.insertSelective(tbSpecificationOption);
        }
    }

    @Override
    public List<Map> findSpecificationList() {
        return tbSpecificationMapper.findSpecificationList();
    }
}
