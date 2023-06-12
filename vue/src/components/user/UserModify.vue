<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isIdError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userId"
                required
                v-model="userinfo.userId"
                placeholder="아이디를 입력해주세요."
                @keyup.enter="register"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPwd">
              <b-form-input
                type="password"
                id="userPwd"
                v-model="userinfo.userPwd"
                required
                placeholder="비밀번호를 입력해주세요."
                @keyup.enter="register"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 재확인:" label-for="userPwd">
              <b-form-input
                type="password"
                id="userPwd"
                :state="pwdState"
                v-model="userinfo.userChkPwd"
                required
                placeholder="비밀번호를 다시 입력해주세요."
                @keyup.enter="register"
              ></b-form-input>
              <b-form-invalid-feedback id="input-live-feedback">
                비밀번호가 다릅니다.
              </b-form-invalid-feedback>
            </b-form-group>

            <b-form-group label="이름 입력:" label-for="userName">
              <b-form-input
                id="userName"
                v-model="userinfo.userName"
                required
                placeholder="이름을 입력해주세요."
                @keyup.enter="register"
              ></b-form-input>
            </b-form-group>

            <b-form-group label="이메일 입력:" label-for="email">
              <div style="display: block">
                <b-form-input
                  style="display: inline-block; width: 45%"
                  id="emailId"
                  v-model="userinfo.emailId"
                  required
                  placeholder="이메일을 입력해주세요."
                  @keyup.enter="register"
                ></b-form-input
                ><span style="display: inline-block">@</span>
                <b-form-select
                  class="w-auto"
                  style="display: inline-block; width: 100%"
                  id="emailDomain"
                  v-model="userinfo.emailDomain"
                  :options="options"
                ></b-form-select>
              </div>
            </b-form-group>
            <b-form-group label="여행 타입 입력:" label-for="type">
              <b-form-input
                id="tytpe"
                v-model="userinfo.type"
                required
                placeholder="여행 타입을 입력해주세요."
                @keyup.enter="register"
              ></b-form-input>
            </b-form-group>
            <b-button type="button" variant="success" class="m-1" @click="check">정보수정</b-button>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
// import http from "@/api/http";
import { mapActions, mapGetters, mapState } from "vuex";

const userStore = "userStore";
export default {
  name: "UserModify",
  components: {},
  data() {
    return {
      userinfo: {
        userId: "",
        userOriginId: "",
        userPwd: "",
        userChkPwd: "",
        userName: "",
        emailId: "",
        emailDomain: "",
        type: "",
      },
      options: [
        { text: "네이버", value: "@naver.com" },
        { text: "구글", value: "@google.com" },
        { text: "싸피", value: "@ssafy.com" },
        { text: "다음", value: "@daum.net" },
        { text: "카카오", value: "@kako.com" },
        { text: "네이트", value: "@nate.com" },
        { text: "직접 입력", value: "direct" },
      ],
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapGetters(["checkUserInfo"]),
    pwdState() {
      if (this.userinfo.userChkPwd != "") {
        return this.userinfo.userChkPwd == this.userinfo.userPwd ? true : false;
      } else return false;
    },
  },
  mounted() {
    this.userinfo.userId = this.userInfo.userId;
    this.userinfo.userPwd = this.userInfo.userPwd;
    this.userinfo.userName = this.userInfo.userName;
    this.userinfo.emailId = this.userInfo.emailId;
    this.userinfo.emailDomain = this.userInfo.emailDomain;
    this.userinfo.type = this.userInfo.type;
  },
  methods: {
    ...mapActions(),
    check() {
      console.log(this.userinfo.userChkPwd);
      if (this.userinfo.userId == "") {
        alert("아이디를 입력해주세요.");
      } else if (this.userinfo.userPwd == "") {
        alert("비밀번호를 입력해주세요.");
      } else if (this.userinfo.userChkPwd == "") {
        alert("재확인 비밀번호를 입력해주세요.");
      } else if (this.userinfo.userName == "") {
        alert("이름을 입력해주세요.");
      } else if (this.userinfo.emailId == "") {
        alert("이메일을 입력해주세요.");
      } else if (this.userinfo.emailDomain == "") {
        alert("이메일도메인을 선택해주세요.");
      } else if (this.userinfo.userChkPwd !== this.userinfo.userPwd) {
        alert("비밀번호가 다릅니다.");
      } else {
        this.moveModify();
      }
    },
    //     async modify() {
    //     try {
    //       const { data } = await http.put(`user/modify`, {
    //         userId: this.userinfo.userId,
    //         userPwd: this.userinfo.userPwd,
    //         userOriginId: this.userInfo.userId,
    //         userName: this.userinfo.userName,
    //         emailId: this.userinfo.emailId,
    //           emailDomain: this.userinfo.emailDomain,
    //           type: this.userinfo.type
    //       });
    //     let msg = "등록 처리시 문제가 발생했습니다.";
    //     console.log(data.message);
    //     if (data.message === "success") {
    //       msg = "수정이 완료되었습니다.";
    //     }
    //     alert(msg);
    //     this.$router.push({ name: "home" });
    //   } catch (error) {
    //     let msg = "이미 사용 중인 아이디입니다."
    //     alert(msg);
    //     console.log(error);
    //   }
    // }
    moveModify() {
      if (confirm("정말로 수정하시겠습니까?")) {
        this.$router.push({
          name: "usermodifymove",
          params: {
            userId: this.userinfo.userId,
            userPwd: this.userinfo.userPwd,
            userOriginId: this.userInfo.userId,
            userName: this.userinfo.userName,
            emailId: this.userinfo.emailId,
            emailDomain: this.userinfo.emailDomain,
            type: this.userinfo.type,
          },
        });
      }
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
}
</style>
