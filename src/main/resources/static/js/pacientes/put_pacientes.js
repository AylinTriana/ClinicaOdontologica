window.addEventListener('load', function () {

    (function() {
        const id = localStorage.getItem('id');
        const url = `/pacientes/buscar/id/${id}`;
        const settings = {
            method: 'GET'
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(paciente => {

                if (paciente && paciente.id) {
                    document.querySelector('#id').value = paciente.id;
                    document.querySelector('#nombre').value = paciente.nombre;
                    document.querySelector('#apellido').value = paciente.apellido;
                    document.querySelector('#cedula').value = paciente.cedula;
                    document.querySelector('#fechaIngreso').value = paciente.fechaIngreso;
                    document.querySelector('#calle').value = paciente.domicilio.calle;
                    document.querySelector('#numero').value = paciente.domicilio.numero;
                    document.querySelector('#localidad').value = paciente.domicilio.localidad;
                    document.querySelector('#provincia').value = paciente.domicilio.provincia;
                    document.querySelector('#email').value = paciente.email;
                } else {
                    console.error('No se recibió un paciente válido.');
                }
            })
            .catch(error => console.error('Error al obtener los datos:', error));
    })();

    // Asegúrate de que el formulario se seleccione correctamente
    const formulario = document.querySelector('#put_paciente_form');

    if (formulario) {
        formulario.addEventListener('submit', function (event) {
            event.preventDefault(); // Prevenir el envío del formulario por defecto

            const formData = {
                id: document.querySelector('#id').value,
                nombre: document.querySelector('#nombre').value,
                apellido: document.querySelector('#apellido').value,
                cedula: document.querySelector('#cedula').value,
                fechaIngreso: document.querySelector('#fechaIngreso').value,
                domicilio: {
                   calle: document.querySelector('#calle').value,
                   numero: document.querySelector('#numero').value,
                   localidad: document.querySelector('#localidad').value,
                   provincia: document.querySelector('#provincia').value,
                },
                email: document.querySelector('#email').value,
            };

         const url = '/pacientes';
         const settings = {
             method: 'PUT',
             headers: {
                 'Content-Type': 'application/json',
             },
             body: JSON.stringify(formData)
         };

         fetch(url, settings)
             .then(response => {
                 if (!response.ok) {
                     throw new Error('Error al actualizar el paciente');
                 }
                 return response.text(); // Si el backend devuelve texto plano
             })
             .then(data => {
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> ' + data + ' </div>'; // Muestra el mensaje devuelto

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
             })
             .catch(error => {
                 console.error('Hubo un problema con la actualización:', error);
                 let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong>Error: </strong>' + error.message + ' </div>';

                 document.querySelector('#response').innerHTML = errorAlert;
                 document.querySelector('#response').style.display = "block";
             });
        });
    } else {
        console.error('El formulario con ID "put_paciente_form" no se encontró.');
    }

    (function(){
        let pathname = window.location.pathname;
        if (pathname === "/" || pathname === "/put_pacientes.html") {
            document.querySelector(".nav .nav-item a:last-child").classList.add("active");
        }
    })();
});









