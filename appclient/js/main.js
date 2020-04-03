//Fichero javascript para app

console.info("Esto lo puedes ver depurado en el navegador");
console.debug("esto es una traza de tipo debug");
console.trace("esto se usa para tracear o decir que entras y sales");
console.warn("Mensaje de Warning!");
console.error("Ha petado nuestra App");

window.addEventListener('load', init());

function init(){
    console.debug('Document Load and Ready');
    // es importante esperar que todo este cragando para comenzar

    const personas = [
        {
            "nombre" : "Oconnor",
            "avatar" : "img/avatar1.png",
            "sexo"   : "h"
        },
        {
            "nombre" : "Pepa",
            "avatar" : "img/avatar2.png",
            "sexo"   : "m"
        },
        {
            "nombre" : "JoseMAri",
            "avatar" : "img/avatar3.png",
            "sexo"   : "h"
        }
    ];


    //seleccionar la lista por id
    let lista = document.getElementById('alumnos');
    lista.innerHTML = ''; // vaciar html 

    for(let i=0; i < personas.length; i++){
        const persona = personas[i];
        lista.innerHTML += `<li><img src="${persona.avatar}" alt="avatar">${persona.nombre}</li>`;
    }

    /*
    * let lista = document.getElementById('lista');
    * lista.innerHTML = '';
    *
    * for(let i=1; i < 7; i++){
    *   lista.innerHTML += `<li><img src="img/avatar${i}.png" alt="avatar"></li>`;
    * }
    */
}//init



