let noticas = [];

const endpoint = "http://localhost:8080/apprest/api/";

window.addEventListener('load', init());

function init() {

    cargarNoticias();
}

/*-******************************** NOTICIAS ********************************-*/
/**
 * 
 */
function cargarNoticias() {

    console.trace('Noticias');
  
    const url = endpoint + 'noticias/';
    const promesa = ajax("GET", url, undefined);
  
    promesa
      .then((data) => {
        console.trace('Promesa of Noticias is Resolve');
        noticias = data;
  
        //Carousel Noticias
        const CarouselNoticias = document.getElementById('carousel-noticias');
        CarouselNoticias.innerHTML= '';

        noticias.forEach( el => 
          CarouselNoticias.innerHTML+=`
          <div id="activo" class="carousel-item ">
                    <img src="img/noticias/${el.imagen}" class="d-block" alt="noticia_1">
                    <div class="carousel-caption d-none d-md-block">
                      <h5>${el.titulo}</h5>
                      <p>${el.contenido}</p>
                    </div>
          </div>
        `
        );
        //Añade la clase 'active' para permitir el slide del carousel
        document.getElementById('activo').classList.add('active');

   
        //Card Noticias
        const CardNoticias = document.getElementById('card-noticias');
        CardNoticias.innerHTML= '';
  
        noticias.forEach( el => 
          CardNoticias.innerHTML+=`
          <div class="card">
            <img src="img/noticias/${el.imagen}" class="card-img-top" alt="Card Noticia">
                <div class="card-body">
                  <h5 class="card-title">${el.titulo}</h5>
                  <p class="card-text">${el.contenido}</p>
                </div>
                <div class="card-footer">
                  <small class="text-muted">Fecha de publicación: ${el.fecha}</small>
                </div>
          </div>
          `);  
      })
      .catch((error) => {
        console.warn('No se ha podido cargar las noticias');
        alert(error.informacion);
      });
  
  }