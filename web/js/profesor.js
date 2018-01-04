/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*jslint browser:true, devel:true, white:true, vars:true */
/*global $:false, intel:false */
// variables para el jslint

/**
 * Creamos el objeto alumno y todos sus métodos.
 */
$.profesor = {};
// Configuración del HOST y URL del servicio
$.profesor.HOST = 'http://localhost:8080';
$.profesor.URL = '/GestionAcademica/rest/profesor';

$.profesor.panel_alta = '#panel_al_pro';
$.profesor.panel_list = '#panel_li_pro';
$.profesor.panel_modi = '#panel_mo_pro';
$.profesor.panel_borr = '#panel_bo_pro';
$.profesor.panel_erro = '#panel_error';

/**
 Esta función hace la llamada REST al servidor y crea la tabla con todos los alumnos.
 */
$.profesor.ProfesorReadREST = function () {
    // con esta función jQuery hacemos la petición GET que hace el findAll()
    $.controller.doGet(
            this.HOST + this.URL,
            function (json) {
                $($.profesor.panel_list).empty();
                $($.profesor.panel_list).append('<h4>Listado de Profesores</h4>');
                var table = $('<table />').addClass('responsive-table highlight');

                table.append($('<thead />').append($('<tr />').append('<th>id</th>', '<th>nombre</th>', '<th>apellidos</th>')));
                var tbody = $('<tbody />');
                for (var clave in json) {
                    tbody.append($('<tr />').append('<td>' + json[clave].id + '</td>',
                            '<td>' + json[clave].nombre + '</td>', '<td>' + json[clave].apellido + '</td>'));
                }
                table.append(tbody);

                $($.profesor.panel_list).append($('<div />').append(table));
                //$('tr:odd').css('background', '#CCCCCC');
            });
};

/**
 Esta función carga los datos del formulario y los envía vía POST al servicio REST
 */
$.profesor.ProfesorCreateREST = function () {
    // Leemos los datos del formulario pidiendo a jQuery que nos de el valor de cada input.
    var datos = {
        'nombre': $("#c_pro_nombre").val(),
        'apellido': $("#c_pro_apellidos").val()
    };

    // comprobamos que en el formulario haya datos...
    if (datos.nombre.length > 2 && datos.apellido.length > 2) {
        // doPost(target, datos, fn_exito)
        $.controller.doPost(
            $.profesor.HOST+$.profesor.URL,
            datos,
            function () {
                // probamos que se ha actualizado cargando de nuevo la lista -no es necesario-
                $.profesor.ProfesorReadREST();
            });

        // cargamos el panel con id r_alumno.
        $.controller.activate($.profesor.panel_list);
    }

};

/**
 Crea un desplegable, un select, con todos los alumnos del servicio para seleccionar el alumno a eliminar
 */
$.profesor.ProfesorDeleteREST = function (id) {
    // si pasamos el ID directamente llamamos al servicio DELETE
    // si no, pintamos el formulario de selección para borrar.
    if (id !== undefined) {
        id = $('#d_pro_sel').val();
        // doDelete (target, id, fn_exito)
        $.controller.doDelete(
            $.profesor.HOST + $.profesor.URL,
            id,
            function () {
                // probamos que se ha actualizado cargando de nuevo la lista -no es necesario-
                $.profesor.ProfesorReadREST();
                // cargamos el panel listado
                $.controller.activate($.profesor.panel_list);
            });
    } else {
        // doGet (target, fn_exito)
        $.controller.doGet(
            this.HOST + this.URL,
            function (json) {
                // pintamos el formulario para ver a quien modificar
                $('select').material_select('destroy');
                $($.profesor.panel_borr).empty();
                var formulario = $('<div />');
                formulario.addClass('input-field');
                var div_select = $('<div />');
                div_select.addClass('form-group');
                var select = $('<select id="d_pro_sel" />');
                select.addClass('form-group');
                for (var clave in json) {
                    select.append('<option value="' + json[clave].id + '">' + json[clave].nombre + ' ' + json[clave].apellido + '</option>');
                }
                formulario.append(select);
                formulario.append('<div class="form-group"></div>').append('<div class="btn btn-danger" onclick="$.profesor.ProfesorDeleteREST(1)"> eliminar! </div>');
                $($.profesor.panel_borr).append(formulario);
                $('select').material_select();
            }); 
    }

};

/**
 Función para la gestión de actualizaciones. Hay tres partes: 
 1) Listado 
 2) Formulario para modificación
 3) Envío de datos al servicio REST (PUT)
 */

$.profesor.ProfesorUpdateREST = function (id, envio) {
    // si no le pasamos parámetro, hay que sacar la lista para 
    // pulsar sobre quien queremos actualizar
    if (id === undefined) {
        $.controller.doGet(
            this.HOST + this.URL,
            function (json) {
                $($.profesor.panel_list).empty();
                $($.profesor.panel_list).append('<h4>Pulse sobre un profesor</h4>');
                var table = $('<table />').addClass('responsive-table highlight');

                table.append($('<thead />').append($('<tr />').append('<th>id</th>', '<th>nombre</th>', '<th>apellidos</th>')));
                var tbody = $('<tbody />');
                for (var clave in json) {
                    // le damos a cada fila un ID para luego poder recuperar los datos para el formulario en el siguiente paso
                    tbody.append($('<tr id="fila_' + json[clave].id + '" onclick="$.profesor.ProfesorUpdateREST(' + json[clave].id + ')"/>').append('<td>' + json[clave].id + '</td>',
                            '<td>' + json[clave].nombre + '</td>', '<td>' + json[clave].apellido + '</td>'));
                }
                table.append(tbody);
                $($.profesor.panel_list).append($('<div />').append(table));
                // $('tr:odd').css('background', '#CCCCCC');
                $.controller.activate($.profesor.panel_list);
            });
    } else if (envio === undefined) {
        var seleccion = "#fila_" + id + " td";
        var pro_id = ($(seleccion))[0];
        var pro_nombre = ($(seleccion))[1];
        var pro_apellidos = ($(seleccion))[2];

        $("#u_pro_id").val(pro_id.childNodes[0].data);
        $("#u_pro_nombre").val(pro_nombre.childNodes[0].data);
        $("#u_pro_apellidos").val(pro_apellidos.childNodes[0].data);
        // cargamos el panel 
        $.controller.activate($.profesor.panel_modi);
    } else {
        //HACEMOS LA LLAMADA REST
        var datos = {
            'id': $("#u_pro_id").val(),
            'nombre': $("#u_pro_nombre").val(),
            'apellido': $("#u_pro_apellidos").val()
        };

        // comprobamos que en el formulario haya datos...
        if (datos.nombre.length > 2 && datos.apellido.length > 2) {
            // doPut(target, id, datos, fn_exito)
            $.controller.doPut(
                $.profesor.HOST + $.profesor.URL,
                $("#u_pro_id").val(),
                datos,
                function() { 
                    // esto es lo que se ejecuta cuando tengamos éxito tras el PUT
                    $.profesor.ProfesorReadREST();
                }
            );

            // cargamos el panel con id r_alumno.
            $.controller.activate($.profesor.panel_list);
        }
    }
};


/**
 Función para la gestión de errores y mensajes al usuario
 */
$.profesor.error = function (title, msg) {
    $($.profesor.panel_erro).empty();
    $($.profesor.panel_erro).append('<h4>' + title + '</h4>');
    $($.profesor.panel_erro).append('<p>' + msg + '</p>');

    // cargamos el panel con id r_alumno.
    $.controller.activate($.profesor.panel_erro);
};