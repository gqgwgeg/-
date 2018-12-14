package pyg.shang.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbBrand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pyg.com.utis.PageResult;
import pyg.com.utis.PygResult;
import pyg.shang.Service.BranService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/brand")
public class BrandController {


    @Reference(timeout =1000000)
    private BranService branService;

    //查询所有
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return branService.findAll();
    }


    //分页查询
    @RequestMapping("/findPage/{page}/{rows}")
    public PageResult findPage(@PathVariable Integer page, @PathVariable Integer rows) {

        return branService.findPage(page, rows);

    }

    @RequestMapping("/add")
    public PygResult add(@RequestBody TbBrand tbBrand) {
        try {
            branService.add(tbBrand);
            return new PygResult(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "添加失败");
        }

    }


    @RequestMapping("/update")
    public PygResult update(@RequestBody TbBrand tbBrand) {
        try {
            branService.update(tbBrand);
            return new PygResult(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "添加失败");
        }


    }

    @RequestMapping("/findOne/{id}")
    public TbBrand findOne(@PathVariable Long id) {

        TbBrand brand = branService.findOne(id);
        return brand;
    }


    @RequestMapping("/del/{ids}")
    public PygResult del(@PathVariable Long[] ids){
    try {
        branService.del(ids);
        return new PygResult(true,"删除成功");
    } catch (Exception e) {
        e.printStackTrace();
        return new PygResult(false,"删除失败");
    }
}

//品牌数据的下拉显示
    @RequestMapping("findBrandList")
    public List<Map> findBrandList(){
        return branService.findBrandList();

    }
}