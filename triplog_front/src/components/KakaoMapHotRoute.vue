<script setup>
import { ref, onMounted, onUpdated, toRef } from "vue";
import { listRouteByKeyNameTop3 } from "@/api/route";
import { findRoutePlace } from "@/api/routePlace";
const props = defineProps({
  keyword: String,
});

const keyword = toRef(props, "keyword");
const routeList = ref([]);
const placeList1 = ref([]);
const placeList2 = ref([]);
const placeList3 = ref([]);

var marker;
var markers = [];
// var polyline;
var polylineList = [];
let imageSrc = "https://mytriplog.s3.us-west-2.amazonaws.com/route/ticjcicons8.png";
// let linePath = [];
var lineList = [];

const script = document.createElement("script");
script.type = "text/javascript";
script.src =
  "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=c4a74963fd48fc7faf83bc29cd7e7647&libraries=services";

function sleep(sec) {
  return new Promise((resolve) => setTimeout(resolve, sec * 1000));
}

onUpdated(async () => {
  await listRouteByKeyNameTop3(keyword.value, ({ data }) => {
    routeList.value = data;
    console.log("3개의 루트:", routeList.value);
  });

  await sleep(2);
});

onMounted(async () => {
  await listRouteByKeyNameTop3("힐링", ({ data }) => {
    routeList.value = data;
    console.log("기본(힐링) 3개의 루트:", routeList.value);
  });
  script.addEventListener("load", () => {
    kakao.maps.load(() => {
      // 카카오맵 API가 로딩이 완료된 후 지도의 기본적인 세팅을 시작해야 한다.
      console.log("First Call");
      initMap();
    });
  });
  document.head.appendChild(script);
});

const initMap = () => {
  // map1
  var mapContainer1 = document.getElementById("map1");
  var mapOption1 = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567),
    level: 5,
  };
  var map1 = new kakao.maps.Map(mapContainer1, mapOption1);

  //map2
  var mapContainer2 = document.getElementById("map2");
  var mapOption2 = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567),
    level: 5,
  };
  var map2 = new kakao.maps.Map(mapContainer2, mapOption2);

  //map3
  var mapContainer3 = document.getElementById("map3");
  var mapOption3 = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567),
    level: 5,
  };
  var map3 = new kakao.maps.Map(mapContainer3, mapOption3);
};
</script>

<template>
  <div class="route-container">
    <div class="route-content">
      <div class="route-item" v-for="(item, index) in routeList" :key="item.route_name">
        <div class="keycity-content">
          <div class="city-item" @click="searchCity(item.city_name)">{{ item.city_name }}</div>
          <div class="key-item" @click="searchKey(item.keyword)">{{ item.keyword }}</div>
        </div>
        <div><img :src="item.img_url" alt="none image" width="200" height="200" /></div>
        <div class="route-name">
          <span>{{ item.route_name }}</span>
        </div>
        <div class="place-item">
          <router-link
            class="placebtn"
            :to="{ name: 'route-place', params: { route_id: item.route_id } }"
            >보러가기</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.map-card {
  position: relative;
  width: 340px;
  height: 500px;
  border: 0;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  background-color: rgb(233, 233, 233);
  box-shadow: 0 0 0.375rem 0.0625rem rgb(46, 46, 46);
}
#map1 {
  width: 300px;
  height: 400px;
  border: 0;
  border-radius: 15px;
}
#map2 {
  width: 300px;
  height: 400px;
  border: 0;
  border-radius: 15px;
}
#map3 {
  width: 300px;
  height: 400px;
  border: 0;
  border-radius: 15px;
}
.route-map {
  margin-top: 5%;
  display: flex;
  justify-content: space-around;
}

.route-container {
  width: 100%;
  border: 0;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 4px 4px -4px rgb(194, 194, 194);
  margin-bottom: 5%;
  padding-bottom: 10%;
}
.route-content {
  margin-top: 5%;
  display: flex;
  justify-content: left;
  flex-wrap: wrap;
  margin-left: 5%;
}
.route-item {
  /* position: relative; */
  width: 340px;
  height: 350px;
  border: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 15px;
  margin-right: 45px;
  margin-bottom: 20px;
}
.route-name {
  font-size: 20px;
  margin-top: 5px;
  margin-bottom: 5px;
}
img {
  border-radius: 5px;
  margin-bottom: 5px;
}
.placebtn {
  outline: 0;
  display: inline-block;
  cursor: pointer;
  border: none;
  padding: 0 10px;
  height: 45px;
  line-height: 45px;
  border-radius: 7px;
  background-color: #0070f3;
  color: white;
  font-weight: 400;
  font-size: 16px;
  box-shadow: 0 4px 14px 0 rgb(0 118 255 / 39%);
  transition: background 0.2s ease, color 0.2s ease, box-shadow 0.2s ease;
  text-decoration: none;
}
.placebtn:hover {
  background: rgba(0, 118, 255, 0.9);
  box-shadow: 0 6px 20px rgb(0 118 255 / 23%);
}
.keycity-content {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.key-item {
  display: inline-block;
  outline: 0;
  border: 0;
  cursor: pointer;
  font-weight: 600;
  color: rgb(72, 76, 122);
  font-size: 18px;
  height: 38px;
  padding: 8px 24px;
  border-radius: 50px;
  background-image: linear-gradient(180deg, #fff, #f5f5fa);
  box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
  transition: all 0.2s ease-out;
  margin-right: 50px;
  margin-bottom: 10px;
}
.key-item:hover,
.city-item:hover {
  box-shadow: 0 8px 22px 0 rgb(37 44 97 / 15%), 0 4px 6px 0 rgb(93 100 148 / 20%);
}
.city-item {
  display: inline-block;
  outline: 0;
  border: 0;
  cursor: pointer;
  font-weight: 600;
  color: rgb(72, 76, 122);
  font-size: 18px;
  height: 38px;
  padding: 8px 24px;
  border-radius: 50px;
  background-image: linear-gradient(180deg, #fff, #f5f5fa);
  box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
  transition: all 0.2s ease-out;
  margin-left: 50px;
  margin-bottom: 10px;
}
</style>
