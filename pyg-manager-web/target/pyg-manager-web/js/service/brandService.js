//定义service服务层

app.service("brandService",function ($http) {
    //把内置服务发送请求方法全部抽取到服务层代码
    //查询所有
    this.findAll=function () {
        return    $http.get("../brand/findAll");
    };
    //分页查询
    this.findPage=function (page, rows) {
        return $http.get("../brand/findPage/" + page + "/" + rows);
    };
    //添加
    this.add=function (entity) {
        return $http.post("../brand/add",entity);
    };
    //修改
    this.update=function (entity) {
        return $http.post("../brand/update",entity);
    };
    //批量删除
    this.del=function (ids) {
        return  $http.get("../brand/del/"+ids);
    };
    //根据id查询
    this.findOne=function (id) {
        return  $http.get("../brand/findOne/"+id);
    }
    this.findBrandList=function () {
        return $http.get("../brand/findBrandList")
    }

});