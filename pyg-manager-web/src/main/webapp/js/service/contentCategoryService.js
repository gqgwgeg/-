//服务层
app.service('contentCategoryService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../contentCategory/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../contentCategory/findPage/'+page+'/'+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../contentCategory/findOne/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../contentCategory/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../contentCategory/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../contentCategory/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../contentCategory/search/'+page+"/"+rows, searchEntity);
	}    	
});
