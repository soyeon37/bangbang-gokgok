<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()">글쓰기</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table striped hover :items="articles" :fields="fields" @row-clicked="viewArticle">
          <template #cell(title)="data">
            <router-link
              :to="{
                name: 'recruitview',
                params: {
                  articleno: data.item.articleId,
                },
              }"
            >
              {{ data.item.title }}
            </router-link>
          </template>
        </b-table>
      </b-col>
    </b-row>
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
    <ul class="pagination justify-content-center">
      <li class="page-item" @click="getArticles(1)">
        <a href="#" class="page-link">최신</a>
      </li>
      <li
        class="page-item"
        :class="{ disabled: currentPage === 1 }"
        @click="getArticles(currentPage - 1)"
      >
        <a href="#" class="page-link">이전</a>
      </li>
      <li
        class="page-item"
        v-for="page in totalPageCount"
        :key="page"
        :class="{ active: currentPage === page }"
        @click="getArticles(page)"
      >
        <a href="#" class="page-link">{{ page }}</a>
      </li>
      <li
        class="page-item"
        :class="{ disabled: currentPage === totalPageCount }"
        @click="getArticles(currentPage + 1)"
      >
        <a href="#" class="page-link">다음</a>
      </li>
      <li class="page-item" @click="getArticles(totalPageCount)">
        <a href="#" class="page-link">마지막</a>
      </li>
    </ul>
  </b-container>
</template>

<script>
import http from "@/api/http";
// import { listArticle } from "@/api/board";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "RecruitList",
  components: {},
  data() {
    return {
      search_option: "1",
      search_key: "",
      search_word: "",
      articles: [],
      currentPage: 1,
      totalPageCount: 0,
      fields: [
        { key: "articleId", label: "글번호", tdClass: "tdClass" },
        { key: "title", label: "제목", tdClass: "tdTitle" },
        { key: "userId", label: "작성자", tdClass: "tdClass" },
        { key: "registerTime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
        { key: "likes", label: "좋아요", tdClass: "tdClass" },
      ],
    };
  },

  computed: {
    ...mapState(userStore, ["userInfo"]),
  },

  async created() {
    await this.getArticles(1);
  },
  watch: {
    search_option: {
      handler(newVal, oldVal) {
        if (newVal !== oldVal) {
          this.getArticles(this.currentPage);
        }
      },
      immediate: true, // 컴포넌트가 생성될 때 watch가 바로 실행되도록 합니다.
    },
  },
  methods: {
    async getArticles(page) {
      const { data } = await http.get(`/recruit/list`, {
        params: {
          pgno: page.toString(),
          key: this.search_key,
          word: this.search_word,
          "search-option": this.search_option,
        },
      });
      this.articles = data.articles;
      this.currentPage = data.pageNavigation.currentPage;
      this.totalPageCount = data.pageNavigation.totalPageCount;
    },
    moveWrite() {
      if (this.userInfo == null) {
        alert("로그인 후 글을 작성해주세요!");
        return false;
      }

      this.$router.push({ name: "recruitwrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "recruitview",
        params: { articleno: article.articleId },
      });
    },
    async searchArticle() {
      if (this.search_word === "") {
        alert("검색 단어를 입력해주세요!");
        return;
      }
      console.log("검색시작");

      const { data } = await http.get(`/recruit/list`, {
        params: {
          pgno: "1",
          key: `${this.search_key}`,
          word: `${this.search_word}`,
          "search-option": `${this.search_option}`,
        },
      });

      this.articles = data.articles;
      this.currentPage = data.pageNavigation.currentPage;
      this.totalPageCount = data.pageNavigation.totalPageCount;
    },
  },
};
</script>

<style scoped>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdTitle {
  width: 300px;
  text-align: left;
}
</style>
