package es.udc.ws.app.client.service.exceptions;

public class ClientYaCanceladoException extends Exception{

    private Long idExcursion;

    public ClientYaCanceladoException(Long idExcursion){
        super("The excursion with id " + idExcursion + " has already been cancelled");
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
