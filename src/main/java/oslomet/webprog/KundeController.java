package oslomet.webprog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class KundeController {

    @Autowired
    KundeRepository rep;

    @Autowired private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(KundeController.class);

    private boolean validerKunde(Kunde kunde){
        String regexPersonnr = "[0-9]{11}";
        String regexNavn = "[a-zA-ZæøåÆØÅ. \\-]{2,30}";
        String regexAdresse = "[0-9a-zA-ZæøåÆØÅ., \\-]{2,50}";
        String regexPassord = "(?=.*[a-zA-ZæøåÆØÅ])(?=.*\\d)[a-zA-ZæøåÆØÅ\\d]{8,}";
        String personnr = "" + kunde.getPersonnr();
        boolean personnrOk = personnr.matches(regexPersonnr);
        boolean navnOk = kunde.getNavn().matches(regexNavn);
        boolean adresseOk = kunde.getAdresse().matches(regexAdresse);
        boolean passordOk = kunde.getPassord().matches(regexPassord);

        if(personnrOk && navnOk && adresseOk && passordOk){
            return true;
        }
        logger.error("Valideringsfeil");
        return false;
    }

    @PostMapping("/lagreKunde")
    public void lagreKunde(Kunde kunde, HttpServletResponse response) throws IOException{
        if(!validerKunde(kunde)){
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
        } else{
            if(!rep.lagreKunde(kunde)){
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
        }
    }

    @GetMapping("/hentKunder")
    public List<Kunde> hentAlleKunder(HttpServletResponse response) throws IOException{
        List<Kunde> alleKunder = new ArrayList<>();
        if(session.getAttribute("Innlogget")!=null){
            alleKunder = rep.hentAlleKunder();
            if(alleKunder == null){
                response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
            }
            return alleKunder;
        }
        response.sendError(HttpStatus.NOT_FOUND.value());
        return null;
    }

    @GetMapping("/slettEnKunde")
    public void slettEnKunde(int id, HttpServletResponse response) throws IOException{
        if(!rep.slettEnKunde(id)){
            response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @GetMapping("/slettAlleKunder")
    public void slettAlleKunder(HttpServletResponse response) throws IOException{
        if(!rep.slettAlleKunder()){
            response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @GetMapping("/login")
    public boolean login(Kunde kunde){
        if(rep.sjekkNavnOgPassord(kunde)){
            session.setAttribute("Innlogget", kunde);
            return true;
        }
        return false;
    }

    @GetMapping("logout")
    public void logout(){session.removeAttribute("innlogget");}
}
