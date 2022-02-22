$(document).ready(function () {
    $("#cpf").mask('000.000.000-00', {reverse: true});

    $("#celular").mask('(00) 0 0000-0009');

    $("#rg").mask("999.999.999-W", {
        translation: {
            'W': {
                pattern: /[xX0-9]/
            }
        },
        reverse: true
        ,
        onKeyPress: function (value, event) {
            event.currentTarget.value = value.toUpperCase();
        }
    });
});

$("#formulario").submit(function() {
    $("#cpf").unmask();
    $("#rg").unmask();
    $("#celular").unmask();
});

$().ready(function() {
    setTimeout(function () {
        $('.alerta').hide();
    }, 5000);
});


