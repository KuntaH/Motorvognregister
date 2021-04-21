$(function(){  // kjøres når dokumentet er ferdig lastet
    hentAlleBiler();
    henteEnMotorvogn();
});

function hentAlleBiler() {
    $.get( "/hentBiler", function( biler ) {
        formaterBiler(biler);
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function formaterBiler(biler){
    let ut = "<select id='valgtMerke' onchange='finnTyper()'>";
    let forrigeMerke = "";
    ut+="<option >Velg merke</option>";
    for (const bil of biler){
        if(bil.merke !== forrigeMerke){
            ut+="<option>"+bil.merke+"</option>";
        }
        forrigeMerke = bil.merke;
    }
    ut+="</select>";
    $("#merke").html(ut);
}

function finnTyper(){
    console.log("Finner typer.");
    const valgtMerke = $("#valgtMerke").val();
    $.get( "/hentBiler", function( biler ) {
        formaterTyper(biler,valgtMerke);
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}
function formaterTyper(biler,valgtMerke){
    console.log("Oppretter typer.");
    let ut = "<select id='valgtType'>";
    for(const bil of biler ){
        if(bil.merke === valgtMerke){
            //ut+="<option value='" + bil.type + "'>" + bil.type +"</option>";
            ut+="<option>"+ bil.type +"</option>";
        }
    }
    ut+="</select>";
    $("#type").html(ut);
}

function henteEnMotorvogn(){
    const id = window.location.search.substring(1); // kommer fra kallet i index.js
    const url = "/henteEnMotorvogn?id="+id;
    $.get( url, function(enMotorVogn) {
        // overfør til input-feltene i skjemaet
        $("#id").val(enMotorVogn.id); // må ha med denne for å vite hvilken id
        $("#personnr").val(enMotorVogn.personnr);
        $("#navn").val(enMotorVogn.navn);
        $("#adresse").val(enMotorVogn.adresse);
        $("#kjennetegn").val(enMotorVogn.kjennetegn);
        console.log("merke i objektet: " + enMotorVogn.merke);
        $("#valgtMerke").val(enMotorVogn.merke);
        console.log("merke i felt: " + $("#valgtMerke").val());
        finnTyper();
        console.log("type i objektet: " + enMotorVogn.type);
        $("#valgtType").val(enMotorVogn.type);
        console.log("type i felt: " + $("#valgtType").val());
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function validerOgEndreMotorvogn(){
    const personnrOk = validerPersonnr($("#personnr").val());
    const navnOk = validerNavn($("#navn").val());
    const adresseOk = validerAdresse($("#adresse").val());
    const kjennetegnOk = validerKjennetegn($("#kjennetegn").val());
    const merkeOk = validerMerke($("#valgtMerke").val());

    if(personnrOk && navnOk && adresseOk && kjennetegnOk && merkeOk){
        endreMotorvogn();
    }
}

function endreMotorvogn() {
    console.log("lagre motorvogn.")
    const motorvogn = {
        id: $("#id").val(),
        personnr : parseInt($("#personnr").val()),
        navn : $("#navn").val(),
        adresse : $("#adresse").val(),
        kjennetegn : $("#kjennetegn").val(),
        merke : $("#valgtMerke").val(),
        type : $("#valgtType").val()
    };
    $.post("/endre", motorvogn, function(){
        window.location.href="index.html";
    })
    .fail(function(jqXHR) {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}


