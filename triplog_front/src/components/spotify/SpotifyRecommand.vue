<script setup>
import { ref, onMounted } from "vue";
import { findMusicList } from "@/api/spotify.js";

const musicList = ref([]);
const getMusic = async (genre) => {
  console.log(genre);
  await findMusicList(2, genre, ({ data }) => {
    musicList.value = data;
    console.log(musicList.value);
  });
};
const palyPreview = (pUrl) => {
  window.open(pUrl);
};
const gotoSpotify = (eUrl) => {
  window.open(eUrl);
};

const gerneList = ref([
  "acoustic",
  "dance",
  "disney",
  "happy",
  "holidays",
  "k-pop",
  "movies",
  "party",
  "pop",
  "r-n-b",
  "rainy-day",
  "road-trip",
  "romance",
  "summer",
]);
</script>

<template>
  <div class="box">
    <div class="item-title"><span>현재 여행과 어울리는 음악을 추천 받아요!</span></div>
    <div class="item-btn">
      <div class="item-detail" v-for="(item, index) in gerneList" :key="index">
        <button class="registbtn" @click="getMusic(item)">{{ item }}</button>
      </div>
    </div>
  </div>
  <div class="route-container">
    <table>
      <tbody>
        <tr v-for="(item, index) in musicList" :key="item.title">
          <td class="m-left" style="width: 100px">
            <img :src="item.imageUrl" alt="" width="90" height="90" />
          </td>
          <td class="m-left">{{ item.title }}</td>
          <td class="m-left">{{ item.artistName }}</td>
          <td class="m-right">
            {{ item.duration_mm }}:{{ String(item.duration_ss).padStart(2, "0") }}
          </td>
          <td class="m-right" v-if="item.previewUrl != null">
            <img
              src="@/assets/play.png"
              alt=""
              width="30"
              height="30"
              @click="palyPreview(item.previewUrl)"
            />
          </td>
          <td class="m-right" v-else></td>
          <td class="m-right">
            <img
              src="@/assets/spotify_logo.svg"
              alt=""
              width="30"
              height="30"
              @click="gotoSpotify(item.externalUrl)"
            />
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}
.item-title {
  font-size: 25px;
  margin-bottom: 20px;
}
.item-btn {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.registbtn {
  display: inline-block;
  outline: 0;
  border: 0;
  cursor: pointer;
  font-weight: 600;
  color: rgb(72, 76, 122);
  font-size: 18px;
  height: 50px;
  width: 130px;
  padding: 8px 24px;
  border-radius: 50px;
  background-image: linear-gradient(180deg, #fff, #f5f5fa);
  box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
  transition: all 0.2s ease-out;
  margin-left: 15px;
  margin-right: 15px;
  margin-bottom: 10px;
}
.registbtn:hover {
  box-shadow: 0 8px 22px 0 rgb(37 44 97 / 15%), 0 4px 6px 0 rgb(93 100 148 / 20%);
}
.route-container {
  width: 100%;
  padding: 2%;
  border: 0;
  margin-bottom: 5%;
  display: flex;
  flex-direction: column;
}
table {
  border-collapse: collapse;
  width: 100%;
  margin: 0rem auto;
  box-shadow: 4px 4px 10px 0 rgba(0, 0, 0, 0.1);
  background-color: white;
  font-size: 18px;
}

/* 테이블 행 */
th,
td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
  text-align: center;
}
tr {
  height: 80px;
}

/* 테이블 올렸을 때 */
tbody tr:hover {
  background-color: #d3d3d3;
  opacity: 0.9;
  cursor: pointer;
}
.m-left {
  text-align: left;
}
.m-right {
  text-align: right;
}
</style>
