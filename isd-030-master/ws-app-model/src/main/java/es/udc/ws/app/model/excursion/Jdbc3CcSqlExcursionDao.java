package es.udc.ws.app.model.excursion;

import java.sql.*;

public class Jdbc3CcSqlExcursionDao extends AbstractSqlExcursionDao{

    @Override
    public Excursion crear(Connection connection, Excursion excursion){

        //Create "queryString"
        String queryString = "INSERT INTO Excursion" +
                "(ciudad, descripcion, fechaExcursion, precioPorPersona, numeroMaximoPlazas, fechaAltaExcursion, numeroPlazasDisponibles)" +
                "VALUES (?, ?, ?, ? ,?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)){

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setString(i++, excursion.getCiudad());
            preparedStatement.setString(i++, excursion.getDescripcion());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(excursion.getFechaExcursion()));
            preparedStatement.setFloat(i++, excursion.getPrecioPorPersona());
            preparedStatement.setInt(i++, excursion.getNumeroMaximoPlazas());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(excursion.getFechaAltaExcursion()));
            preparedStatement.setInt(i++, excursion.getNumeroMaximoPlazas());

            //Execute query
            preparedStatement.executeUpdate();

            //Get idExcursion generated
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next()){
                throw new SQLException("JDBC driver did not return generated key.");
            }

            Long idExcursion = resultSet.getLong(1);

            //Return Excursion
            return new Excursion(idExcursion, excursion.getCiudad(), excursion.getDescripcion(), excursion.getFechaExcursion(),
                    excursion.getPrecioPorPersona(), excursion.getNumeroMaximoPlazas(), excursion.getFechaAltaExcursion(), excursion.getNumeroMaximoPlazas());
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
