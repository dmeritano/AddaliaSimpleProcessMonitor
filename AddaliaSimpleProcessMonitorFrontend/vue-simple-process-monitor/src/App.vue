<template>
  <app-header />
  <router-view />
  <links-to-pages />
  <app-loading />
</template>

<script>
import AppHeader from "./components/AppHeader.vue"
import LinksToPages from "./components/LinksToPages.vue"
import AppLoading from "./components/AppLoading.vue"

export default {
  name: "App",
  components: {
    "links-to-pages": LinksToPages,
    "app-header": AppHeader,
    "app-loading": AppLoading,
  },
  data() {
    return {}
  },
}
</script>

<style>
#app {
  font-family: "Roboto", Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
