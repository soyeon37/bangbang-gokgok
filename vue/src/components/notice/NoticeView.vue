<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          v-if="userInfo && userInfo.userId === 'admin'"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글수정</b-button
        >
        <b-button
          variant="outline-danger"
          v-if="userInfo && userInfo.userId === 'admin'"
          size="sm"
          @click="deleteArticle"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.noticeId}.
          ${article.title} [${article.hit}]</h3><div><h6>${article.userId}</div><div>${article.registerTime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "NoticeView",
  components: {},
  data() {
    return {
      article: {},
    };
  },
  // 자바에서 \n로 넘어오는거 html에 띄울 때 <br>로 변경되도록 comptued 속성 사용
  computed: {
    ...mapState(userStore, ["userInfo"]),
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  async created() {
    // 파라미터 싹 다 들고 다녀야하는거 잠시 넘김
    try {
      const { data } = await http.get(`/notice/${this.$route.params.articleno}`);
      this.article = data.article;
    } catch (error) {
      console.log(error);
    }
  },
  methods: {
    moveModifyArticle() {
      this.$router.replace({
        name: "noticemodify",
        params: { articleno: this.article.noticeId },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "noticedelete",
          params: { articleno: this.article.noticeId },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "noticelist" });
    },
  },
};
</script>

<style scoped></style>
