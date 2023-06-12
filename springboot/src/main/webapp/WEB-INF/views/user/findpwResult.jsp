<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
String pwd = request.getAttribute("pwd").toString();
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
            <mark class="orange">비밀번호 찾기 결과</mark>
          </h2>
        </div>
<%
if(pwd.length() == 0){
%>
		<div class="col-lg-8 col-md-10 col-sm-12">
          	<div class="mb-3">존재하지 않는 아이디 입니다.</div>
          	<div class="col-auto text-center">
            <button type="button" id="btn-findpw" class="btn btn-outline-primary mb-3">비밀번호찾기 </button>
            <button type="button" id="btn-login" class="btn btn-outline-primary mb-3">로그인하기 </button>
        	 </div>
        </div>
      </div>
<%
} else {
%>
        <div class="col-lg-8 col-md-10 col-sm-12">
          	<div class="mb-3">당신의 비밀번호는 <%= pwd %> 입니다.</div>
            <div class="col-auto text-center">
            <button type="button" id="btn-login" class="btn btn-outline-primary mb-3">로그인하기 </button>
        	</div>
        </div>
      </div>
      <script>
      document.querySelector("#btn-login").addEventListener("click", function () {
    	  location.href = "user?action=mvlogin";
      })
    </script>
<%
}
%>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
      crossorigin="anonymous"
    ></script>
    <script>
      document.querySelector("#btn-findpw").addEventListener("click", function () {
    	  location.href = "<%= root %>user?action=findpw";
      })
      document.querySelector("#btn-login").addEventListener("click", function () {
    	  location.href = "<%= root %>user?action=login";
      })
    </script>
  </body>
</html>