<template>
  <div>
    <h1 class="text-center mb-4">전기차 충전소 현황</h1>
    <div class="d-flex flex-column flex-md-row align-items-start align-items-md-center">
      <b-form-group label="시도" label-for="zcode" class="flex-grow-1 mb-2 mb-md-0 mr-md-2">
        <b-form-select id="zcode" v-model="zcode" aria-label="시도 선택">
          <option v-for="obj in zcodes" :key="obj.code" :value="obj.code">
            {{ obj.desc }}
          </option>
        </b-form-select>
      </b-form-group>
      <b-form-group label="구군" label-for="zscode" class="flex-grow-1 mb-2 mb-md-0 mr-md-2">
        <b-form-select id="zscode" v-model="zscode" aria-label="구군 선택">
          <option v-for="obj in zscodes" :key="obj.code" :value="obj.code">
            {{ obj.desc }}
          </option>
        </b-form-select>
      </b-form-group>
      <b-button variant="primary" @click="searchCharger" class="mt-4">검색</b-button>
    </div>
    <b-table striped hover :items="chargerList" :fields="fields">
      <template v-slot:cell(chgerType)="row">
        {{ row.item.chgerType | filterChargerType }}
      </template>
      <template v-slot:cell(stat)="row">
        {{ row.item.stat | filterStat }}
      </template>
      <template v-slot:cell(parkingFree)="row">
        {{ row.item.parkingFree | filterParkingFree }}
      </template>
    </b-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "FriendView",
  components: {},
  data() {
    return {
      chargerList: [],
      zcode: "",
      zscode: "",
      zcodes: [
        {
          code: 11,
          desc: "서울특별시",
        },
        {
          code: 29,
          desc: "광주광역시",
        },
        {
          code: 30,
          desc: "대전광역시",
        },
        {
          code: 26,
          desc: "부산광역시",
        },
        {
          code: 47,
          desc: "경상북도",
        },
      ],
      zscodes: [
        {
          code: 11680,
          desc: "서울강남구",
        },
        {
          code: 30200,
          desc: "대전유성구",
        },
        {
          code: 47190,
          desc: "구미시",
        },
        {
          code: 26440,
          desc: "부산강서구",
        },
        {
          code: 29200,
          desc: "광주광산구",
        },
      ],
      fields: [
        { key: "statId", label: "충전소명" },
        { key: "chgerType", label: "충전기타입" },
        { key: "addr", label: "주소" },
        { key: "useTime", label: "이용가능시간" },
        { key: "stat", label: "충전기상태" },
        { key: "parkingFree", label: "주차료무료" },
        { key: "lat", label: "위도" },
        { key: "lng", label: "경도" },
      ],
    };
  },
  filters: {
    filterChargerType(value) {
      switch (value) {
        case "01":
          return "DC 차데모";
        case "02":
          return "AC 완속";
        case "03":
          return "DC 차데모+AC3 상";
        case "04":
          return "DC콤보";
        case "05":
          return "DC 차데모+DC 콤보";
        case "06":
          return "DC 차데모+AC3 상+DC 콤보";
        case "07":
          return "AC3 상";
        case "89":
          return "H2";
        default:
          return "왜안돼지";
      }
    },

    filterStat(value) {
      switch (value) {
        case "0":
          return "알수없음";
        case "1":
          return "통신이상";
        case "2":
          return "사용가능";
        case "3":
          return "충전중";
        case "4":
          return "운영중지";
        case "5":
          return "점검중";
      }
    },

    filterParkingFree(value) {
      switch (value) {
        case "Y":
          return "무료";
        case "N":
          return "유료";
      }
    },
  },
  created() {},
  methods: {
    searchCharger() {
      // async를 붙이는게 권장됨
      const baseUrl = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo";
      const serviceKey =
        "q%2BkkMPX4jSJcj91gAF6anSSKmaD6slvHXOYR4URxcSjfs0a6VFPo0vN4Sol3ex8XHEg%2BRcEUSoJV82k93fCR9Q%3D%3D";
      const pageNo = 1;
      const numOfRows = 30;

      let url = `${baseUrl}?serviceKey=${serviceKey}&pageNo=${pageNo}&numOfRows=${numOfRows}`;
      if (this.zscode) url += `&zscode=${this.zscode}`;
      else url += `&zcode=${this.zcode}`;

      // await를 활용한 문법으로 변환하는 것이 권장됨
      axios.get(url).then((response) => {
        console.log(response);
        this.chargerList = response.data.items[0].item;
      });
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
