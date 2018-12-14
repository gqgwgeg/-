//定义service服务层

app.service("loginService",function ($http) {
    //把内置服务发送请求方法全部抽取到服务层代码
    //查询所有
    this.loginName=function () {
        return  $http.get("../login/Landing");
    };

    this.loginsj=function () {
        return $http.get("../login/loginsj")
    }
});