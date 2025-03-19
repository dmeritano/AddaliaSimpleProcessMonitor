<template>
  <div class="container px-5">
    <h3 class="my-4">Acerca De</h3>
    <p class="text-light-emphasis">
      Versi&oacute;n Lite del
      <i>Monitor/Dashboard</i> de Addalia(&copy;). Esta aplicaci&oacute;n se
      encarga de mostrar estad&iacute;sticas de cantidad de documentos
      acumulados, procesados y pendientes en los procesos y repositorios de
      soluciones basadas en respositorios (DMS) de Addalia(&copy;)>
    </p>
    <small class="custom-version"
      >Addalia(&copy;) Simple Process Monitor {{ version }}</small
    >
  </div>
</template>

<script>
export default {
  computed: {
    appName() {
      return "SIMPLE-PROCESS-MONITOR"
    },
    text1() {
      return ""
    },
    version() {
      return this.$AppConfig.version
    },
  },
}
</script>
