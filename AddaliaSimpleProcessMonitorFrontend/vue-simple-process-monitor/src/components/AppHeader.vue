<template>
  <nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
      <a class="navbar-brand logo-menu" href="#">
        <img src="@/assets/images/logo-addalia-header.png" alt="Logo" />
      </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link to="/" class="nav-link">Inicio</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/about" class="nav-link">Acerca De</router-link>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto">
          <li class="nav-item nav-link text-white me-3" v-if="authenticated">
            {{ getUsername }}
          </li>
          <li class="nav-item" v-if="!authenticated">
            <router-link
              to="/login"
              v-if="!authenticated"
              class="nav-link text-white me-3"
              >Acceso</router-link
            >
          </li>
          <li class="nav-item" v-if="authenticated">
            <a
              href="#"
              @click.prevent="logoutApp"
              v-if="authenticated"
              class="nav-link text-white me-3"
              >Salir</a
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <h3 class="app-title">Addalia Simple Process Monitor</h3>
</template>

<script>
import { mapGetters, mapActions } from "vuex"
export default {
  data() {
    return {}
  },
  computed: {
    ...mapGetters(["getUsername", "getAuthenticated"]),
    authenticated() {
      return this.getAuthenticated
    },
  },
  methods: {
    ...mapActions(["logoutAction"]),
    logoutApp() {
      this.logoutAction()
      this.$router.push("/login")
    },
  },
  mounted() {},
  created() {},
}
</script>

<style scoped>
.navbar-dark .navbar-brand {
  color: #fff;
  font-weight: bold;
}
.navbar-dark .navbar-nav > li > a {
  color: #fff;
}
.navbar-inverse .navbar-nav > li > a:hover {
  font-weight: bold;
}
.navbar-dark {
  background: linear-gradient(to right, #0a969c, #c4d826);
}
.logo-menu {
  padding-top: 10px;
}
.logo-menu > img {
  width: 135px;
  height: 33px;
}

h3 {
  margin-top: 10px;
  margin-bottom: 10px;
  color: rgb(44, 44, 46);
  font-weight: bolder;
}
@media screen and (max-width: 980px) {
  h3 {
    display: none;
    /*visibility: hidden;*/
  }
}
</style>
