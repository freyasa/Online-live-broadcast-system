<script lang="ts" setup>
import {login} from "../global/global";
import User from "../global/vo/User";
import {ref} from "vue";

let modifyUser = ref(new User());
for (let i in modifyUser) {
  modifyUser[i] = login.user[i];
  // console.log(modifyUser[i])
}

const preModifyInfo = () => {
  const displayElement = document.getElementsByClassName('display')
  const modifyElement = document.getElementsByClassName('modify')
  for (let i = 0; i < displayElement.length; i ++) {
    displayElement[i].style.display='none';
  }

  for (let i = 0; i < modifyElement.length; i ++) {
    modifyElement[i].style.display='block'
  }
}

const cancelModifyInfo = () => {
  const displayElement = document.getElementsByClassName('display')
  const modifyElement = document.getElementsByClassName('modify')
  for (let i = 0; i < displayElement.length; i ++) {
    displayElement[i].style.display='block';
  }

  for (let i = 0; i < modifyElement.length; i ++) {
    modifyElement[i].style.display='none';
  }
}

const affirmModifyInfo = () => {

}
</script>

<template>
  <!--  <div style="height: 1080px;-->
  <!--              background-image: linear-gradient(to bottom, transparent 70%, white), url('http://8.140.143.119:8000/images/2024/02/01/live_background.webp');">-->

  <!--  </div>-->

  <!--  <div style="font-size: 30px; text-align: left; margin-left: 20px">我的信息</div>-->
  <div style="background-color: #f0f0f0; height: calc(100vh - 60px)">
    <div style="width: 70%; background-image: url('https://img2.imgtp.com/2024/02/06/3Et6amP8.webp');
              margin-left: 15%; height: 180px; text-align: left; border-radius: 2px">
      <div style="display: inline-flex; margin-left: 75px; margin-top: 100px">
        <el-avatar :size="64" style=""
                   src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
        <div style="color: white; font-weight: 600; line-height: 64px; margin-left: 20px; font-size: 20px">
          {{ login.user.userName }}
        </div>
      </div>
    </div>

    <div
        style="text-align: left; width: 70%; margin-left: 15%; margin-top: 10px; background-color: white; border-radius: 2px">
      <br/>
      <div style="font-weight: 500; margin-left: 30px; font-size: 25px">个人设置</div>

      <div style="margin-left: 100px; margin-top: 20px">
        <div class="display">
          <span class="attribute">uuid</span>:
          <span class="value"> {{ login.user.uuid }}</span>
        </div>

        <div class="display">
          <span class="attribute">用户名</span>:
          <span class="value"> {{ login.user.userAccount }}</span>
        </div>

        <div class="display">
          <span class="attribute">性别</span>:
          <span class="value"> {{ login.user.userSex === 1 ? '男' : '女' }}</span>
        </div>

        <div class="display">
          <span class="attribute">年龄</span>:
          <span class="value"> {{ login.user.userAge }}</span>
        </div>

        <div class="display">
          <span class="attribute">邮箱</span>:
          <span class="value"> {{ login.user.userEmail }}</span>
        </div>

        <div class="display">
          <span class="attribute">个性签名</span>:
          <span class="value"> {{ login.user.userSignature }}</span>
        </div>

        <div class="modify">
          <span class="attribute">uuid</span>:
          <span class="value"> {{ login.user.uuid }}</span>
        </div>

        <div class="modify">
          <span class="attribute">用户名</span>:
          <span class="value"> {{ login.user.userAccount }}</span>
        </div>

        <div class="modify">
          <span class="attribute">性别</span>:
          <el-radio-group v-model="modifyUser.userSex">å
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </div>

        <div class="modify">
          <span class="attribute">年龄</span>:
          <el-input-number v-model="modifyUser.userAge" class="value input" :step="1" />
<!--          <el-input v-model="modifyUser.userAge" class="value input"></el-input>-->
        </div>

        <div class="modify">
          <span class="attribute">邮箱</span>:
          <el-input v-model="modifyUser.userEmail" class="value input"></el-input>
        </div>

        <div class="modify">
          <span class="attribute">个性签名</span>:
          <el-input v-model="modifyUser.userSignature" class="value input"></el-input>
        </div>

      </div>

      <div style="margin-left: 100px; margin-top: 20px" class="display">
        <el-button type="primary" @click="preModifyInfo">修改信息</el-button>
      </div>
      <div style="margin-left: 100px; margin-top: 20px" class="modify">
        <el-button type="success" @click="affirmModifyInfo">确认修改</el-button>
        <el-button type="danger" @click="cancelModifyInfo">取消</el-button>
      </div>
      <br/>
    </div>
  </div>
</template>

<style scoped>
.display {
  line-height: 2.5;
}

.modify {
  line-height: 2.5;
  display: none;
}

.input {
  width: 20%;
}

.attribute {

}

.value {

}
</style>