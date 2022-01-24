package es.udc.ws.app.restservice.servlets;

import es.udc.ws.app.model.excursionService.ExcursionServiceFactory;
import es.udc.ws.app.model.excursionService.excepciones.*;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.restservice.dto.ReservaToRestReservaDtoConversor;
import es.udc.ws.app.restservice.dto.RestReservaDto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaServlet extends RestHttpServletTemplate {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InstanceNotFoundException, InputValidationException {

        if(req.getPathInfo() == null){
            //Hacemos el crear reserva
            Long idExcursion = ServletUtils.getMandatoryParameterAsLong(req,"idExcursion");
            String email = ServletUtils.getMandatoryParameter(req,"email");
            String numeroTarjetaBancaria = ServletUtils.getMandatoryParameter(req,"numeroTarjetaBancaria");
            int numeroPersonas = Integer.parseInt(ServletUtils.getMandatoryParameter(req,"numeroPersonas"));

            Reserva reserva = null;
            try {
                reserva = ExcursionServiceFactory.getService().realizarReserva(new Reserva(idExcursion, email, numeroPersonas, numeroTarjetaBancaria));
            } catch (ReservaFueraPlazoException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                        AppExceptionToJsonConversor.toReservaFueraPlazoException(e),
                        null);
            } catch (LimiteParticipantesAlcanzadosException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        AppExceptionToJsonConversor.toLimiteParticipantesAlcanzadosException(e),
                        null);
            }

            assert reserva != null;
            RestReservaDto reservaDto = ReservaToRestReservaDtoConversor.toRestReservaDto(reserva);
            String saleURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + reserva.getIdReserva().toString();
            Map<String, String> headers = new HashMap<>(1);
            headers.put("Location", saleURL);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                    JsonToRestReservaDtoConversor.toObjectNode(reservaDto), headers);
        } else {
            String[] path = req.getPathInfo().split("/");
            if (path[2].equals("cancelar")){
                //Hacemos el cancelar reserva
                Long idReserva = Long.parseLong(path[1]);

                String email = ServletUtils.getMandatoryParameter(req, "email");

                try {
                    ExcursionServiceFactory.getService().cancelarReserva(idReserva, email);

                    ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);

                } catch (DistintoEmailException e) {
                    ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                            AppExceptionToJsonConversor.toDistintoEmailException(e),
                            null);
                } catch (CancelarReservaFueraDePlazoException e) {
                    ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                            AppExceptionToJsonConversor.toCancelarReservaFueraDePlazoException(e),
                            null);
                } catch (YaCanceladoException e) {
                    ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                            AppExceptionToJsonConversor.toYaCanceladoException(e),
                            null);
                }
            }
        }
    }


    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException {
        ServletUtils.checkEmptyPath(req);
        String email = req.getParameter("email");

        List<Reserva> reservas = ExcursionServiceFactory.getService().buscarReservasUsuario(email);

        List<RestReservaDto> reservaDtos = ReservaToRestReservaDtoConversor.toRestReservaDtos(reservas);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestReservaDtoConversor.toArrayNode(reservaDtos), null);
    }


}


