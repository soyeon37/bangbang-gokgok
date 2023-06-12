<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userId }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userName }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start"
                >{{ userInfo.emailId }}@{{ userInfo.emailDomain }}</b-col
              >
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.joinDate }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">여행타입</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.type }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="primary" @click="moveModify" class="mr-1">정보수정</b-button>
          <b-button variant="danger" @click="moveDelete">회원탈퇴</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>

    <img src="@/assets/img/trip_mbti.png" width="750" height="550" />

    <h3 class="mt-4">친구 목록</h3>
    <b-list-group>
      <b-list-group-item v-for="(friend, idx) in friends" :key="idx">
        {{ friend.followerId }}
      </b-list-group-item>
    </b-list-group>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapActions, mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(userStore, ["userInfo", "friendInfo"]),
  },

  async created() {
    const { data } = await http.get(`/friend/nowfriends/${this.userInfo.userId}`);
    this.friends = data;
  },

  data() {
    return {
      friends: [],
      myPosts: [],
      likedPosts: [],
    };
  },
  methods: {
    ...mapActions(userStore, ["getFriend"]),

    ...mapActions(),
    moveModify() {
      this.$router.push({ name: "usermodify" });
    },
    moveDelete() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.$router.replace({
          name: "userdelete",
          params: { userId: this.userInfo.userId },
        });
      }
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
</style>
