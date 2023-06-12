<template>
  <div class="home">
    <div class="masthead">
      <div class="slide"></div>
      <div class="slide"></div>
      <div class="slide"></div>
      <div class="slide"></div>
      <div class="slide"></div>
      <div class="title" style="position: absolute; width: 100%; text-align: center">
        <!-- Page heading-->
        <!-- <div  style="display: inline-block; width: 30%; padding-top: 5%; text-align: left;">
            <span style=" font-size: 5rem; color: white;">
              방방곡곡!<br>
            </span> 
            <span style="font-size: 2rem; color: white;">여행 정보, 숙박, 관광지를 검색하고<br>나만의 멋진 계획을 세워 공유해주세요!</span>
        </div>
        <div style="display: inline-block;  width: 20%; padding-top: 0;">
        </div>
        <div  style="display: inline-block;  width: 50%; margin-bottom: 50px;">
          <b-form-input class="col-4" style="display: inline-block;" size="lg" v-model="keyowrd" placeholder="어느 도시로 여행하시나요?"></b-form-input>
          <b-button class="col-3" style="display: inline-block; margin-left: 10%;" >Button</b-button>
        </div> -->
        <div class="row mt-5">
          <div class="col-6" style="height: 300px">
            <div
              style="
                font-size: 5rem;
                margin-top: 15px;
                padding-left: 15%;
                color: white;
                text-align: left;
              "
            >
              방방곡곡!
            </div>
            <br />

            <div style="font-size: 2rem; color: white; padding-left: 15%; text-align: left">
              여행 정보, 숙박, 관광지를 검색하고<br />나만의 멋진 계획을 세워 공유해주세요!
            </div>
          </div>
          <div class="col-6" style="height: 300px">
            <!-- <b-form-select
              class="col-3 mr-3"
              size="lg"
              style="display: inline-block"
              v-model="selected"
              :options="options"
            ></b-form-select>
            <b-form-input
              class="col-3 mr-3"
              style="display: inline-block; margin-top: 10%"
              size="lg"
              v-model="keyowrd"
              placeholder="어느 도시로 여행하시나요?"
            ></b-form-input>
            <b-button
              class="col-3"
              size="lg"
              style="display: inline-block; margin-right: 15%"
              variant="outline-light"
              >검색</b-button
            > -->
            <div class="col-3 mr-3" style="margin-top: 125px;  margin-left: 250px;">
              <input class="c-checkbox" type="checkbox" id="checkbox">
              <div class="c-formContainer">
                <form class="c-form" action="">
                  <input class="c-form__input" placeholder="어느 여행지가 궁금하신가요?" v-model="keyword" type="text" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$" required>
                  <label class="c-form__buttonLabel" for="checkbox">
                    <button class="c-form__button" type="button" @click="search">검색</button>
                  </label>
                  <label class="c-form__toggle" for="checkbox" data-title="SEARCH"></label>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 인기 게시판 start -->
    <div>
      <!-- 여행 게시판 소개글 start -->
      <div style="margin-top: 10%; margin-bottom: 10%">
        <h1>팔도강산 아름다운 우리나라의 여행지를 알아보아요!<br /></h1>
        <h3>
          나만 알고 있던 HOT PLACE를 소개하고,<br />
          다른 사람의 여행 후기를 찾는다면, 바로 여기!<br />
        </h3>
      </div>
      <!-- 여행 게시판 소개글 end -->
      <!-- 인기 관광지 start -->
      <div class="top-board">
        <div style="margin-top: 10%; margin-bottom: 10%">
          <h1>지금 이 순간! <br />가장 인기있는 여행 후기를 확인해요</h1>
        </div>
        <div class="mt-5">
          <Flicking :options="{ circular: true }">
            <div class="col-sm-12 col-md-6 col-lg-3 mb-4" v-for="(board, idx) in boards" :key="idx">
              <div class="card text-white card-has-bg click-col" id="card-board">
                <img
                  class="card-img d-none"
                  src="../assets/img/card-img-dolphin.jpg"
                  alt="Goverment Lorem Ipsum Sit Amet Consectetur dipisi?"
                />
                <div class="card-img-overlay d-flex flex-column">
                  <div class="card-body" >
                    <h2 class="card-title mt-4 mb-4 text-white">TOP {{ idx + 1 }}</h2>
                    <small class="card-meta mb-2">조회수 : {{ board.hit }}</small>
                    <h4 class="card-title mt-0">
                      <router-link
                        :to="{
                          name: 'boardview',
                          params: {
                            articleno: board.articleId,
                          },
                        }"
                      >
                        {{ board.title }}
                      </router-link>
                    </h4>
                    <!-- <small>{{ board.content.substring(0, 20) }}...</small> -->
                  </div>
                  <div class="card-footer" >
                    <div class="media">
                      <b-icon
                        icon="person-circle"
                        font-scale="3.5"
                        style="margin-left: 20"
                      ></b-icon>
                      <div class="media-body" style="margin-right: 65px">
                        <h5 class="my-0 text-white d-block">{{ board.userId }}</h5>
                        <small
                          ><b-icon icon="clock" style="margin-right: 7px"></b-icon
                          >{{ board.registerTime }}</small
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Flicking>
        </div>
      </div>
      <!-- 인기 관광지 end -->
    </div>
    <!-- 인기 게시판 end -->

    <!-- 최근 여행계획 start -->
    <div>
      <!-- 여행 게시판 소개글 start -->
      <div style="margin-top: 10%; margin-bottom: 10%">
        <h1>모두와 여행 계획을 공유해요!<br /></h1>
        <h3>
          직접 여행 계획을 만들고 수정하며,<br />
          내 입맛대로 여행 계획을 수정하고 공유해봐요!<br />
        </h3>
      </div>
      <!-- 여행 게시판 소개글 end -->
      <!-- 인기 관광지 start -->
      <div class="top-board">
        <div style="margin-top: 10%; margin-bottom: 10%">
          <h1>지금 이 순간! <br />가장 최근 만들어진 여행 계획을 확인해요</h1>
        </div>
        <div class="mt-5">
          <Flicking :options="{ circular: true }">
            <div class="col-sm-12 col-md-6 col-lg-3 mb-4" v-for="(plan, idx) in plans" :key="idx">
              <div class="card text-white card-has-bg click-col" id="card-plan">
                <img
                  class="card-img d-none"
                  src="../assets/img/card-img-island.jpg"
                  alt="Goverment Lorem Ipsum Sit Amet Consectetur dipisi?"
                />
                <div class="card-img-overlay d-flex flex-column">
                  <div class="card-body">
                    <h2 class="card-title mt-4 mb-4 text-white">TOP {{ idx + 1 }}</h2>
                    <!-- <small class="card-meta mb-2"> : {{ plan.scrapCnt }}</small> -->
                    <h4 class="card-title mt-0">
                      <router-link
                        :to="{
                          name: '/',
                          params: {
                            planId: plan.planId,
                          },
                        }"
                      >
                        {{ plan.planName }}
                      </router-link>
                    </h4>
                    <small
                      ><b-icon icon="clock" style="margin-right: 7px"></b-icon
                      >{{ plan.startDate }} ~ {{ plan.endDate }}</small
                    >
                  </div>
                  <div class="card-footer">
                    <div class="media">
                      <b-icon
                        icon="person-circle"
                        font-scale="3.5"
                        style="margin-left: 20"
                      ></b-icon>
                      <div class="media-body mt-3" style="margin-right: 65px">
                        <h5 class="my-0 text-white d-block">{{ plan.userId }}</h5>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Flicking>
        </div>
      </div>
      <!-- 인기 관광지 end -->
    </div>
    <!-- 최근 여행계획 end -->
  </div>
</template>

<script>
// @ is an alias to /src
import http from "@/api/http";
export default {
  name: "HomeView",
  components: {},
  data() {
    return {
      boards: [],
      board: {
        articleId: null,
        title: null,
        userId: null,
        registerTime: null,
        hit: null,
        likes: null,
        content: null,
      },
      trips: [],
      trip: {
        contentId: null,
        contentTypeId: null,
        title: null,
        addr1: null,
        addr2: null,
        zipcode: null,
        tel: null,
        firstImage: null,
        latitude: null,
        longitude: null,
        overview: null,
        scrapCnt: null,
      },
      plans: [],
      plan: {
        planId: null,
        planName: null,
        userId: null,
        startDate: null,
        endDate: null,
        isOpen: null,
      },
      keyowrd: "",
    };
  },
  async created() {
    // 짱 오래 걸림
    // const { tripdata } = await http.get(`/attraction/top`);
    //   this.trips = tripdata.trips;
    //   console.log(data.trips);
    this.boardTopList();
    this.planTopList();
  },
  methods: {
    async boardTopList() {
      const { data } = await http.get(`/board/top`);
      this.boards = data.boards;
      console.log(data.boards);
    },
    async planTopList() {
      const { data } = await http.get(`/plan/top`);
      this.plans = data.plans;
      console.log(this.plans);
    },
    search() {
      console.log("keyword: ",this.keyword);
      this.$router.push({
        name: "tripsearchkeyword",
        params:{keyword: this.keyword, isRun: true},
      });
    }
  },
};
</script>

<style scoped>
.masthead {
  overflow: hidden;
  position: relative;
  background-image: url("../assets/img/bg-city.jpg");
  background-color: #343a40;
  background-size: cover;
  padding-top: 8rem;
  padding-bottom: 8rem;
  height: 800px;
}
.masthead:before {
  content: "";
  position: absolute;
  background-color: #1c375e;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
}

.masthead .slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  animation: slide 20s infinite;
  opacity: 0.7;
}

.masthead .slide:nth-child(1) {
  background-image: url("../assets/img/bg-city.jpg");
  animation-delay: -0s;
}

.masthead .slide:nth-child(2) {
  background-image: url("../assets/img/bg-damyang.jpg");
  animation-delay: -4s;
}
/* C:\Users\SSAFY\Downloads\vyootstrap\src\assets\img\bg-chungbuk.jpg */
.masthead .slide:nth-child(3) {
  background-image: url("../assets/img/bg-hanok.jpg");
  animation-delay: -8s;
}

.masthead .slide:nth-child(4) {
  background-image: url("../assets/img/bg-jeju.jpg");
  animation-delay: -12s;
}

.masthead .slide:nth-child(5) {
  background-image: url("../assets/img/bg-chungbuk.jpg");
  animation-delay: -16s;
}
@keyframes slide {
  0%,
  15%,
  100% {
    transform: translateX(0);
    animation-timing-function: ease;
  }
  20% {
    transform: translateX(-100%);
    animation-timing-function: step-end;
  }
  95% {
    transform: translateX(100%);
    animation-timing-function: ease;
  }
}

@media (min-width: 768px) {
  .masthead {
    padding-top: 12rem;
    padding-bottom: 12rem;
  }
}
hr {
  margin: 1rem 0;
  color: #4e73df;
  background-color: currentColor;
  border: 0;
  opacity: 0.25;
}

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

.title {
  width: 70%;
}

.top-board1 {
  background-color: #4e73df;
}

a {
  color: white;
}
a:hover {
  color: #fb8500;
  text-decoration: none;
}
#card-board {
  background-image: url("../assets/img/card-img-dolphin.jpg");
}
#card-plan {
  background-image: url("../assets/img/card-img-wave.jpg");
}
#card-trip {
  background-image: url("../assets/img/card-img-dolphin.jpg");
}
</style>

<style lang="scss" scoped>
body {
  /* Created with https://www.css-gradient.com */
  background: #23ec55;
  background: -webkit-radial-gradient(top right, #23ec55, #2d51c1);
  background: -moz-radial-gradient(top right, #23ec55, #2d51c1);
  background: radial-gradient(to bottom left, #23ec55, #2d51c1);
}
.wrapper {
  margin: 10vh;
}
//post card styles

.card {
  border: none;
  transition: all 500ms cubic-bezier(0.19, 1, 0.22, 1);
  overflow: hidden;
  border-radius: 20px;
  min-height: 450px;
  min-width: 400px;
  box-shadow: 0 0 12px 0 rgba(0, 0, 0, 0.2);

  @media (max-width: 768px) {
    min-height: 350px;
  }

  @media (max-width: 420px) {
    min-height: 300px;
  }

  &.card-has-bg {
    transition: all 500ms cubic-bezier(0.19, 1, 0.22, 1);
    background-size: 120%;
    background-repeat: no-repeat;
    background-position: center center;
    &:before {
      content: "";
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      background: inherit;
      -webkit-filter: grayscale(1);
      -moz-filter: grayscale(100%);
      -ms-filter: grayscale(100%);
      -o-filter: grayscale(100%);
      filter: grayscale(100%);
    }

    &:hover {
      transform: scale(0.98);
      box-shadow: 0 0 5px -2px rgba(0, 0, 0, 0.3);
      background-size: 130%;
      transition: all 500ms cubic-bezier(0.19, 1, 0.22, 1);

      .card-img-overlay {
        transition: all 800ms cubic-bezier(0.19, 1, 0.22, 1);
        background: rgb(35, 79, 109);
        background: linear-gradient(0deg, rgba(4, 69, 114, 0.5) 0%, rgba(4, 69, 114, 1) 100%);
      }
    }
  }
  .card-footer {
    background: none;
    border-top: none;
    margin-bottom: 30px;
    .media {
      img {
        border: solid 3px rgba(255, 255, 255, 0.3);
      }
    }
  }
  .card-meta {
    color: #ffb703;
  }
  .card-body {
    transition: all 500ms cubic-bezier(0.19, 1, 0.22, 1);
    margin-top: 45px;
  }
  &:hover {
    .card-body {
      margin-top: 30px;
      transition: all 800ms cubic-bezier(0.19, 1, 0.22, 1);
    }
    cursor: pointer;
    transition: all 800ms cubic-bezier(0.19, 1, 0.22, 1);
  }
  .card-img-overlay {
    transition: all 800ms cubic-bezier(0.19, 1, 0.22, 1);
    background: rgb(35, 79, 109);
    background: linear-gradient(0deg, #caf0f8 0%, #096ba0 100%);
  }
}
@media (max-width: 767px) {
}

$black: #000000;
$cornflower-lilac: white;
$salmon: #00B4D8;
$white: #ffffff;
$your-pink: #00B4D8;

// body {
//     font-size: 10px; // change value to scale
//     font-family: Roboto, sans-serif;
//     background-color: $salmon;
    
//     margin: 0;
//     display: grid;
//     height: 100vh;
//     place-items: center;
// }

.c-checkbox {
    display: none;

    &:checked + .c-formContainer {
        .c-form {
            width: 37.5em;
        }

        .c-form__toggle {
            visibility: hidden;
            opacity: 0;
            transform: scale(0.7);
        }

        .c-form__input,
        .c-form__buttonLabel {
            transition: 0.2s 0.1s;
            visibility: visible;
            opacity: 1;
            transform: scale(1);
        }
    }

    &:not(:checked),
    &:checked {
        + .c-formContainer .c-form__input:required:valid ~ .c-form__toggle::before {
            content: 'Thank You! \1F60A';
        }
    }

    &:not(:checked) + .c-formContainer {
        .c-form__input:required:valid ~ .c-form__toggle {
            pointer-events: none;
            cursor: default;
        }
    }
}

.c-formContainer,
.c-form,
.c-form__toggle {
    width: 20em;
    height: 6.25em;
}

.c-formContainer {
    position: relative;
    font-weight: 700;
}

.c-form,
.c-form__toggle {
    position: absolute;
    border-radius: 6.25em;
    background-color: $white;
    transition: 0.2s;
}

.c-form {
    left: 50%;
    transform: translateX(-50%);
    padding: 0.625em;
    box-sizing: border-box;
    box-shadow: 0 0.125em 0.3125em rgba($black, 0.3);

    // position form inputs
    display: flex;
    justify-content: center;
}

.c-form__toggle {
    color: $salmon;
    top: 0;
    cursor: pointer;
    z-index: 1;

    // position message
    display: flex;
    align-items: center;
    justify-content: center;

    &::before {
        font-size: 1.75em;
        content: attr(data-title);
    }
}

.c-form__input,
.c-form__button {
    font: inherit;
    border: 0;
    outline: 0;
    border-radius: 5em;
    box-sizing: border-box;
}

.c-form__input,
.c-form__buttonLabel {
    font-size: 1.75em;
    opacity: 0;
    visibility: hidden;
    transform: scale(0.7);
    transition: 0s;
}

.c-form__input {
    color: $your-pink;
    height: 100%;
    width: 100%;
    padding: 0 0.714em;

    &::placeholder {
        color: currentColor;
    }

    &:required:valid {
        color: $salmon;

        + .c-form__buttonLabel {
            color: $white;

            &::before {
                pointer-events: initial;
            }
        }
    }
}

.c-form__buttonLabel {
    color: $cornflower-lilac;
    height: 100%;
    width: auto;
    
    // accepts click events
    &::before {
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        pointer-events: none;
        cursor: pointer;
    }
}

.c-form__button {
    color: inherit;
    padding: 0;
    height: 100%;
    width: 5em;
    background-color: $salmon;
}
</style>
