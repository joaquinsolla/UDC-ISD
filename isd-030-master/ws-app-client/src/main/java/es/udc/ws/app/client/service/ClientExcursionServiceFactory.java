package es.udc.ws.app.client.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

import java.lang.reflect.InvocationTargetException;

public class ClientExcursionServiceFactory {

    private final static String CLASS_NAME_PARAMETER = "ClientExcursionServiceFactory.className";
    private static Class<ClientExcursionService> serviceClass = null;

    private ClientExcursionServiceFactory(){
    }

    @SuppressWarnings("unchecked")
    private synchronized static Class<ClientExcursionService> getServiceClass(){

        if (serviceClass == null){
            try {
                String serviceClassName = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
                serviceClass = (Class<ClientExcursionService>) Class.forName(serviceClassName);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return serviceClass;
    }

    public static ClientExcursionService getService(){

        try {
            return (ClientExcursionService) getServiceClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
