<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>공지사항 보기</title>
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

<c:if test="${article eq null }">
	<script>
		alert("글이 삭제되었거나 잘못된 접근입니다.");
		loacation.href = "${root}/notice/list";
	</script>
</c:if>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<div style="height: 50px"></div>
<div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
        <input type="hidden" class="hidden" value="${article.articleNo }">
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
                  <span class="text-secondary fw-light"> ${article.uploadTime} 조회 : ${article.hit} </span>
                </p>
              </div>
            </div>
            <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
            <div class="divider mb-3"></div>
            <div class="text-secondary">
              ${article.content}
            </div>
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
          </div>
        </div>
      </div>
    </div>
    <script>
      document.querySelector("#btn-list").addEventListener("click", function () {
        location.href = "${root}/notice/list?pgno=1&key=${key}&word=${word}";
      });
      document.querySelector("#btn-mv-modify").addEventListener("click", function () {
    	  
        location.href = "${root}/notice/modify?articleno=${article.articleNo}"; 
      });
      document.querySelector("#btn-delete").addEventListener("click", function () {
        location.href = "${root}/notice/delete?articleno=${article.articleNo}";
      });
    </script>
</body>
</html>