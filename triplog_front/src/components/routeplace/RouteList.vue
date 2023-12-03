<script setup>
import { ref, onMounted, toRef } from "vue";
import { listRoute } from "@/api/route";

const props = defineProps({
  routeList:Array
})
const routeList = toRef(props, "routeList");
const emit = defineEmits(["searchByName"]);

onMounted(() => {
  // getRouteList();
});

const searchName = ref("");
const searchRoute = () => {
  emit("searchByName", 1, searchName.value);
}
const searchKey = (keyword) => {
  emit("searchByName", 2, keyword);
}
const searchCity = (city_name) => {
  emit("searchByName", 3, city_name);
}
</script>

<template>
  <div class="route-container">
    <div class="route-title">
      <span class="title">Route List</span>
    </div>
    <div class="route-search">
      <input type="text" class="searchbtn" v-model="searchName"  placeholder="루트 제목을 입력하세요" @keyup.enter="searchRoute" />
    </div>
    <div class="route-content">
      <div class="route-item" v-for="(item, index) in routeList" :key="item.route_name">
        <div class="keycity-content">
          <div class="city-item" @click="searchCity(item.city_name)">{{ item.city_name  }}</div>
          <div class="key-item" @click="searchKey(item.keyword)">{{ item.keyword  }}</div>
        </div>
        <div><img :src="item.img_url" alt="none image" width="200" height="200" /></div>
        <div class="route-name">
          <span>{{ item.route_name }}</span>
        </div>
        <div class="place-item">
          <router-link class="placebtn" :to="{ name: 'route-place' , params:{route_id: item.route_id}}">보러가기</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.title {
  /* font-family: "Luckiest Guy"; */
  font-size: 30px;
  font-weight: bold;
}
.route-title {
  width: 20%;
  height: 10%;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px;
  /* box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px; */
  padding-top: 5px;
  padding-bottom: 10px;
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
.route-name{
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
  transition: background 0.2s ease,color 0.2s ease,box-shadow 0.2s ease;
  text-decoration: none;
                
            
}
.placebtn:hover {
  background: rgba(0,118,255,0.9);
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
  background-image: linear-gradient(180deg,#fff,#f5f5fa);
  box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
  transition: all .2s ease-out;
  margin-right: 50px;
  margin-bottom: 10px;
                
}
.key-item:hover, .city-item:hover{
  box-shadow: 0 8px 22px 0 rgb(37 44 97 / 15%), 0 4px 6px 0 rgb(93 100 148 / 20%);
}
.city-item{
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
  background-image: linear-gradient(180deg,#fff,#f5f5fa);
  box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
  transition: all .2s ease-out;
  margin-left: 50px;
  margin-bottom: 10px;
}
.searchbtn {
  box-sizing: border-box;
  font-family: inherit;
  font-size: 20px;
  vertical-align: baseline;
  font-weight: 400;
  line-height: 1.29;
  letter-spacing: 0.16px;
  border-radius: 0;
  outline: 2px solid transparent;
  outline-offset: -2px;
  width: 500px;
  height: 50px;
  border: none;
  border-bottom: 1px solid #8d8d8d;
  background-color: #f4f4f4;
  padding: 0 16px;
  color: #161616;
  transition: background-color 70ms cubic-bezier(0.2, 0, 0.38, 0.9),
    outline 70ms cubic-bezier(0.2, 0, 0.38, 0.9);
  :focus {
    outline: 2px solid #0f62fe;
    outline-offset: -2px;
  }
  margin-bottom: 3%;
}
.route-search {
  margin-top: 20px;
}
</style>
