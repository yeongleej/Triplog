<script setup>
import { ref, onMounted } from "vue";
import { RouterLink, RouterView } from "vue-router";
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
import MainPage from "../components/MainPage.vue";
import KakaoMapHotRoute from "../components/KakaoMapHotRoute.vue";
import { listRouteByKeyNameTop3 } from "@/api/route";

const isSelected = ref(false);
const routeList = ref([]);

const keywordList = ref([
  "힐링",
  "액티비티",
  "맛집",
  "가족",
  "포토존",
  "커플",
  "우정",
  "나홀로",
  "계절",
]);
const selectedKeyword = ref("힐링");

onMounted(async () => {
  await listRouteByKeyNameTop3(selectedKeyword.value, ({ data }) => {
    routeList.value = data;
    console.log("3개의 루트:", routeList.value);
  });
});

// const imgUrl = ref([]);
const loadMapView = async (key) => {
  // imgUrl.value = [];
  selectedKeyword.value = key;
  await listRouteByKeyNameTop3(selectedKeyword.value, ({ data }) => {
    routeList.value = data;
    console.log("3개의 루트:", routeList.value);
  });
};
</script>

<template>
  <main>
    <!-- <TheHeadingNavbar></TheHeadingNavbar> -->
    <div>
      <MainPage />
    </div>

    <p class="hot">Popular Route</p>
    <div class="keyword-container">
      <div class="keyword" v-for="(item, index) in keywordList" :key="index">
        <button class="word" @click="loadMapView(item)">{{ item }}</button>
      </div>
    </div>
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
  </main>
</template>

<style scoped>
@media (min-width: 1024px) {
  .login {
    height: 10px;
    display: inline-flex;
    align-items: center;
    overflow: hidden;
  }

  .hot {
    display: flex;
    justify-content: left;
    margin-top: 10%;
    font-size: 200%;
    margin-left: 5%;
    margin-bottom: 5%;
    width: 20%;
    border-radius: 5px;
    padding-top: 5px;
    padding-bottom: 5px;
    box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
    font-size: 30px;
    font-weight: bold;
  }
  .keyword-container {
    display: flex;
    flex-direction: row;
    margin-left: 10px;
    justify-content: space-around;
  }

  .keyword {
    display: flex;
    align-items: center;
  }

  .word {
    display: inline-block;
    outline: 0;
    cursor: pointer;
    border: 2px solid #000;
    border-radius: 3px;
    color: #000;
    background: #bde0fe;
    font-size: 20px;
    font-weight: 600;
    line-height: 28px;
    padding: 12px 20px;
    text-align: center;
    transition-duration: 0.15s;
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  }
  .word:hover {
    background: rgb(251, 193, 245);
  }

  .route {
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin-top: 7%;
  }

  .overview {
    align-content: center;
    border: 0cap;
    border-radius: 5%;
    background-color: lightgray;
    font-family: "Luckiest Guy";
    font-size: large;
    width: 20cap;
    height: 30cap;
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
}
</style>
