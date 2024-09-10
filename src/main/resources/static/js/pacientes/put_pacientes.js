function findBy(id) {
    const url = '/pacientes';
    const settings = {
        method: 'GET'
    };

    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            // Cargar los datos en el formulario
            document.querySelector('#paciente_id').value = data.id;
            document.querySelector('#nombre').value = data.nombre;
            document.querySelector('#apellido').value = data.apellido;
            document.querySelector('#cedula').value = data.cedula;
            document.querySelector('#fechaIngreso').value = data.fechaIngreso;
            document.querySelector('#calle').value = data.domicilio.calle;
            document.querySelector('#numero').value = data.domicilio.numero;
            document.querySelector('#localidad').value = data.domicilio.localidad;
            document.querySelector('#provincia').value = data.domicilio.provincia;
            document.querySelector('#email').value = data.email;

            // Mostrar el formulario de actualización
            document.querySelector('#div_paciente_updating').style.display = "block";
        })
        .catch(error => console.log('Error al buscar paciente: ', error));
}

document.querySelector('#put_paciente_form').addEventListener('submit', function(event) {
    event.preventDefault();

    const pacienteId = document.querySelector('#paciente_id').value;
    const paciente = {
        id: pacienteId,
        nombre: document.querySelector('#nombre').value,
        apellido: document.querySelector('#apellido').value,
        cedula: document.querySelector('#cedula').value,
        fechaIngreso: document.querySelector('#fechaIngreso').value,
        domicilio: {
            calle: document.querySelector('#calle').value,
            numero: document.querySelector('#numero').value,
            localidad: document.querySelector('#localidad').value,
            provincia: document.querySelector('#provincia').value
        }
        email: document.querySelector('#email').value,
    };

    const url = '/pacientes';
    const settings = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(paciente)
    };

    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            // Mostrar éxito y recargar la lista de pacientes
            let successAlert =
                '<div class="alert alert-success alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong></strong> Paciente actualizado correctamente </div>';
            document.querySelector('#response').innerHTML = successAlert;
            document.querySelector('#response').style.display = "block";

            setTimeout(() => { location.reload(); }, 1000);
        })
        .catch(error => console.log('Error al actualizar el paciente: ', error));
});
