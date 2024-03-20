<script lang="ts" setup>
//------------import-----------------
// import global from "../global/global";
import {ref, reactive, toRefs, unref, onMounted, onUnmounted} from 'vue'
import {ClickOutside as vClickOutside, ElMessage} from 'element-plus'
import {presentTemplate, battery} from "../global/static/base64Template";
import {rank1, rank2, rank3} from "../global/static/base64Template";
import {login} from "../global/global";
import axios from "axios";
import {useRoute, useRouter} from "vue-router";

//------------variable-----------------
const inputBarrage = ref('')
const inputNumber = ref(0)
const barrageCount = ref(10)
const url = ref(presentTemplate)
const batteryUrl = ref(battery)
const route = useRoute();
const router = useRouter();

let liveInfo = ref({});
let commentList = ref([]);
let followVisable = ref(true);
let allPartition = ref([]);
let allGift = ref([]);
//------------function-----------------
// 滑到底请求一下
const loadBarrage = () => {
  barrageCount.value += 2
  console.log(barrageCount.value)
}

const setLength = () => {
  inputNumber.value = inputBarrage.value === '' ? 0 : inputBarrage.value.length
}

const sendPresent = (obj) => {
  // console.log(obj.value)
}

const recharge = () => {
  console.log('recharge')
}

const ws = ref();
const initWebSocket = () => {
  ws.value = new WebSocket('ws://8.140.143.119:8080/dev/live');
  ws.value.onopen = () => {
    console.log("连接成功");
    console.log({
      'type': 'JOIN',
      'chatRoom': route.params.liveId,
      'userName': login.user.userName,
      'userId': login.user.uuid,
    })
    ws.value.send(JSON.stringify({
      'type': 'JOIN',
      'chatRoom': route.params.liveId,
      'userName': login.user.userName,
      'userId': login.user.uuid,
    }))
  };

  //后端设置心跳，会每间隔一定时间，触发一次，根据内容变化处理逻辑
  ws.value.onmessage = (e: any) => {
    console.log(e.data);
    let res = JSON.parse(e.data);
    if (res.type === 'CHAT')
      commentList.value.push({userName: res.userName, content: res.content})
  };
  ws.value.onerror = () => {
    console.log("连接错误");
    //断连后每5秒重连一次
    setTimeout(() => {
      initWebSocket();
    }, 5000);
  };
};

//关闭链接（在页面销毁时销毁连接）
const closeWebSocket = () => {
  ws.value.close();
};

const sendMessage = () => {
  // console.log({
  //   type: 'CHAT',
  //   chatRoom: route.params.liveId,
  //   userName: login.user.userName,
  //   content: inputBarrage.value,
  //   userId: login.user.uuid,
  // });
  ws.value.send(JSON.stringify({
    type: 'CHAT',
    chatRoom: route.params.liveId,
    userName: login.user.userName,
    content: inputBarrage.value,
    userId: login.user.uuid,
  }))
  inputBarrage.value = '';
  inputNumber.value = 0;
}

const sendGift = () => {
  ws.value.send(JSON.stringify({
    type: 'GIFT',
    chatRoom: liveInfo.value.liveid,
    userName: login.user.userName,
    userId: login.user.uuid,
    giftId: ''
  }))
  inputBarrage.value = '';
  inputNumber.value = 0;
}

const getCurrentLive = () => {
  axios
      .get("http://localhost:5173/dev/influencer/live?liveID=" + route.params.liveId, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          liveInfo.value.userName = data.data.data.userName;
          liveInfo.value.userSignature = data.data.data.userSignature;
          liveInfo.value.age = data.data.data.age;
          liveInfo.value.sex = data.data.data.sex;
          liveInfo.value.liveid = data.data.data.roomforeignid;
          liveInfo.value.partitionid = data.data.data.partitionid;
          liveInfo.value.profile = data.data.data.profile;
          liveInfo.value.roomAvatar = data.data.data.roomAvatar;
          liveInfo.value.userAvatar = data.data.data.userAvatar;
          liveInfo.value.roomname = data.data.data.roomname;
          if (data.data.data.partitionid === 999)
            liveInfo.value.partition = '无分区'
          else
            liveInfo.value.partition = allPartition.value[data.data.data.partitionid];
          initWebSocket();
          console.log(liveInfo.value)
        }
      })
      .catch((err) => {
        console.log(err);
      });

  axios
      .get("http://localhost:5173/dev/influencer/findIslive?liveID=" + route.params.liveId, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error(data.data.description);
        } else if (data.data.code === 200) {
          initWebSocket();
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

// const buttonRef = ref()
// const popoverRef = ref()
// const onClickOutside = () => {
//   unref(popoverRef).popperRef?.delayHide?.()
// }

const getAllPartition = () => {
  axios
      .get("http://localhost:5173/dev/partition/info", {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          allPartition.value = data.data.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

const getAllGift = () => {
  axios
      .get("http://localhost:5173/dev/gift/info", {
        headers: {
          authorization: login.user.token,
          'Access-Control-Allow-Origin': '*'
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          allGift.value = data.data.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}


//------------setup-----------------
onMounted(() => {
  getCurrentLive();
  getAllPartition();
  getAllGift();
})

onUnmounted(() => {
  closeWebSocket();
});

</script>

<template>
  <div style="background-image: linear-gradient(to bottom, transparent 70%, white), url('https://img2.imgtp.com/2024/02/04/VTNr08Zz.jpg');
             height: calc(100vh - 60px); font-family: 'Microsoft YaHei'">
    <div style="text-align: left; display: inline-flex; margin-top: 15px">
      <el-card class="box-card"
               style="width: 1189px; height: 880px; min-height: 732px; min-width: 920px; margin-right: 10px; padding: 0">
        <div style="height: 100px; width: 100%; text-align: left; display: inline-flex">
          <div style="margin-top: 18px; margin-left: 18px">
            <el-avatar :size="64" :src="liveInfo.userAvatar"/>
          </div>
          <div style="margin-left: 18px">
            <div style="margin-top: 20px">
              <span style="font-size: 18px; color: black">{{ liveInfo.roomname }}</span>
            </div>
            <div style="display:inline-flex; margin-top: 10px">
              <div>
                <span style="font-size: 16px; color:#4f4f4f;">{{ liveInfo.userName }}</span>
              </div>

              <div style="margin-left: 20px">
                <span style="color:#7f7f7f;">{{ liveInfo.partition }}</span>
              </div>

              <div style="margin-left: 850px">
                <el-button type="primary" size="small" v-show="followVisable">关注</el-button>
                <el-button type="primary" size="small" v-show="!followVisable">取消关注</el-button>
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

          <div
              style="height: 104px; width: 80px; margin-top: 15px; text-align: center; cursor:pointer; margin-right: 20px"
              @click="recharge">
            <div style="height: 50px; width: 50px; margin-right: 15px; background-color: #fef7e4; border-radius: 50%;">
              <el-image style="width: 30px; height: 30px; margin-top: 10px" :src="batteryUrl" :fit="fit"/>
            </div>
            <div style="width: 80px; height: 17px">
              <div style="margin-top: 2px; font-size: 12px; color: #9499a0">{{ '余额：0' }}</div>
              <div style="font-size: 12px; color: #18191c"><&nbsp;立即充值</div>
            </div>
          </div>
          <el-divider direction="vertical"/>

          <div style="height: 104px; width: 80px; margin-top: 15px; text-align: center; cursor:pointer"
               @click="sendPresent(this)" v-for="(item, index) in allGift">
            <div style="height: 50px; width: 50px; margin-right: 15px">
              <el-image style="width: 50px; height: 50px" :src="item.presentavatar" :fit="fit"/>
            </div>
            <div style="width: 80px; height: 17px">
              <div style="margin-top: 2px; font-size: 12px; color: #18191c">{{ item.name }}</div>
              <div style="font-size: 12px; color: #9499a0">{{  item.value + '电池' }}</div>
            </div>
          </div>

        </div>
      </el-card>

      <el-card class="box-card" style="width: 300px; height: 880px">
        <div style="height: 178px; width: 100%; text-align: center" ref="buttonRef">
          <div style="font-size: 14px; font-weight: 560; padding-top: 10px">高能用户</div>

          <div style="margin-top: 12px">

            <div style="width: 100%; height: 42px; display: inline-flex">
              <div style="width: 24px; height: 16px; margin-left: 9px">
                <el-image style="width: 24px; height: 16px; margin-top: 13px" :src="rank1" :fit="fit"/>
              </div>

              <div style="width: 36px; height: 36px; margin-top: 3px; margin-left: 10px">
                <el-avatar :size="36" :src="circleUrl"/>
              </div>

              <div style="color:#2F3238; font-size: 12px; margin-left: 10px; line-height: 42px">
                {{ '用户名' }}
              </div>

              <div style="color:#000000; font-size: 12px; margin-left: auto; margin-right: 15px; line-height: 42px">
                {{ '120' }}
              </div>
            </div>

            <div style="width: 100%; height: 42px; display: inline-flex">
              <div style="width: 24px; height: 16px; margin-left: 9px">
                <el-image style="width: 24px; height: 16px; margin-top: 13px" :src="rank2" :fit="fit"/>
              </div>

              <div style="width: 36px; height: 36px; margin-top: 3px; margin-left: 10px">
                <el-avatar :size="36" :src="circleUrl"/>
              </div>

              <div style="color:#2F3238; font-size: 12px; margin-left: 10px; line-height: 42px">
                {{ '用户名' }}
              </div>

              <div style="color:#000000; font-size: 12px; margin-left: auto; margin-right: 15px; line-height: 42px">
                {{ '12' }}
              </div>
            </div>

            <div style="width: 100%; height: 42px; display: inline-flex">
              <div style="width: 24px; height: 16px; margin-left: 9px">
                <el-image style="width: 24px; height: 16px; margin-top: 13px" :src="rank3" :fit="fit"/>
              </div>

              <div style="width: 36px; height: 36px; margin-top: 3px; margin-left: 10px">
                <el-avatar :size="36" :src="circleUrl"/>
              </div>

              <div style="color:#2F3238; font-size: 12px; margin-left: 10px; line-height: 42px">
                {{ '用户名' }}
              </div>

              <div style="color:#000000; font-size: 12px; margin-left: auto; margin-right: 15px; line-height: 42px">
                {{ '1' }}
              </div>
            </div>
          </div>
        </div>

        <el-divider style="margin: 0"/>

        <div style="height: 560px; width: 280px; padding: 5px 10px 5px 10px">

          <el-scrollbar max-height="560px" style="overflow: auto">
            <ul v-infinite-scroll="loadBarrage"
                style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">

              <li v-for="(item, index) in commentList" :key="index">
                <div style="padding: 5px; font-size: 14px">
                  <span style="color: #9499A0">{{ item.userName }}：</span>
                  <span style="color: #61666D">{{ item.content }}</span>
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
          <el-button type="primary" size="small" style="margin-left: 210px; margin-top: 8px" @click="sendMessage">&nbsp;&nbsp;&nbsp;发送&nbsp;&nbsp;&nbsp;</el-button>
        </div>
      </el-card>
    </div>
    <div style="width: 1189px; height: 200px; margin-left: 200px; margin-top:20px; padding: 0; text-align: left">
      <span style="font-size: 20px; margin-left: 20px">简介</span>
      <el-card class="box-card" style="width: 100%; height: 75%; margin-top: 5px; text-align: center">

        <span class="box">{{ liveInfo.profile }}</span>
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

.box {
  line-height: 150px;
}

.el-divider--vertical {
  display: inline-block;
  width: 1px;
  height: 80%; //更改竖向分割线长度
  margin: 8px;
  vertical-align: middle;
  position: relative;
}

::deep(.el-popper__arrow) {
  visibility: hidden;
  background-color: black;
}

//.el-tooltip__popper[x-placement^=top] .el-popper__arrow:after {
//  border-top-color: blue;
//}
//.el-tooltip__popper[x-placement^=top] .el-popper__arrow {
//  border-top-color: blue;
//}

/*//进度条*/
video::-webkit-media-controls-timeline {
  display: none;
}

/*//观看的当前时间*/
video::-webkit-media-controls-current-time-display {
  display: none;
}

/*//剩余时间*/
video::-webkit-media-controls-time-remaining-display {
  display: none;
}
</style>