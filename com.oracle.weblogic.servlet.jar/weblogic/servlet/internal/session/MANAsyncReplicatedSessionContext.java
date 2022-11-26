package weblogic.servlet.internal.session;

import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.servlet.internal.WebAppServletContext;

public class MANAsyncReplicatedSessionContext extends AsyncReplicatedSessionContext {
   private static final ReplicationServices repserv;

   public MANAsyncReplicatedSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
   }

   public String getPersistentStoreType() {
      return "async-replication-across-cluster";
   }

   protected ReplicationServices getReplicationServices() {
      return repserv;
   }

   static {
      repserv = Locator.locate().getReplicationService(ServiceType.MAN_ASYNC);
   }
}
