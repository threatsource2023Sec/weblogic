package weblogic.management.runtime;

import java.util.Properties;
import weblogic.utils.ErrorCollectionException;

public interface ConnectorServiceRuntimeMBean extends RuntimeMBean {
   int getActiveRACount();

   int getRACount();

   ConnectorComponentRuntimeMBean[] getRAs();

   ConnectorComponentRuntimeMBean[] getActiveRAs();

   ConnectorComponentRuntimeMBean[] getInactiveRAs();

   ConnectorComponentRuntimeMBean getRA(String var1);

   /** @deprecated */
   @Deprecated
   int getConnectionPoolCurrentCount();

   /** @deprecated */
   @Deprecated
   int getConnectionPoolsTotalCount();

   /** @deprecated */
   @Deprecated
   ConnectorConnectionPoolRuntimeMBean[] getConnectionPools();

   /** @deprecated */
   @Deprecated
   ConnectorConnectionPoolRuntimeMBean getConnectionPool(String var1);

   ConnectorInboundRuntimeMBean[] getInboundConnections(String var1);

   void suspendAll(Properties var1) throws ErrorCollectionException;

   void suspend(int var1, Properties var2) throws ErrorCollectionException;

   void resumeAll(Properties var1) throws ErrorCollectionException;

   void resume(int var1, Properties var2) throws ErrorCollectionException;

   void suspendAll() throws ErrorCollectionException;

   void suspend(int var1) throws ErrorCollectionException;

   void resumeAll() throws ErrorCollectionException;

   void resume(int var1) throws ErrorCollectionException;
}
