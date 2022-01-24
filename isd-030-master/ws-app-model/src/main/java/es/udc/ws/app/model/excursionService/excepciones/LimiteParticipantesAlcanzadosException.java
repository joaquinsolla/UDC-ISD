package es.udc.ws.app.model.excursionService.excepciones;

public class LimiteParticipantesAlcanzadosException extends Exception {

    private Long idExcursion;

    public LimiteParticipantesAlcanzadosException(Long idExcursion) {
        super("Excursion with id=\"" + idExcursion + "\n has reached the maximum number of places");
        this.idExcursion = idExcursion;
    }


    //Getters y Setters

    public Long getidExcursion() {
        return idExcursion;
    }

    public void setidExcursiond(Long idExcursion) {
        this.idExcursion = idExcursion;
    }

}
