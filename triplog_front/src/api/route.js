import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios 객체

const url = "/route";

function listRoute(success, fail) {
  local.get(`${url}`).then(success).catch(fail);
}
function listRouteByUser(uid, success, fail) {
  local.get(`${url}/user/${uid}`).then(success).catch(fail);
}

function listAllRouteByUser(uid, success, fail) {
  local.get(`${url}/user/${uid}/all`).then(success).catch(fail);
}
function detailRoute(routeId, success, fail) {
  local.get(`${url}/${routeId}`).then(success).catch(fail);
}

function registRoute(route, success, fail) {
  console.log("routejs route", route);
  local
    .post(`${url}`, route, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
}
function listRouteByName(route_name, success, fail) {
  local.get(`${url}/name/${route_name}`).then(success).catch(fail);
}

function listRouteByKeyName(keyword, success, fail) {
  local.get(`${url}/keyname/${keyword}`).then(success).catch(fail);
}
function listRouteByKeyNameTop3(keyword, success, fail) {
  local.get(`${url}/keyname/${keyword}/3`).then(success).catch(fail);
}
function listRouteByCityName(cityName, success, fail) {
  local.get(`${url}/cityname/${cityName}`).then(success).catch(fail);
}

//   function getModifyRoute(routeId, success, fail) {
//     local.get(`${url}/modify/${routeId}`).then(success).catch(fail);
//   }

function modifyRoute(route, success, fail) {
  local.put(`${url}`, JSON.stringify(route)).then(success).catch(fail);
}

function deleteRoute(routeId, success, fail) {
  local.delete(`${url}/${routeId}`).then(success).catch(fail);
}

export {
  listRoute,
  detailRoute,
  registRoute,
  modifyRoute,
  deleteRoute,
  listRouteByUser,
  listRouteByName,
  listRouteByKeyName,
  listRouteByCityName,
  listRouteByKeyNameTop3,
  listAllRouteByUser,
};
