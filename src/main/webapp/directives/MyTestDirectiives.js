/**
 * Created by chenluo on 2014/12/10.
 */
var directiveModel= angular.module('dirTest',[]);

directiveModel.run(function ($templateCache){
   $templateCache.put('hello.html','<div> this is my hello template');
});

directiveModel.directive('hello',function($templateCache){
        return {
            //表示可以作为元素的属性A，元素E，C class，M注释使用
            restrict:'AECM',
            template:$templateCache.get('hello.html'),
            replace:true
        }
    }
);

