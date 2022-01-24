package es.udc.ws.app.model.excursionService;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
public class ExcursionServiceFactory {

    private final static String CLASS_NAME_PARAMETER = "ExcursionServiceFactory.className";
    private static ExcursionService service = null;

    private ExcursionServiceFactory(){}

    @SuppressWarnings("rawtypes")
    private static ExcursionService getInstance(){
        try {
            String serviceClassName = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (ExcursionService) serviceClass.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public synchronized static ExcursionService getService(){

        if(service == null){
            service = getInstance();

        }
        return service;
    }

}
