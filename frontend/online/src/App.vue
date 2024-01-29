<script setup lang="ts">
// import HelloWorld from './components/HelloWorld.vue'
// import Login from './components/login.vue'
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import {login} from './global/global'
const router = useRouter();

const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

// const router = useRouter();
// const logo = "https://img2.imgtp.com/2024/01/23/5NAXxwFs.png";
// const logo = require('./assets/logo.png')
const content = ref('content')
const data = reactive({
  patternVisible: false,
  debugVisible: false,
  aboutExeVisible: false,
})
const {patternVisible, debugVisible, aboutExeVisible} = toRefs(data)

// const state = reactive({
//   circleUrl:
//       'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
//   sizeList: ['small', '', 'large'] as const,
// })

// const {circleUrl, sizeList} = toRefs(state)

</script>

<template>
  <el-backtop :right="100" :bottom="100"/>

  <div>
    <el-container>
      <el-header style="padding: 0; height: 60px">
        <el-affix :offset="0">
          <el-menu
              :default-active="activeIndex"
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"
          >
            <el-menu-item index="1">
              <el-image fit="fill" src="http://localhost:5173/images/2024/01/25/logo_transparent.png"
                        style="width: 190px; height: 190px; margin-top: 5px" @click="toPagePath('/')"/>
              <!--              <span style="margin-left: 1em; font-size: 18px"></span>-->
            </el-menu-item>
            <el-menu-item index="2" @click="toPagePath('/')">首页</el-menu-item>
            <el-menu-item index="3">游戏</el-menu-item>
            <el-menu-item index="4">娱乐</el-menu-item>
            <el-menu-item index="5">电台</el-menu-item>
            <div class="flex-grow"/>
            <el-menu-item index="6" v-if="!login.loginState">登录</el-menu-item>
            <el-menu-item index="6" v-else>
              <div>
                <el-avatar class="personal_info" :size="36" :src="circleUrl" style="vertical-align: top; margin-top: 10px;"/>
              </div>
            </el-menu-item>
          </el-menu>
        </el-affix>
      </el-header>

      <el-main style="padding: 0; text-align: center">

        <router-view></router-view>
      </el-main>

      <el-footer style="padding: 0">
        <div style="background-color: #213547">
          <br/>
        </div>

      </el-footer>
    </el-container>
  </div>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
  <!-- <Login msg=""></Login> -->
  <!--  <div>-->

  <!--    <button @click="toPagePath('/login')">login</button>-->
  <!--    <button @click="toPagePath('/reg')">reg</button>-->
  <!--  </div>-->
</template>


<style lang="less" scoped>
.el-menu-demo {

}

//设置了默认左边框为白色
.el-menu-item {
  border-left: #fff solid 0;
  color: #000000 !important;
  font-weight: 450;
}

//设置鼠标悬停时el-menu-item的样式
.el-menu-item:hover {
  //border-left:#33A2EF solid 6px !important;
  //background-color: #E2EFF9 !important;
  //color: #38B2FF !important;
  //less语法，实现鼠标悬停时icon变色
  i {
    color: #38B2FF;
  }

  background-color: white !important;
}


//设置选中el-menu-item时的样式
.el-menu-item.is-active {
  //border-left: #33A2EF solid 6px !important;
  //background-color: #E2EFF9 !important;
  color: #38B2FF !important;
  border-bottom: 0 !important;
}

.flex-grow {
  flex-grow: 0.8;
}

.personal_info {
  animation: fly 0.1s linear;
  animation-fill-mode: both;

}

@keyframes fly {
  0% {
  }
  100% {
    //box-shadow: 6px 8px 12px #7f7f7f, -3px 4px 6px #7f7f7f;
    //width: 420px;
    //height: 250px;
    border-radius: 10px;
    background-color: #ffffff;
    box-shadow: var(--el-box-shadow);
    transform: scale(2);
  }
}

</style>