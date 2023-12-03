<script setup>
import RouteList from "./RouteList.vue";
import MyRouteList from "./myRouteList.vue";
</script>

<template>
  <input type="text" class="searchbtn" placeholder="루트를 찾아보세요" />
  <MyRouteList></MyRouteList>
  <RouteList></RouteList>
</template>

<style scoped>
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
  width: 50%;
  height: 40px;
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
</style>
