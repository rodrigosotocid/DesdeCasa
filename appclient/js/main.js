//Fichero javascript para app

console.info("Esto lo puedes ver depurado en el navegador");
console.debug("esto es una traza de tipo debug");
console.trace("esto se usa para tracear o decir que entras y sales");
console.warn("Mensaje de Warning!");
console.error("Ha petado nuestra App");


let lista = document.getElementById('lista');
lista.innerHTML = '';

for(let i=1; i < 7; i++){
    lista.innerHTML += `<li><img src="img/avatar${i}.png" alt="avatar"></li>`;
}