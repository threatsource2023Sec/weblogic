package weblogic.management.jmx.mbeanserver;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;

public class WLSMBeanServerInterceptorBase extends NotificationBroadcasterSupport implements WLSMBeanServerInterceptor {
   protected MBeanServerConnection mbeanServerConnection = null;

   public void setNextMBeanServerConnection(MBeanServerConnection next) {
      this.mbeanServerConnection = next;
   }

   public MBeanServerConnection getNextMBeanServerConnection() {
      return this.mbeanServerConnection;
   }

   public ClassLoader getClassLoaderFor(final ObjectName oName) throws InstanceNotFoundException {
      try {
         return (ClassLoader)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection instanceof WLSMBeanServerInterceptor ? ((WLSMBeanServerInterceptor)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).getClassLoaderFor(oName) : ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).getClassLoaderFor(oName);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            return null;
         }
      }
   }

   public ClassLoader getClassLoader(final ObjectName oName) throws InstanceNotFoundException {
      try {
         return (ClassLoader)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public ClassLoader run() throws Exception {
               return ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).getClassLoader(oName);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className) throws ReflectionException, MBeanException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).instantiate(className);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).instantiate(className, loaderName);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final Object[] params, final String[] signature) throws ReflectionException, MBeanException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).instantiate(className, params, signature);
            }
         });
      } catch (PrivilegedActionException var7) {
         Exception e1 = var7.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public Object instantiate(final String className, final ObjectName loaderName, final Object[] params, final String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).instantiate(className, loaderName, params, signature);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else {
            throw new AssertionError(e1);
         }
      }
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.createMBean(s, objectName);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         } else if (e1 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)e1;
         } else if (e1 instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.createMBean(s, objectName, objectName1);
            }
         });
      } catch (PrivilegedActionException var7) {
         Exception e1 = var7.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         } else if (e1 instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)e1;
         } else if (e1 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final Object[] objects, final String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.createMBean(s, objectName, objects, strings);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)e1;
         } else if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)e1;
         } else if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public ObjectInstance createMBean(final String s, final ObjectName objectName, final ObjectName objectName1, final Object[] objects, final String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.createMBean(s, objectName, objectName1, objects, strings);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception e1 = var11.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         } else if (e1 instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)e1;
         } else if (e1 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public void unregisterMBean(final ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.unregisterMBean(objectName);
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public ObjectInstance getObjectInstance(final ObjectName objectName) throws InstanceNotFoundException, IOException {
      try {
         return (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getObjectInstance(objectName);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public Set queryMBeans(final ObjectName objectName, final QueryExp queryExp) throws IOException {
      try {
         return (Set)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.queryMBeans(objectName, queryExp);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public Set queryNames(final ObjectName objectName, final QueryExp queryExp) throws IOException {
      try {
         return (Set)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.queryNames(objectName, queryExp);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public boolean isRegistered(final ObjectName objectName) throws IOException {
      try {
         return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return new Boolean(WLSMBeanServerInterceptorBase.this.mbeanServerConnection.isRegistered(objectName));
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return false;
         }
      }
   }

   public Integer getMBeanCount() throws IOException {
      try {
         return (Integer)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getMBeanCount();
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e1 = var3.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public Object getAttribute(final ObjectName objectName, final String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getAttribute(objectName, s);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof AttributeNotFoundException) {
            throw (AttributeNotFoundException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public AttributeList getAttributes(final ObjectName objectName, final String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      try {
         return (AttributeList)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getAttributes(objectName, strings);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public void setAttribute(final ObjectName objectName, final Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      try {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  WLSMBeanServerInterceptorBase.this.mbeanServerConnection.setAttribute(objectName, attribute);
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e1 = var5.getException();
            if (e1 instanceof InstanceNotFoundException) {
               throw (InstanceNotFoundException)e1;
            }

            if (e1 instanceof MBeanException) {
               throw (MBeanException)e1;
            }

            if (e1 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)e1;
            }

            if (e1 instanceof AttributeNotFoundException) {
               throw (AttributeNotFoundException)e1;
            }

            if (e1 instanceof ReflectionException) {
               throw (ReflectionException)e1;
            }

            if (e1 instanceof IOException) {
               throw (IOException)e1;
            }
         }

      } catch (IllegalArgumentException var6) {
         throw new InvalidAttributeValueException(var6.getMessage());
      } catch (MBeanException var7) {
         Throwable cause = var7.getCause();
         if (cause != null && cause instanceof IllegalArgumentException) {
            throw new InvalidAttributeValueException(cause.getMessage());
         } else {
            throw var7;
         }
      }
   }

   public AttributeList setAttributes(final ObjectName objectName, final AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      try {
         return (AttributeList)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.setAttributes(objectName, attributeList);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public Object invoke(final ObjectName objectName, final String s, final Object[] objects, final String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.invoke(objectName, s, objects, strings);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         } else if (e1 instanceof MBeanException) {
            throw (MBeanException)e1;
         } else if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         } else if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public String getDefaultDomain() throws IOException {
      try {
         return (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getDefaultDomain();
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e1 = var3.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         } else {
            return null;
         }
      }
   }

   public String[] getDomains() throws IOException {
      String[] theObj = null;

      try {
         theObj = (String[])((String[])AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getDomains();
            }
         }));
      } catch (PrivilegedActionException var4) {
         Exception e1 = var4.getException();
         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

      return theObj;
   }

   public void addNotificationListener(final ObjectName objectName, final NotificationListener notificationListener, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.addNotificationListener(objectName, notificationListener, notificationFilter, o);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public void addNotificationListener(final ObjectName objectName, final ObjectName objectName1, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.addNotificationListener(objectName, objectName1, notificationFilter, o);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public void removeNotificationListener(final ObjectName objectName, final ObjectName objectName1) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.removeNotificationListener(objectName, objectName1);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public void removeNotificationListener(final ObjectName objectName, final ObjectName objectName1, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.removeNotificationListener(objectName, objectName1, notificationFilter, o);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public void removeNotificationListener(final ObjectName objectName, final NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.removeNotificationListener(objectName, notificationListener);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public void removeNotificationListener(final ObjectName objectName, final NotificationListener notificationListener, final NotificationFilter notificationFilter, final Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSMBeanServerInterceptorBase.this.mbeanServerConnection.removeNotificationListener(objectName, notificationListener, notificationFilter, o);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception e1 = var9.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

   }

   public MBeanInfo getMBeanInfo(final ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      MBeanInfo theObj = null;

      try {
         theObj = (MBeanInfo)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection.getMBeanInfo(objectName);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e1 = var5.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof IntrospectionException) {
            throw (IntrospectionException)e1;
         }

         if (e1 instanceof ReflectionException) {
            throw (ReflectionException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

      return theObj;
   }

   public boolean isInstanceOf(final ObjectName objectName, final String s) throws InstanceNotFoundException, IOException {
      boolean theObj = false;

      try {
         theObj = (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return new Boolean(WLSMBeanServerInterceptorBase.this.mbeanServerConnection.isInstanceOf(objectName, s));
            }
         });
      } catch (PrivilegedActionException var6) {
         Exception e1 = var6.getException();
         if (e1 instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)e1;
         }

         if (e1 instanceof IOException) {
            throw (IOException)e1;
         }
      }

      return theObj;
   }

   public ObjectInstance registerMBean(final Object object, final ObjectName name) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      ObjectInstance theObj = null;

      try {
         theObj = (ObjectInstance)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSMBeanServerInterceptorBase.this.mbeanServerConnection instanceof WLSMBeanServerInterceptor ? ((WLSMBeanServerInterceptor)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).registerMBean(object, name) : ((MBeanServer)WLSMBeanServerInterceptorBase.this.mbeanServerConnection).registerMBean(object, name);
            }
         });
      } catch (PrivilegedActionException var6) {
         Exception e1 = var6.getException();
         if (e1 instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)e1;
         }

         if (e1 instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)e1;
         }

         if (e1 instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)e1;
         }
      }

      return theObj;
   }
}
