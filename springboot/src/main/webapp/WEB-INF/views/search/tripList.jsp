<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,com.ssafy.attraction.model.dto.AttractionDto"%>
<% List<AttractionDto> attractionList = (List<AttractionDto>) request.getAttribute("attractionList"); %>
<%@ include file="map.jsp" %>
<div style="height: 50px"></div>
	<!-- 검색 결과 내 재검색 입력창 -->
	<form class="d-md-flex my-3" onsubmit="return false;" role="search" >
		<input type="text" class="form-control" id="search-keyword" name="searchInResult" placeholder="결과 내 재검색" value="${param.searchInResult}"/>
		<button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
	</form>
	<!-- 관광지 리스트 start -->
	<div id="trip-list-container" style="display: flex; flex-direction: column; align-items: center">
		<div id="trip-list" class="row justify-content-around">
	</div>
	<script>
	function handleRegist(e) {
	   console.log(e.target.id)
	   let form = document.querySelector(`#form-regist-trip-\${e.target.id}`);
	   form.setAttribute("action", "${root}/trip/registTrip");
	   form.submit();
	};
	</script>
</div>
<!-- 관광지 리스트 end -->
<script>
let places = []; // marker 배열.
let tripList = ``;
let imageSrc;
let markerInfo;

<%
if(attractionList!=null){
for(AttractionDto area: attractionList) { %>
    // 관광지 카드 생성
    tripList += `
    <div class="card mt-4" style="width: 18rem;padding: 10px">
      <img src="<%=(area.getFirstImage() !=null && area.getFirstImage().length() > 0) ? area.getFirstImage() : "https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif"%>" class="card-img-top" alt="관광지 이미지" style="width:100%;height:180px;" loading="lazy">
      <div class="card-body">
        <h5 class="card-title font-weight-bold"><%=area.getTitle()%></h5>
        <p class="card-text"><%=area.getAddr1()%><%=area.getAddr2()!=null? area.getAddr2() : ""%></p>
        <p class="card-text"><%=area.getTel()%></p>
        <form id="form-regist-trip-<%=area.getContentId()%>" method="POST" action="">
        <input hidden name="contentid" value="<%=area.getContentId()%>"/>
        <button type="button" id="<%=area.getContentId()%>" class="btn btn-primary" onclick="handleRegist(event)">일정에 추가하기</a>
        </form>
      </div>
    </div>
    `;

    // 마커 이미지의 이미지 주소입니다
    imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    switch(<%=area.getContentTypeId()%>){
    //관광지
    case "12": imageSrc = "https://cdn-icons-png.flaticon.com/512/201/201623.png"; break;
    //문화시설
    case "14": imageSrc = "https://icons-for-free.com/iconfiles/png/512/bookshelf+library+icon-1320087270870761354.png"; break;
    //축제공연행사
    case "15": imageSrc = "https://cdn-icons-png.flaticon.com/512/2784/2784589.png"; break;
    //여행코스
    case "25": imageSrc = "https://www.wavetransit.com/wp-content/uploads/2017/03/wave-connect-travel-training-icon-300x300.png"; break;
    //레포츠
    case "28": imageSrc = "https://cdn-icons-png.flaticon.com/512/694/694693.png"; break;
    //숙박
    case "32": imageSrc = "https://cdn-icons-png.flaticon.com/512/235/235813.png"; break;
    //쇼핑
    case "38": imageSrc = "https://icons-for-free.com/iconfiles/png/512/buy+citycons+mall+shopping+icon-1320136423977087098.png"; break;
    //음식점
    case "39": imageSrc = "https://cdn-icons-png.flaticon.com/512/121/121907.png"; break;
    }
    
    // 마커 정보 생성
    markerInfo = {
      title: "<%=area.getTitle()%>",
      img: "<%=area.getFirstImage()%>",
      addr: "<%=area.getAddr1()%><%=area.getAddr2()!=null? area.getAddr2() : ""%>",
      zipcode: "<%=area.getZipcode()%>",
      tel: "<%=area.getTel()%>",
      latlng: new kakao.maps.LatLng(<%=area.getLatitude()%>, <%=area.getLongitude()%>),
      markerImage: imageSrc,
    };
    places.push(markerInfo);
<% } %>

  // 지도에 마커 표시
  displayMarker();
  // 관광지 카드 리스트 표시
  document.getElementById("trip-list").innerHTML = tripList;
<% } %>

// 무한스크롤
// 관광지 정보 더 가져오기
function getMoreTrip() {
  resetMoreBtn();
  let tripUrl = "${root}attraction?action=search&numOfRows=8&pageNo=${param.pageNo+1}&areaCode=${param.areaCode}&sigunguCode=${param.sigunguCode}";
  if ("${param.contentTypeId}" != 0) {
    tripUrl += "&contentTypeId=${param.contentTypeId}";
  }
  location.href = tripUrl;
}

let keyword = "";
let inputKeyword = document.getElementById("search-keyword");
let btnSearch = document.getElementById("btn-search");
btnSearch.addEventListener("click", searchKeyword);
function searchKeyword() {
  keyword = inputKeyword.value;
  if (keyword) {
	index = location.href.indexOf("searchInResult");
	//console.log(location.href.slice(0,index));
	if(index != -1) 
		location.href = location.href.slice(0,index)+ `&searchInResult=\${keyword}`; 
	else
    	location.href = location.href + `&searchInResult=\${keyword}`;
  } else {
    alert("검색어를 입력해주세요!");
  }
}
</script>
