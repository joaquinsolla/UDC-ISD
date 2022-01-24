package es.udc.ws.app.model.excursion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSqlExcursionDao implements SqlExursionDao {

    protected AbstractSqlExcursionDao(){}

    @Override
    public void actualizar(Connection connection, Excursion excursion)
            throws InstanceNotFoundException {

        //Create "queryString"
        String queryString = "UPDATE Excursion"
                + " SET ciudad = ?, descripcion = ?, fechaExcursion = ?, "
                + "precioPorPersona = ?, numeroMaximoPlazas = ?, numeroPlazasDisponibles = ? "
                + "WHERE idExcursion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setString(i++, excursion.getCiudad());
            preparedStatement.setString(i++, excursion.getDescripcion());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(excursion.getFechaExcursion()));
            preparedStatement.setFloat(i++, excursion.getPrecioPorPersona());
            preparedStatement.setInt(i++, excursion.getNumeroMaximoPlazas());
            preparedStatement.setInt(i++, excursion.getNumeroPlazasDisponibles());
            preparedStatement.setLong(i++, excursion.getIdExcursion());

            //Execute query
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(excursion.getIdExcursion(),
                        Excursion.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Excursion buscar(Connection connection, Long idExcursion)
            throws InstanceNotFoundException{

        //Create "queryString"
        String queryString = "SELECT ciudad, "
                + "descripcion, fechaExcursion, precioPorPersona, numeroMaximoPlazas, "
                + "fechaAltaExcursion, numeroPlazasDisponibles  FROM Excursion WHERE idExcursion = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setLong(i++, idExcursion.longValue());

            //Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(idExcursion,
                        Excursion.class.getName());
            }

            //Get results
            i = 1;
            String ciudad = resultSet.getString(i++);
            String descripcion = resultSet.getString(i++);
            Timestamp fechaExcursionTS = resultSet.getTimestamp(i++);
            LocalDateTime fechaExcursion = fechaExcursionTS.toLocalDateTime();
            float precioPorPersona = resultSet.getFloat(i++);
            int numeroMaximoPlazas = resultSet.getInt(i++);
            Timestamp fechaAltaExcursionTS = resultSet.getTimestamp(i++);
            LocalDateTime fechaAltaExcursion = fechaAltaExcursionTS.toLocalDateTime();
            int numeroPlazasDisponibles = resultSet.getInt(i++);

            //Return Excursion
            return new Excursion(idExcursion, ciudad, descripcion, fechaExcursion, precioPorPersona,
                    numeroMaximoPlazas, fechaAltaExcursion, numeroPlazasDisponibles);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Excursion> buscarPorCiudad(Connection connection, String ciudad, LocalDateTime fechaInicial, LocalDateTime fechaFinal){

        //Create "queryString"
        String queryString = "SELECT idExcursion, ciudad, descripcion, "
                + " fechaExcursion, precioPorPersona, numeroMaximoPlazas, "
                + "fechaAltaExcursion, numeroPlazasDisponibles FROM Excursion"
                + " WHERE ciudad = ?";
        if (fechaInicial != null && fechaFinal != null) {
            queryString += " AND fechaExcursion >= ? AND fechaExcursion <= ?";
        }
        queryString += " AND fechaExcursion >= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            //Fill "preparedStatement"
            int i = 1;

            preparedStatement.setString(i++, ciudad);

            if (fechaInicial != null && fechaFinal != null) {
                preparedStatement.setTimestamp(i++, Timestamp.valueOf(fechaInicial));
                preparedStatement.setTimestamp(i++, Timestamp.valueOf(fechaFinal));
            }
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now().plusHours(24)));


            //Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Read Excursions
            List<Excursion> excursions = new ArrayList<Excursion>();

            while (resultSet.next()) {

                i = 1;
                Long idExcursion = Long.valueOf(resultSet.getLong(i++));
                String myciudad = resultSet.getString(i++);
                String descripcion = resultSet.getString(i++);
                Timestamp fechaExcursionTS = resultSet.getTimestamp(i++);
                LocalDateTime fechaExcursion = fechaExcursionTS.toLocalDateTime();
                float precioPorPersona = resultSet.getFloat(i++);
                int numeroMaximoPlazas = resultSet.getInt(i++);
                Timestamp fechaAltaExcursionTS = resultSet.getTimestamp(i++);
                LocalDateTime fechaAltaExcursion = fechaAltaExcursionTS.toLocalDateTime();
                int numeroPlazasDisponibles = resultSet.getInt(i++);

                excursions.add(new Excursion(idExcursion, myciudad, descripcion, fechaExcursion,
                        precioPorPersona, numeroMaximoPlazas, fechaAltaExcursion, numeroPlazasDisponibles));
            }

            //Return Excursions
            return excursions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Connection connection, Long idExcursion)
            throws InstanceNotFoundException{

        //Create "queryString"
        String queryString = "DELETE FROM Excursion WHERE idExcursion = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setLong(i++, idExcursion);

            //Execute query
            int removedRows = preparedStatement.executeUpdate();

            if(removedRows == 0){
                throw new InstanceNotFoundException(idExcursion,
                        Excursion.class.getName());
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
