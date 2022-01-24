package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.restservice.dto.RestReservaDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestReservaDtoConversor {

        public static ObjectNode toObjectNode(RestReservaDto reserva){

            ObjectNode reservaObject = JsonNodeFactory.instance.objectNode();

            if (reserva.getIdReserva() != null){
                reservaObject.put("idReserva", reserva.getIdReserva());
            }
            reservaObject.put("idExcursion", reserva.getIdExcursion()).
                    put("email", reserva.getEmail()).
                    put("numeroPersonas", reserva.getNumeroPersonas()).
                    put("numeroTarjetaBancaria", reserva.getNumeroTarjetaBancaria()).
                    put("fechaReserva", reserva.getFechaReserva()).
                    put("precioReserva", reserva.getPrecioReserva()).
                    put("fechaCancelacion", reserva.getFechaCancelacion());

            return reservaObject;
        }

        public static ArrayNode toArrayNode(List<RestReservaDto> reserva){

            ArrayNode reservaNode = JsonNodeFactory.instance.arrayNode();
            for (int i = 0; i < reserva.size(); i++){
                RestReservaDto reservaDto = reserva.get(i);
                ObjectNode reservaObject = toObjectNode(reservaDto);
                reservaNode.add(reservaObject);
            }

            return reservaNode;
        }

        public static RestReservaDto toRestReservaDto(InputStream jsonReserva) throws ParsingException{

            try {
                ObjectMapper objectMapper = ObjectMapperFactory.instance();
                JsonNode rootNode = objectMapper.readTree(jsonReserva);

                if (rootNode.getNodeType() != JsonNodeType.OBJECT){
                    throw new ParsingException("Unrecognized JSON (object expected)");
                } else {
                    ObjectNode reservaObject = (ObjectNode) rootNode;

                    JsonNode reservaIdNode = reservaObject.get("idReserva");
                    Long reservaId = (reservaIdNode != null) ? reservaIdNode.longValue() : null;

                    Long excursionId = reservaObject.get("idExcursion").longValue();
                    String email = reservaObject.get("email").textValue().trim();
                    int numeroPersonas = reservaObject.get("numeroPersonas").intValue();
                    String numeroTarjetaBancaria = reservaObject.get("numeroTarjetaBancaria").textValue().trim();
                    String fechaReserva = reservaObject.get("fechaReserva").textValue().trim();
                    float precioReserva = reservaObject.get("precioReserva").floatValue();
                    String fechaCancelacion = reservaObject.get("fechaCancelacion").textValue().trim();

                    return new RestReservaDto(reservaId, excursionId, email,
                            numeroPersonas, numeroTarjetaBancaria, fechaReserva,
                            precioReserva, fechaCancelacion);
                }

            } catch (ParsingException ex){
                throw ex;
            } catch (Exception e){
                throw new ParsingException(e);
            }

        }
}
