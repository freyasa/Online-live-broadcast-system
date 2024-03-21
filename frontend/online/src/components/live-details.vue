<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import axios from "_axios@1.6.7@axios";
import {login} from "../global/global";


const router = useRouter();
const route = useRoute();
const urlTemplate = ref('https://i0.hdslb.com/bfs/archive/5980c275d22dc388c21affad6de81e568b3614a9.jpg')

let liveList = ref([]);

const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

const getLiveList = () => {
  axios
      .get("http://8.140.143.119:8080/dev/partition/liveInfo?id=" + route.params.partitionId, {
        headers: {
          authorization: login.user.token,
        }
      })
      .then((data) => {
        console.log(data.data);
        if (data.data.code === 200) {
          liveList.value = data.data.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
}

onMounted(() => {
  getLiveList();
})

</script>

<template>
  <div style="background-color: #f1f2f3; text-align: center; margin-top: 30px">
    <div style="width: 1380px; margin-left: calc((100vw - 1380px)/2)">
      <div style="width: 100%; text-align: left">
<!--        <span style="padding-left: 10px; font-size: 25px; font-weight: 400">推荐直播</span>-->
      </div>
      <el-card class="box-card" @click="toPagePath('/live/' + '1')" style="cursor:pointer" v-for="(item, index) in liveList" :key="index">
        <div style="width: 92%; height: 60%; padding-left: 4%; padding-top: 4%" @click="toPagePath('/live/' + item.liveid)">
          <img style="width: 253.914px; height: 142.820px; border-radius: 5px"
                    :src="item.roomAvatar"
                    fit="scale-down"/>
        </div>
        <div style="text-align: left; width: 92%; margin-left: 4%;  display: flex">
          <el-avatar :size="50" :src="circleUrl" style="margin-top: 2%; margin-left: 1%"/>
          <div style="display: inline-block; margin-left: 15px; margin-top: 2.5%;">
            <div>
              <span style="font-size: 18px">{{item.roomname}}</span>
            </div>
            <div>
              <span style="font-size: 14px; color: #7f7f7f">{{item.username}}</span>
            </div>
          </div>
<!--          <div style="margin-top: 12.5%; margin-left: 17%">-->
<!--            <el-button type="primary" size="small">关注</el-button>-->
<!--          </div>-->
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.box-card {
  box-shadow: var(--el-box-shadow);
  box-shadow: none;
  border: none;
  border-radius: 0;
  background-color: #f1f2f3;
  height: 224px;
  width: 276px;
/*/ / margin-left: 10 px;*/
/*/ / margin-right: 10 px;*/
/*/ / margin-bottom: 27 px;*/
/*/ / padding-top: 10 px;*/
  margin: 0;
  --el-card-padding: 0 !important;
  display: inline-block;
}

.box-card:hover {
  animation: fly 0.1s linear;
  animation-fill-mode: both;
}
</style>