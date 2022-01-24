package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;

public class JsonToClientExceptionConversor {

    public static Exception fromBadRequestErrorCode(InputStream exception) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(exception);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InputValidation")) {
                    return toInputValidationException(rootNode);
                }
                else if (errorType.equals("FechaCelebracion")){
                    return toFechaCelebracionException(rootNode);
                }
                else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InputValidationException toInputValidationException(JsonNode rootNode) {
        String message = rootNode.get("message").textValue();
        return new InputValidationException(message);
    }

    public static Exception fromNotFoundErrorCode(InputStream exception) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(exception);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InstanceNotFound")) {
                    return toInstanceNotFoundException(rootNode);
                } else if (errorType.equals("NumeroMaximoPlazas")){
                    return toNumeroMaximoPlazasException(rootNode);
                } else if (errorType.equals("LimiteParticipantesAlcanzados")){
                    return toLimiteParticipantesAlcanzadosException(rootNode);
                } else{
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static InstanceNotFoundException toInstanceNotFoundException(JsonNode rootNode) {
        String instanceId = rootNode.get("instanceId").textValue();
        String instanceType = rootNode.get("instanceType").textValue();
        return new InstanceNotFoundException(instanceId, instanceType);
    }

    public static Exception fromGoneErrorCode(InputStream exception) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(exception);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("FechaFueraDeRango")) {
                    return toFueraDeRangoException(rootNode);
                } else if (errorType.equals("ReservaFueraPlazo")){
                    return toReservaFueraPlazoException(rootNode);
                } else if (errorType.equals("CancelarReservaFueraDePlazo")){
                    return toCancelarReservaFueraDePlazoException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }


    private static ClientFechaFueraDeRangoException toFueraDeRangoException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return new ClientFechaFueraDeRangoException(idExcursion);
    }

    private static ClientFechaCelebracionException toFechaCelebracionException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return new ClientFechaCelebracionException(idExcursion);
    }


    private static ClientNumeroMaximoPlazasException toNumeroMaximoPlazasException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return new ClientNumeroMaximoPlazasException(idExcursion);
    }


    private static ClientReservaFueraPlazoException toReservaFueraPlazoException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return  new ClientReservaFueraPlazoException(idExcursion);
    }

    private static ClientLimiteParticipantesAlcanzadosException toLimiteParticipantesAlcanzadosException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return  new ClientLimiteParticipantesAlcanzadosException(idExcursion);
    }

    private static ClientCancelarReservaFueraDePlazoException toCancelarReservaFueraDePlazoException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        Long idReserva = rootNode.get("idReserva").longValue();
        return new ClientCancelarReservaFueraDePlazoException(idExcursion, idReserva);
    }

    public static Exception fromForbiddenErrorCode(InputStream exception) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(exception);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("YaCancelado")) {
                    return toYaCanceladoException(rootNode);
                } else if (errorType.equals("DistintoEmail")){
                    return toDistintoEmailException(rootNode);
                }
                else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }


    private static ClientYaCanceladoException toYaCanceladoException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        return new ClientYaCanceladoException(idExcursion);
    }

    private static ClientDistintoEmailException toDistintoEmailException(JsonNode rootNode) {
        Long idExcursion = rootNode.get("idExcursion").longValue();
        String email = rootNode.get("email").textValue();
        return new ClientDistintoEmailException(idExcursion, email);
    }

}


