 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};

    //status[auditStatus]
    $scope.status=['未审核','已审核','审核未通过','关闭'];//商品状态

    //定义数组存储分类名称
    $scope.itemCatList = [];

    //查询分类，把所有的分类都封装数组中进行存储
    $scope.findCatAllList = function () {
        //调用分类服务，查询所有分类名称
        itemCatService.findAll().success(function (data) {
            //遍历分类数据集合
            for(var i=0;i<data.length;i++){
                //把分类id作为数组角标，在此角标存储分类名称
                $scope.itemCatList[data[i].id]=data[i].name;
            }
        })
    };

    //运营商系统审核商品
	//0，未审核 1,审核通过  2，审核不通过  3，关闭
	$scope.updateStatus = function (status) {
		//调用服务方法
		goodsService.updateStatus(status,$scope.selectIds).success(function (data) {
			//判断
			if(data.success){
				$scope.reloadList();
			}else {
				alert(data.message);
			}
        })
    }
    
});	
