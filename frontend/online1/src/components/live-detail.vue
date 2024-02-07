<template>
    <h2>学生姓名：{{name}}</h2>
    <h2>学生性别：{{sex}}</h2>
    <h2>当前求和为：{{number}}</h2>
    <button @click="triggerEvent1">触发全局事件总线APP上的event1事件</button>
    <button @click="stuEmitSch">触发学校组件上的schEvent事件</button>
</template>

<script setup lang="ts">
    import {onMounted,ref} from 'vue';
    import $bus from '@/utils/event-bus';
    let name = '张三'
    let sex = '男'
    let number = ref(0)

    onMounted(() => {
         //绑定总线事件:emitter.on('事件名',事件方法)
         $bus.on('stuEvent',function(schoolName){
            alert(`我叫${name},我是${schoolName}的学生`)
        })
    })
    let user = {
            name: '张XXX',
            age: 18
        }
    function triggerEvent1(){
        $bus.emit('event1',user);
    }

    function stuEmitSch(){
        $bus.emit('schEvent',name);
    }

    <h2>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>2.模板语法</title>
        <!-- <script src="https://cdn.bootcdn.net/ajax/libs/vue/2.7.0/vue.js"></script> -->
        <script src="https://cdn.bootcdn.net/ajax/libs/vue/3.3.4/vue.global.js"></script>
    </head>

    <body>
        <div id="root">
            <h2>Number+1:{{number + 1}}</h2>
            <h2>字符串倒转:{{ mess.split('').reverse().join()}}</h2>
            <h3>今天的天气:{{ isHot ? '炎热' : '凉爽' }}</h3>
            <button v-on:click="changeWeather">切换天气</button>
        </div>

        <script>
            let {createApp,ref} = Vue;
             createApp({
            //     data(){
            //         return {
            //             number:10,
            //             mess:'Hello,World~',
            //             isHot:true
            //         }
            //     },
            //     methods:{
            //         changeWeather(){
            //             this.isHot = !this.isHot
            //         },
            //     },
            // }).mount('#root')
            setup() {
                let number = 10;
                let mess = 'Hello,World';
                let isHot = ref(true) ;
                function changeWeather(){
                        isHot.value = !isHot.value
                    }
                return {
                number,mess,isHot,changeWeather
                }
            }
        }).mount('#root')
        </script>
    </body>
    </html>
    </h2>
</script>