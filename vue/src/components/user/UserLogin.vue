<template>
  <div class="container">
    <form class="login">
      <b-alert show variant="danger" v-if="isLoginError"
        >아이디 또는 비밀번호를 확인하세요.</b-alert
      >
      <input class="logininput" type="text" v-model="user.userId" placeholder="ID" />
      <input class="logininput" type="password" v-model="user.userPwd" placeholder="Password" />
      <button class="loginbutton" type="button" @click="confirm">Login</button>
      <div class="signUpDiv">
        <a class="signUp" @click="movePage">아직 회원이 아니신가요?</a>
      </div>
    </form>

    <!-- 
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col>
          <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
        </b-col>
      </b-row>
      <b-row>
        <b-col></b-col>
        <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
              id="userId"
              v-model="user.userId"
              required
              placeholder="아이디 입력...."
              @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPwd">
              <b-form-input
              type="password"
              id="userPwd"
                v-model="user.userPwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
                ></b-form-input>
              </b-form-group>
              <b-button type="button" variant="primary" class="m-1" @click="confirm">로그인</b-button>
              <b-button type="button" variant="success" class="m-1" @click="movePage"
              >회원가입</b-button
              >
            </b-form>
          </b-card>
        </b-col>
        <b-col></b-col>
      </b-row>
    </b-container> -->
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "UserLogin",
  data() {
    return {
      // isLoginError: false,
      user: {
        userId: null,
        userPwd: null,
      },
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo", "getFriend"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        console.log("4. confirm() userInfo :: ", this.userInfo);
        await this.getFriend(this.userInfo.userId);
        this.$router.push({ name: "home" });
      }
    },
    movePage() {
      this.$router.push({ name: "join" });
    },
  },
};
</script>

<style scope>
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

.signUpDiv {
  margin-top: 15px;
}
.signUp {
  font-size: 0.9rem;
  color: #0077b6;
}
.signUp:hover {
  text-decoration: none;
  color: #cf9400;
}

/* .container{
    background-image: linear-gradient(to bottom right, #FF9EC4, #FFD07E);
    width: 900px;
    height: 900px;
    top: 50%;
    left: 50%;
    border-radius: 50%;
    z-index: -2;
} */
</style>

<style lang="scss" scoped>
@import "bourbon";
@mixin transform($transforms) {
  -webkit-transform: $transforms;
  -ms-transform: $transforms;
  transform: $transforms;
}

@mixin transition($properties, $duration) {
  -webkit-transition: $properties $duration;
  -moz-transition: $properties $duration;
  -ms-transition: $properties $duration;
  -o-transition: $properties $duration;
  transition: $properties $duration;
}
@mixin animation($animationName, $duration, $iterationCount, $timingFunction) {
  -webkit-animation: $animationName $duration $iterationCount $timingFunction;
  -moz-animation: $animationName $duration $iterationCount $timingFunction;
  -ms-animation: $animationName $duration $iterationCount $timingFunction;
  -o-animation: $animationName $duration $iterationCount $timingFunction;
  animation: $animationName $duration $iterationCount $timingFunction;
}

@mixin keyframes($animationName) {
  @-webkit-keyframes #{$animationName} {
    @content;
  }
  @-moz-keyframes #{$animationName} {
    @content;
  }
  @-ms-keyframes #{$animationName} {
    @content;
  }
  @-o-keyframes #{$animationName} {
    @content;
  }
  @keyframes #{$animationName} {
    @content;
  }
}

// Colors
$greenSeaweed: #0077b6;
$blueQueen: #00b4d8;
$redFire: #ffb30e;

// Fonts
$fontAsap: "Asap", sans-serif;

.login {
  overflow: hidden;
  background-color: white;
  padding: 50px 30px 42px 30px;
  border-radius: 10px;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 500px;

  @include transform(translate(-50%, -50%));
  @include transition(transform 300ms, box-shadow 300ms);

  box-shadow: 5px 10px 10px rgba($greenSeaweed, 0.2);

  &::before,
  &::after {
    content: "";
    position: absolute;
    width: 650px;
    height: 600px;
    border-top-left-radius: 40%;
    border-top-right-radius: 45%;
    border-bottom-left-radius: 35%;
    border-bottom-right-radius: 40%;
    z-index: -1;
  }

  &::before {
    left: 40%;
    bottom: -130%;
    background-color: rgba($blueQueen, 0.15);
    @include animation(waves, 6s, infinite, linear);
  }

  &::after {
    left: 35%;
    bottom: -125%;
    background-color: rgba($greenSeaweed, 0.2);
    @include animation(waves, 7s, infinite, linear);
  }

  > .logininput {
    font-family: $fontAsap;
    display: block;
    border-radius: 5px;
    font-size: 16px;
    background: white;
    width: 100%;
    border: 0;

    padding: 10px 10px;
    margin: 15px -10px;
  }

  > .loginbutton {
    font-family: $fontAsap;
    cursor: pointer;
    color: #fff;
    font-size: 16px;
    text-transform: uppercase;
    width: 85px;
    border: 0;
    padding: 10px 0;
    margin-top: 13px;
    margin-left: -5px;
    border-radius: 5px;
    background-color: $redFire;
    @include transition(background-color, 300ms);

    &:hover {
      background-color: darken($redFire, 5%);
    }
  }
}

@include keyframes(waves) {
  from {
    @include transform(rotate(0));
  }
  to {
    @include transform(rotate(360deg));
  }
}
</style>
