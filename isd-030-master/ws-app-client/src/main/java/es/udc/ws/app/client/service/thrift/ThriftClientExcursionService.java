package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.ClientExcursionService;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.List;

public class ThriftClientExcursionService implements ClientExcursionService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientExcursionService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

    @Override
    public Long anadirExcursion(ClientExcursionDto excursion) throws InputValidationException {

        ThriftExcursionService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return client.anadirExcursion(ClientExcursionDtoToThriftExcursionDtoConversor.toThriftExcursionDto(excursion)).getIdExcursion();

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public void actualizarExcursion(ClientExcursionDto excursion) throws InputValidationException, InstanceNotFoundException, ClientFechaFueraDeRangoException, ClientFechaCelebracionException, ClientNumeroMaximoPlazasException {
        ThriftExcursionService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();
            client.actualizarExcursion(ClientExcursionDtoToThriftExcursionDtoConversor.toThriftExcursionDto(excursion));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (ThriftInstanceNotFoundException e) {
            throw new InstanceNotFoundException(e.getInstanceId(), e.getInstanceType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientExcursionDto> buscarExcursionesPorCiudad(String ciudad, String fechaInicial, String fechaFinal) throws InputValidationException {
        return null;
    }

    @Override
    public Long realizarReserva(ClientReservaDto reserva) throws InputValidationException, InstanceNotFoundException, ClientReservaFueraPlazoException, ClientLimiteParticipantesAlcanzadosException {
        return null;
    }

    @Override
    public void cancelarReserva(Long idReserva, String email) throws InstanceNotFoundException, InputValidationException, ClientCancelarReservaFueraDePlazoException, ClientYaCanceladoException, ClientDistintoEmailException {

    }

    @Override
    public List<ClientReservaDto> buscarReservasUsuario(String email) throws InputValidationException {
        return null;
    }

    private ThriftExcursionService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftExcursionService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }
}


