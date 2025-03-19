import { createApp } from "vue"
import App from "./App.vue"
import { router } from "./router"
import { store } from "./store"
import loadAppConfig from "./services/appConfigService"
import authService from "./services/apiService"

const APP_VERSION = "v1.0.1"

authService.setupAxiosInterceptors(router)

//Bootstrap 5.x
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.min"

//Global styles
import "@/assets/css/global.css"

//Configure router
router.beforeEach((to, from, next) => {
  if (to.matched.some((route) => route.meta.requiresAuth)) {
    if (!store.getters.getAuthenticated) {
      next("/login")
    } else {
      next()
    }
  } else if (
    to.fullPath.toLocaleLowerCase() === "/login" &&
    store.getters.getAuthenticated
  ) {
    next("/home")
  } else {
    next()
  }
})

//Export the loaded configuration to use outside the components (in vuex store for example)
export var appConfig = {}

//Loading configuration
const promiseConfig = loadAppConfig()
  .then((response) => {
    //agregamos version app
    response.version = APP_VERSION
    appConfig = response
    return new Promise((resolve) => {
      resolve(response)
    })
  })
  .catch((error) => {
    console.log(error)
    throw new Error("Error loading app config", error)
  })

//Create application after load configuration
promiseConfig.then((config) => {
  const app = createApp(App)
  app.config.globalProperties.$AppConfig = config
  app.use(store)
  app.use(router)
  app.mount("#app")
})
//createApp(App).use(store).use(router).mount("#app")
