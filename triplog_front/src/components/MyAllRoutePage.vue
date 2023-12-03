<script setup>
import { ref, onMounted, toRef } from "vue";
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
import { listRoute, listRouteByUser, listAllRouteByUser } from "@/api/route";

// import { listRouteByUser } from "@/api/route";

const routeList = ref([]);
const myRouteList = ref([]);

const userInfo = ref({});
onMounted(async () => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  await listAllRouteByUser(userInfo.value.uid, ({ data }) => {
    myRouteList.value = data;
  });
});
</script>

<template>
  <div class="route-container">
    <div class="route-title">
      <span class="title">My Route</span>
    </div>
    <div v-if="userInfo == null" class="route-content">
      <div class="route-detail">아직 루트가 없어요. 나만의 루트를 만들어 보세요!</div>
    </div>
    <div v-else class="route-content">
      <div class="route-item" v-for="(item, index) in myRouteList" :key="index">
        <div class="keycity-content">
          <div class="city-item">{{ item.city_name }}</div>
          <div class="key-item">{{ item.keyword }}</div>
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
.title {
  font-size: 25px;
  font-weight: bold;
}
.route-title {
  /* font-family: "Luckiest Guy"; */
  width: 20%;
  /* box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px; */
  box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px;
  padding-top: 5px;
  padding-bottom: 5px;
}
.route-container {
  width: 100%;
  padding: 2%;
  border: 0;
  border-radius: 15px;
  box-shadow: 0 0 0.375rem 0.0625rem rgb(194, 194, 194);
  margin-bottom: 5%;
  display: flex;
  flex-direction: column;
}
.route-content {
  margin-top: 5%;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
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
}
.route-name {
  font-size: 20px;
  margin-top: 5px;
  margin-bottom: 5px;
}

.edit {
  margin-top: 5%;
  align-items: center;
  width: 50%;
}
.myRoutesbtn {
  display: inline-block;
  outline: none;
  cursor: pointer;
  font-size: 14px;
  line-height: 1;
  border-radius: 500px;
  transition-property: background-color, border-color, color, box-shadow, filter;
  transition-duration: 0.3s;
  border: 1px solid transparent;
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
  height: 48px;
}
.myRoute-item {
  display: flex;
  justify-content: center;
  margin-top: 3%;
}
.myRoutesbtn:hover {
  color: #fff;
  background-color: #616467;
}
img {
  border-radius: 5px;
  margin-bottom: 5px;
}
.place-item {
  display: flex;
  justify-content: center;
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
.route-detail {
  margin-top: 140px;
  font-size: 20px;
}
</style>
