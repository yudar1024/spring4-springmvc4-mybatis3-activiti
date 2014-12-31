


var controllers = angular.module('app_controllers', []);

controllers.controller('login_controller', ['$scope', '$http', function($scope, $http) {
    $scope.user = {
        name: 'ccc',
        password: 'test'
    }
    $scope.msg = {
        message: 'before login'
    }
    $scope.apitest = function() {
       $http({
    	   method:'GET',
    	   url:'/angularjsdemo/restapi/auth/hello.action'
       }).success(function(data,status,headers,config){
    	   console.log('success');
    	   $scope.user=data;
       }).error(function(data,status,headers,config){
    	   console.log('error');
       });
    }

    $scope.login = function() {
        $http({
     	   method:'POST',
     	   url:'/angularjsdemo/restapi/login.action?username=roger&password=password'
        }).success(function(data,status,headers,config){
     	   console.log('success');
     	   $scope.user=data;
        }).error(function(data,status,headers,config){
     	   console.log('error');
        });
     }
}]);
controllers.controller('index_controller', ['$scope',function($scope) {
	$scope.indexValue='cccc';

}]);
