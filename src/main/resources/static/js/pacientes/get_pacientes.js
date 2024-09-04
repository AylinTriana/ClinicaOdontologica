window.addEventListener('load', function () {
    (function(){

      const url = '/pacientes';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        console.log(data)
         for(paciente of data){

            var table = document.getElementById("pacienteTable");
            var pacienteRow =table.insertRow();
            let tr_id = 'tr_' + paciente.id;
            pacienteRow.id = tr_id;

            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + paciente.id + '\"' +
                                      ' type="button" onclick="deleteBy('+paciente.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';

            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + paciente.id + '\"' +
                                      ' type="button" onclick="findBy('+paciente.id+')" class="btn btn-info btn_id">' +
                                      paciente.id +
                                      '</button>';

            pacienteRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_nombre\">' + paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_apellido\">' + paciente.apellido.toUpperCase() + '</td>' +
                    '<td class=\"td_cedula\">' + paciente.cedula.toUpperCase() + '</td>' +
                    '<td class=\"td_fechaIngreso\">' + paciente.fechaIngreso + '</td>' +
                    '<td class=\"td_calle\">' + paciente.domicilio.calle.toUpperCase() + '</td>' +
                    '<td class=\"td_numero\">' + paciente.domicilio.numero + '</td>' +
                    '<td class=\"td_localidad\">' + paciente.domicilio.localidad.toUpperCase() + '</td>' +
                    '<td class=\"td_provincia\">' + paciente.domicilio.provincia.toUpperCase() + '</td>' +
                    '<td class=\"td_email\">' + paciente.email.toUpperCase() + '</td>' +
                    '<td>' + deleteButton + '</td>';




        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "./get_pacientes.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })