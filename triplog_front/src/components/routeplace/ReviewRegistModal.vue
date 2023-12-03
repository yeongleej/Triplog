<script setup>
import { ref, onMounted } from "vue";

const userInfo = ref({});
onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
});
const reviewText = ref("");
const reviewImgs = ref("");

const review = ref({
  place_id: 5,
  review_text: "",
  review_imgs: [],
});

// 이미지 업로드
const photoupload = (e) => {
  // let file = e.target.files[0];
  // route.value.image = file;
  // console.log(route.value.image);
};

const handleAddImages = (event) => {
  reviewImgs.value = event.target.files[0];
  console.log(reviewImgs.value);
};

const emit = defineEmits(["submitReview"]);
const submitReview = () => {
  emit("submitReview", reviewText.value, reviewImgs.value);
};
</script>

<template>
  <div class="review-container">
    <div class="inputTitle">
      <h4>리뷰를 작성해보세요!</h4>
    </div>
    <div class="review-text">
      <textarea v-model="reviewText" class="inputbox" rows="8" cols="80"></textarea>
    </div>
    <div class="review-item">
      <div class="review-imgs">
        <input
          class="inputbox"
          type="file"
          id="saveImage"
          ref="saveImage"
          @change="handleAddImages"
        />
      </div>
      <div class="review-btn-cotent">
        <button class="review-btn" @click="submitReview">등록</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.review-container {
  margin-top: 30px;
}
textarea {
  font-size: 20px;
}
.review-item {
  display: flex;
  justify-content: space-around;
}
.review-imgs {
  margin-left: -60px;
}
.review-btn {
  display: inline-block;
  outline: 0;
  appearance: none;
  padding: 0px 12px;
  border-radius: 4px;
  text-decoration: none;
  cursor: pointer;
  background-color: rgb(249, 251, 250);
  border: 1px solid rgb(137, 151, 155);
  box-shadow: rgb(6 22 33 / 30%) 0px 1px 2px;
  color: rgb(61, 79, 88);
  font-size: 14px;
  font-weight: 400;
  height: 36px;
  transition: all 150ms ease-in-out 0s;
  margin-right: -100px;
}
.review-btn:hover {
  color: rgb(61, 79, 88);
  background-color: rgb(255, 255, 255);
  border: 1px solid rgb(93, 108, 116);
  box-shadow: rgb(0 0 0 / 30%) 0px 4px 4px, rgb(231 238 236) 0px 0px 0px 3px;
}
</style>
