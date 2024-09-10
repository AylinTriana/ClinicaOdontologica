window.addEventListener('load', function () {


    const formulario = document.querySelector('#delete_paciente');

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/eliminar/") {
            document.querySelector(".nav .nav-item a:first").classList.add("active");
        } else if (pathname == "./delete_pacientes.html") {
            document.querySelector(".nav .nav-item a:last").classList.add("active");
        }
    })();
});

function deleteBy(id) {
    const url = `pacientes/eliminar/${id}`;
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
               '<strong></strong> Paciente eliminado correctamente </div>';
               document.querySelector('#response').innerHTML = successAlert;
               document.querySelector('#response').style.display = "block";
                const row = document.getElementById(`row_${id}`);
                if (row) {
                    row.remove();
                }
                setTimeout(() => {location.reload();}, 500);
            } else {
                throw new Error('Error al eliminar el paciente');
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
}
