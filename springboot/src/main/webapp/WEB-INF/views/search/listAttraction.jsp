<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<div style="height: 100px"></div>
    <div class="container">
    	<div class="col-12">
		  <h2 class="my-3 py-3 shadow-sm bg-light text-center">
		    <mark class="orange">검색 결과</mark>
		  </h2>
		</div>
		<%@ include file="/WEB-INF/views/search/tripList.jsp" %>
		<div style="height: 20px"></div>
      	<div class="row">${navigation.navigator}</div>
      	<form id="form-param" method="get" action="">
	      <input type="hidden" id="p-action" name="action" value="">
	      <input type="hidden" id="p-pageNo" name="pageNo" value="">
	      <input type="hidden" id="p-keyword" name="keyword" value="">
	      <input type="hidden" id="p-areaCode" name="areaCode" value="">
	      <input type="hidden" id="p-sigunguCode" name="sigunguCode" value="">
	      <input type="hidden" id="p-contentTypeId" name="contentTypeId" value="">
	    </form>
    </div>
    <div style="height: 250px"></div>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
<script>
  let pages = document.querySelectorAll(".page-link");
  pages.forEach(function (page) {
    page.addEventListener("click", function () {
      console.log(this.parentNode.getAttribute("data-pg"));
      document.querySelector("#p-action").value = "search";
   	  document.querySelector("#p-pageNo").value = this.parentNode.getAttribute("data-pg");
   	  document.querySelector("#p-keyword").value = "${param.keyword}";
   	  document.querySelector("#p-areaCode").value = "${param.areaCode}";
   	  document.querySelector("#p-sigunguCode").value = "${param.sigunguCode}";
   	  document.querySelector("#p-contentTypeId").value = "${param.contentTypeId}";
      document.querySelector("#form-param").submit();
    });
  });
  </script>
</html>