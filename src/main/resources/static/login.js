function validerOgLogin(){
    const navnOk = validerNavn($("#navn").val());

    if(navnOk){
        login();
    }
}

function login(){
    const kunde = {
        navn: $("#navn").val(),
        passord: $("#passord").val()
    }

    const url = "/login";

    $.post(url, kunde, function (innlogget){
        if(innlogget){
            window.location.href = "index.html";
        } else {
            $("#feil").html("Feil brukernavn eller passord");
        }
    }).fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}