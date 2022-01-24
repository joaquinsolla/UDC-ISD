package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.thrift.ThriftExcursionDto;

import java.time.LocalDateTime;

public class ExcursionToThriftExcursionDtoConversor {

    public static Excursion toExcursion(ThriftExcursionDto excursion){
        return new Excursion(excursion.getIdExcursion(),excursion.getCiudad(), excursion.getDescripcion(),
                LocalDateTime.parse(excursion.getFechaExcursion()), (float) excursion.getPrecioPorPersona(),
                excursion.getNumeroMaximoPlazas(), excursion.getNumeroMaximoPlazas());
    }

    public static ThriftExcursionDto toThriftExcursionDto(Excursion excursion){
        return new ThriftExcursionDto(excursion.getIdExcursion(), excursion.getCiudad(),
                excursion.getDescripcion(), excursion.getFechaExcursion().toString(),
                excursion.getPrecioPorPersona(), excursion.getNumeroMaximoPlazas(),
                excursion.getNumeroPlazasDisponibles());
    }
}
