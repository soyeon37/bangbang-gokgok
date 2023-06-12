<template>
  <div class="about">
    <h1>This is an about page</h1>
    데이터를 잘 받아오는지 확인합니다 <br />
    공공데이터<br />
    draggable 테스트<br />
    <draggable v-model="myArray">
      <transition-group>
        <div v-for="(todoItem, index) in myArray" v-bind:key="index">
          {{ todoItem.test }}
        </div>
      </transition-group>
    </draggable>
    <b-button @click="showArrayItems">버튼</b-button>
    로그인 유저 정보<br />
    <div v-if="userInfo">
      {{ this.userInfo.userId }} <br />
      {{ this.userInfo }}
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex"; // vuex에서 얻어옴
const userStore = "userStore"; // export 바깥에서 선언해도 되나봄
import draggable from "vuedraggable";

export default {
  name: "AboutView",
  components: {
    draggable,
  },
  data() {
    return {
      message: "",
      myArray: [{ test: "test" }, { test: "test2" }],
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]), // computed 안에서 userinfo 받아오면 됨
  },
  created() {},
  methods: {
    showArrayItems() {
      alert(this.myArray.join("\n"));
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: 700;
  font-style: normal;
}

* {
  font-family: "GmarketSansMedium";
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}
</style>
