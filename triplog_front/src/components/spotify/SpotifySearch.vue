<script setup>
import { ref, onMounted } from "vue";
import { findMusicList } from "@/api/spotify.js";

const musicList = ref([]);
const searchKeyword = ref("");
const getMusic = async () => {
  console.log(searchKeyword.value);
  await findMusicList(1, searchKeyword.value, ({ data }) => {
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
</script>

<template>
  <div class="box">
    <div class="item-btn">
      <input
        type="text"
        class="searchbtn"
        v-model="searchKeyword"
        placeholder="제목, 가수이름을 검색해보세요"
        @keyup.enter="getMusic"
      />
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
