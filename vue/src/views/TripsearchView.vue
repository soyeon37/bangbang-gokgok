<template>
  <div>
    <trip-search-form-component @search-area="searchArea"></trip-search-form-component>
    <map-component :places="placesList" @regist-trip="registTrip"></map-component>
    <trip-list-component
      :places="placesList"
      @research-area="researchArea"
      @regist-trip="registTrip"
      v-show="this.placesList.length > 0"
    ></trip-list-component>
    <b-sidebar id="sidebar-1" title="Sidebar" shadow v-model="showSidebar">
      <p>
        {{ selectedTitle }}을 추가할 여행계획을 선택해주세요. <br />
        <b-img :src="selectedImage" fluid thumbnail></b-img>
      </p>
      <b-button class="btn btn-outline-success" @click="openAddModal">계획 추가</b-button>
      <div class="px-3 py-2" v-for="(plan, index) in planlist" :key="index">
        <b-card :title="plan.planName" :img-src="plan.image" img-alt="Image" img-top>
          <b-card-text>
            {{ plan.content }}
          </b-card-text>
          <b-button @click="openModal(plan)">계획 선택</b-button>
        </b-card>
        <!-- <b-button @click="openModal()">일정 목록 1</b-button> <br /> -->
        <!-- <b-button @click="openModal()">일정 목록 2</b-button> -->
      </div>
    </b-sidebar>

    <!-- 여행 목록 고르면 선택한 여행지가 해당 여행 계획 맨 마지막에 자동으로 가서 추가된다 -->
    <!-- 안에서 수정 버튼을 누를시에는 실제 db에서 반영된다 -->

    <!-- 여행 삭제 버튼 누르면 안에서 delete 구현해야 함 -->
    <!-- 구현완료 -->
    <!--  -->

    <b-modal id="modal-1" title="여행 계획 등록" size="xl" centered :ok-only="true" hide-footer>
      <my-plan-detail-vue
        :modalPlanId="selectedPlanId"
        :modalPlanName="selectedPlanName"
        :modalContentId="selectedContentId"
        :modalContentTypeId="selectedContentTypeId"
        :modalTitle="selectedTitle"
        @close-modal="closeModal"
        :isAdd="true"
      ></my-plan-detail-vue>
      해당 여행을 삭제하려면 아래 버튼을 눌러주세요.<br />
      <b-button class="btn btn-danger" @click="deletePlan()">삭제</b-button>
    </b-modal>

    <b-modal id="modal-2" title="여행 계획 추가" size="xl" centered :ok-only="true" hide-footer>
      <b-form-group
        id="title-group"
        label="여행 제목:"
        label-for="title"
        description="여행 제목을 입력하세요."
      >
        <b-form-input
          id="title"
          v-model="newPlanTitle"
          type="text"
          required
          placeholder="여행 이름 입력"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="content-group"
        label="여행 메모:"
        label-for="content"
        description="여행 메모를 입력하세요"
      >
        <b-form-input
          id="content"
          v-model="newPlanContent"
          type="text"
          required
          placeholder="여행 메모 입력"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="start-date-group"
        label="여행 시작 날짜:"
        label-for="start-date"
        description="여행 시작 날짜를 선택하세요."
      >
        <b-form-datepicker
          id="start-date"
          v-model="newStartDate"
          required
          placeholder="여행 시작 날짜 선택"
        ></b-form-datepicker>
      </b-form-group>

      <b-form-group
        id="end-date-group"
        label="여행 종료 날짜:"
        label-for="end-date"
        description="여행 종료 날짜를 선택하세요."
      >
        <b-form-datepicker
          id="end-date"
          v-model="newEndDate"
          required
          placeholder="여행 종료 날짜 선택"
        ></b-form-datepicker>
      </b-form-group>

      <b-button class="btn btn-outline-success" @click="addPlanList()">추가</b-button>
    </b-modal>
  </div>
</template>

<script>
import http from "@/api/http";
import MapComponent from "@/components/MapComponent.vue";
import TripSearchFormComponent from "@/components/TripSearchFormComponent.vue";
import axios from "axios";
import TripListComponent from "@/components/TripListComponent.vue";
import { mapState } from "vuex";
import MyPlanDetailVue from "@/components/plan/MyPlanDetail.vue";
const userStore = "userStore";

export default {
  name: "TripsearchView",
  components: {
    MapComponent,
    TripSearchFormComponent,
    TripListComponent,
    MyPlanDetailVue,
  },

  computed: {
    ...mapState(userStore, ["userInfo", "isLogin"]),
  },
  data() {
    return {
      areaCode: "",
      sigunguCode: "",
      contentTypeIdList: [],
      keyword: "",
      searchInResult: "",
      placesList: [],
      showSidebar: false,
      selectedTitle: "",
      selectedImage: "",
      selectedContentId: 0,
      selectedContentTypeId: 0,
      selectedPlanName:"",
      planlist: [],
      newPlanTitle: "",
      newPlanContent: "",
      newStartDate: "",
      newEndDate: "",
      selectedPlanId: null,
      isUser: false,
      isAdd: null,
      isRun: false,
    };
  },

  async created() {
    if (this.userInfo != null) {
      console.log("로그인 중");
      this.getPlanList();
      this.isUser = true;
    } else {
      console.log("로그인 안됨");
    }

    if (this.$route.params.isRun) {
      this.keyword = this.$route.params.keyword;
      console.log(this.keyword);
      this.searchArea("", "", [], this.keyword);
    }
  },
  methods: {
    async deletePlan() {
      const { data } = await http.delete(`/plan/${this.selectedPlanId}`);
      if (data === "success") {
        this.$bvModal.hide("modal-1");
        this.getPlanList();
      } else {
        console.log("여행 삭제 도중에 오류가 발생했습니다");
      }
    },

    async getPlanList() {
      if (this.userInfo == null) {
        return false;
      }
      const { data } = await http.get(`/plan/planlist/${this.userInfo.userId}?key=&word=&pgno=1`);

      if (data.message === "success") {
        this.planlist = data.plans;
      } else {
        console.log("data 입력 받기 도중에 오류가 발생했습니다");
      }
    },

    async addPlanList() {
      const { data } = await http.post(`/plan`, {
        planName: this.newPlanTitle,
        userId: this.userInfo.userId,
        startDate: this.newStartDate,
        endDate: this.newEndDate,
        content: this.newPlanContent,
      });
      console.log(data);
      this.getPlanList();
      this.$bvModal.hide("modal-2");
    },

    openModal(plan) {
      this.selectedPlanId = plan.planId;
      this.selectedPlanName = plan.planName;
      this.$bvModal.show("modal-1");
    },

    openAddModal() {
      this.newPlanTitle = "";
      this.newPlanContent = "";
      this.newStartDate = "";
      this.newEndDate = "";
      this.$bvModal.show("modal-2");
    },

    closeModal() {
      this.$bvModal.hide("modal-1");
    },

    registTrip(title, firstImage, contentId, contentTypeId) {
      if (!this.isUser) {
        confirm("로그인 후 이용가능한 서비스입니다.");
        this.$router.push({
          name: "login",
        });
        return;
      }
      console.log(contentId);
      console.log(contentTypeId);

      if (title == this.selectedTitle && this.showSidebar == true) {
        this.showSidebar = false;
        return false;
      }

      this.selectedTitle = title;
      this.selectedImage = firstImage;
      this.selectedContentId = contentId;
      this.selectedContentTypeId = contentTypeId;
      this.showSidebar = true;
    },

    async searchArea(areaCode, sigunguCode, contentTypeIdList, keyword) {
      this.areaCode = areaCode;
      this.sigunguCode = sigunguCode;
      this.contentTypeIdList = contentTypeIdList;
      this.keyword = keyword;
      // console.log("searchArea동작");
      let contentTypeIdsParam = this.contentTypeIdList.map((id) => `contentTypeId=${id}`).join("&");

      // const url = `http://localhost/attraction?pageNo=1&areaCode=${this.areaCode}&sigunguCode=${this.sigunguCode}&contentTypeId=${this.contentTypeId}&keyword=${this.keyword}`;
      const url = `http://localhost/attraction?pageNo=1&areaCode=${this.areaCode}&sigunguCode=${this.sigunguCode}&${contentTypeIdsParam}&keyword=${this.keyword}`;
      console.log(url);
      const { data } = await axios.get(url);
      // search결과값 토대로 placesList 갱신, 하위 컴포넌트들에 props로 넘기고, 하위 컴포넌트들에선 watch로 데이터 갱신시 지도와 리스트를 새로 그린다
      this.placesList = data.attractionList;
      // console.log(this.placesList);
      // 여기서 구군 얻어오는 것을 만들면 안된다
      // 시도, 구군 얻어오는 모든 곳에서 다 gugunGet()을 하게 됨
    },

    // trip-list-component에서 재검색 요청이 들어오면 실행됨
    async researchArea(searchInResult) {
      this.searchInResult = searchInResult;
      console.log("부모 컴포넌트에서 resarchArea가 실행되었으며 매개변수는 다음과 같다");
      console.log(
        `${this.areaCode} ${this.sigunguCode} ${this.contentTypeId} ${this.keyword} ${this.contentTypeId} ${this.keyword} ${this.searchInResult}`
      );
      let contentTypeIdsParam = this.contentTypeIdList.map((id) => `contentTypeId=${id}`).join("&");
      const url = `http://localhost/attraction?pageNo=1&areaCode=${this.areaCode}&sigunguCode=${this.sigunguCode}&${contentTypeIdsParam}&keyword=${this.keyword}&searchInResult=${this.searchInResult}`;
      console.log(url);
      const { data } = await axios.get(url);
      this.placesList = data.attractionList;
    },
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
