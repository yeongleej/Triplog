<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, modifyArticle, detailArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const userInfo = ref({});

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  uid: "",
  nickname: "",
  hit: 0,
  registerTime: "",
});

onMounted(() => {
  const user = JSON.parse(localStorage.getItem("user"));
  userInfo.value = user;
  article.value.uid = userInfo.value.uid;
  article.value.nickname = userInfo.value.nickname;

  console.log("보드작성 >", article.value);
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  // API 호출
  isUseId.value = true;
  detailArticle(articleno, ({ data }) => {
    console.log(data);
    article.value = data;
  });
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  ////////////////////수정중////////////////////////////////////////
  // 사용자 uid 얻어오기 - cookie(jwt token)에서
  // article.value.uid = 1;
  /////////////////////////////////////////////////////////////////
  console.log("글등록하자!!", article.value);

  // API 호출
  registArticle(article.value, article.value);
  alert("글이 등록되었습니다!!");
  moveList();
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  // API 호출
  modifyArticle(article.value, article.value);
  // router.push({ name: "article-view", params: { articleno: article.articleNo } });
  alert("글이 수정되었습니다!!");
  moveList();
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="nickname" class="form-label">작성자 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.nickname"
        disabled
        :placeholder="article.nickname"
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        :placeholder="article.subject"
      />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea
        class="form-control"
        v-model="article.content"
        rows="10"
        :placeholder="article.content"
      ></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
