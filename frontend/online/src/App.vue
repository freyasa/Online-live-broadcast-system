<script setup lang="ts">
// import HelloWorld from './components/HelloWorld.vue'
// import Login from './components/login.vue'
import { useRouter } from "vue-router";
import { ref, reactive, toRefs, } from 'vue'
const router = useRouter();
const content = ref('content')
const data = reactive({
  patternVisible: false,
  debugVisible: false,
  aboutExeVisible: false,
})
const { patternVisible, debugVisible, aboutExeVisible } = toRefs(data)
const toPagePath = (url: string) => {
  // 这里回调写成对象，方便后面传参 push 写成 replace 不会留下历史记录
  router.push({
    path: url,
  });
};

</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
  <!-- <Login msg=""></Login> -->
  <div>

    <button @click="toPagePath('/login')">login</button>
    <button @click="toPagePath('/reg')">reg</button>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
