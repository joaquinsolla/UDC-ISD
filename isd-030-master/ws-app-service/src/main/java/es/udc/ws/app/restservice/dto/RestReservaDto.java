package es.udc.ws.app.restservice.dto;

public class RestReservaDto {

    private Long idReserva;
    private Long idExcursion;
    private String email;
    private int numeroPersonas;
    private String numeroTarjetaBancaria;
    private String fechaReserva;
    private float precioReserva;
    private String fechaCancelacion;

    public RestReservaDto(){}

    public RestReservaDto(Long idReserva, Long idExcursion, String email,
                          int numeroPersonas, String numeroTarjetaBancaria,
                          String fechaReserva, float precioReserva,
                          String fechaCancelacion) {
        this.idReserva = idReserva;
        this.idExcursion = idExcursion;
        this.email = email;
        this.numeroPersonas = numeroPersonas;
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
        this.fechaReserva = fechaReserva;
        this.precioReserva = precioReserva;
        this.fechaCancelacion = fechaCancelacion;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

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

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getNumeroTarjetaBancaria() {
        return numeroTarjetaBancaria;
    }

    public void setNumeroTarjetaBancaria(String numeroTarjetaBancaria) {
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public float getPrecioReserva() {
        return precioReserva;
    }

    public void setPrecioReserva(float precioReserva) {
        this.precioReserva = precioReserva;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }
}
