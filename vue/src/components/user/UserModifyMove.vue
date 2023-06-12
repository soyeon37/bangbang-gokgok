<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col><b-alert show variant="danger">변경 처리 중...</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
const userStore = "userStore";
export default {
  name: "UserModifyMove",
  props: {
    userId: {
      type: String,
      default: "",
    },
    userPwd: {
      type: String,
      default: "",
    },
    userOriginId: {
      type: String,
      default: "",
    },
    userName: {
      type: String,
      default: "",
    },
    emailId: {
      type: String,
      default: "",
    },
    emailDomain: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      user: {
        userId: "",
        userPwd: "",
        userOriginId: "",
        userName: "",
        emailId: "",
        emailDomain: "",
        type: "",
      },
    };
  },
  created() {
    this.user.userId = this.userId;
    this.user.userPwd = this.userPwd;
    this.user.userOriginId = this.userOriginId;
    this.user.userName = this.userName;
    this.user.emailId = this.emailId;
    this.user.emailDomain = this.emailDomain;
    this.user.type = this.type;
    console.log("회원 정보 받아옴:", this.user);
    this.updateUserinfo(this.user);
  },
  computed: {},
  methods: {
    ...mapActions(userStore, ["userUpdate"]),

    updateUserinfo(data) {
      this.userUpdate(data);

      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style>
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: 700;
  font-style: normal;
}

* {
  font-family: "GmarketSansMedium";
}
</style>
