<script lang="ts" setup>
import global from "../global/global";
import {ref, reactive, toRefs,} from 'vue'
import number = CSS.number;
import {presentTemplate, battery} from "../global/base64Template";

const inputBarrage = ref('')
const inputNumber = ref(0)
const barrageCount = ref(10)
const url = ref(presentTemplate)
const battery = ref(battery)

// 滑到底请求一下
const loadBarrage = () => {
  barrageCount.value += 2
  console.log(barrageCount.value)
}

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
        <div style="height: 115px; width: 100%; display: inline-flex;" dir='rtl'>

          <div style="height: 104px; width: 80px; margin-top: 15px; text-align: center">
            <div style="height: 50px; width: 50px; background-color: pink; margin-right: 15px">
              <el-image style="width: 50px; height: 50px" :src="battery" :fit="fit" />
            </div>
            <div style="width: 80px; height: 17px">
              <div style="margin-top: 2px; font-size: 12px; color: #9499a0">{{ '余额：0' }}</div>
              <div style="font-size: 12px; color: #18191c">立即充值></div>
            </div>
          </div>

          <div style="height: 104px; width: 80px; margin-top: 15px; text-align: center">
            <div style="height: 50px; width: 50px; background-color: pink; margin-right: 15px">
              <el-image style="width: 50px; height: 50px" :src="url" :fit="fit" />
            </div>
            <div style="width: 80px; height: 17px">
              <div style="margin-top: 2px; font-size: 12px; color: #18191c">{{ '小花花' }}</div>
              <div style="font-size: 12px; color: #9499a0">{{ '1电池' }}</div>
            </div>
          </div>

          <div style="height: 104px; width: 80px; margin-top: 15px; text-align: center">
            <div style="height: 50px; width: 50px; background-color: pink; margin-right: 15px">
              <el-image style="width: 50px; height: 50px" :src="url" :fit="fit" />
            </div>
            <div style="width: 80px; height: 17px">
              <div style="margin-top: 2px; font-size: 12px; color: #18191c">{{ '小花花' }}</div>
              <div style="font-size: 12px; color: #9499a0">{{ '1电池' }}</div>
            </div>
          </div>

        </div>
      </el-card>

      <el-card class="box-card" style="width: 300px; height: 880px">
        <div style="height: 178px; width: 100%; text-align: center">
          <div style="font-size: 14px; font-weight: 560; padding-top: 10px">高能用户</div>
        </div>

        <el-divider style="margin: 0"/>

        <div style="height: 560px; width: 280px; padding: 5px 10px 5px 10px">

          <el-scrollbar max-height="560px" style="overflow: auto">
            <ul v-infinite-scroll="loadBarrage"
                style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">
              <li>
                <div style="padding: 5px; font-size: 14px">
                  <span style="color: #9499A0">发送者：</span>
                  <span style="color: #61666D">消息内容</span>
                </div>
              </li>
              <li  v-for="i in barrageCount" :key="i">
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
    <div style="width: 1189px; height: 200px; margin-left: 200px; margin-top:20px; padding: 0; text-align: left">
      <span style="font-size: 20px; margin-left: 20px">简介</span>
      <el-card class="box-card" style="width: 100%; height: 75%; margin-top: 5px; text-align: center">

        <span class="box">{{ '这里是直播间简介' }}</span>
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

.box{
  line-height: 150px;

}
</style>