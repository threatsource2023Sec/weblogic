package weblogic.cluster.singleton;

import java.rmi.RemoteException;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;

@Service
@Named
@RunLevel(20)
public class SingletonServicesBatchManager implements ServerService, TimerListener {
   @Inject
   @Named("ReplicationService")
   ServerService dependencyOnReplicationService;
   @Inject
   private RuntimeAccess runtimeAccess;
   private static SingletonServicesBatchManager singleton;
   private boolean started = false;
   private static final long SINGLETON_MONITOR_DISCOVERY_PERIOD_MILLIS = 10000L;

   public String getName() {
      return "Singleton Services Batch Manager";
   }

   public String getVersion() {
      return "1.0";
   }

   synchronized boolean hasStarted() {
      return this.started;
   }

   static SingletonServicesBatchManager theOne() {
      return singleton;
   }

   private static void setTheOne(SingletonServicesBatchManager one) {
      singleton = one;
   }

   public synchronized void startService(String name) throws IllegalArgumentException {
      try {
         this.registerWithSingletonMonitor(name);
      } catch (RemoteException var3) {
         throw new AssertionError("Unable to register singleton service '" + name + "'\n" + StackTraceUtils.throwable2StackTrace(var3));
      } catch (LeasingException var4) {
         throw new AssertionError("Unable to register singleton service '" + name + "'\n" + StackTraceUtils.throwable2StackTrace(var4));
      }
   }

   @PostConstruct
   public synchronized void start() throws ServiceFailureException {
      ServerMBean serverMBean = this.runtimeAccess.getServer();
      if (serverMBean.getCluster() != null) {
         setTheOne(this);
         this.started = true;
         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 0L, 10000L);
      }
   }

   private void registerWithSingletonMonitor(String name) throws RemoteException, LeasingException {
      SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote();
      if (smr != null) {
         smr.register(name);
      } else {
         throw new LeasingException("Could not contact Singleton Monitor.");
      }
   }

   public void timerExpired(Timer timer) {
      try {
         SingletonMonitorRemote smr = MigratableServerService.theOne().getSingletonMasterRemote();
         if (smr == null) {
            return;
         }
      } catch (LeasingException var8) {
         return;
      }

      Object[] services = SingletonServicesManagerService.getInstance().getInternalSingletonServices();

      for(int i = 0; i < services.length; ++i) {
         String name = (String)services[i];

         try {
            this.registerWithSingletonMonitor(name);
         } catch (RemoteException var6) {
            throw new AssertionError("Unable to register singleton service '" + name + "'\n" + StackTraceUtils.throwable2StackTrace(var6));
         } catch (LeasingException var7) {
            throw new AssertionError("Unable to register singleton service '" + name + "'\n" + StackTraceUtils.throwable2StackTrace(var7));
         }
      }

      timer.cancel();
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }
}
