import { localAxios } from "@/util/http-commons";
import axios from "axios";
import { ref } from "vue";

const local = localAxios(); // axios 객체
const spoturl = "/spot";
const placeurl = "/place";
const placeList = ref([]);

function findPlace(placeName, longitude, latitude, success, fail) {
  local
    .get(`${spoturl}?spotName=${placeName}&longitude=${longitude}&latitude=${latitude}`)
    .then(success)
    .catch(fail);
}

function findPlaceAgain(placeName, longitude, latitude, success, fail) {
  local
    .get(`${spoturl}/again?spotName=${placeName}&longitude=${longitude}&latitude=${latitude}`)
    .then(success)
    .catch(fail);
}

function findRoutePlace(routeId, success, fail) {
  local.get(`${placeurl}/${routeId}/all`).then(success).catch(fail);
}
function registRoutePlace(route_id, place, success, fail) {
  local.post(`${placeurl}/${route_id}`, JSON.stringify(place)).then(success).catch(fail);
}

function deletePlace(route_id, place_id, priority, success, fail) {
  local.delete(`${placeurl}/${route_id}/${place_id}/${priority}`).then(success).catch(fail);
}

function searchPath(success, fail) {
  axios
    .get("https://apis-navi.kakaomobility.com/v1/directions", {
      headers: {
        Authorization: "2db6533c9a7ac200635542eb12574641",
        "Content-Type": "application/json",
      },
    })
    .then((success) => {
      console.log(success.data);
    })
    .catch(fail);
}

export { findPlace, findRoutePlace, findPlaceAgain, registRoutePlace, deletePlace, searchPath };
