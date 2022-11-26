package weblogic.jdbc.common.rac;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.OracleHelper;

public interface RACModule extends OracleHelper {
   void setONSConfiguration(String var1);

   String getONSConfiguration();

   RACConnectionEnv getConnection(Properties var1) throws ResourceException;

   void start() throws ResourceException;

   void stop() throws ResourceException;

   void connectionOpened(RACConnectionEnv var1) throws ResourceException;

   void processConnectionsOnDownEvent(RACModuleFailoverEvent var1, List var2, List var3) throws ResourceException;

   int processConnectionsOnUpEvent(RACModuleFailoverEvent var1, List var2, List var3, int var4, int var5) throws ResourceException;

   RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv var1) throws ResourceException;

   List getInstancesForHost(String var1);

   int getInstanceWeight(String var1);

   RACInstance getRACInstance(String var1);

   RACInstance getOrCreateRACInstance(String var1) throws ResourceException;

   boolean getInstanceAffValue(String var1);

   long getFailedRCLBBasedBorrowCount();

   long getSuccessfulRCLBBasedBorrowCount();

   long getFailedAffinityBasedBorrowCount();

   long getSuccessfulAffinityBasedBorrowCount();

   void markConnectionGood(RACConnectionEnv var1);

   List getInstances();

   void connectionClosed(RACConnectionEnv var1) throws ResourceException;

   Set getServiceInstanceNames();
}
