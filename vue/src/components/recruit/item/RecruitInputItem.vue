<template>
  <div>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="true"
            v-model="userInfo.userId"
            type="text"
            required
            placeholder="작성자 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="title-group"
          label="제목:"
          label-for="title"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="title"
            v-model="article.title"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <!-- <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea> -->
          <!-- <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
            @input="handleContentInput"
            @paste="handleContentPaste"
          ></b-form-textarea> -->
          <!-- <div class="editor-image-preview">
            <img
              v-for="(image, index) in editorImages"
              :key="index"
              :src="image.url"
              :alt="'Image ' + (index + 1)"
            />
          </div> -->
          <vue-editor id="content" @imageAdded="handleImageAdded" v-model="article.content">
          </vue-editor>
        </b-form-group>

        <div class="fileUpLoad">
          <label for="file">파일:</label>
          <input type="file" id="file" ref="fileInput" @change="onFileChange" multiple />

          <ul class="fileList">
            <li v-for="(file, index) in files" :key="index">
              {{ file.name }}
              <button @click="removeFile(index)">X</button>
            </li>
          </ul>
        </div>
        <b-button type="button" @click="getPlanList">여행계획등록</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
  
  <!-- 여행 계획 Modal START -->
  <b-modal id="modal-1" title="여행 계획 이름" size="xl" centered :ok-only="true" hide-footer>
      <my-plan-detail-vue
        :modalPlanId="planId"
        :isAdd=false
      ></my-plan-detail-vue>
      이 여행을 삭제하려면 아래 버튼을 눌러주세요<br />
      <b-button class="btn btn-danger" @click="deletePlan()">삭제</b-button>
    </b-modal>

  <!-- 여행 계획 Modal END -->
  
  
  <b-sidebar id="sidebar-1" title="Sidebar" shadow v-model="showSidebar">
      <p>
        등록할 여행계획을 선택해주세요.
      </p>
      <div class="px-3 py-2" v-for="(plan, index) in planlist" :key="index">
        <b-card :title="plan.planName" :img-src="plan.image" img-alt="Image" img-top>
          <b-card-text>
            {{ plan.content }}
          </b-card-text>
          <b-button  v-if="plan.isVisible" @click="addPlan(plan.planId, index)">계획 추가</b-button>
          <b-button  v-else @click="cancelPlan(plan.planId, index)">계획 추가 취소</b-button>
          <b-button @click="openModal(plan)">계획 보기</b-button>
        </b-card>
        <!-- <b-button @click="openModal()">일정 목록 1</b-button> <br /> -->
        <!-- <b-button @click="openModal()">일정 목록 2</b-button> -->
      </div>
    </b-sidebar>
</div>
</template>

<script>
import http from "@/api/http";
import { VueEditor } from "vue2-editor";
import { mapState } from "vuex"; // vuex에서 얻어옴
const userStore = "userStore"; // export 바깥에서 선언해도 되나봄
import MyPlanDetailVue from "@/components/plan/MyPlanDetail.vue";
export default {
  name: "RecruitInputItem",
  components: {
    VueEditor,
    MyPlanDetailVue,
  },
  props: {
    type: { type: String },
  },
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        title: "",
        content: "",
        editorImages: [],
      },
      isUserid: false,
      files: [],
      showSidebar: false,
      planlist: [],
      planId: null,
      planIdList: [],
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]), // computed 안에서 userinfo 받아오면 됨
  },
  async created() {
    if (this.type === "modify") {
      const { data } = await http.get(`/recruit/${this.$route.params.articleno}`);
      this.article.articleno = data.article.articleId;
      this.article.userid = data.article.userId;
      this.article.title = data.article.title;
      this.article.content = data.article.content;
      this.isUserid = true;
    }
  },
  methods: {
    handleImageAdded: async function (file, Editor, cursorLocation, resetUploader) {
      // An example of using FormData
      // NOTE: Your key could be different such as:
      // formData.append('file', file)

      console.log("이미지가 추가되었습니다");

      var formData = new FormData();
      formData.append("image", file);

      try {
        const result = await http.post("/recruit/uploadImage", formData);
        const url = result.data.url;
        Editor.insertEmbed(cursorLocation, "image", url);
        resetUploader();
      } catch (e) {
        console.log(e);
      }
    },

    // handleImageAdded: function (file, Editor, cursorLocation, resetUploader) {
    //   // An example of using FormData
    //   // NOTE: Your key could be different such as:
    //   // formData.append('file', file)

    //   console.log("이미지가 추가되었습니다");

    //   var formData = new FormData();
    //   formData.append("image", file);

    //   http
    //     .post("/board/uploadImage", formData)
    //     .then((result) => {
    //       const url = result.data.url; // Get url from response
    //       Editor.insertEmbed(cursorLocation, "image", url);
    //       resetUploader();
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
    // handleContentPaste(event) {
    //   const items = (event.clipboardData || event.originalEvent.clipboardData).items;
    //   const files = [];
    //   for (let i = 0; i < items.length; i++) {
    //     const item = items[i];
    //     if (item.kind === "file" && item.type.includes("image")) {
    //       const file = item.getAsFile();
    //       files.push(file);
    //     }
    //   }
    //   this.uploadImages(files);
    // },

    // uploadImages(files) {
    //   const formData = new FormData();
    //   for (let i = 0; i < files.length; i++) {
    //     formData.append("upfile", files[i]);
    //   }

    //   // 이미지 업로드 API 요청
    //   // ...

    //   // 업로드된 이미지의 URL을 삽입
    //   // this.insertUploadedImages([...]);
    // },

    // insertUploadedImages(imageUrls) {
    //   const cursorPosition = this.$refs.contentInput.selectionStart;
    //   const content = this.article.content;
    //   const newContent =
    //     content.slice(0, cursorPosition) + imageUrls.join("") + content.slice(cursorPosition);
    //   this.article.content = newContent;
    // },

    removeFile(index) {
      this.files.splice(index, 1); // remove the file at the index
    },

    onFileChange(e) {
      const files = e.target.files || e.dataTransfer.files;
      if (!files.length) return;
      for (let i = 0; i < files.length; i++) {
        this.files.push(files[i]); // 선택한 파일을 files 배열에 추가합니다.
      }
    },

    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      // !this.article.userid &&
      //   ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      // err &&
      !this.article.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false));
      // ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.title = "";
      this.article.content = "";
      this.files = [];
      this.moveList();
    },
    async registArticle() {
      if (this.userInfo == null) {
        alert("로그인 후 댓글을 작성해주세요!");
        return false;
      }
      console.log("usertype", this.userInfo.type);
      const formData = new FormData();
      formData.append("title", this.article.title);
      formData.append("content", this.article.content);
      formData.append("category", 3);
      formData.append("type", this.userInfo.type);
      formData.append("userId", this.userInfo.userId);
      formData.append("planIds", this.planIdList);
      console.log(this.planIdList);

      // const fileInput = this.$refs.fileInput;

      // for (let i = 0; i < fileInput.files.length; i++) {
      //   formData.append("upfile", fileInput.files[i]);
      // }
      for (let i = 0; i < this.files.length; i++) {
        formData.append("upfile", this.files[i]);
      }

      try {
        const { data } = await http.post(`recruit/write`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        if (data === "success") {
          alert("등록이 완료되었습니다.");
          this.moveList();
        } else {
          alert("등록 처리시 문제가 발생했습니다.");
        }
      } catch (error) {
        alert("에러발생");
      }

      // const { data } = await http.post(`board/write`, {
      //   // noticeId: null,
      //   // userId: null,
      //   title: this.article.subject,
      //   content: this.article.content,
      //   // hit: null,
      //   // registerTime: null,
      //   category: 1,
      //   // likes: null,
      // });
      // let msg = "등록 처리시 문제가 발생했습니다.";
      // if (data === "success") {
      //   msg = "등록이 완료되었습니다.";
      // }
      // alert(msg);
      // this.moveList();
    },
    async modifyArticle() {
      const { data } = await http.put(`/recruit/modify`, {
        articleId: this.article.articleno,
        userId: this.article.userid,
        title: this.article.title,
        content: this.article.content,
        type: this.userInfo.type,
        // hit: null,
        // registerTime: null,
        // 카테고리 수정해야 함
        category: 1,
      });

      let msg = "수정 처리시 문제가 발생했습니다.";
      if (data === "success") {
        msg = "수정이 완료되었습니다.";
      }
      alert(msg);
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "recruitlist" });
    },

    async getPlanList() {
      const { data } = await http.get(`/plan/planlist/${this.userInfo.userId}?key=&word=&pgno=1`);
      let msg = "여행계획 호출 처리시 문제가 발생했습니다.";
      if (data.message === "success") {
        this.planlist = data.plans;
        console.log(this.planlist)
        this.planlist = this.planlist.map(plan => ({ ...plan, isVisible: true }));
        console.log(this.planlist);

        // 이미 담은 여행인지 비교
        for (let i = 0; i < this.planlist.length; i++) {
          if (this.planIdList.includes(this.planlist[i].planId)) {
            this.planlist[i].isVisible = false;
          }
          
        }





        this.showSidebar = true;
        
      } else {
        alert(msg);
      }
      
      // var size = this.planlist.length;
      // this.buttons = Array(size).fill({ isVisible: true });
      // console.log(this.buttons)


    },
    openModal(plan) {
      this.planId = plan.planId;
      console.log(this.planId)
      this.$bvModal.show("modal-1");
    },
    addPlan(planId, index) {
      this.planIdList.push(planId);
      console.log(this.planIdList);
      this.planlist[index].isVisible = !this.planlist[index].isVisible;
      console.log(this.planlist)
    },
    cancelPlan(planId, index) {
      var idx = this.planIdList.indexOf(planId);
      if (idx !== -1) {
        this.planIdList.splice(idx, 1);
        this.planlist[index].isVisible = !this.planlist[index].isVisible;
      }
      console.log(this.planlist)
    }

  },
};
</script>

<style scoped>
/* .editor-image-preview {
  margin-top: 10px;
}

.editor-image-preview img {
  max-width: 100%;
  max-height: 200px;
  margin-right: 10px;
} */
</style>
