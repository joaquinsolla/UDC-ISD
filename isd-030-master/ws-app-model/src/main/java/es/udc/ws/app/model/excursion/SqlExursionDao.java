package es.udc.ws.app.model.excursion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlExursionDao {

    public Excursion crear(Connection connection, Excursion excursion);

    public void actualizar(Connection connection, Excursion excursion)
        throws InstanceNotFoundException;

    public Excursion buscar(Connection connection, Long idExcursion)
        throws InstanceNotFoundException;

    public List<Excursion> buscarPorCiudad(Connection connection, String ciudad, LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    public void eliminar(Connection connection, Long idExcursion)
        throws InstanceNotFoundException;
}
