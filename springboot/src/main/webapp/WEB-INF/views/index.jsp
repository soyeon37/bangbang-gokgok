<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" import="com.ssafy.user.model.dto.UserDto"%> 
<%
UserDto memberDto = (UserDto) session.getAttribute("userinfo");
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
    <link href="<%= request.getContextPath() %>/assets/css/app.css" rel="stylesheet" />
    <title>SSAFY</title>
  </head>
  <body>
<%
if(memberDto == null) {
%>
    <%@ include file="/WEB-INF/views/user/login.jsp" %>
<%
} else {
%>
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<div style="height: 100px"></div>
    <div class="container">
	    <div class="col-12">
		  <h2 class="my-3 py-3 shadow-sm bg-light text-center">
		    <mark class="orange">관광지 검색</mark>
		  </h2>
		</div>
		<%@ include file="/WEB-INF/views/include/searchForm.jsp" %>
    </div>
    <div style="height: 250px"></div>
<%
}
%>
  	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
  </body>
</html>
