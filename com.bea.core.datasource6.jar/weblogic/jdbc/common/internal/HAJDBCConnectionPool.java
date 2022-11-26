package weblogic.jdbc.common.internal;

import java.util.List;
import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface HAJDBCConnectionPool extends JDBCConnectionPool {
   void addHADataSourceRuntime(HAJDBCConnectionPool var1, HADataSourceRuntime var2);

   HADataSourceRuntime removeHADataSourceRuntime(HAJDBCConnectionPool var1);

   void createInstanceRuntime(HAJDBCConnectionPool var1, ResourcePoolGroup var2, String var3);

   RACModule getRACModule();

   int getWeightForInstance(String var1);

   boolean getAffForInstance(String var1);

   String getServiceName();

   String getDatabaseName();

   long getFailedAffinityBasedBorrowCount();

   long getFailedRCLBBasedBorrowCount();

   long getSuccessfulAffinityBasedBorrowCount();

   long getSuccessfulRCLBBasedBorrowCount();

   void getAvailableAndBorrowedConnections(List var1, List var2);

   void getAvailableAndBorrowedConnections(List var1, List var2, List var3, boolean var4);

   void initAffinityKeyIfNecessary() throws ResourceException;

   String getAffinityContextKey();

   ResourcePoolGroup getPoolGroup();

   ResourcePoolGroup getGroupForInstance(String var1);

   PooledResourceInfo getPooledResourceInfo(RACInstance var1, Properties var2);

   void fcfDownEvent(HAJDBCConnectionPool var1, RACModule var2, RACModuleFailoverEvent var3) throws ResourceException;

   int fcfUpEvent(HAJDBCConnectionPool var1, RACModule var2, RACModuleFailoverEvent var3) throws ResourceException;

   ConnectionEnv reserve(RACModule var1, AuthenticatedSubject var2, int var3, Properties var4, String var5, String var6) throws ResourceException;

   HAConnectionEnv getExistingConnectionToInstance(HAJDBCConnectionPool var1, RACInstance var2, int var3, Properties var4) throws ResourceException;

   boolean removeFromAvailableForProcessing(List var1);

   List getAvailableConnections(RACInstance var1, boolean var2);

   List getReservedConnections(RACInstance var1);

   boolean doDraining(HAJDBCConnectionPool var1);

   boolean isXA();

   void initOns(RACModule var1) throws ResourceException;
}
