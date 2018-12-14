app.controller("baseController", function ($scope) {


    //定义分页查询方法
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage)

    };
//分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 5,
        itemsPerPage: 5,
        perPageOptions: [5, 10, 15, 20, 25],
        onChange: function () {
            $scope.reloadList();
        }
    };


    //定义数组封装id参数
    $scope.selectIds = [];

    //组装选中的id参数
    $scope.updateSelection = function ($event, id) {
        //判断是否是checkbox选中事件
        if ($event.target.checked) {
            $scope.selectIds.push(id);

        } else {
            //取消事件
            $scope.selectIds.splice($scope.selectIds.indexOf(id), 1);
        }
    };


//模板数据json字符串 转换为对象进行回显
    $scope.jsonType = function (jsonstr,key) {
        var jsonT= JSON.parse(jsonstr);
        var a="";
        for (var i=0;jsonT.length>i;i++) {
            //判断拼接i
            if(i>0){
                a+=","
            }
            a+=jsonT[i][key]
        }
        //返回拼接好的字符串
        return a;
    };




});