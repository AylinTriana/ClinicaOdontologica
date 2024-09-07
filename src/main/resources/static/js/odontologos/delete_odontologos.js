window.addEventListener('load', function () {

    // Al cargar la página, buscamos y obtenemos el formulario donde estarán
    // los datos que el usuario cargará para eliminar un odontólogo
    const formulario = document.querySelector('#delete_odontologo');  // Asegúrate de que el ID del formulario sea correcto

    // Ante un submit del formulario se ejecutará la siguiente función
    formulario.addEventListener('onclick', function (event) {
        event.preventDefault();  // Prevenimos el comportamiento por defecto del formulario

        // Obtenemos el ID del odontólogo que se desea eliminar
        const odontologoId = document.querySelector('#odontologo_id').value;

        // Llamamos a la función deleteByID con el ID del odontólogo
        deleteByID(odontologoId);
    });

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/eliminar/") {
            document.querySelector(".nav .nav-item a:first").classList.add("active");
        } else if (pathname == "./delete_odontologos.html") {
            document.querySelector(".nav .nav-item a:last").classList.add("active");
        }
    })();
});

function deleteBy(id) {
    const url = `odontologos/eliminar/${id}`;
    const settings = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    };

    fetch(url, settings)
        .then(response => {
            if (response.ok) {
               let successAlert =
               '<div class="alert alert-success alert-dismissible">' +
               '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
               '<strong></strong> Odontólogo eliminado correctamente </div>';
               document.querySelector('#response').innerHTML = successAlert;
               document.querySelector('#response').style.display = "block";
                const row = document.getElementById(`row_${id}`);
                if (row) {
                    row.remove();  // Elimina la fila del odontólogo
                }
                setTimeout(() => {location.reload();}, 500);
            } else {
                throw new Error('Error al eliminar el odontólogo');
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
}

function resetUploadForm() {
    document.querySelector('#odontologo_id').value = "";
    // Puedes resetear otros campos del formulario aquí si es necesario
}