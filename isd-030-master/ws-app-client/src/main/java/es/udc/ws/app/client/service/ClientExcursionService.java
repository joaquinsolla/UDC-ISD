package es.udc.ws.app.client.service;

import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientExcursionService {

    public Long anadirExcursion(ClientExcursionDto excursion)
        throws InputValidationException;

    public void actualizarExcursion(ClientExcursionDto excursion)
        throws InputValidationException, InstanceNotFoundException, ClientFechaFueraDeRangoException,
            ClientFechaCelebracionException, ClientNumeroMaximoPlazasException;

    public List<ClientExcursionDto> buscarExcursionesPorCiudad(String ciudad, String fechaInicial, String fechaFinal)
            throws InputValidationException;

    public Long realizarReserva(ClientReservaDto reserva)
        throws InputValidationException, InstanceNotFoundException,
            ClientReservaFueraPlazoException, ClientLimiteParticipantesAlcanzadosException;

    public void cancelarReserva(Long idReserva, String email)
        throws InstanceNotFoundException, InputValidationException,
            ClientCancelarReservaFueraDePlazoException, ClientYaCanceladoException,
            ClientDistintoEmailException;

    public List<ClientReservaDto> buscarReservasUsuario(String email)
        throws InputValidationException;
}