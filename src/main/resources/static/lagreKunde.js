function validerOgLagreKunde(){
    const personnrOk = validerPersonnr($("#personnr").val());
    const navnOk = validerNavn($("#navn").val());
    const adresseOk = validerAdresse($("#adresse").val());
    const passordOk = validerPassord($("#passord").val());

    if(personnrOk && navnOk && adresseOk && passordOk){
        lagreKunde();
    }
}

function lagreKunde(){
    const kunde = {
        personnr: parseInt($("#personnr").val()),
        navn: $("#navn").val(),
        adresse: $("#adresse").val(),
        passord: $("#passord").val()
    }
    const url = "/lagreKunde";
    $.post(url, kunde, function (){
        window.location.href = "index.html";
    }).fail(function (status){
       if(status.status==422){
           $("#formFeil").html("Feil i db - prøv igjen senere.");
       }
       else{
           $("#formFeil").html("Valideringsfeil -prøv igjen senere.");
       }
    });
}