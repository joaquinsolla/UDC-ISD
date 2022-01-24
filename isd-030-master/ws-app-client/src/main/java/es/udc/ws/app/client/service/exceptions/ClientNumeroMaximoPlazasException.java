package es.udc.ws.app.client.service.exceptions;

public class ClientNumeroMaximoPlazasException extends Exception {

    private Long idExcursion;

    public ClientNumeroMaximoPlazasException(Long idExcursion){

        super ("Cannot to update excursion with id=\"" + idExcursion +
                "\" because numeroReservas is greather than new numeroMaximoPlazas");
        this.idExcursion = idExcursion;
    }

    //Getters y Setters

    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }
}
