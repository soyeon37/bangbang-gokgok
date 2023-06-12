// index page 로딩 후 전국의 시도 설정.
let areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;
let areaCode = "";
let sigunguCode = "";
let contentTypeId = "";

let selArea = document.getElementById("search-area-id");
let selDetail = document.getElementById("search-area-detail-id");
let selContent = document.getElementById("search-content-id");
let btnSearch = document.getElementById("btn-search");

fetch(areaUrl, { method: "GET" })
  .then((response) => response.json())
  .then((data) => makeOption(data, "search-area-id"));

// select option 생성
function makeOption(data, id) {
  let areas = data.response.body.items.item;
  // console.log(areas);
  let sel = document.getElementById(id);
  areas.forEach((area) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", area.code);
    opt.setAttribute("class", `select-item_${id}`);
    opt.appendChild(document.createTextNode(area.name));
    sel.appendChild(opt);
  });
}

// 시군구 리스트 초기화
function resetAreaDetail() {
  let opts = document.getElementsByClassName("select-item_search-area-detail-id");
  for (let i = opts.length - 1; i >= 0; i--) {
    opts[i].parentNode.removeChild(opts[i]);
  }
}

// 지역 선택했을 때
selArea.addEventListener("change", selectArea);
function selectArea(e) {
  console.log(e.target.value);
  areaCode = e.target.value;
  if (areaCode != 0) {
    let areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=${areaCode}&_type=json`;
    fetch(areaUrl, { method: "GET" })
      .then((response) => response.json())
      .then((data) => {
        resetAreaDetail();
        makeOption(data, "search-area-detail-id");
      });
  } else {
    selDetail.value = 0;
    selContent.value = 0;
  }
}

// 검색했을 때
btnSearch.addEventListener("click", searchTrip);
let pageNo;
function searchTrip() {
  sigunguCode = selDetail.value;
  if (sigunguCode != 0) {
    // 기존에 표시되어 있던 마커 초기화
    resetMarkers();
    places = [];
    tripList = ``;
    let tripUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=8&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&areaCode=${areaCode}&sigunguCode=${sigunguCode}`;
    contentTypeId = selContent.value;
    if (contentTypeId != 0) {
      tripUrl += `&contentTypeId=${contentTypeId}`;
    }
    pageNo = 1;
    fetch(tripUrl, { method: "GET" })
      .then((response) => response.json())
      .then((data) => makeTripList(data));
  } else {
    alert("시군구를 선택해주세요!");
  }
}

let places = []; // marker 배열.
let tripList = ``;
const makeTripList = (data) => {
  let trips = data.response.body.items.item;

  trips.forEach((area) => {
    // 관광지 카드 생성
    tripList += `
    <div class="card mt-4" style="width: 18rem;padding: 10px">
      <img src="${
        area.firstimage
      }" class="card-img-top" alt="관광지 이미지" style="width:100%;height:180px;" loading="lazy">
      <div class="card-body">
        <h5 class="card-title font-weight-bold">${area.title}</h5>
        <p class="card-text">${area.addr1}${area.addr2 ? " " + area.addr2 : ""}</p>
        <p class="card-text">${area.tel}</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
      </div>
    </div>
    `;

    // 마커 정보 생성
    let markerInfo = {
      title: area.title,
      img: area.firstimage,
      addr: `${area.addr1}${area.addr2 ? " " + area.addr2 : ""}`,
      zipcode: area.zipcode,
      tel: area.tel,
      latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
    };
    places.push(markerInfo);
  });

  // 지도에 마커 표시
  displayMarker();
  // 관광지 카드 리스트 표시
  document.getElementById("trip-list").innerHTML = tripList;
  // 더보기 버튼 추가
  let moreBtn = document.createElement("input");
  moreBtn.setAttribute("class", "btn btn-primary");
  moreBtn.setAttribute("id", "showmore-btn");
  moreBtn.setAttribute("type", "button");
  moreBtn.setAttribute("value", "+ 더보기");
  moreBtn.setAttribute("style", "margin-top: 50px;");
  moreBtn.addEventListener("click", getMoreTrip);
  document.getElementById("trip-list-container").appendChild(moreBtn);
};

// 무한스크롤
// 관광지 정보 더 가져오기
function getMoreTrip() {
  const moreBtn = document.getElementById("showmore-btn");
  moreBtn.parentNode.removeChild(moreBtn);
  pageNo++;
  let tripUrl = `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=${serviceKey}&numOfRows=8&pageNo=${pageNo}&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&areaCode=${areaCode}&sigunguCode=${sigunguCode}`;
  contentTypeId = selContent.value;
  if (contentTypeId != 0) {
    tripUrl += `&contentTypeId=${contentTypeId}`;
  }
  fetch(tripUrl, { method: "GET" })
    .then((response) => response.json())
    .then((data) => makeTripList(data));
}
