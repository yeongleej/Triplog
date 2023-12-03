<script setup>
import { ref } from "vue";
import { login } from "../api/auth";

const idErrorMsg = ref("");
const pwErrorMsg = ref("");

const loginForm = ref({
  email: "",
  pw: "",
});

function onSubmit() {
  if (idErrorMsg.value) {
    alert(idErrorMsg.value);
  } else if (pwErrorMsg.value) {
    alert(pwErrorMsg.value);
  } else {
    console.log(loginForm.value);
    login(loginForm.value);
  }
}
</script>

<template>
  <div>
    <a href="/"
      ><img alt="Triplog Logo" class="triplog" src="@/assets/triplogm.svg" width="250" height="250"
    /></a>
    <p style="color: black; font-size: xxx-large">Login</p>

    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <input
          type="text"
          placeholder="E-MAIL"
          id="email"
          v-model="loginForm.email"
          style="
            border: none;
            text-align: center;
            font-size: x-large;
            margin-top: 5%;
            color: lightgray;
          "
        />
        <hr style="color: black; width: 30%; border-top: 2px solid #000000; margin-left: 35%" />
      </div>
      <div class="form-group">
        <input
          type="text"
          placeholder="PASSWORD"
          id="pw"
          v-model="loginForm.pw"
          style="
            border: none;
            text-align: center;
            font-size: x-large;
            margin-top: 2%;
            color: lightgray;
          "
        />
        <hr style="color: black; width: 30%; border-top: 2px solid #000000; margin-left: 35%" />
        <RouterLink :to="{ name: 'register' }">아직 triplog 회원이 아니신가요?</RouterLink>
      </div>
      <button
        style="
          border: 0;
          text-align: center;
          background: #fa644a;
          border-radius: 30px;
          font-family: 'Luckiest Guy';
          font-size: xx-large;
          width: 30%;
          height: 70px;
          margin-top: 3%;
          color: white;
        "
        type="submit"
      >
        Login
      </button>

      <div class="kakao">
        <a id="custom-login-btn" @click="kakaoLogin()">
          <img
            src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
            width="222"
            alt="카카오 로그인 버튼"
          />
        </a>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    kakaoLogin() {
      window.location.href = "http://localhost:8080/login/oauth2/kakao";
      /*
    window.Kakao.Auth.login({
      scope: "account_email, profile_nickname",
      success: this.getKakaoAccount,
    });
    */
    },
  },
};
</script>

<style scoped>
@media (min-width: 1024px) {
  .triplog {
    margin-top: 5%;
  }

  .form-group {
    margin-bottom: 20px;
  }

  .kakao {
    margin-top: 5%;
  }
}
</style>
