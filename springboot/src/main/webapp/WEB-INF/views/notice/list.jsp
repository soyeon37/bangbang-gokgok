<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>공지사항 목록</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
  </head>
  <body>
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <div style="height: 80px"></div>

    <!--게시판 목록-->
    <div class="container">
      <div style="display: flex; justify-content: space-between">
        <!--글쓰는 창으로 가는 버튼-->
        <input type="button" class="btn btn-secondary" value="글쓰기" onclick="location.href='${root}/notice/write'"/></input>
        <div>
          <form class="d-flex" id="form-search" action="">
	        <input type="hidden" name="pgno" value="1"/>
	        <select name ="search-option" class="form-select form-select-sm ms-5 me-1 w-50" aria-label="Default select example">
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
	                  <option value="article_no">글번호</option>
	                  <option value="subject">제목</option>
	                  <option value="user_id">작성자</option>
	          </select>
	          <div class="input-group input-group-sm">
                  <input type="text" name="word" id="word" class="form-control" placeholder="검색어..." />
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
        <tbody>
		  <c:forEach var="article" items="${articles }">
          <tr>
          	<th scope="row">${article.articleNo}</th>
            <td><b><a href="#" class="article-title link-dark" data-no="${article.articleNo }" style="text-decoration: none">
            	${article.subject }</a></b>
            </td>
            <td><b>${article.userId }</b></td>
            <td><b>${article.hit }</b></td>
            <td><b>${article.uploadTime }</b></td>
          </tr>
          </c:forEach>
        </tbody>
      </table>

      <div style="height: 20px"></div>
      <div class="row">${navigation.navigator }</div>
    </div>
  </body>
  <form id="form-param" method="get" action="">
      <input type="hidden" id="p-action" name="go" value="">
      <input type="hidden" id="p-pgno" name="pgno" value="">
      <input type="hidden" id="p-key" name="key" value="">
      <input type="hidden" id="p-word" name="word" value="">
    </form>
   <form id="form-no-param" method="get" action="">
      <input type="hidden" name="pgno" id="pgno" value="${pgno}">
      <input type="hidden" name="key" value="${key}">
      <input type="hidden" name="word" value="${word}">
    </form>
  <script>
  let titles = document.querySelectorAll(".article-title");
  titles.forEach(function (title) {
    title.addEventListener("click", function () {
      console.log(this.getAttribute("data-no"));
      location.href = "${root}/notice/view?articleno=" + this.getAttribute("data-no");
    });
  });
  
  document.querySelector("#btn-search").addEventListener("click", function () {
	  let form = document.querySelector("#form-search");
      form.setAttribute("action", "${root}/notice/list");
      form.submit();
  });
  
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
  </script>
</html>
