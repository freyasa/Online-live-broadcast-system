<script lang="ts" setup>
import {ref} from "vue";

const urlTemplate = ref('https://i0.hdslb.com/bfs/archive/5980c275d22dc388c21affad6de81e568b3614a9.jpg')

</script>

<template>
  <div style="background-color: #f1f2f3; text-align: center; margin-top: 30px">
    <div style="width: 1380px; margin-left: 270px">
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

<style scoped>
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
</style>