import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);

app.use(router);
app.use(createPinia());

app.mount("#app");

window.Kakao.init("d11b452ed0b6be2964d3fbfd4cfdce4c");
