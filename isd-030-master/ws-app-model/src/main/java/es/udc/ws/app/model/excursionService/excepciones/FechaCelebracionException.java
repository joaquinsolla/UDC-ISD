package es.udc.ws.app.model.excursionService.excepciones;

import java.time.LocalDateTime;

public class FechaCelebracionException extends Exception{

    private Long idExcursion;


    public FechaCelebracionException(Long idExcrusion){

        super ("Tried to update excursion with id=\"" + idExcrusion +
                "\" with a new date earlier " +
                "than the old one.");
        this.idExcursion = idExcrusion;
    }

    //Getters y Setters

    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }
}
