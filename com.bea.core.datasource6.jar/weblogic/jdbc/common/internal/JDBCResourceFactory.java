package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceFactory;

public interface JDBCResourceFactory extends PooledResourceFactory {
   String getTestQuery();

   void clearTestValidation();

   void initializeTest(ConnectionEnv var1, String var2) throws ResourceException;

   JDBCConnectionPool getPool();

   Properties getPoolParams();

   void setStatementCacheSize(int var1);

   void setSecondsToTrustAnIdlePoolConnection(int var1);

   boolean isTestValidationFailed();

   ConnectionEnv instantiatePooledResource(Properties var1);

   void updateCredential(String var1) throws SQLException;
}
