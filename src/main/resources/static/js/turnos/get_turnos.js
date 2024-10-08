window.addEventListener('load', function () {
    (function(){

      const url = '/turno';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        console.log(data)
         for(turno of data){

            var table = document.getElementById("turnoTable");
            var turnoRow =table.insertRow();
            let tr_id = 'tr_' + turno.id;
            turnoRow.id = tr_id;

            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                                      ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                                      ' type="button" onclick="getBy('+turno.id+')" class="btn btn-info btn_id">' +
                                      turno.id +
                                      '</button>';

            turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_nombrePaciente\">' + turno.paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellidoPaciente\">' + turno.paciente.apellido.toUpperCase()  + '</td>' +
                    '<td class=\"td_nombreOdontologo\">' + turno.odontologo.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellidoOdontologo\">' + turno.odontologo.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_fechaTurno\">' + turno.fecha + '</td>' +
                    '<td>' + deleteButton + '</td>';




        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "./get_turnos.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })

    function getBy(id) {
                               localStorage.setItem('id', id);
                               window.location.href = 'put_turnos.html';

      };