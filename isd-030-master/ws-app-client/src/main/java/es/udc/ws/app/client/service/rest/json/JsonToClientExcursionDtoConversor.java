package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientExcursionDtoConversor {

    public static ObjectNode toObjectNode(ClientExcursionDto excursion) throws IOException{

        ObjectNode excursionObject = JsonNodeFactory.instance.objectNode();

        if (excursion.getIdExcursion() != null){
            excursionObject.put("idExcursion", excursion.getIdExcursion());
        }

        excursionObject.put("ciudad", excursion.getCiudad()).
                put("descripcion", excursion.getDescripcion()).
                put("fechaExcursion", excursion.getFechaExcursion().toString()).
                put("precioPorPersona", excursion.getPrecioPorPersona()).
                put("numeroMaximoPlazas", excursion.getNumeroMaximoPlazas()).
                put("numeroPlazasDisponibles", excursion.getNumeroMaximoPlazas() - excursion.getNumeroPlazasReservadas());

        return excursionObject;
    }

    public static ClientExcursionDto toClientExcursionDto(InputStream jsonExcursion) throws ParsingException {

        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT){
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientExcursionDto(rootNode);
            }
        } catch (ParsingException ex){
            throw ex;
        } catch (Exception e){
            throw new ParsingException(e);
        }
    }

    public static List<ClientExcursionDto> toClientExcursionDtos(InputStream jsonExcursion) throws ParsingException{

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY){
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode excursionArray = (ArrayNode) rootNode;
                List<ClientExcursionDto> excursionDtos = new ArrayList<>(excursionArray.size());
                for (JsonNode excursionNode : excursionArray){
                    excursionDtos.add(toClientExcursionDto(excursionNode));
                }

                return excursionDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e){
            throw new ParsingException(e);
        }
    }


    private static ClientExcursionDto toClientExcursionDto(JsonNode excursionNode) throws ParsingException{

        if (excursionNode.getNodeType() != JsonNodeType.OBJECT){
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode excursionObject = (ObjectNode) excursionNode;

            JsonNode excursionIdNode = excursionObject.get("idExcursion");
            Long idExcursion = (excursionIdNode != null) ? excursionIdNode.longValue() : null;

            String ciudad = excursionObject.get("ciudad").textValue().trim();
            String descripcion = excursionObject.get("descripcion").textValue().trim();
            String fechaExcursion = excursionObject.get("fechaExcursion").textValue().trim();
            float precioPorPersona = excursionObject.get("precioPorPersona").floatValue();
            int numeroMaximoPlazas = excursionObject.get("numeroMaximoPlazas").intValue();
            int numeroPlazasDisponibles = excursionObject.get("numeroPlazasDisponibles").intValue();

            return new ClientExcursionDto(idExcursion, ciudad, descripcion, LocalDateTime.parse(fechaExcursion),
                    precioPorPersona, numeroMaximoPlazas, numeroMaximoPlazas - numeroPlazasDisponibles);
        }

    }

}
