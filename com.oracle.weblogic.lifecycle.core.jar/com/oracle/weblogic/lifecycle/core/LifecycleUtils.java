package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.config.LifecycleConfigUtil;
import java.lang.reflect.Method;
import java.security.AccessController;
import weblogic.management.configuration.LifecycleManagerConfigMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LifecycleUtils {
   static final String WLSTYPE = "wls";
   static final String OTDTYPE = "otd";
   static final String DB = "database";
   static final String OOB = "oob";
   static final String SOURCE = "source";
   static final String OP_TYPE = "operationtype";
   static final String PHASE = "phase";
   static final String PARTITION_SET = "partitionSet";
   static final String ORIGINATING_PARTITION = "originatingPartition";
   static final String NAME = "name";
   static final String TYPE = "type";
   static final String HOSTNAME = "hostname";
   static final String PORT = "port";
   static final String ID = "id";
   static final String DOMAIN = "DOMAIN";
   static final String OTDLIST = "otdlist";
   static final String MANAGEDSERVER = "managedserver";
   static final String ADDRESS = "address";
   static final String IS_DELETE_RUNTIME = "isDeleteRuntime";
   public static final String SOURCE_WLS = "wls";
   private static final int DEFAULT_PERIODIC_SYNC_INTERVAL_IN_HOURS = 2;
   private static final long DEFAULT_PROPAGATION_ACTIVATION_TIMEOUT = 180000L;
   private static final long DEFAULT_SERVER_RUNTIME_TIMEOUT = 600000L;

   public static boolean isAppServer() {
      return LifecycleConfigUtil.isAppServer();
   }

   public static boolean isDebugEnabled() {
      return LifecycleConfigUtil.isDebugEnabled();
   }

   public static void debug(String msg) {
      LifecycleConfigUtil.debug(msg, (Throwable)null);
   }

   public static void debug(String msg, Throwable th) {
      LifecycleConfigUtil.debug(msg, th);
   }

   static Boolean isAdmin() {
      if (isAppServer()) {
         try {
            AuthenticatedSubject notFinal = null;

            try {
               notFinal = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            } catch (Exception var9) {
               notFinal = null;
            }

            if (notFinal != null) {
               Class mgmtService = Class.forName("weblogic.management.provider.ManagementService");
               Class authSubj = Class.forName("weblogic.security.acl.internal.AuthenticatedSubject");
               Method rtAccess = mgmtService.getMethod("getRuntimeAccess", authSubj);
               Object returnObject = rtAccess.invoke((Object)null, notFinal);
               Class rtAccessClass = Class.forName("weblogic.management.provider.RuntimeAccess");
               Method isAdminMethod = rtAccessClass.getMethod("isAdminServer");
               Object returnObject1 = isAdminMethod.invoke(returnObject);
               Boolean isAdmin = (Boolean)returnObject1;
               return isAdmin;
            }
         } catch (Exception var10) {
            if (isDebugEnabled()) {
               debug("Caught Exception determining isAdmin", var10);
            }

            return false;
         }
      }

      return false;
   }

   public static int getPeriodicSyncInterval() {
      if (!isAppServer()) {
         return -1;
      } else {
         LifecycleManagerConfigMBean configMBean = LifecycleConfigUtil.getLifecycleManagerConfigMBean();
         return configMBean != null ? configMBean.getPeriodicSyncInterval() : 2;
      }
   }

   public static long getPropagationActivateTimeout() {
      if (isAppServer()) {
         LifecycleManagerConfigMBean configMBean = LifecycleConfigUtil.getLifecycleManagerConfigMBean();
         if (configMBean != null) {
            return configMBean.getPropagationActivateTimeout();
         }
      }

      return 180000L;
   }

   public static long getServerRuntimeTimeout() {
      if (isAppServer()) {
         LifecycleManagerConfigMBean configMBean = LifecycleConfigUtil.getLifecycleManagerConfigMBean();
         if (configMBean != null) {
            return configMBean.getServerRuntimeTimeout();
         }
      }

      return 600000L;
   }
}
