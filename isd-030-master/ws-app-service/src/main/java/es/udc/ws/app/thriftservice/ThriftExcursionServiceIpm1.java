package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursionService.ExcursionServiceFactory;
import es.udc.ws.app.model.excursionService.excepciones.FechaCelebracionException;
import es.udc.ws.app.model.excursionService.excepciones.FechaFueraDeRangoException;
import es.udc.ws.app.model.excursionService.excepciones.NumeroMaximoPlazasException;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;

public class ThriftExcursionServiceIpm1 implements ThriftExcursionService.Iface {


    @Override
    public ThriftExcursionDto anadirExcursion(ThriftExcursionDto excursionDto) throws ThriftInputValidationException, TException {

        Excursion excursion = ExcursionToThriftExcursionDtoConversor.toExcursion(excursionDto);

        try {
            Excursion addedExcursion = ExcursionServiceFactory.getService().anadirExcursion(excursion);
            return ExcursionToThriftExcursionDtoConversor.toThriftExcursionDto(addedExcursion);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public void actualizarExcursion(ThriftExcursionDto excursionDto) throws ThriftInputValidationException, ThriftInstanceNotFoundException, ThriftFechaFueraDeRangoException, ThriftFechaCelebracionException, ThriftNumeroMaximoPlazasException, TException {

        Excursion excursion = ExcursionToThriftExcursionDtoConversor.toExcursion(excursionDto);


        try {
            ExcursionServiceFactory.getService().actualizarExcursion(excursion);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (FechaFueraDeRangoException e) {
            throw new ThriftFechaFueraDeRangoException(e.getIdExcursion());
        } catch (FechaCelebracionException e) {
            throw new ThriftFechaCelebracionException(e.getIdExcursion());
        } catch (NumeroMaximoPlazasException e) {
            throw new ThriftNumeroMaximoPlazasException(e.getIdExcursion());
        }
    }
}
