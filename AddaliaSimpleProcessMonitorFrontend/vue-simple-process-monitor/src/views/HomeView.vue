<template>
  <div class="mt-5 mb-2">
    <div class="d-flex justify-content-end pe-4 opacity-25">
      <button
        class="btn btn-light border border-success"
        @click="getData(true)"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          class="bi bi-arrow-repeat"
          viewBox="0 0 16 16"
        >
          <path
            d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41m-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9"
          />
          <path
            fill-rule="evenodd"
            d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5 5 0 0 0 8 3M3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9z"
          />
        </svg>
      </button>
    </div>
  </div>
  <div class="d-flex justify-content-start ps-3 m-0 text-info-emphasis">
    <p>► Instalaciones configuradas</p>
  </div>
  <div v-for="service in datos.services" :key="service.id">
    <div class="service-title">
      <h2>{{ service.installationName }}</h2>
    </div>
    <div class="grid-container">
      <div
        class="contenedor"
        v-for="(query, index) in service.queries"
        :key="index"
      >
        <div class="esfera color-esferas-1">{{ query.totalDocuments }}</div>
        <div class="leyenda">{{ query.queryNameOrDescription }}</div>
      </div>
    </div>
  </div>
</template>

<script>
/* que quilombo acá la puta madre*/

import authService from "@/services/apiService"
import { mapMutations, mapGetters } from "vuex"
export default {
  data() {
    return {}
  },
  computed: {
    ...mapGetters(["getRetrievedData"]),
    datos() {
      return this.getRetrievedData
    },
  },
  methods: {
    ...mapMutations(["setLoading", "setRetrievedData"]),
    async getData(showLoading) {
      try {
        if (showLoading) {
          this.setLoading(true)
        }
        const data = await authService.getData()
        this.setRetrievedData(data)
      } catch (error) {
        console.error("Error obteniendo datos:", error)
      } finally {
        this.setLoading(false)
      }
    },
  },
  mounted() {
    const inter = this.$AppConfig.intervalToCheckForDataUpdate
    this.intervalo = setInterval(() => {
      this.getData(false)
    }, inter)
    this.getData(true)
  },
  beforeUnmount() {
    clearInterval(this.intervalo)
  },
}
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  justify-items: center;
  margin-bottom: 50px;
  margin-top: 25px;
}
.esfera {
  width: 250px;
  height: 250px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 48px;
  text-align: center;
  font-weight: bolder;
  border: 2px solid #0c969a;
  border-style: inset;
}
.contenedor {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}
.leyenda {
  margin-top: 5px; /* Reduce el espacio entre la esfera y la leyenda */
  font-size: 16px;
}
.color-esferas-1 {
  background-color: #eaecee;
  color: black;
}
.color-esferas-2 {
  background-color: #e89238;
  color: black;
}
.service-title {
  background-color: #eaecee;
  border-radius: 5px;
  border-color: #0c969a;
  margin: 0px 10px 10px 10px;
  padding: 2px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}
/* Media queries */
@media (max-width: 800px) {
  .grid-container {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (max-width: 500px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>
