package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceFactory;

public class HAUtilImpl extends HAUtil {
   public PooledResourceFactory createConnectionEnvFactory(ConnectionPool connectionPool, String appName, String moduleName, String compName, Properties initInfo) throws ResourceException {
      return new HAConnectionEnvFactory(connectionPool, appName, moduleName, compName, initInfo);
   }

   public XAConnectionEnvFactory createXAConnectionEnvFactory(ConnectionPool connectionPool, String appName, String moduleName, String compName, String mpName, Properties initInfo) throws ResourceException, SQLException {
      return new HAXAConnectionEnvFactory(connectionPool, appName, moduleName, compName, mpName, initInfo);
   }

   public PooledResourceFactory createPooledConnectionEnvFactory(ConnectionPool connectionPool, String appName, String moduleName, String compName, Properties initInfo) throws ResourceException, SQLException {
      return new HAPooledConnectionEnvFactory(connectionPool, appName, moduleName, compName, initInfo);
   }
}
