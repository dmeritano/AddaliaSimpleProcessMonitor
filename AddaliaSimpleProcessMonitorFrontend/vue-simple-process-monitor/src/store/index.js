import { createStore } from "vuex"
import VuexPersistence from "vuex-persist"

function initialState() {
  return {
    authenticated: false,
    token: "",
    loggedInUser: "David",
    loading: false,
    version: require("../../package.json").version,
    retrievedData: { services: [] },
  }
}

export const store = createStore({
  state: initialState(),
  getters: {
    getToken(state) {
      return state.token
    },
    getUsername(state) {
      return state.loggedInUser
    },
    getAuthenticated(state) {
      return state.authenticated
    },
    getLoading(state) {
      return state.loading
    },
    getRetrievedData(state) {
      return state.retrievedData
    },
    getAppVersion(state) {
      return state.version
    },
  },
  mutations: {
    setToken(state, value) {
      state.token = value
    },
    setLoading(state, value) {
      state.loading = value
    },
    setUsername(state, value) {
      state.loggedInUser = value
    },
    setAuthenticated(state, value) {
      state.authenticated = value
    },
    setRetrievedData(state, value) {
      state.retrievedData = value
    },
  },
  actions: {
    logoutAction(context) {
      context.commit("setToken", "")
      context.commit("setUsername", "")
      context.commit("setAuthenticated", false)
      context.commit("setRetrievedData", { services: [] })
    },
    loginAction(context, data) {
      context.commit("setToken", data.token)
      context.commit("setUsername", data.username)
      context.commit("setAuthenticated", true)
    },
  },
  modules: {},
  plugins: [
    new VuexPersistence({
      storage: window.localStorage,
    }).plugin,
  ],
})
