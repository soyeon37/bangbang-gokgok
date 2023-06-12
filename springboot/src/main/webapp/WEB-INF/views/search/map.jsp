<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- kakao map start -->
<div id="map" class="mt-3" style="width: 100%; height: 400px"></div>
<!-- kakao map end -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6119f2c209a8a3c5a7b5aa8edbb394ad&libraries=services,clusterer,drawing"></script>
<script>
//카카오지도
let mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
    level: 5, // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
let map = new kakao.maps.Map(mapContainer, mapOption);

// 여러 개의 마커를 관리할 배열
let markers = [];

// 지도에 표시된 마커 초기화
function resetMarkers() {
  for (let i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
}

function displayMarker() {
  for (let i = 0; i < places.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    let imageSize = new kakao.maps.Size(35, 35);

    // 마커 이미지를 생성합니다
    let markerImage = new kakao.maps.MarkerImage(places[i].markerImage, imageSize);

    // 마커를 생성합니다
    let marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: places[i].latlng, // 마커를 표시할 위치
      title: places[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });

    // 인포윈도우 html
    let iwContent = `
    <style>
    #infowindow-container {
      width: 250px;
      height: 200px;
      display: flex;
      flex-direction: column;
      border-radius: 2px;
    }
    #trip-title {
      width: 100%;
      height: 30px;
      background-color: lightgrey;
      font-size: 18px;
      font-weight: bold;
      text-align: center;
      vertical-align: middle;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
    #trip-content {
      padding: 10px;
      display: flex;
      flex-direction: row;
    }
    #trip-img {
      width: 100px;
      height: 100px;
      margin-right: 10px;
      object-fit: fill;
    }
    #trip-info {
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: flex-start;
    }
    #trip-addr {
      padding:0px;
      font-size: 14px;
      flex-wrap: wrap;
    }
    #trip-zipcode {
      padding:0px;
      color: gray;
      font-size: 12px;
    }
    #trip-tel {
      padding:0px;
      color: gray;
      font-size: 12px;
    }
    </style>
    <div class="infowindow-container">
      <div id="trip-title">\${places[i].title}</div>
      <div id="trip-content">
        <img id="trip-img" src="\${places[i].img? places[i].img :"https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif"}" />
        <div id="trip-info">
          <p id="trip-addr">\${places[i].addr}</p>
          <p id="trip-zipcode">(우) \${places[i].zipcode}</p>
          <p id="trip-tel">(전화번호) \${places[i].tel}</p>
        </div>
      </div>
    </div>
    `;
    // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    let iwPosition = new kakao.maps.LatLng(places[i].mapy, places[i].mapx); //인포윈도우 표시 위치입니다

    // 인포윈도우를 생성합니다
    let infowindow = new kakao.maps.InfoWindow({
      position: iwPosition,
      content: iwContent,
    });

    // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
    kakao.maps.event.addListener(marker, "mouseover", makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, "mouseout", makeOutListener(infowindow));

    markers.push(marker);
  }

  // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
  if(places.length > 0)
 	 map.setCenter(places[0]?.latlng);
  else
	 map.setCenter(new kakao.maps.LatLng(37.500613, 127.036431));
}

function moveCenter(lat, lng) {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
  return function () {
    infowindow.open(map, marker);
  };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
  return function () {
    infowindow.close();
  };
}
</script>