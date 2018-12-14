package pyg.shang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.vo.Specification;
import org.springframework.web.bind.annotation.*;
import pyg.com.utis.PageResult;
import pyg.com.utis.PygResult;
import pyg.shang.Service.specificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specification")
public class specificationController {

    @Reference(timeout = 100000)
    private specificationService specificationService;


    //规格数据的分页查询
    @RequestMapping("findPage/{page}/{rows}")
    public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows) {
        return specificationService.findPage(page, rows);
    }


    //添加规格及规格选项表2张表的数据使用包装类对象Specification
    @RequestMapping("add")
    public PygResult add(@RequestBody Specification specification) {
        try {
            specificationService.add(specification);
            return new PygResult(true, "添加成功");
        } catch (Exception e) {
            return new PygResult(false, "添加失败");
        }

    }

    @RequestMapping("dele/{ids}")
    public PygResult del(@PathVariable Long[] ids) {

        try {
            specificationService.del(ids);
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }
    }
    //id查询
    @RequestMapping("findOne/{id}")
    public Specification findOne(@PathVariable Long id){
          return   specificationService.findOne(id);
    }

//修改规格及规格选项数据
    @RequestMapping("update")
    public PygResult update(@RequestBody Specification specification){

        try {
            specificationService.update(specification);
            return new PygResult(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "失败");
        }
    }

    @RequestMapping("findSpecificationList")
    public List<Map> findSpecificationList(){
     return specificationService.findSpecificationList();

    }
}
