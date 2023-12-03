<script setup>
import { ref, toRef, onMounted } from "vue";
import ReviewRegistModal from "../routeplace/ReviewRegistModal.vue";
import { showReview, registReview } from "@/api/review";
import ReviewDetailModal from "./ReviewDetailModal.vue";

const props = defineProps({
  placeList: Array,
});

const emit = defineEmits(["sendPlace", "callPlace"]);
const deletePlace = (id, priority) => {
  emit("sendPlace", id, priority);
};

// 플레이스 리스트 얻어오기
const placeList = toRef(props, "placeList");

onMounted(() => {});

const isOpenDetailModal = ref(false);
const isOpenAddModal = ref(false);
const selectedAddPlace = ref(0);
const selectedDetailPlace = ref(0);
const placeDetail = ref({});
const openReviewRegistModal = (pid) => {
  selectedAddPlace.value = pid;
  console.log(selectedAddPlace.value);
  isOpenAddModal.value = true;
};
const openReviewDetailModal = (place) => {
  console.log("작성된 리뷰 열람 ", place);
  // 리뷰 상세 모달창 안열려 있으면 열음
  if (!isOpenDetailModal.value) {
    selectedDetailPlace.value = place.place_id;
    placeDetail.value = place;
    isOpenDetailModal.value = true;
    console.log("리뷰 열어", selectedDetailPlace, placeDetail.value, isOpenDetailModal.value);
  }
  // 이미 열려 있으면 닫음
  else {
    selectedDetailPlace.value = 0;
    isOpenDetailModal.value = false;
  }
};

// const isShowClose = (ans) => {
//   if (ans) {
//     isOpenShowModal.value = false;
//   } else {
//     isOpenShowModal.value = true;
//   }
// };

// const isAddClose = (ans) => {
//   if (ans) {
//     isOpenAddModal.value = false;
//   } else {
//     isOpenAddModal.value = true;
//   }
// };

const url = ref("");
const reviewObj = ref({});
const imgList = ref([]);

function placeReview() {
  showReview(localStorage.getItem("reviewId"), ({ data }) => {
    reviewObj.value = data;
    imgList.value = reviewObj.value.imageList;
    console.log(imgList.value);
  });
}

const newReview = ref({});
const addReview = async (reviewText, reviewImgs) => {
  newReview.value = {
    place_id: selectedAddPlace.value,
    review_imgs: reviewImgs,
    review_text: reviewText,
  };
  console.log(newReview.value);
  await registReview(newReview.value);
  selectedAddPlace.value = 0;
  isOpenAddModal.value = false;
  alert("리뷰 등록이 완료되었습니다!");
  emit("callPlace");
};
</script>

<template>
  <div class="route-container" v-for="(place, index) in placeList" :key="place.address">
    <div class="place-content">
      <div class="route-title">
        <button class="title" @click="placeReview()">Place {{ place.priority }}</button>
      </div>
      <div class="route-content">
        <div class="route-item">
          <div class="place-item">{{ place.placeName }}</div>
          <div class="place-item">( {{ place.address }} )</div>
        </div>
      </div>
      <div class="route-btn">
        <div class="review-btn" v-if="place.review_id == null">
          <button class="addReview" @click="openReviewRegistModal(place.place_id)">
            리뷰 작성
          </button>
        </div>
        <div class="review-btn" v-else>
          <div @click="openReviewDetailModal(place)">
            <img src="@/assets/review.png" width="30" height="30" />
          </div>
        </div>
        <div @click="deletePlace(place.place_id, place.priority)">
          <img src="@/assets/delete-place.png" width="25" height="25" />
        </div>
      </div>
    </div>
    <ReviewRegistModal
      v-if="selectedAddPlace == place.place_id && isOpenAddModal"
      @submitReview="addReview"
    ></ReviewRegistModal>
    <ReviewDetailModal
      :placeDetail="placeDetail"
      v-if="selectedDetailPlace == place.place_id && isOpenDetailModal"
    ></ReviewDetailModal>
  </div>
  <div class="myRoute-item">
    <router-link :to="{ name: 'route-regist' }" class="myRoutesbtn">목록으로</router-link>
  </div>
</template>

<style scoped>
.title {
  font-size: 25px;
  font-weight: bold;
  background-color: rgba(0, 0, 0, 0);
  border-color: rgba(0, 0, 0, 0);
}
.route-title {
  /* font-family: "Luckiest Guy"; */
  width: 20%;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px;
  /* box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px; */
  padding-top: 5px;
  padding-bottom: 5px;
}
.route-container {
  width: 100%;
  padding: 2%;
  border: 0;
  border-radius: 15px;
  box-shadow: 0 0 0.375rem 0.0625rem rgb(194, 194, 194);
  margin-top: 10px;
  margin-bottom: 5%;
  display: flex;
  flex-direction: column;
}
.place-content {
  display: flex;
  flex-direction: row;
}
.route-content {
  display: flex;
  align-items: center;
  width: 850px;
}
.route-item {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-left: 20px;
}
.place-item {
  font-size: 20px;
  margin-right: 10px;
}
.route-btn {
  display: flex;
  align-items: center;
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
.addReview {
  display: inline-block;
  outline: 0;
  border: 0;
  cursor: pointer;
  transition: box-shadow 0.15s ease, transform 0.15s ease;
  will-change: box-shadow, transform;
  background: #fcfcfd;
  box-shadow: 0px 2px 4px rgb(45 35 66 / 40%), 0px 7px 13px -3px rgb(45 35 66 / 30%),
    inset 0px -3px 0px #d6d6e7;
  height: 48px;
  width: 100px;
  padding: 0 5px;
  font-size: 18px;
  border-radius: 6px;
  color: #36395a;
  transition: box-shadow 0.15s ease, transform 0.15s ease;
}
.addReview:hover {
  box-shadow: 0px 4px 8px rgb(45 35 66 / 40%), 0px 7px 13px -3px rgb(45 35 66 / 30%),
    inset 0px -3px 0px #d6d6e7;
  transform: translateY(-2px);
}
.addReview:active {
  box-shadow: inset 0px 3px 7px #d6d6e7;
  transform: translateY(2px);
}
.review-btn {
  margin-right: 10px;
}
</style>
