package weblogic.connector.common;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.ResourceAdapter;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.exception.RACommonException;
import weblogic.connector.exception.RAException;
import weblogic.connector.extensions.Suspendable;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.utils.TypeUtils;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PlatformConstants;

public class Utils {
   public static void throwAsResourceException(String reason, Throwable t) throws ResourceException {
      ResourceException rex = new ResourceException(reason);
      rex.initCause(t);
      throw rex;
   }

   public static void throwAsApplicationServerInternalException(String reason, Exception ex) throws ApplicationServerInternalException {
      ApplicationServerInternalException rex = new ApplicationServerInternalException(reason);
      rex.initCause(ex);
      throw rex;
   }

   public static void setProperties(RAInstanceManager raIM, Object obj, Collection configPropertiesList, PropSetterTable propSetterTable) throws RACommonException {
      if (obj == null) {
         throw new AssertionError("obj == null");
      } else {
         Debug.enter("weblogic.connector.common.Utils", "setProperties( " + obj.getClass().getName() + " )");

         try {
            Debug.println("Iterate through all the config properties and (optionally) set them in the obj");
            String errorMsg = "";
            RACommonException raex = new RACommonException("Error when set properties on " + obj);
            Iterator var6 = configPropertiesList.iterator();

            while(var6.hasNext()) {
               ConfigPropInfo configProp = (ConfigPropInfo)var6.next();
               if (configProp.getValue() != null) {
                  try {
                     PropSetterTable.PropertyInjector injector = propSetterTable.getInjector(configProp.getName(), configProp.getType());
                     if (injector != null) {
                        invokeInjector(injector, obj, configProp);
                     }
                  } catch (RuntimeException var14) {
                     String exMsg = Debug.getExceptionPropertyValueTypeMismatch(configProp.getName(), configProp.getType(), configProp.getValue(), var14.toString());
                     errorMsg = errorMsg + exMsg + PlatformConstants.EOL;
                     raex.addError(var14);
                  } catch (RACommonException var15) {
                     errorMsg = errorMsg + var15.getBaseMessage() + PlatformConstants.EOL;
                     raex.addError(var15);
                  }
               }
            }

            if (errorMsg.length() > 0) {
               Debug.logConfigPropWarning(obj.getClass().getName(), raIM != null ? raIM.getModuleName() : "", errorMsg);
               throw raex;
            }
         } finally {
            Debug.exit("weblogic.connector.common.Utils", "setProperties() ");
         }

      }
   }

   private static void invokeInjector(PropSetterTable.PropertyInjector injector, Object obj, ConfigPropInfo configProp) throws RACommonException {
      Object valueObject = TypeUtils.getValueByType(configProp.getValue(), configProp.getType());
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         injector.inject(obj, valueObject);
      } catch (InvocationTargetException var8) {
         Throwable cause = var8.getCause();
         if (cause == null || !(cause instanceof PropertyVetoException)) {
            String exMsg = Debug.getExceptionInvokeSetter(configProp.getName());
            throw new RACommonException(exMsg, var8);
         }

         Debug.logPropertyVetoWarning(obj.getClass().getName(), configProp.getName(), configProp.getType(), configProp.getValue(), cause.toString());
      } catch (IllegalAccessException var9) {
         String exMsg = Debug.getExceptionInvokeSetter(configProp.getName());
         throw new RACommonException(exMsg, var9);
      }

   }

   public static RAException consolidateException(RAException oldException, Throwable newException) {
      if (newException != null) {
         if (oldException == null) {
            oldException = new RAException();
         }

         oldException.addError(newException);
         if (newException instanceof RAException) {
            RAException tmpNewException = (RAException)newException;
            Iterator exceptionsIterator = tmpNewException.getErrors();
            Throwable tmpNestedThrowable = null;

            while(exceptionsIterator.hasNext()) {
               tmpNestedThrowable = (Throwable)exceptionsIterator.next();
               consolidateException(oldException, tmpNestedThrowable);
            }
         }
      }

      return oldException;
   }

   public static int getManagementCount() {
      return ManagementCountThreadLocal.get();
   }

   public static void startManagement() {
      ManagementCountThreadLocal.increment();
   }

   public static void stopManagement() {
      ManagementCountThreadLocal.decrement();
   }

   public static boolean isRAVersionable(RAInstanceManager newRAIM, RAInstanceManager oldRAIM) {
      ResourceAdapter oldRA = null;
      if (oldRAIM != null) {
         oldRA = oldRAIM.getResourceAdapter();
      }

      ResourceAdapter newRA = newRAIM.getResourceAdapter();
      boolean isOldRAVersionable = false;
      boolean isNewRAVersionable = false;
      boolean versionable = false;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      isOldRAVersionable = oldRAIM == null || oldRA != null && oldRA instanceof Suspendable && oldRAIM.getAppContext().getRuntime().isEAR() && newRAIM.getAdapterLayer().supportsVersioning((Suspendable)oldRA, kernelId) && !oldRAIM.getRAInfo().isEnableAccessOutsideApp();
      isNewRAVersionable = newRA != null && newRA instanceof Suspendable && newRAIM.getAppContext().getRuntime().isEAR() && newRAIM.getAdapterLayer().supportsInit((Suspendable)newRA, kernelId) && newRAIM.getAdapterLayer().supportsVersioning((Suspendable)newRA, kernelId) && !newRAIM.getRAInfo().isEnableAccessOutsideApp();
      versionable = isOldRAVersionable && isNewRAVersionable;
      return versionable;
   }

   public static Class[] getInterfaces(Class cls) {
      List all = new ArrayList();
      addInterfacesRecusively(cls, all);
      Class[] result = new Class[all.size()];
      return (Class[])((Class[])all.toArray(result));
   }

   private static void addInterfacesRecusively(Class cls, List list) {
      Class[] interfaces = cls.getInterfaces();

      for(int i = 0; i < interfaces.length; ++i) {
         if (!list.contains(interfaces[i])) {
            list.add(interfaces[i]);
         }

         addInterfacesRecusively(interfaces[i], list);
      }

      Class superClass = cls.getSuperclass();
      if (superClass != null) {
         addInterfacesRecusively(superClass, list);
      }

   }

   public static void unregisterRuntimeMBean(RuntimeMBeanDelegate rt) {
      if (rt != null) {
         try {
            Debug.deployment("unregisterRuntimeMBean runtimeMbean " + rt.getName() + ": " + rt);
            rt.unregister();
         } catch (Throwable var2) {
            ConnectorLogger.logIgnoredErrorWhenUnregisterRuntimeMBean(rt.toString(), var2);
         }
      }

   }

   public static void assertNotNull(Object para, String paraName) {
      if (para == null) {
         Debug.throwAssertionError("Parameter '" + paraName + "' is null");
      }

   }

   public static void assertNotEmpty(String para, String paraName) {
      if (para == null || para.trim().length() == 0) {
         Debug.throwAssertionError("String '" + paraName + "' is null or empty");
      }

   }
}
