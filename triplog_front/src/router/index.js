import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import BoardView from "../views/BoardView.vue";
import RouteView from "../views/RouteView.vue";
import MypageView from "../views/MypageView.vue";
import ModifyMyInfoView from "../views/ModifyMyInfoView.vue";
import ChangePwdView from "../views/ChangePwdView.vue";
import SpotifyView from "../views/SpotifyView.vue";
// import BoardList from "@/components/board/BoardList.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
    },
    {
      path: "/modify",
      name: "modify",
      component: ModifyMyInfoView,
    },
    {
      path: "/modify/pw",
      name: "modifyPw",
      component: ChangePwdView,
    },
    {
      path: "/my",
      name: "mypage",
      component: MypageView,
    },
    {
      path: "/route",
      name: "route",
      component: RouteView,
      redirect: { name: "route-regist" },
      children: [
        {
          path: "regist",
          name: "route-regist",
          component: () => import("@/components/routeplace/RouteRegistPage.vue"),
        },
        {
          path: "my",
          name: "my-route",
          component: () => import("@/views/MyAllRouteView.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: BoardView,
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/place/:route_id",
      name: "route-place",
      component: () => import("@/views/RoutePlaceView.vue"),
    },
    {
      path: "/music",
      name: "music",
      component: SpotifyView,
      redirect: { name: "music-list" },
      children: [
        {
          path: "list",
          name: "music-list",
          component: () => import("@/components/spotify/SpotifyListPage.vue"),
        },
      ],
    },
  ],
});

export default router;
