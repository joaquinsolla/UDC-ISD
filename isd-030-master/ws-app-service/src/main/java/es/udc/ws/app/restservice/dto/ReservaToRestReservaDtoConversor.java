package es.udc.ws.app.restservice.dto;

import es.udc.ws.app.model.reserva.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaToRestReservaDtoConversor {

    public static List<RestReservaDto> toRestReservaDtos(List<Reserva> reservas){
        List<RestReservaDto> reservasDto = new ArrayList<>(reservas.size());
        for (int i = 0; i < reservas.size(); i++){
            Reserva reserva = reservas.get(i);
            reservasDto.add(toRestReservaDto(reserva));
        }
        return reservasDto;
    }

    public static RestReservaDto toRestReservaDto(Reserva reserva){
        return new RestReservaDto(reserva.getIdReserva(), reserva.getIdExcursion(),
                reserva.getEmail(), reserva.getNumeroPersonas(),
                reserva.getNumeroTarjetaBancaria().substring(reserva.getNumeroTarjetaBancaria().length() - 4),
                reserva.getFechaReserva().toString(), reserva.getPrecioReserva(),
                (reserva.getFechaCancelacion() == null) ? null : reserva.getFechaCancelacion().toString());
    }

    public static Reserva toReserva(RestReservaDto reserva){
        return new Reserva(reserva.getIdReserva(), reserva.getIdExcursion(),
                reserva.getEmail(), reserva.getNumeroPersonas(),
                reserva.getNumeroTarjetaBancaria(), LocalDateTime.parse(reserva.getFechaReserva()),
                reserva.getPrecioReserva(), LocalDateTime.parse(reserva.getFechaCancelacion()));
    }

}
