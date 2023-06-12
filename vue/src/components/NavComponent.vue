<template>
  <div>
    <header>
      <b-navbar toggleable="lg" type="dark" variant="info" class="navbar-transparent">
        <b-navbar-brand>
          <router-link to="/" class="link mr-5">
            <img src="../assets/img/bangbang-logo.png" alt="방방곡곡 로고" style="width: 100px; height: auto;">

          </router-link>
        </b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <router-link to="/notice" class="link mr-5 ">공지사항</router-link>
            <router-link to="/board" class="link mr-5 ">여행 후기 게시판</router-link>
            <router-link to="/recruit" class="link mr-5 ">친구모집게시판</router-link>
            <router-link to="/tripsearch" class="link mr-5 ">여행지검색</router-link>
            <!-- <router-link to="/about">마이페이지</router-link> -->
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <!-- after login -->
          <b-navbar-nav class="ml-auto" v-if="userInfo">
            <b-nav-item class="align-self-center">
              <b-avatar
                variant="primary"
                v-text="userInfo.userId.charAt(0).toUpperCase()"
              ></b-avatar>
              <span class="link ml-2"
                >{{ userInfo.userName }}({{ userInfo.userId }})님 환영합니다.</span
              >
            </b-nav-item>
            <b-nav-item class="align-self-center">
              <router-link :to="{ name: 'mypage' }" class="link align-self-center"
                >내 정보보기</router-link
              >
            </b-nav-item>
            <b-nav-item class="align-self-center">
              <router-link :to="{ name: 'myplan' }" class="link align-self-center"
                >나의 여행계획</router-link
              >
            </b-nav-item>
            <b-nav-item class="link align-self-center" @click.prevent="onClickLogout"
              ><div class="link">로그아웃</div>
            </b-nav-item>
          </b-navbar-nav>
          <!-- before login -->
          <b-navbar-nav class="ml-auto" v-else>
            <b-nav-item-dropdown right>
              <template #button-content>
                <b-icon icon="people-fill" font-scale="2" class="user-icon"></b-icon>
              </template>
              <b-dropdown-item href="#">
                <router-link :to="{ name: 'join' }" class="link">
                  <b-icon icon="person-circle" class="user-icon"></b-icon> 회원가입
                </router-link>
              </b-dropdown-item>
              <b-dropdown-item href="#">
                <router-link :to="{ name: 'login' }" class="link">
                  <b-icon icon="key"></b-icon> 로그인
                </router-link>
              </b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </header>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "NavComponent",
  data() {
    return {};
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scoped>
b-navbar {
  padding: 30px;
}

b-navbar-nav a {
  font-weight: bold;
  color: #2c3e50;
}

.link {
  text-decoration: none;
  color: #0077B6;
}
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
.navbar-transparent {
  background-color: transparent !important;
  border-color: transparent !important;
}
.user-icon{
  color: #0077B6 !important;
}
</style>
