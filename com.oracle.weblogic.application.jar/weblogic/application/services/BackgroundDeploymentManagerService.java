package weblogic.application.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationContext;
import weblogic.application.background.BackgroundApplication;
import weblogic.application.background.BackgroundDeployment;
import weblogic.application.background.BackgroundDeploymentManager;
import weblogic.application.ondemand.Deployer;
import weblogic.application.ondemand.DeploymentProvider;
import weblogic.application.ondemand.DeploymentProviderManager;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.server.RunningStateListener;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.SrvrUtilities;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

@Service
@Singleton
public final class BackgroundDeploymentManagerService {
   private static final BackgroundDeploymentManager mgr;
   private static final Map workers;
   private static final DebugLogger LOGGER;
   private static final StateChange prepareStateChange;
   private static final StateChange activateStateChange;
   private static final StateChange adminStateChange;

   public void startBackgroundDeploymentsForDomain() throws ServiceFailureException {
      this.startBackgroundDeployments((String)null);
   }

   public void startBackgroundDeploymentsForPartition(String partitionName) throws ServiceFailureException {
      this.startBackgroundDeployments(partitionName);
   }

   public synchronized void waitForCompletion() {
      Iterator var1 = workers.values().iterator();

      while(var1.hasNext()) {
         BackgroundDeploymentManagerWorker worker = (BackgroundDeploymentManagerWorker)var1.next();
         worker.waitForCompletion();
      }

   }

   public synchronized void waitForCompletion(String partitionName) {
      BackgroundDeploymentManagerWorker worker = (BackgroundDeploymentManagerWorker)workers.get(partitionName == null ? "" : partitionName);
      if (worker != null) {
         worker.waitForCompletion();
      }

   }

   public void unRegisterOnDemandBackgroundDeployments(String partitionName) {
      List onDemandApps = getBackgroundAppsForPartition(partitionName, mgr.getBackgroundOnDemandApps(), false);
      Iterator var3 = onDemandApps.iterator();

      while(var3.hasNext()) {
         BackgroundApplication app = (BackgroundApplication)var3.next();
         Iterator var5 = DeploymentProviderManager.instance.getProviders().iterator();

         while(var5.hasNext()) {
            DeploymentProvider provider = (DeploymentProvider)var5.next();
            ApplicationContext appCtx = app.getDeployment().getApplicationContext();
            AppDeploymentMBean appMBean = appCtx.getAppDeploymentMBean();
            if (LOGGER.isDebugEnabled()) {
               debug("unRegisterOnDemandBackgroundDeployments, calling provider.unclaim for " + appMBean.getName());
            }

            provider.unclaim(appMBean);
            app.getDeployment().setOnDemandContextPathRegistered(false);
         }
      }

   }

   private static boolean isSkip(BackgroundDeployment depl, boolean isDeploy, boolean isOnDemand) {
      return isDeploy && (depl.getStartedDeployment() || depl.getCompletedDeployment() || !isOnDemand && depl.getOnDemandContextPathRegistered()) || !isDeploy && (depl.getStartedUnDeployment() || depl.getCompletedUnDeployment());
   }

   private static BackgroundApplication getBackgroundOnDemandApp(String appName) {
      Iterator var1 = mgr.getBackgroundOnDemandApps().iterator();

      BackgroundApplication app;
      AppDeploymentMBean appMBean;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         app = (BackgroundApplication)var1.next();
         ApplicationContext appCtx = app.getDeployment().getApplicationContext();
         appMBean = appCtx.getAppDeploymentMBean();
      } while(!appName.equals(appMBean.getName()));

      return app;
   }

   private static List getBackgroundAppsForPartition(String partitionName, List allApps, boolean isDeploy) {
      List appNames = new ArrayList();
      List apps = new ArrayList();
      Iterator var5 = allApps.iterator();

      while(true) {
         while(true) {
            BackgroundApplication app;
            do {
               if (!var5.hasNext()) {
                  return apps;
               }

               app = (BackgroundApplication)var5.next();
            } while(isSkip(app.getDeployment(), isDeploy, false));

            ApplicationContext appCtx = app.getDeployment().getApplicationContext();
            AppDeploymentMBean appMBean = appCtx.getAppDeploymentMBean();
            if (appMBean == null) {
               if (LOGGER.isDebugEnabled()) {
                  debug("getBackgroundAppsForPartition, unable to get AppDeploymentMBean from ApplicationContext - appId=" + appCtx.getApplicationId());
               }
            } else if ((appMBean.getPartitionName() == null && partitionName == null || appMBean.getPartitionName() != null && appMBean.getPartitionName().equals(partitionName)) && !appNames.contains(appMBean.getName())) {
               apps.add(app);
               appNames.add(appMBean.getName());
            }
         }
      }
   }

   private void startBackgroundDeployments(String partitionName) {
      BackgroundDeploymentManagerWorker backgroundWorker = new BackgroundDeploymentManagerWorker(partitionName);
      OnDemandDeployer onDemandDeployer = new OnDemandDeployer(backgroundWorker);
      List backgroundOnDemandApps = getBackgroundAppsForPartition(partitionName, mgr.getBackgroundOnDemandApps(), true);
      Iterator var5 = backgroundOnDemandApps.iterator();

      while(var5.hasNext()) {
         BackgroundApplication app = (BackgroundApplication)var5.next();
         ApplicationContext appCtx = app.getDeployment().getApplicationContext();
         AppDeploymentMBean appMBean = appCtx.getAppDeploymentMBean();
         Iterator var9 = DeploymentProviderManager.instance.getProviders().iterator();

         while(var9.hasNext()) {
            DeploymentProvider provider = (DeploymentProvider)var9.next();
            if (LOGGER.isDebugEnabled()) {
               debug("startBackgroundDeployment, calling provider.claim for " + appMBean.getName());
            }

            provider.claim(appMBean, onDemandDeployer);
            app.getDeployment().setOnDemandContextPathRegistered(true);
         }
      }

      if (!mgr.getBackgroundApps().isEmpty()) {
         List backgroundApps = getBackgroundAppsForPartition(partitionName, mgr.getBackgroundApps(), true);
         synchronized(workers) {
            workers.put(backgroundWorker.getPartitionName(), backgroundWorker);
         }

         backgroundWorker.setBackgroundApps((BackgroundApplication[])backgroundApps.toArray(new BackgroundApplication[backgroundApps.size()]));
         backgroundWorker.start();
      }
   }

   private static void debug(String s) {
      LOGGER.debug("BackgroundDeploymentManagerService " + s);
   }

   static {
      mgr = BackgroundDeploymentManager.instance;
      workers = new ConcurrentHashMap();
      LOGGER = DebugLogger.getDebugLogger("DebugBackgroundDeployment");
      prepareStateChange = new StateChange() {
         public void next(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().prepare(a.getDeploymentContext());
         }

         public void previous(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().unprepare(a.getDeploymentContext());
         }

         public void logRollbackError(StateChangeException e) {
            J2EELogger.logIgnoringUndeploymentError(e.getCause());
         }
      };
      activateStateChange = new StateChange() {
         public void next(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().activate(a.getDeploymentContext());
         }

         public void previous(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().deactivate(a.getDeploymentContext());
         }

         public void logRollbackError(StateChangeException e) {
            J2EELogger.logIgnoringUndeploymentError(e.getCause());
         }
      };
      adminStateChange = new StateChange() {
         public void next(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().adminToProduction(a.getDeploymentContext());
         }

         public void previous(Object obj) throws Exception {
            BackgroundApplication a = (BackgroundApplication)obj;
            a.getDeployment().getDelegate().forceProductionToAdmin(a.getDeploymentContext());
         }

         public void logRollbackError(StateChangeException e) {
            J2EELogger.logIgnoringAdminModeErrro(e.getCause());
         }
      };
   }

   private static class OnDemandBackgroundDeployAction extends BackgroundDeployAction {
      private final BackgroundApplication app;

      OnDemandBackgroundDeployAction(BackgroundApplication app, BackgroundDeploymentManagerWorker _worker) {
         super(new BackgroundApplication[]{app}, _worker);
         this.app = app;
      }

      public void run() {
         Class var1 = OnDemandBackgroundDeployAction.class;
         synchronized(OnDemandBackgroundDeployAction.class) {
            this.app.getDeployment().setStartedDeployment(true);
            if (!this.app.getDeployment().getCompletedDeployment() && this.app.getFailureException() == null) {
               super.run();
            }
         }
      }
   }

   private static class BackgroundDeployAction implements Runnable {
      private final BackgroundApplication[] backgroundApps;
      private final StateMachineDriver driver = new StateMachineDriver();
      private final BackgroundDeploymentManagerWorker worker;

      BackgroundDeployAction(BackgroundApplication[] bdapps, BackgroundDeploymentManagerWorker _worker) {
         this.backgroundApps = bdapps;
         this.worker = _worker;
      }

      public void run() {
         boolean var20 = false;

         label193: {
            label194: {
               try {
                  label184: {
                     BackgroundApplication[] var2;
                     int var3;
                     int var4;
                     BackgroundApplication backgroundApp;
                     try {
                        var20 = true;
                        BackgroundApplication[] var31 = this.backgroundApps;
                        int var32 = var31.length;

                        BackgroundApplication backgroundApp;
                        for(var3 = 0; var3 < var32; ++var3) {
                           backgroundApp = var31[var3];
                           backgroundApp.getDeployment().setStartedDeployment(true);
                        }

                        this.driver.nextState(BackgroundDeploymentManagerService.prepareStateChange, this.backgroundApps);

                        try {
                           this.driver.nextState(BackgroundDeploymentManagerService.activateStateChange, this.backgroundApps);

                           try {
                              this.driver.nextState(BackgroundDeploymentManagerService.adminStateChange, this.backgroundApps);
                              var31 = this.backgroundApps;
                              var32 = var31.length;

                              for(var3 = 0; var3 < var32; ++var3) {
                                 backgroundApp = var31[var3];
                                 backgroundApp.getDeployment().setCompletedDeployment(true);
                              }

                              var20 = false;
                              break label193;
                           } catch (Exception var25) {
                              this.driver.previousState(BackgroundDeploymentManagerService.activateStateChange, this.backgroundApps);
                              throw var25;
                           }
                        } catch (Exception var26) {
                           this.driver.previousState(BackgroundDeploymentManagerService.prepareStateChange, this.backgroundApps);
                           throw var26;
                        }
                     } catch (Exception var27) {
                        Exception e = var27;
                        J2EELogger.logErrorDeployingApplication("Internal Application", var27.getMessage(), var27);
                        var2 = this.backgroundApps;
                        var3 = var2.length;

                        for(var4 = 0; var4 < var3; ++var4) {
                           backgroundApp = var2[var4];
                           backgroundApp.setFailureException(e);
                        }

                        var20 = false;
                        break label184;
                     } catch (Error var28) {
                        Error e = var28;
                        var2 = this.backgroundApps;
                        var3 = var2.length;

                        for(var4 = 0; var4 < var3; ++var4) {
                           backgroundApp = var2[var4];
                           backgroundApp.setFailureException(new RuntimeException(e));
                        }
                     }

                     var20 = false;
                     break label194;
                  }
               } finally {
                  if (var20) {
                     this.worker.complete();
                     synchronized(BackgroundDeploymentManagerService.workers) {
                        BackgroundDeploymentManagerService.workers.remove(this.worker.getPartitionName());
                     }
                  }
               }

               this.worker.complete();
               synchronized(BackgroundDeploymentManagerService.workers) {
                  BackgroundDeploymentManagerService.workers.remove(this.worker.getPartitionName());
                  return;
               }
            }

            this.worker.complete();
            synchronized(BackgroundDeploymentManagerService.workers) {
               BackgroundDeploymentManagerService.workers.remove(this.worker.getPartitionName());
               return;
            }
         }

         this.worker.complete();
         synchronized(BackgroundDeploymentManagerService.workers) {
            BackgroundDeploymentManagerService.workers.remove(this.worker.getPartitionName());
         }

      }
   }

   private static class OnDemandDeployer implements Deployer {
      private final BackgroundDeploymentManagerWorker worker;

      OnDemandDeployer(BackgroundDeploymentManagerWorker _worker) {
         this.worker = _worker;
      }

      public void deploy(String appName, boolean loadAsynchronously) throws DeploymentException {
         BackgroundApplication app = BackgroundDeploymentManagerService.getBackgroundOnDemandApp(appName);
         if (app == null) {
            throw new IllegalArgumentException("appName " + appName + " is not found");
         } else if (!app.getDeployment().getCompletedDeployment()) {
            if (app.getFailureException() != null) {
               if (app.getFailureException() instanceof DeploymentException) {
                  throw (DeploymentException)app.getFailureException();
               } else {
                  throw new DeploymentException(app.getFailureException());
               }
            } else {
               this.worker.waitForCompletion();
               if (!loadAsynchronously || !app.getDeployment().getStartedDeployment()) {
                  OnDemandBackgroundDeployAction action = new OnDemandBackgroundDeployAction(app, this.worker);
                  if (loadAsynchronously) {
                     WorkManagerFactory.getInstance().getSystem().schedule(action);
                  } else {
                     action.run();
                     if (app.getFailureException() != null) {
                        if (app.getFailureException() instanceof DeploymentException) {
                           throw (DeploymentException)app.getFailureException();
                        }

                        throw new DeploymentException(app.getFailureException());
                     }
                  }

               }
            }
         }
      }
   }

   private static class BackgroundDeploymentManagerWorker implements TimerListener, RunningStateListener {
      private volatile boolean backgroundActionScheduled = false;
      private volatile boolean stillRunning;
      private BackgroundApplication[] backgroundAppsArray;
      private static final long BACKGROUND_DELAY = 3000L;
      private static int globalId = 0;
      private final int id;
      private final String partitionName;

      public BackgroundDeploymentManagerWorker(String _partitionName) {
         this.partitionName = _partitionName == null ? "" : _partitionName;
         this.id = ++globalId;
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("created:" + this.id);
         }

      }

      public String getPartitionName() {
         return this.partitionName;
      }

      public void setBackgroundApps(BackgroundApplication[] _appsArray) {
         this.backgroundAppsArray = _appsArray;
      }

      public void timerExpired(Timer timer) {
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("timerExpired:" + this.id);
         }

         this.scheduleBackgroundDeployAction();
      }

      public void onRunning() {
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("onRunning:" + this.id);
         }

         this.scheduleBackgroundDeployAction();
      }

      private synchronized void scheduleBackgroundDeployAction() {
         if (this.backgroundActionScheduled) {
            if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
               debug("already scheduled:" + this.id);
            }

         } else {
            this.backgroundActionScheduled = true;
            SrvrUtilities.removeRunningStateListener(this);
            if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
               debug("scheduling background deploy action:" + this.toString());
            }

            WorkManagerFactory.getInstance().getSystem().schedule(new BackgroundDeployAction(this.backgroundAppsArray, this));
         }
      }

      public void start() {
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("starting:" + this.id);
         }

         this.stillRunning = true;
         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 3000L);
         SrvrUtilities.addRunningStateListener(this);
      }

      public synchronized void waitForCompletion() {
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("waiting for completion :" + this.toString());
         }

         while(this.stillRunning) {
            try {
               this.wait();
            } catch (InterruptedException var2) {
            }
         }

      }

      public synchronized void complete() {
         if (BackgroundDeploymentManagerService.LOGGER.isDebugEnabled()) {
            debug("marking as complete :" + this.id);
         }

         this.stillRunning = false;
         this.notifyAll();
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("id=");
         sb.append(this.id);
         if (this.backgroundAppsArray != null && this.backgroundAppsArray.length > 0) {
            sb.append(", backgroundApps=[ ");

            for(int i = 0; i < this.backgroundAppsArray.length; ++i) {
               sb.append(this.backgroundAppsArray[i].getDeployment().getApplicationContext().getApplicationId());
               if (i < this.backgroundAppsArray.length - 1) {
                  sb.append(", ");
               }
            }

            sb.append(" ]");
         }

         return sb.toString();
      }

      private static void debug(String s) {
         BackgroundDeploymentManagerService.debug("BackgroundDeploymentManagerWorker " + s);
      }
   }
}
