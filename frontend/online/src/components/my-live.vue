<script lang="ts" setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {login} from "../global/global";
import {ElMessage, genFileId, UploadProps, UploadRawFile} from 'element-plus'
import LiveInfo from "../global/vo/LiveInfo";

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


const setLength = () => {
  inputNumber.value = inputBarrage.value === '' ? 0 : inputBarrage.value.length
}

const getLiveRoomInfo = () => {
  axios
      .get("http://localhost:5173/dev/influencer/getRoomInfo?uuid=" + login.user.uuid, {
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

const affirmModifyInfo = () => {
  axios
      .post("http://localhost:5173/dev/influencer/updateLive", {
        liveid: modifyLiveInfo.value.liveid,
        uuid: modifyLiveInfo.value.uuid,
        roomName: modifyLiveInfo.value.roomname,
        profile: modifyLiveInfo.value.profile,
        partitionId: modifyLiveInfo.value.partitionid,
        roomAvatar: modifyLiveInfo.value.roomAvatar
      },{
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 201) {
          ElMessage.error('请重新登录');
        } else if (data.data.code === 200) {

        }
      })
      .catch((err) => {
        console.log(err);
      });
}

//获取所有分区信息
const getAllPartition = () => {

}


getLiveRoomInfo();
getAllPartition();

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
                <el-button color="#ff4b98" :dark='false' style="color: #ffffff" @click="console.log('开播')">我要开播
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

              <el-scrollbar max-height="560px" style="overflow: auto">
                <ul
                    style="background-color: white; height: 100%; width: 280px; margin: 0; padding: 0;">
                  <!--                    v-infinite-scroll="loadBarrage"-->
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
        <span class="attribute">直播间封面</span>:
        <span class="value">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                    :limit="1"
                    :on-change="getFile"
                    :on-exceed="handleExceed"
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
</style>
