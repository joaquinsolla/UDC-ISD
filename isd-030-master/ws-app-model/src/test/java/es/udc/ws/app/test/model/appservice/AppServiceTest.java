package es.udc.ws.app.test.model.appservice;

import static es.udc.ws.app.model.util.ModelConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursion.SqlExcursionDaoFactory;
import es.udc.ws.app.model.excursion.SqlExursionDao;
import es.udc.ws.app.model.excursionService.ExcursionService;
import es.udc.ws.app.model.excursionService.ExcursionServiceFactory;
import es.udc.ws.app.model.excursionService.excepciones.*;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class AppServiceTest {

    private static final String EMAIL = "test@udc.es";
    private static final String TARJETA_CREDITO_VALIDA = "1234567890123456";
    private static final String TARJETA_CREDITO_NO_VALIDA = "";

    private final long NON_EXISTENT_EXCURSION_ID = -1;

    private static ExcursionService excursionService = null;
    private static SqlReservaDao reservaDao = null;
    private static SqlExursionDao excursionDao = null;

    @BeforeAll
    public static void init() {

        DataSource dataSource = new SimpleDataSource();

        // Add "dataSource" to "DataSourceLocator"
        DataSourceLocator.addDataSource(APP_DATA_SOURCE, dataSource);

        excursionService = ExcursionServiceFactory.getService();

        reservaDao = SqlReservaDaoFactory.getDao();

        excursionDao = SqlExcursionDaoFactory.getDao();
    }

    private Excursion getValidExcursion(){
        return new Excursion( "ciudad", "descripcion",
                LocalDateTime.of(2022, 9, 19, 13, 20, 5), 18.95F, 150);
    }

    private Excursion getValidExcursion(LocalDateTime fechaExcursion){
        return new Excursion((long) 888, "ciudad1", "descripcion",
                fechaExcursion, 18.95F, 150,
                null, 150);
    }

    private Reserva getValidReserva(Long idExcursion){
        return new Reserva(idExcursion, "a@gmail.com", 2, "1234567898765439", 37);
    }

    private Excursion createExcursion(Excursion excursion){

        Excursion excursionAdded = null;

        try {
            excursionAdded = excursionService.anadirExcursion(excursion);
        } catch (InputValidationException e){
            throw new RuntimeException(e);
        }
        return excursionAdded;
    }

    private Reserva createReserva(Reserva reserva){

        Reserva reservaAdded = null;

        try {
            reservaAdded = excursionService.realizarReserva(reserva);
        } catch (InputValidationException e){
            throw new RuntimeException(e);
        } catch (ReservaFueraPlazoException | LimiteParticipantesAlcanzadosException | InstanceNotFoundException e) {
            e.printStackTrace();
        }
        return reservaAdded;
    }

    private Excursion findExcursion (Long idExcursion){

        Excursion excursionFound = null;
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                excursionFound = excursionDao.buscar(connection, idExcursion);

            } catch (InstanceNotFoundException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return excursionFound;

    }

    private Reserva findReserva (Long idReserva){

        Reserva reservaFound = null;
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                reservaFound = reservaDao.buscar(connection, idReserva);

            } catch (InstanceNotFoundException e){
                throw new RuntimeException(e);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return reservaFound;

    }


    private void updateExcursion(Excursion excursion){

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                excursionDao.actualizar(connection, excursion);

                //Commit
                connection.commit();

            } catch (InstanceNotFoundException e){
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e){
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e){
                connection.rollback();
                throw e;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void updateReserva(Reserva reserva){

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                reservaDao.actualizarReserva(connection, reserva);

                //Commit
                connection.commit();

            } catch (InstanceNotFoundException e){
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e){
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e){
                connection.rollback();
                throw e;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void removeExcursion(Long idExcursion){

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                excursionDao.eliminar(connection, idExcursion);

                //Commit
                connection.commit();
            } catch (InstanceNotFoundException e){
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e){
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e){
                connection.rollback();
                throw e;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void removeReserva(Long idReserva){

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                reservaDao.eliminar(connection, idReserva);

                //Commit
                connection.commit();
            } catch (InstanceNotFoundException e){
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e){
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e){
                connection.rollback();
                throw e;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddExcursionAndFindExcursion() throws InputValidationException, RuntimeException{

        Excursion excursion = getValidExcursion();
        Excursion excursionAdded = null;

        try {

            //Create Excursion
            LocalDateTime beforeCreationService = LocalDateTime.now().withNano(0);

            excursionAdded = createExcursion(excursion);

            LocalDateTime afterCreationService = LocalDateTime.now().withNano(0);

            //Find Excursion
            Excursion excursionFound = findExcursion(excursionAdded.getIdExcursion());

            assertEquals(excursionAdded, excursionFound);
            assertEquals(excursionFound.getCiudad(), excursion.getCiudad());
            assertEquals(excursionFound.getDescripcion(), excursion.getDescripcion());
            assertEquals(excursionFound.getFechaExcursion(), excursion.getFechaExcursion());
            assertEquals(excursionFound.getPrecioPorPersona(), excursion.getPrecioPorPersona());
            assertEquals(excursionFound.getNumeroMaximoPlazas(), excursion.getNumeroMaximoPlazas());

            assertTrue((excursionFound.getFechaAltaExcursion().compareTo(beforeCreationService) >= 0) &&
                    (excursionFound.getFechaAltaExcursion().compareTo(afterCreationService) <= 0));

            assertEquals(excursionFound.getNumeroPlazasDisponibles(), excursion.getNumeroPlazasDisponibles());

        } finally {
            //Clear DataBase
            if (excursionAdded != null) {
                removeExcursion(excursionAdded.getIdExcursion());
            }
        }
    }

    @Test
    public void testAddInvalidExcursion() {

        //Check ciudad not null
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setCiudad(null);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check ciudad not empty
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setCiudad("");
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check descripcion not null
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setDescripcion(null);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check descripcion not empty
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setDescripcion("");
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check precioPorPersona >= 0
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setPrecioPorPersona(-1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check precioPorPersona <= MAX_PRICE
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setPrecioPorPersona(MAX_PRICE + 1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check  numeroMaximoPlazas >= 0
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setNumeroMaximoPlazas(-1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check numeroMaximoPlazas <= MAX_PLAZAS
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setNumeroMaximoPlazas(MAX_PLAZAS + 1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check  numeroPlazasDisponibles >= 0
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setNumeroPlazasDisponibles(-1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check numeroPlazasDisponibles <= numeroMaximoPlazas
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setNumeroPlazasDisponibles(excursion.getNumeroMaximoPlazas() + 1);
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });

        //Check fechaActual + 72 <= fechaExcursion
        assertThrows(InputValidationException.class, () -> {
            Excursion excursion = getValidExcursion();
            excursion.setFechaExcursion(LocalDateTime.now());
            Excursion excursionAdded = excursionService.anadirExcursion(excursion);
            removeExcursion(excursionAdded.getIdExcursion());
        });
    }


    @Test
    public void testFindNonExistentExcursion() {
        assertThrows(RuntimeException.class, () -> findExcursion(NON_EXISTENT_EXCURSION_ID));
    }


    @Test
    public void testActualizarExcursion() throws InstanceNotFoundException {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());

        try {

            //Update Excursion
            Excursion excursionToUpdate = new Excursion(excursion.getIdExcursion(), excursion.getCiudad(), "NUEVA DESCRIPCION", excursion.getFechaExcursion(),
                    excursion.getPrecioPorPersona(), excursion.getNumeroMaximoPlazas(), excursion.getFechaAltaExcursion(), excursion.getNumeroPlazasDisponibles());

            excursionService.actualizarExcursion(excursionToUpdate);

            //Find Excursion
            Excursion updatedExcursion = findExcursion(excursion.getIdExcursion());

            assertEquals(excursionToUpdate, updatedExcursion);
            assertEquals(excursionToUpdate.getCiudad(), updatedExcursion.getCiudad());
            assertEquals(excursionToUpdate.getDescripcion(), updatedExcursion.getDescripcion());
            assertEquals(excursionToUpdate.getFechaExcursion(), updatedExcursion.getFechaExcursion());
            assertEquals(excursionToUpdate.getPrecioPorPersona(), updatedExcursion.getPrecioPorPersona());
            assertEquals(excursionToUpdate.getNumeroMaximoPlazas(), updatedExcursion.getNumeroMaximoPlazas());

        } catch (FechaCelebracionException | InputValidationException | NumeroMaximoPlazasException | FechaFueraDeRangoException e) {
            e.printStackTrace();
        } finally {
            // Clear Database
            removeExcursion(excursion.getIdExcursion());
        }

    }


    @Test
    public void testActualizarInvalidExcursion() {

        //Create Excursion
        Long idExcursion = createExcursion(getValidExcursion()).getIdExcursion();

        try {

            // Check ciudad not null
            Excursion excursion = findExcursion(idExcursion);
            excursion.setCiudad(null);

            assertThrows(InputValidationException.class, () -> excursionService.actualizarExcursion(excursion));
        } finally {
            // Clear Database
            removeExcursion(idExcursion);
        }

    }


    @Test
    public void testActualizarNonExistentExcursion() {

        Excursion excursion = getValidExcursion();
        excursion.setIdExcursion(NON_EXISTENT_EXCURSION_ID);
        excursion.setFechaAltaExcursion(LocalDateTime.now());

        assertThrows(InstanceNotFoundException.class, () -> excursionService.actualizarExcursion(excursion));

    }


    @Test
    public void testFindExcursionPorCiudad() {

        //Create list of Excursions

        List<Excursion> excursions = new LinkedList<Excursion>();
        Excursion excursion1 = createExcursion(getValidExcursion(LocalDateTime.of(2022, 8, 1, 13, 20, 5)));
        excursions.add(excursion1);
        Excursion excursion2 = createExcursion(getValidExcursion(LocalDateTime.of(2022, 10, 2, 13, 20, 5)));
        excursions.add(excursion2);
        Excursion excursion3 = createExcursion(getValidExcursion(LocalDateTime.of(2022, 10, 3, 13, 20, 5)));
        excursions.add(excursion3);

        try {

            //Find Excursions
            List<Excursion> foundExcursions = excursionService.buscarExcursionesPorCiudad("ciudad1", LocalDateTime.of(2022, 7, 1, 0, 0, 0), LocalDateTime.of(2022, 12, 1, 0, 0, 0));
            assertEquals(excursions, foundExcursions);

            foundExcursions = excursionService.buscarExcursionesPorCiudad("ciudad1", LocalDateTime.of(2022, 7, 1, 0, 0, 0), LocalDateTime.of(2022, 9, 1, 0, 0, 0));
            assertEquals(1, foundExcursions.size());
            assertEquals(excursions.get(0), foundExcursions.get(0));

            foundExcursions = excursionService.buscarExcursionesPorCiudad("ciudad2", LocalDateTime.of(2022, 7, 1, 0, 0, 0), LocalDateTime.of(2022, 12, 1, 0, 0, 0));
            assertEquals(0, foundExcursions.size());

        } catch (InputValidationException e) {
            e.printStackTrace();
        } finally {

            // Clear Database
            for (Excursion excursion : excursions) {
                removeExcursion(excursion.getIdExcursion());
            }
        }
    }


    @Test
    public void testRealizarReservaYBuscarReservasUsuario() throws InstanceNotFoundException,InputValidationException, LimiteParticipantesAlcanzadosException, ReservaFueraPlazoException {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());

        Reserva reserva = new Reserva(excursion.getIdExcursion(), EMAIL, 1,TARJETA_CREDITO_VALIDA, excursion.getPrecioPorPersona());

        try {
            //Create Reserva
            LocalDateTime fechaAntesReserva = LocalDateTime.now().withNano(0);

            reserva = excursionService.realizarReserva(reserva);

            LocalDateTime fechaDespuesReserva = LocalDateTime.now().withNano(0);

            //Find Reserva
            List<Reserva> listaReservasBuscada = excursionService.buscarReservasUsuario(EMAIL);
            assertEquals(1, listaReservasBuscada.size());

            Reserva reservaBuscada = listaReservasBuscada.get(0);

            assertEquals(reserva, reservaBuscada);
            assertEquals(excursion.getIdExcursion(), reservaBuscada.getIdExcursion());
            assertEquals(reserva.getEmail(), reservaBuscada.getEmail());
            assertEquals(1, reservaBuscada.getNumeroPersonas());
            assertEquals(TARJETA_CREDITO_VALIDA, reservaBuscada.getNumeroTarjetaBancaria());
            assertEquals(excursion.getPrecioPorPersona(), reservaBuscada.getPrecioReserva());
            assertNull(reservaBuscada.getFechaCancelacion());
            assertTrue(fechaAntesReserva.compareTo(reservaBuscada.getFechaReserva()) <= 0 && fechaDespuesReserva.compareTo(reservaBuscada.getFechaReserva()) >= 0);

            //Search Reservas with an email that not exits
            listaReservasBuscada = excursionService.buscarReservasUsuario("noesmail@udc.es");
            assertEquals(0, listaReservasBuscada.size());

        } finally {

            // Clear Database
            if (reserva != null) {
                removeReserva(reserva.getIdReserva());
            }
            removeExcursion(excursion.getIdExcursion());
        }
    }

    @Test
    public void testRealizarReservaInvalida() {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());
        Reserva reserva = new Reserva(excursion.getIdExcursion(), EMAIL, 10, TARJETA_CREDITO_NO_VALIDA, excursion.getPrecioPorPersona());

        try {
            assertThrows(InputValidationException.class, () -> {
                Reserva reservaAux = excursionService.realizarReserva(reserva);
                removeReserva(reservaAux.getIdReserva());
            });

        } finally {

            // Clear Database
            removeExcursion(excursion.getIdExcursion());
        }
    }

    @Test
    public void testRealizarReservaTarjetaCreaditoInvalida() {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());
        Reserva reserva = new Reserva(excursion.getIdExcursion(), EMAIL, 1, TARJETA_CREDITO_NO_VALIDA, excursion.getPrecioPorPersona());

        try {
            assertThrows(InputValidationException.class, () -> {
                Reserva reservaAux = excursionService.realizarReserva(reserva);
                removeReserva(reservaAux.getIdReserva());
            });

        } finally {
            // Clear Database
            removeExcursion(excursion.getIdExcursion());
        }
    }

    @Test
    public void testRealizarReservaExcursionNoValida() {

        Reserva reserva = new Reserva(NON_EXISTENT_EXCURSION_ID, EMAIL, 1, TARJETA_CREDITO_VALIDA, 1000);

        assertThrows(InstanceNotFoundException.class, () -> {
            Reserva reservaAux = excursionService.realizarReserva(reserva);
            removeReserva(reservaAux.getIdReserva());
        });
    }

    @Test
    public void testRealizarReservaFueraPlazo() {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());

        Excursion excursionActual = new Excursion(excursion.getIdExcursion(), excursion.getCiudad(),
                excursion.getDescripcion(), LocalDateTime.now(), excursion.getPrecioPorPersona(),
                excursion.getNumeroMaximoPlazas(), excursion.getFechaAltaExcursion(),
                excursion.getNumeroPlazasDisponibles());

        updateExcursion(excursionActual);

        Reserva reserva = new Reserva(excursion.getIdExcursion(), EMAIL, 1, TARJETA_CREDITO_VALIDA, excursion.getPrecioPorPersona());

        try {
            assertThrows(ReservaFueraPlazoException.class, () -> {
                Reserva reservaAux = excursionService.realizarReserva(reserva);
                removeReserva(reservaAux.getIdReserva());
            });

        } finally {
            //Clear DataBase
            removeExcursion(excursion.getIdExcursion());
        }
    }

    @Test
    public void testRealizarReservaLimitePArticipantes() {

        //Create Excursion
        Excursion excursion = createExcursion(new Excursion( "ciudad", "descripcion", LocalDateTime.of(2022, 9, 19, 13, 20, 5), 18.95F, 2));

        Reserva reserva = new Reserva(excursion.getIdExcursion(), EMAIL, 4, TARJETA_CREDITO_VALIDA, excursion.getPrecioPorPersona());

        try {
            assertThrows(LimiteParticipantesAlcanzadosException.class, () -> {
                Reserva reservaAux = excursionService.realizarReserva(reserva);
                removeReserva(reservaAux.getIdReserva());
            });

        } finally {
            //Clear DataBase
            removeExcursion(excursion.getIdExcursion());
        }
    }


    @Test
    public void testCancelarReserva(){

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());

        //Create Reserva
        Reserva reserva = createReserva(getValidReserva(excursion.getIdExcursion()));

        try{

            assertNull(reserva.getFechaCancelacion());

            //Set cancellation hour
            LocalDateTime beforeCancelReserva = LocalDateTime.now().withNano(0);

            reserva.setFechaCancelacion(LocalDateTime.now());

            LocalDateTime afterCancelReserva = LocalDateTime.now().withNano(0);

            assertTrue((reserva.getFechaCancelacion().compareTo(beforeCancelReserva) >= 0) &&
                    (reserva.getFechaCancelacion().compareTo(afterCancelReserva)) <= 0);


        } finally {

            //Clear DataBase
            if (reserva != null){
                removeReserva(reserva.getIdReserva());
            }
            removeExcursion(excursion.getIdExcursion());
        }
    }
    @Test
    public void testCancellInvalidReserva() {

        //Create Excursion
        Excursion excursion = createExcursion(getValidExcursion());

        //Create Reserva
        Reserva reserva = createReserva(getValidReserva(excursion.getIdExcursion()));


        try{

            //Check reserva already cancelled
            Reserva reservaCancelled = new Reserva(reserva.getIdReserva(), reserva.getIdExcursion(), reserva.getEmail(),
                    reserva.getNumeroPersonas(), reserva.getNumeroTarjetaBancaria(), reserva.getFechaReserva(),
                    reserva.getPrecioReserva(), LocalDateTime.now());
            updateReserva(reservaCancelled);

            Reserva reservaYaCancelada = findReserva(reserva.getIdReserva());
            assertThrows(YaCanceladoException.class, () ->
                    excursionService.cancelarReserva(reservaYaCancelada.getIdReserva(), reservaYaCancelada.getEmail()));


            //Check reserva's email is different
            Reserva reservaSinCancelacion = new Reserva(reserva.getIdReserva(), reserva.getIdExcursion(), reserva.getEmail(),
                    reserva.getNumeroPersonas(), reserva.getNumeroTarjetaBancaria(), reserva.getFechaReserva(),
                    reserva.getPrecioReserva(), null);
            updateReserva(reservaSinCancelacion);

            Reserva reservaEmail = findReserva(reserva.getIdReserva());
            assertThrows(DistintoEmailException.class, () ->
                    excursionService.cancelarReserva(reservaEmail.getIdReserva(), "alvaro@yahoo"));


            //Check fechaActual + 48 <= fechaExcursion
            Excursion excursionActual = new Excursion(excursion.getIdExcursion(), excursion.getCiudad(),
                    excursion.getDescripcion(), LocalDateTime.now().plusDays(1), excursion.getPrecioPorPersona(),
                    excursion.getNumeroMaximoPlazas(), excursion.getFechaAltaExcursion(),
                    excursion.getNumeroPlazasDisponibles());

            updateExcursion(excursionActual);

            assertThrows(CancelarReservaFueraDePlazoException.class, () ->
                    excursionService.cancelarReserva(reservaEmail.getIdReserva(), reservaEmail.getEmail()));
        }finally {

            //Clear DataBase
            removeReserva(reserva.getIdReserva());
            removeExcursion(excursion.getIdExcursion());
        }
    }
}
