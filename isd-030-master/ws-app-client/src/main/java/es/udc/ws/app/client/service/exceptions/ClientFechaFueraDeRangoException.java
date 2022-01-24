package es.udc.ws.app.client.service.exceptions;

public class ClientFechaFueraDeRangoException extends Exception{

    private Long idExcursion;

    public ClientFechaFueraDeRangoException(Long idExcursion) {
        super ("Tried to update excursion with id=\"" + idExcursion +
                " when the date of the celebration will be in minus than 72 hours");
        this.idExcursion = idExcursion;

    }

    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }

}
