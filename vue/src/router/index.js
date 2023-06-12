import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import FriendView from "../views/FriendView";
import HotplaceView from "../views/HotplaceView";
import ShareView from "../views/ShareView";
import TripsearchView from "../views/TripsearchView";
import RecruitView from "@/views/RecruitView";

import store from "@/store";

Vue.use(VueRouter);
// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  // 로그인을 한 사람이지만, 아직 인증처리 전
  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("userStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import(/* webpackChunkName: "user" */ "@/views/AppUser"),
    children: [
      {
        path: "join",
        name: "join",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserLogin"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserMyPage"),
      },
      {
        path: "usermodify",
        name: "usermodify",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserModify"),
      },
      {
        path: "usermodifymove",
        name: "usermodifymove",
        beforeEnter: onlyAuthUser,
        props: true,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserModifyMove"),
      },
      {
        path: "userdelete",
        name: "userdelete",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserDelete"),
      },
    ],
  },
  {
    path: "/notice",
    name: "notice",
    component: () => import("@/views/NoticeView"),
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "noticelist",
        component: () => import("@/components/notice/NoticeList"),
      },
      {
        path: "write",
        name: "noticewrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeWrite"),
      },
      {
        path: "view/:articleno",
        name: "noticeview",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeView"),
      },
      {
        path: "modify",
        name: "noticemodify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeModify"),
      },
      {
        path: "delete/:articleno",
        name: "noticedelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeDelete"),
      },
    ],
  },

  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import("@/components/board/BoardList"),
      },
      {
        path: "write",
        name: "boardwrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardWrite"),
      },
      {
        path: "view/:articleno",
        name: "boardview",
        component: () => import("@/components/board/BoardView"),
      },
      {
        path: "modify",
        name: "boardmodify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify"),
      },
      {
        path: "delete/:articleno",
        name: "boarddelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDelete"),
      },
    ],
  },
  {
    path: "/recruit",
    name: "recruit",
    component: () => import("@/views/RecruitView"),
    redirect: "/recruit/list",
    children: [
      {
        path: "list",
        name: "recruitlist",
        component: () => import("@/components/recruit/RecruitList"),
      },
      {
        path: "write",
        name: "recruitwrite",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/recruit/RecruitWrite"),
      },
      {
        path: "view/:articleno",
        name: "recruitview",
        component: () => import("@/components/recruit/RecruitView"),
      },
      {
        path: "modify",
        name: "recruitmodify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/recruit/RecruitModify"),
      },
      {
        path: "delete/:articleno",
        name: "recruidtelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/recruit/RecruitDelete"),
      },
    ],
  },

  {
    path: "/hotplace",
    name: "hotplace",
    component: HotplaceView,
  },
  {
    path: "/recruit",
    name: "recruit",
    component: RecruitView,
  },
  {
    path: "/friend",
    name: "friend",
    component: FriendView,
  },
  {
    path: "/share",
    name: "share",
    component: ShareView,
  },
  {
    path: "/tripsearch",
    name: "tripsearch",
    component: TripsearchView,
  },
  {
    path: "/tripsearch/:keyword/:isRun",
    name: "tripsearchkeyword",
    component: TripsearchView,
  },
  {
    path: "/myplan/:planId",
    name: "myplanmodify",
    beforeEnter: onlyAuthUser,
    component: () => import("@/components/plan/MyPlanDetail"),
  },
  {
    path: "/myplan",
    name: "myplan",
    beforeEnter: onlyAuthUser,
    component: () => import("@/components/plan/MyPlanView"),
  },
  {
    path: "/attraction/search",
    name: "search",
    component: () => import("@/components/TripSearchComponent"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
