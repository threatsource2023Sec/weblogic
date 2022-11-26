package weblogic.cluster.replication;

import org.jvnet.hk2.annotations.Contract;
import weblogic.server.ServiceFailureException;

@Contract
public interface WANPersistenceServiceControl {
   void startService() throws ServiceFailureException;

   void stopService();
}
