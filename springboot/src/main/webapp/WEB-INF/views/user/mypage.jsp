<%@page import="com.ssafy.user.model.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String root = request.getContextPath();
%>
<!DOCTYPE html>
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
    <link href="<%= root %>/assets/css/app.css" rel="stylesheet" />
    <title>SSAFY</title>
  </head>
  <body>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="orange">내 정보 수정</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-join" method="POST" action="">
          	<input type="hidden" name="action" value="modify" />
            <div class="mb-3">
              <label for="username" class="form-label">이름 : </label>
              <input
                type="text"
                class="form-control"
                id="username"
                name="userName"
                placeholder="이름..."
                value="${sessionScope.userinfo.userName}"
              />
            </div>
            <div class="mb-3">
              <label for="userid" class="form-label">아이디 : </label>
              <input
              	readonly
                type="text"
                class="form-control"
                id="userid"
                name="userId"
                placeholder="아이디..."
                value="${sessionScope.userinfo.userId}"
              />
            </div>
            <div class="mb-3">
              <label for="emailid" class="form-label">이메일 : </label>
              <div class="input-group">
                <input
                  type="text"
                  class="form-control"
                  id="emailid"
                  name="emailId"
                  placeholder="이메일아이디"
                  value="${sessionScope.userinfo.emailId}"
                />
                <span class="input-group-text">@</span>
                <select
                  class="form-select"
                  id="emaildomain"
                  name="emailDomain"
                  aria-label="이메일 도메인 선택"
                >
                  <option>선택</option>
                  <option value="ssafy.com" ${sessionScope.userinfo.emailDomain eq 'ssafy.com' ? 'selected': ''} >싸피</option>
                  <option value="google.com" ${sessionScope.userinfo.emailDomain eq 'google.com' ? 'selected': ''}>구글</option>
                  <option value="naver.com" ${sessionScope.userinfo.emailDomain eq 'naver.com' ? 'selected': ''}>네이버</option>
                  <option value="kakao.com" ${sessionScope.userinfo.emailDomain eq 'kakao.com' ? 'selected': ''}>카카오</option>
                </select>
              </div>
            </div>
            <div class="mb-3">
              <label for="join_date" class="form-label">가입일자 : ${sessionScope.userinfo.joinDate} </label>
            </div>
            
            <div class="col-auto text-center">
              <button type="button" id="btn-index" class="btn btn-outline-primary mb-3">
홈으로
              </button>
              <button type="button" id="btn-join" class="btn btn-outline-primary mb-3">
수정
              </button>
              <button type="button" id="btn-delete" class="btn btn-outline-success mb-3">탈퇴</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
      crossorigin="anonymous"
    ></script>
    <script>
      document.querySelector("#btn-index").addEventListener("click", function () {
    	location.href = "<%= root %>/";  
      })
      document.querySelector("#btn-join").addEventListener("click", function () {
        if (!document.querySelector("#username").value) {
          alert("이름 입력!!");
          return;
        } else if (!document.querySelector("#emailid").value) {
          alert("이메일 입력!!");
          return;
        } else if (!document.querySelector("#emaildomain").value) {
          alert("이메일 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-join");
          form.setAttribute("action", "<%= root %>/user/modify");
          form.submit();
        }
      });
      document.querySelector("#btn-delete").addEventListener("click", function () {
    	  location.href = "<%= root %>/user/delete";
      })
    </script>
  </body>
</html>