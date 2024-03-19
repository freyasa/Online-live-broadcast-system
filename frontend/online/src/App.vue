<script setup lang="ts">
import {useRouter} from "vue-router";
import {ref, reactive, toRefs, onMounted,} from 'vue'
import {login} from './global/global'
import {video, mine, exit, defaultAvatar} from "./global/static/base64Template";
import User from "./global/vo/User";
import {Search} from '@element-plus/icons-vue'
import {logo} from "./global/static/base64Template";
import axios from 'axios'


const router = useRouter();
let imgHover = false;
let cardHover = false;
let cardUp = false;
let showLoginPage = ref(false);
let showWhichPage = 'login' // login or register
let handleUser = ref(new User());
let login_button = ref(true);
let userPasswordError = ref(false);
let affirmPasswordError = ref(false);
let successRegister = ref(false);
let loginState = ref(false);
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

const switchPage = () => {
  let login_docu = document.getElementById('login_menu');
  let reg_docu = document.getElementById('register_menu');

  if (showWhichPage === 'login') {
    login_docu.style.color = "#33A2EF";
    reg_docu.style.color = "#606266";
    showWhichPage = 'reg'
    login_button.value = true;
  } else {
    login_docu.style.color = "#606266";
    reg_docu.style.color = "#33A2EF";
    showWhichPage = 'login'
    login_button.value = false;
  }
}

const handleLogin = () => {
  axios
      .post("http://localhost:5173/dev/user/login", {
        userAccount: handleUser.value.userAccount,
        userPassword: handleUser.value.userPassword,
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code !== 200) {
          successRegister.value = false;
          userPasswordError.value = true;
          affirmPasswordError.value = false;
        } else {
          login.user = data.data.data;
          console.log(login.user.userAvatar)
          console.log(login.user.userAvatar == null)
          console.log(login.user.userAvatar === '')
          if (login.user.userAvatar == null || login.user.userAvatar === '') {
            login.user.userAvatar = defaultAvatar;
          }
          login.loginState = true;
          loginState.value = true;
          localStorage['user'] = JSON.stringify(login.user);
          showLoginPage.value = false;
          console.log(login.user)
        }
      })
      .catch((err) => {
        console.log(err);
        successRegister.value = false;
        userPasswordError.value = true;
        affirmPasswordError.value = false;
      })
}

const handleRegister = () => {
  console.log(handleUser.value)
  if (handleUser.value.userPassword === handleUser.value.userAffirmPassword) {
    axios
        .post("http://localhost:5173/dev/user/register", {
          userAccount: handleUser.value.userAccount,
          userPassword: handleUser.value.userPassword,
          checkPassword: handleUser.value.userAffirmPassword
        })
        .then((data) => {
          console.log(data.data);
          successRegister.value = true;
          userPasswordError.value = false;
          affirmPasswordError.value = false;
          switchPage();
          if (data.data.code !== 200) {
            successRegister.value = false;
            userPasswordError.value = false;
            affirmPasswordError.value = true;
          }
        })
        .catch((err) => {
          console.log(err);
          successRegister.value = false;
          userPasswordError.value = false;
          affirmPasswordError.value = true;
        });
  } else {
    affirmPasswordError.value = true;
  }
}

const exitLogin = () => {
  login.loginState = false;
  loginState.value = false;
  login.user = {};
  localStorage['user'] = '';
}

window.setInterval(displayControl, 100)

onMounted(() => {
  if (localStorage['user'] != null && localStorage['user'] != '') {
    login.user = JSON.parse(localStorage['user']);
    let token = login.user.token;
    axios
        .get("http://localhost:5173/dev/user/getCurrentUser",
            {
              headers: {
                authorization: token,
              }
            })
        .then((data) => {
          // console.log("data.data");
          // console.log(data.data);
          login.user.uuid = data.data.data.uuid;
          login.user.userAccount = data.data.data.useraccount;
          login.user.userName = data.data.data.username;
          login.user.userSex = data.data.data.usersex;
          login.user.userAge = data.data.data.userage;
          login.user.userAvatar = data.data.data.useravatar;
          login.user.userEmail = data.data.data.useremail;
          login.user.userSignature = data.data.data.userSignature;
          login.loginState = true;
          loginState.value = true;
          localStorage['user'] = JSON.stringify(login.user);
        })
        .catch((err) => {
          console.log(err);
          login.loginState = false;
          loginState.value = false;
        })
  } else {
    login.loginState = false;
    loginState.value = false;
  }
  console.log(login.user.userAvatar)
  // console.log("login.user")
  // console.log(login.user)
})
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
              <el-image fit="fill" :src="logo"
                        style="width: 190px; height: 190px; margin-top: 5px" @click="toPagePath('/')"/>
              <!--              <el-image fit="fill" src="http://localhost:5173/images/2024/01/25/logo_transparent.png"-->
              <!--              <span style="margin-left: 1em; font-size: 18px"></span>-->
              <!--              <img src="http://localhost:5173/2024/02/04/L57lXrGq.png" style="width: 190px; height: 190px; margin-top: 5px" @click="toPagePath('/')"/>-->
            </el-menu-item>
            <el-menu-item index="2" @click="toPagePath('/')">首页</el-menu-item>
            <el-menu-item index="3">游戏</el-menu-item>
            <el-menu-item index="4">娱乐</el-menu-item>
            <el-menu-item index="5">电台</el-menu-item>
            <el-menu-item index="50" style="margin-left: 150px; width: 600px">
              <el-input
                  style=""
                  v-model="input2"
                  class="w-100 m-2"
                  placeholder="Please Input"
                  :prefix-icon="Search"
              />
              &nbsp;&nbsp;&nbsp;&nbsp;
              <el-button>搜索</el-button>
            </el-menu-item>

            <div class="flex-grow"/>
            <el-menu-item index="6" v-show="!loginState" @click="showLoginPage = true">登录</el-menu-item>
            <el-menu-item index="6" v-show="loginState" style="border-bottom: 0">
              <!--              <div id="userAvatarDiv" class="personal_info"-->
              <!--                   style="width: 76px; height: 76px; z-index: 10; position: absolute">-->
              <!--                <el-avatar :size="36"-->
              <!--                           :src="'https://i2.hdslb.com/bfs/face/816b2f8c9eb9bcc2784e923cd75dd42ec2c087a5.jpg'"-->
              <!--                           style="vertical-align: top"/>-->
              <!--<div>-->
              <img @click="toPagePath('/my-info')" @mouseover="imgHover=true" @mouseout="imgHover=false" id="userAvatar"
                   :src="login.user.userAvatar"
                   style="border-radius: 18px; width: 36px; height: 36px; z-index: 100"/>
              <!--              </div>-->
              <!--              @mouseout="userAvatarDown"-->
              <div>
                <el-card @mouseover="cardHover=true" @mouseout="cardHover=false" id="userCard"
                         style="width: 300px; position: fixed; top: 65px; right: 115px; z-index: 1;
                         border-radius: 10px; visibility: hidden; cursor: default; line-height: normal">

                  <div style="height: 20px"></div>

                  <div style="font-weight: 500; font-size: 16px; line-height: normal; text-align: center">
                    {{ login.user.username }}
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
                      <div class="listItemFont" style="display: inline-flex" @click="toPagePath('/my-live')">
                        我的直播间
                      </div>
                    </div>
                    <div class="listItem">
                      <div style="display: inline-flex;margin-top: 10px">
                        <el-image style="width: 20px; height: 20px; margin-left: 20px" :src="exit" :fit="'fill'"/>
                      </div>
                      <div class="listItemFont" style="display: inline-flex" @click="exitLogin">
                        退出登录
                      </div>
                    </div>
                  </div>

                </el-card>
              </div>
            </el-menu-item>

            <el-menu-item index="7" style="position: absolute; right: 80px">
              <el-button color="#ff4b98" :dark='false' style="color: white" @click="toPagePath('/my-live')">我要开播
              </el-button>
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

  <el-dialog v-model="showLoginPage" title="" width="600">
    <div style="text-align: center;">
      <div style="display: inline-flex">
        <div id="login_menu" class="login_menu" tabindex="1" @click="switchPage"
             style="color: #33A2EF">
          <span style="font-size: 20px">&nbsp;&nbsp;&nbsp;&nbsp;登录&nbsp;&nbsp;&nbsp;&nbsp;</span>
        </div>
        <el-divider direction="vertical"/>
        <div id="register_menu" class="login_menu" tabindex="2" @click="switchPage">
          <span style="font-size: 20px">&nbsp;&nbsp;&nbsp;&nbsp;注册&nbsp;&nbsp;&nbsp;&nbsp;</span>
        </div>
      </div>

      <div style="margin-top: 20px">
        <el-alert v-show="successRegister" title="注册成功" type="success" show-icon/>
        <el-alert v-show="affirmPasswordError" title="用户名已存在或两次密码不一致" type="error" show-icon/>
        <el-alert v-show="userPasswordError" title="用户名或密码错误" type="error" show-icon/>
      </div>

      <div style="width: 60%; margin-left: 20%; margin-top: 60px">
        <div style="">
          <el-input v-model="handleUser.userAccount" placeholder="">
            <template #prepend>
              <div style="font-size: 15px; color: #555555">用户名</div>
            </template>
          </el-input>
        </div>
        <div style="margin-top: 40px">
          <el-input v-model="handleUser.userPassword" placeholder="" show-password>
            <template #prepend>
              <div style="font-size: 15px; color: #555555">密&nbsp;&nbsp;&nbsp;码</div>
            </template>
          </el-input>
        </div>
        <div style="margin-top: 20px" v-show="!login_button">
          <el-input v-model="handleUser.userAffirmPassword" placeholder="" show-password>
            <template #prepend>
              <div style="font-size: 15px; color: #555555">确认密码</div>
            </template>
          </el-input>
        </div>
        <div style="margin-top: 60px" v-show="login_button">
          <el-button @click="handleLogin">登&nbsp;&nbsp;录</el-button>
        </div>

        <div style="margin-top: 60px" v-show="!login_button">
          <el-button @click="handleRegister">注&nbsp;&nbsp;册</el-button>
        </div>
      </div>
    </div>
  </el-dialog>

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
  flex-grow: 0.6;
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

.login_menu {
  font-weight: 600;
}

.login_menu {
  cursor: pointer;
}

</style>