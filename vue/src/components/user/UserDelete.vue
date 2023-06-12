<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col><b-alert show variant="danger">삭제처리중...</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "UserDelete",
  created() {
    let param = this.$route.params.userId;
    console.log(param);
    this.deleteUserinfo(param);
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userDelete"]),

    deleteUserinfo(param) {
      this.userDelete(param);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기

      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scope>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
  font-weight: 700;
  font-style: normal;
}

* {
  font-family: 'GmarketSansMedium';
 
}
