package pyg.shang.Service.Ipml;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.pojo.TbTypeTemplateExample;
import com.pyg.pojo.TbTypeTemplateExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import pyg.com.utis.PageResult;
import pyg.shang.Service.TypeTemplateService;
import pyg.shang.mapper.TbSpecificationOptionMapper;
import pyg.shang.mapper.TbTypeTemplateMapper;

import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TbTypeTemplateMapper typeTemplateMapper;

    /**
     * 规格选项
     */
    @Autowired
    private   TbSpecificationOptionMapper tbSpecificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbTypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbTypeTemplateExample example = new TbTypeTemplateExample();
        Criteria criteria = example.createCriteria();

        if (typeTemplate != null) {
            if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
                criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
            if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
                criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
            }
            if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
                criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
            }
            if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
                criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
            }

        }

        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询规格及规格选项
     * @param typeId 模板id
     * @return
     */
    @Override
    public List<Map> findSpecList(Long typeId) {

        //使用模板id查询
        TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(typeId);
         //获得规格属性数据:[{"id":33,"text":"电视屏幕尺寸"},{"id":32,"text":"机身内存"},{"id":28,"text":"手机屏幕尺寸"}]
        String specIds = tbTypeTemplate.getSpecIds();
         //转成map对象
        List<Map> specList = JSON.parseArray(specIds, Map.class);
        for (Map map : specList) {
            //数据强转成long类型
          // 方式一:频繁创建对象不太好 Long id =new Long((Integer) map.get("id"));
           //方式二:
            Long specId = Long.valueOf(String.valueOf(map.get("id"))).longValue();
       //设置条件查询规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
                        criteria.andSpecIdEqualTo(specId);
            List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(example);
            //把规格选项添加到map集合中
             map.put("optionList",tbSpecificationOptions);
        }
        //[{"id":33,"text":"电视屏幕尺寸","optionList":[{}{}{}]},{"id":32,"text":"机身内存"},{"id":28,"text":"手机屏幕尺寸","optionList":[{}{}{}]}]
        return specList;
    }

}
