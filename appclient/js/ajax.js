/**
 * llamada ajax en vanilla javascript
 * @param {*} metodo 
 * @param {*} url 
 * @param {*} datos en formato JSON para el request pero luego hacemos un "stringify"
 * @return Promise
 */
function ajax( metodo, url, datos ){

    return new Promise( (resolve, reject ) => {

        console.debug(`promesa ajax metodo ${metodo} - ${url}` );
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            
            if (this.readyState == 4 ) {

                if ( this.status == 200 || this.status == 201 ){

                    // funciona promesa, then
                    if(this.responseText){

                        const jsonData = JSON.parse(this.responseText);    
                        console.debug( jsonData );
                        resolve(jsonData);
                    } else {

                        resolve();
                    }
                }else{
                    // falla promesa, catch
                    //reject( Error( JSON.parse(this.responseText) ));
                    if( this.responseText ){
                        reject( JSON.parse(this.responseText) );
                    }else{
                        reject( this.status );
                    }
                }               
            }// readyState == 4

        };// onreadystatechange

        xhttp.open( metodo , url , true);
        xhttp.setRequestHeader('Content-Type', 'application/json'); //Se define el tipo de contenido de la request es de tipo JSON
        xhttp.send( JSON.stringify(datos) );    //Se debe parsear a string para enviar
    });
}