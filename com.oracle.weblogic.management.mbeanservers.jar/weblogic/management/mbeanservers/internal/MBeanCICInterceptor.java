package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Pair;

public class MBeanCICInterceptor extends WLSMBeanServerInterceptorBase {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugMBeanCIC");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final Map partitionMBeansCICCache_ = new HashMap();
   private boolean notificationListenerRegistered = false;
   private String domainName;

   public MBeanCICInterceptor(String domainName) {
      this.domainName = domainName;
   }

   public void setNextMBeanServerConnection(MBeanServerConnection next) {
      super.setNextMBeanServerConnection(next);

      try {
         if (!this.notificationListenerRegistered) {
            next.addNotificationListener(MBeanServerDelegate.DELEGATE_NAME, new RegistrationUnRegistrationMBeanServerNotificationListener(), (NotificationFilter)null, new LocalNotificationHandback());
            this.notificationListenerRegistered = true;
         }

      } catch (Exception var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("setNextMBeanServerConnection(MBeanServerConnection next). Error registering listener with MBeanServerDelegate: " + var3.getMessage());
         }

         throw new RuntimeException(var3);
      }
   }

   public Object getAttribute(final ObjectName objectName, final String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      try {
         ComponentInvocationContext mbeanCIC = getCICForMBean(objectName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("getAttribute: MBean: " + objectName.toString() + ", CIC: " + mbeanCIC);
         }

         if (mbeanCIC != null && (!mbeanCIC.getPartitionName().equals("DOMAIN") || mbeanCIC.getApplicationId() != null)) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Calling getAttribute(" + s + ") as " + mbeanCIC);
            }

            return ComponentInvocationContextManager.runAs(KERNEL_ID, mbeanCIC, new Callable() {
               public Object call() throws Exception {
                  return MBeanCICInterceptor.super.getAttribute(objectName, s);
               }
            });
         } else {
            return super.getAttribute(objectName, s);
         }
      } catch (ExecutionException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)cause;
         } else if (cause instanceof AttributeNotFoundException) {
            throw (AttributeNotFoundException)cause;
         } else if (cause instanceof MBeanException) {
            throw (MBeanException)cause;
         } else if (cause instanceof ReflectionException) {
            throw (ReflectionException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      }
   }

   public AttributeList getAttributes(final ObjectName objectName, final String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      try {
         ComponentInvocationContext mbeanCIC = getCICForMBean(objectName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("getAttributes: MBean: " + objectName.toString() + ", CIC: " + mbeanCIC);
         }

         if (mbeanCIC == null || mbeanCIC.getPartitionName().equals("DOMAIN") && mbeanCIC.getApplicationId() == null) {
            return super.getAttributes(objectName, strings);
         } else {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Calling getAttributes(" + strings + ") as " + mbeanCIC);
            }

            return (AttributeList)ComponentInvocationContextManager.runAs(KERNEL_ID, mbeanCIC, new Callable() {
               public AttributeList call() throws Exception {
                  return MBeanCICInterceptor.super.getAttributes(objectName, strings);
               }
            });
         }
      } catch (ExecutionException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)cause;
         } else if (cause instanceof ReflectionException) {
            throw (ReflectionException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      }
   }

   public void setAttribute(final ObjectName objectName, final Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      try {
         ComponentInvocationContext mbeanCIC = getCICForMBean(objectName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("setAttribute: MBean: " + objectName.toString() + ", CIC: " + mbeanCIC);
         }

         if (mbeanCIC == null || mbeanCIC.getPartitionName().equals("DOMAIN") && mbeanCIC.getApplicationId() == null) {
            super.setAttribute(objectName, attribute);
         } else {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Calling setAttribute(" + attribute.getName() + "," + attribute.getValue() + ") as " + mbeanCIC);
            }

            ComponentInvocationContextManager.runAs(KERNEL_ID, mbeanCIC, new Callable() {
               public Void call() throws Exception {
                  MBeanCICInterceptor.super.setAttribute(objectName, attribute);
                  return null;
               }
            });
         }

      } catch (ExecutionException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)cause;
         } else if (cause instanceof MBeanException) {
            throw (MBeanException)cause;
         } else if (cause instanceof ReflectionException) {
            throw (ReflectionException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof NoAccessRuntimeException) {
            throw (NoAccessRuntimeException)cause;
         } else if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      }
   }

   public Object invoke(final ObjectName objectName, final String s, final Object[] objects, final String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      try {
         ComponentInvocationContext mbeanCIC = getCICForMBean(objectName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("invoke method " + s + " on MBean: " + objectName.toString() + ", CIC: " + mbeanCIC);
         }

         if (mbeanCIC == null || mbeanCIC.getPartitionName().equals("DOMAIN") && mbeanCIC.getApplicationId() == null) {
            return super.invoke(objectName, s, objects, strings);
         } else {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Calling invoke(" + s + ") as " + mbeanCIC);
            }

            return ComponentInvocationContextManager.runAs(KERNEL_ID, mbeanCIC, new Callable() {
               public Object call() throws Exception {
                  return MBeanCICInterceptor.super.invoke(objectName, s, objects, strings);
               }
            });
         }
      } catch (ExecutionException var7) {
         Throwable cause = var7.getCause();
         if (cause instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)cause;
         } else if (cause instanceof MBeanException) {
            throw (MBeanException)cause;
         } else if (cause instanceof ReflectionException) {
            throw (ReflectionException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      }
   }

   public AttributeList setAttributes(final ObjectName objectName, final AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      try {
         ComponentInvocationContext mbeanCIC = getCICForMBean(objectName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("setAttributes: MBean: " + objectName.toString() + ", CIC: " + mbeanCIC);
         }

         if (mbeanCIC == null || mbeanCIC.getPartitionName().equals("DOMAIN") && mbeanCIC.getApplicationId() == null) {
            return super.setAttributes(objectName, attributeList);
         } else {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Calling setAttributes: as " + mbeanCIC);
            }

            return (AttributeList)ComponentInvocationContextManager.runAs(KERNEL_ID, mbeanCIC, new Callable() {
               public AttributeList call() throws Exception {
                  return MBeanCICInterceptor.super.setAttributes(objectName, attributeList);
               }
            });
         }
      } catch (ExecutionException var5) {
         Throwable cause = var5.getCause();
         if (cause instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)cause;
         } else if (cause instanceof ReflectionException) {
            throw (ReflectionException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else {
            throw new RuntimeException(cause);
         }
      }
   }

   public static ComponentInvocationContext getCICForMBean(ObjectName oname) {
      Pair pair;
      synchronized(partitionMBeansCICCache_) {
         pair = (Pair)partitionMBeansCICCache_.get(oname);
      }

      return pair == null ? null : (ComponentInvocationContext)pair.getA();
   }

   private class RegistrationUnRegistrationMBeanServerNotificationListener implements NotificationListener {
      private RegistrationUnRegistrationMBeanServerNotificationListener() {
      }

      public void handleNotification(Notification notification, Object handback) {
         MBeanServerNotification notif = (MBeanServerNotification)MBeanServerNotification.class.cast(notification);
         if ("JMX.mbean.registered".equals(notif.getType())) {
            ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(MBeanCICInterceptor.KERNEL_ID).getCurrentComponentInvocationContext();
            synchronized(MBeanCICInterceptor.partitionMBeansCICCache_) {
               ObjectName objectName = notif.getMBeanName();
               Pair pairx = (Pair)MBeanCICInterceptor.partitionMBeansCICCache_.get(objectName);
               if (pairx == null) {
                  if (objectName.getKeyProperty("Location") != null) {
                     if (objectName.getKeyProperty("Location").equals(MBeanCICInterceptor.this.domainName)) {
                        MBeanCICInterceptor.partitionMBeansCICCache_.put(objectName, new Pair(cic, 1));
                     }
                  } else {
                     MBeanCICInterceptor.partitionMBeansCICCache_.put(objectName, new Pair(cic, 1));
                  }
               } else {
                  pairx.setB((Integer)pairx.getB() + 1);
               }
            }
         }

         if ("JMX.mbean.unregistered".equals(notif.getType())) {
            synchronized(MBeanCICInterceptor.partitionMBeansCICCache_) {
               ObjectName objectNamex = notif.getMBeanName();
               Pair pair = (Pair)MBeanCICInterceptor.partitionMBeansCICCache_.get(objectNamex);
               if (pair != null) {
                  if ((Integer)pair.getB() <= 1) {
                     if (objectNamex.getKeyProperty("Location") != null) {
                        if (objectNamex.getKeyProperty("Location").equals(MBeanCICInterceptor.this.domainName)) {
                           MBeanCICInterceptor.partitionMBeansCICCache_.remove(objectNamex);
                        }
                     } else {
                        MBeanCICInterceptor.partitionMBeansCICCache_.remove(objectNamex);
                     }
                  } else {
                     pair.setB((Integer)pair.getB() - 1);
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      RegistrationUnRegistrationMBeanServerNotificationListener(Object x1) {
         this();
      }
   }
}
