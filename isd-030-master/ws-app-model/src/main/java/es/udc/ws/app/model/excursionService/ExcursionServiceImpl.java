package es.udc.ws.app.model.excursionService;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursion.SqlExursionDao;
import es.udc.ws.app.model.excursion.SqlExcursionDaoFactory;
import es.udc.ws.app.model.excursionService.excepciones.*;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.ws.app.model.util.ModelConstants.*;


public class ExcursionServiceImpl implements ExcursionService {

    private final DataSource dataSource;
    private SqlExursionDao excursionDao = null;
    private SqlReservaDao reservaDao = null;


    public ExcursionServiceImpl(){
        dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        excursionDao = SqlExcursionDaoFactory.getDao();
        reservaDao = SqlReservaDaoFactory.getDao();
    }

    private void validarExcursion(Excursion excursion) throws InputValidationException{
        PropertyValidator.validateMandatoryString("ciudad", excursion.getCiudad());
        PropertyValidator.validateMandatoryString("descripcion", excursion.getDescripcion());
        PropertyValidator.validateDouble("precioPorPersona", excursion.getPrecioPorPersona(), 0, MAX_PRICE);
        PropertyValidator.validateLong("numeroMaximoPlazas", excursion.getNumeroMaximoPlazas(), 1, MAX_PLAZAS);
        PropertyValidator.validateLong("numeroPlazasDisponibles", excursion.getNumeroPlazasDisponibles(), 0, excursion.getNumeroMaximoPlazas());
    }

    private void validarReserva(Reserva reserva) throws InputValidationException{
        PropertyValidator.validateMandatoryString("email", reserva.getEmail());
        PropertyValidator.validateLong("numeroPersonas", reserva.getNumeroPersonas(), 1, 5);
        PropertyValidator.validateCreditCard(reserva.getNumeroTarjetaBancaria());
    }


    @Override
    public Excursion anadirExcursion(Excursion excursion) throws InputValidationException{

        validarExcursion(excursion);
        excursion.setFechaAltaExcursion(LocalDateTime.now());
        excursion.setNumeroPlazasDisponibles(excursion.getNumeroMaximoPlazas());

        try(Connection connection = dataSource.getConnection()){

            try {

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                if (excursion.getFechaExcursion().isBefore(excursion.getFechaAltaExcursion().plusHours(72))) throw new InputValidationException("Nueva Fecha > Antigua Fecha-72h");

                Excursion createExcursion = excursionDao.crear(connection, excursion);

                //Commit
                connection.commit();

                return createExcursion;
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

    @Override
    public void actualizarExcursion(Excursion excursion) throws InputValidationException, InstanceNotFoundException,
            FechaFueraDeRangoException, FechaCelebracionException, NumeroMaximoPlazasException{

        validarExcursion(excursion);

        try (Connection connection= dataSource.getConnection()) {

            try {

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                Excursion excursionOld = excursionDao.buscar(connection, excursion.getIdExcursion());
                if (excursionOld.getFechaExcursion().minusHours(72).isBefore(LocalDateTime.now())) throw new FechaFueraDeRangoException(excursion.getIdExcursion());
                if (excursionOld.getFechaExcursion().isAfter(excursion.getFechaExcursion())) throw new FechaCelebracionException(excursion.getIdExcursion());
                if (excursionOld.getNumeroMaximoPlazas()-excursionOld.getNumeroPlazasDisponibles()>excursion.getNumeroMaximoPlazas())
                    throw new NumeroMaximoPlazasException(excursion.getIdExcursion());

                excursion.setNumeroPlazasDisponibles(excursion.getNumeroMaximoPlazas()-(excursionOld.getNumeroMaximoPlazas()-excursionOld.getNumeroPlazasDisponibles()));
                excursionDao.actualizar(connection, excursion);

                //Commit
                connection.commit();

            } catch (FechaFueraDeRangoException | FechaCelebracionException | NumeroMaximoPlazasException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Excursion> buscarExcursionesPorCiudad(String ciudad, LocalDateTime fechaInicial, LocalDateTime fechaFinal)
            throws InputValidationException {


        try (Connection connection= dataSource.getConnection()) {

            try {

                //Do work
                if (ciudad == null) throw new InputValidationException("Ciudad is NULL");
                if (fechaFinal.isBefore(fechaInicial)) throw new InputValidationException("Fecha Inicial > Fecha Final");

                return excursionDao.buscarPorCiudad(connection, ciudad, fechaInicial, fechaFinal);

            } catch (InputValidationException e){
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reserva realizarReserva(Reserva reserva) throws InputValidationException, InstanceNotFoundException,
            ReservaFueraPlazoException, LimiteParticipantesAlcanzadosException {
        validarReserva(reserva);
        reserva.setFechaReserva(LocalDateTime.now());

        try (Connection connection = dataSource.getConnection()) {
            try {

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                //Do work
                Excursion excursion = excursionDao.buscar(connection, reserva.getIdExcursion());

                if (excursion.getFechaExcursion().isBefore(reserva.getFechaReserva().plusHours(24))) {
                    throw new ReservaFueraPlazoException(excursion.getIdExcursion());
                }

                if (excursion.getNumeroPlazasDisponibles() - reserva.getNumeroPersonas() < 0) {
                    throw new LimiteParticipantesAlcanzadosException(excursion.getIdExcursion());
                }

                reserva.setPrecioReserva(excursion.getPrecioPorPersona() * reserva.getNumeroPersonas());

                Reserva RealizarReserva = reservaDao.crear(connection, reserva);
                excursion.setNumeroPlazasDisponibles(excursion.getNumeroPlazasDisponibles()-reserva.getNumeroPersonas());

                excursionDao.actualizar(connection, excursion);

                // Commit
                connection.commit();

                return RealizarReserva;
            } catch (InstanceNotFoundException | ReservaFueraPlazoException | LimiteParticipantesAlcanzadosException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelarReserva(Long idReserva, String email) throws InstanceNotFoundException,
            CancelarReservaFueraDePlazoException, YaCanceladoException, DistintoEmailException{

        try (Connection connection = dataSource.getConnection()){

            try{

                //Prepare connection
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);


                //Do work
                Reserva reserva = reservaDao.buscar(connection, idReserva);

                if (reserva.getFechaCancelacion() != null) throw new YaCanceladoException(idReserva);
                if (!reserva.getEmail().equals(email)) throw new DistintoEmailException(idReserva, email);

                Excursion excursion = excursionDao.buscar(connection, reserva.getIdExcursion());

                if (excursion.getFechaExcursion().isBefore(LocalDateTime.now().plusHours(48))) throw new CancelarReservaFueraDePlazoException(excursion.getIdExcursion(), idReserva);

                LocalDateTime fechaCancelacion = LocalDateTime.now();
                reserva.setFechaCancelacion(fechaCancelacion);

                reservaDao.actualizarReserva(connection, reserva);
                excursion.setNumeroPlazasDisponibles(excursion.getNumeroPlazasDisponibles()+reserva.getNumeroPersonas());
                excursionDao.actualizar(connection, excursion);

                //Commit
                connection.commit();

            } catch (InstanceNotFoundException | YaCanceladoException | DistintoEmailException | CancelarReservaFueraDePlazoException e){
                connection.commit();
                throw e;
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

    @Override
    public List<Reserva> buscarReservasUsuario(String email) {

        try (Connection connection = dataSource.getConnection()) {
            return reservaDao.buscarReservasPorUsuarios(connection, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
