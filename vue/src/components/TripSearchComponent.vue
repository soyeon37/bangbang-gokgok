<template>
  <div>
    <div style="height: 50px"></div>
    <!-- 검색 결과 내 재검색 입력창 -->
    <form class="d-md-flex my-3" onsubmit="return false;" role="search">
      <input
        type="text"
        class="form-control"
        id="search-keyword"
        name="searchInResult"
        placeholder="결과 내 재검색"
        value="${params.searchInResult}"
      />
      <button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
    </form>
    <!-- 관광지 리스트 start -->
    <div
      id="trip-list-container"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <div id="trip-list" class="row justify-content-around"></div>
    </div>
  </div>
</template>

<script>
// import http from "@/api/http"; // 주석용

export default {
  name: "TripSearchComponent",
  components: {},
  data() {
    return {
      message: "",
      places: [],
      tripList: ``,
      imageSrc: "",
      markerInfo: "",
    };
  },
  async created() {
    // console.log(this.areaCode);
    // const { data } = await http.get(`/attraction/search`, {
    //     params: {
    //       pageNo: "1",
    //       areaCode: $route.params.areaCode,
    //       sigunguCode: this.sigunguCode,
    //       contentTypeId: this.contentTypeId,
    //       keyword: this.keyword,
    //       searchInResult : this.keyword,
    //     },
    // });
    // this.places = data.places;
  },
  methods: {
    handleRegist(e) {
      console.log(e.target.id);
      let form = document.querySelector(`#form-regist-trip-\${e.target.id}`);
      form.setAttribute("action", "${root}/trip/registTrip");
      form.submit();
    },
  },
};
</script>

<style scoped></style>
