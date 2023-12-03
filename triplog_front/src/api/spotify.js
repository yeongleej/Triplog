import { localAxios } from "@/util/http-commons";
import axios from "axios";
import { ref } from "vue";

const local = localAxios(); // axios 객체
const url = "/spotify";

function findMusicList(type, keyword, success, fail) {
    local.get(`${url}/${type}/${keyword}`).then(success).catch(fail);
}
  
export { findMusicList };