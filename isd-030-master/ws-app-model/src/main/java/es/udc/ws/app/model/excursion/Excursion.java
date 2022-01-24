package es.udc.ws.app.model.excursion;

import java.time.LocalDateTime;
import java.util.Objects;

public class Excursion {

    private Long idExcursion;
    private String ciudad;
    private String descripcion;
    private LocalDateTime fechaExcursion;
    private float precioPorPersona;
    private int numeroMaximoPlazas;
    private LocalDateTime fechaAltaExcursion;
    private int numeroPlazasDisponibles;


    public Excursion(String ciudad, String descripcion, LocalDateTime fechaExcursion,
                     float precioPorPersona, int numeroMaximoPlazas){
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.precioPorPersona = precioPorPersona;
        this.numeroMaximoPlazas = numeroMaximoPlazas;
        this.fechaExcursion = (fechaExcursion != null) ? fechaExcursion.withNano(0) : null;
        this.numeroPlazasDisponibles = numeroMaximoPlazas;
    }

    public Excursion(Long idExcursion, String ciudad, String descripcion,
                     LocalDateTime fechaExcursion, float precioPorPersona, int numeroMaximoPlazas) {
        this.idExcursion = idExcursion;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.precioPorPersona = precioPorPersona;
        this.numeroMaximoPlazas = numeroMaximoPlazas;
    }


    public Excursion(String ciudad, String descripcion, LocalDateTime fechaExcursion, float precioPorPersona,
                     int numeroMaximoPlazas, LocalDateTime fechaAltaExcursion) {
        this(ciudad, descripcion, fechaExcursion, precioPorPersona, numeroMaximoPlazas);

        this.fechaAltaExcursion = (fechaAltaExcursion != null) ? fechaAltaExcursion.withNano(0) : null;
    }

    public Excursion(Long idExcursion, String ciudad, String descripcion, LocalDateTime fechaExcursion,
                     float precioPorPersona, int numeroMaximoPlazas, int numeroPlazasDisponibles) {
        this.idExcursion = idExcursion;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.precioPorPersona = precioPorPersona;
        this.numeroMaximoPlazas = numeroMaximoPlazas;
        this.numeroPlazasDisponibles = numeroPlazasDisponibles;
    }

    public Excursion(Long idExcursion, String ciudad, String descripcion, LocalDateTime fechaExcursion,
                     float precioPorPersona, int numeroMaximoPlazas, LocalDateTime fechaAltaExcursion, int numeroPlazasDisponibles) {
        this(ciudad, descripcion, fechaExcursion, precioPorPersona, numeroMaximoPlazas);

        this.idExcursion = idExcursion;
        this.fechaAltaExcursion = (fechaAltaExcursion != null) ? fechaAltaExcursion.withNano(0) : null;
        this.numeroPlazasDisponibles = numeroPlazasDisponibles;
    }


    public Long getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(Long idExcursion) {
        this.idExcursion = idExcursion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaExcursion() {
        return fechaExcursion;
    }

    public void setFechaExcursion(LocalDateTime fechaExcursion) {

        this.fechaExcursion = (fechaExcursion != null) ? fechaExcursion.withNano(0) : null;;
    }

    public float getPrecioPorPersona() {
        return precioPorPersona;
    }

    public void setPrecioPorPersona(float precioPorPersona) {
        this.precioPorPersona = precioPorPersona;
    }

    public int getNumeroMaximoPlazas() {
        return numeroMaximoPlazas;
    }

    public void setNumeroMaximoPlazas(int numeroMaximoPlazas) {
        this.numeroMaximoPlazas = numeroMaximoPlazas;
    }

    public LocalDateTime getFechaAltaExcursion() {
        return fechaAltaExcursion;
    }

    public void setFechaAltaExcursion(LocalDateTime fechaAltaExcursion) {
        this.fechaAltaExcursion = (fechaAltaExcursion != null) ? fechaAltaExcursion.withNano(0) : null;
    }

    public int getNumeroPlazasDisponibles() {
        return numeroPlazasDisponibles;
    }


    public void setNumeroPlazasDisponibles(int numeroPlazasDisponibles) {
        this.numeroPlazasDisponibles = numeroPlazasDisponibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Excursion excursion = (Excursion) o;
        return Float.compare(excursion.precioPorPersona, precioPorPersona) == 0 &&
                numeroMaximoPlazas == excursion.numeroMaximoPlazas &&
                numeroPlazasDisponibles == excursion.numeroPlazasDisponibles &&
                Objects.equals(idExcursion, excursion.idExcursion) &&
                Objects.equals(ciudad, excursion.ciudad) &&
                Objects.equals(descripcion, excursion.descripcion) &&
                Objects.equals(fechaExcursion, excursion.fechaExcursion) &&
                Objects.equals(fechaAltaExcursion, excursion.fechaAltaExcursion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExcursion, ciudad, descripcion, fechaExcursion, precioPorPersona, numeroMaximoPlazas, fechaAltaExcursion, numeroPlazasDisponibles);
    }
}
