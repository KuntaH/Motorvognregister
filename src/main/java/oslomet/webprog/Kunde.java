package oslomet.webprog;

public class Kunde {

    private int id;
    private long personnr;
    private String navn;
    private String adresse;
    private String passord;

    public Kunde(){

    }

    public Kunde(int id, long personnr, String navn, String adresse, String passord) {
        this.id = id;
        this.personnr = personnr;
        this.navn = navn;
        this.adresse = adresse;
        this.passord = passord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPersonnr() {
        return personnr;
    }

    public void setPersonnr(long personnr) {
        this.personnr = personnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}
