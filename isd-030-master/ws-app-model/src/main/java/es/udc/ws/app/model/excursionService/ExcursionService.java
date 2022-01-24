package es.udc.ws.app.model.excursionService;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursionService.excepciones.*;
import es.udc.ws.app.model.reserva.Reserva;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;


public interface ExcursionService {

    public Excursion anadirExcursion(Excursion excursion) throws InputValidationException;

    public void actualizarExcursion(Excursion excursion) throws InputValidationException, InstanceNotFoundException,
            FechaFueraDeRangoException, FechaCelebracionException, NumeroMaximoPlazasException;

    public List<Excursion> buscarExcursionesPorCiudad(String ciudad, LocalDateTime fechaInicial, LocalDateTime fechaFinal)
            throws InputValidationException;

    public Reserva realizarReserva(Reserva reserva) throws InputValidationException, InstanceNotFoundException,
            ReservaFueraPlazoException, LimiteParticipantesAlcanzadosException;

    public void cancelarReserva(Long idReserva, String email) throws InstanceNotFoundException, InputValidationException,
            CancelarReservaFueraDePlazoException, YaCanceladoException, DistintoEmailException;

    public List<Reserva> buscarReservasUsuario(String email) throws InputValidationException;

}
