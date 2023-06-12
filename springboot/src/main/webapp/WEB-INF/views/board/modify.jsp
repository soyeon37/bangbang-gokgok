<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>글쓰기</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css"
    />
  </head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<div class="container">
      <div style="height: 100px"></div>
      <h1>게시글 쓰기</h1>
      <hr />
      <form method="POST" action="${root }/board/modify">
      	<input type="hidden" name="articleno" value="${article.articleNo }">
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
          <textarea
            class="form-control"
            rows="10"
            name="content"
            required
            style="margin-top: 20px"
          >${article.content }</textarea>
        </div>
        <button
          type="submit"
          class="btn btn-secondary mb-3"
          style="margin-top: 20px"
        >
          글수정하기
        </button>
        <button
          type="button"
          class="btn btn-secondary mb-3"
          style="margin-top: 20px"
          onclick="location.href='${root}/board/list?pgno=1&key=&word='"
        >
          목록으로
        </button>
      </form>
    </div>
</body>
</html>