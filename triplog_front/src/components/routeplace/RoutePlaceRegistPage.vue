<script setup>
import { ref, onMounted, onBeforeMount, onUpdated, reactive } from "vue";
import { findPlace } from "../../api/routePlace";
import { useRoute, useRouter } from "vue-router";
import AddRouteList from "./AddRouteList.vue";
import { findRoutePlace, findPlaceAgain, registRoutePlace, deletePlace } from "@/api/routePlace";
import { getReviewListByRoute } from "@/api/review";
import { useGeolocation } from "@vueuse/core";
const { coords, locatedAt, error, resume, pause } = useGeolocation();

const route = useRoute();
const router = useRouter();

const placeInput = ref("");
const isModalOpen = ref(false);
const placeList = ref([]);
const rePlace = ref([]);
const reviewList = ref([]);
const placePriority = ref(1);

const myPlaceList = ref([]);
var f_latitude = ref(0);
var f_longitude = ref(0);

var mapContainer;
var mapOption;
var map;
var marker;
var markers = [];
// var polyline;
var polylineList = [];
let imageSrc = "https://mytriplog.s3.us-west-2.amazonaws.com/route/ticjcicons8.png";
// let linePath = [];
var lineList = [];

defineProps({
  place: String,
});

onBeforeMount(() => {});

const script = document.createElement("script");
script.type = "text/javascript";
script.src =
  "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=c4a74963fd48fc7faf83bc29cd7e7647&libraries=services";

const myPos = ref({});

onUpdated(() => {
  findRoutePlace(route.params.route_id, ({ data }) => {
    myPlaceList.value = data;
    
    if (myPlaceList.value.length == 0) {
      myPlaceList.value = [];
    }
    placePriority.value = myPlaceList.value.length + 1;
  });
})

onMounted(async () => {
  navigator.geolocation.getCurrentPosition((position) => {
    myPos.value = {
      longitude: position.coords.longitude,
      latitude: position.coords.latitude,
    };
  });
  await findRoutePlace(route.params.route_id, ({ data }) => {
    myPlaceList.value = data;

    if (myPlaceList.value.length == 0) {
      myPlaceList.value = [];
    }
    placePriority.value = myPlaceList.value.length + 1;
  });

  script.addEventListener("load", () => {
    kakao.maps.load(() => {
      // 카카오맵 API가 로딩이 완료된 후 지도의 기본적인 세팅을 시작해야 한다.
      console.log("First Call");
      initMap();
    });
  });
  document.head.appendChild(script);

  // console.log("myRegist : ", myPlaceList.value);
  // console.log("under" + f_latitude.value);
  // if (!window.kakao || !window.kakao.maps) {
  //   /* eslint를 사용한다면 kakao 변수가 선언되지 않았다고
  //    * 오류를 내기 때문에 아래 줄과 같이 전역변수임을
  //    * 알려주어야 한다. */
  //   /* global kakao */
  //   script.addEventListener("load", () => {
  //     kakao.maps.load(() => {
  //       // 카카오맵 API가 로딩이 완료된 후 지도의 기본적인 세팅을 시작해야 한다.
  //       console.log("First Call");
  //       initMap();
  //     });
  //   });
  //   document.head.appendChild(script);
  // } else {
  //   // 이미 카카오맵 API가 로딩되어 있다면 바로 지도를 생성한다.
  //   console.log("Re-call");
  //   initMap();
  // }

});

const initMap = () => {
  // map1
  mapContainer = document.getElementById("map");
  // 등록된 내 루트들이 있다면 내루트 중심으로 이동
  if (myPlaceList.value.length != 0) {
    mapOption = {
      // center: new kakao.maps.LatLng(myPos.value.latitude, myPos.value.longitude),
      center: new kakao.maps.LatLng(myPlaceList.value[0].latitude, myPlaceList.value[0].longitude),
      level: 5,
    };
  } else {
    mapOption = {
      center: new kakao.maps.LatLng(myPos.value.latitude, myPos.value.longitude),
      // center: new kakao.maps.LatLng(36.3504119, 127.3845475),
      // center: new kakao.maps.LatLng(f_latitude, f_longitude),
      level: 5,
    };
  }
  map = new kakao.maps.Map(mapContainer, mapOption);

  console.log("myRegist : ", myPlaceList.value);

  makeMarkerAndLine();
};

///////// 마커 및 선 생성하기 //////////
const makeMarkerAndLine = () => {
  var linePath = [];
  for (var i = 0; i < myPlaceList.value.length; i++) {
    var spot_name = myPlaceList.value[i].spot_name;
    var latitude = myPlaceList.value[i].latitude;
    var longitude = myPlaceList.value[i].longitude;

    var imageSize = new kakao.maps.Size(64, 69); // 마커이미지의 크기입니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(latitude, longitude), // 마커를 표시할 위치
      title: spot_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커이미지
      clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
    });
    marker.setMap(map);
    markers.push(marker);

    // 선을 표시
    linePath.push(new kakao.maps.LatLng(latitude, longitude));
  }

  lineList.push(linePath);
  // 지도에 표시할 선을 생성합니다
  var polyline = new kakao.maps.Polyline({
    path: linePath, // 선을 구성하는 좌표배열 입니다
    strokeWeight: 5, // 선의 두께 입니다
    strokeColor: "#6297e6", // 선의 색깔입니다
    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: "solid", // 선의 스타일입니다
  });
  console.log(polyline);
  polylineList.push(polyline);
  polyline.setMap(map);
};

function findInfos() {
  findPlace(placeInput.value, 127.3845475, 36.3504119, ({ data }) => {
    placeList.value = data;
    rePlace.value = [];
    console.log(placeList.value);

    // 마커를 표시할 위치와 title 객체 배열입니다
    for (var i = 0; i < placeList.value.length; i++) {
      var spot_name = placeList.value[i].spot_name;
      var latitude = placeList.value[i].latitude;
      var logitude = placeList.value[i].longitude;

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(latitude, logitude), // 마커를 표시할 위치
        title: spot_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      });
    }
  });
}

function findAgain() {
  findPlaceAgain(placeInput.value, 127.3845475, 36.3504119, ({ data }) => {
    rePlace.value = data;
    console.log(placeList.value);

    // 마커를 표시할 위치와 title 객체 배열입니다
    for (var i = 0; i < placeList.value.length; i++) {
      var spot_name = rePlace.value[i].spot_name;
      var latitude = rePlace.value[i].latitude;
      var logitude = rePlace.value[i].longitude;

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(latitude, logitude), // 마커를 표시할 위치
        title: spot_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      });
    }
  });
}

const selected = ref({});
const focusPos = (p) => {
  var moveLatLon = new kakao.maps.LatLng(p.latitude, p.longitude);
  // 지도 중심을 이동 시킵니다
  map.setCenter(moveLatLon);
  selected.value = p;
};

const registPlace = () => {
  console.log(selected.value);
  if (Object.keys(selected.value).length === 0) {
    alert("장소를 선택해주세요!!!");
  } else {
    var pri = placePriority.value;
    var newPlace = ref({
      spot_id: selected.value.spot_id,
      rp_name: selected.value.spot_name,
      longitude: selected.value.longitude,
      latitude: selected.value.latitude,
      priority: pri,
      visit_time: "20230101000000",
    });
    var responsePlace = ref({
      placeName: selected.value.spot_name,
      longitude: selected.value.longitude,
      latitude: selected.value.latitude,
      priority: pri,
      visit_time: "20230101000000",
      address: selected.value.address,
    });

    registRoutePlace(route.params.route_id, newPlace.value, ({ data }) => {
      console.log("등록된 루트야1 : ", data);
      myPlaceList.value.push(data);
    });
    placePriority.value += 1;

    ////// 등록된 새로운 마커 및 선 긋기 //////
    var markerPosition = new kakao.maps.LatLng(
      responsePlace.value.latitude,
      responsePlace.value.longitude
    ); // 마커가 표시될 위치입니다

    var imageSize = new kakao.maps.Size(64, 69); // 마커이미지의 크기입니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
    // 마커를 생성합니다
    marker = new kakao.maps.Marker({
      map: map,
      position: markerPosition,
      image: markerImage, // 마커이미지 설정
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    markers.push(marker);

    // *******마지막 linePath에 추가
    var linePath = lineList.pop();
    // 선을 표시
    linePath.push(
      new kakao.maps.LatLng(responsePlace.value.latitude, responsePlace.value.longitude)
    );
    lineList.push(linePath);
    // 지도에 표시할 선을 생성합니다
    var polyline = new kakao.maps.Polyline({
      path: linePath, // 선을 구성하는 좌표배열 입니다
      strokeWeight: 5, // 선의 두께 입니다
      strokeColor: "#6297e6", // 선의 색깔입니다
      strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      strokeStyle: "solid", // 선의 스타일입니다
    });
    console.log(polyline);
    polylineList.push(polyline);
    polyline.setMap(map);
  }
};

// 선택된 플레이스 삭제하기
const sendPlace = (pid, priority) => {
  deletePlace(route.params.route_id, pid, priority, ({ data }) => {
    console.log("재정렬된 리스트 : ", data);
    myPlaceList.value = data;
    placePriority.value = myPlaceList.value.length + 1;

    // 모든 마커 삭제
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    console.log("lineList : ", lineList);
    console.log("삭제전 >", polylineList);
    // // 모든 선 삭제
    // console.log("삭제 전", polyline);
    // polyline = new kakao.maps.Polyline({
    //   path: [], // 선을 구성하는 좌표배열 입니다
    // });
    // polyline.setMap(null);
    // console.log("삭제 후", polyline);

    for (var i = 0; i < polylineList.length; i++) {
      polylineList[i].setMap(null);
    }
    console.log("삭제후 >", polylineList);

    // 다시 마커 & 선 그리기
    makeMarkerAndLine();
  });
};

// 리뷰 등록된 후 세부장소 정보 다시 얻어오기
const callPlace = async () => {
  await findRoutePlace(route.params.route_id, ({ data }) => {
    myPlaceList.value = data;
    if (myPlaceList.value.length == 0) {
      myPlaceList.value = [];
    }
    placePriority.value = myPlaceList.value.length + 1;
    console.log("리뷰 등록후 세부장소 다시 얻어왔어^^ ", myPlaceList.value);
  });
};
</script>

<template>
  <div class="white-bg" v-if="isModalOpen">
    <div class="select-box">
      <div class="undobtn">
        <img
          src="@/assets/undo.png"
          class="close"
          @click="isModalOpen = false"
          alt="undo"
          width="30"
          height="30"
        />
      </div>
      <div class="select-title">
        <h4>장소를 선택해주세요</h4>
      </div>
      <div class="selected-content">
        <div class="selected-item">
          <div>{{ selected.spot_name }}</div>
          <div>{{ selected.address }}</div>
        </div>
        <div class="selected-btn">
          <button class="sbtn" @click="registPlace">등록</button>
        </div>
      </div>
      <div class="select-content">
        <div
          class="select-item"
          v-for="place in placeList"
          :key="place"
          @click="focusPos(place)"
          id="selectedPlace"
        >
          <div class="place-info-name">{{ place.spot_name }}</div>
          <div class="place-info-address">{{ place.address }}</div>
        </div>
        <div class="again" @click="findAgain()">
          등록하려는 장소가 없으신가요?
          <div class="reselect-content">
            <div
              class="select-item"
              v-for="place in rePlace"
              :key="place"
              @click="focusPos(place)"
              id="selectedPlace"
            >
              <div class="place-info-name">{{ place.spot_name }}</div>
              <div class="place-info-address">{{ place.address }}</div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
  <div class="place-search">
    <div class="place-name">
      <input type="text" class="place" v-model="placeInput" />
    </div>
    <div class="place-btn">
      <button
        style="border: 0; background: #fa644a; font: bold; color: white"
        class="search"
        type="submit"
        @click="findInfos(), (isModalOpen = true)"
      >
        검색
      </button>
    </div>
  </div>

  <div id="map"></div>
  <AddRouteList
    :placeList="myPlaceList"
    @sendPlace="sendPlace"
    @callPlace="callPlace"
  ></AddRouteList>
</template>

<style scoped>
#map {
  width: 1220px;
  height: 800px;
  border: 0;
}
@media (min-width: 1024px) {
  .place-search {
    position: absolute;
    top: 50px;
    left: 43%;
    z-index: 10;
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  .place {
    border-radius: 5px;
    height: 50px;
    line-height: normal;
    color: #282828;
    display: block;
    width: 300px;
    box-sizing: border-box;
    user-select: auto;
    font-size: 16px;
    padding: 0 6px;
    padding-left: 12px;
    border: 3px solid #000;
    :focus {
      border: 3px solid #5551ff;
    }
  }

  .search {
    font-size: 20px;
    width: 80px;
    height: 50px;
  }

  .white-bg {
    position: absolute;
    margin-top: 10px;
    margin-left: 10px;
    height: 790px;
    width: 350px;
    background: #ffffff;
    z-index: 15;
    border: 0;
    border-radius: 15px;
    display: flex;
    justify-content: center;
    background-color: #ffffff;
    box-shadow: 0 0 0.375rem 0.0625rem rgb(46, 46, 46);
    /* overflow-y: auto; */
    overflow-y: scroll;
  }
  .white-bg::-webkit-scrollbar {
    display: none;
  }
  .again {
    margin-top: 15%;
    font-size: large;
    color: #5551ff;
  }
  .reselect-content {
    text-decoration: none;
  }

  .select-box {
    z-index: 20;
    padding: 5px;
  }
  .undobtn {
    display: flex;
    justify-content: right;
    margin-top: 5px;
    margin-right: 5px;
  }
  .select-title {
    margin-top: 5px;
    padding-bottom: 10px;
    box-shadow: 0 4px 4px -4px black;
  }
  .select-content {
    margin-top: 10px;
    margin-left: 10px;
    margin-right: 10px;
  }
  .select-item {
    display: flex;
    flex-direction: column;
    align-items: start;
    font-size: 18px;

    outline: none;
    cursor: pointer;
    line-height: 1;
    border-radius: 5px;
    transition-property: background-color, border-color, color, box-shadow, filter;
    transition-duration: 0.3s;
    border: 1px transparent;
    letter-spacing: 2px;
    min-width: 160px;
    text-transform: uppercase;
    white-space: normal;
    font-weight: 700;
    text-align: center;
    padding: 16px 14px 18px;
    color: #616467;
    box-shadow: inset 0 0 0 2px #616467;
    background-color: transparent;
    margin-bottom: 5px;
  }
  .select-item:hover {
    color: #fff;
    background-color: #616467;
  }
  .selected-content {
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 10px;
  }
  .selected-item {
    display: flex;
    flex-direction: column;
    margin-right: 10px;
    font-size: 20px;
    color: #fa644a;
  }
  .selected-btn {
    display: flex;
    align-items: center;
  }
  .sbtn {
    display: inline-block;
    outline: 0;
    cursor: pointer;
    border: none;
    padding: 0 15px;
    height: 45px;
    line-height: 45px;
    border-radius: 7px;
    background-color: #0070f3;
    color: white;
    font-weight: 400;
    font-size: 16px;
    box-shadow: 0 4px 14px 0 rgb(0 118 255 / 39%);
    transition: background 0.2s ease, color 0.2s ease, box-shadow 0.2s ease;
  }
  .sbtn:hover {
    background: rgba(0, 118, 255, 0.9);
    box-shadow: 0 6px 20px rgb(0 118 255 / 23%);
  }
}
</style>
