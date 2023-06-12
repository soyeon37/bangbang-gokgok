import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import noticeStore from "@/store/modules/noticeStore";
import boardStore from "@/store/modules/boardStore";
import userStore from "@/store/modules/userStore";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    noticeStore,
    boardStore,
    userStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
