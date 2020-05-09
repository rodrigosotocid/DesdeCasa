let profesores = [];

const endpoint = "http://localhost:8080/apprest/api/";

window.addEventListener('load', init());

function init() {

    cargarProfesores();
}

/*-******************************** PROFESORES ********************************-*/

function cargarProfesores() {

    console.trace('Profesores');
  
    const url = endpoint + 'personas/';
    const promesa = ajax("GET", url, undefined);

    promesa
        .then(data => {

            profesores = data;

            let lista = document.getElementById('profesores');
            lista.innerHTML = ""; // vaciar html

            console.log('estos son mis profes %o :', profesores[0].rol);

            profesores.forEach(i => {
                if(i.rol == 2){

                    console.log('profesor %o', i);

                   
                        (lista.innerHTML += `
                            <tr>
                            <th hidden>${i.id}</th>
                            <td>${i.nombre}</td>
        
                            <td>
                                <img src="img/${i.avatar}" class="tabla-img" alt="Responsive image">
                            </td>
        
                            <td>${i.cursos.length} cursos</td>
        
                            <td class="text-center p-0">
                                <a class="btn-mod btn-lg" href="#top" title="Seleccionar"><i class="far fa-edit" onclick="seleccionar(${i.id})"></i></a>
                                <a class="btn-del btn-lg" title="Eliminar"><i class="far fa-trash-alt" onclick="eliminar(${i.id})"></i></a>
                            </td>	
                            </tr>
                            `)
                  
                }
            });


         /*    profesores.forEach(
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
            ); */



        })
        .catch((error) => {
            console.debug(error);
            alert(error.informacion);
        });
}