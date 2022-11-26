package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface SingletonMasterService {
   String SINGLETON_MASTER = "SINGLETON_MASTER";

   boolean isSingletonMaster();

   SingletonMonitorRemote getSingletonMonitorRemote();

   void start();

   void stop();
}
