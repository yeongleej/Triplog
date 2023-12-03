<script setup>
import { ref, watch } from "vue";
import { register } from "../api/auth";
import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute();

const idErrorMsg = ref("");
const pwErrorMsg = ref("");

const registerForm = ref({
  email: "",
  pw: "",
  nickname: "",
});

function onSubmit() {
  if (idErrorMsg.value) {
    alert(idErrorMsg.value);
  } else if (pwErrorMsg.value) {
    alert(pwErrorMsg.value);
  } else {
    console.log(registerForm.value);
    register(registerForm.value);
    router.push({ name: "login" });
  }
}

watch(
  () => registerForm.value.email,
  (value) => {
    let len = value.length;
    if (len == 0) {
      idErrorMsg.value = "이메일을 입력해 주세요!";
    } else idErrorMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => registerForm.value.pw,
  (value) => {
    let len = value.length;
    if (len == 0) {
      pwErrorMsg.value = "패스워드를 입력해 주세요!";
    } else pwErrorMsg.value = "";
  },
  { immediate: true }
);
</script>

<template>
  <div>
    <a href="/"
      ><img alt="Triplog Logo" class="triplog" src="@/assets/triplogm.svg" width="250" height="250"
    /></a>
    <p style="color: black; font-size: xxx-large">Register</p>

    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <input
          type="text"
          placeholder="E-MAIL"
          id="email"
          v-model="registerForm.email"
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
          id="password"
          v-model="registerForm.pw"
          style="
            border: none;
            text-align: center;
            font-size: x-large;
            margin-top: 2%;
            color: lightgray;
          "
        />
        <hr style="color: black; width: 30%; border-top: 2px solid #000000; margin-left: 35%" />
      </div>
      <div class="form-group">
        <input
          type="text"
          placeholder="NICKNAME"
          id="nickname"
          v-model="registerForm.nickname"
          style="
            border: none;
            text-align: center;
            font-size: x-large;
            margin-top: 2%;
            color: lightgray;
          "
        />
        <hr style="color: black; width: 30%; border-top: 2px solid #000000; margin-left: 35%" />
      </div>
      <button
        type="submit"
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
      >
        Register
      </button>
    </form>
  </div>
</template>

<style scoped>
@media (min-width: 1024px) {
  .triplog {
    margin-top: 5%;
  }

  .form-group {
    margin-bottom: 20px;
  }
}
</style>
