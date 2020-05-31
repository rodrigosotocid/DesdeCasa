"use strict";

/**
 * URL's Api Rest
 */
const endpoint = "http://localhost:8080/apprest/api/";

let profesores = [];
let cursos = [];
let profesorSeleccionado = { "id":0, 
                            "nombre": "sin nombre" , 
                            "avatar" : "img/avatar7.png", 
                            "sexo": "h",
                            "cursos": []
                          };

window.addEventListener('load', init());

/**
 * Se ejecuta cuando todo esta cargado
 */
function init() {

    listener();
    initGallery();
    cargarProfesores();

}//init


/**
 * Inicializamos los listener de profesores.hml
 * 1) Selector de 'sexo' y busqueda por nombre
 * 2) Filtro de Cursos
 * 3) Filtro para buscar profesor por nombre
 * @see function filtro
 */
function listener() {

    //1) selector de sexo y busqueda por nombre
   let selectorSexo = document.getElementById("selectorSexo");
   let inputNombre = document.getElementById("inombre");
 
   selectorSexo.addEventListener("change", filtro);
   inputNombre.addEventListener("keyup", filtro);
 
   //2) Filtro de Cursos
   let filtroCursos = document.getElementById('filtroCurso');
 
   filtroCursos.addEventListener('keyup', function(event) {
     let filtroValor = filtroCursos.value.trim();
 
     if(filtroValor.length >= 3) {
       console.debug('filtroCursos keyup' + filtroValor);
       cargarCursos(filtroValor);
     } else {
       cargarCursos();
     }
   });
 
   //3) Filtro para buscar profesor por nombre
   let iNombre = document.getElementById('inputNombre');
   let msgValidador = document.getElementById('nombre-mensaje');
 
   iNombre.addEventListener('keyup', () => {
     console.debug('tecla pulsada ' + iNombre.value );
 
     const url = endpoint + 'personas/?filtro=' + iNombre.value;
 
     if ( profesorSeleccionado.nombre != iNombre.value ){
     ajax('GET', url, undefined)
       .then( (data) => {
         console.debug('nombre NO disponible');
         
         msgValidador.textContent = 'Nombre NO disponible';
         msgValidador.classList.add('invalid');
         msgValidador.classList.remove('valid');
       } )
       .catch( ( error ) => {
 
         console.debug('Nombre disponible');
 
         console.log(error.informacion);
         console.log(error.hypermedias[0]);
         console.log(error.hypermedias[1]);
         msgValidador.textContent = 'Nombre disponible';
         msgValidador.classList.add('valid');
         msgValidador.classList.remove('invalid');
       });
 
     }
 
   });
 
 } //listener

 /**
 * Filtra los profesores cuando se buscan por sexo y nombre
 */
function filtro() {

    let selectorSexo = document.getElementById("selectorSexo");
    let inputNombre = document.getElementById("inombre");
  
    const sexo = selectorSexo.value;
    const nombre = inputNombre.value.trim().toLowerCase();
  
    console.trace(`filtro sexo=${sexo} nombre=${nombre}`);
    console.debug("profesores %o", profesores);
  
    //creamos una copia para no modificar el original
    let profesoresFiltrados = profesores.map((el) => el);
  
    //filtrar por sexo, si es 't' todos no hace falta filtrar
    if (sexo == "h" || sexo == "m") {
      profesoresFiltrados = profesoresFiltrados.filter((el) => el.sexo == sexo);
      console.debug("filtrado por sexo %o", profesoresFiltrados);
    }
  
    //filtrar por nombre buscado
    if (nombre != " ") {
      profesoresFiltrados = profesoresFiltrados.filter((el) =>
        el.nombre.toLowerCase().includes(nombre)
      );
      console.debug("filtrado por nombre %o", profesoresFiltrados);
    }
  
    maquetarLista(profesoresFiltrados);
  } // filtro

/*-******************************** PROFESORES ********************************-*/
/**
 * 
 */
function cargarProfesores() {

    console.trace('Profesores');
  
    const url = endpoint + 'personas/?rol=profesor';
    const promesa = ajax("GET", url, undefined);

    promesa
        .then(data => {

            profesores = data;
            console.log('Array de Profesores:%o', profesores);

            let lista = document.getElementById('profesores');
            lista.innerHTML = ""; // vaciar html

            profesores.forEach(i => {
                
                        (lista.innerHTML += `
                            <tr>
                            <th hidden>${i.id}</th>
                            <td> ${i.nombre}</td>
                            <td>
                                <img src="img/${i.avatar}" class="tabla-img" alt="Responsive image">
                            </td>
                            <td>${i.cursos.length} cursos</td>
                            <td class="text-center p-0">
                                <a class="btn-mod btn-lg" href="#top" title="Editar"><i class="far fa-edit" onclick="seleccionar(${i.id})"></i></a>
                                <a class="btn-del btn-lg" title="Eliminar"><i class="far fa-trash-alt" onclick="eliminar(${i.id})"></i></a>
                            </td>	
                            </tr>
                            `)
            });

        })
        .catch((error) => {
            console.warn("promesa: Error al cargar los Profesores");
            console.debug(error);
            alert(error.informacion);
        });

}//cargarProfesores

/**
 * ELIMINAR
 * Se ejecuta al pulsar el botón de la papelera y llama al Servicio REST para DELETE
 * @param {*} id corresponde al Id del Alumno
 */
function eliminar(id = 0) {

    let profesorSeleccionado = profesores.find( p => p.id == id);
    console.debug("Click: Eliminar Profesor %o", profesorSeleccionado);
    const mensaje = `¿Estas seguro que quieres eliminar a ${profesorSeleccionado.nombre} ?`;
  
    if (confirm(mensaje)) {
      
      const url = endpoint + 'personas/' + profesorSeleccionado.id;
      ajax("DELETE", url, undefined)
        .then((data) => cargarProfesores())
        .catch((error) => {
  
          alert(error);
          console.trace(error);
          console.warn(error);
  
        });
    }
  } //eliminar

/**
 * SELECCIONAR
 * Se ejecuta al pulsar el boton de 'editar' dela tabla o el botón 'Nuevo' de la parte supeior de la misma
 * Rellena el formulario con los datos del Profesor
 * @param {*} id id del Profesor, si no existe en el array usa profesorSeleccionado
 * @see profesorSeleccionado = { "id":0, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };
 */
function seleccionar( id = 0 ) {

      //Utilizamos find() para hacer busquedas por índice
      profesorSeleccionado = profesores.find( el=> el.id == id);
        if ( !profesorSeleccionado ){
            profesorSeleccionado = { "id":0, 
                                    "nombre": "" , 
                                    "avatar" : "img/avatar1.png", 
                                    "sexo": "h",
                                    "cursos": [],
                                    "rol": {
                                      "id": 2,
                                      "tipo": "profesor"
                                    }
                                 };
    
                                 // Pone el cursor en el campo escribir nombre al dar botón 'Nuevo'
                                 //document.getElementById('inputNombre').focus();
        }
    
      console.debug("Click: Seleccionar Profesor %o", profesorSeleccionado);
    
      //rellenar formulario
      document.getElementById('inputId').value = profesorSeleccionado.id;
      document.getElementById('inputNombre').value = profesorSeleccionado.nombre;    
      document.getElementById('inputAvatar').value = profesorSeleccionado.avatar;
    
    
      //seleccionar Avatar
      const avatares = document.querySelectorAll('#gallery img');
        avatares.forEach( el => {
            el.classList.remove('selected');
            if ( "img/"+profesorSeleccionado.avatar == el.dataset.path ){
                el.classList.add('selected');
            }
        });
    
      const sexo = profesorSeleccionado.sexo;
      let checkHombre = document.getElementById("sexoh");
      let checkMujer = document.getElementById("sexom");
    
      if (sexo == "h") {
        checkHombre.checked = "checked";
        checkMujer.checked = "";
      } else {
        checkHombre.checked = "";
        checkMujer.checked = "checked";
      }
      
      //Cursos del Profesor
      let misCursos = document.getElementById("mis-cursos");
      misCursos.innerHTML = '<h1 class="rounded-right mb-4">Mis Cursos</h1>'; 
    
      const ulElement = document.createElement('ul');
      ulElement.setAttribute('id', 'ulElement');
    
      profesorSeleccionado.cursos.forEach( el => {
        ulElement.innerHTML += `
          <li id="liCursos">
            <div class="row m-0 d-flex justify-content-between">
             <img src="img/${el.imagen}" class="card-img m-1 mr-1" style="max-width: 50px;" alt="Imagen Curso">
             <h5 class="card-title pt-3">
              <span class="nombre-curso-asignado">Curso de </span>${el.nombre}
              <span class="nombre-curso-asignado">impartido por </span>${el.profesor.nombre ? el.profesor.nombre : 'No asignado'}
             </h5>
             <a class="btn- btn-lg pt-3" title="Elimina Curso">
               <i class="far fa-trash-alt" onclick="borraProfesorCurso(event, ${el.id})"></i>
             </a>
           </div>
          </li>
        `;
      });
      misCursos.appendChild(ulElement);
    
    }//seleccionar

/**
 * GUARDAR
 * Llama al servicio Rest para hacer un POST ( id == 0) o PUT ( id != 0 )
 */
function guardar() {

    console.trace("Click: Guardar");
  
    const id = document.getElementById("inputId").value;
    const nombre = document.getElementById("inputNombre").value;
    const avatar = document.getElementById("inputAvatar").value;
  
    // verificamos al radio que esté checado para elegir opción
    let sexo = document.getElementById("sexoh").checked ? "h" : "m";
  
    // Maquetamos profesor en formato JSON
    let profesor = {
  
      "id": id,
      "nombre": nombre,
      "avatar": avatar,
      "sexo": sexo,
      "rol": {
        "id": 2,
        "tipo": "profesor"
      }
    };
  
    console.debug("profesor a guardar %o", profesor);
    
      //CREAR
      if (id == 0) {
  
        console.trace("POST/INSERT: profesor");
        const url = endpoint + 'personas/';
  
        ajax("POST", url, profesor)
          .then((data) => {
            alert("Hola " + profesor.nombre + " bienvenid@ a nuestro equipo de profesores!");
            //limpiar formulario
            document.getElementById("inputId").value = 0;
            document.getElementById("inputNombre").value = '';
            document.getElementById("inputAvatar").value = 'img/avatar1.png';
            document.getElementById("sexoh").checked = true;
            document.getElementById("sexom").checked = false;
  
            cargarProfesores();
          })
          .catch((error) => {
            console.warn("POST Error: %o" , error.informacion);
            alert(error.informacion);
          });
  
        // MODIFICAR
      } else {
  
        console.trace("PUT/UPDATE: profesor");
        const url = endpoint + 'personas/' + profesor.id;
  
        ajax("PUT", url, profesor)
        .then( data => {
          alert('El nombre ' +  profesor.nombre + ' ha sido modificado con exito ');
          cargarProfesores();
      })
          .catch((error) => {
            console.warn("PUT - No ejecutado! %o" , error.informacion);
            alert(error.informacion);
          });
      }
  }//guardar

/**
 * Carga todas las imagenes de los avatares
 */
function initGallery() {
    let divGallery = document.getElementById("gallery");
  
    for (let i = 1; i <= 16; i++) {
      divGallery.innerHTML += `
        <img 
          onclick="selectAvatar(event)" 
          class="avatar" 
          data-path="avatar${i}.png"
          src="img/avatar${i}.png"
        >`;
    }
  }//initGallery

  /**
 * Evento de selección de Avatar
 * @param {*} evento
 */
function selectAvatar(evento) {

    console.trace("Click: Avatar");
  
    const avatares = document.querySelectorAll("#gallery img");
  
    //eliminamos la clase 'selected' a todas las imagenes del div#gallery
    avatares.forEach((el) => el.classList.remove("selected"));
  
    // ponemos clase 'selected' a la imagen que hemos hecho click ( evento.target )
    evento.target.classList.add("selected");
  
    let iAvatar = document.getElementById("inputAvatar");
    iAvatar.value = evento.target.dataset.path;

  }//initGallery

  /*-******************************** CURSO ********************************-*/
/**
 * CARGA TODOS LOS CURSOS EN VENTANA MODAL
 * @param {*} filtro por nombre de curso, busca coincidencias
 */
function cargarCursos(filtro = '') {

  console.trace("Ejecutando => cargarCursos()");

  const url = endpoint + 'cursos/?filtro=' + filtro;
  const promesa = ajax("GET", url, undefined);

  promesa
    .then((data) => {

      console.trace("promesa resolve");

      cursos = data;
      const listaCurso = document.getElementById("listaCursos");
      listaCurso.innerHTML = ""; // vaciar html
      
      // Vista de los cursos desplegable (Modal)
      cursos.forEach(el =>
          listaCurso.innerHTML += `
          <div id="card-cursos" class="card mb-1" ">
            <div class="row align-items-center">

              <div class="col-3">
                <img src="img/${el.imagen}" id="img-all-cursos" class="card-img" alt="curso">
              </div>
              <div class="col-5">
                  <h5 class="card-title ">
                    <span class="nombre-curso-modal">${ el.nombre} </span><br>
                    <span class="curso-profesor-modal">Profesor: </span> ${el.profesor.nombre ? el.profesor.nombre : 'No asignado'}
                  </h5>
                  <p class="card-text">
                    <span class="font-weight-bold">Precio:</span>
                    <span class="c-precio">${el.precio}</span> €
                  </p>
              </div>
              <div class="col-4">
                <a href="#" id="añadir-curso" class="btn btn-danger" onClick="addProfesorCurso(${el.id})" >Añadir Curso</a>
              </div>
           
            </div>
          </div>
          `
      );
      seleccionar(profesorSeleccionado.id); 
      
    })
    .catch((error) => {
      console.warn("Error al intentar pintar lista de CURSOS");
      alert('No se pueden cargar cursos--> ' + error);
    });
}//cargarCursos

/**
 * ASIGNAR CURSO (Curso getById en appRest)
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function asignarCurso( idPersona = 0, idCurso ){

  idPersona = (idPersona != 0) ? idPersona : profesorSeleccionado.id;

  console.debug(`click asignarCurso idPersona=${idPersona} idCurso=${idCurso}`);

  const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;

  ajax('POST', url, undefined)
  .then( data => {

      alert(data.informacion);
    
      const curso = data.data;
      
      let lista = document.getElementById('ulElement');
      // Vista del curso recién añadido
      lista.innerHTML += `
                          <li id="liCursos">
                            <div class="row m-0 d-flex justify-content-between">
                              <img src="img/${curso.imagen}" class="card-img m-1 mr-1" style="max-width: 50px;" alt="Imagen Curso">
                              <h5 class="card-title pt-3">
                                <span class="nombre-curso-asignado">Curso de </span>${curso.nombre} 
                                <span class="nombre-curso-asignado">impartido por </span>${curso.profesor.nombre ? curso.profesor.nombre : 'No asignado'}
                              </h5>
                              <a class="btn- btn-lg pt-3" title="Elimina Curso">
                                <i class="far fa-trash-alt" onclick="borraProfesorCurso(event, ${curso.id})"></i>
                              </a>
                            </div>
                          </li>
                          `;
      cargarProfesores();
      
  })
  .catch( (error) => {

    console.debug(error);
    alert(error);
  
  });

}//asignarCurso


/**
 * Modifica el valor de la columna id_persona de la tabla Curso
 * su valor pasa a null para desvincular el curso del profesor.
 * @param {*} event 
 * @param {*} idCurso 
 */
function borraProfesorCurso( event , idCurso ) {

  console.trace("Click: borraProfesorCurso --> %o", idCurso);

  //Encuentra el id coincidente
  let cursoUpdate = profesorSeleccionado.cursos.find( el=> el.id == idCurso);
  console.log('curso update '+ cursoUpdate);

  //Si es null el profesor asignado e este curso se elimina
  cursoUpdate.profesor = null;
  console.log('Si es null el profesor asignado e este curso se elimina' + cursoUpdate.profesor);

  const url = endpoint + 'cursos/' + idCurso;

  ajax('PUT', url, cursoUpdate)
    .then(data => {

      alert("Has dejado de impartir el curso de " + data.nombre);
  
      event.target.parentElement.parentElement.parentElement.classList.add('animated','rollOut');

      // Obtengo una HTMLCollection y elimino el componente según su posición
      let htmlArray = document.getElementsByClassName('animated');
      htmlArray[0].remove();

      cargarProfesores();
    })
    .catch( error => {

      alert("Error: " + error);
      console.warn("Error:" + error);

    });
}//borraProfesorCurso


function addProfesorCurso( idCurso ) {

  console.trace("Click: addProfesorCurso --> %o", idCurso);

  //Encuentra el id coincidente sino con el if te avisa que ya lo tienes
  let cursoUpdate = profesorSeleccionado.cursos.find( el=> el.id == idCurso);

  if(cursoUpdate == null){

  cursoUpdate = cursos.find( el=> el.id == idCurso);
  
  cursoUpdate.profesor.id = profesorSeleccionado.id ;

  const url = endpoint + 'cursos/' + idCurso;

  ajax('PUT', url, cursoUpdate)
    .then(data => {

      const curso = data;
      alert('Curso asignado con éxito')                    
      
      let lista = document.getElementById('ulElement');
      // Vista del curso recién añadido
      lista.innerHTML += `
                          <li id="liCursos">
                            <div class="row m-0 d-flex justify-content-between">
                              <img src="img/${curso.imagen}" class="card-img m-1 mr-1" style="max-width: 50px;" alt="Imagen Curso">
                              <h5 class="card-title pt-3">
                                <span class="nombre-curso-asignado">Curso de </span>${curso.nombre} 
                                <span class="nombre-curso-asignado">impartido por </span>${curso.profesor.nombre}
                              </h5>
                              <a class="btn- btn-lg pt-3" title="Elimina Curso">
                                <i class="far fa-trash-alt" onclick="borraProfesorCurso(event, ${curso.id})"></i>
                              </a>
                            </div>
                          </li>
                          `;

    seleccionar(profesorSeleccionado.id); 
  
  })
  .catch( error => {
    
    alert("Error: " + error);
    console.warn("Error:" + error);
    cargarCursos();
  });
  
  
  

  } else {
    alert('Lo siento, ¡Ya tienes este curso! ');
    cargarCursos();
  }

}//addProfesorCurso

/*
 * ANIMATE.CSS 
 */

 //MODAL FX
const element =  document.querySelector('.modal')
element.classList.add('animated', 'rollIn')