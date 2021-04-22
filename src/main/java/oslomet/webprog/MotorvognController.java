package oslomet.webprog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.util.List;

@RestController
public class MotorvognController {

    @Autowired
    private MotorvognRepository rep;

    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    private boolean validerMotorvogn(Motorvogn motorvogn){
        String regexPersonnr = "[0-9]{11}";
        String regexNavn = "[a-zA-ZæøåÆØÅ. \\-]{2,30}";
        String regexAdresse = "[0-9a-zA-ZæøåÆØÅ., \\-]{2,50}";
        String regexKjennetegn = "[0-9a-zA-ZæøåÆØÅ. \\-]{2,50}";

        String personnr = "" + motorvogn.getPersonnr();
        System.out.println(personnr + " personnr");

        boolean personnrOk = personnr.matches(regexPersonnr);
        boolean navnOk = motorvogn.getNavn().matches(regexNavn);
        boolean adresseOk = motorvogn.getAdresse().matches(regexAdresse);
        boolean kjennetegnOk = motorvogn.getKjennetegn().matches(regexKjennetegn);

        if(personnrOk && navnOk && adresseOk && kjennetegnOk){
            return true;
        }

        logger.error("valideringsfeil");
        return false;
    }

    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException {
        List<Bil> alleBiler = rep.hentAlleBiler();
        if(alleBiler==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
        return alleBiler;
    }

    @PostMapping("/lagre")
    public void lagreMotorvogn(Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if(!validerMotorvogn(motorvogn)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i inputvalidering");
        } else{
            if(!rep.lagreMotorvogn(motorvogn)){
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
            }
        }
    }

    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlleMotorvogner(HttpServletResponse response) throws IOException {
        List<Motorvogn> alleMotorvogner = rep.hentAlleMotorvogner();
        if(alleMotorvogner==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
        return alleMotorvogner;
    }

    @GetMapping("/henteEnMotorvogn")
    public Motorvogn henteEnMotorvogn(int id, HttpServletResponse response) throws IOException{
        Motorvogn enMotorvogn = rep.henteEnMotorvogn(id);
        if(enMotorvogn == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
        return enMotorvogn;
    }

    @PostMapping("/endre")
    public void endre(Motorvogn motorvogn, HttpServletResponse response) throws IOException{
        if(!validerMotorvogn(motorvogn)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i inputvalidering");
        } else{
            if(!rep.endreMotorvogn(motorvogn)){
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
            }
        }
    }

    @GetMapping("/slettEnMotorvogn")
    public void slettEnMotorvogn(int id, HttpServletResponse response) throws IOException{
        if(!rep.slettEnMotorvogn(id)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }

    @GetMapping("/slettAlle")
    public void slettAlleMotorvogner(HttpServletResponse response) throws IOException{
        if(!rep.slettAlleMotorvogner()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }
}

