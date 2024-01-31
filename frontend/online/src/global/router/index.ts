import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
 
// 2. 配置路由
const defaultRoutes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    component: () => import("../../components/login.vue"),
  },
  {
    path: "/reg",
    component: () => import("../../components/reg.vue"),
  },
  {
    path: "/",
    component: () => import("../../components/index.vue"),
    name: 'index',
    meta: {
      title: '主页'
    }
  },
  {
    path: "/live/:liveId",
    component: () => import("../../components/live.vue"),
    name: 'live',
    meta: {
      title: '直播间'
    }
  },
];
// 1.返回一个 router 实列，为函数，里面有配置项（对象） history
const router = createRouter({
  history: createWebHistory(),
  routes: defaultRoutes,
});

// const errorRouter: Array<RouteRecordRaw> = [
//   { // 当没有匹配到正确路由的时候，匹配notOpen组件
//     path: '/:pathMatch(.*)*',   // 这个地方做了一个路由匹配。如果没有，则跳转到LoginOut组件里面
//     redirect: '/',
//     meta: {
//       footShow: false // true显示，false隐藏
//     }
//   }
// ]

// 3导出路由   然后去 main.ts 注册 router.ts
export {defaultRoutes}
export default router
