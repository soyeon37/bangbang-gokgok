<template>
  <div>
    <div class="row">
      <div class="col-3">
        <!-- <button class="btn btn-secondary button" @click="sort">To original order</button> -->
        <button class="registPlanButton" type="button" @click="update">일정 등록하기</button>
      </div>

      <div class="col-6">
        <h3 class="plan-title">{{ planName }}</h3>
        <div class="planList">

          <draggable
          class="list-group"
          tag="ul"
          v-model="trips"
          v-bind="dragOptions"
          @start="isDragging = true"
          @end="handleEndDrag"
        >
        <transition-group type="transition" name="flip-list">
          <div
          class="card order-card"
          :class="{
            active1: trip.contentTypeId == 12,
            active2: trip.contentTypeId == 15,
            active3: trip.contentTypeId == 25,
            active4: trip.contentTypeId == 28,
            active5: trip.contentTypeId == 32,
            active6: trip.contentTypeId == 38,
            active7: trip.contentTypeId == 39,
            'new-card': trip.isNew, // 새 카드에 대한 CSS 클래스 추가
          }"
              v-for="(trip, idx) in trips"
              v-bind:key="idx"
              >
              <div class="card-block">
                <h4 class="m-b-20">{{ trip.title }}</h4>
                <h2 class="text-right"><b-icon icon="journal-bookmark-fill"></b-icon></h2>
                <p class="m-b-0">{{ trip.addr1 }}<span class="f-right">{{ trip.zipcode }}</span></p>
                <b-icon icon="x" font-scale="3" @click="removeAt(idx)"></b-icon>
              </div>
            </div>
          </transition-group>
        </draggable>
      </div>
      </div>
      <!-- <div class="col-2"> -->
        <!-- <a class="friends" href="#"><b-icon icon="person-plus" font-scale="5"></b-icon></a> -->
      <!-- </div> -->
    </div>
  </div>
</template>
<script>
import http from "@/api/http";
import { mapState } from "vuex"; // vuex에서 얻어옴
const userStore = "userStore"; //
import draggable from "vuedraggable";
const message = [
  "vue.draggable",
  "draggable",
  "component",
  "for",
  "vue.js 2.0",
  "based",
  "on",
  "Sortablejs",
];
export default {
  name: "transition-example",
  display: "Transition",
  order: 6,
  components: {
    draggable,
  },
  props: {
    modalPlanId: {
      type: Number,
      required: false,
    },

    modalContentId: {
      type: Number,
      required: false,
    },

    modalContentTypeId: {
      type: Number,
      required: false,
    },

    modalTitle: {
      type: String,
      required: false,
    },
    isShare: {
      type: Boolean,
      required: false,
    },
    isAdd: {
      type: Boolean,
      required: false,
    },
    modalPlanName: {
      type: String,
      required: false,
    }
  },
  data() {
    return {
      myArray: [{ test: "test" }, { test: "test2" }],
      planId: this.$route.params.planId,
      planName: this.$route.params.planName,
      trips: [],
      trip: {
        // trip
        tripId: null,
        tripNo: null,
        contentId: null,
        planId: null,

        // attracion
        contentTypeId: null,
        title: null,
        addr1: null,
        addr2: null,
        zipcode: null,
        tel: null,
        firstImage: null,
        readcount: null,
        sidoCode: null,
        gugunCode: null,
        latitude: null,
        longitude: null,
        mlevel: null,
        overview: null,
        scrap_cnt: null,
      },
      list: message.map((name, index) => {
        return { name, order: index + 1 };
      }),
    };
  },
  async created() {
    let data;
    try {
      if (this.modalPlanId == undefined) {
        let response = await http.get(`/plan/tripList/${this.planId}`);
        data = response.data;
      } else {
        let response = await http.get(`/plan/tripList/${this.modalPlanId}`);
        data = response.data;
      }
      if (data.message === "success") {
        this.trips = data.trips;
        if (this.modalPlanId != undefined && this.isAdd != false) {
          this.trips.push({
            contentId: this.modalContentId,
            contentTypeId: this.modalContentTypeId,
            title: this.modalTitle,
            isNew: true, // 새로운 카드 표시를 위한 플래그 추가
          });
          this.planName = this.modalPlanName;
        }
      } else {
        alert("목록을 불러오지 못했습니다.");
      }
    } catch (error) {
      console.log(error);
    }
    // alert(this.modalTitle);
  },
  methods: {
    // sort() {
    //   this.list = this.list.sort((a, b) => a.order - b.order);
    // },

    handleEndDrag(event) {
      // ...
      if (!this.trips[event.newIndex].isNew) return; // 새로운 카드가 아니면 무시
      this.trips[event.newIndex].isNew = false; // 새로운 카드 플래그 제거
    },

    // 여행계획 수정을 어떻게 시킬까? vue단에서 들고있는 trips 싹 다 넘긴다음에 기존에 trip의 부모가 plan_id
    // 인거 싹 다 삭제한다음에 새로 넣는게 가장 먼저 떠오르는 방법이긴 하다

    // trip_id로 조회하면서 클라에서 넘어온 순서대로 trip_no를 재설정하는 방법으로도 해결할 수 있을 것으로 보임
    // 근데 새로운 여행지 추가파트는 그러면 어떻게 해결할 것인지?
    // 한번의 서버 접근마다 하나의 여행지만 추가가 가능한가? modal창 끄면 destory가 실행되는가? -> 아님
    // 추가버튼 누르면 그냥 서버단에 넣고 시작하자 이게 가장 짜기가 쉽다
    // 아니면 추가버튼 누르면 오른쪽에 요소 하나 두고 어디로 넣을지 선택하라고 하는 것도 괜찮아 보임
    // 일단 순서 바꾸는 메소드를 우선 짜자 순서 바꾸는 메소드만 짜면 추가는 금방 짠다
    async updatePlan() {
      const { data } = await http.put(`/plan`, {});
      console.log(data);
    },
    removeAt(idx) {
      this.trips.splice(idx, 1);
    },
    async update() {
      let data;
      if (this.modalPlanId == undefined) {
        let response = await http.put(`/plan/modifyTrip/${this.planId}`, this.trips);
        data = response.data;
      } else if (this.isShare == true) {
        // let response = await http.put(`/plan/modifyTrip/$`)
      } else {
        let response = await http.put(`/plan/modifyTrip/${this.modalPlanId}`, this.trips);
        data = response.data;
      }
      let msg = "여행 등록시 문제가 발생했습니다.";
      if (data === "success") {
        msg = "여행 등록이 완료되었습니다.";
      }
      alert(msg);

      if (this.modalPlanId == undefined) {
        this.$router.push({ name: "myplan" });
      } else {
        this.$emit("close-modal");
      }
    },
    // add: function() {
    //   this.list.push({ name: "Juan " + id, id: id++ });
    // },
    // replace: function() {
    //   this.list = [{ name: "Edgard", id: id++ }];
    // },
    // checkMove: function(e) {
    //   window.console.log("Future index: " + e.draggedContext.futureIndex);
    // }
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    dragOptions() {
      return {
        animation: 0,
        group: "description",
        disabled: false,
        ghostClass: "ghost",
      };
    },
    // draggingInfo() {
    //   return this.dragging ? "under drag" : "";
    // }
  },
};
</script>

<style scoped>
.button {
  margin-top: 35px;
}

.flip-list-move {
  transition: transform 0.5s;
}

.no-move {
  transition: transform 0s;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.list-group {
  min-height: 20px;
}

.list-group-item {
  cursor: move;
}

.list-group-item i {
  cursor: pointer;
}

.friends {
  right: 0;
  position: absolute;
  display: fixed;
}

/* body{
    margin-top:20px;
    background:#FAFAFA;
} */
.order-card {
  color: #fff;
}

/* .bg-c-blue {
    background: linear-gradient(45deg,#4099ff,#73b4ff);
} */

.active1 {
  background: linear-gradient(45deg, #4099ff, #73b4ff);
}

.active2 {
  background: linear-gradient(45deg, #2ed8b6, #59e0c5);
}

.active3 {
  background: linear-gradient(45deg, #f87575, #ffa9a3);
}

.active4 {
  background: linear-gradient(45deg, #ff5370, #ff869a);
}

.active5 {
  background: linear-gradient(45deg, #9381ff, #b8b8ff);
}

.active6 {
  background: linear-gradient(45deg, #99d98c, #d9ed92);
}

.active7 {
  background: linear-gradient(45deg, #ff8500, #ffb64d);
}

.new-card {
  opacity: 0.5;
}

/* .bg-c-green {
    background: linear-gradient(45deg,#2ed8b6,#59e0c5);
}

.bg-c-yellow {
    background: linear-gradient(45deg,#FFB64D,#ffcb80);
}

.bg-c-pink {
    background: linear-gradient(45deg,#FF5370,#ff869a);
} */

.card {
  cursor: move;
  width: 60%;
  border-radius: 5px;
  -webkit-box-shadow: 0 1px 2.94px 0.06px rgba(4, 26, 55, 0.16);
  box-shadow: 0 1px 2.94px 0.06px rgba(4, 26, 55, 0.16);
  border: none;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 30px;
  -webkit-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

.card .card-block {
  padding: 25px;
}

.order-card i {
  font-size: 26px;
}

.f-left {
  float: left;
}

.f-right {
  float: right;
}

.registPlanButton{
  background-color: #ffb30e;
  padding: 20px 40px 20px 40px;
  border: none;
  color: white;
  /* width: 140px; */
  /* height: 45px; */
  font-size: 17px;
  position: fixed;
  /* top: 180px; */
  /* left: 150px; */
  bottom: 140px;
  right: 130px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}
.registPlanButton:hover {
  background-color: #FB8500;
  box-shadow: 0px 15px 20px rgba(245, 194, 10, 0.918);
  color: #fff;
  transform: translateY(-7px);
}
.plan-title{
  text-align: center;
  background-color: #fff;
  padding: 30px 20px 30px 20px;
  margin-top: 5%;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(3, 186, 210, 0.1);
  
}
</style>

<style lang="scss" scoped>
@mixin transition($properties, $duration) {
  -webkit-transition: $properties $duration;
  -moz-transition: $properties $duration;
  -ms-transition: $properties $duration;
  -o-transition: $properties $duration;
  transition: $properties $duration;
}
$redFire: #ffb30e;
.planList{
  border-radius: 10px;
  // position: absolute;
  padding: 50px 30px 42px 30px;
  box-shadow: 5px 10px 10px rgba(#0077b6, 0.2);
  margin-top: 10%;
  margin-bottom: 10%;
  @include transition(transform 300ms, box-shadow 300ms);
}
</style>