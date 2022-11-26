package weblogic.security.spi;

public interface SecurityServicesJDBC {
   JDBCConnectionService getJDBCConnectionService() throws JDBCConnectionServiceException;
}
