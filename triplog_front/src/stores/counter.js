import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios 객체

const urlCities = "/cities";
const urlKeyword = "/keyword-list";

// export const useCounterStore = defineStore('counter', () => {
//   const count = ref(0)
//   const doubleCount = computed(() => count.value * 2)
//   function increment() {
//     count.value++
//   }

//   return { count, doubleCount, increment }
// })
export const useRouteStore = defineStore("routeStore", () => {
  const cities = ref([]);
  const keywordList = ref([]);
  const routeList = ref([]);
  const myRouteList = ref([]);
  const myRecentRouteList = ref([]);

  const getCities = (success, fail) => {
    local
      .get(`${urlCities}`)
      .then((success) => {
        cities.value = success.data;
      })
      .catch(fail);
  };

  const getKeyword = (success, fail) => {
    local
      .get(`${urlKeyword}`)
      .then((success) => {
        // console.log(success.data);
        keywordList.value = success.data;
      })
      .catch(fail);
  };
  //   function getRouteList(success, fail) {
  //     local.get('/route').then((success) => {
  //       routeList.value = success.data;
  //     }).catch(fail);
  //   };
  //   function getMyRouteList(success, fail) {
  //     local.get(`route/user/${uid}`).then((success) => {
  //       myRouteList.value = success.data;
  //       // 나의 최근 3개의 루트 뽑기
  //       var cnt = 0;
  //       var idx = myRouteList.value.length - 1;
  //       while (cnt < 3 && idx >= 0) {
  //         myRecentRouteList.value.push(myRouteList.value[idx--]);
  //         cnt++;
  //       }
  //     }).catch(fail);
  // };

  return {
    cities,
    keywordList,
    routeList,
    myRouteList,
    myRecentRouteList,
    getCities,
    getKeyword,
    // getRouteList,
    // getMyRouteList,
  };
});

export const userInfoStore = defineStore("userStore", () => {
  const userInfo = ref({});

  const getUser = (success, fail) => {
    local
      .get("/auth")
      .then((success) => {
        console.log(success.data);
        userInfo.value = success.data;
      })
      .catch(fail);
  };
  return {
    userInfo,
    getUser,
  };
});
