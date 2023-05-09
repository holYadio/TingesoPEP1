# TINGESO PEP 1 2023-01: Aplicación Monolítica
* Autor: Jhoisan Allendes
* Sección: 13265-0-B-2
* Profesor: Alcides Quispe

### Descripción
En este repositorio repositorio se encuentra la aplicación monolítica desarrollada para la PEP 1 de TINGESO en 2023. La aplicacion esta desarrollada principalmente en Java, utilizando SpringBoot, HTML5 y CSS

### Herramientas Utilizadas

* Java 17: La aplicación utiliza la Programación Orientada a Objetos y se desarrolla utilizando capas, compuestas por Servicios, Entidades, Controladores y Repositorios.
* HTML5: Se utiliza HTLM5 para el desarrollo de las vistas, junto con otros plugins de springboot.
* CSS: Se utiliza CSS para darle estilo a los distintos componentes de las "vistas" del proyecto.
* IntelliJ IDEA Ultimate 2023.1: IDE que cuenta con multiples funcionalidades que facilitan el trabajo con java.
* Docker / Docker-Compose / Docker Desktop: Se utiliza Docker junto con Docker-Compose para crear contenedores de Imágenes y asi poder levantar la aplicación en distintos PCs localmente. Las imágenes de docker se descargan desde Docker Hub.
* Jenkins: Se utiliza para automatizar todo el proceso de ensamblamiento de la aplicación junto con la creacion de imágenes de Docker y la subida de esta a Docker Hub.
* SonarQube: Se utiliza para analizar el código, y de esta forma encontrar *Code Smells*, entre otras métricas.
* Terraform: Se utiliza para poder levantar la aplicacion en un servidor web(AWS).

### Análisis Estático de código fuente con SonarQube
[![Cobertura.png](https://i.postimg.cc/Kzn1ykWT/Cobertura.png)](https://postimg.cc/5Y24B0b9)

***
  
# Manual de Usuario
## Tabla de Contenidos
1. [Contexto](###Contexto)
2. [Vistas](###Vistas)
4. [Entradas](###Entradas)
5. [Cómo subir un archivo de acopio](###Cómo-Subir-un-archivo-de-acopio)
6. [Cómo subir un archivo del laboratorio](###Cómo-subir-un-archivo-de-laboratorio)

### Contexto
Esta aplicación fue desarrollada para facilitar el calculo de los pagos a los proveedores en relacion a la cantidad de la leche, calidad de la leche y la frecuencia de los envios realizados. Para ello, se cuenta con distintas secciones, en donde se puede subir la infor
