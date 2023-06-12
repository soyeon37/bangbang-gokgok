<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.userid"
            type="text"
            required
            placeholder="작성자 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="title-group"
          label="제목:"
          label-for="title"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="title"
            v-model="article.title"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
// import { writeNotice, modifyNotice, getNotice } from "@/api/notice";

export default {
  name: "NoticeInputItem",
  components: {},
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        title: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  async created() {
    if (this.type === "modify") {
      try {
        const { data } = await http.get(`/notice/${this.$route.params.articleno}`);
        this.article.articleno = data.article.noticeId;
        this.article.userid = data.article.userId;
        this.article.title = data.article.title;
        this.article.content = data.article.content;
      } catch (error) {
        console.log(error);
      }
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid &&
        ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err &&
        !this.article.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registNotice() : this.modifyNotice();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.title = "";
      this.article.content = "";
      this.moveList();
    },
    async registNotice() {
      try {
        const { data } = await http.post(`notice/write`, {
          // noticeId: null,
          // userId: null,
          title: this.article.title,
          content: this.article.content,
          // hit: null,
          // registerTime: null,
          category: 1,
          // likes: null,
        });
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "등록이 완료되었습니다.";
        }
        alert(msg);
      } catch (error) {
        console.log(error);
      }
      this.moveList();
    },

    async modifyNotice() {
      try {
        const { data } = await http.put(`/notice/modify`, {
          noticeId: this.article.articleno,
          userId: this.article.userid,
          title: this.article.title,
          content: this.article.content,
          // hit: null,
          // registerTime: null,
          category: 1,
        });

        let msg = "수정 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "수정이 완료되었습니다.";
        }
        alert(msg);
        this.moveList();
      } catch (error) {
        console.log(error);
      }
    },
    moveList() {
      this.$router.push({ name: "noticelist" });
    },
  },
};
</script>

<style scoped></style>
