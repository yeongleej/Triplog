<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 2778px;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: block;
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: block;
    place-items: start center;
    /*padding-right: calc(var(--section-gap) / 2);*/
    padding-left: auto;
  }

  header .wrapper {
    display: block;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  div {
    text-align: center;
    margin-left: auto;
    font-size: 1rem;
  }
}
</style>
