<template>
  <div>
    <b-container class=" mt-3">
      <div v-for="plan in plans" v-bind:key="plan.id" class="centered-card">
        <b-card no-body class="overflow-hidden" style="max-width: 540px; display: flex">
          <b-row no-gutters>
            <b-col md="6">
              <b-card-img
                src="https://img.freepik.com/free-photo/aerial-shot-of-a-small-green-island-in-the-middle-of-the-ocean_181624-2933.jpg"
                alt="Image"
                class="rounded-0"
                style="width: 100%; height: 100%;"
              ></b-card-img>
            </b-col>
            <b-col md="6">
              <b-card-body title="여행 계획">
                <b-card-text>
                  <div>여행 제목 : {{ plan.planName }}</div>
                  <div>시작 날짜 : {{ plan.startDate }}</div>
                  <div>종료 날짜 : {{ plan.endDate }}</div>
                  <div>여행 설명 : {{ plan.content }}</div>
                </b-card-text>
                <b-button @click="moveModify(plan.planId, plan.planName)" variant="primary"
                  >여행 내용 보기</b-button
                >
              </b-card-body>
            </b-col>
          </b-row>
        </b-card>
      </div>
      <b-navbar-nav class="ml-auto">
        <b-nav-form class="justify-content-center">
          <b-form-select size="sm" v-model="search_option" class="mr-sm-2">
            <option :value="1" selected>최신글</option>
            <option :value="2">지난글</option>
            <option :value="3">조회순</option>
          </b-form-select>
          <b-form-select size="sm" v-model="search_key" class="mr-sm-2">
            <option :value="''" selected>검색 조건</option>
            <option :value="'article_id'">글번호</option>
            <option :value="'title'">제목</option>
            <option :value="'user_id'">작성자</option>
          </b-form-select>
          <b-form-input
            size="sm"
            class="mr-sm-2"
            placeholder="Search"
            v-model="search_word"
          ></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit" @click.prevent="searchArticle"
            >Search</b-button
          >
        </b-nav-form>
      </b-navbar-nav>
    </b-container>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
const userStore = "userStore";
export default {
  name: "MyPlanView",
  components: {},
  data() {
    return {
      search_option: "1",
      search_key: "",
      search_word: "",
      plans: [],
      plan: {
        planId: null,
        planName: null,
        startDate: null,
        endDate: null,
        content: null,
        isOpen: null,
      },
      message: "",
    };
  },
  async created() {
    try {
      const { data } = await http.get(`/plan/planlist/${this.userInfo.userId}`, {
        params: {
          pgno: "1",
          key: "",
          word: "",
          "search-option": "1",
        },
      });
      if (data.message === "success") {
        this.plans = data.plans;
        console.log(this.plans);
      } else {
        alert("목록을 불러오지 못했습니다.");
      }
    } catch (error) {
      console.log(error);
    }
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    moveModify(planId, planName) {
      console.log("planId", planId);
      this.$router.push({
        name: "myplanmodify",
        params: { planId: planId, planName: planName },
      });
    },
  },
};
</script>

<style scoped>
.centered-card {
  display: flex;
  justify-content: center; /* 가로 정렬 */
  align-items: center; /* 세로 정렬 */
  margin-bottom: 20px; /* 카드 간격 조절을 위한 여백 설정 */
}
</style>
