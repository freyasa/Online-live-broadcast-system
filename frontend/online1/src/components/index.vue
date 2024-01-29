<script setup lang="ts">
import {useRouter} from "vue-router";
import {ref, reactive, toRefs,} from 'vue'

const router = useRouter();

const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};
</script>

<template>
  <div style="background-color: #f1f2f3; text-align: center">
    <div style="width: 86%; margin-left: 7%">
      <div style="width: 100%; text-align: left">
        <span style="padding-left: 20px; font-size: 30px">推荐直播</span>
      </div>
      <el-card class="box-card" @click="toPagePath('/live/' + '1')" style="cursor:pointer">
        <el-image style="width: 92%; height: 60%"
                  src="https://i0.hdslb.com/bfs/archive/5980c275d22dc388c21affad6de81e568b3614a9.jpg" fit="scale-down"/>
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
      <el-card class="box-card">

      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>
      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>      <el-card class="box-card">
        MainCard
      </el-card>
    </div>
  </div>
</template>

<style lang="less" scoped>

.box-card {
  //box-shadow: var(--el-box-shadow);
  //box-shadow: none;
  //border: none;
  border-radius: 0;
  background-color: #f1f2f3;
  height: 220px;
  width: 280px;
  margin: 15px;
  padding-top: 10px;
  --el-card-padding: 0 !important;
  display: inline-block;
}

.box-card:hover {
  animation: fly 0.1s linear;
  animation-fill-mode: both;
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