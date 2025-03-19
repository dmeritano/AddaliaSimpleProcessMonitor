<template>
  <div class="container">
    <div class="login-container">
      <h4 class="text-center py-2">Acceso</h4>
      <form class="login-form" @submit.prevent="handleLogin">
        <div class="row">
          <div class="col-md-12 form-group">
            <!-- TRICK para evitar autocompletado de los navegadores 
                readonly
                onfocus="this.removeAttribute('readonly');"
                Poniendolo en este campo tambien me soluciona el autocomplete del campo siguiente (pass)            
              -->
            <input
              type="text"
              readonly
              onfocus="this.removeAttribute('readonly');"
              class="form-control custom-input"
              id="usuario"
              v-model="credentials.username"
              placeholder="Usuario"
            />
          </div>
        </div>
        <div class="row mt-2">
          <div class="col-md-12 form-group">
            <input
              type="password"
              autocomplete="off"
              class="form-control custom-input"
              id="password"
              v-model="credentials.password"
              placeholder="Password"
            />
          </div>
        </div>
        <div class="row my-3">
          <div class="col-md-12 text-center">
            <p class="text-danger">{{ loginError }}</p>
          </div>
        </div>
        <div class="row mt-3">
          <div class="col-md-12 form-group text-center">
            <button type="submit" class="btn btn-default">Entrar</button>
            <div class="text-center pt-2">
              <small class="custom-version">{{ appVersion }}</small>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "@/services/apiService" // Ajusta la ruta
import { mapMutations, mapActions } from "vuex"
export default {
  components: {},
  data() {
    return {
      credentials: {
        username: null,
        password: null,
      },
      loginError: null,
      appVersion: this.$AppConfig.version,
    }
  },
  computed: {},
  methods: {
    ...mapMutations(["setLoading"]),
    ...mapActions(["loginAction"]),
    async handleLogin() {
      try {
        this.loginError = null
        this.setLoading(true)
        const respData = await authService.login(this.credentials)
        this.loginAction(respData)
        this.$router.push("/home")
      } catch (error) {
        console.error("Error de inicio de sesión:", error)
        console.error("Status: ", error.status)
        if (error.status === 401) {
          this.loginError = "Usuario o contraseña incorrectos"
        }
      } finally {
        this.setLoading(false)
      }
    },
  },
}
</script>

<style scoped>
.login-container {
  width: 320px;
  max-width: 100%;
  margin: 50px auto;
}
</style>
