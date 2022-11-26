package weblogic.management.jmx.modelmbean;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.util.concurrent.ExecutionException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.OperationsException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.provider.Service;
import weblogic.management.visibility.utils.MBeanNameUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLSModelMBeanFactory {
   private static DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugJMXCore");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static WLSModelMBean registerWLSModelMBean(Object bean, final ObjectName objectName, final WLSModelMBeanContext ctx) throws OperationsException, MBeanRegistrationException {
      final WLSModelMBean modelMBean = new WLSModelMBean(bean, ctx);
      String partitionToRun;
      String appNameToRun;
      String appVersionToRun;
      String moduleNameToRun;
      String componentNameToRun;
      if (bean instanceof Service) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
         partitionToRun = cic.getPartitionName();
         appNameToRun = cic.getApplicationName();
         appVersionToRun = cic.getApplicationVersion();
         moduleNameToRun = cic.getModuleName();
         componentNameToRun = cic.getComponentName();
      } else {
         partitionToRun = MBeanNameUtil.findPartitionName(objectName);
         appNameToRun = null;
         appVersionToRun = null;
         moduleNameToRun = null;
         componentNameToRun = null;
      }

      if (ctx.getMBeanServer() instanceof WLSMBeanServer) {
         try {
            ComponentInvocationContextManager.runAs(kernelId, ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext(partitionToRun, appNameToRun, appVersionToRun, moduleNameToRun, componentNameToRun), new Runnable() {
               public void run() {
                  try {
                     ctx.getMBeanServer().registerMBean(modelMBean, objectName);
                  } catch (Exception var2) {
                     throw new RuntimeException(var2);
                  }
               }
            });
         } catch (ExecutionException var10) {
            throw new RuntimeException(var10.getCause());
         }
      } else {
         ctx.getMBeanServer().registerMBean(modelMBean, objectName);
      }

      ctx.getNameManager().registerObject(objectName, bean);
      return modelMBean;
   }

   static boolean checkIsProxy(Object bean) {
      if (bean == null) {
         return false;
      } else {
         return Proxy.isProxyClass(bean.getClass());
      }
   }

   static boolean checkIsClone(Object instance) {
      return instance != null && instance instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)instance)._isClone();
   }

   public static void registerWLSModelMBean(Object bean, WLSModelMBeanContext ctx) {
      registerWLSModelMBean(bean, (WLSModelMBeanContext)ctx, (WLSModelMBeanInstanceContext)null);
   }

   private static void registerWLSModelMBean(Object bean, WLSModelMBeanContext ctx, WLSModelMBeanInstanceContext instanceContext) {
      if (checkIsProxy(bean)) {
         throw new AssertionError("Attempt to register Proxy MBean in MBeanServer" + bean);
      } else if (checkIsClone(bean)) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("registerWLSModelMBean: skipping the clone object " + bean);
         }

      } else {
         ObjectNameManager nameManager = ctx.getNameManager();
         MBeanServer mbeanServer = ctx.getMBeanServer();
         if (!ctx.isFilteringEnabled() || !nameManager.isFiltered(bean)) {
            ObjectName beanObjectName = null;
            WLSModelMBean beanMBean = null;

            try {
               beanObjectName = nameManager.lookupRegisteredObjectName(bean);
               if (beanObjectName == null) {
                  beanObjectName = nameManager.buildObjectName(bean, instanceContext);
                  if (beanObjectName == null) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Skipping registerWLSModelMBean for unsupported implementation type.: " + beanObjectName + " for " + bean);
                     }

                     return;
                  }
               }

               if (mbeanServer.isRegistered(beanObjectName)) {
                  return;
               }

               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("registerWLSModelMBean: " + beanObjectName + " for " + bean);
                  DEBUG.debug("The context read-only flag is " + ctx.isReadOnly());
               }

               beanMBean = registerWLSModelMBean(bean, beanObjectName, ctx);
            } catch (MBeanVersionMismatchException var21) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Skipping registerWLSModelMBean for out of date mbean.: " + beanObjectName + " for " + bean);
               }

               return;
            } catch (InstanceAlreadyExistsException var22) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Ignoring register failure if bean already registered.: " + beanObjectName + " for " + bean);
               }

               return;
            } catch (RuntimeException var23) {
               if (var23.getCause() instanceof InstanceAlreadyExistsException) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Ignoring register failure if bean already registered.: " + beanObjectName + " for " + bean);
                  }

                  return;
               }
            } catch (Throwable var24) {
               JMXLogger.logRegistrationFailed(beanObjectName, var24);
               return;
            }

            if (ctx.isRecurse()) {
               WLSModelMBeanContext parentCtx = beanMBean.getContext();
               BeanInfo beanInfo = beanMBean.getBeanInfo();
               PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

               for(int i = 0; i < properties.length; ++i) {
                  PropertyDescriptor property = properties[i];
                  Class propertyClass = property.getPropertyType();
                  if (ctx.isContainedBean(property)) {
                     Method method;
                     if (propertyClass.isArray()) {
                        method = property.getReadMethod();

                        try {
                           Object[] beans = (Object[])((Object[])method.invoke(bean, (Object[])null));
                           if (beans != null && beans.length != 0) {
                              boolean hasInstanceContext = bean instanceof AbstractDescriptorBean;

                              for(int j = 0; j < beans.length; ++j) {
                                 Object child = beans[j];
                                 WLSModelMBeanInstanceContext instanceCtx = hasInstanceContext ? new WLSModelMBeanInstanceContext(j) : null;
                                 registerWLSModelMBean(child, parentCtx, instanceCtx);
                              }
                           }
                        } catch (Throwable var20) {
                           JMXLogger.logRegistrationFailedForProperty(beanObjectName, property.getName(), var20);
                        }
                     } else {
                        method = property.getReadMethod();

                        try {
                           Object child = method.invoke(bean, (Object[])null);
                           if (child != null) {
                              registerWLSModelMBean(child, parentCtx);
                           }
                        } catch (Throwable var19) {
                           JMXLogger.logRegistrationFailedForProperty(beanObjectName, property.getName(), var19);
                        }
                     }
                  }
               }

            }
         }
      }
   }

   public static void unregisterWLSModelMBean(Object bean, WLSModelMBeanContext ctx) {
      if (checkIsProxy(bean)) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("registerWLSModelMBean: skipping proxy object " + bean);
         }

      } else if (checkIsClone(bean)) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("unregisterWLSModelMBean: skipping the clone object " + bean);
         }

      } else {
         ObjectNameManager nameManager = ctx.getNameManager();
         MBeanServer mbeanServer = ctx.getMBeanServer();
         if (!ctx.isFilteringEnabled() || !nameManager.isFiltered(bean)) {
            ObjectName beanObjectName = nameManager.lookupObjectName(bean);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("unregisterWLSModelMBean: " + beanObjectName + " for " + bean);
            }

            if (beanObjectName != null && mbeanServer.isRegistered(beanObjectName)) {
               ctx.unregister(bean);
               if (ctx.isRecurse()) {
                  ModelMBeanInfoWrapper modelMBeanInfoWrapper;
                  try {
                     modelMBeanInfoWrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInstance(bean, ctx.isReadOnly(), ctx.getVersion(), ctx.getNameManager());
                  } catch (OperationsException var16) {
                     JMXLogger.logUnregisterFailed(beanObjectName, var16);
                     return;
                  }

                  BeanInfo beanInfo = modelMBeanInfoWrapper.getBeanInfo();
                  PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

                  for(int i = 0; i < properties.length; ++i) {
                     PropertyDescriptor property = properties[i];
                     if (ctx.isContainedBean(property)) {
                        Class propertyClass = property.getPropertyType();
                        Method method;
                        if (propertyClass.isArray()) {
                           method = property.getReadMethod();

                           try {
                              Object[] beans = (Object[])((Object[])method.invoke(bean, (Object[])null));
                              if (beans != null && beans.length != 0) {
                                 for(int j = 0; j < beans.length; ++j) {
                                    Object child = beans[j];
                                    unregisterWLSModelMBean(child, ctx);
                                 }
                              }
                           } catch (Throwable var17) {
                              JMXLogger.logUnregisterFailedForProperty(beanObjectName, property.getName(), var17);
                           }
                        } else {
                           method = property.getReadMethod();

                           try {
                              Object child = method.invoke(bean, (Object[])null);
                              if (child != null) {
                                 unregisterWLSModelMBean(child, ctx);
                              }
                           } catch (Throwable var15) {
                              JMXLogger.logUnregisterFailedForProperty(beanObjectName, property.getName(), var15);
                           }
                        }
                     }
                  }

               }
            } else {
               if (beanObjectName != null) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("unregisterWLSModelMBean: removing " + beanObjectName + " for " + bean + " from the nameManager, wasn't registered with the mbean server");
                  }

                  nameManager.unregisterObjectInstance(bean);
               }

            }
         }
      }
   }
}
