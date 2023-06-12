<template>
  <!-- <div id="map" class="mt-3" style="width: 100%; height: 400px"></div> -->
  <!-- 여행 계획 추가하기 버튼 누르면 옆에 사이드바 뜨면서 여행계획 쭉 뜨고 맨 밑에 여행계획 추가하기 버튼, 하나 선택하면 -->
  <!-- 모달창 나오면서 해당 여행 계획의 상세 페이지가 존재하게 됨 -->
  <div id="map"></div>
</template>

<script>
export default {
  name: "MapComponent",
  components: {},
  data() {
    return {
      map: null,
      // 마커를 띄우기 위한 여행지 정보 저장
      positions: [],
      // 마커 정보 배열 목록
      markers: [],
      // 임시 마커
      marker: null,
    };
  },
  props: {
    // 검색 결과창에서 얻어온 여행지 리스트
    places: [],
  },
  watch: {
    // 부모 컴포넌트로부터 새로운 관광지 정보 받아오면 갱신 시작
    places() {
      console.log("여행지 변경 감지 및 마커 갱신 시작", this.places);
      this.positions = [];
      this.places.forEach((place) => {
        let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
        switch (place.contentTypeId) {
          case 12:
            console.log("imageSrc 12로 변경");
            imageSrc = "https://cdn-icons-png.flaticon.com/512/201/201623.png";
            break;
          case 14:
            console.log("imageSrc 14로 변경");
            imageSrc =
              "https://icons-for-free.com/iconfiles/png/512/bookshelf+library+icon-1320087270870761354.png";
            break;
          case 15:
            console.log("imageSrc 15로 변경");
            imageSrc = "https://cdn-icons-png.flaticon.com/512/2784/2784589.png";
            break;
          case 25:
            console.log("imageSrc 25로 변경");
            imageSrc =
              "https://www.wavetransit.com/wp-content/uploads/2017/03/wave-connect-travel-training-icon-300x300.png";
            break;
          case 28:
            console.log("imageSrc 28로 변경");
            imageSrc = "https://cdn-icons-png.flaticon.com/512/694/694693.png";
            break;
          case 32:
            console.log("imageSrc 32로 변경");
            "https://cdn-icons-png.flaticon.com/512/235/235813.png";
            break;
          case 38:
            console.log("imageSrc 38로 변경");
            "https://icons-for-free.com/iconfiles/png/512/buy+citycons+mall+shopping+icon-1320136423977087098.png";
            break;
          case 39:
            console.log("imageSrc 39로 변경");
            "https://cdn-icons-png.flaticon.com/512/121/121907.png";
            break;
          default:
            console.log("아무데도 안걸리고 내려옴 에러난듯");
        }
        let obj = {};
        obj.title = place.title;
        obj.img = place.firstImage;
        obj.addr = `${place.addr1}${place.addr2.length > 0 ? place.addr2 : ""}`;
        obj.zipcode = place.zipcode;
        obj.tel = place.tel;
        obj.latlng = new kakao.maps.LatLng(place.latitude, place.longitude);
        obj.markerImage = imageSrc;
        obj.contentId = place.contentId;
        obj.contentTypeId = place.contentTypeId;

        this.positions.push(obj);
      });
      this.loadMaker();
    },
  },

  async mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
    this.marker = new window.kakao.maps.Marker({
      position: this.map.getCenter(),
    });

    this.marker.setMap(this.map);
  },
  created() {
    window.addEventListener("custom-event", this.registTrip);
  },
  beforeDestroy() {
    window.removeEventListener("custom-event", this.registTrip);
  },

  methods: {
    // 처음 실행때 kakao 맵 api 정보 받아오기
    loadScript() {
      const script = document.createElement("script");
      // autoload 추가
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=9299cb2ca12f08adc5df63f4dd54c237&libraries=services,clusterer,drawing";

      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);
      document.head.appendChild(script);
    },

    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map");

      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
    },

    // 마커 불러오기
    loadMaker() {
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      console.log("마커 불러오기 시작");
      this.deleteMarker();
      console.log("기존 마커 제거");
      // 마커 이미지를 생성합니다
      //   const imgSrc = require("@/assets/map/markerStar.png");
      // 마커 이미지의 이미지 크기 입니다
      //   const imgSize = new kakao.maps.Size(24, 35);
      //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      // 마커를 생성합니다
      this.markers = [];
      // 마커의 이미지 사이즈를 설정합니다
      const imageSize = new window.kakao.maps.Size(35, 35);

      // 마커 생성을 위한 포지션 배열에서 얻어온다
      this.positions.forEach((position) => {
        const markerImage = new window.kakao.maps.MarkerImage(position.markerImage, imageSize);
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커의 이미지
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
      <div id="trip-title">${position.title}</div>
      <div id="trip-content">
        <img id="trip-img" src="${
          position.img
            ? position.img
            : "https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif"
        }" />
        <div id="trip-info">
          <p id="trip-addr">${position.addr}</p>
          <p id="trip-zipcode">(우) ${position.zipcode}</p>
          <p id="trip-tel">(전화번호) ${position.tel}</p>

          <button onclick="window.open('https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=${
            position.title
          }')">네이버 검색</button>
          <button onclick="window.open('https://instagram.com/explore/tags/${
            position.title
          }')">인스타 검색</button>
          <button onclick="window.dispatchEvent(new CustomEvent('custom-event', { detail: { title: '${
            position.title
          }', image: '${position.img}', contentId: '${position.contentId}', contentTypeId: '${
          position.contentTypeId
        }' } }))">여행지 추가</button>
          

        </div>
      </div>
    </div>
    


    `;

        // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        let iwPosition = new kakao.maps.LatLng(position.latitude, position.longitude); //인포윈도우 표시 위치입니다

        let infowindow = new window.kakao.maps.InfoWindow({
          position: iwPosition,
          content: iwContent,
          removable: true,
        });

        var iwContent2 = `<div style="padding:5px;">${position.title}</div>`; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

        // 인포윈도우를 생성합니다
        var infowindow2 = new kakao.maps.InfoWindow({
          position: iwPosition,
          content: iwContent2,
        });

        // mouseover (마우스 올리면) 또는 click으로 설정 가능
        kakao.maps.event.addListener(
          marker,
          "click",
          this.makeOverListener(this.map, marker, infowindow)
        );

        kakao.maps.event.addListener(
          marker,
          "mouseover",
          this.makeOverListener(this.map, marker, infowindow2)
        );

        kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow2));

        // 마우스 벗어나면 닫히도록도 설정 가능
        // kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));

        this.markers.push(marker);
      });
      console.log("마커수 ::: " + this.markers.length);

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },

    registTrip(event) {
      console.log("마커 여행지 추가 버튼 클릭이 인식되었습니다");
      this.$emit(
        "regist-trip",
        event.detail.title,
        event.detail.image,
        event.detail.contentId,
        event.detail.contentTypeId
      );
    },

    resetMarkers() {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
    },

    deleteMarker() {
      console.log("마커 싹 지우자!!!", this.markers.length);
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          console.log(item);
          item.setMap(null);
        });
      }
    },

    // load Marker랑 역할이 겹침
    displayMarker() {
      for (let i = 0; i < this.places.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        let imageSize = new window.kakao.maps.Size(35, 35);

        // 마커 이미지를 생성합니다
        let markerImage = new window.kakao.maps.MarkerImage(this.places[i].markerImage, imageSize);

        // 마커를 생성합니다
        let marker = new window.kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: this.places[i].latlng, // 마커를 표시할 위치
          title: this.places[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
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
        let iwPosition = new window.kakao.maps.LatLng(this.places[i].mapy, this.places[i].mapx); //인포윈도우 표시 위치입니다

        // 인포윈도우를 생성합니다
        let infowindow = new window.kakao.maps.InfoWindow({
          position: iwPosition,
          content: iwContent,
        });

        // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
        window.kakao.maps.event.addListener(
          marker,
          "click",
          // "mouseover",
          // this.makeOverListener(this.map, marker, infowindow)
          function () {
            infowindow.open(this.map, marker);
          }
        );
        window.kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));

        this.markers.push(marker);
      }

      // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
      if (this.places.length > 0) this.map.setCenter(this.places[0]?.latlng);
      else this.map.setCenter(new window.kakao.maps.LatLng(37.500613, 127.036431));
    },

    moveCenter(lat, lng) {
      this.map.setCenter(new window.kakao.maps.LatLng(lat, lng));
    },

    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },

    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 700px;
}
</style>
