<script setup>
import { ref, watch } from "vue";
import { changePw } from "../api/auth";

const idErrorMsg = ref("");
const pwErrorMsg = ref("");

const changeForm = ref({
  curPw: "",
  newPw: "",
});

const verify = ref("");

function onSubmit() {
  if (idErrorMsg.value) {
    alert(idErrorMsg.value);
  } else if (pwErrorMsg.value) {
    alert(pwErrorMsg.value);
  } else {
    console.log(changeForm.value);
    const userData = localStorage.getItem("user");
    const parsed = JSON.parse(userData);
    changePw(parsed["uid"], changeForm.value);
  }

  window.location.href = "/my";
}

watch(
  () => changeForm.value.curPw,
  (value) => {
    let len = value.length;
    if (len == 0) {
      idErrorMsg.value = "현재 비밀번호를 입력해 주세요!";
    } else idErrorMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => changeForm.value.newPw,
  (value) => {
    let len = value.length;
    if (len == 0) {
      pwErrorMsg.value = "바꿀 비밀번호를 입력해 주세요!";
    } else pwErrorMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => verify.value,
  (value) => {
    let verification = value.toString;

    if (verification != changeForm.value.newPw.toString) {
      pwErrorMsg.value = "바꿀 비밀번호를 다시 입력해 주세요!";
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
    <p style="color: black; font-size: xxx-large">비밀번호 변경</p>

    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <input
          type="password"
          placeholder="Current Password"
          id="curPw"
          v-model="changeForm.curPw"
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
          type="password"
          placeholder="New Password"
          id="newPw"
          v-model="changeForm.newPw"
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
          type="password"
          placeholder="New Password (Again)"
          id="verify"
          v-model="verify"
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
        Update
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
