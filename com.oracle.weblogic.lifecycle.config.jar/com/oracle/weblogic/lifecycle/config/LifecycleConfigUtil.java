package com.oracle.weblogic.lifecycle.config;

import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LifecycleManagerConfigMBean;
import weblogic.management.configuration.LifecycleManagerEndPointMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LifecycleConfigUtil {
   private static Boolean isServer = false;
   private static DebugLogger debugLogger = null;
   private static long fileLockTimeout = 120000L;
   private static final String DEBUG_LIFECYCLE_MANAGER = "DebugLifecycleManager";
   private static final String DEBUG_LCM_PROP = "weblogic.debug.DebugLifecycleManager";

   public static boolean isLCMEnabled() {
      if (!isAppServer()) {
         return false;
      } else {
         LifecycleManagerConfigMBean configMBean = getLifecycleManagerConfigMBean();
         return configMBean != null ? configMBean.isEnabled() : false;
      }
   }

   public static boolean isAppServer() {
      return isServer;
   }

   public static LifecycleManagerConfigMBean getLifecycleManagerConfigMBean() {
      try {
         if (ManagementService.isRuntimeAccessInitialized()) {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
            return domainMBean.getLifecycleManagerConfig();
         }
      } catch (NoClassDefFoundError var2) {
      }

      return null;
   }

   public static LifecycleManagerEndPointMBean[] getActiveEndPoints() {
      if (!isAppServer()) {
         return null;
      } else {
         LifecycleManagerConfigMBean configMBean = getLifecycleManagerConfigMBean();
         return configMBean != null ? configMBean.getEndPoints() : new LifecycleManagerEndPointMBean[0];
      }
   }

   public static String getResourceURLEndPoint(LifecycleManagerEndPointMBean endPoint, String resourceURI) {
      if (endPoint == null) {
         throw new IllegalArgumentException("No LifecycleManagerEndPointMBean instance provided");
      } else if (resourceURI != null && resourceURI.length() != 0) {
         URL endPointUrl = null;

         try {
            endPointUrl = new URL(endPoint.getURL());
         } catch (Exception var4) {
            throw new RuntimeException("Badly formed URL in endpoint", var4);
         }

         StringBuffer result = new StringBuffer();
         result.append(endPointUrl.getProtocol() + "://" + endPointUrl.getHost() + ":" + endPointUrl.getPort());
         if (!resourceURI.startsWith("/")) {
            result.append("/management/lifecycle/latest/");
         }

         result.append(resourceURI);
         return result.toString();
      } else {
         throw new IllegalArgumentException("No resourceURI provided");
      }
   }

   public static long getFileLockTimeout() {
      if (isAppServer()) {
         LifecycleManagerConfigMBean configMBean = getLifecycleManagerConfigMBean();
         if (configMBean != null) {
            fileLockTimeout = configMBean.getConfigFileLockTimeout();
         }
      }

      return fileLockTimeout;
   }

   public static boolean isDebugEnabled() {
      boolean isDebugEnabled;
      if (isAppServer()) {
         if (debugLogger != null) {
            isDebugEnabled = debugLogger.isDebugEnabled();
         } else {
            isDebugEnabled = false;
         }
      } else {
         isDebugEnabled = Boolean.getBoolean("weblogic.debug.DebugLifecycleManager");
      }

      return isDebugEnabled;
   }

   public static void debug(String msg) {
      debug(msg, (Throwable)null);
   }

   public static void debug(String msg, Throwable th) {
      if (msg != null) {
         if (isAppServer()) {
            if (debugLogger != null) {
               if (th == null) {
                  debugLogger.debug(msg);
               } else {
                  debugLogger.debug(msg, th);
               }
            }
         } else {
            System.out.println(msg);
            if (th != null) {
               System.out.println(th.getMessage());
               th.printStackTrace();
            }
         }
      }

   }

   public static String getLifecycleConfigAsString() {
      LifecycleManagerConfigMBean lcmConfig = getLifecycleManagerConfigMBean();
      StringBuffer sb = new StringBuffer();
      if (lcmConfig != null) {
         sb.append("Enabled:").append(lcmConfig.isEnabled()).append(" DeploymentType:").append(lcmConfig.getDeploymentType()).append(" PersistenceType:").append(lcmConfig.getPersistenceType()).append(" OutOfBandEnabled:").append(lcmConfig.isOutOfBandEnabled()).append(" LCMInitiatedConnectTimeout:").append(lcmConfig.getLCMInitiatedConnectTimeout()).append(" LCMInitiatedReadTimeout:").append(lcmConfig.getLCMInitiatedReadTimeout()).append(" LCMInitiatedConnectTimeoutForElasticity:").append(lcmConfig.getLCMInitiatedConnectTimeoutForElasticity()).append(" LCMInitiatedReadTimeoutForElasticity:").append(lcmConfig.getLCMInitiatedReadTimeoutForElasticity()).append(" ConfigFileLockTimeout:").append(lcmConfig.getConfigFileLockTimeout()).append(" PeriodicSyncInterval:").append(lcmConfig.getPeriodicSyncInterval()).append(" ServerRuntimeTimeout:").append(lcmConfig.getServerRuntimeTimeout()).append(" ServerReadyTimeout:").append(lcmConfig.getServerReadyTimeout()).append(" DataSourceName:").append(lcmConfig.getDataSourceName());
         LifecycleManagerEndPointMBean[] endPointMBeans = lcmConfig.getEndPoints();
         LifecycleManagerEndPointMBean[] configuredEndPoints;
         int var5;
         if (endPointMBeans != null) {
            sb.append(" Number of end points:").append(endPointMBeans.length);
            configuredEndPoints = endPointMBeans;
            int var4 = endPointMBeans.length;

            for(var5 = 0; var5 < var4; ++var5) {
               LifecycleManagerEndPointMBean endPointMBean = configuredEndPoints[var5];
               if (endPointMBean == null) {
                  sb.append(" endPointMBean: null");
               } else {
                  sb.append(" Endpoint: " + endPointMBean.getURL());
               }
            }
         } else {
            sb.append(" Endpoints : null");
         }

         configuredEndPoints = lcmConfig.getConfiguredEndPoints();
         if (configuredEndPoints != null) {
            sb.append(" Number of configured end points:").append(configuredEndPoints.length);
            LifecycleManagerEndPointMBean[] var8 = configuredEndPoints;
            var5 = configuredEndPoints.length;

            for(int var9 = 0; var9 < var5; ++var9) {
               LifecycleManagerEndPointMBean configuredEndPointMBean = var8[var9];
               if (configuredEndPointMBean == null) {
                  sb.append(" configuredEndPointMBean: null");
               } else {
                  sb.append(" ConfiguredEndpoint: " + configuredEndPointMBean.getURL());
               }
            }
         } else {
            sb.append(" ConfiguredEndpoints : null");
         }
      } else {
         sb.append(" LifecycleManagerConfigMBean : null");
      }

      return sb.toString();
   }

   static {
      try {
         Class myClass = Class.forName("weblogic.kernel.KernelStatus");
         Method myMethod = myClass.getMethod("isServer");
         Object returnObject = myMethod.invoke((Object)null);
         isServer = (Boolean)returnObject;
         if (isServer) {
            debugLogger = DebugLogger.getDebugLogger("DebugLifecycleManager");
         }
      } catch (Exception var3) {
      }

   }
}
