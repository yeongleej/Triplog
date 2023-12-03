<script setup>
defineProps({
  keyword: String,
  img: Object,
});
</script>

<template>
  <div>{{ keyword }}</div>
  <img class="routes" :src="img.src" alt="Loaded Image" />
</template>

<style scoped>
@media (min-width: 1024px) {
  .routes {
    width: 30%;
    height: 30%;
  }
}
</style>
