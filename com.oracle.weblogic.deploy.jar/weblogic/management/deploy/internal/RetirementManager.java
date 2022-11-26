package weblogic.management.deploy.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.common.Debug;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class RetirementManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int RETIREMENT_SCHEDULED = 1;
   private static final int RETIREMENT_STARTED = 2;
   private static final int RETIREMENT_CANCELLED = 3;
   private static final String RETIRE_TASK_ID_PREFIX = "weblogic.retire.";
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();
   private static final Hashtable retireInfoMap = new Hashtable();
   private static HashMap retireOnRestartApps = null;
   private static int curTaskId = 0;
   private static Timer retireTimer = null;
   private static final int MAX_RETIRE_WAIT_TIME_SECS = 20;

   public static void retire(AppDeploymentMBean dep, DeploymentData info) throws ManagementException {
      if (dep != null) {
         String appId = dep.getApplicationIdentifier();
         if (retireInfoMap.get(appId) == null) {
            int retireTimeoutSecs = getRetireTimeoutSecs(info);
            boolean ignoreSessions = getIgnoreSessions(info);
            int rmiGracePeriodSecs = getRMIGracePeriodSecs(info);
            long retireTimeMillis = System.currentTimeMillis();
            if (retireTimeoutSecs > 0) {
               retireTimeMillis += (long)(retireTimeoutSecs * 1000);
            }

            appRTStateMgr.setRetireTimeoutSeconds(appId, retireTimeoutSecs);
            appRTStateMgr.setRetireTimeMillis(appId, retireTimeMillis);
            if (retireTimeoutSecs == -1) {
               DeployerRuntimeLogger.logRetireGracefully(ApplicationVersionUtils.getDisplayName(appId));
               stopForGracefulRetire(appId, ignoreSessions, rmiGracePeriodSecs);
            } else {
               scheduleRetire(appId, retireTimeoutSecs, retireTimeMillis, ignoreSessions);
            }

         }
      }
   }

   public static void retireAppsOnStartup() {
      ArrayList apps = getToBeRetiredApps();
      if (apps != null && apps.size() != 0) {
         ServerRuntimeMBean serverRTMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         if (serverRTMBean != null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Registering " + apps.size() + " app(s) for retirement");
            }

            HashMap appIds = new HashMap(apps.size());

            for(int i = 0; i < apps.size(); ++i) {
               appIds.put(((AppDeploymentMBean)apps.get(i)).getApplicationIdentifier(), new Integer(1));
            }

            retireOnRestartApps = appIds;
            serverRTMBean.addPropertyChangeListener(new RetireOnRestartListener(apps));
         } else if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Cannot register apps for retirement");
         }

      }
   }

   private static ArrayList getToBeRetiredApps() {
      RuntimeAccess rtAccess = ManagementService.getRuntimeAccess(kernelId);
      if (!rtAccess.isAdminServer()) {
         return null;
      } else {
         AppDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibs(rtAccess.getDomain());
         ArrayList retireAppList = new ArrayList();

         for(int i = 0; i < apps.length; ++i) {
            AppDeploymentMBean app = apps[i];
            if (app != null && app.getVersionIdentifier() != null && !(app instanceof LibraryMBean) && appRTStateMgr.getCurrentState(app) != null && !appRTStateMgr.isActiveVersion(app) && !appRTStateMgr.isRetiredVersion(app) && !appRTStateMgr.isFailedVersion(app)) {
               retireAppList.add(app);
            }
         }

         return retireAppList;
      }
   }

   private static void retireOnRestart(ArrayList apps) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("RetireOnRestart " + apps.size() + " app(s)");
      }

      Iterator iter = apps.iterator();

      while(iter.hasNext()) {
         retireOnRestart((AppDeploymentMBean)iter.next());
         iter.remove();
      }

      synchronized(retireInfoMap) {
         retireOnRestartApps = null;
      }
   }

   private static void retireOnRestart(AppDeploymentMBean deployMBean) {
      if (deployMBean != null) {
         String appId = deployMBean.getApplicationIdentifier();
         int retireTimeoutSecs = appRTStateMgr.getRetireTimeoutSeconds(appId);
         long retireTimeMillis = appRTStateMgr.getRetireTimeMillis(appId);
         long currentTimeMillis = System.currentTimeMillis();
         if (retireTimeMillis > 0L) {
            if (retireTimeMillis <= currentTimeMillis) {
               if (retireTimeoutSecs == -1) {
                  DeployerRuntimeLogger.logRetireGracefully(ApplicationVersionUtils.getDisplayName(deployMBean));
                  retireNow(appId, true, true);
               } else {
                  DeployerRuntimeLogger.logRetireNow(ApplicationVersionUtils.getDisplayName(deployMBean));
                  retireNow(appId, false, true);
               }
            } else {
               scheduleRetire(appId, (int)((retireTimeMillis - currentTimeMillis) / 1000L), retireTimeMillis, true);
            }

         }
      }
   }

   private static void stopForGracefulRetire(String appId, boolean ignoreSessions, int rmiGracePeriodSecs) {
      String retireTaskId = getRetireTaskId(appId);
      RetireInfo retireInfo = new RetireInfo(2, appId, retireTaskId, true, ignoreSessions, rmiGracePeriodSecs, System.currentTimeMillis());
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("RM.putRetireInfo for stop, app=" + retireInfo.appId + ", taskId=" + retireInfo.taskId);
      }

      retireInfoMap.put(appId, retireInfo);
      stopForGracefulRetire(retireInfo);
   }

   private static void retireNow(String appId, boolean graceful, boolean ignoreSessions) {
      String retireTaskId = getRetireTaskId(appId);
      RetireInfo retireInfo = new RetireInfo(2, appId, retireTaskId, graceful, ignoreSessions, -1, System.currentTimeMillis());
      synchronized(retireInfoMap) {
         if (retireOnRestartApps != null && retireOnRestartApps.containsKey(appId) && (Integer)retireOnRestartApps.remove(appId) == 3) {
            return;
         }

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("RM.putRetireInfo for retireNow, app=" + retireInfo.appId + ", taskId=" + retireInfo.taskId);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise((graceful ? "Graceful " : "") + "Retire for application '" + appId + "' is set to occur now with taskId of " + retireInfo.taskId);
         }

         retireInfoMap.put(appId, retireInfo);
      }

      retire(retireInfo);
   }

   private static void scheduleRetire(String appId, int secsToRetire, long retireTimeMillis, boolean ignoreSessions) {
      DeployerRuntimeLogger.logRetireTimeout(ApplicationVersionUtils.getDisplayName(appId), secsToRetire);
      String retireTaskId = getRetireTaskId(appId);
      RetireInfo retireInfo = new RetireInfo(1, appId, retireTaskId, false, ignoreSessions, -1, retireTimeMillis);
      synchronized(retireInfoMap) {
         if (retireOnRestartApps != null && retireOnRestartApps.containsKey(appId) && (Integer)retireOnRestartApps.remove(appId) == 3) {
            return;
         }

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("RM.putRetireInfo for scheduleRetire, app=" + retireInfo.appId + ", taskId=" + retireInfo.taskId);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Retire for application '" + appId + "' is set to occur in " + secsToRetire + " sec. with taskId of " + retireInfo.taskId);
         }

         retireInfoMap.put(appId, retireInfo);
      }

      startTimerIfNeeded();
   }

   public static boolean cancelIfNeeded(String appName, String versionId) throws ManagementException {
      String appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
      RetireInfo retireInfo;
      synchronized(retireInfoMap) {
         if (retireOnRestartApps != null && retireOnRestartApps.containsKey(appId)) {
            retireOnRestartApps.put(appId, new Integer(3));
            return true;
         }

         retireInfo = (RetireInfo)retireInfoMap.get(appId);
      }

      if (retireInfo == null) {
         return true;
      } else {
         boolean cancel = false;
         synchronized(retireInfo) {
            if (retireInfo.state == 1) {
               retireInfo.state = 3;
               cancel = true;
            } else if (retireInfo.state == 2) {
               return false;
            }
         }

         if (cancel) {
            retireInfoMap.remove(appId);
            DeployerRuntimeLogger.logRetirementCancelled(ApplicationVersionUtils.getDisplayName(appName, versionId), (retireInfo.retireTimeMillis - System.currentTimeMillis()) / 1000L);
         }

         return true;
      }
   }

   public static boolean isRetirementInProgress(String appName, String versionId) {
      String appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
      synchronized(retireInfoMap) {
         return retireInfoMap.get(appId) != null || retireOnRestartApps != null && retireOnRestartApps.containsKey(appId);
      }
   }

   public static void waitForRetirementCompleteIfNeeded(String appName, String versionId) {
      String appId = ApplicationVersionUtils.getApplicationId(appName, versionId);
      RetireInfo retireInfo = (RetireInfo)retireInfoMap.get(appId);
      if (retireInfo != null) {
         synchronized(retireInfo) {
            try {
               if (!retireInfo.notificationDone()) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("RM.waitForRetirementComplete for, app=" + retireInfo.appId + ", taskId=" + retireInfo.taskId);
                  }

                  retireInfo.wait(20000L);
               }
            } catch (InterruptedException var7) {
            }
         }
      }

   }

   private static void retireExpiredApps() {
      HashMap currentMap;
      synchronized(retireInfoMap) {
         currentMap = new HashMap(retireInfoMap);
      }

      ArrayList expiredApps = new ArrayList();
      Iterator iter = currentMap.values().iterator();

      while(iter.hasNext()) {
         RetireInfo retireInfo = (RetireInfo)iter.next();
         long currentTimeMillis = System.currentTimeMillis();
         synchronized(retireInfo) {
            if (retireInfo.state != 1 || retireInfo.retireTimeMillis > currentTimeMillis) {
               continue;
            }

            if (Debug.isDeploymentDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
               String debugMsg = "Retirement timer expired for application '" + retireInfo.appId + "'.";
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug(debugMsg);
               } else {
                  Debug.deploymentDebugConcise(debugMsg);
               }
            }

            retireInfo.state = 2;
         }

         expiredApps.add(retireInfo);
      }

      initiateRetire(expiredApps);
   }

   private static void initiateRetire(final ArrayList apps) {
      WorkManagerFactory.getInstance().getDefault().schedule(new Runnable() {
         public void run() {
            Iterator iter = apps.iterator();

            while(iter.hasNext()) {
               RetirementManager.retire((RetireInfo)iter.next());
            }

         }
      });
   }

   private static void retire(RetireInfo retireInfo) {
      if (Debug.isDeploymentDebugEnabled() || Debug.isDeploymentDebugConciseEnabled()) {
         String debugMsg = "Start retire operation for application '" + retireInfo.appId + "'.";
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(debugMsg);
         } else {
            Debug.deploymentDebugConcise(debugMsg);
         }
      }

      AppDeploymentMBean deployMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupAppDeployment(retireInfo.appId);
      if (deployMBean == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("RM.retire skip undeployed app=" + retireInfo.appId);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Skip retire operation for application '" + retireInfo.appId + "' as it may already have been undeployed.");
         }

         retireTaskDone(retireInfo);
         synchronized(retireInfo) {
            retireInfo.notifyAll();
            retireInfo.setNotificationDone(true);
         }
      } else {
         try {
            DeployerRuntimeImpl deployerRuntime = (DeployerRuntimeImpl)ApplicationUtils.getDeployerRuntimeFromCIC();
            if (deployerRuntime == null) {
               return;
            }

            DeploymentTaskRuntimeMBean retireTask = deployerRuntime.retire(retireInfo.appId, getDeployData(retireInfo), retireInfo.taskId, false);
            if (retireTask != null) {
               retireTask.addPropertyChangeListener(new RetireTaskListener(retireInfo));
            }

            if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("RetireTask id '" + retireInfo.taskId + "' is started for application '" + retireInfo.appId + "'.");
            }

            retireTask.start();
         } catch (ManagementException var5) {
         }

      }
   }

   private static void retireTaskDone(RetireInfo inRetireInfo) {
      if (inRetireInfo != null && inRetireInfo.appId != null && inRetireInfo.taskId != null) {
         RetireInfo retireInfo = (RetireInfo)retireInfoMap.get(inRetireInfo.appId);
         if (retireInfo != null && inRetireInfo.taskId.equals(retireInfo.taskId)) {
            retireInfoMap.remove(retireInfo.appId);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("RM.retireTaskDone for app=" + retireInfo.appId + ", taskId=" + retireInfo.taskId);
            } else if (Debug.isDeploymentDebugConciseEnabled()) {
               Debug.deploymentDebugConcise("RetireTask id '" + retireInfo.taskId + "' is done for application '" + retireInfo.appId + "'.");
            }
         }

      }
   }

   private static void stopForGracefulRetire(RetireInfo retireInfo) {
      AppDeploymentMBean deployMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupAppDeployment(retireInfo.appId);
      if (deployMBean == null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("RM.stopForGracefulRetire skip app=" + retireInfo.appId);
         }

      } else {
         try {
            DeployerRuntimeImpl deployerRuntime = (DeployerRuntimeImpl)DeploymentServerService.getDeployerRuntime();
            if (deployerRuntime == null) {
               return;
            }

            DeploymentTaskRuntimeMBean gracefulStopTask = deployerRuntime.stopForGracefulRetire(retireInfo.appId, getDeployData(retireInfo), retireInfo.taskId, false);
            if (gracefulStopTask != null) {
               gracefulStopTask.addPropertyChangeListener(new GracefulStopTaskListener(retireInfo));
            }

            gracefulStopTask.start();
         } catch (ManagementException var4) {
         }

      }
   }

   public static synchronized String getRetireTaskId(String appId) {
      return "weblogic.retire." + appId + "." + curTaskId++;
   }

   public static boolean isRetireTaskId(String taskId) {
      return taskId != null && taskId.startsWith("weblogic.retire.");
   }

   private static int getRetireTimeoutSecs(DeploymentData deployData) {
      return deployData != null && deployData.getDeploymentOptions() != null ? deployData.getDeploymentOptions().getRetireTime() : -1;
   }

   private static boolean getIgnoreSessions(DeploymentData deployData) {
      return deployData != null && deployData.getDeploymentOptions() != null ? deployData.getDeploymentOptions().isGracefulIgnoreSessions() : false;
   }

   private static int getRMIGracePeriodSecs(DeploymentData deployData) {
      return deployData != null && deployData.getDeploymentOptions() != null ? deployData.getDeploymentOptions().getRMIGracePeriodSecs() : -1;
   }

   private static DeploymentData getDeployData(RetireInfo info) {
      DeploymentData data = new DeploymentData();
      DeploymentOptions opts = new DeploymentOptions();
      opts.setRetireGracefully(info.graceful);
      if (info.graceful) {
         data.setTimeOut(Integer.MAX_VALUE);
         opts.setTimeout(Long.MAX_VALUE);
      }

      opts.setGracefulProductionToAdmin(info.graceful);
      opts.setGracefulIgnoreSessions(info.ignoreSessions);
      opts.setRMIGracePeriodSecs(info.rmiGracePeriodSecs);
      data.setDeploymentOptions(opts);
      return data;
   }

   private static synchronized void startTimerIfNeeded() {
      if (retireTimer == null) {
         retireTimer = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TimerListener() {
            public void timerExpired(Timer timer) {
               RetirementManager.retireExpiredApps();
            }
         }, 5000L, 5000L);
      }

   }

   private static class RetireOnRestartListener implements PropertyChangeListener {
      private ArrayList apps;

      private RetireOnRestartListener(ArrayList apps) {
         this.apps = apps;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equalsIgnoreCase("State")) {
            Object newValue = evt.getNewValue();
            if (newValue instanceof String && ((String)newValue).equalsIgnoreCase("RUNNING")) {
               TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new TimerListener() {
                  public void timerExpired(Timer timer) {
                     RetirementManager.retireOnRestart(RetireOnRestartListener.this.apps);
                  }
               }, (long)(ManagementService.getRuntimeAccess(RetirementManager.kernelId).getServer().getAdminReconnectIntervalSeconds() * 1000));
            }
         }

      }

      // $FF: synthetic method
      RetireOnRestartListener(ArrayList x0, Object x1) {
         this(x0);
      }
   }

   private static class RetireTaskListener implements PropertyChangeListener {
      private RetireInfo info;

      private RetireTaskListener(RetireInfo info) {
         this.info = info;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equalsIgnoreCase("State")) {
            Object newValue = evt.getNewValue();
            if (newValue instanceof Integer) {
               int state = (Integer)newValue;
               if (state == 4) {
                  DeployerRuntimeImpl deployerRuntime = (DeployerRuntimeImpl)DeploymentServerService.getDeployerRuntime();
                  deployerRuntime.removeTask(this.info.taskId);
                  this.info.taskId = RetirementManager.getRetireTaskId(this.info.appId);
                  this.info.state = 1;
               }

               if (state == 2 || state == 3) {
                  RetirementManager.retireTaskDone(this.info);
                  synchronized(this.info) {
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("RetireTaskListener.propertyChange for, app=" + this.info.appId + ", taskId=" + this.info.taskId);
                     }

                     this.info.notifyAll();
                     this.info.setNotificationDone(true);
                  }

                  try {
                     ((RuntimeMBeanDelegate)evt.getSource()).removePropertyChangeListener(this);
                  } catch (Throwable var6) {
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      RetireTaskListener(RetireInfo x0, Object x1) {
         this(x0);
      }
   }

   private static class GracefulStopTaskListener implements PropertyChangeListener {
      private RetireInfo info;

      private GracefulStopTaskListener(RetireInfo info) {
         this.info = info;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equalsIgnoreCase("State")) {
            Object newValue = evt.getNewValue();
            if (newValue instanceof Integer) {
               int state = (Integer)newValue;
               if (state == 2 || state == 3 || state == 4) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("RM.stopForGracefulRetire done for app=" + this.info.appId + ", taskId=" + this.info.taskId);
                  } else if (Debug.isDeploymentDebugConciseEnabled()) {
                     Debug.deploymentDebugConcise("RetireTask id '" + this.info.taskId + "' completed Stop for Graceful Retire for application '" + this.info.appId + "'.");
                  }

                  RetirementManager.retireTaskDone(this.info);
                  this.info.taskId = RetirementManager.getRetireTaskId(this.info.appId);
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("RM.putRetireInfo for retire, app=" + this.info.appId + ", taskId=" + this.info.taskId);
                  }

                  RetirementManager.retireInfoMap.put(this.info.appId, this.info);
                  RetirementManager.retire(this.info);

                  try {
                     ((RuntimeMBeanDelegate)evt.getSource()).removePropertyChangeListener(this);
                  } catch (Throwable var5) {
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      GracefulStopTaskListener(RetireInfo x0, Object x1) {
         this(x0);
      }
   }

   private static class RetireInfo {
      private int state;
      private String appId;
      private String taskId;
      private boolean graceful;
      private boolean ignoreSessions;
      private int rmiGracePeriodSecs;
      private long retireTimeMillis;
      private boolean notificationDone;

      RetireInfo(int state, String appId, String taskId, boolean graceful, boolean ignoreSessions, int rmiGracePeriodSecs, long retireTimeMillis) {
         this.state = state;
         this.appId = appId;
         this.taskId = taskId;
         this.graceful = graceful;
         this.ignoreSessions = ignoreSessions;
         this.rmiGracePeriodSecs = rmiGracePeriodSecs;
         this.retireTimeMillis = retireTimeMillis;
         this.notificationDone = false;
      }

      boolean notificationDone() {
         return this.notificationDone;
      }

      void setNotificationDone(boolean notified) {
         this.notificationDone = notified;
      }

      public String toString() {
         return "RetireInfo[appId=" + this.appId + ",taskId=" + this.taskId + ",state=" + this.state + "]";
      }
   }
}
