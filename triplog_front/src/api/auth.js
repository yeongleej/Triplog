import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios 객체

const url = "/api/v1/auth";

function register(form, succes, fail) {
  local.post("/api/v1/auth/new", form).then(succes).catch(fail);
}

function login(form, success, fail) {
  local
    .post(`${url}`, form)
    .then((success) => {
      localStorage.setItem("user", JSON.stringify(success.data));
      window.location = "/";
    })
    .catch(fail);
}

function oauthLogin() {
  local.get("/login/oauth2/kakao");
}

function changePw(id, form, success, fail) {
  local
    .put(`${url}/${id}`, form)
    .then((success) => {
      console.log(success.data);
    })
    .catch(fail);
}

function logout(success, fail) {
  local.post(`${url}/logout`).then(success).catch(fail);
}

function modifyUserInfo(uid, nickname, succes, fail) {
  local.put(`${url}/modify/${uid}`, nickname).then(succes).catch(fail);
}

export { register, login, oauthLogin, changePw, logout, modifyUserInfo };
