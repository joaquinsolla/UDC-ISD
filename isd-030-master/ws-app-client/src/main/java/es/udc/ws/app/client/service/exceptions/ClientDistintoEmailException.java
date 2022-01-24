package es.udc.ws.app.client.service.exceptions;

public class ClientDistintoEmailException extends Exception{

    private Long idExcursion;
    private String email;

    public ClientDistintoEmailException(Long idExcursion, String email){
        super("The excursion with id " + idExcursion + " was not booked with this " + email +
                " so it cannot be cancelled");
        this.idExcursion = idExcursion;
        this.email = email;
    }

    //Getters y Setters

    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
