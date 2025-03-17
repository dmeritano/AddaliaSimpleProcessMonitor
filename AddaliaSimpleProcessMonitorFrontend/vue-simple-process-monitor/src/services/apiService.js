import axios from "axios"
import { store } from "@/store"
import { appConfig } from "@/main"

const login = async (credentials) => {
  const response = await axios.post(
    `${appConfig.backendServiceURI}/api/authenticate`,
    credentials
  )
  return response.data
}

const getData = async () => {
  const response = await axios.get(
    `${appConfig.backendServiceURI}/api/datasecured`
  )
  //return JSON.stringify(response.data)
  return response.data
}

const setupAxiosInterceptors = (router) => {
  axios.interceptors.request.use(
    (config) => {
      const token = store.getters.getToken
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

  axios.interceptors.response.use(
    (response) => {
      return response
    },
    (error) => {
      if (error.response.status === 401 || error.response.status === 403) {
        store.dispatch("logoutAction")
        router.push("/login") // Redirige al login en caso de token inválido
      }
      return Promise.reject(error)
    }
  )
}

export default {
  login,
  setupAxiosInterceptors,
  getData,
}
