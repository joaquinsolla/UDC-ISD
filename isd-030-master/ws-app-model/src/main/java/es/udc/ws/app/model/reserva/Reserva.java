package es.udc.ws.app.model.reserva;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva {


    private Long idReserva;
    private Long idExcursion;
    private String email;
    private int numeroPersonas;
    private String numeroTarjetaBancaria;
    private LocalDateTime fechaReserva;
    private float precioReserva;
    private LocalDateTime fechaCancelacion;


    public Reserva(Long idReserva, Long idExcursion, int numeroPersonas, LocalDateTime fechaCancelacion) {
        this.idReserva = idReserva;
        this.idExcursion = idExcursion;
        this.numeroPersonas = numeroPersonas;
        this.fechaCancelacion = fechaCancelacion;
    }

    public Reserva(Long idExcursion, String email, int numeroPersonas,
                   String numeroTarjetaBancaria, float precioReserva){
        this.idExcursion = idExcursion;
        this.email = email;
        this.numeroPersonas = numeroPersonas;
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
        this.precioReserva = precioReserva;
    }

    public Reserva(Long idExcursion, String email, int numeroPersonas,
                   String numeroTarjetaBancaria){
        this.idExcursion = idExcursion;
        this.email = email;
        this.numeroPersonas = numeroPersonas;
        this.numeroTarjetaBancaria = numeroTarjetaBancaria;
    }



    public Reserva(Long idReserva, Long idExcursion, String email, int numeroPersonas,
                   String numeroTarjetaBancaria, LocalDateTime fechaReserva,
                   float precioReserva, LocalDateTime fechaCancelacion) {
        this(idExcursion, email, numeroPersonas, numeroTarjetaBancaria, precioReserva);
        this.idReserva = idReserva;
        this.fechaReserva = (fechaReserva != null) ? fechaReserva.withNano(0) : null;
        this.fechaCancelacion = (fechaCancelacion != null) ? fechaCancelacion.withNano(0) : null;
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

        this.fechaReserva = (fechaReserva != null) ? fechaReserva.withNano(0) : null;
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

        this.fechaCancelacion = (fechaCancelacion != null) ? fechaCancelacion.withNano(0) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return numeroPersonas == reserva.numeroPersonas &&
                Float.compare(reserva.precioReserva, precioReserva) == 0 &&
                Objects.equals(idReserva, reserva.idReserva) &&
                Objects.equals(idExcursion, reserva.idExcursion) &&
                Objects.equals(email, reserva.email) &&
                Objects.equals(numeroTarjetaBancaria, reserva.numeroTarjetaBancaria) &&
                Objects.equals(fechaReserva, reserva.fechaReserva) &&
                Objects.equals(fechaCancelacion, reserva.fechaCancelacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva, idExcursion, email, numeroPersonas, numeroTarjetaBancaria, fechaReserva, precioReserva, fechaCancelacion);
    }
}
