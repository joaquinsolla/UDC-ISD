package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientReservaDto {

    private Long idReserva;
    private Long idExcursion;
    private String email;
    private int numeroPersonas;
    private String numeroTarjetaBancaria;
    private LocalDateTime fechaReserva;
    private float precioReserva;
    private LocalDateTime fechaCancelacion;


    public ClientReservaDto() {
    }

    public ClientReservaDto(Long idReserva, Long idExcursion,
                            String email, int numeroPersonas,
                            String numeroTarjetaBancaria, LocalDateTime fechaReserva,
                            float precioReserva, LocalDateTime fechaCancelacion) {
        this.idReserva = idReserva;
        this.idExcursion = idExcursion;
        this.email = email;
        this.numeroPersonas = numeroPersonas;
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
        this.fechaReserva = fechaReserva;
        this.precioReserva = precioReserva;
        this.fechaCancelacion = fechaCancelacion;
    }

    public ClientReservaDto(Long idExcursion, String email, int numeroPersonas, String numeroTarjetaBancaria) {
        this.idExcursion = idExcursion;
        this.email = email;
        this.numeroPersonas = numeroPersonas;
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
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

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public float getPrecioReserva() {
        return precioReserva;
    }

    public void setPrecioReserva(float precioReserva) {
        this.precioReserva = precioReserva;
    }

    public LocalDateTime getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }
}
