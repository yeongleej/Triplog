<script setup>
import { ref, onMounted } from "vue";
import { logout } from "@/api/auth";

const isLogin = ref(false);
const userInfo = ref({});
onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  if (userInfo.value != null) {
    isLogin.value = true;
  }

  else {
    const cookies = document.cookie.split(`; `).map((el) => el.split('='));
    let getItem = [];

    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i][0] === "nickname") {
        getItem.push(cookies[i][1]);
        break;
      }
    }

    if (getItem.length > 0) {
      userInfo.value = getItem;
      isLogin.value = true;
    }
  }
});

function logoutButton() {
  isLogin.value = false;
  userInfo.value = null;
  localStorage.clear();
  logout();
}
</script>

<template>
  <div class="header">
    <div class="item">
      <RouterLink :to="{ name: 'home' }">
        <img alt="Triplog Logo" class="triplog" src="@/assets/logo3.png" width="230" height="95"
      /></RouterLink>
    </div>
    <div class="item"></div>
    <div class="item"></div>
    <div class="item">
      <router-link :to="{ name: 'route-regist' }">루트 만들기</router-link>
    </div>
    <div class="item">
      <router-link :to="{ name: 'music' }">음악 찾기</router-link>
    </div>
    <div class="item">
      <router-link :to="{ name: 'board' }">게시판</router-link>
    </div>
    <div class="auth-content" v-if="!isLogin">
      <div class="item">
        <router-link class="login" to="/login" style="color: #fa644a; height: 100%"
          >Sign In</router-link
        >
      </div>
    </div>
    <div class="auth-content" v-else>
      <div class="item" id="name">
        <span>{{ userInfo.nickname }}님</span>
      </div>
      <div class="item">
        <router-link class="login" :to="{ name: 'mypage' }" style="color: #fa644a; height: 100%"
          >MyPage</router-link
        >
      </div>
      <div class="item">
        <div
          class="login"
          style="color: #fa644a; height: 100%; font-size: 20px"
          id="logout"
          @click="logoutButton"
        >
          Logout
        </div>
        <!-- <router-link class="login" to="#" style="color: #fa644a; height: 100%">Logout</router-link> -->
      </div>
    </div>
  </div>
  <RouterView />
</template>

<style scoped>
@media (min-width: 1024px) {
  .header {
    display: flex;
    align-items: center;
    justify-content: space-around;
    height: 180px;
    border: 0;
    border-radius: 10px;
    box-shadow: 0 0 0.375rem 0.0625rem rgb(194, 194, 194);
    margin-bottom: 2%;
    margin-top: 1%;
  }
  .item {
    flex-direction: column;
    justify-content: center;
  }
  .auth-content {
    width: 22%;
    display: flex;
    justify-content: space-around;
  }
  .item > a {
    text-decoration: none;
    font-size: 20px;
  }
  #name {
    margin-left: 3%;
    font-size: 20px;
    font-weight: bolder;
  }

  .menu {
    max-width: 1280;
    margin-left: 9%;
    margin-right: 9%;
    margin-bottom: 3%;
    padding-bottom: 1%;
    padding-top: 15px;
    display: flex;
    justify-content: space-around;
    /* box-shadow: 0 4px 4px -4px black; */
    /* background-color: #dae5f6; */
    box-shadow: 0 0 0.375rem 0.0625rem rgb(194, 194, 194);
  }
  .menu-item > a {
    text-decoration: none;
    font-size: 20px;
    font-weight: bold;
    color: black;
  }
}
</style>
