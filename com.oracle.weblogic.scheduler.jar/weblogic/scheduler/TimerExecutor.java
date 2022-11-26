package weblogic.scheduler;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.TargetUtils;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingException;
import weblogic.cluster.singleton.LeasingFactory;
import weblogic.cluster.singleton.Leasing.LeaseOwnerIdentity;
import weblogic.cluster.singleton.LeasingFactory.Locator;
import weblogic.jndi.Environment;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;

public class TimerExecutor implements NakedTimerListener {
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();
   private static final String TIMER_MANAGER = "weblogic.scheduler.TimerExecutor";
   static TimerExecutor THE_ONE;
   private final String leaseName;
   private final String timerMasterJndiName;
   private TimerBasis timerBasis;
   private String partitionName;
   private Set unavailableTimerApps = new HashSet();

   static synchronized void initialize() {
      Debug.assertion(THE_ONE == null);
      THE_ONE = new TimerExecutor(TimerMaster.getInstance(), (String)null);
   }

   static TimerExecutor initialize(TimerMaster timerMaster, String partitionName) {
      return new TimerExecutor(timerMaster, partitionName);
   }

   private Set getUnavailableTimerApplications() {
      Set skipTimers = new HashSet();
      synchronized(this.unavailableTimerApps) {
         Iterator itr = this.unavailableTimerApps.iterator();

         while(itr.hasNext()) {
            TimerApplication appTimer = (TimerApplication)itr.next();
            if (this.isDeployedLocally(appTimer.getApplicationName())) {
               itr.remove();
            } else {
               skipTimers.add(appTimer.getTimerId());
            }
         }

         return skipTimers;
      }
   }

   private boolean isDeployedLocally(String appName) {
      ApplicationContextInternal aci = ApplicationAccess.getApplicationAccess().getApplicationContext(appName);
      boolean isDeployedLocally = aci != null && TargetUtils.isDeployedLocally(aci.getBasicDeploymentMBean().getTargets());
      return isDeployedLocally;
   }

   private TimerExecutor(TimerMaster timerMaster, String partitionName) {
      this.leaseName = timerMaster.getName();
      this.timerBasis = timerMaster.getTimerBasis();
      this.timerMasterJndiName = timerMaster.getJndiName();
      Timer timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.scheduler.TimerExecutor", "weblogic.kernel.System").schedule(this, 0L, 30000L);
      this.partitionName = partitionName;
      timerMaster.setExecutorTimer(timer);
   }

   public void timerExpired(Timer timer) {
      TimerMasterRemote master = this.lookupTimerMaster();
      if (master != null) {
         if (DEBUG) {
            debug(" looked up TimerMasterRemote " + master);
         }

         List readyTimers;
         try {
            Set skipTimers = this.getUnavailableTimerApplications();
            readyTimers = master.getReadyTimers(skipTimers);
            if (DEBUG) {
               debug("getReadyTimers from MASTER size=" + readyTimers.size());
            }
         } catch (TimerException var15) {
            if (DEBUG) {
               var15.printStackTrace();
            }

            return;
         } catch (RemoteException var16) {
            if (DEBUG) {
               var16.printStackTrace();
            }

            return;
         }

         Iterator i = readyTimers.listIterator();

         while(i.hasNext()) {
            String timerId = (String)i.next();

            try {
               TimerState state = this.timerBasis.getTimerState(timerId);
               state.fireWhenReady();
            } catch (TimerException var13) {
               Throwable thr = var13.getCause();
               if (thr != null && thr instanceof ApplicationNotFoundException) {
                  ApplicationNotFoundException anfe = (ApplicationNotFoundException)thr;
                  TimerApplication appTimer = new TimerApplication(anfe.getMessage(), timerId);
                  synchronized(this.unavailableTimerApps) {
                     this.unavailableTimerApps.add(appTimer);
                  }

                  if (DEBUG && !(anfe instanceof GlobalResourceGroupApplicationNotFoundException)) {
                     debug("Failed to find " + appTimer);
                  }
               }
            } catch (NoSuchObjectLocalException var14) {
            }
         }

      }
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[TimerExecutor] " + s);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private TimerMasterRemote lookupTimerMaster() {
      Context ctx = null;
      String server = null;

      String url;
      try {
         LeasingFactory factory = Locator.locateService();
         Leasing leasingService = factory.findOrCreateLeasingService("service");
         String owner = leasingService.findOwner(this.leaseName);
         if (owner != null) {
            server = LeaseOwnerIdentity.getServerNameFromIdentity(owner);
            if (DEBUG) {
               debug("The location of " + this.leaseName + " is: " + server);
            }

            url = getURLManagerService().findAdministrationURL(server);
            if (DEBUG) {
               debug("Contacting " + server + " at " + url + " to fetch the " + this.leaseName);
            }

            Environment env;
            if (url == null) {
               env = null;
               return env;
            }

            env = new Environment();
            env.setProviderUrl(url);
            ctx = env.getInitialContext();
            TimerMasterRemote var8 = (TimerMasterRemote)PortableRemoteObject.narrow(ctx.lookup(this.timerMasterJndiName), TimerMasterRemote.class);
            return var8;
         }

         if (DEBUG) {
            debug("Could not find the current owner of the service " + this.leaseName);
         }

         url = null;
      } catch (LeasingException var24) {
         if (DEBUG) {
            debug("Could not find " + this.leaseName + " on " + server + ". Exception:" + var24.getMessage());
         }

         return null;
      } catch (NamingException var25) {
         if (DEBUG) {
            debug("Could not find " + this.leaseName + " on " + server + ". Exception:" + var25.getMessage());
         }

         return null;
      } catch (UnknownHostException var26) {
         if (DEBUG) {
            debug("Could not find server " + server);
         }

         return null;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var23) {
            }
         }

      }

      return url;
   }

   private class TimerApplication {
      private String applicationName;
      private String timerId;
      private int hashCode;

      public TimerApplication(String app, String timer) {
         this.applicationName = app;
         this.timerId = timer;
         this.hashCode = this.applicationName.hashCode() ^ this.timerId.hashCode();
      }

      public String getApplicationName() {
         return this.applicationName;
      }

      public String getTimerId() {
         return this.timerId;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else {
            if (obj instanceof TimerApplication) {
               TimerApplication appTimer = (TimerApplication)obj;
               if (appTimer.getApplicationName().equals(this.applicationName) && appTimer.getTimerId().equals(this.timerId)) {
                  return true;
               }
            }

            return false;
         }
      }

      public String toString() {
         return "ApplictionName:" + this.applicationName + " TimerId:" + this.timerId;
      }
   }
}
