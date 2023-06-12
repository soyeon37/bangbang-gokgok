<template>
  <!-- 부모 (게시글)에서 글 번호와 카테고리 얻어온 이후 서버랑 통신해서 해당 게시글에 있는 모든 댓글을 불러온다 -->
  <!-- 가장 하단에는 댓글을 달 수 있는 칸을 만든다  -->
  <!-- 대댓글 기능은 추후 구현한다 -->
  <div>
    <div v-for="comment in comments" :key="comment.id">
      <div>{{ comment.userId }} : {{ comment.content }}</div>
    </div>
    <input type="text" v-model="newComment" @keyup.enter="addComment" />
  </div>
</template>

<script>
export default {
  name: "",
  components: {},
  data() {
    return {
      newComment: "",
      comments: [],
    };
  },
  created() {},
  methods: {},
};
</script>

<style scoped></style>
