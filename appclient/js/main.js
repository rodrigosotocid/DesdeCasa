//LISTA DE TAREAS
//TODO Probar bien todo el CRUD y si funciona crear TAG version 1.0

"use strict";
// este array se carga de forma asincrona mediante Ajax
//const endpoint = 'http://127.0.0.1:5500/js/data/personas.json';

/**
 * URL's Api Rest
 */
const endpoint = "http://localhost:8080/apprest/api/personas/";
const epCursos = "http://localhost:8080/apprest/api/cursos/";
const epContratados = "http://localhost:8080/apprest/api/contratados/";

//* Declaración de arrays
let personas = [];
let cursos = [];
let contratados = [];

window.addEventListener("load", init());

/**
 * Se ejecuta cuando todo esta cargado
 */
function init() {
  console.debug("Document Load and Ready");

  listener();
  initGallery();
  pintarLista();
  //pintarListaCurso()
  //pintarListaContratados()

  console.debug("...continua la ejecución del script de forma sincrona");
} //init

/**
 * Inicializamos los listener de index.hml
 */
function listener() {
  let selectorSexo = document.getElementById("selectorSexo");
  let inputNombre = document.getElementById("inombre");

  selectorSexo.addEventListener("change", filtro);
  inputNombre.addEventListener("keyup", filtro);
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

/**
 * Obtiene los datos del servicio rest y pinta la lista de Alumnos
 */
function pintarLista() {
  console.trace("pintarLista");

  const promesa = ajax("GET", endpoint, undefined);
  promesa
    .then((data) => {
      console.trace("promesa resolve");
      personas = data;
      maquetarLista(personas); 
    })
    .catch((error) => {
      console.warn("promesa rejectada");
      alert(error);
    });
} // pintarLista

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
      (lista.innerHTML += `<tr>
                              <th scope="row">${p.id}</th>
                              <td onclick="seleccionar(${i})">${p.nombre}</td>
                              <td onclick="seleccionar(${i})">
                                <img src="img/${p.avatar}" class="tabla-img" alt="Responsive image">
                              </td>
                              <td class="text-center">
                                <a class="btn-new btn-lg" href="#top"><i class="fas fa-plus" onclick="seleccionar()"></i></a>
                                <a class="btn-mod btn-lg" href="#top"><i class="far fa-edit" onclick="seleccionar(${i})"></i></a>
                                <a class="btn-del btn-lg"><i class="far fa-trash-alt" onclick="eliminar(${i})"></i></a>
                              </td>	
                            </tr>`)
  );
} //maquetarLista

/**
 * ELIMINAR
 * Se ejecuta al pulsar el boton de la papeleray llama al servicio rest para DELETE
 * @param {*} indice posicion del alumno dentro del array personas
 */
function eliminar(indice) {
  let personaSeleccionada = personas[indice];
  console.debug("click eliminar persona %o", personaSeleccionada);
  const mensaje = `¿Estas seguro que quieres eliminar  a ${personaSeleccionada.nombre} ?`;
  if (confirm(mensaje)) {
    const url = endpoint + personaSeleccionada.id;
    ajax("DELETE", url, undefined)
      .then((data) => pintarLista())
      .catch((error) => {
        console.warn("promesa rejectada");
        alert(error);
      });
  }
} // eliminar

/*
 * OBTENER TODOS BBDD
 */
function getAll(data) {
  ajax("GET", endpoint, data)
    .then((data) => {
      console.trace("promesa resolve");
      personas = data;
      pintarLista(personas);
      console.log("GET: Registros obtenidos correctamente! %o", personas);
    })
    .catch((error) => {
      console.warn("promesa rejectada");
      alert(error);
    });
}

/*
 * SELECCIONAR
 */
function seleccionar(indice) {
  let personaSeleccionada = {
    id: 0,
    nombre: "sin nombre",
    avatar: "avatar7.png",
    sexo: "h",
  };

  if (indice > -1) {
    personaSeleccionada = personas[indice];
  }

  console.debug("click guardar persona %o", personaSeleccionada);

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
  //pintarListaCurso()
  pintarListaContratados();
}

/**
 * GUARDAR
 * Llama al servicio Rest para hacer un POST ( id == 0) o PUT ( id != 0 )
 */
function guardar() {
  console.trace("click guardar");

  const id = document.getElementById("inputId").value;
  const nombre = document.getElementById("inputNombre").value;
  const avatar = document.getElementById("inputAvatar").value;

  // verificamos al radio que esté checado para elegir opción
  let sexo = document.getElementById("sexoh").checked ? "h" : "m";

  // Maquetamos persona en formato JSON
  let persona = {
    id: id,
    nombre: nombre,
    avatar: avatar,
    sexo: sexo,
  };

  console.debug("persona a guardar %o", persona);
  
  //TODO console.log('persona nombre %o',persona.nombre);
  nombreUnico(persona.nombre);
  
    if (id == 0) {
      console.trace("POST/INSERT: Persona");

      //CREAR
      ajax("POST", endpoint, persona)
        .then((data) => {
          alert(persona.nombre + " bienvenid@");
          //limpiar formulario
          document.getElementById("inputId").value = 0;
          document.getElementById("inputNombre").value = "";
          document.getElementById("inputAvatar").value = "img/avatar1.png";
          document.getElementById("sexoh").checked = true;
          document.getElementById("sexom").checked = false;

          getAll(data);
        })
        .catch((error) => {
          console.warn("POST - No ejecutado!");
          alert(error);
        });

      // MODIFICAR
    } else {
      console.trace("PUT/UPDATE: Persona");

      let url = endpoint + persona.id;

      ajax("PUT", url, persona)
        .then((data) => {
          getAll(data);
        })
        .catch((error) => {
          console.warn("PUT - No ejecutado!");
          alert(error);
        });
    }
/*   } else {
    alert("Lo siento! El nombre " + persona.nombre + " ya está en uso, prueba con otro!");
  } */
}

function nombreUnico(iNombre, listaNombre) {

}

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
 *
 * @param {*} evento
 */
function selectAvatar(evento) {
  console.trace("click avatar");

  const avatares = document.querySelectorAll("#gallery img");

  //eliminamos la clase 'selected' a todas las imagenes del div#gallery
  avatares.forEach((el) => el.classList.remove("selected"));

  // ponemos clase 'selected' a la imagen que hemos hecho click ( evento.target )
  evento.target.classList.add("selected");

  let iAvatar = document.getElementById("inputAvatar");
  //@see: https://developer.mozilla.org/es/docs/Learn/HTML/como/Usando_atributos_de_datos
  iAvatar.value = evento.target.dataset.path;
}

/* ********************** CURSO ********************** */

function pintarListaCurso() {
  console.trace("Ejecutando => pintarListaCurso()");

  const promesa = ajax("GET", epCursos, undefined);
  promesa
    .then((data) => {
      console.trace("promesa resolve");
      cursos = data;
      console.log("pintarListaCurso() => Ejecutado con éxito! %o ", cursos);
      maquetaCursos(cursos);
    })
    .catch((error) => {
      console.warn("promesa rejectada");
      alert(error);
    });
}

/**
 * Llamada a la API Rest para obtener lista de los Cursos
 * @param {cursos} cursos
 */

function maquetaCursos(cursos) {
  const listaCurso = document.getElementById("cursillos");
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
              <h5 class="card-title font-weight-bold">${c.nombre}</h5>
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
    console.log("Maquetado correcto en maquetaCursos() para:%o", c.nombre);
  });
}

$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').trigger('focus')
})

/* ********************** CURSOS CONTRATADOS ********************** */
/* function pintarListaContratados() {
  console.trace("Ejecutando => pintarListaCurso()");

  const promesa = ajax("GET", epContratados, undefined);
  promesa
    .then((data) => {
      console.trace("promesa resolve");
      contratados = data;
      console.log(
        "pintarListaContratados() => Ejecutado con éxito! %o ",
        contratados
      );
      maquetaContratados(contratados);
    })
    .catch((error) => {
      console.warn("promesa rejectada");
      alert(error);
    });
}

function maquetaContratados(contratados) {
  const listaContratados = document.getElementById("contratados");
  listaContratados.innerHTML = ""; // vaciar html
  contratados.forEach(
    (cc) =>
      (listaContratados.innerHTML += `
      <div id="card-cursos" class="card mb-3" style="max-width: 100%;">
        <div class="row no-gutters">
          <div class="col-md-4">
            <img src="img/android.png" class="card-img" alt="curso">
          </div>
          <div class="col-md-8">
            <div class="card-body">
              <h5 class="card-title font-weight-bold">Curso Android</h5>
              <p class="card-text"><span class="font-weight-bold">Precio:</span> 300 €</p>
              <a href="#" class="btn btn-danger mt-3" name="delete">Eliminar</a>
            </div>
          </div>
        </div>
      </div>`)
  );
  //const element = document.createElement('div');
  contratados.forEach((cc) => {
    console.log(
      "Maquetado correcto en maquetaContratados() para:%o",
      cc.persona
    );
    console.log("Maquetado correcto en maquetaContratados() para:%o", cc.curso);
  });
} */
