
/* // FUNCIONES DE LA TABLA
$(document).ready(function() {
  $('.table').DataTable({
          "language": {
              "url": "//cdn.datatables.net/plug-ins/1.10.20/i18n/Spanish.json"
          }
      } );
}); */

"use strict";
// este array se carga de forma asincrona mediante Ajax
//const endpoint = 'http://127.0.0.1:5500/js/data/personas.json';
const endpoint = 'http://localhost:8080/apprest/api/personas/';
let personas = [];

window.addEventListener('load', init() );

function init(){
    console.debug('Document Load and Ready');    
    listener();

    const promesa = ajax("GET", endpoint, undefined);
    promesa
    .then( data => {
            console.trace('promesa resolve'); 
            personas = data;
            pintarLista( personas );

    }).catch( error => {
            console.warn('promesa rejectada');
            alert(error);
    });

    console.debug('continua la ejecuion del script de forma sincrona');
    // CUIDADO!!!, es asincrono aqui personas estaria sin datos
    // pintarLista( personas );

}//init

/**
 * Inicializamos los listener de index.hml
 */
function listener(){

    let selectorSexo = document.getElementById('selectorSexo');
    let inputNombre = document.getElementById('inombre');

    selectorSexo.addEventListener('change', busqueda( selectorSexo.value, inputNombre.value ) );
    
    inputNombre.addEventListener('keyup', function(){
        const busqueda = inputNombre.value.toLowerCase();
        console.debug('tecla pulsada, valor input ' +  busqueda );
        if ( busqueda ){
            const personasFiltradas = personas.filter( el => el.nombre.toLowerCase().includes(busqueda));
            pintarLista(personasFiltradas);
        }else{
            pintarLista(personas);
        }    
    });
}

function pintarLista( arrayPersonas ){
  
  //seleccionar la lista por id
  let lista = document.getElementById('estudiantes');
  lista.innerHTML = ''; // vaciar html 
  lista.innerHTML = `<thead class="head-tabla class="thead-light"">
                        <tr>
                          <th>Id </th>
                          <th>Nombre</th>
                          <th>Avatar</th>
                          <th>Opciones</th>
                        </tr>
                    </thead>`;

  arrayPersonas.forEach( p => lista.innerHTML += `<tbody>
                                                      <tr>
                                                        <td>${p.id}</td>
                                                        <td>${p.nombre}</td>
                                                        <td>
                                                            <img src="../img/${p.avatar}" class="tabla-img" alt="Responsive image">
                                                        </td>
                                                        <td class="text-center">
                                                            <a class="btn-new btn-lg"><i class="fas fa-plus"></i></a>
                                                            <a class="btn-mod btn-lg"><i class="far fa-edit"></i></a>
                                                            <a class="btn-del btn-lg"><i class="far fa-trash-alt"></i></a>
                                                        </td>	
                                                      </tr>
                                                  </tbody>
                                                
                                                        ` );

}

function eliminar(indice){
  let personaSeleccionada = personas[indice];
  console.debug('click eliminar persona %o', personaSeleccionada);
  const mensaje = `¿Estas seguro que quieres eliminar  a ${personaSeleccionada.nombre} ?`;
  if ( confirm(mensaje) ){

      //TODO mirar como remover de una posicion
      //personas = personas.splice(indice,1);
      personas = personas.filter( el => el.id != personaSeleccionada.id) 
      pintarLista(personas);
      //TODO llamada al servicio rest

  }

}

function seleccionar(indice){

  let  personaSeleccionada = { "id":0, "nombre": "sin nombre" };

  if ( indice != 0 ){
      personaSeleccionada = personas[indice];
  }
  
  console.debug('click guardar persona %o', personaSeleccionada);
 
  //rellernar formulario
  document.getElementById('inputId').value = personaSeleccionada.id;
  document.getElementById('inputNombre').value = personaSeleccionada.nombre;
 
}

function guardar(){

  console.trace('click guardar');
  let id = document.getElementById('inputId').value;
  let nombre = document.getElementById('inputNombre').value;

  let persona = {
      "id" : id,
      "nombre" : nombre,
      "avatar" : "avatar7.png"
  };

  console.debug('persona a guardar %o', persona);

  //TODO llamar servicio rest

  personas.push(persona);
  pintarLista(personas);

}

function busqueda( sexo = 't', nombreBuscar = '' ){

    console.info('Busqueda sexo %o nombre %o', sexo, nombreBuscar );
}


