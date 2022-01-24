package es.udc.ws.app.model.excursionService.excepciones;

public class NumeroMaximoPlazasException extends Exception {

    private Long idExcursion;


    public NumeroMaximoPlazasException(Long idExcursion){

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