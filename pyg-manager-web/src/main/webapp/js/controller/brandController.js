app.controller("brandController", function ($scope,$controller,brandService) {
    //控制器的继承操作
    $controller("baseController",{$scope:$scope});

    $scope.findAll = function () {
        brandService.findAll().success(function (data) {
            $scope.list = data;
        })
    };


    //定义分页查询方法

    $scope.findPage = function (page, rows) {
        //发送分页查询请求
        brandService.findPage(page,rows).success(function (data) {

            $scope.list = data.rows;
            $scope.paginationConf.totalItems = data.total;
        })
    };


    //定义添加品牌的方法
    $scope.add = function () {
        //接受添加,或者返回对象
        var objService=null;

        if($scope.entity.id != null){
            //修改
            objService=brandService.update($scope.entity)

        }else {
            objService = brandService.add($scope.entity);
        }
        objService.success(function (data) {
            if (data.success) {
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    };

    //定义ID查询品牌数据方法

    $scope.findOne=function (id) {
        brandService.findOne(id).success(function (data) {
            $scope.entity=data;
        })
    };
    //定义删除的方法
    $scope.del=function () {
        brandService.del($scope.selectIds).success(function (data) {
            if (data.success) {
                $scope.selectIds=[];
                $scope.reloadList();

            } else {
                alert(data.message);
            }
        })
    };
    

})