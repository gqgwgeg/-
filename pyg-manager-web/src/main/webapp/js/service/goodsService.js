//服务层
app.service('goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../goods/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../goods/findPage/'+page+'/'+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../goods/findOne/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../goods/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../goods/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../goods/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../goods/search/'+page+"/"+rows, searchEntity);
	}

    //运营商系统审核商品
    this.updateStatus=function(status,ids){
        return $http.get('../goods/updateStatus/'+status+'/'+ids);
    }
});
