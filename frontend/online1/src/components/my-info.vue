<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1.事件的基本使用</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/vue/3.3.4/vue.global.js"></script>
</head>
<body>
    <!--
        1. prevent: 阻止默认事件（常用）
        2. stop: 阻止事件冒泡（常用）
        3. once: 事件只触发一次（常用）
        4. capture: 使用事件的捕获模式
        5. self:  只有event.target是当前操作的元素时才出发事件
    -->
    <div id="root">
        <h2>你好，欢迎来到{{name}}学习！</h2>
         <!--prevent: 阻止默认事件（常用）：即点击了a标签后，只执行showInfo方法中的提示信息，不跳转href中的网址-->
        <a href="https://www.baidu.com" v-on:click.prevent="showInfo">点我提示信息-1</a>
        <!--stop: 阻止事件冒泡。不加stop，当点击按钮时，div的点击事件也会执行。-->
        <div class="demo1" @click="chickDiv">
            <button @click.stop="clickA">点我提示信息-2</button>
            <a href="https://www.baidu.com" v-on:click.stop.prevent="showInfo">点我提示信息-3</a>
        </div>
        <!--once: 事件只触发一次（常用）:即第一次点击时出发事件，再次点击时不触发事件-->
        <button @click.once  ="showInfo">点我提示信息-4</button>
         <!--capture: 使用事件的捕获模式。事件是先捕获在冒泡，点击div2时，
            先捕获的是div1.但是冒泡时从内向外的。使用capture，捕获到就冒泡。-->
        <div class="box1" @click.capture="showMsg('666666')">
            div1
            <div class="box2" @click="showMsg('888888')">
                div2
            </div>
        </div>
        <!--self:  只有event.target是当前操作的元素时才触发事件 -->
        <div id="demo1" class="demo1" @click.self="chickDiv">
            <button @click  ="clickA">点我提示信息-5</button>
        </div>
    </div>
    <script>
        const app = Vue.createApp({
          setup() {
            let name = 'NIIT';

            let showInfo = function(){
                alert('你好同学~~~~~~');
            }

            let chickDiv = function(){
                alert("你好，Div被点击了");
            }

            let clickA = function(){
                alert("你好，Button按钮被点击了");
            }
            function showMsg(msg){
                alert(msg)
            }
            return{name,showInfo,chickDiv,clickA,showMsg}
          }
        }).mount('#root')
    </script>
</body>
<style>
    .demo1{
        background-color: aqua;
        width: 300px;
        height: 200px;
    }
    .box1{
        background-color: antiquewhite;
        width: 300px;
        height: 100px;
    }
    .box2{
        background-color: aquamarine;
        width: 200px;
        height: 50px;
    }
</style>
</html>