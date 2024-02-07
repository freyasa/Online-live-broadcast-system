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
</script>