package es.udc.ws.app.restservice.servlets;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursionService.ExcursionServiceFactory;
import es.udc.ws.app.model.excursionService.excepciones.FechaCelebracionException;
import es.udc.ws.app.model.excursionService.excepciones.FechaFueraDeRangoException;
import es.udc.ws.app.model.excursionService.excepciones.NumeroMaximoPlazasException;
import es.udc.ws.app.restservice.dto.ExcursionToRestExcursionDtoConversor;
import es.udc.ws.app.restservice.dto.RestExcursionDto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestExcursionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ExcursionServlet extends RestHttpServletTemplate {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException {

        ServletUtils.checkEmptyPath(req);

        RestExcursionDto excursionDto = JsonToRestExcursionDtoConversor.toRestExcursionDto(req.getInputStream());
        Excursion excursion = ExcursionToRestExcursionDtoConversor.toExcursion(excursionDto);

        excursion = ExcursionServiceFactory.getService().anadirExcursion(excursion);

        excursionDto = ExcursionToRestExcursionDtoConversor.toRestExcursionDto(excursion);
        String excursionURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + excursion.getIdExcursion();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", excursionURL);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestExcursionDtoConversor.toObjectNode(excursionDto), headers);

    }

    @Override
    protected void processPut(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InstanceNotFoundException, InputValidationException {
        Long excursionId = ServletUtils.getIdFromPath(req, "excursion");

        RestExcursionDto excursionDto = JsonToRestExcursionDtoConversor.toRestExcursionDto(req.getInputStream());
        if(!excursionId.equals(excursionDto.getIdExcursion())){
            throw new InputValidationException("Invalid Request: invalid idExcursion");
        }
        Excursion excursion = ExcursionToRestExcursionDtoConversor.toExcursion(excursionDto);

        try {
            ExcursionServiceFactory.getService().actualizarExcursion(excursion);
        } catch (FechaFueraDeRangoException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    AppExceptionToJsonConversor.toFechaFueraDeRangoException(e),
                    null);
        } catch (FechaCelebracionException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    AppExceptionToJsonConversor.toFechaCelebracionException(e),
                    null);
        } catch (NumeroMaximoPlazasException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    AppExceptionToJsonConversor.toNumeroMaximoPlazasException(e),
                    null);
        }

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException {
        ServletUtils.checkEmptyPath(req);
        String ciudad = req.getParameter("ciudad");
        LocalDateTime fechaInicial = LocalDateTime.parse(req.getParameter("fechaInicial"));
        LocalDateTime fechaFinal = LocalDateTime.parse(req.getParameter("fechaFinal"));

        List<Excursion> excursion = ExcursionServiceFactory.getService().
                buscarExcursionesPorCiudad(ciudad, fechaInicial, fechaFinal);

        List<RestExcursionDto> excursionDtos = ExcursionToRestExcursionDtoConversor.toRestExcursionDtos(excursion);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestExcursionDtoConversor.toArrayNode(excursionDtos), null);
    }
}
