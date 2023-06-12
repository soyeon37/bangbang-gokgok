<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글보기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          v-if="this.userInfo != null && userInfo.userId == article.userId"
          >글수정</b-button
        >
        <b-button
          variant="outline-danger"
          size="sm"
          @click="deleteArticle"
          v-if="this.userInfo != null && userInfo.userId == article.userId"
          >글삭제</b-button
        >
        <b-button variant="outline-info" v-if="liked" @click="click_like">좋아요 취소</b-button>
        <b-button variant="outline-info" v-else @click="click_like">좋아요</b-button>
      </b-col>
    </b-row>

    <!-- <b-row class="mb-1">
      <b-col>
        <template v-slot:header>
          <h3>{{ article.articleId }}. {{ article.title }} [{{ article.hit }}]</h3>
          <div>
            <h6>
              <b-button id="popover-target" variant="link" class="text-left p-0 m-0">
                {{ article.userId }}
              </b-button>
            </h6>
          </div>
          <div>{{ article.registerTime }}</div>
        </template>
        <div v-html="message"></div>
      </b-col>
    </b-row> -->

    <b-row class="mb-1">
      <b-col>
        <div class="mb-2" style="border: 1px solid #343a40; border-radius: 0.25rem">
          <div class="card-header">
            <h3>{{ article.articleId }}. {{ article.title }} [{{ article.hit }}]</h3>
            <div>
              <h6>
                <b-button id="popover-target" variant="link" class="text-left p-0 m-0">
                  {{ article.userId }}
                </b-button>
              </h6>
            </div>
            <div>{{ article.registerTime }}</div>
          </div>
          <div class="card-body text-left">
            <div v-html="message"></div>
          </div>
        </div>
      </b-col>
    </b-row>

    <b-popover
      :show.sync="show"
      v-if="article.userId"
      target="popover-target"
      triggers="click"
      placement="topright"
      :html="true"
    >
      <template v-slot:default>
        <div style="text-align: center">
          <div class="mb-2">
            <b-icon icon="person-circle" font-scale="5"></b-icon>
            <div>유저 아이디 : {{ article.userId }}</div>
          </div>
          <div>
            <div>
              <b-button variant="info" class="mb-2" @click="gotoDetail">상세보기</b-button>
            </div>
            <div>
              <b-button variant="primary" @click="addFriend">친구추가</b-button>
            </div>
          </div>
        </div>
      </template>
    </b-popover>
    <!-- <div class="mt-3" v-if="article.fileInfos && article.fileInfos.length > 0">
      <ul>
        <li v-for="file in article.fileInfos" :key="file.saveFile">
          <a @click="downloadFile(file.saveFolder, file.originalFile, file.saveFile)" href="#"
            >{{ file.originalFile }} [다운로드]</a
          >
        </li>
      </ul>
    </div> -->

    <b-card class="mt-3 my-card" v-if="article.fileInfos && article.fileInfos.length > 0">
      <b-list-group>
        <b-list-group-item
          class="my-list-group-item"
          v-for="file in article.fileInfos"
          :key="file.saveFile"
        >
          <b-button
            @click="downloadFile(file.saveFolder, file.originalFile, file.saveFile)"
            href="#"
            class="my-button"
          >
            {{ file.originalFile }} [다운로드]
          </b-button>
        </b-list-group-item>
      </b-list-group>
    </b-card>
    <!-- comments table에서 category = 1이고 comments.article_id == boards.article_id인 모든 댓글을 불러온다 -->
    <!-- parent, depth, group_no를 보면서 적절한 모양으로 출력한다 -->
    <!-- user_id가 현재 로그인한 user_id와 같은 경우 수정 및 삭제 버튼을 출력한다 -->
    <!-- 아니 왜 commentController가 없어 인생 -->
    댓글 조회 <br />
    <!-- 댓글 조회 -->
    <b-row class="mt-3" v-if="comments.length > 0">
      <b-col>
        <b-list-group>
          <b-list-group-item
            v-for="(comment, index) in comments"
            :key="index"
            :style="{
              'border-left': 'none',
              'border-right': 'none',
            }"
          >
            <div
              :style="{
                'margin-left': `${comment.depth * 120}px`,
                'text-align': 'left',
              }"
              @click="toggleReplyForm(index)"
            >
              <h3 v-if="comment.depth > 0">⤷</h3>
              <h5>작성자: {{ comment.userId }}</h5>
              <small>작성 날짜 : {{ comment.registerTime }}<br /></small>
              <p>내용: {{ comment.content }}</p>
            </div>
            <div v-if="replyIndex === index">
              <textarea v-model="replyContent" placeholder="Write your reply..."></textarea>
              <b-button @click="submitRecomment(comment.commentId, comment.depth, comment.groupNo)"
                >Submit reply</b-button
              >
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-form @submit.prevent="submitComment">
          <b-form-group id="commentInputGroup" label="댓글 내용" label-for="commentInput">
            <b-form-input
              id="commentInput"
              v-model="commentText"
              required
              placeholder="댓글을 입력하세요."
            ></b-form-input>
          </b-form-group>
          <b-button type="submit" variant="primary">댓글 추가</b-button>
        </b-form>
      </b-col>
    </b-row>
    <!-- 모달 -->
    <b-modal
      id="modal-1"
      :title="`${article.userId}님의 정보`"
      size="xl"
      centered
      :ok-only="true"
      hide-footer
    >
      <b-row>
        <b-col></b-col>
        <b-col cols="8">
          <b-jumbotron>
            <!-- <template #header>My Page</template> -->

            <template #lead>상세 정보 </template>

            <hr class="my-4" />

            <b-container class="mt-4">
              <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="2" align-self="end">아이디</b-col
                ><b-col cols="4" align-self="start">{{ article.userId }}</b-col>
                <b-col cols="2"></b-col>
              </b-row>
              <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="2" align-self="end">이름</b-col
                ><b-col cols="4" align-self="start">{{ otherUserName }}</b-col>
                <b-col cols="2"></b-col>
              </b-row>
              <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="2" align-self="end">여행타입</b-col
                ><b-col cols="4" align-self="start">{{ otherType }}</b-col>
                <b-col cols="2"></b-col>
              </b-row>
            </b-container>
            <hr class="my-4" />
          </b-jumbotron>
        </b-col>
        <b-col></b-col>
      </b-row>
    </b-modal>
  </b-container>
</template>

<script>
import http from "@/api/http";
// import { getArticle } from "@/api/board";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "BoardView",
  components: {},
  data() {
    return {
      userId: "hello",
      article: {},
      comments: [],
      commentText: "",
      // 사용자 프로필 이미지 URL. 실제 URL로 바꾸세요.
      userProfileImage: "https://example.com/path/to/image.jpg",
      show: false,
      replyContent: "", // 대댓글 작성 내용
      replyIndex: null, // 대댓글 작성 폼이 나타날 댓글의 인덱스
      liked: false, // 현재 사용자가 좋아요를 눌렀는지 안눌렀는지에 대한 여부를 나타냄
      otherUserName: "",
      otherType: 0,
    };
  },
  // 자바에서 \n로 넘어오는거 html에 띄울 때 <br>로 변경되도록 comptued 속성 사용
  computed: {
    ...mapState(userStore, ["userInfo"]),
    message() {
      if (!this.article.content) {
        return "";
      }
      const contentWithBr = this.article.content.split("\n").join("<br>");
      return this.decodeImage(contentWithBr);
    },
  },
  async created() {
    try {
      const { data } = await http.get(`/board/${this.$route.params.articleno}`);
      this.article = data.article;
    } catch (error) {
      console.log(error);
    }

    // 임시 comments 관리
    this.comments = [
      { author: "John", text: "추천넣고갑니다", timestamp: "2023-05-22" },
      { author: "Jane", text: "비추천넣고갑니다", timestamp: "2023-05-23" },
      { author: "Tom", text: "퍼가요", timestamp: "2023-05-24" },
    ];

    // 파라미터 싹 다 들고 다녀야하는거 잠시 넘김

    // get 요청으로 rest api 서버에서 모든 comment를 받아옴
    this.loadComments();
    this.loadLikes();
  },

  mounted() {
    // 아마 필요 없음 추후 제거 예정
    // popover가 렌더링 되는게 article.userId보다 빨라서 popover에서 유저 이름이 안뜨는 문제
    // 디버깅용 코드
    this.$nextTick(() => {
      this.userId = this.article.userId;
    });
  },

  methods: {
    decodeImage(encodedString) {
      let result = encodedString;
      let imgTagStartIndex = result.indexOf("<img");

      while (imgTagStartIndex >= 0) {
        const imgTagEndIndex = result.indexOf(">", imgTagStartIndex);
        const imgTag = result.slice(imgTagStartIndex, imgTagEndIndex + 1);
        const newImgTag = imgTag.replace("<img", '<img style="max-width:100%; height:auto;"');
        result = result.replace(imgTag, newImgTag);
        imgTagStartIndex = result.indexOf("<img", imgTagEndIndex + 1);
      }

      return result;
    },

    async click_like() {
      if (this.userInfo == null) {
        alert("로그인을 해주세요");
        return false;
      }

      if (this.liked) {
        const { data } = await http.delete(
          `/board/likes/${this.$route.params.articleno}?userId=${this.userInfo.userId}`
        );

        if (data == "success") {
          alert("좋아요 취소 완료");
        }

        this.liked = !this.liked;
      } else {
        const { data } = await http.post(`/board/likes`, {
          articleNo: this.$route.params.articleno,
          userId: this.userInfo.userId,
        });

        if (data == "success") {
          alert("좋아요 완료");
        }

        this.liked = !this.liked;
      }
    },
    async gotoDetail() {
      // TODO: {{article.userid}}를 참조해 해당 유저의 상세 페이지로 가는 함수 생성
      await this.getUserInfo();
      this.$bvModal.show("modal-1");
      // this.show = !this.show;
    },

    async getUserInfo() {
      const { data } = await http.get(`/user/${this.article.userId}`);
      if (data.message === "success") {
        this.otherUserName = data.userinfo.userName;
        this.otherType = data.userinfo.type;
      } else {
        console.log("유저 로그인 정보 받아오기 실패");
      }
    },

    async addFriend() {
      // TODO: {{article.userid}}를 참조해 해당 유저에게 친구추가를 하는 함수 생성
      if (this.userInfo == null) {
        alert("로그인 후 친구 추가가 가능합니다");
        return false;
      }

      if (this.userInfo.userId === this.article.userId) {
        alert("자신에게 친구추가를 보낼 수 없습니다");
        return false;
      }

      const { data } = await http.post(`/friend/add`, {
        followerId: this.userInfo.userId,
        userId: this.article.userId,
      });

      if (data === "success") {
        alert("친구 추가가 완료되었습니다");
      } else {
        alert("친구 추가 도중 오류가 발생했습니다");
      }
    },

    // 대댓글 작성 폼이 표시될 인덱스를 표시힌다, 누른 댓글을 또 누르면 나와있던 대댓글 작성 폼이 사라진다
    toggleReplyForm(index) {
      if (this.replyIndex === index) {
        this.replyIndex = null;
        this.replyContent = "";
      } else {
        this.replyIndex = index;
        this.replyContent = "";
      }
    },

    // 대댓글을 서버로 전송하고, 대댓글 작성 폼을 제거한다
    async submitRecomment(comment_id, parent_depth, parent_group_no) {
      // Submit this.replyContent to the server...
      if (this.userInfo == null) {
        alert("로그인 후 댓글을 작성해주세요!");
        return false;
      }

      try {
        const { data } = await http.post(`comment/write`, {
          content: this.replyContent,
          userId: this.userInfo.userId, // 추후 JWT 토큰 보고 userId 갱신
          articleId: this.$route.params.articleno,
          parent: comment_id,
          depth: parent_depth + 1,
          groupNo: parent_group_no,
          // group_no를 어떻게 알지?
          // 대댓글의 경우에는 내 부모의 group_no를 들고오면 된다는게 자명
          // 댓글에서 group_no를 어떻게 할당할까?
          // service단에서 insert 이후 depth가 0이면 group_no update하는 식으로 처리했음 여기서 groupno 안줘도 됨
          category: 1, // 핫플 게시판의 카테고리는 1
        });

        let msg = "등록 처리시 문제가 발생했습니다";
        if (data === "success") {
          msg = "댓글 등록이 완료되었습니다.";
        }
        alert(msg);
        this.loadComments();
      } catch (error) {
        console.log(error);
      }

      console.log(`입력받은 comment.commentId : ${comment_id}`);
      this.replyContent = "";
      this.replyIndex = null;
      this.loadComments();
    },

    async submitComment() {
      // TODO: 서버에 댓글 추가 요청을 보내는 코드를 작성해주세요.
      // 예를 들어, 아래와 같은 코드를 사용할 수 있습니다.
      // await this.$axios.post('/api/comments', { text: this.commentText });
      // this.commentText = '';
      // this.loadComments();
      if (this.userInfo == null) {
        alert("로그인 후 댓글을 작성해주세요!");
        return false;
      }

      try {
        const { data } = await http.post(`comment/write`, {
          content: this.commentText,
          userId: this.userInfo.userId, // 추후 JWT 토큰 보고 userId 갱신
          articleId: this.$route.params.articleno,
          parent: 0, // 댓글은 부모를 0으로 설정
          depth: 0, // 댓글은 당연히 depth가 0
          // group_no를 어떻게 알지?
          // 대댓글의 경우에는 내 부모의 group_no를 들고오면 된다는게 자명
          // 댓글에서 group_no를 어떻게 할당할까?
          // service단에서 insert 이후 depth가 0이면 group_no update하는 식으로 처리했음 여기서 groupno 안줘도 됨
          category: 1, // 핫플 게시판의 카테고리는 1
        });

        let msg = "등록 처리시 문제가 발생했습니다";
        if (data === "success") {
          msg = "댓글 등록이 완료되었습니다.";
        }
        alert(msg);
        this.loadComments();
        this.commentText = "";
      } catch (error) {
        console.log(error);
      }
    },

    async loadLikes() {
      try {
        const { data } = await http.get(
          `/board/likes/${this.$route.params.articleno}?userId=${this.userInfo.userId}`
        );
        this.liked = data;
      } catch (error) {
        console.log(error);
      }
    },

    async loadComments() {
      // TODO: 서버에서 댓글 목록을 불러오는 코드를 작성해주세요.
      // 예를 들어, 아래와 같은 코드를 사용할 수 있습니다.
      // const response = await this.$axios.get('/api/comments');
      // this.comments = response.data;
      try {
        // 핫플 게시판의 category는 1이므로, comment/${articleid}/${category} 에서 category는 항상 1로 고정해서 받아온다
        const { data } = await http.get(`/comment/${this.$route.params.articleno}/1`);
        this.comments = data;
        console.log(data);
      } catch (error) {
        console.log(error);
      }
    },

    moveModifyArticle() {
      this.$router.replace({
        name: "boardmodify",
        params: { articleno: this.article.articleId },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "boarddelete",
          params: { articleno: this.article.articleId },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
    async downloadFile(saveFolder, originalFile, saveFile) {
      const { data } = await http.get(`/file/download/${saveFolder}/${originalFile}/${saveFile}`, {
        responseType: "blob",
      });
      const url = window.URL.createObjectURL(new Blob([data]));
      // const url = `${process.env.VUE_APP_BACKEND_URL}/file/download/${saveFolder}/${originalFile}/${saveFile}`;
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", originalFile); //or any other extension
      document.body.appendChild(link);
      link.click();
    },
  },
};
</script>

<style scoped>
.my-card {
  background-color: #f5f5f5;
  border-radius: 10px;
}

.my-list-group-item {
  padding: 10px;
}

.my-button {
  background-color: #007bff;
  color: #fff;
  border-radius: 5px;
}
</style>
