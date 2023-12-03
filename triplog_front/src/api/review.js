import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios 객체
const url = "/api/v1/users/review";

function showReview(reviewId, success, fail) {
  local.get(`${url}/${reviewId}`).then(success).catch(fail);
}

function registReview(review, success, fail) {
  console.log("axios 등록 리뷰: ", review);
  local
    .post(`${url}`, review, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((success) => {
      console.log("등록 성공한 리뷰뷰>", success.data);
    })
    .catch(fail);
}

function getReviewListByRoute(route_id, success, fail) {
  local.get(`${url}/${route_id}/all`).then(success).catch(fail);
}

export { showReview, registReview, getReviewListByRoute };
