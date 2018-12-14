 //控制层 
app.controller('goodsController',function($scope,$controller,
										  goodsService,
										  itemCatService,
										  typeTemplateService,
										  uploadService){
	
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
		var serviceObject;
		//服务层对象
		if($scope.entity.TbGoods.id!=null){
			//如果有ID
			serviceObject=goodsService.update($scope.entity); //修改
		}else{
			//获取富文本的文本数据赋值给实体属性
			$scope.entity.TbGoodsDesc.introduction=editor.html();
			serviceObject=goodsService.add($scope.entity);//增加

		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert("添加成功");
					//添加成功清空文本
					$scope.entity={}
					//清空富文本数据
					editor.html('');
				}else{
					alert(response.message);
				}
			}		
		);				
	};

	//定义一个查询分类的方法
	//默认查询顶级节点
	$scope.findItemCat1List=function () {
		itemCatService.ItemCatOne(0).success(function (data) {
			$scope.cat1List=data;
        })
    }
	//加载商品分类的二级节点,二级节点必须根据一级节点选中节点来进行加载
	//使用 angaljs  监听服务$watch 服务 监听上级节点变化,实现多级联动查询效果
	//newValue:当前变化的值
	//oldValue:变化之前的值
	$scope.$watch('entity.TbGoods.category1Id',function (newValue, oldValue) {
		//查询二级节点
		itemCatService.ItemCatOne(newValue).success(function (data) {
			$scope.cat2List=data;
        })
    })

//监听二级节点查询三级节点
	$scope.$watch('entity.TbGoods.category2Id',function (newValue, oldValue) {
		//查询三级节点
		itemCatService.ItemCatOne(newValue).success(function (data) {
			$scope.cat3List=data;
        })
    })
    // //初始化对象
    // $scope.entity={TbGoods:{}}
	//监控三级节点的变化查询模板ID
	$scope.$watch('entity.TbGoods.category3Id',function (newValue, oldValue) {
		//根据id查询商品的分类对象
		itemCatService.findOne(newValue).success(function (data) {
			$scope.entity.TbGoods.typeTemplateId=data.typeId;
        })

    })
	//初始化对象组装参数 itemImages 
	$scope.entity={tbGoodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]}};

//监控分类模板id的变化 ,查询出模板对象获取当前分类中的品牌数据,此时模板当中存储了当前分类的所有品牌
	$scope.$watch('entity.TbGoods.typeTemplateId',function (newValue, oldValue) {
		typeTemplateService.findOne(newValue).success(function (data) {
           //先给模板对象赋值,进行初始化
			$scope.typeTemplate=data;
			//把字符串json 转成 json对象
			$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
			//js字符串数组转换成对象
			$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
        })
		typeTemplateService.findSpecList(newValue).success(function (data) {

			$scope.specList=data;
        })
    })

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

//文件上传方法
	$scope.uploadFile=function () {
      uploadService.uploadFile().success(function (data) {

          if(data.success){
              $scope.image_entity.url=data.message;

          }else {
              alert(data.message)
          }
      })
    };


//组装图片地址 颜色参数
	$scope.add_image_entity=function () {
		$scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);
    }


    //判断选中的是规格中的那个属性值
    $scope.searchSpecOption=function (list, key, name) {
           //循环
		for(var i=0;i<list.length;i++){
			if(list[i][key]==name){
				return list[i];
			}
		}
		return null;
    }

 //组装规格选项数据
$scope.updateSecOption=function ($event,name,option) {
    //判断是否是选中规格中的属性值
	//获取商品描述中的 规格数据
        var specList= $scope.entity.tbGoodsDesc.specificationItems;

        var obj= $scope.searchSpecOption(specList,'attributeName',name);
      //判断是否为空
        if(obj!=null){
        	//判断是否是选中事件
			if($event.target.checked){
				obj.attributeValue.push(option);
			}else {
				//取消事件
				obj.attributeValue.splice(obj.attributeValue.indexOf(option),1);
				//判断如果规格选项已经删除完了,需要把整个规格对象也删除
				if(obj.attributeValue.length==0){
					specList.splice(specList.indexOf(obj),1);
				}

			}
		}else {
        	   //第一次选中时 他是空数组 没有值需要初始化对值的添加
            $scope.entity.tbGoodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[option]});
		}
}
//定义一个方法,根据选中的规格选项动态生成sku行

	$scope.createitems=function () {

		//定义初始化 sku行
		$scope.entity.items=[{spec:{},price:0,num:9999,status:"1",isDefault:"0"}]

		//获取选中的规格选项数据
       var specList= $scope.entity.tbGoodsDesc.specificationItems;

		//判断规格选项是否为空
         if(specList.length==0){
             //删除最后一行
             $scope.entity.items=[];
		 }

		//循环选中的规格选项,根据规格选项生成sku行
		for(var i=0;i<specList.length;i++){
			//添加行items
		$scope.entity.items=addColumn($scope.entity.items,specList[i].attributeName,specList[i].attributeValue)


		}
    }


    addColumn=function (items, name, values) {
		//定义新的数组,封装新组装结果
		var newList=[];
		for(var i=0;i<items.length;i++){
			//获取旧的行
		var oldRow=	items[i];
		//根据选中规格选项组装行
			//循环规格选项
		for(var j=0;j<values.length;j++){
			//深克隆操作,新建一行
			var newRow=JSON.parse(JSON.stringify(oldRow));
			newRow.spec[name]=values[j];
			newList.push(newRow);
		}
		}
		return newList;
    };

   $scope.status=['未审核','已审核','关闭','驳回'];

	$scope.itemCatList11=[];

	$scope.itemListfindAll=function () {
		itemCatService.findAll().success(function (data) {
			for(var i=0;i<data.length;i++){
				$scope.itemCatList11[data[i].id]=data[i].name;
			}
        })
    };

	$scope.shelfStatus=['下架商品',"上架商品"];
    //商品上下架
	$scope.updateIsMarketable=function (isMarketable) {
		goodsService.iMarketable(isMarketable,$scope.selectIds).success(function (data) {
			if(data.success){
				$scope.reloadList();

			}else {

				alert(data.message)
			}
        })
    }
});	
