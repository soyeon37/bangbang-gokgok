<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-hotpink"><b-icon icon="journals"></b-icon> 친구모집게시판</h3>
    <router-view></router-view>
  </b-container>
</template>

<script>
export default {
  name: "RecruitView",
  components: {},
  data() {
    return {
      message: "",
    };
  },
  created() {},
  methods: {},
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
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}
</style>
