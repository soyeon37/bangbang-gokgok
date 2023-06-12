<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-hotpink"><b-icon icon="journals"></b-icon> 공지사항</h3>
    <router-view></router-view>
  </b-container>
</template>

<script>
export default {
  name: "NoticeView",
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
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(231, 27, 139, 0.3) 30%);
}
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: "GmarketSansMedium";
}
</style>
