package weblogic.servlet.internal.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;

public class GracefulShutdownHelper {
   private static int maxPendingSessionTimeout;
   private static final long PENDING_SESSION_INTERVAL = 30000L;
   private static final String PRODUCTION_TO_ADMIN_LOCK_PREFIX = "weblogic.webapp.ProductionToAdminLock";
   private static WebServerRegistry registry = WebServerRegistry.getInstance();

   public static void waitForPendingSessions() {
      waitForPendingSessions(false);
   }

   public static void waitForPendingSessions(boolean allSessions) {
      waitForPendingSessions(allSessions, (String)null, (String)((String)null));
   }

   public static void waitForPendingSessions(boolean allSessions, String partitionName, Set rgs) {
      Map m = getActiveSessions(allSessions, partitionName, rgs);
      if (m.size() > 0) {
         String[] msg = getPendingSessionsMessage(m);
         int timeout = getPendingSessionTimeout(maxPendingSessionTimeout);
         HTTPLogger.logSessionListDuringSuspend(msg[0], msg[1]);
         HTTPLogger.logInitialSessionsDuringSuspend(timeout);
         startSessionChecker(30000L, allSessions, partitionName, rgs);
      }

      HTTPLogger.logPrepareToSuspendComplete();
   }

   public static void waitForPendingSessions(boolean allSessions, String partitionName, String resourceGroup) {
      if (resourceGroup != null) {
         Set rgs = new HashSet();
         rgs.add(resourceGroup);
         waitForPendingSessions(allSessions, partitionName, (Set)rgs);
      } else {
         waitForPendingSessions(allSessions, partitionName, (Set)null);
      }

   }

   private static Map getActiveSessions(boolean allSessions, String partitionName, Set resourceGroups) {
      Map sessionTable = new HashMap();
      Iterator i = registry.getHttpServers().iterator();

      while(true) {
         label58:
         while(true) {
            WebAppServletContext[] webapps;
            do {
               if (!i.hasNext()) {
                  return sessionTable;
               }

               HttpServer httper = (HttpServer)i.next();
               webapps = httper.getServletContextManager().getAllContexts();
            } while(webapps == null);

            if (resourceGroups != null) {
               Iterator var15 = resourceGroups.iterator();

               while(true) {
                  String resourceGroup;
                  do {
                     if (!var15.hasNext()) {
                        continue label58;
                     }

                     resourceGroup = (String)var15.next();
                  } while(resourceGroup == null);

                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("GracefulShutdown getActiveSessions start with resourceGroup[" + resourceGroup + "] for partition [" + partitionName + "]");
                  }

                  WebAppServletContext[] var17 = webapps;
                  int var18 = webapps.length;

                  for(int var11 = 0; var11 < var18; ++var11) {
                     WebAppServletContext context = var17[var11];
                     if (context != null && context.getApplicationContext() != null && context.getApplicationContext().getResourceGroupMBean() != null) {
                        ResourceGroupMBean rgMbean = context.getApplicationContext().getResourceGroupMBean();
                        if (HTTPDebugLogger.isEnabled()) {
                           HTTPDebugLogger.debug("GracefulShutdown getActiveSessions on context[" + context.getAppDisplayName() + "] ResourceGroupMBean[" + rgMbean.getName() + "] for partition [" + partitionName + "]");
                        }

                        String contextPartitionName = context.getApplicationContext().getPartitionName();
                        if ((partitionName == null && contextPartitionName == null || partitionName != null && partitionName.equals(contextPartitionName)) && resourceGroup.equals(context.getApplicationContext().getResourceGroupMBean().getName())) {
                           maxPendingSessionTimeout = updateActiveSessionTable(context, sessionTable, maxPendingSessionTimeout, allSessions);
                        }
                     }
                  }
               }
            } else {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("GracefulShutdown getActiveSessions with no Partition & ResourceGroup defined!");
               }

               WebAppServletContext[] var7 = webapps;
               int var8 = webapps.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  WebAppServletContext context = var7[var9];
                  maxPendingSessionTimeout = updateActiveSessionTable(context, sessionTable, maxPendingSessionTimeout, allSessions);
               }
            }
         }
      }
   }

   private static int getPendingSessionTimeout(int inMaxPendingSessionTimeout) {
      int timeout = inMaxPendingSessionTimeout;
      ServerMBean servermbean = registry.getServerMBean();
      if (servermbean.getGracefulShutdownTimeout() > 0) {
         timeout = Math.min(servermbean.getGracefulShutdownTimeout(), inMaxPendingSessionTimeout);
      }

      return timeout;
   }

   private static int updateActiveSessionTable(WebAppServletContext context, Map sessionTable, int inMaxPendingSessionTimeout, boolean waitForAllSessions) {
      if (context.isInternalApp()) {
         return inMaxPendingSessionTimeout;
      } else {
         SessionContext sessionContext = context.getSessionContext();
         int pendingSessionsCount = waitForAllSessions ? sessionContext.getCurrOpenSessionsCount() : sessionContext.getNonPersistedSessionCount();
         if (context.getVersionId() != null && waitForAllSessions) {
            pendingSessionsCount += registry.getManagementProvider().getWebServicesConversationSessionCount(context);
         }

         if (pendingSessionsCount > 0) {
            sessionTable.put(context.getContextPath(), new Long((long)pendingSessionsCount));
            return Math.max(inMaxPendingSessionTimeout, sessionContext.getConfigMgr().getSessionTimeoutSecs());
         } else {
            return inMaxPendingSessionTimeout;
         }
      }
   }

   private static String[] getPendingSessionsMessage(Map m) {
      StringBuffer msg = new StringBuffer();
      StringBuffer count = new StringBuffer();
      Iterator i = m.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         msg.append(e.getKey());
         count.append(e.getValue());
         if (i.hasNext()) {
            msg.append(", ");
            count.append(", ");
         }
      }

      return new String[]{msg.toString(), count.toString()};
   }

   private static void startSessionChecker(long periodLength, boolean allSessions, String partitionName, Set resourceGroups) {
      if (periodLength > 0L) {
         SessionChecker sessionChecker = new SessionChecker(periodLength, allSessions, partitionName, resourceGroups);
         sessionChecker.start();
      }
   }

   public static void waitForPendingSessions(String appId, WebAppModule webAppModule, boolean ignoreSessions) {
      if (!ignoreSessions) {
         Map m = new HashMap();
         int maxSessionTimeout = getActiveSessions(webAppModule, m);
         String appDisplayName = ApplicationVersionUtils.getDisplayName(appId);
         if (m.size() > 0) {
            String[] msg = getPendingSessionsMessage(m);
            int timeout = getPendingSessionTimeout(maxSessionTimeout);
            HTTPLogger.logSessionListDuringGracefulProductionToAdmin(appDisplayName, webAppModule.getName(), msg[0], msg[1]);
            HTTPLogger.logInitialSessionsDuringGracefulProductionToAdmin(appDisplayName, webAppModule.getName(), timeout);
            suspendContext(webAppModule);
            startSessionChecker(30000L, appId, webAppModule);
         } else {
            HTTPLogger.logGracefulProductionToAdminComplete(appDisplayName, webAppModule.getName());
         }

      }
   }

   private static int getActiveSessions(WebAppModule module, Map sessionTable) {
      int maxSessionTimeout = 0;
      Iterator var3 = module.getAllContexts().iterator();

      while(var3.hasNext()) {
         WebAppServletContext context = (WebAppServletContext)var3.next();
         if (context != null) {
            maxSessionTimeout = updateActiveSessionTable(context, sessionTable, maxSessionTimeout, true);
         }
      }

      return maxSessionTimeout;
   }

   private static void suspendContext(WebAppModule module) {
      Iterator var1 = module.getAllContexts().iterator();

      while(var1.hasNext()) {
         WebAppServletContext context = (WebAppServletContext)var1.next();
         if (context != null) {
            context.setContextPhase(WebAppServletContext.ContextPhase.SUSPENDING);
         }
      }

   }

   private static void startSessionChecker(long periodLength, String appId, WebAppModule webAppModule) {
      if (periodLength > 0L) {
         SessionChecker sessionChecker = new SessionChecker(periodLength, appId, webAppModule);
         sessionChecker.start();
      }
   }

   public static void notifyGracefulProductionToAdmin(String appId, WebAppModule webAppModule) {
      Object lock = getProductionToAdminLock(appId, webAppModule);
      synchronized(lock) {
         lock.notify();
      }
   }

   private static Object getProductionToAdminLock(String appId, WebAppModule webAppModule) {
      return webAppModule == null ? "weblogic.webapp.ProductionToAdminLock" : ("weblogic.webapp.ProductionToAdminLock." + appId + "." + webAppModule.getName()).intern();
   }

   private static final class SessionChecker {
      private final long period;
      private long duration;
      private long nextLogInterval;
      private String appId;
      private WebAppModule webAppModule;
      private boolean allSessions = false;
      private String partitionName = null;
      private Set resourceGroups = null;

      SessionChecker(long period, boolean allSessions, String partitionName, Set resourceGroups) {
         this.period = period;
         this.allSessions = allSessions;
         this.partitionName = partitionName;
         this.resourceGroups = resourceGroups;
      }

      SessionChecker(long period, String appId, WebAppModule webAppModule) {
         this.period = period;
         this.appId = appId;
         this.webAppModule = webAppModule;
      }

      private final void start() {
         while(true) {
            Map m;
            if ((m = this.getActiveSessions()).size() > 0 && (this.webAppModule == null || !this.webAppModule.isSuspended())) {
               if (this.shouldLog()) {
                  String[] msg = GracefulShutdownHelper.getPendingSessionsMessage(m);
                  this.logSessionsDuringSuspend(this.duration, msg[0]);
               }

               this.duration += this.period / 1000L;
               Object lock = GracefulShutdownHelper.getProductionToAdminLock(this.appId, this.webAppModule);
               synchronized(lock) {
                  try {
                     lock.wait(this.period);
                     continue;
                  } catch (InterruptedException var6) {
                  }
               }
            }

            this.logPrepareToSuspendComplete();
            return;
         }
      }

      private Map getActiveSessions() {
         if (this.webAppModule == null) {
            return GracefulShutdownHelper.getActiveSessions(this.allSessions, this.partitionName, this.resourceGroups);
         } else {
            Map m = new HashMap();
            GracefulShutdownHelper.getActiveSessions(this.webAppModule, m);
            return m;
         }
      }

      private boolean shouldLog() {
         if (this.nextLogInterval > 0L && this.duration < this.nextLogInterval) {
            return false;
         } else {
            this.nextLogInterval = this.duration * 2L;
            return true;
         }
      }

      private void logSessionsDuringSuspend(long duration, String msg) {
         int tm = (int)(duration / 60L);
         if (this.webAppModule == null) {
            HTTPLogger.logSessionsDuringSuspend(tm, msg);
         } else {
            HTTPLogger.logSessionsDuringGracefulProductionToAdmin(ApplicationVersionUtils.getDisplayName(this.appId), this.webAppModule.getName(), tm, msg);
         }

      }

      private void logPrepareToSuspendComplete() {
         if (this.webAppModule == null) {
            HTTPLogger.logPrepareToSuspendComplete();
         } else if (this.webAppModule.isSuspended()) {
            HTTPLogger.logGracefulProductionToAdminInterrupted(ApplicationVersionUtils.getDisplayName(this.appId), this.webAppModule.getName());
         } else {
            HTTPLogger.logGracefulProductionToAdminComplete(ApplicationVersionUtils.getDisplayName(this.appId), this.webAppModule.getName());
         }

      }
   }
}
