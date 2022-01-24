package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.thrift.ThriftExcursionDto;

import java.time.LocalDateTime;

public class ClientExcursionDtoToThriftExcursionDtoConversor {

    public static ThriftExcursionDto toThriftExcursionDto(
            ClientExcursionDto clientExcursionDto) {

        Long excursionId = clientExcursionDto.getIdExcursion();

        return new ThriftExcursionDto(
                excursionId == null ? -1 : excursionId.longValue(),
                clientExcursionDto.getCiudad(),
                clientExcursionDto.getDescripcion(),
                clientExcursionDto.getFechaExcursion().toString(),
                clientExcursionDto.getPrecioPorPersona(),
                clientExcursionDto.getNumeroMaximoPlazas(),
                clientExcursionDto.getNumeroMaximoPlazas() - clientExcursionDto.getNumeroPlazasReservadas());
    }

    private static ClientExcursionDto toClientExcursionDto(ThriftExcursionDto excursion) {

        return new ClientExcursionDto(
                excursion.getIdExcursion(),
                excursion.getCiudad(),
                excursion.getDescripcion(),
                LocalDateTime.parse(excursion.getFechaExcursion()),
                (float) excursion.getPrecioPorPersona(),
                excursion.getNumeroMaximoPlazas(),
                excursion.getNumeroMaximoPlazas() - excursion.getNumeroPlazasDisponibles());
    }
}
