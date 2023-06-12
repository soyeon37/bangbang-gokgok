<template>
  <div>
    <form class="signUp">
      <input class="signUpInput" v-model="userinfo.userId" type="text" placeholder="ID"  @keyup.enter="check">
      <input class="signUpInput" v-model="userinfo.userName" type="text" placeholder="Name"  @keyup.enter="check">
      <input class="signUpInput" v-model="userinfo.userPwd" type="password" placeholder="Password"  @keyup.enter="check">
      <input class="signUpInput"   v-model="userinfo.userChkPwd" type="password" placeholder="Check Password"  @keyup.enter="check">
      <input class="signUpInput"   v-model="userinfo.emailId" type="text" placeholder="Email"  @keyup.enter="check">
      <input class="signUpInput"  v-model="userinfo.emailDomain" placeholder="옵션을 입력하세요" />
    <select class="signUpInput"   v-model="userinfo.emailDomain">
      <option v-for="option in options" :key="option" :value="option.value">{{ option.text }}</option>
    </select>
      <button class="registerButton" type="button" @click="check">signUp</button>
      <div>
      </div>
    </form>
    <b-modal id="modalSurvey" title="여행 성향 파악" size="xl" centered :ok-only="true" hide-footer>
     <div class="registContainer" style="height: 600px">

      <!-- <div
          class="card order-card"
              >
              <div class="card-block">
                <h4 class="m-b-20">{{ trip.title }}</h4>
                <h2 class="text-right"><b-icon icon="journal-bookmark-fill"></b-icon></h2>
                <p class="m-b-0">{{ trip.addr1 }}<span class="f-right">{{ trip.zipcode }}</span></p>
                <b-icon icon="x" font-scale="3" @click="removeAt(idx)"></b-icon>
              </div>
            </div> -->

       <div class="registHalf card order-card" @click="addValue(1)">
              <div class="card-block">
                <h4 class="m-b-20" style="padding-left: 5%; padding-right: 5%;">{{ questions[questionIndex][0] }}</h4>
                <!-- <h2 class="text-right"><b-icon icon="journal-bookmark-fill"></b-icon></h2> -->
                <!-- <p class="m-b-0">{{ trip.addr1 }}<span class="f-right">{{ trip.zipcode }}</span></p> -->
                <!-- <b-icon icon="x" font-scale="3" @click="removeAt(idx)"></b-icon> -->
              </div>
         <!-- <p>{{ questions[questionIndex][0] }}</p> -->
       </div>
       <div class="registHalf card order-card" @click="addValue(2)">
        <!-- <div class="card-block"> -->
                <h4 class="m-b-20" style="padding-left: 5%; padding-right: 5%;" >{{ questions[questionIndex][1] }}</h4>
                <!-- <h2 class="text-right"><b-icon icon="journal-bookmark-fill"></b-icon></h2> -->
                <!-- <p class="m-b-0">{{ trip.addr1 }}<span class="f-right">{{ trip.zipcode }}</span></p> -->
                <!-- <b-icon icon="x" font-scale="3" @click="removeAt(idx)"></b-icon> -->
              </div>
         <!-- <p>{{ questions[questionIndex][1] }}</p> -->
       <!-- </div> -->
     </div>
   </b-modal>
  </div>

   <!-- <b-container class="bv-example-row mt-3">
      <b-modal id="modalSurvey" title="여행 성향 파악" size="xl" centered :ok-only="true" hide-footer>
        <div class="registContainer" style="height: 600px">
          <div class="registHalf" @click="addValue(1)">
            <p>{{ questions[questionIndex][0] }}</p>
          </div>
          <div class="registHalf" @click="addValue(2)">
            <p>{{ questions[questionIndex][1] }}</p>
          </div>
        </div>
      </b-modal>
      
      <b-row>
        <b-col>
          <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
        </b-col>
      </b-row>
      <b-row>
        <b-col></b-col>
      <b-col cols="10">
        <b-card class="text-center mt-3" style="max-width: 100%" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isIdError"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
              id="userId"
              v-model="userinfo.userId"
              required
              placeholder="아이디를 입력해주세요."
              @keyup.enter="register"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPwd">
              <b-form-input
              type="password"
              id="userPwd"
              v-model="userinfo.userPwd"
              required
              placeholder="비밀번호를 입력해주세요."
              @keyup.enter="register"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 재확인:" label-for="userPwd">
              <b-form-input
              type="password"
              id="userPwd"
              :state="pwdState"
              v-model="userinfo.userChkPwd"
              required
              placeholder="비밀번호를 다시 입력해주세요."
              @keyup.enter="register"
              ></b-form-input>
              <b-form-invalid-feedback id="input-live-feedback">
                비밀번호가 다릅니다.
              </b-form-invalid-feedback>
            </b-form-group>
            
            <b-form-group label="이름 입력:" label-for="userName">
              <b-form-input
                id="userName"
                v-model="userinfo.userName"
                required
                placeholder="이름을 입력해주세요."
                @keyup.enter="register"
                ></b-form-input>
              </b-form-group>
              
              <b-form-group label="이메일 입력:" label-for="email">
                <div style="display: block">
                  <b-form-input
                  style="display: inline-block; width: 30%"
                  id="emailId"
                  v-model="userinfo.emailId"
                  required
                  placeholder="이메일을 입력해주세요."
                  @keyup.enter="register"
                  ></b-form-input
                  >
                  @
                  <b-form-input
                  style="display: inline-block; width: 35%"
                  id="emailDomain"
                  v-model="userinfo.emailDomain"
                  required
                  placeholder="이메일을 입력해주세요."
                  @keyup.enter="register"
                  ></b-form-input
                  >
                  <b-form-select
                  
                  style="display: inline-block; width: 28%; margin-left: 7px;"
                  id="emailDomain"
                  v-model="userinfo.emailDomain"
                  :options="options"
                  ></b-form-select>
                </div>
            </b-form-group>

            <b-button type="button" variant="success" class="m-1" @click="check">회원가입</b-button>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row> 
  </b-container>--> 

</template>

<script>
import http from "@/api/http";
export default {
  computed: {
    pwdState() {
      if (this.userinfo.userChkPwd != "") {
        return this.userinfo.userChkPwd == this.userinfo.userPwd ? true : false;
      } else return false;
    },
  },
  name: "UserRegister",
  data() {
    return {
      userinfo: {
        userId: "",
        userPwd: "",
        userChkPwd: "",
        userName: "",
        emailId: "",
        emailDomain: "",
        type: "",
      },
      options: [
        { text: "네이버", value: "naver.com" },
        { text: "구글", value: "google.com" },
        { text: "싸피", value: "ssafy.com" },
        { text: "다음", value: "daum.net" },
        { text: "카카오", value: "kakao.com" },
        { text: "네이트", value: "nate.com" },
        { text: "직접 입력", value: "" },
      ],
      value: 0,
      questionIndex: 0,
      questions: [
        [
          // E, I
          "다양한 사람들과 만나서 새로운 추억을 쌓는 것이 좋다!",
          "사람이 북적북적한 관광명소보다 고즈넉한 골목길을 걸어다니는 것이 좋다!",
        ],
        [
          // S, N
          "경이로운 자연 명소를 보며 압도되는 기분을 느끼는 것이 좋다!",
          "역사가 깊은 유적지를 구경하며 과거를 경험할 수 있는 것이 좋다!",
        ],
        [
          // T, F
          "숙소는 단출하게! 여행지를 구경하는 것이 더 중요하다!",
          "여행 후 편안한 휴식은 필수! 숙소는 여행의 화룡점정이다!",
        ],
        [
          // P, J
          "바람따라 구름따라! 내가 가는 곳이 곧 길이다. 계획은 내 바짓가락을 붙잡을 뿐...",
          "일정을 꼼꼼히 계획하고, 하나하나 채워나가는 것이 묘미! 계획을 달성했을 때의 희열이 최고다!",
        ],
      ],
    };
  },

  methods: {
    addValue(num) {
      if (this.questionIndex == 0) {
        if (num == 1) this.userinfo.type += "E";
        else this.userinfo.type += "I";
      } else if (this.questionIndex == 1) {
        if (num == 1) this.userinfo.type += "S";
        else this.userinfo.type += "N";
      } else if (this.questionIndex == 2) {
        if (num == 1) this.userinfo.type += "T";
        else this.userinfo.type += "F";
      } else if (this.questionIndex == 3) {
        if (num == 1) this.userinfo.type += "P";
        else this.userinfo.type += "J";
      }

      if (this.questionIndex < this.questions.length - 1) {
        this.questionIndex += 1;
      } else {
        alert(`당신의 여행 mbti는 ${this.userinfo.type}입니다.`);
        this.register();
      }
    },

    check() {
      console.log(this.userinfo.userChkPwd);
      if (this.userinfo.userId == "") {
        alert("아이디를 입력해주세요.");
      } else if (this.userinfo.userName == "") {
        alert("이름을 입력해주세요.");
      } else if (this.userinfo.userPwd == "") {
        alert("비밀번호를 입력해주세요.");
      } else if (this.userinfo.userChkPwd == "") {
        alert("재확인 비밀번호를 입력해주세요.");
      } else if (this.userinfo.userChkPwd !== this.userinfo.userPwd) {
        alert("비밀번호가 다릅니다.");
      } else if (this.userinfo.emailId == "") {
        alert("이메일을 입력해주세요.");
      } else if (this.userinfo.emailDomain == "") {
        alert("이메일도메인을 선택해주세요.");
      } else {
        this.$bvModal.show("modalSurvey");
        // this.register();
      }
    },
    async register() {
      try {
        const { data } = await http.post(`user/join`, {
          userId: this.userinfo.userId,
          userPwd: this.userinfo.userPwd,
          userName: this.userinfo.userName,
          emailId: this.userinfo.emailId,
          emailDomain: this.userinfo.emailDomain,
          type: this.userinfo.type,
        });
        let msg = "등록 처리시 문제가 발생했습니다.";
        console.log(data.message);
        if (data.message === "success") {
          msg = "등록이 완료되었습니다.";
        }
        alert(msg);
        this.$router.push({ name: "login" });
      } catch (error) {
        let msg = "이미 사용 중인 아이디입니다.";
        alert(msg);
        console.log(error);
      }
    },
  },
};
</script>

<style scoped>
.registContainer {
  display: flex;
  width: 100%;
  height: 100%;
}

.registHalf {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
 
}

/* .signInDiv {
  margin-top: 15px;
}
.signIn {
  font-size: 0.9rem;
  color: #0077b6;
}
.signIn:hover {
  text-decoration: none;
  color: #cf9400;
} */

.card .card-block {
  padding: 25px;
}



.registHalf {
  background: linear-gradient(45deg, #4099ff, #73b4ff);
}
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
  color: white;
}

.card:hover{
  background-color: #2EE59D;
  box-shadow: 0px 15px 20px rgba(44, 128, 255, 0.4);
  color: #FFB703;
}


</style>

<style lang="scss" scoped>
@import "bourbon";
@mixin transform($transforms) {
  -webkit-transform: $transforms;
  -ms-transform: $transforms;
  transform: $transforms;
}

@mixin transition($properties, $duration) {
  -webkit-transition: $properties $duration;
  -moz-transition: $properties $duration;
  -ms-transition: $properties $duration;
  -o-transition: $properties $duration;
  transition: $properties $duration;
}
@mixin animation($animationName, $duration, $iterationCount, $timingFunction) {
  -webkit-animation: $animationName $duration $iterationCount $timingFunction;
  -moz-animation: $animationName $duration $iterationCount $timingFunction;
  -ms-animation: $animationName $duration $iterationCount $timingFunction;
  -o-animation: $animationName $duration $iterationCount $timingFunction;
  animation: $animationName $duration $iterationCount $timingFunction;
}
@mixin keyframes($animationName) {
  @-webkit-keyframes #{$animationName} {
    @content;
  }
  @-moz-keyframes #{$animationName} {
    @content;
  }
  @-ms-keyframes #{$animationName} {
    @content;
  }
  @-o-keyframes #{$animationName} {
    @content;
  }
  @keyframes #{$animationName} {
    @content;
  }
}
// Colors
$greenSeaweed: #0077b6;
$blueQueen: #00b4d8;
$redFire: #ffb30e;

// Fonts
$fontAsap: "Asap", sans-serif;


.signUp{
  overflow: hidden;
  background-color: white;
  padding: 50px 30px 42px 30px;
  border-radius: 10px;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 500px;

  @include transform(translate(-50%, -50%));
  @include transition(transform 300ms, box-shadow 300ms);

  box-shadow: 5px 10px 10px rgba($greenSeaweed, 0.2);

  &::before,
  &::after{
    content: "";
    position: absolute;
    width: 800px;
    height: 700px;
    border-top-left-radius: 40%;
    border-top-right-radius: 45%;
    border-bottom-left-radius: 35%;
    border-bottom-right-radius: 40%;
    z-index: -1;
  }

  &::before {
    left: 33%;
    bottom: -60%;
    background-color: rgba($blueQueen, 0.15);
    @include animation(waves, 6s, infinite, linear);
  }


  &::after {
    left: 28%;
    bottom: -55%;
    background-color: rgba($greenSeaweed, 0.2);
    @include animation(waves, 7s, infinite, linear);
  }

  > .signUpInput{
    font-family: $fontAsap;
    display: block;
    border-radius: 5px;
    font-size: 16px;
    background: white;
    width: 100%;
    border: 0;

    padding: 10px 10px;
    margin: 15px -10px;
  }

  > .registerButton{
    font-family: $fontAsap;
    cursor: pointer;
    color: white;
    font-size: 16px;
    text-transform: uppercase;
    width: 85px;
    border: 0;
    padding: 10px 0;
    margin-top: 13px;
    margin-left: -5px;
    border-radius: 5px;
    background-color: $redFire;
    @include transition(background-color, 300ms);
    

    &:hover{
      background-color: darken($redFire, 5%);
    }
  }
}

@include keyframes(waves){
  from{
    @include transform(rotate(0));
  }to{
    @include transform(rotate(360deg));
  }
}

</style>