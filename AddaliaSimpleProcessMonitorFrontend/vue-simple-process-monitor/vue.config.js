const { defineConfig } = require("@vue/cli-service")
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === "production" ? "." : "/",
  devServer: {
    proxy: {
      "^/AddaliaSimpleProcessMonitorBackend": {
        target: "http://localhost:8080/",
        ws: true,
        changeOrigin: true,
      },
    },
  },
})
