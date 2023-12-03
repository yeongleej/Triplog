<script setup>
import TheHeadingNavbar from "../components/layout/TheHeadingNavbar.vue";
import { ref, onMounted, watch } from "vue";
import { listRouteByUser, listAllRouteByUser } from "@/api/route";
import { changePw } from "@/api/auth"

const myRouteList = ref([]);
const userInfo = ref({});
const passwords = ref({
  curPw: "",
  newPw: ""
})

onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  getRouteListByUser();
});

const getRouteListByUser = () => {
  // API 호출
  // listArticle(파리미터, 성공시 객체, 실패 시)

  listAllRouteByUser(userInfo.value.uid, ({ data }) => {
    myRouteList.value = data;
  });
};

function changePassword() {
  changePw(passwords);
}
</script>

<template>
  <TheHeadingNavbar></TheHeadingNavbar>
  <div class="route-container">
    <div class="route-title">
      <span class="title">My Page</span>
    </div>
    <div class="my-content">
      <div class="my-img">
        <img src="@/assets/profile.png" alt="none image" width="180" height="180" />
      </div>
      <div class="my-info">
        <div class="my-item">
          <span class="my-title">이메일</span>
          <div class="my-input">{{ userInfo.email }}</div>
        </div>
        <div class="my-item">
          <span class="my-title">닉네임</span>
          <div class="my-input">{{ userInfo.nickname }}</div>
        </div>
        <div class="my-item">
          <span class="my-title">여행 정보</span>
          <div class="my-route-info">
            <div v-for="(item, index) in myRouteList" :key="item.route_name" class="my-route-item">
              <img src="@/assets/check.png" alt="none image" width="40" height="40" />
            <RouterLink to="#">{{ item.route_name }}</RouterLink>  
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="my-btn">
      <div class="modify">
        <RouterLink to="/modify" style="text-decoration: none; color: black;">수정하기</RouterLink></div>
      <button class="remove">회원탈퇴</button>
    </div>
  </div>
</template>

<style scoped>
@media (min-width: 1024px) {
.title {
  /* font-family: "Luckiest Guy"; */
  font-size: 25px;
  font-weight: bold;
}
.route-title {
  width: 20%;
  height: 10%;
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px;
  /* box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px; */
  padding-top: 5px;
  padding-bottom: 10px;
}
.route-container {
  width: 100%;
  padding: 2%;
  border: 0;
  border-radius: 15px;
  box-shadow: 0 0 0.375rem 0.0625rem rgb(194, 194, 194);
  margin-bottom: 5%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.my-content {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 80px;
  width: 75%;
  justify-content: space-around;
}
.my-img{
  box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 50%;
  /* box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 50%; */
  padding-top: 5px;
  padding-bottom: 5px;
  width: 250px;
  height: 250px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.my-item{
  display: flex;
  flex-direction: row;
}
.my-title{
  margin-right: 50px;
  margin-bottom: 20px;
  font-size: 25px;
  display: flex;
  align-items: center;
}
.my-input {
  font-size: 25px;
  width: 300px;
  /* box-shadow: -3px -3px 7px #ffffff73, 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px; */
  box-shadow: inset -3px -3px 7px #ffffff73, inset 3px 3px 5px rgba(94, 104, 121, 0.288);
  border-radius: 5px;
  padding-top: 5px;
  padding-bottom: 5px;
  margin-right: 20px;
  margin-bottom: 20px;
}
.my-route-item{
  display: flex;
  justify-content: left;
  font-size: 18px;
  align-items: center;
  margin-bottom: 10px;
}
.my-route-item > img{
  margin-right: 30px;
}
.my-btn{
  margin-top: 30px;
  width: 20%;
  display: flex;
  justify-content: space-around;
}
.modify{

  display: inline-block;
  outline: 0;
  cursor: pointer;
  border-radius: 6px;
  border: 2px solid gray;
  color: gray;
  background: 0 0;
  padding: 8px;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 2px 4px 0px, rgba(0, 0, 0, 0.05) 0px 1px 1.5px 0px;
  font-weight: 800;
  font-size: 16px;
  height: 42px;
                
}
.modify:hover{
  background-color: gray;
  color: #fff;
}
.remove {

  display: inline-block;
  outline: 0;
  cursor: pointer;
  border-radius: 6px;
  border: 2px solid #ff4742;
  color: #fff;
  background-color: #ff4742;
  padding: 8px;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 2px 4px 0px, rgba(0, 0, 0, 0.05) 0px 1px 1.5px 0px;
  font-weight: 800;
  font-size: 16px;
  height: 42px;

                
}
.remove:hover{
  background: 0 0;
  color: #ff4742;
}
}
</style>
