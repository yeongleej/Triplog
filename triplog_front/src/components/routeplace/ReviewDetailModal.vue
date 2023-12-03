<script setup>
const props = defineProps({
  placeDetail: Object,
});
</script>

<template>
  <div class="review-container">
    <div class="review-header">
      <h4>Review</h4>
    </div>
    <div class="review-content">
      <div v-for="(item, index) in placeDetail.imageList" :key="index">
        <img :src="item.img_url" width="200" height="200" />
      </div>
      <div class="review-text">{{ placeDetail.review_text }}</div>
    </div>
  </div>
</template>

<style scoped>
.review-container {
  display: flex;
  flex-direction: column;
}
.review-header {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  box-shadow: 0 4px 4px -4px rgb(194, 194, 194);
}
.review-content {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.review-text {
  margin-top: 20px;
  font-size: 25px;
}
</style>
