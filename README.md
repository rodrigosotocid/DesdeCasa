# CRUD - Alumnos Api Rest ©2020 

    La aplicación es un CRUD el cual nos muestra una lista de alumnos mostrandonos su datos principales como el id, nombre avatar y opciones de añadir nuevo, eliminar y seleccionar/modificar. Al seleccionar un alumno en concreto nos permite visualizar los cursos que ha ha adquirido, además, de tener la posibilidad de añadir otro curso si así lo quisiera (en una lista dentro de una ventana modal) o directamente eliminarlo.
  
  ![Imagen 1](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(1).png)
  
  ![imagen 2](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(4).png)
  
  ![imagen 3](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(3).png)
  
  ![imagen 4](https://github.com/istikis/DesdeCasa/blob/master/screenshots/Screenshot_CRUD%20-%20Alumnos%20(2).png)
  
  ***
  
# Explicación General del Proyecto.-

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
- alguna librería de JS por definir!!!

### Configuración
  
  En [esta ruta](https://github.com/istikis/DesdeCasa/blob/master/appclient/js/main.js) podemos encontrar nuestro fichero "main.js" en el cual "ednpoint" de nuestra Aplicación Rest.

#### Opciones del CRUD:
- <code>const endpoint = 'http://localhost:8080/apprest/api/';
- Obtener Personas GET: url = endpoint + 'personas/';
- Insetar Persona POST: url = endpoint + 'personas';
- Modificar Persona PUT: url = endpoint + 'personas/{id}'
- Borrar Persona DELETE: url = endpoint + 'personas/{id}'
- Asignar un Curso POST: url = endpoint + 'personas/{idPersona}/cursos{idCurso}'
- Borrar un curso de alumno DELETE: url = endpoint + 'personas/{idPersona}/cursos{idCurso}'
- Obtener cursos GET: url = endpoint + 'cursos/?filtro={String}'
  </code>

[Ruta Base de Datos](https://github.com/istikis/DesdeCasa/blob/master/apprest/alumnos_database.sql, "Acceso a la Base de Datos")

___
## AppRest

### Introducción

de que va la app rest

### Tecnología Usada

que tecnologia usamos jxs-rs, bean, etc
### Configuración

como esta estructurado mas o menos

## TAGs o Versiones

TAG 1 o v1.0

Sus princupales funcionalidades
Listado de Tag o versiones relevantes y describir su motivo

      
