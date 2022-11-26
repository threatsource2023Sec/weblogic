package weblogic.jdbc.common.rac;

import java.util.Properties;
import oracle.ucp.ConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.OraclePool;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.DataAffinityCallback;

public interface RACModulePool extends OraclePool {
   RACConnectionEnv getExistingConnectionToInstance(RACInstance var1, int var2, Properties var3) throws ResourceException;

   RACConnectionEnv getExistingConnection(int var1, Properties var2) throws ResourceException;

   RACConnectionEnv createConnectionToInstance(RACInstance var1) throws ResourceException;

   void removePooledResource(RACConnectionEnv var1) throws ResourceException;

   int getMaxPoolSize();

   int getMinPoolSize();

   String getPoolName();

   int getRemainingPoolCapacity();

   int getCurrentPoolCapacity();

   String getJDBCURL();

   void fcfDownEvent(RACModuleFailoverEvent var1) throws ResourceException;

   int fcfUpEvent(RACModuleFailoverEvent var1) throws ResourceException;

   ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy();

   AffinityCallback getXAAffinityCallback();

   AffinityCallback getSessionAffinityCallback();

   DataAffinityCallback getDataAffinityCallback();

   RACConnectionEnv reserveInternalResource() throws ResourceException;

   void release(ConnectionEnv var1) throws ResourceException;

   String getServiceName();

   String getDatabaseName();

   RACModulePool getSharedRACModulePool();

   boolean isSharedPool();

   boolean isSharingPool();

   void switchToRootPartition(ConnectionEnv var1) throws ResourceException;

   void initAffinityKeyIfNecessary() throws ResourceException;

   RACConnectionEnv createTemporaryConnection() throws ResourceException;
}
