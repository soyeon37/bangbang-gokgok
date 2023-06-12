<template>
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

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import { VueEditor } from "vue2-editor";
import { mapState } from "vuex"; // vuex에서 얻어옴
const userStore = "userStore"; // export 바깥에서 선언해도 되나봄

export default {
  name: "BoardInputItem",
  components: {
    VueEditor,
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
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]), // computed 안에서 userinfo 받아오면 됨
  },
  props: {
    type: { type: String },
  },
  async created() {
    if (this.type === "modify") {
      const { data } = await http.get(`/board/${this.$route.params.articleno}`);
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
        const result = await http.post("/board/uploadImage", formData);
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

      const formData = new FormData();
      formData.append("title", this.article.title);
      formData.append("content", this.article.content);
      formData.append("category", 1);
      formData.append("userId", this.userInfo.userId);
      // const fileInput = this.$refs.fileInput;

      // for (let i = 0; i < fileInput.files.length; i++) {
      //   formData.append("upfile", fileInput.files[i]);
      // }
      for (let i = 0; i < this.files.length; i++) {
        formData.append("upfile", this.files[i]);
      }

      try {
        const { data } = await http.post(`board/write`, formData, {
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
      const { data } = await http.put(`/board/modify`, {
        articleId: this.article.articleno,
        userId: this.article.userid,
        title: this.article.title,
        content: this.article.content,
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
      this.$router.push({ name: "boardlist" });
    },
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
