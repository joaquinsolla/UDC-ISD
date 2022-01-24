package es.udc.ws.app.client.service.exceptions;

public class ClientCancelarReservaFueraDePlazoException extends Exception{

    private Long idExcursion;
    private Long idReserva;

    public ClientCancelarReservaFueraDePlazoException(Long idExcursion, Long idReserva){
        super("The book with id " + idReserva + " of the excursion with id " + idExcursion +
                "cannot be cancelled as the excursion date is not higher than 48 hours");
        this.idExcursion = idExcursion;
        this.idReserva = idReserva;

    }

    //Getters y Setters

    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
}
