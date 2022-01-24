package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientReservaDtoConversor {

    public static ObjectNode toObjectNode(ClientReservaDto reserva) throws IOException {

        ObjectNode reservaObject = JsonNodeFactory.instance.objectNode();

        if (reserva.getIdReserva() != null){
            reservaObject.put("idReserva", reserva.getIdReserva());
        }

        reservaObject.put("idExcursion", reserva.getIdExcursion()).
                put("email", reserva.getEmail()).
                put("numeroPersonas", reserva.getNumeroPersonas()).
                put("numeroTarjetaBancario", reserva.getNumeroTarjetaBancaria()).
                put("precioReserva", reserva.getPrecioReserva());

        return reservaObject;
    }


    public static ClientReservaDto toClientReservaDto(InputStream jsonReserva) throws ParsingException{
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonReserva);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT){
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode reservaObject = (ObjectNode) rootNode;

                JsonNode reservaIdNode = reservaObject.get("idReserva");
                Long idReserva = (reservaIdNode != null) ? reservaIdNode.longValue() : null;

                Long idExcursion = reservaObject.get("idExcursion").longValue();
                String email = reservaObject.get("email").textValue().trim();
                int numeroPersonas = reservaObject.get("numeroPersonas").intValue();
                String numeroTarjetaBancaria = reservaObject.get("numeroTarjetaBancaria").textValue().trim();
                LocalDateTime fechaReserva = LocalDateTime.parse(reservaObject.get("fechaReserva").textValue().trim());
                float precioReserva = reservaObject.get("precioReserva").floatValue();
                String fechaCancelacionNode = reservaObject.get("fechaCancelacion").asText();
                LocalDateTime fechaCancelacion = (!fechaCancelacionNode.equals("null")) ? LocalDateTime.parse(reservaObject.get("fechaCancelacion").textValue().trim()) : null;

                return new ClientReservaDto(idReserva, idExcursion, email, numeroPersonas,
                        numeroTarjetaBancaria, fechaReserva, precioReserva, fechaCancelacion);
            }


        } catch (ParsingException ex){
            throw ex;
        } catch (Exception e){
            throw new ParsingException(e);
        }
    }

    public static List<ClientReservaDto> toClientReservaDtos(InputStream jsonExcursion) throws ParsingException{

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY){
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode reservaArray = (ArrayNode) rootNode;
                List<ClientReservaDto> reservasDto = new ArrayList<>(reservaArray.size());
                for (JsonNode reservaNode : reservaArray){
                    reservasDto.add(toClientReservaDto(reservaNode));
                }

                return reservasDto;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e){
            throw new ParsingException(e);
        }
    }


    private static ClientReservaDto toClientReservaDto(JsonNode reservaNode) throws ParsingException{

        if (reservaNode.getNodeType() != JsonNodeType.OBJECT){
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode reservaObject = (ObjectNode) reservaNode;

            JsonNode reservaIdNode = reservaObject.get("idReserva");
            Long idReserva = (reservaIdNode != null) ? reservaIdNode.longValue() : null;


            Long idExcursion = reservaObject.get("idExcursion").longValue();
            String email = reservaObject.get("email").textValue().trim();
            int numeroPersonas = reservaObject.get("numeroPersonas").intValue();
            String numeroTarjetaBancaria = reservaObject.get("numeroTarjetaBancaria").textValue().trim();
            LocalDateTime fechaReserva = LocalDateTime.parse(reservaObject.get("fechaReserva").textValue().trim());
            float precioReserva = reservaObject.get("precioReserva").floatValue();
            String fechaCancelacionNode = reservaObject.get("fechaCancelacion").asText();
            LocalDateTime fechaCancelacion = (!fechaCancelacionNode.equals("null")) ? LocalDateTime.parse(reservaObject.get("fechaCancelacion").textValue().trim()) : null;


            return new ClientReservaDto(idReserva, idExcursion, email, numeroPersonas, numeroTarjetaBancaria, fechaReserva,
                    precioReserva, fechaCancelacion);
        }

    }

}
