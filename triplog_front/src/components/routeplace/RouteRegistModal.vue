<script setup>
import { ref, onMounted } from "vue";
import { registRoute } from "@/api/route";
import { useRouteStore } from "@/stores/counter";
import { storeToRefs } from "pinia";
const props = defineProps({
  isOpenModal: Boolean,
});

const routeStore = useRouteStore();
const { cities, keywordList } = storeToRefs(routeStore);
const { getCities, getKeyword } = routeStore;

const sequence = ref(0);

const userInfo = ref({});

const route = ref({
  uid: 0,
  city_id: 0,
  key_id: 0,
  route_name: "",
  route_day: "",
  image: "",
});
const routeName = ref("");
const keyword = ref("");

const emit = defineEmits(["isClose"]);
// 모달창 닫기
const close = (event) => {
  if (event.target.classList.contains("black-bg") || event.target.classList.contains("close")) {
    emit("isClose", true);
  } else if (event.target.classList.contains("white-bg")) {
    emit("isClose", false);
  }
};

onMounted(async () => {
  await getCities();
  await getKeyword();
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  // console.log(cities.value);
  // console.log(keywordList.value);
});

// 선택된 도시 저장 및 이름 반환
const selectCity = (param) => {
  route.value.city_id = param;
  routeName.value = cities.value[param - 1].city_name;
  console.log(route.value.city_id);
};
// 선택된 키워드 및 이름 반환
const selectKeyword = (param) => {
  route.value.key_id = param;
  keyword.value = keywordList.value[param - 1].keyword;
  console.log(route.value.key_id);
};

// 이미지 업로드
const photoupload = (e) => {
  let file = e.target.files[0];
  route.value.image = file;
  console.log(route.value.image);
};

const regist = () => {
  ////////////////////수정중////////////////////////////////////////
  // 사용자 uid 얻어오기 - cookie(jwt token)에서
  route.value.uid = userInfo.value.uid;
  /////////////////////////////////////////////////////////////////
  console.log("루트등록하자!!", route.value);

  // API 호출
  registRoute(route.value, route.value);
  alert("루트 등록이 완료되었습니다!!!");
  emit("isClose", true);
};
const next = () => {
  if (sequence.value == 0) {
    if (route.value.route_name.length == 0) {
      alert("내용을 입력하세요!!!");
    } else {
      sequence.value++;
    }
  } else {
    sequence.value++;
  }
};
</script>

<template>
  <div class="black-bg" v-if="isOpenModal">
    <div class="white-bg">
      <div class="undobtn">
        <img
          src="@/assets/undo.png"
          class="close"
          @click="close"
          alt="undo"
          width="30"
          height="30"
        />
      </div>
      <div v-if="sequence == 0">
        <div class="inputTitle">
          <h4>루트 이름을 작성하세요</h4>
        </div>
        <!-- <div class="inputContent">
          <p>여행하다가 마음에 드는 장소를 루트에 등록해보세요!</p>
        </div> -->
        <div>
          <input class="inputbox" type="text" v-model="route.route_name" />
        </div>
      </div>
      <div v-if="sequence == 1">
        <div class="inputTitle">
          <h4>날짜를 선택해주세요</h4>
        </div>
        <div>
          <input class="inputbox" type="date" v-model="route.route_day" />
        </div>
      </div>
      <div v-if="sequence == 2">
        <div class="inputTitle">
          <h4>지역을 선택해주세요</h4>
          <div class="selectedCity">{{ routeName }}</div>
        </div>
        <table class="select-box">
          <tr class="select-item" v-for="(item, i) in 3" :key="i">
            <td v-for="(item, j) in 3" :key="j">
              <button class="radiotag-city" @click="selectCity(i * 3 + j + 1)">
                <label>{{ cities[i * 3 + j].city_name }}</label>
              </button>
            </td>
          </tr>
        </table>
      </div>
      <div v-if="sequence == 3">
        <div class="inputTitle">
          <h4>키워드를 선택해주세요</h4>
          <div class="selectedCity">{{ keyword }}</div>
        </div>
        <table class="select-box">
          <tr class="select-item" v-for="(item, i) in 3" :key="i">
            <td v-for="(item, j) in 3" :key="j">
              <button class="radiotag-keyword" @click="selectKeyword(i * 3 + j + 1)">
                <label>{{ keywordList[i * 3 + j].keyword }}</label>
              </button>
            </td>
          </tr>
        </table>
      </div>
      <div v-if="sequence == 4">
        <div class="inputTitle">
          <h4>썸네일 이미지를 업로드 해주세요</h4>
        </div>
        <div>
          <input
            class="inputbox"
            type="file"
            id="saveImage"
            ref="saveImage"
            @change="photoupload"
          />
        </div>
      </div>
      <div class="routebtn">
        <button class="prebtn" v-show="sequence >= 1" @click="sequence -= 1">이전</button>
        <button class="nextbtn" v-show="sequence <= 3" @click="next()">다음</button>
        <button class="registbtn" v-show="sequence == 4" @click="regist">등록</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@media (min-width: 1024px) {
  .black-bg {
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    position: fixed;
    top: 0;
    left: 0;
  }
  .white-bg {
    position: relative;
    max-width: 60%;
    height: 700px;
    margin: 80px auto;
    background: #e0eafc; /* fallback for old browsers */
    background: -webkit-linear-gradient(
      to right,
      #cfdef3,
      #e0eafc
    ); /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(
      to right,
      #cfdef3,
      #e0eafc
    ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */

    border-radius: 5px;
    padding: 20px 0;
  }
  .undobtn {
    display: flex;
    justify-content: right;
    margin-right: 15px;
  }
  h4 {
    font-family: "NPSfontBold";
  }
  .inputTitle {
    margin-top: 100px;
    margin-bottom: 30px;
  }
  .inputContent {
    color: gray;
    margin-bottom: 30px;
  }
  .inputbox {
    position: relative;
    cursor: text;
    font-size: 20px;
    line-height: 20px;
    padding: 0 16px;
    height: 48px;
    width: 60%;
    background-color: #fff;
    border: 1px solid #d6d6e7;
    border-radius: 3px;
    color: rgb(35, 38, 59);
    box-shadow: inset 0 1px 4px 0 rgb(119 122 175 / 30%);
    overflow: hidden;
    transition: all 100ms ease-in-out;
    :focus {
      border-color: #3c4fe0;
      box-shadow: 0 1px 0 0 rgb(35 38 59 / 5%);
    }
  }
  .routebtn {
    position: absolute;
    bottom: 30px;
    left: 50%;
    transform: translate(-50%, 0%);
  }
  .nextbtn {
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
    padding: 17px 48px;
    color: #fff;
    background-color: #6297e6;
    height: 48px;
    margin-left: 5px;
  }
  .nextbtn:hover {
    transform: scale(1.04);
    background-color: #4e8ae4;
  }
  .prebtn {
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
    margin-right: 5px;
  }
  .prebtn:hover {
    color: #fff;
    background-color: #616467;
  }
  .registbtn {
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
    padding: 17px 48px;
    color: #fff;
    background-color: #fa6449;
    height: 48px;
    margin-left: 5px;
  }
  .registbtn:hover {
    transform: scale(1.04);
    background-color: #e75c44;
  }
  .select-box {
    position: absolute;
    left: 50%;
    transform: translate(-50%, 0%);
  }
  .radiotag-city {
    display: inline-block;
    outline: 0;
    border: 0;
    cursor: pointer;
    font-weight: 600;
    color: #fff;
    font-size: 14px;
    height: 100px;
    width: 100px;
    padding: 8px 24px;
    border-radius: 50px;
    background-image: linear-gradient(180deg, #7c8aff, #3c4fe0);
    box-shadow: 0 4px 11px 0 rgb(37 44 97 / 15%), 0 1px 3px 0 rgb(93 100 148 / 20%);
    transition: all 0.2s ease-out;
    margin-bottom: 10px;
    margin-right: 10px;
  }
  .radiotag-city:hover {
    box-shadow: 0 8px 22px 0 rgb(37 44 97 / 15%), 0 4px 6px 0 rgb(93 100 148 / 20%);
  }
  .radiotag-keyword {
    display: inline-block;
    outline: 0;
    cursor: pointer;
    border: 2px solid #000;
    border-radius: 3px;
    color: #000;
    background: #fff;
    font-size: 20px;
    font-weight: 600;
    line-height: 28px;
    padding: 12px 20px;
    text-align: center;
    transition-duration: 0.15s;
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    margin-bottom: 10px;
    margin-right: 10px;
  }
  .radiotag-keyword:hover {
    background: rgb(251, 193, 245);
  }
  .selectedCity {
    margin-top: 10px;
    font-family: "NPSfontBold";
    font-size: 22px;
    font-weight: bold;
    height: 50px;
    margin-left: 30%;
    margin-right: 30%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fa6449;
  }
}
</style>
