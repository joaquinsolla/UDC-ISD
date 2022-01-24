package es.udc.ws.app.model.excursion;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class SqlExcursionDaoFactory {

    private final static String CLASS_NAME_PARAMETER = "SqlExcursionDaoFactory.className";
    private static SqlExursionDao dao = null;

    private SqlExcursionDaoFactory(){}

    @SuppressWarnings("rawtypes")
    private static SqlExursionDao getInstance(){
        try{
            String daoClassName = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);

            return (SqlExursionDao) daoClass.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public synchronized static SqlExursionDao getDao(){
        if (dao == null){
            dao = getInstance();
        }
        return dao;
    }
}
