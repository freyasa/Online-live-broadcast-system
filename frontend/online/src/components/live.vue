<script lang="ts" setup>
import global from "../global/global";
import {ref, reactive, toRefs,} from 'vue'
import number = CSS.number;

const inputBarrage = ref('')
const inputNumber = ref(0)

// function setLength(obj, maxlength, id) {
//   let num = maxlength - obj.value.length;
//   let leng = id;
//   if (num < 0) {
//     num = 0;
//   }
//   document.getElementById(leng).innerHTML = num + "/20";
//   console.log(num)
// }

const setLength = () => {
  inputNumber.value = inputBarrage.value === '' ? 0 : inputBarrage.value.length
}


</script>

<template>
  <div style="background-image: linear-gradient(to bottom, transparent 70%, white), url('http://8.140.143.119:8000/images/2024/01/25/live_background.jpg');
             height: calc(100vh - 60px); font-family: 'Microsoft YaHei'">
    <div style="text-align: left; display: inline-flex; margin-top: 15px">
      <el-card class="box-card"
               style="width: 1189px; height: 880px; min-height: 732px; min-width: 920px; margin-right: 10px; padding: 0">
        <div style="height: 100px; width: 100%; text-align: left; display: inline-flex">
          <div style="margin-top: 18px; margin-left: 18px">
            <el-avatar :size="64" :src="circleUrl"/>
          </div>
          <div style="margin-left: 18px">
            <div style="margin-top: 20px">
              <span style="font-size: 18px; color: black">{{ '直播间名称' + $route.params.liveId }}</span>
            </div>
            <div style="display:inline-flex; margin-top: 10px">
              <div>
                <span style="font-size: 16px; color:#4f4f4f;">{{ '用户名' }}</span>
              </div>

              <div style="margin-left: 20px">
                <span style="color:#7f7f7f;">{{ '分区' }}</span>
              </div>
            </div>
          </div>
        </div>
        <div style="height: 670px; width: 100%">
          <video
              id="videoLive"
              crossorigin="anonymous"
              controls
              autoplay
              width="100%"
              height="100%"
              style="object-fit: fill"
          >

          </video>
        </div>
        <div style="height: 115px; width: 100%">
          礼物部分
        </div>
      </el-card>

      <el-card class="box-card" style="width: 300px; height: 880px">
        <div style="height: 178px; width: 100%; text-align: center">
          <div style="font-size: 14px; font-weight: 560; padding-top: 10px">高能用户</div>
        </div>

        <el-divider style="margin: 0"/>

        <div style="height: 560px; width: 280px; padding: 5px 10px 5px 10px">

          <el-scrollbar max-height="560px" style="overflow: auto">
            <ul v-infinite-scroll="load"
                style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">
              <li v-for="i in 20" :key="i">
                <div style="padding: 5px; font-size: 14px">
                  <span style="color: #9499A0">发送者：</span>
                  <span style="color: #61666D">消息内容</span>
                </div>
              </li>
              <li>
                <div style="padding: 5px; font-size: 14px">
                  <span style="color: #9499A0">发送者：</span>
                  <span style="color: #61666D">消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容</span>
                </div>
              </li>
            </ul>
          </el-scrollbar>
        </div>
        <div style="height: 145px; width: 100%; margin-top: 5px;">
          <div
              style="height:78px; width: 272px; margin-left: 10px; border: 1px solid #bfbfbf; padding: 2px; border-radius: 6px">


          <textarea
              v-model="inputBarrage"
              style="resize:none; width: 258px; height: 64px; margin-top: 4px; margin-left: 4px; border: transparent;
                      outline:none; wrap:hard; box-shadow: inset 0 0 0 rgba(0,0,0,0.5);"
              maxlength="20"
              placeholder="发个弹幕呗～"
              @keyup="setLength()"
          />
            <div style="position:relative; left:220px; bottom:23px; font-size:12px; color:#999999">
              {{ inputNumber + " / 20" }}
            </div>
          </div>
          <el-button type="primary" size="small" style="margin-left: 210px; margin-top: 8px">&nbsp;&nbsp;&nbsp;发送&nbsp;&nbsp;&nbsp;</el-button>
        </div>
      </el-card>
    </div>
    <div style="width: 1189px; height: 200px; margin-left: 200px; margin-top:12px; padding: 0; background:#000;">
      <el-card class="box-card">
      </el-card>
    </div>
  </div>


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
</template>

<style lang="less" scoped>
.box-card {
  border-radius: 10px;
}

:deep(.el-card .el-card__body) {
  padding: 0 !important;
}

//::deep(.resizeNone){
//  .el-textarea__inner{ //el_input中的隐藏属性
//    resize: none;//主要是这个样式
//  }
//}
</style>