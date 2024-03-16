<script setup lang="ts">
//------------import-----------------
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'
import {onMounted,onUnmounted} from 'vue'

//------------variable-----------------
const router = useRouter();
const recommendCarouselLiveId = ref(0)
const urlTemplate = ref('https://i0.hdslb.com/bfs/archive/5980c275d22dc388c21affad6de81e568b3614a9.jpg')

let lastSelectRecommendCarousel = null;


//------------function-----------------
const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

const toRecommendCarousel = (context, index) => {
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


//------------setup-----------------
onMounted(()=>{
  lastSelectRecommendCarousel = document.getElementById('recommend_carousel0');
  lastSelectRecommendCarousel.style.backgroundColor = '#61ace9'
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

        <div v-for="(i, index) in 5" :id="'recommend_carousel' + index" :key="i" class="recommend_carousel" @click="toRecommendCarousel($event, index)"
             style="width: 200px; height: 110px; margin-top: 13px; margin-left: 10px; margin-right: 10px; border-radius: 5px">
          <div>
            <el-image style="width: 196px; height: 106px; margin-top: 2px; border-radius: 5px" :src="urlTemplate" :fit="fit"/>
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
      <el-card class="box-card" @click="toPagePath('/live/' + '1')" style="cursor:pointer" v-for="i in 50" :key="i">
        <div style="width: 92%; height: 60%; padding-left: 4%; padding-top: 4%">
          <el-image style="width: 100%; height: 100%; border-radius: 5px"
                    :src="urlTemplate"
                    fit="scale-down"/>
        </div>
        <div style="text-align: left; width: 92%; margin-left: 4%;  display: flex">
          <el-avatar :size="50" :src="circleUrl" style="margin-top: 2%; margin-left: 1%"/>
          <div style="display: inline-block; margin-left: 15px; margin-top: 2.5%;">
            <div>
              <span style="font-size: 18px">这里是标题</span>
            </div>
            <div>
              <span style="font-size: 14px; color: #7f7f7f">这里是用户名</span>
            </div>
          </div>
          <div style="margin-top: 12.5%; margin-left: 17%">
            <el-button type="primary" size="small">关注</el-button>
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

</style>