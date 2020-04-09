
//Fichero javascript para app

/* 
console.info("Esto lo puedes ver depurado en el navegador");
console.debug("esto es una traza de tipo debug");
console.trace("esto se usa para tracear o decir que entras y sales");
console.warn("Mensaje de Warning!");
console.error("Ha petado nuestra App"); 
*/

function pintarListaRest(PersonasRest){
    const url = `http://localhost:8080/apprest/api/personas/`;
  
    var request = new XMLHttpRequest();
  
    request.onreadystatechange = function () {
  
        if (this.readyState == 4 && this.status == 200) {
          const personasRest = JSON.parse(this.responseText);
  
          console.debug('Personas Rest %o', personasRest);
  
         /*  //seleccionar la lista por id
          let lista = document.getElementById('alumnos');
          lista.innerHTML = ''; // vaciar html 
          personasRest.forEach( p => lista.innerHTML += `<li>
                                                              <div class="card border border-warning" style="width: 10rem;">
                                                                  <p id="id-p-card" >${p.id}</p>
                                                                  <img src="../img/${p.avatar}" class="card-img-top" alt="Responsive image">
                                                                  <div class="card-body">
                                                                      <h2 class="card-title text-center"><b>${p.nombre}</b></h2>
                                                                  </div>
                                                              </div> 
                                                        </li>` );
        } */
  
        //seleccionar la lista por id
        let lista = document.getElementById('estudiantes');
        //lista.innerHTML = ''; // vaciar html 
        personasRest.forEach( p => lista.innerHTML += `
                                                          <td>${p.id}</td>
                                                          <td>${p.nombre}</td>
                                                          <td><img src="../img/${p.avatar}" class="card-img-top" alt="Responsive image"></td>
                                                       
                                                        ` );
      }
      };
      request.open("GET", url, true);
      request.send();
  }

  //selectorSexo.addEventListener('change', busqueda( selectorSexo.value, inputNombre.value ) );
  selectorSexo.addEventListener('change', function(){
    const sexo = selectorSexo.value;
    console.debug('select cambiado a: ' + sexo);
    if ( 't' != sexo ){
        const personasFiltradas = personas.filter( el => el.sexo == sexo );
        pintarLista(personasFiltradas);
    }else{
        pintarLista( personas );
    }    
});


