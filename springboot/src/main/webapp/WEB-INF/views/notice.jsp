<!DOCTYPE html>
<!-- jsp랑 섞어쓰느라 jsp를 따로 추가해줌 -->
<!-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<!-- <c:set var="root" value="${pageContext.request.contextPath}" /> -->
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <link href="/assets/css/app.css" rel="stylesheet" />
    <title>SSAFY</title>
    <style>
      #user-info div {
        width: 350px;
      }
    </style>
  </head>
  <body>
    <!--  header.jsp -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
      <div class="container">
        <div class="d-flex justify-content-between" style="width: 100%">
          <a class="navbar-brand text-primary fw-bold" href="${root}">Enjoy Trip</a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${root}trip/list"
                  >나의여행경로</a
                >
              </li>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${root}hotplace.html">핫플자랑하기</a>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  aria-current="page"
                  href="${root}board/list&pgno=1&key=&word="
                  >여행정보공유</a
                >
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  aria-current="page"
                  href="${root}notice/list&pgno=1&key=&word="
                  >공지사항</a
                >
              </li>
            </ul>
            <div style="flex: auto"></div>
            <ul class="navbar-nav mb-2 me-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${root}user/logout"
                  >로그아웃</a
                >
              </li>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${root}user/mypage"
                  >마이페이지</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>

    <div style="height: 80px"></div>
    <!-- list.jsp -->
    <!--게시판 목록-->
    <div class="container" id="list" style="display: block">
      <div style="display: flex; justify-content: space-between">
        <!--글쓰는 창으로 가는 버튼-->
        <button type="button" id="btn-write" class="btn btn-secondary" onclick="writeArticle()">글쓰기</button>
        <div>
          <form class="d-flex" id="form-search" action="">
            <input type="hidden" name="go" value="list" />
            <input type="hidden" name="pgno" value="1" />
            <select
              name="search-option"
              class="form-select form-select-sm ms-5 me-1 w-50"
              aria-label="Default select example"
            >
              <option selected>검색옵션</option>
              <option value="2">지난글</option>
              <option value="3">조회순</option>
            </select>
            <select
              name="key"
              id="key"
              class="form-select form-select-sm ms-5 me-1 w-50"
              aria-label="검색조건"
            >
              <option selected>검색조건</option>
              <option value="article_no" id="article_no">글번호</option>
              <option value="subject">제목</option>
              <option value="user_id">작성자</option>
            </select>
            <div class="input-group input-group-sm">
              <input
                type="text"
                name="word"
                id="word"
                class="form-control"
                placeholder="검색어..."
              />
              <button id="btn-search" class="btn btn-secondary" type="button">검색</button>
            </div>
          </form>
        </div>
      </div>
      <hr style="height: 2px; background-color: black" />
      <table class="table table-striped">
        <thead>
          <tr>
            <th scope="col"><b>번호</b></th>
            <th scope="col"><b>제목</b></th>
            <th scope="col"><b>작성자</b></th>
            <th scope="col"><b>조회수</b></th>
            <th scope="col"><b>날짜</b></th>
          </tr>
        </thead>
        <tbody id="articlelist"></tbody>
        <!-- <tbody>
				<c:forEach var="article" items="${articles}">
					<tr>
						<th scope="row">${article.articleNo}</th>
						<td><b><a href="#" class="article-title link-dark"
								data-no="${article.articleNo }" style="text-decoration: none">
									${article.subject }</a></b></td>
						<td><b>${article.userId }</b></td>
						<td><b>${article.hit }</b></td>
						<td><b>${article.uploadTime }</b></td>
					</tr>
				</c:forEach>
			</tbody> -->
      </table>

      <div style="height: 20px"></div>
      <!-- navigation -->
      <div id="navigation" class="row"></div>
      <!-- <div class="row">${navigation.navigator}</div> -->
    </div>

    <!-- modify.jsp  -->
    <div class="container" id="modify" style="display: none">
      <div style="height: 100px"></div>
      <h1>공지사항 쓰기</h1>
      <hr />
		<div id="articleModify"></div>
<!--       <form method="POST" action="${root }notice?go=modify&articleno=${article.articleNo}"> -->
		<!-- modifyArticle -->
      <!-- <form method="POST" id="articleModify" >
        <input type="hidden" value="${article.articleNo }" />
        <h1 class="modal-title fs-5" id="loginModal">제목 :</h1>
        <input
          type="text"
          name="subject"
          class="form-control mt-4 mb-2"
          value="${article.subject }"
          required
        />
        <div class="form-group">
          <h1 class="modal-title fs-5" id="loginModal" style="margin-top: 20px">내용 :</h1>
          <textarea class="form-control" rows="10" name="content" required style="margin-top: 20px">
${article.content }</textarea
          >
        </div>
        <button type="submit" class="btn btn-secondary mb-3" style="margin-top: 20px">
          글수정하기
        </button>
        <button
          type="button"
          class="btn btn-secondary mb-3"
          style="margin-top: 20px"
          onclick="listArticle()"
        >
          목록으로
        </button>
      </form> -->
    </div>

    <!-- view.jsp -->
      <!-- <script>
        alert("글이 삭제되었거나 잘못된 접근입니다.");
        loacation.href = "${root}/notice?go=list";
      </script> -->
    <div class="container" id="view" style="display: none">
      <div style="height: 50px"></div>
      <div class="row justify-content-center" >
        <div class="col-lg-8 col-md-10 col-sm-12">
          
          <!-- makeView -->
          <div id="articleView"></div>
          <!-- <input type="hidden" class="hidden" value="${article.articleNo }" />
          <div class="row my-2">
            <h2 class="text-secondary px-5">${article.articleNo}. ${article.subject}</h2>
          </div>
           <div class="row">
            <div class="col-md-8">
              <div class="clearfix align-content-center">
                <img
                  class="avatar me-2 float-md-start bg-light p-2"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                />
                <p>
                  <span class="fw-bold">${article.userId}</span> <br />
                  <span class="text-secondary fw-light">
                    ${article.uploadTime} 조회 : ${article.hit}
                  </span>
                </p>
              </div>
            </div>
            <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
            <div class="divider mb-3"></div>
            <div class="text-secondary">${article.content}</div>
            <div class="divider mt-3 mb-3"></div>
            <div class="d-flex justify-content-end">
              <button type="button" id="btn-list" class="btn btn-outline-primary mb-3">
                글목록
              </button>
              <button type="submit" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">
                글수정
              </button>
              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">
                글삭제
              </button>
            </div>
          </div> -->
        </div>
      </div>
    </div>

    <!-- write.jsp -->
    <div class="container" id="write" style="display: none">
      <div style="height: 100px"></div>
      <h1>공지사항 쓰기</h1>
      <hr />
      <form method="POST">
        <h1 class="modal-title fs-5" id="loginModal">제목 :</h1>
        <input
          type="text"
          name="subject"
          id="subject"
          class="form-control mt-4 mb-2"
          placeholder="제목을 입력해주세요."
          required
        />
        <div class="form-group">
          <h1 class="modal-title fs-5" id="loginModal" style="margin-top: 20px">내용 :</h1>
          <textarea
            class="form-control"
            rows="10"
            name="content"
            id="content"
            placeholder="내용을 입력해주세요"
            required
            style="margin-top: 20px"
          ></textarea>
        </div>
        <!--일단 목록으로 돌아가게만-->
        <button type="submit" id="btn-regist" class="btn btn-secondary mb-3" style="margin-top: 20px">
          글쓰기
        </button>
        <!--목록으로 돌아가는 버튼-->
        <button
          type="button"
          class="btn btn-secondary mb-3"
          style="margin-top: 20px"
          onclick="location.href='${root}notice?go=list&pgno=1&key=&word='"
        >
          목록으로
        </button>
      </form>
    </div>
  </body>
  <form id="form-param" method="get" action="">
      <input type="hidden" id="pgno" name="pgno" value="${pgno}" />
      <input type="hidden" name="key" value="${key}" />
      <input type="hidden" name="word" value="${word}" />
    </form>
  <script>
    let root = "/";

    // 공지사항 글 목록.
    fetch(`${root}/notice/list`)
      .then((response) => response.json())
      .then((data) => makeList(data));
 
    // 공지사항 글 보기
    let titles = document.querySelectorAll(".article-title");
    titles.forEach(function (title) {
      title.addEventListener("click", function () {
        console.log(this.getAttribute("data-no"));
        let articleno = this.getAttribute("data-no");
        let config = {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(articleno),
        };
        fetch(`${root}/notice/view`, config)
          .then((response) => response.json())
          .then((data) => makeView(data));
      });
      viewArticle();
    });
	
    // 버튼 검색
    document.querySelector("#btn-search").addEventListener("click", function () {
    	const search = new FormData(form-search);
    	let config = {
    	          method: "GET",
    	          headers: {
    	            "Content-Type": "application/x-www-form-urlencoded",
    	          },
    	          body: search
    	        }; 
    	 fetch(`${root}/notice/list`, config)
         .then((response) => response.json())
         .then((data) => makeList(data));
      });
    
    
    
    // 작성된 글 등록
    document.querySelector("#btn-regist").addEventListener("click", function () {
      let articleinfo = {
        subject: document.querySelector("#subject").value,
        content: document.querySelector("#content").value,
      };
      let config = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(articleinfo),
      };
      fetch(`${root}/notice/write`, config)
        .then((response) => response.json())
        .then((data) => makeView(data));
      
      viewArticle();
    });
    
    // 글 수정 업데이트
    function registModify(el){
    	let id = el.parentNode.parentNode.getAttribute("data-no");
        let config = {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
        };
        fetch(`${root}/notice/aritcle/${id}`, config)
          .then((response) => response.json())
          .then((data) => makeView(data));
        
        viewArticle();
    }
    
 // 글 삭제
    function userDelete(el) {
      if (confirm("정말 삭제하시겠습니까?")) {
        let id = el.parentNode.parentNode.getAttribute("data-no");
        let config = {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        };
        fetch(`${root}/notice/aritcle/${id}`, config)
          .then((response) => response.json())
          .then((data) => console.log(data));
        
        listArticle();
      }
    }
 
    // 공지사항 리스트 페이지로 이동
    function listArticle() {
    	fetch(`${root}/notice/list`)
         .then((response) => response.json())
         .then((data) => makeList(data));
      document.querySelector(`#list`).setAttribute("style", "display: ;");
      document.querySelector(`#wrtie`).setAttribute("style", "display: none;");
      document.querySelector(`#view`).setAttribute("style", "display: none;");
      document.querySelector(`#modify`).setAttribute("style", "display: none;");
    }
    
 // 글 view 페이지로 이동
    function viewArticle() {
      document.querySelector(`#list`).setAttribute("style", "display: none;");
      document.querySelector(`#wrtie`).setAttribute("style", "display: none;");
      document.querySelector(`#view`).setAttribute("style", "display: ;");
      document.querySelector(`#modify`).setAttribute("style", "display: none;");
    }
    
 // 글 수정 페이지로 이동
    function modifyArticle() {
      document.querySelector(`#list`).setAttribute("style", "display: none;");
      document.querySelector(`#wrtie`).setAttribute("style", "display: none;");
      document.querySelector(`#view`).setAttribute("style", "display: none;");
      document.querySelector(`#modify`).setAttribute("style", "display: ;");
    }
 
 // 글 작성 페이지로 이동
    function writeArticle(){
 	      document.querySelector(`#list`).setAttribute("style", "display: none;");
 	      document.querySelector(`#wrtie`).setAttribute("style", "display: ;");
 	      document.querySelector(`#view`).setAttribute("style", "display: none;");
 	      document.querySelector(`#modify`).setAttribute("style", "display: none;");
     }
 // 공지사항 list html
    function makeList(articles) {
      let tbody = ``;
      articles.forEach(function (article) {
        tbody += `
		        	  <tr>
						<th scope="row">${article.articleNo}</th>
						<td><b><a href="#" class="article-title link-dark"
								data-no="${article.articleNo}" style="text-decoration: none">
									${article.subject}</a></b></td>
						<td><b>${article.userId}</b></td>
						<td><b>${article.hit}</b></td>
						<td><b>${article.uploadTime}</b></td>
					</tr>
							`;
      });
      document.querySelector("#articlelist").innerHTML = tbody;
      makePage(this);
    }
 function makePage(articles){
	 let div = ``;
	 articles.forEach(function (article) {
		 div += `
			 ${navigation.navigator}
								`;
	      });
	      document.querySelector("#navigation").innerHTML = div;
 }
 
 // 공지사항 view html
    function makeView(article) {
      let div = ``;
      if(article){
    	  alert("글이 삭제되었거나 잘못된 접근입니다.");
    	  listArticle();
      } else{
    	  
      function (article) {
        div += `
        	<input type="hidden" class="hidden" data-no="${article.articleNo}" />
        	<div class="row my-2">
           		<h2 class="text-secondary px-5">${article.articleNo}. ${article.subject}</h2>
          	</div>
        	<div class="row">
            <div class="col-md-8">
              <div class="clearfix align-content-center">
                <img
                  class="avatar me-2 float-md-start bg-light p-2"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                />
                <p>
                  <span class="fw-bold">${article.userId}</span> <br />
                  <span class="text-secondary fw-light">
                    ${article.uploadTime} 조회 : ${article.hit}
                  </span>
                </p>
              </div>
            </div>
            <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
            <div class="divider mb-3"></div>
            <div class="text-secondary">${article.content}</div>
            <div class="divider mt-3 mb-3"></div>
            <div class="d-flex justify-content-end">
              <button type="button" id="btn-list" class="btn btn-outline-primary mb-3" onclick="">
                글목록
              </button>
              <button type="submit" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1" onclick="modifyArticle(this)">
                글수정
              </button>
              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1" onclick="deleteArticle(this)">
                글삭제
              </button>
            </div>
          </div>
							`;
      };
      }
      document.querySelector("#articleView").innerHTML = div;
    }
 
 	// 공지사항 modify html
    function makeModify(article) {
        let div = ``;
        function (article) {
          div += `
        	  <form method="POST">
              <input type="hidden" data-no="${article.articleNo}" />
              <h1 class="modal-title fs-5" id="loginModal">제목 :</h1>
              <input
                type="text"
                name="subject"
                class="form-control mt-4 mb-2"
                value="${article.subject }"
                required
              />
              <div class="form-group">
                <h1 class="modal-title fs-5" id="loginModal" style="margin-top: 20px">내용 :</h1>
                <textarea class="form-control" rows="10" name="content" required style="margin-top: 20px">
      ${article.content }</textarea
                >
              </div>
              <button type="submit" class="btn btn-secondary mb-3" style="margin-top: 20px" onclick="registModify(this)">
                글수정하기
              </button>
              <button
                type="button"
                class="btn btn-secondary mb-3"
                style="margin-top: 20px"
                onclick="listArticle()"
              >
                목록으로
              </button>
            </form>
  							`;
        };
        document.querySelector("#articleModify").innerHTML = div;
      }
 
 // 페이지
    let pages = document.querySelectorAll(".page-link");
    pages.forEach(function (page) {
      page.addEventListener("click", function () {
        console.log(this.parentNode.getAttribute("data-pg"));
        document.querySelector("#p-action").value = "list";
        document.querySelector("#p-pgno").value = this.parentNode.getAttribute("data-pg");
        document.querySelector("#p-key").value = "${param.key}";
        document.querySelector("#p-word").value = "${param.word}";
        document.querySelector("#form-param").submit();
      });
    });
    
    /*    document.querySelector("#btn-search").addEventListener("click", function () {
    let form = document.querySelector("#form-search");
    form.setAttribute("action", "${root}notice");
    form.submit();
  }); */
 // document.querySelector("#btn-list").addEventListener("click", function () {
    //   location.href = "${root}notice?go=list";
    // });

    // document.querySelector("#btn-mv-modify").addEventListener("click", function () {
    //   location.href = "${root}notice?go=modify&articleno=${article.articleNo}";
    // });

    // document.querySelector("#btn-delete").addEventListener("click", function () {
    //   location.href = "${root}notice?go=delete&articleno=${article.articleNo}";
    // });
  </script>
  
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
    crossorigin="anonymous"
  ></script>
</html>
