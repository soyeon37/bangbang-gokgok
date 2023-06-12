<template>
  <div>
    <!-- <b-sidebar id="sidebar-1" title="Sidebar" shadow v-model="showSidebar">
      <div class="px-3 py-2">
        <p>
          {{ selectedTitle }}을 일정에 추가합니다. <br />
          <b-button @click="openModal()">일정 목록 1</b-button> <br />
          <b-button @click="openModal()">일정 목록 2</b-button>
        </p>
        <b-img :src="selectedImage" fluid thumbnail></b-img>
      </div>
    </b-sidebar> -->

    <!-- <b-modal id="modal-1" title="Modal Title" size="xl" centered :ok-only="true" hide-footer>
      {{ selectedTitle }}
      여행 순서 어쩌구 <br />
      여행 순서 드래그 앤 드롭으로 설정 어쩌구 <br />
      저장 버튼 누르면 db에 반영...<br />
      <b-button class="btn btn-outline-success">저장</b-button>
    </b-modal> -->

    <a>결과내 재검색</a>
    <form class="d-md-flex my-3" onsubmit="return false;" role="search">
      <input
        type="text"
        class="form-control"
        id="search-keyword"
        name="searchInResult"
        placeholder="결과 내 재검색"
        value="${param.searchInResult}"
        v-model="research"
      />
      <button id="btn-search" class="btn btn-outline-success" type="button" @click="researchArea">
        검색
      </button>
      <div
        id="trip-list-container"
        style="display: flex; flex-direction: column; align-items: center"
      >
        <div id="trip-list" class="row justify-content-around" />
      </div>
    </form>
    <!-- 관광지 리스트 start -->
    <div
      id="trip-list-container"
      style="display: flex; flex-direction: column; align-items: center"
    >
      <div id="trip-list" class="row justify-content-around">
        <div
          v-for="area in areas"
          :key="area.title"
          class="card mt-4"
          style="width: 18rem; padding: 10px"
        >
          <img
            :src="area.firstImage"
            class="card-img-top"
            alt="관광지 이미지"
            style="width: 100%; height: 180px"
            loading="lazy"
          />
          <div class="card-body">
            <h5 class="card-title font-weight-bold">{{ area.title }}</h5>
            <p class="card-text">{{ area.addr1 }}{{ area.addr2 ? area.addr2 : "" }}</p>
            <p class="card-text">{{ area.tel }}</p>
            <form
              :id="'form-regist-trip-' + area.contentId"
              method="POST"
              @submit.prevent="registTrip(area.contentId)"
            >
              <input type="hidden" name="contentid" :value="area.contentId" />
              <button type="submit" class="btn btn-primary" @click="registTrip(area)">
                일정에 추가하기
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- 관광지 리스트 end -->
  </div>
</template>

<script>
export default {
  name: "TripListComponent",
  components: {},
  data() {
    return {
      tripList: ``,
      imageSrc: ``,
      markerInfo: ``,
      research: "",
      lists: [],
      areas: [],
      showSidebar: false,
      selectedTitle: "",
      selectedImage: "",
    };
  },
  props: {
    places: [],
  },
  watch: {
    places() {
      console.log("trip-list에서 여행지 변경 감지");
      this.areas = [];
      this.places.forEach((place) => {
        let obj = {};
        obj.title = place.title;
        obj.addr1 = place.addr1;
        obj.addr2 = place.addr2;
        obj.tel = place.tel;
        obj.firstImage = place.firstImage;
        obj.contentId = place.contentId;
        obj.contentTypeId = place.contentTypeId;
        this.areas.push(obj);
      });
    },
  },
  created() {},
  methods: {
    // openModal() {
    //   this.$bvModal.show("modal-1");
    // },

    registTrip(area) {
      // if (area.title == this.selectedTitle) {
      //   this.showSidebar = false;
      //   return false;
      // }
      // this.selectedTitle = area.title;
      // this.selectedImage = area.firstImage;
      // this.showSidebar = true;

      this.$emit("regist-trip", area.title, area.firstImage, area.contentId, area.contentTypeId);
    },

    toggleSidebar() {
      this.showSidebar = !this.showSidebar;
    },

    researchArea() {
      console.log("재검색하러갑시다");
      if (this.research === "") {
        alert("검색할 단어를 입력해주세요!");
        return false;
      }

      this.$emit("research-area", this.research);
    },
  },
};
</script>

<style scoped></style>
