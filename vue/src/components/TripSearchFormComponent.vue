<template>
  <div style="margin-bottom: 1%;">
    <b-form @submit.prevent="searchArea" class="d-md-flex my-3" role="search">
      <b-form-select style="margin-left: 3%;" v-model="areaCode" class="me-2" @change="fetchAreaDetails">
        <option value="0" selected>지역</option>
        <option v-for="area in areas" :value="area.code" :key="area.code">{{ area.name }}</option>
      </b-form-select>
      <b-form-select v-model="sigunguCode" class="me-2">
        <option value="0" selected>시군구</option>
        <option v-for="area in areaDetails" :value="area.code" :key="area.code">
          {{ area.name }}
        </option>
      </b-form-select>
      <!-- <b-form-select v-model="contentTypeId" class="me-2">
        <option value="0" selected>관광지 유형</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </b-form-select> -->
      <b-form-input type="text" v-model="keyword" placeholder="키워드입력"></b-form-input>
      <b-button type="submit" style="width: 450px; margin-right: 3%; margin-left: 1%;" variant="outline-primary">검색</b-button>
    </b-form>

      <!-- <input type="radio" name="payment" id="card">
<label for="card">
    <i class="fa fa-credit-card" aria-hidden="true"></i>
    <span>Card</span>
</label>
<input type="radio" name="payment" id="cash">
<label for="cash">
    <i class="fa fa-money" aria-hidden="true"></i>
    <span>Cash</span>
</label>        -->

      <!-- 하나도 체크 안하면 전체 검색, 하나 이상 체크하면 체크한 관광지들만 조회하도록 구현한다 -->
      <b-form-checkbox-group v-model="contentTypeIdList" style="margin-top: 1%;">
        <b-form-checkbox value="12">&#127958; 관광지</b-form-checkbox>
        <b-form-checkbox value="15">🎉 축제공연행사</b-form-checkbox>
        <b-form-checkbox value="25">🛤 여행코스</b-form-checkbox>
        <b-form-checkbox value="28">&#127938; 레포츠</b-form-checkbox>
        <b-form-checkbox value="32">&#127976; 숙박</b-form-checkbox>
        <b-form-checkbox value="38">&#128179; 쇼핑</b-form-checkbox>
        <b-form-checkbox value="39">&#127834; 음식점</b-form-checkbox>
      </b-form-checkbox-group>
      <!-- <b-button
        type="submit"
        class="mr-3"
        variant="outline-info"
        value="12"
        @click="searchContent(this.value)"
        >🍎 관광지</b-button
      >
      <b-button class="mr-3" variant="outline-info" value="15" @click="searchContent(this.value)"
        >🍎 축제공연행사</b-button
      >
      <b-button type="submit" class="mr-3" variant="outline-info" value="25" @click="searchArea"
        >🍎 여행코스</b-button
      >
      <b-button
        type="submit"
        class="mr-3"
        variant="outline-info"
        value="28"
        v-model="contentTypeId"
        @click="searchArea"
        >🍎 레포츠</b-button
      >
      <b-button type="submit" class="mr-3" variant="outline-info" value="32" @click="searchArea"
        >🍎 숙박</b-button
      >
      <b-button type="submit" class="mr-3" variant="outline-info" value="28" @click="searchArea"
        >🍎 쇼핑</b-button
      >
      <b-button type="submit" class="mr-3" variant="outline-info" value="39" @click="searchArea"
        >🍎 음식점</b-button
      > -->

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "TripSearchFormComponent",
  components: {},
  data() {
    return {
      serviceKey:
        "JPaih3TvB82xKTEEo0u7o2szUU3w5qjrXZGt5TFdhlf4%2FAJEndq3UTYMVZhxT%2BAlaF05ro%2FmiDUh4qnZYE78CA%3D%3D",
      areaCode: "",
      sigunguCode: "",
      contentTypeId: "",
      keyword: "",
      areas: [],
      areaDetails: [],
      contentTypeIdList: [],
      isRun: false,
    };
  },
  created() {
    this.fetchAreas();
    if (this.isRun) {
      this.searchArea();
    }
  },
  watch: {
    contentTypeIdList: function () {
      this.searchArea();
    },
  },
  methods: {
    async fetchAreas() {
      const url = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${this.serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;
      const response = await axios.get(url);
      this.areas = response.data.response.body.items.item;
    },
    async fetchAreaDetails() {
      if (this.areaCode == 0) {
        this.areaDetails = [];
        return;
      }
      const url = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${this.serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=${this.areaCode}&_type=json`;
      const response = await axios.get(url);
      this.areaDetails = response.data.response.body.items.item;
    },
    searchContent(val) {
      this.contentTypeId = val;
      console.log(this.contentTypeId);
      this.searchArea();
    },
    searchArea() {
      if (this.sigunguCode == 0 && this.areaCode != 0) {
        alert("시군구를 선택해주세요!");
        // this.areaDetails = [];
        return false;
      }
      if (this.sigunguCode == 0 && this.keyword.length < 2) {
        alert("두 글자 이상 입력해주세요!");
        return false;
      }
      console.log("emit직전");
      this.$emit(
        "search-area",
        this.areaCode,
        this.sigunguCode,
        this.contentTypeIdList,
        this.keyword
      );
      // TODO : 지역코드 + 시군구코드 + 콘텐트 타입보고 검색값에 넘길값 파악, Rest Sever와 연동하여 해당 지역에 해당하는 모든 Attraction을 받아와야 한다
      // this.$router.push({
      //   name: "search",
      //   params: {
      //     pageNo: 1,
      //     areaCode: this.areaCode,
      //     sigunguCode: this.sigunguCode,
      //     contentTypeId: this.contentTypeId,
      //     keyword: this.keyword,
      //     searchInResult : this.keyword,
      //   },
      //   });

      //   const url = `http://localhost/attraction?action=search&pageNo=1&keyword=&areaCode=${this.areaCode}&sigunguCode=${this.sigunguCode}&contentTypeId=${this.contentTypeId}`;
      //   const response = await axios.get(url);
      //   console.log(response);
      //   response 토대로 TripListComponent.vue에서 값을 띄워야 함 -> 자식끼리의 데이터 전달이므로 event bus로 보내거나 vuex를 쓰거나 해야 함
      //   임시적으로 event bus로 구현하겠음

      //   const tripUrl = `${root}attraction?action=search&pageNo=1&areaCode=${this.areaCode}&sigunguCode=${this.sigunguCode}&contentTypeId=${this.contentTypeId}&keyword=${this.keyword}`;
      //   window.location.href = tripUrl;
    },
  },
};
</script>

<style scoped></style>
