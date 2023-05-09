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
1. [Vistas](###Vistas)
2. [Entradas](###Entradas)
3. [Cómo subir un archivo de acopio](###Cómo-subir-un-archivo-de-acopio)
4. [Cómo subir un archivo del laboratorio](###Cómo-subir-un-archivo-de-laboratorio)
5. [Cómo ingresar un nuevo proveedor](###Cómo-ingresar-un-nuevo-proveedor)
6. [Cómo generar la planilla de pagos](###Cómo-generar-la-planilla-de-pagos)

### 1. Vistas
La aplicación web desarrollada cuenta con multiples vistas con distinto propositos, estas vistas son:

#### Página Principal:

[![home.png](https://i.postimg.cc/g2g2hwrV/home.png)](https://postimg.cc/v46yFZrT)

#### Cargar Acopio:
[![Cargar-datos-acopio.png](https://i.postimg.cc/MH1GfdjH/Cargar-datos-acopio.png)](https://postimg.cc/H8smf0z1)

#### Ver Informacion de Acopio:
[![Ver-informacion-Proveedores.png](https://i.postimg.cc/DwvzmQff/Ver-informacion-Proveedores.png)](https://postimg.cc/4m0GS9hj)

#### Cargar Datos del Laboratorio:
[![Cargar-datos-laboratorio.png](https://i.postimg.cc/vmYmKrxx/Cargar-datos-laboratorio.png)](https://postimg.cc/xXZYJbR9)

#### Ver Datos del Laboratorio:
[![Ver-informacion-Lab.png](https://i.postimg.cc/Pq055BFY/Ver-informacion-Lab.png)](https://postimg.cc/94thxN2Q)

#### Ver Proveedores:
[![Ver-proveedores.png](https://i.postimg.cc/7L0pSdZg/Ver-proveedores.png)](https://postimg.cc/yJ6QHf9d)

#### Nuevo Proveedor:
[![nuevo-proveedor.png](https://i.postimg.cc/BbqPYvH2/nuevo-proveedor.png)](https://postimg.cc/PpRqNT6q)

#### Planilla de Pagos:
[![planilla-de-Pagos.png](https://i.postimg.cc/vH2xhbB6/planilla-de-Pagos.png)](https://postimg.cc/svS2DF5V)

### 2. Entradas
Dado que en la aplicación web se puede subir archivos y llenar casillas, se debe tener en consideración el formato que deben tener los distintos parámetros que se ingresan.
Para cargar un acopio o los datos del laboratorio se debe tener en consideración que la extensión de estos archivos debe ser  ***.csv***, es decir que el archivo deberia de ser de la forma ***nombre_archivo.csv***.

Para el Archivo de acopio, los datos que se encuentren dentro deben tener el formato de ***Fecha;Turno;Proveedor;KLS Leche***, un ejemplo de esto se puede ver en la imágen a continuación:

[![archivo-ejemplo-acopio.png](https://i.postimg.cc/Nj3vmsy4/archivo-ejemplo-acopio.png)](https://postimg.cc/dhjfKKjk)

Para el Archivo de los datos del laboratorio los datos deberán de tener el formato de ***Proveedor;Porcentaje Grasa;Porcentaje Solido Total***, un ejemplo de esto se puede ver en la imágen a continuación:

[![archivo-ejemplo-lab.png](https://i.postimg.cc/4nCNd6T6/archivo-ejemplo-lab.png)](https://postimg.cc/N24wCrFF)

### 3. Cómo subir un archivo de acopio
Para poder subir un archivo con los datos del acopio, se debe seleccionar la opción de cargar acopio, en caso de estar en la página principal.

[![Seleccionar-cargar-acopio.png](https://i.postimg.cc/zf3XFJR9/Seleccionar-cargar-acopio.png)](https://postimg.cc/2qRDzNGw)

Ahora en esta vista se debe seleccionar el archivo que se desea subir, en este caso ocuparemos el archivo ejemplo que se muestra a continuación.

[![archivo-ejemplo-acopio.png](https://i.postimg.cc/Nj3vmsy4/archivo-ejemplo-acopio.png)](https://postimg.cc/dhjfKKjk)

Después de haber seleccionado el archivo de acopio que se desea subir, se apreta el botón subir.

[![Seleccionar-opcion-subir.png](https://i.postimg.cc/fbNYVdvf/Seleccionar-opcion-subir.png)](https://postimg.cc/2101JVM1)

Si todo salio bien, la aplicación nos muestra un mensaje que el que se guarda correctamente.

[![confirmacion-acopio.png](https://i.postimg.cc/GmhQcVbx/confirmacion-acopio.png)](https://postimg.cc/RNyfdP6W)

Si seleccionamos la opción de ver los datos del acopio, podremos verificar que se agrego el acopio correctamente a la base de datos.

[![verificar-informacion-acopio.png](https://i.postimg.cc/wBncQBLw/verificar-informacion-acopio.png)](https://postimg.cc/t1txXpr6)

### 4. Cómo subir un archivo de laboratorio
Para poder subir un archivo con los datos del laboratorio, se debe seleccionar la opción de cargar datos del laboratorio, en caso de estar en la página principal.

[![Seleccionar-cargar-laboratorio.png](https://i.postimg.cc/3JHNQ5Pv/Seleccionar-cargar-laboratorio.png)](https://postimg.cc/0rcP735k)

Ahora en esta vista se debe seleccionar el archivo que se desea subir, en este caso ocuparemos el archivo ejemplo que se muestra a continuación.

[![archivo-ejemplo-lab.png](https://i.postimg.cc/4nCNd6T6/archivo-ejemplo-lab.png)](https://postimg.cc/N24wCrFF)

Después de haber seleccionado el archivo de acopio que se desea subir, se ingresa la quincena a la cual pertenecen esos datos en el formato ***AAAA/MM/Q***, y por ultimo se apreta el botón subir.

[![Seleccionar-opcion-subir-lab.png](https://i.postimg.cc/nzvRsmg4/Seleccionar-opcion-subir-lab.png)](https://postimg.cc/9RQtKDW0)

Si todo salio bien, la aplicación nos muestra un mensaje que el que se guarda correctamente.

[![confirmacion-lab.png](https://i.postimg.cc/J4KFcbnK/confirmacion-lab.png)](https://postimg.cc/zVbpqHTR)

Si seleccionamos la opción de ver los datos del laboratorio, podremos verificar que se agregaron los datos del laboratorio correctamente a la base de datos.

[![verificar-informacion-lab.png](https://i.postimg.cc/026Scv53/verificar-informacion-lab.png)](https://postimg.cc/zVr3G9gS)

### 5. Cómo ingresar un nuevo proveedor
Para poder ingresar un nuevo proveedor , se debe seleecionar la opción de obtener proveedores, en caso de estar en la página principal. 

[![Seleccionar-Planilla-pagos.png](https://i.postimg.cc/Yq1dbQCT/Seleccionar-Planilla-pagos.png)](https://postimg.cc/rKwGp0W9)

Luego selecionamos la opción de ingresar nuevo proveedor.

[![Seleccionar-opcion-nuevo-proveedor.png](https://i.postimg.cc/DZSSwQNZ/Seleccionar-opcion-nuevo-proveedor.png)](https://postimg.cc/34H8LGY5)

Ahora se rellena los datos que se solicitan para el nuevo proveedor, y se apreta el botón de subir.

[![Rellenar-datos.png](https://i.postimg.cc/sfYCkQ6C/Rellenar-datos.png)](https://postimg.cc/dDVfd1c5)

Para verificar que se a subido correctamente el proveedor podemos apretar el botón volver para regresar al listado de proveedores.

[![verificar-informacion-proveedor.png](https://i.postimg.cc/mkCLqmPv/verificar-informacion-proveedor.png)](https://postimg.cc/D4f34dd6)

### 6. Cómo generar la planilla de pagos
Para generar la planilla de pagos se debe seleccionar la opción de planilla de pagos en la página principal.

[![Seleccionar-Planilla-pagos.png](https://i.postimg.cc/Yq1dbQCT/Seleccionar-Planilla-pagos.png)](https://postimg.cc/rKwGp0W9)

