window.addEventListener('load', function () {

    (function() {
        const id = localStorage.getItem('id');
        const url = `/turno/buscar/${id}`;
        const settings = {
            method: 'GET'
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(turno => {

                if (turno && turno.id) {
                    document.querySelector('#turno_id').value = turno.id;
                    document.querySelector('#idPaciente').value = turno.paciente.id;
                    document.querySelector('#nombrePaciente').value = turno.paciente.nombre;
                    document.querySelector('#apellidoPaciente').value = turno.paciente.apellido;
                    document.querySelector('#idOdontologo').value = turno.odontologo.id;
                    document.querySelector('#nombreOdontologo').value = turno.odontologo.nombre;
                    document.querySelector('#apellidoOdontologo').value = turno.odontologo.apellido;
                    document.querySelector('#fechaTurno').value = turno.fecha;
                } else {
                    console.error('No se recibió un turno válido.');
                }
            })
            .catch(error => console.error('Error al obtener los datos:', error));
    })();

    // Asegúrate de que el formulario se seleccione correctamente
    const formulario = document.querySelector('#put_turno_form');

    if (formulario) {
        formulario.addEventListener('submit', function (event) {
            event.preventDefault(); // Prevenir el envío del formulario por defecto

            const formData = {
                id: document.querySelector('#turno_id').value,
                    paciente:{
                 id: (document.querySelector('#idPaciente').value)
                },
                 odontologo:{
                     id: (document.querySelector('#idOdontologo').value)
                },
                fecha: document.querySelector('#fechaTurno').value,
            };

            const url = '/turno';
            const settings = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            };

            fetch(url, settings)
                .then(response => response.json())
                .then(data => {
                    let successAlert = '<div class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong></strong> Turno actualizado </div>';

                    document.querySelector('#response').innerHTML = successAlert;
                    document.querySelector('#response').style.display = "block";

                })
             
             
             
             

             
             
             
        });
    } else {
        console.error('El formulario con ID "put_turno_form" no se encontró.');
    }

    (function(){
        let pathname = window.location.pathname;
        if (pathname === "/" || pathname === "/put_turnos.html") {
            document.querySelector(".nav .nav-item a:last-child").classList.add("active");
        }
    })();
});









