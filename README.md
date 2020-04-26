# CRUD - Alumnos Api Rest ©2020 

La aplicación es un CRUD el cual consta de dos partes Backend y Frontend, tenemos una lista de alumnos y su datos principales como el id, nombre avatar y opciones de añadir nuevo, eliminar y seleccionar/modificar. Al seleccionar un alumno en concreto nos permite visualizar los cursos que ha ha adquirido, además, de tener la posibilidad de añadir otro o eliminar si así lo quisiera.
  
  ![Imagen 1](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(1).png)
  
  ![imagen 2](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(2).png)
  
  ***
## AppCliente

### Introducción

 La parte de Cliente destaca en dos areas principales:
  1. Interfaz Gráfica
  2. Lógica de Cliente

**Interfaz Gráfica:** Aquí podemos visualizar la distribución de los elementos de nuestra aplicación, de una forma que nos permita su manejo de forma intuitiva y ágil. 

   Contamos con una tabla principal en la cual nos muestra todos los Alumnos de nuestra base de datos y su información mas relevante tales como el Id, Nombre, Avatar, Cursos que tiene adquiridos y un apartado de opciones en el cual podemos crear Nuevo, Seleccionar o Eliminar Alumno.
   
   En la parte derecha de la aplicación tenemos un un formulario el cual nos muestra el Id, nombre, avatar y sexo del Alumno, como también la información si este ha sido seleccionado, así, podremos modificar sus datos o desplegar el apartado de cursos para hacer las modificaciones que necesitemos.-

**Lógica de Cliente:** En nuestra lógica hemos construido diversas funciones las cuales no ayudan con las tareas de cargar Alum nos y Cursos, seleccionar Alumno, distintos filtros tanto para lumnos como para los cursos, llamadas Ajax para añadir, modificar o eliminar Alumnos o Cursos y maquetar algunas secciones de código desde JavaScript entre otras.- 

### Tecnología Usada

- HTML 5
- CSS 3
- JavaScript ES6
- Bootstrap v4.4
- Font Awesome v5.13.0
- Animate.css
- Insomnia (Testing API REST)

### Configuración
El código html de nuestra aplicación se encuentra en el archivo [index.html](https://github.com/istikis/DesdeCasa/blob/master/appclient/index.html), en el archivo [main.js](https://github.com/istikis/DesdeCasa/blob/master/appclient/js/main.js) podemos encontrar el endpoint por si se quiere modificar y la configuración para la llamada a la API  por ajax en [ajax.js](https://github.com/istikis/DesdeCasa/blob/master/appclient/js/ajax.js)

```javascript
URI Principal de la aplicación

const endpoint = "http://localhost:8080/apprest/api/";
```
___
## AppRest

### Introducción

AppRest es el 'Backend' de nuestra aplicación, la cual está desarrollada en Java EE utilizando Maven como gestor de dependencias y MySQL para crear y gestionar la Base de Datos. 

### Tecnología Usada
- Java EE
- Maven
- JAX-RS
- MySQL

### Configuración

* Puedes descargar aquí el [script de la Base de Datos](https://github.com/istikis/DesdeCasa/blob/master/apprest/alumnos_database.sql, "Acceso a la Base de Datos") :arrow_down:
* En el archivo [context.xml](https://github.com/istikis/DesdeCasa/blob/master/apprest/WebContent/META-INF/context.xml) podemos ver los datos de configuración para el acceso a la base de datos o el tipo de driver utilizado.-
* En el archivo [pom.xml](https://github.com/istikis/DesdeCasa/blob/master/apprest/pom.xml) podemos visualizar las dependencia usadas en este proyecto.-
* En el archivo [web.xml](https://github.com/istikis/DesdeCasa/blob/master/apprest/WebContent/WEB-INF/web.xml) contiene las siguientes caracteristicas:

  * `<welcome-file>index.jsp</welcome-file>` Página de inicio del servidor donde nos lleva al endpoint
  * `<servlet-name>javax.ws.rs.core.Application</servlet-name>` REST
  * `<url-pattern>/api/*</url-pattern>` Ruta base de nuestra URI

```javascript
const endpoint = "http://localhost:8080/apprest/api/";
```
### Personas
```javascript
'GET'= endpoint + 'personas/' -> Nos permite OBTENER TODOS los registros de Personas.
```
```javascript
'POST': = endpoint + 'personas/' -> Nos permite AÑADIR una nueva Persona.
```
```javascript
'PUT': = endpoint + 'personas/{id}' -> Nos permite MODIFICAR el registro de una Persona.
```
```javascript
'DELETE': = endpoint + 'personas/{id}' -> Nos permite ELIMINAR el registro de una Persona.
```
### Cursos
```javascript
'GET'= endpoint + 'cursos/?filtro={String}' -> Nos permite OBTENER TODOS los registros de los Cursos de 
nuestra base de datos.
```
```javascript
'POST': = endpoint + 'personas/{idPersona}/cursos{idCurso}' -> Nos permite ASIGNAR un Curso una Persona.
```
```javascript
'DELETE': = endpoint + 'personas/{idPersona}/cursos{idCurso}' -> Nos permite ELIMINAR un Curso asociado a 
una Persona.
```
## Diagrama Entidad Relación
![Imagen Entidad Relacion](https://github.com/istikis/DesdeCasa/blob/master/screenshots/der.png)

## TAGs o Versiones

- Tag 1 o v1.0

<!-- Task List -->
* [x] v1.0 - Primera versión estable.
* [x] v2.0 - Añadida funcionalidad de asignar, eliminar cursos y cambio estetico.
* [ ] v3.0 - Versión en proceso de desarrollo.  


 ___

 ### Otros Proyectos en la Web :link:

 -  [Dietogenica: Html / CSS y algo de JS](https://dietogenica.netlify.app/ )
 - [Administración Veterinaria: Web SPA desarrollada con React JS](https://admveterinaria.netlify.app/)
 - [Api de Noticias: Desarrollada con React JS](https://reactnewsapi.netlify.app/)

___
