<script setup lang="ts">
//------------import-----------------
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import {onMounted, onUnmounted} from 'vue'
import flvjs from "flv.js";
import axios from "_axios@1.6.7@axios";
import {login} from "../global/global";

//------------variable-----------------
const router = useRouter();
const recommendCarouselLiveId = ref(0)
const urlTemplate = ref('https://i0.hdslb.com/bfs/archive/5980c275d22dc388c21affad6de81e568b3614a9.jpg')

let lastSelectRecommendCarousel = null;
let flvPlayer = ref();
let recommendCarouselList = ref([]);
let recommendLiveList = ref([]);

//------------function-----------------
const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

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
    console.log(flvPlayer)
    flvPlayer.value.attachMediaElement(videoElement);
    flvPlayer.value.load();
    flvPlayer.value.play();
  }
}

const toRecommendCarousel = (context, index, item) => {
  const docu = document.getElementById('recommend_carousel' + index);
  // console.log(docu); // 输出组件实例
  // console.log(context); // 输出组件实例
  // console.log(context.target.value); // 输出组件实例
  /* width | style | color */
  // context.target.style.border = '3px solid #61ace9';
  // context.target.style.backgroundColor = '#61ace9';
  // context.target.style.padding = '30px';
  // context.target.style.visibility = "hidden";
  docu.style.backgroundColor = '#61ace9'
  if (lastSelectRecommendCarousel != null && lastSelectRecommendCarousel != docu)
    lastSelectRecommendCarousel.style.backgroundColor = 'transparent'
  lastSelectRecommendCarousel = docu
  // console.log(context.target.style.border);
}

const getPartition = () => {

}

const getRecommendCarousel = () => {
  axios
      .get("http://localhost:5173/dev/liveOnline/random?num=" + 5, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          recommendCarouselList.value = data.data.data;
          lastSelectRecommendCarousel = document.getElementById('recommend_carousel0');
          lastSelectRecommendCarousel.style.backgroundColor = '#61ace9'
          console.log(recommendCarouselList.value.length)
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

const getRecommendLive = () => {
  axios
      .get("http://localhost:5173/dev/liveOnline/random?num=" + 10, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          recommendLiveList.value = data.data.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}


//------------setup-----------------
onMounted(() => {
  // createVideo('http://8.140.143.119:8002/live?port=8001&app=live&stream=17', "videoLive")
  getRecommendCarousel();
  getRecommendLive();
})


</script>

<template>
  <div style="width: 100%; height: 738px;
  background-position: center; text-align: center;
  background-image: url('https://img2.imgtp.com/2024/02/04/4f6cOhwz.jpg')">
    <!--  background-image: url('http://8.140.143.119:8000/images/2024/01/27/pexels-photo-1054218bc2f53a94c640c5d.jpg')">-->
    <div style="height: 630px; width: 1342px; display: inline-flex; margin-top: 54px">
      <div style="width: auto; height: 630px">
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
      <div
          style="width: 220px; height: 630px; margin-left: 12px; border-radius: 5px; background-color: rgba(0, 0, 0, 0.5)">

        <div v-for="(item, index) in recommendCarouselList" :id="'recommend_carousel' + index" :key="index"
             class="recommend_carousel" @click="toRecommendCarousel($event, index, item)"
             style="width: 200px; height: 110px; margin-top: 13px; margin-left: 10px; margin-right: 10px; border-radius: 5px">
          <div>
            <el-image style="width: 196px; height: 106px; margin-top: 2px; border-radius: 5px" :src="item.roomAvatar"
                      :fit="fit"/>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div style="background-color: #f1f2f3; text-align: center;; margin-top: 30px">
    <div style="width: 1380px; margin-left: calc((100% - 1380px)/2);">
      <div style="width: 100%; text-align: left">
        <span style="padding-left: 10px; font-size: 25px; font-weight: 400">推荐直播</span>
      </div>
      <el-card class="box-card" @click="toPagePath('/live/' + item.liveid)" style="cursor:pointer" v-for="(item, index) in recommendLiveList" :key="index">
        <div style="width: 92%; height: 60%; padding-left: 4%; padding-top: 4%">
          <el-image style="width: 253.914px; height: 142.820px; border-radius: 5px"
                    :src="item.roomAvatar"
                    fit="scale-down"/>
        </div>
        <div style="text-align: left; width: 92%; margin-left: 4%;  display: flex">
          <el-avatar :size="50" :src="item.avatar" style="margin-top: 2%; margin-left: 1%"/>
          <div style="display: inline-block; margin-left: 15px; margin-top: 2.5%;">
            <div>
              <span style="font-size: 18px">{{ item.roomname }}</span>
            </div>
            <div>
              <span style="font-size: 14px; color: #7f7f7f">这里是用户名</span>
            </div>
          </div>
          <div style="margin-top: 12.5%; margin-left: 17%">
<!--            <el-button type="primary" size="small">关注</el-button>-->
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style lang="less" scoped>

.box-card {
  box-shadow: var(--el-box-shadow);
  box-shadow: none;
  border: none;
  border-radius: 0;
  background-color: #f1f2f3;
  height: 224px;
  width: 276px;
  //margin-left: 10px;
  //margin-right: 10px;
  //margin-bottom: 27px;
  //padding-top: 10px;
  margin: 0;
  --el-card-padding: 0 !important;
  display: inline-block;
}

.box-card:hover {
  animation: fly 0.1s linear;
  animation-fill-mode: both;
}

.recommend_carousel:focus {
  background-color: #33A2EF;
}

@keyframes fly {
  0% {
  }
  100% {
    //box-shadow: 6px 8px 12px #7f7f7f, -3px 4px 6px #7f7f7f;
    //width: 420px;
    //height: 250px;
    border-radius: 10px;
    background-color: #ffffff;
    box-shadow: var(--el-box-shadow);
    transform: scale(1.1);
  }
}

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