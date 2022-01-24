package es.udc.ws.app.restservice.dto;

public class RestExcursionDto {

    private Long idExcursion;
    private String ciudad;
    private String descripcion;
    private String fechaExcursion;
    private float precioPorPersona;
    private int numeroMaximoPlazas;
    private int numeroPlazasDisponibles;

    public RestExcursionDto(){}


    public RestExcursionDto(Long idExcursion, String ciudad, String descripcion,
                            String fechaExcursion, float precioPorPersona,
                            int numeroMaximoPlazas, int numeroPlazasDisponibles) {

        this.idExcursion = idExcursion;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.precioPorPersona = precioPorPersona;
        this.numeroMaximoPlazas = numeroMaximoPlazas;
        this.numeroPlazasDisponibles = numeroPlazasDisponibles;
    }

    public RestExcursionDto(Long idExcursion, String ciudad, String descripcion,
                            String fechaExcursion, float precioPorPersona,
                            int numeroMaximoPlazas) {

        this.idExcursion = idExcursion;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.precioPorPersona = precioPorPersona;
        this.numeroMaximoPlazas = numeroMaximoPlazas;
    }

    public Long getIdExcursion() { return idExcursion; }

    public void setIdExcursion(Long idExcursion) { this.idExcursion = idExcursion; }

    public String getCiudad() { return ciudad; }

    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFechaExcursion() { return fechaExcursion; }

    public void setFechaExcursion(String fechaExcursion) { this.fechaExcursion = fechaExcursion; }

    public float getPrecioPorPersona() { return precioPorPersona; }

    public void setPrecioPorPersona(float precioPorPersona) { this.precioPorPersona = precioPorPersona; }

    public int getNumeroMaximoPlazas() { return numeroMaximoPlazas; }

    public void setNumeroMaximoPlazas(int numeroMaximoPlazas) { this.numeroMaximoPlazas = numeroMaximoPlazas; }

    public int getNumeroPlazasDisponibles() { return numeroPlazasDisponibles; }

    public void setNumeroPlazasDisponibles(int numeroPlazasDisponibles) { this.numeroPlazasDisponibles = numeroPlazasDisponibles; }

    @Override
    public String toString() {
        return "RestExcursionDto[" +
                "idExcursion=" + idExcursion +
                ", ciudad='" + ciudad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaExcursion='" + fechaExcursion + '\'' +
                ", precioPorPersona=" + precioPorPersona +
                ", numeroMaximoPlazas=" + numeroMaximoPlazas +
                ", numeroPlazasDisponibles=" + numeroPlazasDisponibles +
                ']';
    }
}
