
'use strict';

var myapp=angular.module('testapp',['ngRoute','app_controllers']);


myapp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/index', {
                templateUrl: 'partials/login.html',
                controller: 'login_controller'
            }).when('/auth', {
            	templateUrl: 'partials/index.html',
                controller: 'index_controller'
            }
            ).otherwise({
                redirectTo: '/index'
            });
    }]);