package weblogic.servlet.internal.session;

import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.servlet.internal.WebAppServletContext;

public class AsyncReplicatedSessionContext extends ReplicatedSessionContext {
   private static final ReplicationServices repserv;

   public AsyncReplicatedSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   public String getPersistentStoreType() {
      return "async-replication";
   }

   protected ReplicationServices getReplicationServices() {
      return repserv;
   }

   protected ReplicatedSessionData instantiateReplicatedSessionData(String sessionID, boolean isNew) {
      return new AsyncReplicatedSessionData(sessionID, this, isNew);
   }

   public void destroy(boolean forceShutdown) {
      repserv.sync();
      super.destroy(forceShutdown);
   }

   static {
      repserv = Locator.locate().getReplicationService(ServiceType.ASYNC);
   }
}
