<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col><b-alert show variant="danger">삭제처리중...</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";

export default {
  name: "NoticeDelete",
  components: {},
  async created() {
    try {
      let msg = "삭제 처리시 문제가 발생하였습니다.";
      const { data } = await http.delete(`/notice/delete/${this.$route.params.articleno}`);
      if (data === "success") {
        msg = "삭제가 완료되었습니다.";
      }
      alert(msg);
      this.$router.push({ name: "noticelist" });
    } catch (error) {
      console.log(error);
    }
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
