<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.ssafy.user.model.dto.MemberDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 프로젝트의 context 경로를 편하게 사용하기 위한 코드 --%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous" />
<link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css"
    />
<style>
#user-info div {
	width:350px;
}
</style>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
	<div class="container">
		<div class="d-flex justify-content-between" style="width:100%">
			<a class="navbar-brand text-primary fw-bold" href="${root}/">Enjoy
				Trip</a>
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
					  	<a class="nav-link" aria-current="page" href="${root}/trip/index">나의여행경로</a>
					</li>
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${root}/hotplace.html">핫플자랑하기</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${root}/board/list?pgno=1&key=&word=">여행정보공유</a></li>
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${root}/notice/list?pgno=1&key=&word=">공지사항</a></li>
				</ul>
				<div style="flex:auto"></div>
				<ul class="navbar-nav mb-2 me-2 mb-lg-0">
		            <li class="nav-item">
		              <a class="nav-link" aria-current="page" href="${root}/user/logout">로그아웃</a>
		            </li>
		            <li class="nav-item">
		              <a class="nav-link" aria-current="page" href="${root}/user/mypage">마이페이지</a>
		            </li>
		        </ul>
			</div>
		</div>
	</div>
</nav>
<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
  crossorigin="anonymous"
></script>
