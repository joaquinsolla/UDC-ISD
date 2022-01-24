package es.udc.ws.app.client.service.exceptions;

public class ClientReservaFueraPlazoException extends Exception {
    private Long idExcursion;

    public ClientReservaFueraPlazoException(Long idExcursion) {
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
