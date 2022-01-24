package es.udc.ws.app.model.reserva;

import java.sql.*;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao{

    @Override
    public Reserva crear(Connection connection, Reserva reserva) {

        //Create "queryString"
        String queryString = "INSERT INTO Reserva"
                + "(idExcursion, email, numeroPersonas, numeroTarjetaBancaria, fechaReserva, precioReserva)"
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {

            int aux = 1;

            //Fill "preparedStatement"
            preparedStatement.setLong(aux++, reserva.getIdExcursion());
            preparedStatement.setString(aux++, reserva.getEmail());
            preparedStatement.setInt(aux++, reserva.getNumeroPersonas());
            preparedStatement.setString(aux++, reserva.getNumeroTarjetaBancaria());
            preparedStatement.setTimestamp(aux++, Timestamp.valueOf(reserva.getFechaReserva()));
            preparedStatement.setDouble(aux++, reserva.getPrecioReserva());

            //Execute query
            preparedStatement.executeUpdate();

            //Get idReserva generated
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }

            Long idReserva = resultSet.getLong(1);

            // Return Reserva
            return new Reserva(idReserva, reserva.getIdExcursion(), reserva.getEmail(), reserva.getNumeroPersonas(),
                    reserva.getNumeroTarjetaBancaria(), reserva.getFechaReserva(),
                    reserva.getPrecioReserva(), null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
