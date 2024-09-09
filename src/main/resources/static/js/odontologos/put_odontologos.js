window.addEventListener('load', function () {

    (function() {
        const id = localStorage.getItem('id');
        const url = `/odontologos/buscar/id/${id}`;
        const settings = {
            method: 'GET'
        };

        fetch(url, settings)
            .then(response => response.json())
            .then(odontologo => {

                if (odontologo && odontologo.id) {
                    document.querySelector('#id').value = odontologo.id;
                    document.querySelector('#nombre').value = odontologo.nombre;
                    document.querySelector('#apellido').value = odontologo.apellido;
                    document.querySelector('#matricula').value = odontologo.matricula;
                } else {
                    console.error('No se recibió un odontólogo válido.');
                }
            })
            .catch(error => console.error('Error al obtener los datos:', error));
    })();

    // Asegúrate de que el formulario se seleccione correctamente
    const formulario = document.querySelector('#put_odontologo_form');

    if (formulario) {
        formulario.addEventListener('submit', function (event) {
            event.preventDefault(); // Prevenir el envío del formulario por defecto

            const formData = {
                id: document.querySelector('#id').value,
                nombre: document.querySelector('#nombre').value,
                apellido: document.querySelector('#apellido').value,
                matricula: document.querySelector('#matricula').value,
            };

            const url = '/odontologos';
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
                        '<strong></strong> Odontólogo actualizado </div>';

                    document.querySelector('.response').innerHTML = successAlert;
                    document.querySelector('.response').style.display = "block";
                    resetUploadForm();
                })
                .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong> Error, intente nuevamente</strong> </div>';

                    document.querySelector('.response').innerHTML = errorAlert;
                    document.querySelector('.response').style.display = "block";
                    resetUploadForm();
                });
        });
    } else {
        console.error('El formulario con ID "put_odontologo_form" no se encontró.');
    }

    (function(){
        let pathname = window.location.pathname;
        if (pathname === "/" || pathname === "/put_odontologos.html") {
            document.querySelector(".nav .nav-item a:last-child").classList.add("active");
        }
    })();
});









