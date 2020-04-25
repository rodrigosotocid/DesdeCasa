"use strict";
// este array se carga de forma asincrona mediante Ajax
//const endpoint = 'http://127.0.0.1:5500/js/data/personas.json';

/**
 * URL's Api Rest
 */
const endpoint = "http://localhost:8080/apprest/api/";

//* Declaración de arrays
let personas = [];
let cursos = [];
let personaSeleccionada = { "id":0, 
                            "nombre": "sin nombre" , 
                            "avatar" : "img/avatar7.png", 
                            "sexo": "h",
                            "cursos": []
                          };

window.addEventListener("load", init());

/**
 * Se ejecuta cuando todo esta cargado
 */
function init() {
  console.debug("Document Load and Ready");

  listener();
  initGallery();
  cargarAlumnos();

  console.debug("...continua la ejecución del script de forma sincrona");
} //init

/**
 * Inicializamos los listener de index.hml
 * 1) Selector de 'sexo' y busqueda por nombre
 * 2) Filtro de Cursos
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
}

/**
 * Filtra las personas cuando se buscan por sexo y nombre
 */
function filtro() {

  let selectorSexo = document.getElementById("selectorSexo");
  let inputNombre = document.getElementById("inombre");

  const sexo = selectorSexo.value;
  const nombre = inputNombre.value.trim().toLowerCase();

  console.trace(`filtro sexo=${sexo} nombre=${nombre}`);
  console.debug("personas %o", personas);

  //creamos una copia para no modificar el original
  let personasFiltradas = personas.map((el) => el);

  //filtrar por sexo, si es 't' todos no hace falta filtrar
  if (sexo == "h" || sexo == "m") {
    personasFiltradas = personasFiltradas.filter((el) => el.sexo == sexo);
    console.debug("filtrado por sexo %o", personasFiltradas);
  }

  //filtrar por nombre buscado
  if (nombre != " ") {
    personasFiltradas = personasFiltradas.filter((el) =>
      el.nombre.toLowerCase().includes(nombre)
    );
    console.debug("filtrado por nombre %o", personasFiltradas);
  }

  maquetarLista(personasFiltradas);
} // filtro

/*-******************************** PERSONAS : ALUMNOS ********************************-*/
/**
 * Obtiene los datos del servicio rest y pinta la lista de Alumnos
 */
function cargarAlumnos() {
  console.trace("cargarAlumnos");

  const urlPersonas = endpoint + "personas/";
  const promesa = ajax("GET", urlPersonas, undefined);

  promesa
    .then((data) => {
      console.trace("promesa resolve");
      personas = data;
      maquetarLista(personas);
    })
    .catch((error) => {
      console.warn("promesa: Error al pintar lista de Personas");
      alert(error);
    });
} // cargarAlumnos

/**
 * Maqueta el listado de Alumnos
 * @param {*} elementos alumnos a pintar
 */
function maquetarLista(elementos) {
  console.trace("maquetarLista");

  let lista = document.getElementById("alumnos");
  lista.innerHTML = ""; // vaciar html

  elementos.forEach(
    (p, i) =>
      (lista.innerHTML += `
        <tr>
          <th hidden>${p.id}</th>
          <td onclick="seleccionar(${p.id})">${p.nombre}</td>

          <td onclick="seleccionar(${p.id})">
            <img src="img/${p.avatar}" class="tabla-img" alt="Responsive image">
          </td>

          <td onclick="seleccionar(${p.id})">${p.cursos.length} cursos</td>

          <td class="text-center p-0">
            <a class="btn-mod btn-lg" href="#top" title="Seleccionar"><i class="far fa-edit" onclick="seleccionar(${p.id})"></i></a>
            <a class="btn-del btn-lg" title="Eliminar"><i class="far fa-trash-alt" onclick="eliminar(${p.id})"></i></a>
          </td>	
        </tr>
                            `)
  );
} //maquetarLista

/**
 * ELIMINAR
 * Se ejecuta al pulsar el botón de la papelera y llama al Servicio REST para DELETE
 * @param {*} id corresponde al Id del Alumno
 */
function eliminar(id = 0) {

  let personaSeleccionada = personas.find( p => p.id == id);
  console.debug("Click: Eliminar Persona %o", personaSeleccionada);
  const mensaje = `¿Estas seguro que quieres eliminar a ${personaSeleccionada.nombre} ?`;

  if (confirm(mensaje)) {
    
    const url = endpoint + 'personas/' + personaSeleccionada.id;
    ajax("DELETE", url, undefined)
      .then((data) => cargarAlumnos())
      .catch((error) => {

        alert(error);
        console.trace(error);
        console.warn(error);

      });
  }
} //eliminar

/**
 * SELECCIONAR
 * Se ejecuta al pulsar el boton de editar(al lado de la papelera) o boton 'Nueva Persona'
 * Rellena el formulario con los datos de la persona
 * @param {*} id id del alumno, si no existe en el array usa personaSeleccionada
 * @see personaSeleccionada = { "id":0, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };
 */
function seleccionar( id = 0 ) {

/*  
  let cntFormulario = document.getElementById('content-formulario');
  cntFormulario.style.display = 'block';
  cntFormulario.classList.add('animated','fadeInRight'); */

  //Utilizamos find() para hacer busquedas por índice
  personaSeleccionada = personas.find( el=> el.id == id);
    if ( !personaSeleccionada ){
        personaSeleccionada = { "id":0, 
                                "nombre": "Nuevo Alumno" , 
                                "avatar" : "img/avatar7.png", 
                                "sexo": "h",
                                "cursos": []
                             };
    }

  console.debug("Click: Seleccionar Persona %o", personaSeleccionada);

  //rellenar formulario
  document.getElementById('inputId').value = personaSeleccionada.id;
  document.getElementById('inputNombre').value = personaSeleccionada.nombre;    
  document.getElementById('inputAvatar').value = personaSeleccionada.avatar;

  //seleccionar Avatar
  const avatares = document.querySelectorAll('#gallery img');
    avatares.forEach( el => {
        el.classList.remove('selected');
        if ( "img/"+personaSeleccionada.avatar == el.dataset.path ){
            el.classList.add('selected');
        }
    });

  const sexo = personaSeleccionada.sexo;
  let checkHombre = document.getElementById("sexoh");
  let checkMujer = document.getElementById("sexom");

  if (sexo == "h") {
    checkHombre.checked = "checked";
    checkMujer.checked = "";
  } else {
    checkHombre.checked = "";
    checkMujer.checked = "checked";
  }
  
  //Cursos del Alumno
  let misCursos = document.getElementById("mis-cursos");
  misCursos.innerHTML = '<h1 class="rounded-right mb-4">Mis Cursos</h1>'; 

  const ulElement = document.createElement('ul');
  ulElement.setAttribute('id', 'ulElement');

  personaSeleccionada.cursos.forEach( el => {
    ulElement.innerHTML += `
      <li id="liCursos">
        <div class="row m-0 d-flex justify-content-between">
         <img src="img/${el.imagen}" class="card-img m-1 mr-1" style="max-width: 50px;" alt="Imagen Curso">
         <h5 class="card-title pt-3">${el.nombre}</h5>
         <a class="btn- btn-lg pt-3" title="Elimina Curso">
           <i class="far fa-trash-alt" onclick="eliminarCurso(event, ${personaSeleccionada.id},${el.id})"></i>
         </a>
       </div>
      </li>
    `;
  });
  //TODO boton efecto eliminar
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

  // Maquetamos persona en formato JSON
  let persona = {

    "id": id,
    "nombre": nombre,
    "avatar": avatar,
    "sexo": sexo
  };

  console.debug("persona a guardar %o", persona);
  
    //CREAR
    if (id == 0) {

      console.trace("POST/INSERT: Persona");
      const url = endpoint + 'personas/';

      ajax("POST", url, persona)
        .then((data) => {
          alert("Hola " + persona.nombre + " bienvenid@");
          //limpiar formulario
          document.getElementById("inputId").value = 0;
          document.getElementById("inputNombre").value = '';
          document.getElementById("inputAvatar").value = 'img/avatar1.png';
          document.getElementById("sexoh").checked = true;
          document.getElementById("sexom").checked = false;

          cargarAlumnos();
        })
        .catch((error) => {
          console.warn("Ups! POST No ejecutado! JS %o" , error.informacion);
          alert("Lo siento este nombre ya existe! %o");
          //TODO 
        });

      // MODIFICAR
    } else {

      console.trace("PUT/UPDATE: Persona");
      const url = endpoint + 'personas/' + persona.id;

      ajax("PUT", url, persona)
      .then( data => {
        alert( persona.nombre + ' modificado con exito ');
        cargarAlumnos();
    })
        .catch((error) => {
          console.warn("PUT - No ejecutado! %o" , error);
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
  //@see: https://developer.mozilla.org/es/docs/Learn/HTML/como/Usando_atributos_de_datos
  iAvatar.value = evento.target.dataset.path;
}

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
      
      cursos.forEach(el =>
          listaCurso.innerHTML += `
          <div id="card-cursos" class="card mb-1" ">
            <div class="row align-items-center">

              <div class="col-3">
                <img src="img/${el.imagen}" id="img-all-cursos" class="card-img" alt="curso">
              </div>
              <div class="col-5">
                  <h5 class="card-title font-weight-bold">${el.nombre}</h5>
                  <p class="card-text">
                    <span class="font-weight-bold">Precio:</span>
                    <span class="c-precio">${el.precio}</span> €
                  </p>
              </div>
              <div class="col-4">
                <a href="#" id="añadir-curso" class="btn btn-danger" onClick="asignarCurso( 0, ${el.id})" >Añadir Curso</a>
              </div>
           
            </div>
          </div>
          `
      );
      seleccionar(personaSeleccionada.id); 
      
    })
    .catch((error) => {
      console.warn("promesa rejectada al intentar pintar lista de CURSOS");
      alert('No se pueden cargar cursos' + error);
    });
}//cargarCursos

/**
 * ELIMINAR CURSO
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function eliminarCurso( event, idPersona, idCurso  ){

  console.debug(`click eliminarCurso idPersona=${idPersona} idCurso=${idCurso}`);

  const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;

  ajax('DELETE', url, undefined)
  .then( data => {

      alert(data.informacion);

      event.target.parentElement.parentElement.parentElement.classList.add('animated','rollOut');
      //event.target.parentElement.parentElement.parentElement.remove();
      
      cargarAlumnos();
  })
  .catch( error => alert(error));

}//eliminarCurso

/**
 * ASIGNAR CURSO
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function asignarCurso( idPersona = 0, idCurso ){

  idPersona = (idPersona != 0) ? idPersona : personaSeleccionada.id;

  console.debug(`click asignarCurso idPersona=${idPersona} idCurso=${idCurso}`);

  const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;

  ajax('POST', url, undefined)
  .then( data => {

      alert(data.informacion);

      const curso = data.data;

      let lista = document.getElementById('ulElement');

      lista.innerHTML += `
                          <li id="liCursos">
                            <div class="row m-0 d-flex justify-content-between">
                              <img src="img/${curso.imagen}" class="card-img m-1 mr-1" style="max-width: 50px;" alt="Imagen Curso">
                              <h5 class="card-title pt-3">${curso.nombre}</h5>
                              <a class="btn- btn-lg pt-3" title="Elimina Curso">
                                <i class="far fa-trash-alt" onclick="eliminarCurso(event, ${idPersona},${curso.id})"></i>
                              </a>
                            </div>
                          </li>
                          `;
      cargarAlumnos();
      
  })
  .catch( (error) => {

    console.debug(error);
    alert(error);
  
  });

}//asignarCurso


/*
 * ANIMATE.CSS 
 */

 //MODAL FX
const element =  document.querySelector('.modal')
element.classList.add('animated', 'rollIn')