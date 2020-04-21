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
let personaSeleccionada = {};

window.addEventListener("load", init());

/**
 * Se ejecuta cuando todo esta cargado
 */
function init() {
  console.debug("Document Load and Ready");

  listener();
  initGallery();
  restPersonas();
  restCursos();

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
      restCursos();
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

  layoutPersonas(personasFiltradas);
} // filtro

/*-******************************** PERSONAS : ALUMNOS ********************************-*/
/**
 * Obtiene los datos del servicio rest y pinta la lista de Alumnos
 */
function restPersonas() {
  console.trace("restPersonas");

  const urlPersonas = endpoint + "personas/";
  const promesa = ajax("GET", urlPersonas, undefined);

  promesa
    .then((data) => {
      console.trace("promesa resolve");
      personas = data;
      layoutPersonas(personas); 
      misCursos(personas);
    })
    .catch((error) => {
      console.warn("promesa: Error al pintar lista de Personas");
      alert(error);
    });
} // restPersonas

/**
 * Maqueta el listado de Alumnos
 * @param {*} elementos alumnos a pintar
 */
function layoutPersonas(elementos) {
  console.trace("layoutPersonas");

  let lista = document.getElementById("alumnos");
  lista.innerHTML = ""; // vaciar html

  elementos.forEach(
    (p, i) =>
      (lista.innerHTML += `
        <tr>
          <th scope="row">${p.id}</th>
          <td onclick="seleccionar(${p.id})">${p.nombre}</td>
          <td onclick="seleccionar(${p.id})">
            <img src="img/${p.avatar}" class="tabla-img" alt="Responsive image">
          </td>
          <td><span class="fright" >${p.cursos.length} cursos</span></td>
          <td class="text-center">
            <a class="btn-new btn-lg" href="#top"><i class="fas fa-plus" onclick="seleccionar(${p.id})"></i></a>
            <a class="btn-mod btn-lg" href="#top"><i class="far fa-edit" onclick="seleccionar(${p.id})"></i></a>
            <a class="btn-del btn-lg"><i class="far fa-trash-alt" onclick="eliminar(${p.id})"></i></a>
          </td>	
        </tr>
                            `)
  );
} //layoutPersonas

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
      .then((data) => restPersonas())
      .catch((error) => {
        console.warn("promesa rejectada al intentar eliminar");
        alert(error);
      });
  }
} // eliminar

/**
 * SELECCIONAR
 * Se ejecuta al pulsar el boton de editar(al lado de la papelera) o boton 'Nueva Persona'
 * Rellena el formulario con los datos de la persona
 * @param {*} id id del alumno, si no existe en el array usa personaSeleccionada
 * @see personaSeleccionada = { "id":0, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };
 */
function seleccionar(id = 0) {

  //Utilizamos find() para hacer busquedas por índice
  const personaSeleccionada = personas.find(p => p.id = id);

  if(!personaSeleccionada){

    personaSeleccionada = {
      "id": 0,
      "nombre": "sin nombre",
      "avatar": "avatar7.png",
      "sexo": "h",
      "cursos": []
    };
  }

  console.debug("Click: Seleccionar Persona %o", personaSeleccionada);

  //rellenar formulario
  document.getElementById("inputId").value = personaSeleccionada.id;
  document.getElementById("inputNombre").value = personaSeleccionada.nombre;
  document.getElementById("inputAvatar").value = personaSeleccionada.avatar;

  //seleccionar Avatar
  const avatares = document.querySelectorAll("#gallery img");
  avatares.forEach((el) => {
    el.classList.remove("selected");
    if ("img/" + personaSeleccionada.avatar == el.dataset.path) {
      el.classList.add("selected");
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
  
  //TODO Test: despliega la lista de todos los cursos, luego será solo los que tiene el alumno
  //restPersonas();

}// SELECCIONAR

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

          restPersonas();
        })
        .catch((error) => {
          console.warn("Ups! POST No ejecutado! %o" , error);
          alert(error.informacion);
        });

      // MODIFICAR
    } else {

      console.trace("PUT/UPDATE: Persona");
      const url = endpoint + 'personas/' + persona.id;

      ajax("PUT", url, persona)
      .then( data => {
        alert( persona.nombre + ' modificado con exito ');
        restPersonas();
    })
        .catch((error) => {
          console.warn("PUT - No ejecutado! %o" , error);
          alert(error.informacion);
        });
    }
}// GUARDAR

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
}
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

function restCursos(filtro = '') {

  console.trace("Ejecutando => restCursos()");

  const urlCurso = endpoint + 'cursos/?filtro=' + filtro;
  const promesa = ajax("GET", urlCurso, undefined);

  promesa
    .then((data) => {
      console.trace("promesa resolve");
      cursos = data;

      console.log("restCursos: Ejecutado con éxito! %o ", cursos);
      layoutCursos(cursos);
      
    })
    .catch((error) => {
      console.warn("promesa rejectada al intentar pintar lista CURSO");
      alert(error);
    });
}

/**
 * Llamada a la API Rest para obtener lista de los Cursos desplegado en MODAL
 * @param {cursos} cursos
 */
function layoutCursos(cursos) {

  const listaCurso = document.getElementById("listaCursos");
  listaCurso.innerHTML = ""; // vaciar html
  cursos.forEach(
    (c) =>
      (listaCurso.innerHTML += `
        <div id="card-cursos" class="card mb-3" style="max-width: 100%;">
          <div class="row no-gutters">
            <div class="col-md-4">
              <img src="img/${c.imagen}" class="card-img" alt="curso">
            </div>
            <div class="col-md-8">
              <div class="card-body">
                <h5 class="card-title font-weight-bold">${c.nombre}hh</h5>
                <p class="card-text">
                  <span class="font-weight-bold">Precio:</span>
                  <span class="c-precio">${c.precio}</span> €
                </p>
                <a href="#" class="btn btn-danger mt-3" name="delete">Añadir Curso</a>
              </div>
            </div>
          </div>
        </div>
      `)
  );
  //const element = document.createElement('div');
  cursos.forEach((c) => {
    console.log("Maquetado correcto en layoutCursos() para:%o", c.nombre);
  });
}

/**
 * ELIMINAR CURSO
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function eliminarCurso( idPersona, idCurso ){

  console.debug(`click eliminarCurso idPersona=${idPersona} idCurso=${idCurso}`);

  const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;

  ajax('DELETE', url, undefined)
  .then( data => {
      alert('Curso Eliminado');

      //FIXME falta quitar curso del formulario, problema Asincronismo
      restPersonas();
      seleccionar(idPersona);
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
      alert('Curso Asignado');

      //FIXME falta pintar curso del formulario, problema Asincronismo
      restPersonas();
      seleccionar(idPersona);
  })
  .catch( error => alert(error));

}//asignarCurso

function misCursos(elementos){

  console.trace("misCursos");

  let lista = document.getElementById("misCursos");
  lista.innerHTML = ""; // vaciar html

  elementos.forEach(
    (p, i) =>
      (lista.innerHTML += `
        <li><img src="img/${p.avatar}" class="tabla-img" alt="Responsive image"></li>
        <li>${p.nombre}</li>
        <li>${p.cursos.forEach(el => el)}</li>
              `)
  );
}