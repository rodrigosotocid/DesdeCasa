//Fichero javascript para app

/* 
console.info("Esto lo puedes ver depurado en el navegador");
console.debug("esto es una traza de tipo debug");
console.trace("esto se usa para tracear o decir que entras y sales");
console.warn("Mensaje de Warning!");
console.error("Ha petado nuestra App"); 
*/

const personas = [
  {
    nombre: "Oconnor",
    avatar: "img/avatar1.png",
    sexo: "h",
  },
  {
    nombre: "Pepa",
    avatar: "img/avatar2.png",
    sexo: "m",
  },
  {
    nombre: "JoseMAri",
    avatar: "img/avatar3.png",
    sexo: "h",
  },
];

window.addEventListener("load", init());

function init() {
  // es importante esperar que todo este cargando para comenzar
  console.debug("Document Load and Ready");

  /* pintarLista(personasFiltradas);
  console.log(personasFiltradas); */
} //init

function pintarLista(arrayPersonas) {
  //seleccionar la lista por id
  let lista = document.getElementById("alumnos");
  lista.innerHTML = "";
  arrayPersonas.forEach(
    (p) =>
      (lista.innerHTML += `<li><img src="${p.avatar}" alt="avatar">${p.nombre}</li>`)
  );
}

function seleccionSexo() {
  let option = document.getElementById("selectorSexo").value;
  console.log(option);
  const personasFiltradas = personas.filter((p) => p.sexo == `${option}`);
  console.log(personasFiltradas);
  pintarLista(personasFiltradas);
}
