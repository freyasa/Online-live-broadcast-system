<template>
<h2>学校名称：{{name}}</h2>
<h2>学校地址：{{address}}</h2>
<button @click="triggerEvent2">触发全局事件总线APP上的event1事件</button>
<button @click="schEmitStu">触发学生组件上的stuEvent事件</button>
</template>

<script setup lang="ts">
    import $bus from '@/utils/event-bus';
    import {onMounted} from 'vue';

    let name = '宁夏大学';
    let address = '银川市西夏区';

    //绑定总线事件:emitter.on('事件名',事件方法)
    onMounted(()=>{
            $bus.on('schEvent',(studentName)=>{
                alert(`这是${name},你是${studentName}么~~~`)
            })
        })

    function triggerEvent2(){
        $bus.emit('event1',{name:'李XX',age:22})
    }

    function schEmitStu(){
        $bus.emit('stuEvent',name);
    }
</script>