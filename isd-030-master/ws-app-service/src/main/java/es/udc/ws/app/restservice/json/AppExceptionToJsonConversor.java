package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.model.excursionService.excepciones.*;

public class AppExceptionToJsonConversor {

    public static ObjectNode toCancelarReservaFueraDePlazoException(CancelarReservaFueraDePlazoException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "CancelarReservaFueraDePlazo");
        exceptionObject.put("idExcursion", ex.getIdExcursion());
        exceptionObject.put("idReserva", ex.getIdReserva());

        return exceptionObject;
    }

    public static ObjectNode toDistintoEmailException(DistintoEmailException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "DistintoEmail");
        exceptionObject.put("idExcursion", ex.getIdExcursion());
        exceptionObject.put("email", ex.getEmail());

        return exceptionObject;
    }

    public static ObjectNode toFechaCelebracionException(FechaCelebracionException ex) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "FechaCelebracion");
        exceptionObject.put("idExcursion", ex.getIdExcursion());

        return exceptionObject;
    }

    public static ObjectNode toFechaFueraDeRangoException(FechaFueraDeRangoException ex) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "FechaFueraDeRango");
        exceptionObject.put("idExcursion", ex.getIdExcursion());

        return exceptionObject;
    }

    public static ObjectNode toLimiteParticipantesAlcanzadosException(LimiteParticipantesAlcanzadosException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "LimiteParticipantesAlcanzados");
        exceptionObject.put("idExcursion", ex.getidExcursion());

        return exceptionObject;
    }

    public static ObjectNode toNumeroMaximoPlazasException(NumeroMaximoPlazasException ex) {
        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "NumeroMaximoPlazas");
        exceptionObject.put("idExcursion", ex.getIdExcursion());

        return exceptionObject;
    }

    public static ObjectNode toReservaFueraPlazoException(ReservaFueraPlazoException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "ReservaFueraPlazo");
        exceptionObject.put("idExcursion", ex.getidExcursion());

        return exceptionObject;
    }

    public static ObjectNode toYaCanceladoException(YaCanceladoException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "YaCancelado");
        exceptionObject.put("idExcursion", ex.getIdExcursion());

        return exceptionObject;
    }
}