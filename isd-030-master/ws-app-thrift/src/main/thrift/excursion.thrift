namespace java es.udc.ws.app.thrift

struct ThriftExcursionDto {
    1: i64 idExcursion;
    2: string ciudad;
    3: string descripcion;
    4: string fechaExcursion;
    5: double precioPorPersona;
    6: i32 numeroMaximoPlazas;
    7: i32 numeroPlazasDisponibles;
}

exception ThriftInputValidationException {
    1: string message
}

exception ThriftInstanceNotFoundException {
    1: string instanceId
    2: string instanceType
}

exception ThriftFechaFueraDeRangoException {
    1: i64 idExcursion;
}

exception ThriftFechaCelebracionException {
    1: i64 idExcursion;
}

exception ThriftNumeroMaximoPlazasException {
    1: i64 idExcursion;
}

service ThriftExcursionService {

    ThriftExcursionDto anadirExcursion(1: ThriftExcursionDto excursionDto) throws (1: ThriftInputValidationException e)

    void actualizarExcursion(1: ThriftExcursionDto excursionDto) throws (1: ThriftInputValidationException e1, 2: ThriftInstanceNotFoundException e2, 3: ThriftFechaFueraDeRangoException e3, 4: ThriftFechaCelebracionException e4, 5: ThriftNumeroMaximoPlazasException e5)
}
