<script lang="ts" setup>
import {onMounted, onUnmounted, ref} from "vue";
import axios from "axios";
import {login} from "../global/global";
import {ElMessage, genFileId, UploadProps, UploadRawFile} from 'element-plus'
import LiveInfo from "../global/vo/LiveInfo";
import flvjs from "flv.js";
import {flatMap} from "_@volar_typescript@1.11.1@@volar/typescript/lib/typescript/core";
import {useRoute, useRouter} from "vue-router";

const route = useRoute();
const router = useRouter();
const inputBarrage = ref('');
const modifyRoomInfoVisible = ref(false);
const modifyLiveInfo = ref({
  liveid: 0,
  partitionid: 0,
  profile: '',
  roomAvatar: '',
  roomname: '',
  uuid: '',
});
const liveInfo = ref(new LiveInfo());

let allPartition = ref([]);
let flvPlayer = ref();
let upFile = ref();
let inputNumber = ref(0);
let commentList = ref([]);
let isLive = ref(false);
const setLength = () => {
  inputNumber.value = inputBarrage.value === '' ? 0 : inputBarrage.value.length
}

const createVideo = (url, elementId) => {
  if (flvjs.isSupported()) {
    let videoElement = document.getElementById(elementId);
    flvPlayer.value = flvjs.createPlayer({
      type: "flv",
      enableWorker: true, //浏览器端开启flv.js的worker,多进程运行flv.js
      isLive: true, //直播模式
      hasAudio: false, //关闭音频
      hasVideo: true,
      stashInitialSize: 128,
      enableStashBuffer: true, //播放flv时，设置是否启用播放缓存，只在直播起作用。
      url: url,
    });
    flvPlayer.value.attachMediaElement(videoElement);
    flvPlayer.value.load();
    flvPlayer.value.play();
  }
}

const getLiveRoomInfo = () => {
  axios
      .get("http://8.140.143.119:8080/dev/influencer/getRoomInfo?uuid=" + login.user.uuid, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error('请重新登录');
        } else if (data.data.code === 200) {
          modifyLiveInfo.value.liveid = data.data.data.liveid;
          modifyLiveInfo.value.partitionid = data.data.data.partitionid;
          modifyLiveInfo.value.profile = data.data.data.profile;
          modifyLiveInfo.value.roomAvatar = data.data.data.roomAvatar;
          modifyLiveInfo.value.roomname = data.data.data.roomname;
          modifyLiveInfo.value.uuid = data.data.data.uuid;

          liveInfo.value.liveid = data.data.data.liveid;
          liveInfo.value.partitionid = data.data.data.partitionid;
          liveInfo.value.profile = data.data.data.profile;
          liveInfo.value.roomAvatar = data.data.data.roomAvatar;
          liveInfo.value.roomname = data.data.data.roomname;
          liveInfo.value.uuid = data.data.data.uuid;
          initWebSocket();
          getCurrentLiveByLiveId();
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

const getBase64 = (file) => {
  return new Promise(function (resolve, reject) {
    let reader = new FileReader();
    let imgResult = "";
    reader.readAsDataURL(file);
    reader.onload = function () {
      imgResult = reader.result;
    };
    reader.onerror = function (error) {
      reject(error);
    };
    reader.onloadend = function () {
      resolve(imgResult);
    };
  });
}

const getFile = (file) => {
  // if(file.success){
  console.log(file)
  const isJPG = file.raw.type === 'image/jpeg';
  const isPNG = file.raw.type === 'image/png';
  const isLt5M = file.raw.size / 1024 / 1024 < 5;
  if (!isJPG && !isPNG) {
    console.log('上传图片只能是JPG或者PNG格式!');
  }
  if (!isLt5M) {
    console.log('上传图片大小不能超过 5MB!');
  }
  if ((isJPG || isPNG) && isLt5M) {
    getBase64(file.raw).then(res => {
      modifyLiveInfo.value.roomAvatar = res;
    });
  }
}

const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}

const handleChange = (file, fileList) => {
  console.log(file, fileList)
  upFile.value = file.raw
}

const getAllPartitions = () => {
  axios
      .get("http://8.140.143.119:8080/dev/partition/info", {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          allPartition.value = data.data.data;
          console.log(allPartition.value)
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

const affirmModifyInfo = () => {
  // console.log({
  //   liveid: modifyLiveInfo.value.liveid,
  //   uuid: login.user.uuid,
  //   roomName: modifyLiveInfo.value.roomname,
  //   profile: modifyLiveInfo.value.profile,
  //   partitionId: modifyLiveInfo.value.partitionid,
  //   roomAvatar: modifyLiveInfo.value.roomAvatar
  // })

  let fd = new FormData();
  fd.append("liveId", modifyLiveInfo.value.liveid);       //附件类型
  fd.append("uuid", login.user.uuid);       //附件类型
  fd.append("roomName", modifyLiveInfo.value.roomname);       //附件类型
  fd.append("profile", modifyLiveInfo.value.profile);       //附件类型
  fd.append("partitionId", modifyLiveInfo.value.partitionid);       //附件类型
  fd.append("roomAvatar", upFile.value);       //附件类型

  let config = {
    headers: {
      'Content-Type': 'multipart/form-data',
      'authorization': login.user.token,
    }
  }

  axios.post('http://8.140.143.119:8080/dev/influencer/updateLive', fd, config).then(data => {
    console.log(data.data)
    if (data.data.code === 200) {
      ElMessage({
        message: '修改成功',
        type: 'success',
      })
      modifyRoomInfoVisible.value = false;
    }
  }).catch(data => {
    console.log(data)
  })


  // axios
  //     .post("http://8.140.143.119:8080/dev/influencer/updateLive", {
  //       liveid: modifyLiveInfo.value.liveid,
  //       uuid: login.user.uuid,
  //       roomName: modifyLiveInfo.value.roomname,
  //       profile: modifyLiveInfo.value.profile,
  //       partitionId: modifyLiveInfo.value.partitionid,
  //       roomAvatar: modifyLiveInfo.value.roomAvatar
  //     }, {
  //       headers: {
  //         authorization: login.user.token,
  //       }
  //     })
  //     .then((data) => {
  //       console.log(data.data);
  //       if (data.data.code === 201) {
  //         ElMessage.error('请重新登录');
  //       } else if (data.data.code === 200) {
  //
  //       }
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
}

const handleOnLive = () => {
  axios
      .post("http://8.140.143.119:8080/dev/influencer/startlive", {
        liveid: liveInfo.value.liveid,
        uuid: login.user.uuid
      }, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error('请重新登录');
        } else if (data.data.code === 200) {
          ElMessage({
            message: '开播成功',
            type: 'success',
          })
          isLive.value = true;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  createVideo('http://8.140.143.119:8002/live?port=8001&app=live&stream=' + liveInfo.value.liveid, "videoLive");
}

const handleOffLive = () => {
  axios
      .post("http://8.140.143.119:8080/dev/influencer/endlive", {
        liveid: liveInfo.value.liveid,
        uuid: login.user.uuid
      }, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error('请重新登录');
        } else if (data.data.code === 200) {
          ElMessage({
            message: '下播成功',
            type: 'success',
          })
          isLive.value = false;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

//获取所有分区信息
const getAllPartition = () => {
  getAllPartitions();
}

const getCurrentLive = () => {
  axios
      .get("http://8.140.143.119:8080/dev/influencer/getRoomInfo?uuid=" + login.user.uuid)
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error('请重新登录');
        } else if (data.data.code === 200) {
          liveInfo.value.liveid = data.data.data.liveid;
          liveInfo.value.partitionid = data.data.data.partitionid;
          liveInfo.value.profile = data.data.data.profile;
          liveInfo.value.roomAvatar = data.data.data.roomAvatar;
          liveInfo.value.roomname = data.data.data.roomname;
          liveInfo.value.uuid = data.data.data.uuid;
          getCurrentLiveByLiveId();
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

const getCurrentLiveByLiveId = () => {
  axios
      .get("http://8.140.143.119:8080/dev/influencer/findIslive?liveID=" + liveInfo.value.liveid, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          isLive.value = false;
        } else if (data.data.code === 200) {
          isLive.value = true;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

//这里后端接口需要用登录的用户id，通过session获取
// const userInfo = getSessStorage("userInfo") as { userId: string };
const userInfo = null;


const ws = ref();
const initWebSocket = () => {
  ws.value = new WebSocket('ws://8.140.143.119:8080/dev/live');
  ws.value.onopen = () => {
    console.log("连接成功");
    // console.log({
    //   type: 'JOIN',
    //   chatRoom: modifyLiveInfo.value.liveid,
    //   userName: login.user.userName,
    //   userId: login.user.uuid,
    // })
    ws.value.send(JSON.stringify({
      'type': 'JOIN',
      'chatRoom': modifyLiveInfo.value.liveid,
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
  console.log({
    'type': 'CHAT',
    // chatRoom: modifyLiveInfo.value.liveid,
    'userName': login.user.userName,
    'content': inputBarrage.value,
    // userId: login.user.uuid,
  });
  ws.value.send(JSON.stringify({
    type: 'CHAT',
    chatRoom: modifyLiveInfo.value.liveid,
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
    chatRoom: modifyLiveInfo.value.liveid,
    userName: login.user.userName,
    userId: login.user.uuid,
    giftId: ''
  }))
  inputBarrage.value = '';
  inputNumber.value = 0;
}

onMounted(() => {
  getLiveRoomInfo();
  getAllPartition();
  // initWebSocket();
  // createVideo('http://8.140.143.119:8002/live?port=8001&app=live&stream=' + liveInfo.value.liveid, "videoLive");
})

onUnmounted(() => {
  closeWebSocket();
});

</script>

<template>
  <div style="background-image: linear-gradient(to bottom, transparent 70%, #ffffff), url('https://img2.imgtp.com/2024/02/04/VTNr08Zz.jpg');
             height: calc(100vh - 120px); font-family: 'Microsoft YaHei'; padding-top: 120px;">
    <div style="height: 648px; width: 1536px; margin-left: 40px; display: inline-flex">
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
              <div style="font-size: 14px">{{ 'http://8.140.143.119/live/' + liveInfo.liveid }}</div>
              <div style="margin-top: 50px">
                <el-button v-show="!isLive" color="#ff4b98" :dark='false' style="color: #ffffff" @click="handleOnLive">
                  我要开播
                </el-button>
                <el-button v-show="isLive" color="#ff4b98" :dark='false' style="color: #ffffff" @click="handleOffLive">
                  我要下播
                </el-button>
                <!--/*                <button style="background-color: #ff4b98; color: white; width: 90px; height: 35px; font-size: 13px" @click="console.log('开播')">我要开播</button>*/-->
                <el-button @click="modifyRoomInfoVisible = true">更改信息</el-button>
              </div>
            </div>
          </el-card>
        </div>

        <div style="margin-left: 10px; margin-top: 10px">

          <el-card class="box-card" style="width: 300px; height: 485px">

            <div style="height: 340px; width: 280px; padding: 5px 10px 5px 10px">

              <el-scrollbar max-height="560px" style="overflow: auto; text-align: left">
                <ul
                    style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">
                  <!--                    v-infinite-scroll="loadBarrage"-->
                  <li v-for="(item, index) in commentList" :key="index">
                    <div style="padding: 5px; font-size: 14px">
                      <span style="color: #9499A0">{{ item.userName }}</span>：
                      <span
                          style="color: #61666D">{{ item.content }}</span>
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
              @keyup="setLength"
          />
                <div style="position:relative; left:100px; bottom:23px; font-size:12px; color:#999999">
                  {{ inputNumber + " / 20" }}
                </div>
              </div>
              <el-button type="primary" size="small" style="margin-left: 210px; margin-top: 8px" @click="sendMessage">
                &nbsp;&nbsp;&nbsp;发送&nbsp;&nbsp;&nbsp;
              </el-button>
            </div>
          </el-card>

        </div>
      </div>
    </div>
  </div>


  <el-dialog v-model="modifyRoomInfoVisible" title="修改信息" width="500">

    <div style="width: 80%; margin-left: 10%">

      <div class="modify">
        <span class="attribute">直播间名称</span>:
        <el-input v-model="modifyLiveInfo.roomname" class="value input"></el-input>
      </div>

      <div class="modify">
        <span class="attribute">直播间简介</span>:
        <el-input v-model="modifyLiveInfo.profile" class="value input"></el-input>
      </div>

      <div class="modify">
        <span class="attribute">直播间分区</span>:
        <br/>
        <el-select v-model="modifyLiveInfo.partitionid" placeholder="Select" style="width: 240px">
          <el-option
              v-for="item in allPartition"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>      </div>

      <div class="modify">
        <span class="attribute">直播间封面</span>:
        <span class="value">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                    :limit="1"
                    :data="uploadData"
                    :on-change="handleChange"
                    :on-exceed="handleChange"
                    :http-request="affirmModifyAvatar"
                    :auto-upload="false"
                >
                  <template #trigger>
                    <el-button type="primary">select file</el-button>
                  </template>
                  <!--                  <el-button class="ml-3" type="success" @click="affirmModifyAvatar">-->
                  <!--                    upload to server-->
                  <!--                  </el-button>-->
                  <template #tip>
                    <div class="el-upload__tip text-red">
                      limit 1 file, new file will cover the old file
                    </div>
                  </template>
                </el-upload>
              </span>
      </div>
      <el-button type="success" @click="affirmModifyInfo">确认修改</el-button>
    </div>

  </el-dialog>
</template>

<style scoped>
:deep(.el-card .el-card__body) {
  padding: 0 !important;
}

.modify {
  line-height: 2.5;
}


/*//全屏按钮*/
/*video::-webkit-media-controls-fullscreen-button {*/
/*  display: none;*/
/*}*/

/*//播放按钮*/
/*video::-webkit-media-controls-play-button {*/
/*  display: none;*/
/*}*/

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

/*//音量按钮*/
/*video::-webkit-media-controls-mute-button {*/
/*  display: none;*/
/*}*/

/*video::-webkit-media-controls-toggle-closed-captions-button {*/
/*  display: none;*/
/*}*/

/*//音量的控制条*/
/*video::-webkit-media-controls-volume-slider {*/
/*  display: none;*/
/*}*/

/*//所有控件*/
/*video::-webkit-media-controls-enclosure {*/
/*  display: none;*/
/*}*/
</style>
