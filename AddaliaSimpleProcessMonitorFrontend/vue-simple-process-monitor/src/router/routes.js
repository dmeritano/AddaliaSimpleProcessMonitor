import HomeView from "../views/HomeView.vue"
import AboutView from "../views/AboutView.vue"
import LoginView from "../views/LoginView.vue"

const routes = [
  { path: "/login", component: LoginView },
  { path: "/about", component: AboutView },
  { path: "/home", component: HomeView, meta: { requiresAuth: true } },
  { path: "/:pathMatch(.*)*", redirect: "/home" },
]

export default routes
