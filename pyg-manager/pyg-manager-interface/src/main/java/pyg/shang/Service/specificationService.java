package pyg.shang.Service;

import com.pyg.vo.Specification;
import pyg.com.utis.PageResult;

import java.util.List;
import java.util.Map;

public interface specificationService {

    //规格表分页查询
    public PageResult findPage(Integer pageNum, Integer rows);

    //添加规格数据
    public void add(Specification specification);

    //删除规格数据
    public void del(Long[] ids);

    //根据id查询数据
    public Specification findOne(Long id);


    //修改数据
    public void update(Specification specification);

   public List<Map> findSpecificationList();
}
