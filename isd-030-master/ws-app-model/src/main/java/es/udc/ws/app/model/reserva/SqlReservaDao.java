package es.udc.ws.app.model.reserva;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.util.List;

public interface SqlReservaDao {

    public Reserva crear(Connection connection, Reserva reserva);

    public Reserva buscar(Connection connection, Long idReserva)
        throws InstanceNotFoundException;

    public void actualizarReserva(Connection connection, Reserva reserva)
        throws InstanceNotFoundException;

    public List<Reserva> buscarReservasPorUsuarios(Connection connection, String email);

    public void eliminar(Connection connection, Long idReserva)
        throws InstanceNotFoundException;

}
