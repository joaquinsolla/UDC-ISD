package es.udc.ws.app.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.ws.app.client.service.ClientExcursionService;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.app.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientExcursionDtoConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientReservaDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;


public class RestClientExcursionService implements ClientExcursionService {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientExcursionService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long anadirExcursion(ClientExcursionDto excursion)
            throws InputValidationException{

        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "excursion").
                    bodyStream(toInputStream(excursion), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientExcursionDtoConversor.toClientExcursionDto(response.getEntity().getContent()).getIdExcursion();
        } catch (InputValidationException e){
            throw e;
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void actualizarExcursion(ClientExcursionDto excursion) throws InputValidationException,
            InstanceNotFoundException, ClientNumeroMaximoPlazasException, ClientFechaCelebracionException {

        try {
            HttpResponse response = Request.Put(getEndpointAddress() + "excursion/" + excursion.getIdExcursion()).
                    bodyStream(toInputStream(excursion), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        } catch (InputValidationException e) {
            throw e;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (ClientFechaCelebracionException e) {
            throw e;
        } catch (ClientNumeroMaximoPlazasException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ClientExcursionDto> buscarExcursionesPorCiudad(String city, String fechaInicial, String fechaFinal) throws InputValidationException {

        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "excursion?ciudad="
                            + URLEncoder.encode(city, "UTF-8") + "&fechaInicial="
                            + URLEncoder.encode(fechaInicial, "UTF-8") + "&fechaFinal="
                            + URLEncoder.encode(fechaFinal, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);
            return JsonToClientExcursionDtoConversor.toClientExcursionDtos(response.getEntity()
                    .getContent());

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Long realizarReserva(ClientReservaDto reserva)
            throws InputValidationException, InstanceNotFoundException,
            ClientReservaFueraPlazoException, ClientLimiteParticipantesAlcanzadosException {
        try {


            HttpResponse response = Request.Post(getEndpointAddress() + "reserva").
                    bodyForm(
                            Form.form().
                                    add("idExcursion", Long.toString(reserva.getIdExcursion())).
                                    add("email", reserva.getEmail()).
                                    add("numeroTarjetaBancaria", reserva.getNumeroTarjetaBancaria()).
                                    add("numeroPersonas", Integer.toString(reserva.getNumeroPersonas())).
                                    build()).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientReservaDtoConversor.toClientReservaDto(response.getEntity().getContent()).getIdReserva();

        } catch (InputValidationException | InstanceNotFoundException | ClientReservaFueraPlazoException | ClientLimiteParticipantesAlcanzadosException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelarReserva(Long idReserva, String email)
            throws InstanceNotFoundException, InputValidationException,
            ClientCancelarReservaFueraDePlazoException, ClientYaCanceladoException,
            ClientDistintoEmailException{

        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "reserva/" + idReserva + "/cancelar").
                    bodyForm(
                            Form.form().
                                    add("email", email).
                                    build()).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

        } catch (InputValidationException | InstanceNotFoundException | ClientCancelarReservaFueraDePlazoException
                | ClientYaCanceladoException | ClientDistintoEmailException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<ClientReservaDto> buscarReservasUsuario(String email)
            throws InputValidationException{
        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "reserva?email="
                            + URLEncoder.encode(email, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientReservaDtoConversor.toClientReservaDtos(response.getEntity()
                    .getContent());

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientExcursionDto excursion) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientExcursionDtoConversor.toObjectNode(excursion));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private InputStream toInputStream(ClientReservaDto reserva) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientReservaDtoConversor.toObjectNode(reserva));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == successCode) {
            return;
        }


            switch (statusCode) {

                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_FORBIDDEN:
                    throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw JsonToClientExceptionConversor.fromGoneErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }


    }

}
