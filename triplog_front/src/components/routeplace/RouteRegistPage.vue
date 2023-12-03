<script setup>
import RouteList from "@/components/routeplace/RouteList.vue";
import RouteRegistModal from "@/components/routeplace/RouteRegistModal.vue";
import MyRouteList from "@/components/routeplace/myRouteList.vue";
import { ref, onMounted } from "vue";
import {
  listRoute,
  listRouteByUser,
  listRouteByName,
  listRouteByKeyName,
  listRouteByCityName,
} from "@/api/route";
import { useRoute, useRouter } from "vue-router";
// import { useRouteStore } from "@/stores/counter";
// import { storeToRefs } from "pinia";

// const router = useRouter();
// const routeStore = useRouteStore();
// const { routeList, myRouteList, myRecentRouteList } = storeToRefs(routeStore);
// const { getRouteList, getMyRouteList } = routeStore;

const myRouteList = ref([]);
const routeList = ref([]);

const popState = ref(false);
const isOpenModal = ref(false);
const isClose = async (ans) => {
  if (ans) {
    await listRoute(({ data }) => {
      routeList.value = data;
    });
    await listRouteByUser(userInfo.value.uid, ({ data }) => {
      myRouteList.value = data;
    });
    isOpenModal.value = false;
    console.log("추가전체리스트 : ", routeList.value);
    console.log("추가내리스트 : ", myRouteList.value);
  } else {
    isOpenModal.value = true;
  }
};

const userInfo = ref({});
onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  // getRouteList();
  getRouteList();
  if (userInfo == null) {
    myRouteList.value = [];
  } else {
    getRouteListByUser();
  }
});

const getRouteList = async () => {
  // API 호출
  // listArticle(파리미터, 성공시 객체, 실패 시)
  await listRoute(({ data }) => {
    routeList.value = data;
    console.log("전체루트 >> ", routeList.value);
  });
};

const myRecentRouteList = ref([]);
const getRouteListByUser = async () => {
  // API 호출
  // listArticle(파리미터, 성공시 객체, 실패 시)

  await listRouteByUser(userInfo.value.uid, ({ data }) => {
    myRouteList.value = data;
  });
};

const onSearchName = async (type, ans) => {
  console.log("부모로 전달된 >", type, ans);
  // 루트 이름 검색
  if (type === 1) {
    await listRouteByName(ans, ({ data }) => {
      routeList.value = data;
    });
  } else if (type === 2) {
    // 키워드 이름 검색
    await listRouteByKeyName(ans, ({ data }) => {
      routeList.value = data;
    });
  } else if (type === 3) {
    await listRouteByCityName(ans, ({ data }) => {
      routeList.value = data;
    });
  }
};
</script>

<template>
  <div class="box">
    <div class="item-cotent"><span>나만의 특별한 여행 계획을 만들어요!</span></div>
    <div class="item">
      <button class="registbtn" @click="isOpenModal = true">루트 만들기</button>
    </div>
  </div>
  <RouteRegistModal :is-open-modal="isOpenModal" @is-close="isClose"></RouteRegistModal>

  <MyRouteList :myRouteList="myRouteList"></MyRouteList>
  <RouteList :routeList="routeList" @search-by-name="onSearchName"></RouteList>
</template>

<style scoped>
.box {
  display: flex;
  flex-direction: row;
  justify-content: right;
  align-items: center;
  margin-bottom: 10px;
}
.registbtn {
  display: inline-block;
  outline: none;
  cursor: pointer;
  font-size: 16px;
  line-height: 1;
  border-radius: 500px;
  transition-property: background-color, border-color, color, box-shadow, filter;
  transition-duration: 0.3s;
  border: 1px solid transparent;
  letter-spacing: 2px;
  min-width: 160px;
  text-transform: uppercase;
  white-space: normal;
  font-weight: 700;
  text-align: center;
  padding: 17px 48px;
  color: #fff;
  background-color: #fca407;
  height: 48px;
  :hover {
    transform: scale(1.04);
    background-color: #e0c321;
  }
}
.item-cotent {
  margin-right: 3%;
  font-size: 20px;
}
</style>
