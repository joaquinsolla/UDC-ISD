package es.udc.ws.app.model.excursionService.excepciones;

public class YaCanceladoException extends Exception {

    private Long idExcursion;

    public YaCanceladoException(Long idExcursion){
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
