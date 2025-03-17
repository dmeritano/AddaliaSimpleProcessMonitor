## ADDALIA SIMPLE PROCESS MONITOR

## Descripción

Esta aplicación se encarga de mostrar estadísticas de cantidad de documentos acumulados, procesados y pendientes en los procesos y repositorios de soluciones basadas en el DMS de Addalia(©)

La aplicación consume un servicio/api ( AddaliaSimpleProcessMonitorBackend ) en el cual deben configurarse todas las queries (DMS queries) necesarias para mostrar la información al usuario.

## Características

- La App está construida con Vue.js (v3) y se ejecuta por completo en el navegador del usuario. Vue 3 - Vanilla Javascript - ES6

- Utiliza Bootstrap 5.3 para los estilos, con las personalización de ciertas clases de ese framework para adaptarlos al look and feel de GoodOK

- La App se entrega empaquetada como un fichero WAR para que pueda ser deplegada en un servidor de aplicaciones Tomcat. El propio proceso de build genera el war en /target

- Para el manejo del estado la App usa 'vuex' y para la persistencia de los datos del navegador se usa 'vuex-persist', utilizando como repositorio el localstorage.

- Las peticiones al backend las hace con axios y para la navegación entre páginas y la seguridad (rutas protegidas) utiliza vue-router.

## Configuración

**config.json**

Toda la configuración de la App se apoya en el fichero `config.json` , el cual una vez desplegada la aplicación estará en el directorio raíz.

La idea sería que el configurador adapte el fichero con la configuración deseada y luego lo reemplace en el WAR que se terminará desplegando en el Tomcat.

### Atributos

A continuación se describen todos los atributos en `config.json` y su significado.

> backendServiceURI: Es la URL donde se encuentra desplegado el servicio AddaliaSimpleProcessMonitorBackend
> intervalToCheckForDataUpdate: Es el intervalo de tiempo (en milisengudos) tras el cual se se solicitarán al backend los datos actualizados, mientras el usuario se encuentre en la pagina principal (Dashboard)

### Usuarios

La aplicación se autentica contra el servicio AddaliaSimpleProcessMonitorBackend con usuario y contraseña. Este servicio maneja su propia BD/tabla de usuarios y por tanto el administrador dicho servicio es el encargado de crear los usuarios que se necesiten.
