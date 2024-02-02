<script setup lang="ts">
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import {login} from './global/global'
import {video, mine, exit} from "./global/static/base64Template";

const router = useRouter();
let imgHover = false;
let cardHover = false;
let cardUp = false;

const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

const userAvatarUp = () => {
  let userAvatar = document.getElementById('userAvatar');
  let userCard = document.getElementById('userCard');

  userCard.style.visibility = 'visible'

  const keyframes = [
    {
      //0%
      width: '36px',
      height: '36px'
    },
    {
      // 100%
      width: '76px',
      height: '76px',
      transform: 'translate(10px, 30px)',
      borderRadius: '38px'
    },
  ]

  const options = {
    // 动画执行次数
    iterations: 1,
    // 动画开始时间点
    iterationStart: 0,
    // 动画开始之前的延迟
    delay: 0,
    // 动画结束之后的延迟
    // endDelay: 100,
    // 动画是否在下一周期逆向播放
    direction: 'normal',
    // 动画时长
    duration: 150,
    // 动画前后保持的状态
    fill: 'forwards',
    // 动画缓动类型
    easing: 'ease-in-out',
  }
  userAvatar.animate(
      keyframes, options
  );

  cardUp = true;
}

const userAvatarDown = () => {
  let userAvatar = document.getElementById('userAvatar');
  let userCard = document.getElementById('userCard');
  userCard.style.visibility = 'hidden'

  const keyframes = [
    {
      //0%
      width: '76px',
      height: '76px',
      // transform: 'translate(14px, 20px)',
      borderRadius: '38px'
    },
    {
      // 100%
      width: '36px',
      height: '36px',
      transform: 'translate(0px, 0px)',
      borderRadius: '18px'
    },
  ]

  const options = {
    // 动画执行次数
    iterations: 1,
    // 动画开始时间点
    iterationStart: 0,
    // 动画开始之前的延迟
    delay: 100,
    // 动画结束之后的延迟
    endDelay: 0,
    // 动画是否在下一周期逆向播放
    direction: 'normal',
    // 动画时长
    duration: 150,
    // 动画前后保持的状态
    fill: 'forwards',
    // 动画缓动类型
    easing: 'ease-in-out',
  }
  userAvatar.animate(
      keyframes, options
  );

  cardUp = false;
}

const content = ref('content')
const data = reactive({
  patternVisible: false,
  debugVisible: false,
  aboutExeVisible: false,
})
const {patternVisible, debugVisible, aboutExeVisible} = toRefs(data)


const displayControl = () => {
  if (imgHover && !cardUp) userAvatarUp();
  if (!imgHover && !cardHover && cardUp) userAvatarDown();
}

window.setInterval(displayControl, 100)


</script>

<template>
  <el-backtop :bottom="100">
    <div
        style="
        height: 100%;
        width: 100%;
        background-color: var(--el-bg-color-overlay);
        box-shadow: var(--el-box-shadow-lighter);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      "
    >
      UP
    </div>
  </el-backtop>
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
            <el-menu-item index="6" v-else style="border-bottom: 0">
              <!--              <div id="userAvatarDiv" class="personal_info"-->
              <!--                   style="width: 76px; height: 76px; z-index: 10; position: absolute">-->
              <!--                <el-avatar :size="36"-->
              <!--                           :src="'https://i2.hdslb.com/bfs/face/816b2f8c9eb9bcc2784e923cd75dd42ec2c087a5.jpg'"-->
              <!--                           style="vertical-align: top"/>-->
              <!--<div>-->
              <img @click="toPagePath('/my-info')" @mouseover="imgHover=true" @mouseout="imgHover=false" id="userAvatar"
                   src="https://i2.hdslb.com/bfs/face/816b2f8c9eb9bcc2784e923cd75dd42ec2c087a5.jpg"
                   style="border-radius: 18px; width: 36px; height: 36px; z-index: 100"/>
              <!--              </div>-->
              <!--              @mouseout="userAvatarDown"-->
              <div>
                <el-card @mouseover="cardHover=true" @mouseout="cardHover=false" id="userCard"
                         style="width: 300px; position: fixed; top: 65px; right: 155px; z-index: 1;
                         border-radius: 10px; visibility: visible; cursor: default; line-height: normal">

                  <div style="height: 20px"></div>

                  <div style="font-weight: 500; font-size: 16px; line-height: normal; text-align: center">
                    {{ login.user.userName }}
                  </div>

                  <div style="margin-top: 35px" class="listAll">
                    <div class="listItem">
                      <div style="display: inline-flex;margin-top: 10px">
                        <el-image style="width: 20px; height: 20px; margin-left: 20px" :src="mine" :fit="'fill'"/>
                      </div>
                      <div @click="toPagePath('/my-info')" class="listItemFont" style="display: inline-flex">
                        个人中心
                      </div>
                    </div>
                    <div class="listItem">
                      <div style="display: inline-flex;margin-top: 10px">
                        <el-image style="width: 20px; height: 20px; margin-left: 20px" :src="video" :fit="'fill'"/>
                      </div>
                      <div class="listItemFont" style="display: inline-flex">
                        我的直播间
                      </div>
                    </div>
                    <div class="listItem">
                      <div style="display: inline-flex;margin-top: 10px">
                        <el-image style="width: 20px; height: 20px; margin-left: 20px" :src="exit" :fit="'fill'"/>
                      </div>
                      <div class="listItemFont" style="display: inline-flex">
                        退出登录
                      </div>
                    </div>
                  </div>

                </el-card>
              </div>
            </el-menu-item>

            <el-menu-item index="7" style="position: absolute; right: 80px">
              <el-button color="#ff4b98" :dark='false' style="color: white">我要开播</el-button>
            </el-menu-item>
          </el-menu>
        </el-affix>
      </el-header>

      <el-main style="padding: 0; text-align: center">

        <router-view></router-view>
      </el-main>

      <el-footer style="padding: 0">
<!--        <div style="background-color: #213547">-->
<!--          <br/>-->
<!--        </div>-->

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

.personal_info :hover {
  animation: fly 0.15s linear;
  animation-fill-mode: both;
}

#userAvatar {
  -webkit-backface-visibility: hidden; //隐藏转换的元素的背面
  -webkit-transform-style: preserve-3d; //使被转换的元素的子元素保留其 3D 转换
  -webkit-transform: translate3d(0, 0, 0); //开启GPU硬件加速模式，使用GPU代替CPU渲染动画（在安卓系统中有时会有莫名其妙的BUG，建议慎重）
}

@keyframes fly {
  0% {
  }
  100% {
    //box-shadow: 6px 8px 12px #7f7f7f, -3px 4px 6px #7f7f7f;
    width: 76px;
    height: 76px;
    //left: 20px;
    transform: translate(10px, 0);
    //margin-left: -20px;
    margin-top: 15px;
    border-radius: 38px;
    //background-color: #ffffff;
    //box-shadow: var(--el-box-shadow);
    //transform: scale(2);
  }
}

.listItem {
  height: 40px;
  width: 252px;
  border-radius: 6px;
  color: #61666d;
  font-weight: 600;
  cursor: pointer;
  font-family: 'PingFang SC';
  //display: inline-block;
  //background-color: pink;
  margin-top: 2px;
}

.listAll :hover {
  background-color: #e1e2e3;
  //border: 1px solid;
}

.listItemFont {
  font-weight: 600;
  color: #61666d;
  margin-left: 20px;
}

</style>