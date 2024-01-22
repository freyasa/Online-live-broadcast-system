<script setup lang="ts">
// import HelloWorld from './components/HelloWorld.vue'
// import Login from './components/login.vue'
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'

const router = useRouter();
const content = ref('content')
const data = reactive({
  patternVisible: false,
  debugVisible: false,
  aboutExeVisible: false,
})
const {patternVisible, debugVisible, aboutExeVisible} = toRefs(data)
const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

</script>

<template>
  <el-backtop :right="100" :bottom="100"/>

  <div>
    <el-container>
      <el-header style="padding: 0">
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
        >


          <el-menu-item index="1">
            <el-avatar shape="square" fit="square" src="./logo.png" />
              <span style="margin-left: 1em; font-size: 18px">快旭直播</span>
          </el-menu-item>

          <el-menu-item index="3" disabled>Info</el-menu-item>

          <div class="flex-grow"/>


          <el-menu-item index="4">Orders</el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <div style="background-color: #213547">
          Main
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
          <br/>
        </div>
      </el-main>
      <el-footer>
        <div style="background-color: #213547">Footer</div>
      </el-footer>
    </el-container>
  </div>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo"/>
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo"/>
    </a>
  </div>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
  <!-- <Login msg=""></Login> -->
  <div>

    <button @click="toPagePath('/login')">login</button>
    <button @click="toPagePath('/reg')">reg</button>
    <router-view></router-view>
  </div>
</template>

<!--<style scoped>-->
/*.logo {*/
/*  height: 6em;*/
/*  padding: 1.5em;*/
/*  will-change: filter;*/
/*  transition: filter 300ms;*/
/*}*/

/*.logo:hover {*/
/*  filter: drop-shadow(0 0 2em #646cffaa);*/
/*}*/

/*.logo.vue:hover {*/
/*  filter: drop-shadow(0 0 2em #42b883aa);*/
/*}*/
<!--</style>-->

<style lang="less" scoped>
//设置了默认左边框为白色
.el-menu-item{
  border-left:#fff solid 6px;
}
//设置鼠标悬停时el-menu-item的样式
.el-menu-item:hover{
  //border-left:#33A2EF solid 6px !important;
  //background-color: #E2EFF9 !important;
  //color: #38B2FF !important;
  //less语法，实现鼠标悬停时icon变色
  i {
    color: #38B2FF;
  }
}
//设置选中el-menu-item时的样式
.el-menu-item.is-active {
  border-left:#33A2EF solid 6px !important;
  background-color: #E2EFF9 !important;
  color: #38B2FF !important;
}

.flex-grow {
  flex-grow: 0.9;
}

</style>