<script setup>
import RoutePlaceRegistPage from "../components/routeplace/RoutePlaceRegistPage.vue";
</script>

<template>
  <main>
    <RoutePlaceRegistPage></RoutePlaceRegistPage>
  </main>
</template>

<style scoped>
@media (min-width: 1024px) {
  .place {
    position: fixed;
    top: 10%;
    display: inline-flex;
    width: 20%;
    float: left;
    z-index: 1;
  }

  .search {
    border-radius: 7%;
    display: inline-flex;
    float: left;
  }
}
</style>
