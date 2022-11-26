package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface MigratableServer {
   boolean isSingletonMaster();

   String findSingletonMaster() throws LeasingException;

   SingletonMonitorRemote getSingletonMasterRemote() throws LeasingException;

   SingletonMonitorRemote getSingletonMasterRemote(int var1) throws LeasingException;

   SingletonMonitorRemote getSingletonMaster(String var1) throws LeasingException;

   public static class Locator {
      public static MigratableServer locate() {
         return (MigratableServer)GlobalServiceLocator.getServiceLocator().getService(MigratableServer.class, new Annotation[0]);
      }
   }
}
