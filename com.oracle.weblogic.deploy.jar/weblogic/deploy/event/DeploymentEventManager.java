package weblogic.deploy.event;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;
import java.util.List;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class DeploymentEventManager {
   private static Object deployEventListenersLock = "DeployEventListeners";
   private static ArrayList deployEventListeners = new ArrayList();
   private static Object vetoableDeployListenersLock = "VetoDeployListeners";
   private static ArrayList vetoableDeployListeners = new ArrayList();
   private static Object vetoableSystemResourceListenersLock = "VetoSystemResourceListeners";
   private static ArrayList vetoableSystemResourceListeners = new ArrayList();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void addDeploymentEventListener(DeploymentEventListener listener) {
      internalAddDeploymentEventListener(listener, false);
   }

   public static void addDeploymentEventListener(DeploymentEventListener listener, boolean adminServerOnly) {
      internalAddDeploymentEventListener(listener, adminServerOnly);
   }

   public static void addDeploymentEventListener(String appName, DeploymentEventListener listener) throws DeploymentException {
      internalAddDeploymentEventListener(new DeploymentEventListenerProxy(appName, listener), false);
   }

   private static void internalAddDeploymentEventListener(EventListener listener, boolean adminServerOnly) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Add DeploymentEventListener " + listener);
      }

      if (listener != null) {
         if (!adminServerOnly || ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            synchronized(deployEventListenersLock) {
               ArrayList curList = new ArrayList(deployEventListeners);
               curList.add(listener);
               deployEventListeners = curList;
            }
         }
      }
   }

   public static boolean removeDeploymentEventListener(DeploymentEventListener listener) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Remove DeploymentEventListener " + listener);
      }

      if (listener == null) {
         return false;
      } else {
         synchronized(deployEventListenersLock) {
            if (deployEventListeners.size() == 0) {
               return false;
            } else {
               ArrayList curList = new ArrayList(deployEventListeners);
               boolean rtn = removeListener(listener, curList);
               deployEventListeners = curList;
               return rtn;
            }
         }
      }
   }

   public static void sendDeploymentEvent(DeploymentEvent evt) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Send deployment event: " + evt);
      }

      if (evt != null) {
         ArrayList curList;
         synchronized(deployEventListenersLock) {
            curList = deployEventListeners;
         }

         BaseDeploymentEvent.ListenerAdapter adapter = evt.getListenerAdapter();
         Throwable ths = null;
         Iterator iter = curList.iterator();

         while(iter.hasNext()) {
            EventListener listener = (EventListener)iter.next();
            if (isEventEnabled(listener, evt)) {
               try {
                  adapter.notifyListener(getListener(listener), evt);
               } catch (Throwable var8) {
                  Throwable curTh = new Throwable(var8.toString());
                  curTh.setStackTrace(var8.getStackTrace());
                  curTh.initCause(ths);
                  ths = curTh;
               }
            }
         }

         if (ths != null) {
            DeployerRuntimeLogger.logSendDeploymentEventError(ApplicationVersionUtils.getDisplayName(evt.getAppDeployment()), evt.getType().getName(), ths);
         }

      }
   }

   public static void addVetoableDeploymentListener(VetoableDeploymentListener listener) throws DeploymentException {
      internalAddVetoableDeploymentListener(listener);
   }

   public static void addVetoableDeploymentListener(String appName, VetoableDeploymentListener listener) throws DeploymentException {
      internalAddVetoableDeploymentListener(new DeploymentEventListenerProxy(appName, listener));
   }

   private static void internalAddVetoableDeploymentListener(EventListener listener) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Add VetoableDeploymentListener: " + listener);
      }

      if (listener != null) {
         synchronized(vetoableDeployListenersLock) {
            ArrayList curList = new ArrayList(vetoableDeployListeners);
            curList.add(listener);
            vetoableDeployListeners = curList;
         }
      }
   }

   public static boolean removeVetoableDeploymentListener(VetoableDeploymentListener listener) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Remove VetoableDeploymentListener: " + listener);
      }

      if (listener == null) {
         return false;
      } else {
         synchronized(vetoableDeployListenersLock) {
            if (vetoableDeployListeners.size() == 0) {
               return false;
            } else {
               ArrayList curList = new ArrayList(vetoableDeployListeners);
               boolean rtn = removeListener(listener, curList);
               vetoableDeployListeners = curList;
               return rtn;
            }
         }
      }
   }

   public static void addVetoableSystemResourceDeploymentListener(VetoableDeploymentListener listener) throws DeploymentException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Add VetoableSystemResourceDeploymentListener: " + listener);
      }

      if (listener != null) {
         synchronized(vetoableSystemResourceListenersLock) {
            ArrayList curList = new ArrayList(vetoableSystemResourceListeners);
            curList.add(listener);
            vetoableSystemResourceListeners = curList;
         }
      }
   }

   public static boolean removeVetoableSystemResourceDeploymentListener(VetoableDeploymentListener listener) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Remove VetoableSystemResourceDeploymentListener: " + listener);
      }

      if (listener == null) {
         return false;
      } else {
         synchronized(vetoableSystemResourceListenersLock) {
            if (vetoableSystemResourceListeners.size() == 0) {
               return false;
            } else {
               ArrayList curList = new ArrayList(vetoableSystemResourceListeners);
               boolean rtn = removeListener(listener, curList);
               vetoableSystemResourceListeners = curList;
               return rtn;
            }
         }
      }
   }

   public static void sendVetoableDeploymentEvent(VetoableDeploymentEvent evt) throws DeploymentException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Send vetoable deploy event: " + evt);
      }

      if (evt != null) {
         List curList = null;
         BasicDeploymentMBean depMBean = evt.getAppDeployment();
         if (depMBean != null) {
            synchronized(vetoableDeployListenersLock) {
               curList = vetoableDeployListeners;
            }
         } else {
            depMBean = evt.getSystemResource();
            if (depMBean != null) {
               synchronized(vetoableSystemResourceListenersLock) {
                  curList = vetoableSystemResourceListeners;
               }
            }
         }

         if (curList != null) {
            BaseDeploymentEvent.ListenerAdapter adapter = evt.getListenerAdapter();
            Throwable ths = null;
            Iterator iter = curList.iterator();

            while(iter.hasNext()) {
               EventListener listener = (EventListener)iter.next();
               if (isEventEnabled(listener, evt)) {
                  try {
                     adapter.notifyListener(getListener(listener), evt);
                  } catch (DeploymentVetoException var9) {
                     throw new DeploymentException(var9.getMessage(), var9);
                  } catch (Throwable var10) {
                     Throwable curTh = new Throwable(var10.toString());
                     curTh.setStackTrace(var10.getStackTrace());
                     curTh.initCause(ths);
                     ths = curTh;
                  }
               }
            }

            if (ths != null) {
               DeployerRuntimeLogger.logSendVetoableDeployEventError(ApplicationVersionUtils.getDisplayName((BasicDeploymentMBean)depMBean), ths);
            }

         }
      }
   }

   private static boolean removeListener(EventListener listener, ArrayList listeners) {
      boolean rtn = false;
      Iterator iter = listeners.iterator();

      while(iter.hasNext()) {
         EventListener curListener = (EventListener)iter.next();
         if (curListener instanceof EventListenerProxy) {
            curListener = ((EventListenerProxy)curListener).getListener();
         }

         if (curListener == listener) {
            iter.remove();
            rtn = true;
            break;
         }
      }

      return rtn;
   }

   private static boolean isEventEnabled(EventListener listener, BaseDeploymentEvent evt) {
      if (!(listener instanceof DeploymentEventListenerProxy)) {
         return true;
      } else {
         String appName = ((DeploymentEventListenerProxy)listener).getAppName();
         AppDeploymentMBean appMBean = evt.getAppDeployment();
         return appMBean != null && (appMBean.getApplicationName().equals(appName) || appMBean.getName().equals(appName));
      }
   }

   private static EventListener getListener(EventListener listener) {
      return listener instanceof EventListenerProxy ? ((EventListenerProxy)listener).getListener() : listener;
   }
}
