app.controller("loginController", function ($scope,loginService) {


    //获取登陆的用户名
    $scope.loginName = function () {
loginService.loginName().success(function (data) {
    $scope.loginName=data.loginName
})
    }

    $scope.loginsj=function () {
        loginService.loginsj().success(function (data) {
            $scope.loginsj=data.loginsj;
        })

    }
});