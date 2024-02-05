<script>
import {ref} from "vue";

const inputBarrage = ref('')

const setLength = () => {
  inputNumber.value = inputBarrage.value === '' ? 0 : inputBarrage.value.length
}
</script>

<template>
  <div style="background-image: linear-gradient(to bottom, transparent 70%, #ffffff), url('https://img2.imgtp.com/2024/02/04/VTNr08Zz.jpg');
             height: calc(100vh - 120px); font-family: 'Microsoft YaHei'; padding-top: 120px;">
    <div style="height: 648px; width: 1536px; margin-left: 192px; display: inline-flex">
      <div style="width: 1152px">
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

      <div style="">
        <div style="margin-left: 10px">
          <el-card>
            <div style="height: 140px; padding-top: 10px">
              <div style="font-size: 16px; font-weight: 600">直播间地址</div>
              <div style="font-size: 14px">{{ 'http://8.140.143.119/live/' + '地址' }}</div>
              <div style="margin-top: 50px">
                <el-button color="#ff4b98" :dark='false' style="color: white" @click="">我要开播</el-button>
              </div>
            </div>
          </el-card>
        </div>

        <div style="margin-left: 10px; margin-top: 10px">

          <el-card class="box-card" style="width: 300px; height: 485px">

            <div style="height: 340px; width: 280px; padding: 5px 10px 5px 10px">

              <el-scrollbar max-height="560px" style="overflow: auto">
                <ul v-infinite-scroll="loadBarrage"
                    style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">
                  <li>
                    <div style="padding: 5px; font-size: 14px">
                      <span style="color: #9499A0">发送者：</span>
                      <span style="color: #61666D">消息内容</span>
                    </div>
                  </li>
                  <li v-for="i in 100" :key="i">
                    <div style="padding: 5px; font-size: 14px">
                      <span style="color: #9499A0">发送者：</span>
                      <span
                          style="color: #61666D">消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容消息内容</span>
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
      </div>
    </div>
  </div>
</template>

<style scoped>
:deep(.el-card .el-card__body) {
  padding: 0 !important;
}

</style>
