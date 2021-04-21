function validerPersonnr(personnr){
    const regexp = /^[0-9]{11}$/;
    const ok = regexp.test(personnr)
    if(!ok){
        $("#feilPersonnr").html("Personnr må bestå av 11 tall, ingen bokstaver eller tegn.");
        return false;
    } else {
        $("#feilPersonnr").html(" ");
        return true;
    }
}

function validerNavn(navn){
    const regexp = /^[a-zA-ZæøåÆØÅ. \-]{2,30}$/;
    const ok = regexp.test(navn)
    if(!ok){
        $("#feilNavn").html("Navn må bestå av 2 til 30 bokstaver.");
        return false;
    } else {
        $("#feilNavn").html(" ");
        return true;
    }
}

function validerAdresse(adresse){
    const regexp = /^[0-9a-zA-ZæøåÆØÅ., \-]{2,50}$/;
    const ok = regexp.test(adresse);
    if(!ok){
        $("#feilAdresse").html("Adresse må bestå av 2 til 50 bokstaver.");
        return false;
    } else {
        $("#feilAdresse").html(" ");
        return true;
    }
}

function validerKjennetegn(kjennetegn){
    const regexp = /^[0-9a-zA-ZæøåÆØÅ. \-]{2,50}$/;
    const ok = regexp.test(kjennetegn);
    if(!ok){
        $("#feilKjennetegn").html("kjennetegn må bestå av 2 til 50 bokstaver.");
        return false;
    } else {
        $("#feilKjennetegn").html(" ");
        return true;
    }
}

function validerMerke(merke){
    if(merke === "Velg merke"){
        $("#feilMerke").html("Du må velge merke.");
        return false;
    } else {
        $("#feilMerke").html(" ");
        return true;
    }
}