package es.udc.ws.app.model.excursionService.excepciones;

public class ReservaFueraPlazoException extends Exception {

    private Long idExcursion;

    public ReservaFueraPlazoException(Long idExcursion) {
        super("Late reservations on the excursion with id=\"" + idExcursion + "\n");
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
