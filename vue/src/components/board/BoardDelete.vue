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
  name: "BoardDelete",
  components: {},
  data() {
    return {
      message: "",
    };
  },
  async created() {
    let msg = "삭제 처리시 문제가 발생하였습니다.";
    const { data } = await http.delete(`/board/delete/${this.$route.params.articleno}`);
    if (data === "success") {
      msg = "삭제가 완료되었습니다.";
    }
    alert(msg);
    this.$router.push({ name: "boardlist" });
  },
  methods: {},
};
</script>

<style scoped></style>
