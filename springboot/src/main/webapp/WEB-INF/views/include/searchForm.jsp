<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="d-md-flex my-3" onsubmit="return false;" role="search">
	<select id="search-area-id" class="form-select me-2">
		<option value="0" selected>지역</option>
	</select> 
	<select id="search-area-detail-id" class="form-select me-2">
		<option value="0" selected>시군구</option>
	</select> 
	<select id="search-content-id" class="form-select me-2">
		<option value="0" selected>관광지 유형</option>
		<option value="12">관광지</option>
		<option value="14">문화시설</option>
		<option value="15">축제공연행사</option>
		<option value="25">여행코스</option>
		<option value="28">레포츠</option>
		<option value="32">숙박</option>
		<option value="38">쇼핑</option>
		<option value="39">음식점</option>
	</select>
	<input type="text" class="form-control" id="search-keyword" name="keyword" placeholder="키워드입력" />
	<button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
</form>
<script>
const serviceKey = "JPaih3TvB82xKTEEo0u7o2szUU3w5qjrXZGt5TFdhlf4%2FAJEndq3UTYMVZhxT%2BAlaF05ro%2FmiDUh4qnZYE78CA%3D%3D";
let areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=\${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;
let areaCode = "";
let sigunguCode = "";
let contentTypeId = "";
let keyword = "";

let selArea = document.getElementById("search-area-id");
let selDetail = document.getElementById("search-area-detail-id");
let selContent = document.getElementById("search-content-id");
let inputKeyword = document.getElementById("search-keyword");
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
    opt.setAttribute("class", `select-item_\${id}`);
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
  areaCode = e.target.value;
  if (areaCode != 0) {
    let areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=\${serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=\${areaCode}&_type=json`;
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
function searchTrip() {
  sigunguCode = selDetail.value;
  contentTypeId = selContent.value;
  keyword = inputKeyword.value;
  if (sigunguCode != 0) {
    let tripUrl = `${root}attraction?action=search&pageNo=1&areaCode=\${areaCode}&sigunguCode=\${sigunguCode}&contentTypeId=\${contentTypeId}&keyword=\${keyword}`;
    location.href = tripUrl;
  } else {
    alert("시군구를 선택해주세요!");
  }
}
</script>