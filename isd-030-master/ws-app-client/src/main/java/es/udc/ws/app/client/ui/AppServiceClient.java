package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientExcursionService;
import es.udc.ws.app.client.service.ClientExcursionServiceFactory;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class AppServiceClient {
    public static void main(String[] args) {

        if(args.length == 0){
            printUsageAndExit();
        }
        ClientExcursionService clientExcursionService = ClientExcursionServiceFactory.getService();

        if("-addExc".equalsIgnoreCase(args[0])) {
            validateArgs(args, 6, new int[] {5});

            // [add] ExcursionServiceClient -addExc <city> <description> <date> <price> <maxPlaces>

            try {
                Long idExcursion = clientExcursionService.anadirExcursion(new ClientExcursionDto(null,
                        args[1], args[2], LocalDateTime.parse(args[3]),
                        Float.valueOf(args[4]), Integer.valueOf(args[5]), 0));

                System.out.println("Excursion " + idExcursion + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if ("-updateExc".equalsIgnoreCase(args[0])) {
            validateArgs(args, 7, new int[]{1, 5, 6});

            // [update] ExcursionServiceClient -updateExc <excId> <city> <description> <date> <price> <maxPlaces>

            try {
                clientExcursionService.actualizarExcursion(new ClientExcursionDto(
                        Long.valueOf(args[1]),
                        args[2], args[3], LocalDateTime.parse(args[4]),
                        Float.valueOf(args[5]), Integer.valueOf(args[6])));

                System.out.println("Excursion " + args[1] + " updated sucessfully");

            } catch (NumberFormatException | InputValidationException | InstanceNotFoundException
                    | ClientFechaCelebracionException | ClientNumeroMaximoPlazasException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if ("-findExcursions".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[]{});

            // [find] ExcursionServiceClient -findExcursion <city> <fromDate> <toDate>

            try {
                List<ClientExcursionDto> excursions = clientExcursionService.buscarExcursionesPorCiudad(args[1], args[2], args[3]);
                System.out.println("Found " + excursions.size() +
                        " excursion(s) with city '" + args[1] + "' between dates '"
                        +   args[2] + "' and '" + args[3] + "'");
                for (int i = 0; i < excursions.size(); i++) {
                    ClientExcursionDto excursionDto = excursions.get(i);
                    System.out.println("Id: " + excursionDto.getIdExcursion() +
                            ", Ciudad: " + excursionDto.getCiudad() +
                            ", Descripcion: " + excursionDto.getDescripcion() +
                            ", Fecha: " + excursionDto.getFechaExcursion() +
                            ", Precio: " + excursionDto.getPrecioPorPersona()+ " eur./pers." +
                            ", Maximo Plazas: " + excursionDto.getNumeroMaximoPlazas() +
                            ", Plazas Reservadas: " + excursionDto.getNumeroPlazasReservadas());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-reserve".equalsIgnoreCase(args[0])) {
            validateArgs(args, 5, new int[] {4});

            // [reserve] ExcursionServiceClient -reserve <userEmail> <excursionId> <creditCardNumber> <places>

            try {
                Long idReserva = clientExcursionService.realizarReserva(new ClientReservaDto(Long.valueOf(args[2]), args[1], Integer.parseInt(args[4]), args[3]));
                System.out.println("Reserva " + idReserva + " created sucessfully");

            } catch (NumberFormatException | InputValidationException | ClientReservaFueraPlazoException | ClientLimiteParticipantesAlcanzadosException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-cancel".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[]{});

            // [cancel] ExcursionServiceClient -cancel <reservationId> <userEmail>

            try {
                clientExcursionService.cancelarReserva(Long.valueOf(args[1]), args[2]);

                System.out.println("Reservation cancelled successfully");

            } catch (NumberFormatException | InputValidationException |InstanceNotFoundException
                    | ClientCancelarReservaFueraDePlazoException | ClientYaCanceladoException
                    | ClientDistintoEmailException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }


        } else if("-findReservations".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [findReservations] ExcursionServiceClient -findReservations <userEmail>

            try {
                List<ClientReservaDto> reservas = clientExcursionService.buscarReservasUsuario(args[1]);
                System.out.println("Found " + reservas.size() +
                        " reservas(s) with email '" + args[1] + "'");
                for (int i = 0; i < reservas.size(); i++) {
                    ClientReservaDto reservaDto = reservas.get(i);
                    System.out.println("Id: " + reservaDto.getIdReserva() +
                            ", Id excursion: " + reservaDto.getIdExcursion() +
                            ", Email: " + reservaDto.getEmail() +
                            ", Numero personas: " + reservaDto.getNumeroPersonas() +
                            ", Numero trajeta bancaria: " + reservaDto.getNumeroTarjetaBancaria() +
                            ", Fecha de la reserva: " + reservaDto.getNumeroTarjetaBancaria() +
                            ", Precio total: " + reservaDto.getPrecioReserva() +
                            ", Fecha de cancelacion: " + reservaDto.getFechaCancelacion());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        }
    }


    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [addExcursion]    ExcursionServiceClient -addExc <city> <description> <date> <price> <maxPlaces>\n" +
                "    [updateExcursion] ExcursionServiceClient -updateExc <excId> <city> <description> <date> <price> <maxPlaces>\n" +
                "    [findExcursion]   ExcursionServiceClient -findExcursion <city> <fromDate> <toDate>\n" +
                "    [reserve]         ExcursionServiceClient -reserve <userEmail> <excursionId> <creditCardNumber> <places> \n" +
                "    [cancelReserva]   ExcursionServiceClient -cancel <reservationId> <userEmail> \n" +
                "    [findReservations] ExcursionServiceClient - findReservations <userEmail>\n");
    }
}