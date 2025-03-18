<template>
  <nav>
    <RouterLink to="/">Inicio</RouterLink>
    <RouterLink to="/about">Acerca De</RouterLink>
  </nav>

  <footer class="text-center">
    <div class="mb-3">
      <small class="text-muted"
        >&copy;ADDALIA. Todos los derechos reservados.</small
      >
    </div>
  </footer>
</template>

<script>
export default {
  data() {
    return {}
  },
  computed: {},
  methods: {},
  mounted() {},
  created() {},
}
</script>

<style scoped>
nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
    margin-right: 5px;
  }
}
</style>
