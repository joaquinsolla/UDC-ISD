package es.udc.ws.app.model.reserva;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlReservaDao implements SqlReservaDao {

    protected AbstractSqlReservaDao(){}

    @Override
    public Reserva buscar(Connection connection, Long idReserva)
            throws InstanceNotFoundException{

        //Create queryString
        String queryString = "SELECT idExcursion, email, numeroPersonas, numeroTarjetaBancaria, " +
                "fechaReserva, precioReserva, fechaCancelacion FROM " +
                "Reserva WHERE idReserva = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setLong(i++, idReserva.longValue());

            //Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()){
                throw new InstanceNotFoundException(idReserva,
                        Reserva.class.getName());
            }

            //Get results
            i = 1;
            Long idExcursion = resultSet.getLong(i++);
            String email = resultSet.getString(i++);
            int numeroPersonas = resultSet.getInt(i++);
            String numeroTarjetaBancaria = resultSet.getString(i++);
            Timestamp fechaReservaTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime fechaReserva = (fechaReservaTimestamp != null) ? fechaReservaTimestamp.toLocalDateTime() : null;
            float precioReserva = resultSet.getInt(i++);
            Timestamp fechaCancelacionTimeStamp = resultSet.getTimestamp(i++);
            LocalDateTime fechaCancelacion = (fechaCancelacionTimeStamp != null) ? fechaCancelacionTimeStamp.toLocalDateTime() : null;

            //Return Reserva
            return new Reserva(idReserva, idExcursion, email, numeroPersonas, numeroTarjetaBancaria,
                    fechaReserva, precioReserva, fechaCancelacion);

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarReserva(Connection connection, Reserva reserva)
            throws InstanceNotFoundException{

        //Create queryString
        String queryString = "UPDATE Reserva " +
                "SET idExcursion = ?, email = ?, numeroPersonas = ?, numeroTarjetaBancaria = ?, " +
                "fechaReserva = ?, precioReserva = ?, fechaCancelacion = ? WHERE idReserva = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            //Fill "queryString"
            int i = 1;
            preparedStatement.setLong(i++, reserva.getIdExcursion());
            preparedStatement.setString(i++, reserva.getEmail());
            preparedStatement.setInt(i++, reserva.getNumeroPersonas());
            preparedStatement.setString(i++, reserva.getNumeroTarjetaBancaria());
            Timestamp fechaReservaTimeStamp = (reserva.getFechaReserva() != null) ? Timestamp.valueOf(reserva.getFechaReserva()):null;
            preparedStatement.setTimestamp(i++, fechaReservaTimeStamp);
            preparedStatement.setFloat(i++, reserva.getPrecioReserva());
            Timestamp fechaCancelacionTimeStamp = (reserva.getFechaCancelacion() != null) ? Timestamp.valueOf(reserva.getFechaCancelacion()):null;
            preparedStatement.setTimestamp(i++,fechaCancelacionTimeStamp);
            preparedStatement.setLong(i++, reserva.getIdReserva());

            //Execute query
            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows == 0){
                throw new InstanceNotFoundException(reserva.getIdReserva(),
                        Reserva.class.getName());
            }


        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reserva> buscarReservasPorUsuarios(Connection connection, String email) {

        //Create queryString
        String queryString = "SELECT idReserva, idExcursion, email, numeroPersonas, numeroTarjetaBancaria, fechaReserva, " +
                "precioReserva, fechaCancelacion  FROM Reserva WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            //Fill "preparedStatement"
            int aux = 1;
            preparedStatement.setString(aux++, email);

            //Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            //Read Reservas
            List<Reserva> reservas = new ArrayList<>();
            while (resultSet.next()) {
                int i = 1;

                Long idReserva = resultSet.getLong(i++);
                Long idExcursion = resultSet.getLong(i++);
                String e = resultSet.getString(i++);
                int numeroPersonas = resultSet.getInt(i++);
                String numeroTarjetaBancaria = resultSet.getString(i++);
                LocalDateTime fechaReserva = resultSet.getTimestamp(i++).toLocalDateTime();
                float precioReserva = resultSet.getFloat(i++);
                Timestamp auxFechaCancelacion = resultSet.getTimestamp(i++);
                LocalDateTime fechaCancelacion =  auxFechaCancelacion != null
                        ? auxFechaCancelacion.toLocalDateTime()
                        : null;

                reservas.add(new Reserva(idReserva, idExcursion, e, numeroPersonas,
                        numeroTarjetaBancaria, fechaReserva,
                        precioReserva, fechaCancelacion));
            }

            //Return Reservas
            return reservas;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Connection connection, Long idReserva)
            throws InstanceNotFoundException{

        //Create "queryString"
        String queryString = "DELETE FROM Reserva WHERE idReserva = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            //Fill "preparedStatement"
            int i = 1;
            preparedStatement.setLong(i++, idReserva);

            //Execute query
            int removedRows = preparedStatement.executeUpdate();

            if(removedRows == 0){
                throw new InstanceNotFoundException(idReserva,
                        Reserva.class.getName());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
