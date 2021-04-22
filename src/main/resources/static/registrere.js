$(function(){  // kjøres når dokumentet er ferdig lastet
    hentAlleBiler();
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
    let ut = "<select id='valgtMerke' onchange='finnTyper(), validerMerke(this.value)'>";
    let forrigeMerke = "";
    ut+="<option>Velg merke</option>";
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
    let ut = "<select id='valgtType'>";
    for(const bil of biler ){
        if(bil.merke === valgtMerke){
            ut+="<option>"+bil.type+"</option>";
        }
    }
    ut+="</select>";
    $("#type").html(ut);
}

function validerOgRegMotorvogn(){
    const personnrOk = validerPersonnr($("#personnr").val());
    const navnOk = validerNavn($("#navn").val());
    const adresseOk = validerAdresse($("#adresse").val());
    const kjennetegnOk = validerKjennetegn($("#kjennetegn").val());
    const merkeOk = validerMerke($("#merke").val());

    if(personnrOk && navnOk && adresseOk && kjennetegnOk && merkeOk){
        regMotorvogn();
    }
}

function regMotorvogn() {
    const motorvogn = {
        personnr : parseInt($("#personnr").val()),
        navn : $("#navn").val(),
        adresse : $("#adresse").val(),
        kjennetegn : $("#kjennetegn").val(),
        merke : $("#valgtMerke").val(),
        type : $("#valgtType").val()
    };
    $.post("/lagre", motorvogn, function() {
        window.location.href = '/';
    })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}


