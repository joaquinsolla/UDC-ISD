package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.restservice.dto.RestExcursionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.util.List;

public class JsonToRestExcursionDtoConversor {

    public static ObjectNode toObjectNode(RestExcursionDto excursion){

        ObjectNode excursionObject = JsonNodeFactory.instance.objectNode();

        if (excursion.getIdExcursion() != null) {
            excursionObject.put("idExcursion", excursion.getIdExcursion());
        }
        excursionObject.put("ciudad", excursion.getCiudad()).
                put("descripcion", excursion.getDescripcion()).
                put("fechaExcursion", excursion.getFechaExcursion()).
                put("precioPorPersona", excursion.getPrecioPorPersona()).
                put("numeroMaximoPlazas", excursion.getNumeroMaximoPlazas()).
                put("numeroPlazasDisponibles", excursion.getNumeroPlazasDisponibles());

        return excursionObject;

    }


    public static ArrayNode toArrayNode(List<RestExcursionDto> excursion){

        ArrayNode excursionNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < excursion.size(); i++){
            RestExcursionDto excursionDto = excursion.get(i);
            ObjectNode excursionObject = toObjectNode(excursionDto);
            excursionNode.add(excursionObject);
        }

        return excursionNode;
    }


    public static RestExcursionDto toRestExcursionDto(InputStream jsonExcursion) throws ParsingException{

        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT){
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {

                ObjectNode excursionObject = (ObjectNode) rootNode;

                JsonNode excursionIdNode = excursionObject.get("idExcursion");
                Long excursionId = (excursionIdNode != null) ? excursionIdNode.longValue() : null;

                String ciudad = excursionObject.get("ciudad").textValue().trim();
                String descripcion = excursionObject.get("descripcion").textValue().trim();
                String fechaExcursion = excursionObject.get("fechaExcursion").textValue().trim();
                float precioPorPersona = excursionObject.get("precioPorPersona").floatValue();
                int numeroMaximoPlazas = excursionObject.get("numeroMaximoPlazas").intValue();
                JsonNode numeroPlazasDisponiblesNode = excursionObject.get("numeroPlazasDisponibles");
                int numeroPlazasDisponibles = (numeroPlazasDisponiblesNode != null) ? excursionObject.get("numeroPlazasDisponibles").intValue() : 0;

                return new RestExcursionDto(excursionId, ciudad, descripcion, fechaExcursion,
                        precioPorPersona, numeroMaximoPlazas, numeroPlazasDisponibles);
            }

        } catch (ParsingException ex){
            throw ex;
        } catch (Exception e){
            throw new ParsingException(e);
        }

    }

}
