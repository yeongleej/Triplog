<script setup>
import { ref, onMounted } from "vue";
import { logout } from "../api/auth";

defineProps({
  msg: {
    type: String,
    required: true,
  },
});
const searchWord = ref("");
const isLogin = ref(false);

const userInfo = ref({});
onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  
  console.log("메인화면 >>", userInfo.value);
  if (userInfo.value != null) {
    isLogin.value = true;
  }

  else {
    const cookies = document.cookie.split(`; `).map((el) => el.split('='));
    var getUid = "";
    var getEmail = "";
    var getNickname = "";

    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i][0] === 'uid') {
        getUid = cookies[i][1];
      }

      if (cookies[i][0] === 'email') {
        getEmail = cookies[i][1];
      }

      if (cookies[i][0] === 'nickname') {
        getNickname = cookies[i][1];
      }
    }
    const oauth = ref({
      uid: "",
      email: "",
      nickname: "",
    })
    
    if (getUid != '' && getNickname != '') {
      oauth.value.uid = getUid;
      oauth.value.email = getEmail;
      oauth.value.nickname = getNickname;

      userInfo.value = oauth.value
      isLogin.value = true
      localStorage.setItem("user", JSON.stringify(oauth.value))
    }
  }
});

const enterWord = () => {
  console.log(searchWord.value);
};

function logoutButton() {
  isLogin.value = false;
  localStorage.clear();
  document.cookie = "uid=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  document.cookie = "email=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  document.cookie = "nickname=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  logout();
}
</script>

<template>
  <div class="main-container">
    <img alt="Main Image" class="mainpic" src="@/assets/mainimage.jpg" />
    <div class="header">
      <div class="item">
        <RouterLink :to="{ name: 'home' }">
          <img alt="Triplog Logo" class="triplog" src="@/assets/logo2.png" width="150" height="150"
        /></RouterLink>
      </div>
      <div class="item">
        <router-link :to="{ name: 'route-regist' }">루트 만들기</router-link>
      </div>
      <div class="item">
        <router-link :to="{ name: 'music' }">음악 찾기</router-link>
      </div>
      <div class="item">
        <router-link :to="{ name: 'board' }">게시판</router-link>
      </div>
      <div class="auth-cotent" v-if="!isLogin">
        <div class="item" id="sign-in"><RouterLink to="/login">sign in</RouterLink></div>
        <div class="item" id="sign-up"><RouterLink to="/register">sign up</RouterLink></div>
      </div>
      <div class="auth-cotent" v-else>
        <div class="item" id="nickname">
          <span>{{ userInfo.nickname }}님</span>
        </div>
        <div class="item" id="sign-in">
          <RouterLink :to="{ name: 'mypage' }">MyPage</RouterLink>
        </div>
        <button class="logout" id="logout" @click="logoutButton">Logout</button>
      </div>
    </div>
    <div class="main-content">
      <span id="main-title">triplog</span>
      <span id="main-description">Find your special trip log</span>
    </div>
    <div class="main-search">
      <input id="main-input" type="text" v-model="searchWord" @keyup.enter="enterWord" />
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Luckiest+Guy&family=Varela+Round&display=swap");
@media (min-width: 1024px) {
  h1 {
    font-weight: 500;
    font-size: 2.6rem;
    position: relative;
    top: -10px;
  }
  .main-container {
    position: relative;
  }

  .mainpic {
    width: 100%;
    height: 60%;
    opacity: 0.9;
    position: relative;
  }
  .header {
    position: absolute;
    width: 100%;
    top: 5%;
    display: flex;
    justify-content: space-around;
    padding-bottom: 20px;
    box-shadow: 0 4px 4px -4px black;
  }
  .item {
    /* align-content: center; */
    margin-left: 1%;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .auth-cotent {
    width: 22%;
    display: flex;
    justify-content: space-around;
  }
  .item > a {
    text-decoration: none;
    color: white;
    font-size: 18px;
    font-family: "EASTARJET-Medium";
  }
  .triplog {
    width: 120px;
    height: 100px;
  }
  .main-content {
    position: absolute;
    width: 100%;
    top: 40%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    color: white;
  }
  #main-title {
    font-size: 40px;
    font-family: "Varela Round", sans-serif;
    font-weight: bold;
  }
  #main-description {
    font-size: 25px;
    font-weight: bold;
    font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  }
  .main-search {
    position: absolute;
    width: 100%;
    top: 55%;
  }
  #main-input {
    width: 300px;
    height: 40px;
    font-size: 15px;
    border: 0;
    border-radius: 15px;
    outline: none;
    padding-left: 10px;
    background-color: rgb(233, 233, 233);
    box-shadow: 0 0 0.375rem 0.0625rem gainsboro;
  }
  #nickname {
    font-size: 20px;
    font-weight: 900;
    color: gainsboro;
  }
  /* 
  .menu {
    width: 80%;
    display: flex;
    justify-content: space-around;
    box-shadow: 0 4px 4px -4px black;
  } */

  .logout {
    background: transparent;
    border: none !important;
    text-decoration: none;
    color: white;
    font-size: 18px;
    font-family: "EASTARJET-Medium";
  }
}
</style>
