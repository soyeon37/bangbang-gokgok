<%@page import="java.util.List"%>
<%@page import="com.ssafy.plan.model.dto.PlanDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<PlanDto> planList = (List<PlanDto>) request.getAttribute("planList");
%>  
<style>
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:500px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 40px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#btn-add {
	position:absolute;left:30px;bottom:5px;z-index: 1;
	width: 200px;height:35px; 
}
.trip_item {
width: 100%;height:40px;border-radius:8px;margin-bottom:5px;padding:3px;background-color:lightgray;
display:flex;flex-direction:row;justify-content:space-between;
}
.trip_item .text {
font-size: 16px;font-weight:bold;
overflow:hidden;display:block;white-space:nowrap;text-overflow:ellipsis;
}
#btn-delete-trip {width:36px;margin-right:8px;}
.dot {overflow:hidden;float:left;width:12px;height:12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png');}    
.dotOverlay {position:relative;bottom:10px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;font-size:12px;padding:5px;background:#fff;}
.dotOverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}    
.number {font-weight:bold;color:#ee6152;}
.dotOverlay:after {content:'';position:absolute;margin-left:-6px;left:50%;bottom:-8px;width:11px;height:8px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png')}
.distanceInfo {position:relative;top:5px;left:5px;list-style:none;margin:0;}
.distanceInfo .label {display:inline-block;width:50px;}
.distanceInfo:after {content:none;}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.10.2/Sortable.min.js"></script>
<!-- kakao map start -->
<div class="map_wrap">
    <div id="map" class="mt-3" style="width:100%; height: 500px;"></div>
    <div id="menu_wrap" class="bg_white">
      <hr>
      <div id="placesList" class="wrapper">
      <c:forEach var="plan" items="${planList}">
      	<div class="item trip_item" id="${plan.planId}">
      		<button class="btn btn-danger" id="btn-delete-trip" onclick="deleteTrip(${plan.planId}, ${plan.planNo})">삭제</button>
	        <div class="text"> ${plan.title} </div>
	        <i class="fas fa-bars"></i>
	    </div>
      </c:forEach> 
      </div>
    </div>
    <button id="btn-add" type="button" class="btn btn-primary">일정 등록하기</button>
</div>
<!-- kakao map end -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6119f2c209a8a3c5a7b5aa8edbb394ad&libraries=services,clusterer,drawing"></script>

<script>
function deletePlan(planId, planNo){
	location.href = "${root}/plan/delete/"+ planId +"/"+ planNo; 
}

document.querySelector("#btn-add").addEventListener("click", function() {
	location.href = "${root}/plan/index";
})
const dragArea = document.querySelector(".wrapper");
new Sortable(dragArea, {
  animation: 350,
  onUpdate: handleUpdate
});
function handleUpdate(evt) {
	location.href = "${root}/plan/modifyNo/"+ evt.oldIndex +"/" + evt.newIndex +"/" + evt.item.id;
	console.log(evt.oldIndex, evt.newIndex, evt.item.id);
}
</script>

<script>
//카카오지도
let mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
    level: 5, // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
let map = new kakao.maps.Map(mapContainer, mapOption);

var drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
var moveLine; // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
var clickLine // 마우스로 클릭한 좌표로 그려질 선 객체입니다
var distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
var dots = []; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.

let position;
<%
for(PlanDto planDto: planList){
%>	
	position = new kakao.maps.LatLng(<%= planDto.getLatitude() %>, <%= planDto.getLongitude() %>);
	if(!drawingFlag){
		// 상태를 true로, 선이 그리고있는 상태로 변경합니다
		drawingFlag = true;
		
		//클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
		clickLine = new kakao.maps.Polyline({
		    map: map, // 선을 표시할 지도입니다 
		    path: [position], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
		    strokeWeight: 3, // 선의 두께입니다 
		    strokeColor: '#db4040', // 선의 색깔입니다
		    strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
		    strokeStyle: 'solid' // 선의 스타일입니다
		});
		
		// 선이 그려지고 있을 때 마우스 움직임에 따라 선이 그려질 위치를 표시할 선을 생성합니다
		moveLine = new kakao.maps.Polyline({
		    strokeWeight: 3, // 선의 두께입니다 
		    strokeColor: '#db4040', // 선의 색깔입니다
		    strokeOpacity: 0.5, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
		    strokeStyle: 'solid' // 선의 스타일입니다    
		});
		
		// 클릭한 지점에 대한 정보를 지도에 표시합니다
		displayCircleDot(position, 0);
	} else {
		// 그려지고 있는 선의 좌표 배열을 얻어옵니다
	    var path = clickLine.getPath();
	
	    // 좌표 배열에 클릭한 위치를 추가합니다
	    path.push(position);
	    
	    // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
	    clickLine.setPath(path);
	
	    var distance = Math.round(clickLine.getLength());
	    displayCircleDot(position, distance);
	}

<%
}
%>

<%
if(planList.size() != 0){
%>
map.setCenter(new kakao.maps.LatLng(<%= planList.get(planList.size()-1).getLatitude() %>, <%= planList.get(planList.size()-1).getLongitude() %>));
<%
}
%>
if (drawingFlag) {
    
    // 마우스무브로 그려진 선은 지도에서 제거합니다
    moveLine.setMap(null);
    moveLine = null;  
    
    // 마우스 클릭으로 그린 선의 좌표 배열을 얻어옵니다
    var path = clickLine.getPath();

    // 선을 구성하는 좌표의 개수가 2개 이상이면
    if (path.length > 1) {

        // 마지막 클릭 지점에 대한 거리 정보 커스텀 오버레이를 지웁니다
        if (dots[dots.length-1].distance) {
            dots[dots.length-1].distance.setMap(null);
            dots[dots.length-1].distance = null;    
        }

        var distance = Math.round(clickLine.getLength()), // 선의 총 거리를 계산합니다
            content = getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다
            
        // 그려진 선의 거리정보를 지도에 표시합니다
        showDistance(content, path[path.length-1]);  
         
    }          
}
//선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여 
//클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
function displayCircleDot(position, distance) {

 // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
 var circleOverlay = new kakao.maps.CustomOverlay({
     content: '<span class="dot"></span>',
     position: position,
     zIndex: 1
 });

 // 지도에 표시합니다
 circleOverlay.setMap(map);

 if (distance > 0) {
     // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
     var distanceOverlay = new kakao.maps.CustomOverlay({
         content: '<div class="dotOverlay">거리 <span class="number">' + distance + '</span>m</div>',
         position: position,
         yAnchor: 1,
         zIndex: 2
     });

     // 지도에 표시합니다
     distanceOverlay.setMap(map);
 }

 // 배열에 추가합니다
 dots.push({circle:circleOverlay, distance: distanceOverlay});
}

//마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하거
//마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
function showDistance(content, position) {
 
 if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면
     
     // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
     distanceOverlay.setPosition(position);
     distanceOverlay.setContent(content);
     
 } else { // 커스텀 오버레이가 생성되지 않은 상태이면
     
     // 커스텀 오버레이를 생성하고 지도에 표시합니다
     distanceOverlay = new kakao.maps.CustomOverlay({
         map: map, // 커스텀오버레이를 표시할 지도입니다
         content: content,  // 커스텀오버레이에 표시할 내용입니다
         position: position, // 커스텀오버레이를 표시할 위치입니다.
         xAnchor: 0,
         yAnchor: 0,
         zIndex: 3  
     });      
 }
}

//마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여 
//그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
//HTML Content를 만들어 리턴하는 함수입니다
function getTimeHTML(distance) {

 // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
 var walkkTime = distance / 67 | 0;
 var walkHour = '', walkMin = '';

 // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
 if (walkkTime > 60) {
     walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + '</span>시간 '
 }
 walkMin = '<span class="number">' + walkkTime % 60 + '</span>분'

 // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
 var bycicleTime = distance / 227 | 0;
 var bycicleHour = '', bycicleMin = '';

 // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
 if (bycicleTime > 60) {
     bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + '</span>시간 '
 }
 bycicleMin = '<span class="number">' + bycicleTime % 60 + '</span>분'

 // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
 var content = '<ul class="dotOverlay distanceInfo">';
 content += '    <li>';
 content += '        <span class="label">총거리</span><span class="number">' + distance + '</span>m';
 content += '    </li>';
 content += '    <li>';
 content += '        <span class="label">도보</span>' + walkHour + walkMin;
 content += '    </li>';
 content += '    <li>';
 content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
 content += '    </li>';
 content += '</ul>'

 return content;
}

</script>