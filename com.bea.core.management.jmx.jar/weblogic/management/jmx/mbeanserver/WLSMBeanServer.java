package weblogic.management.jmx.mbeanserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;
import weblogic.diagnostics.debug.DebugLogger;

public class WLSMBeanServer implements MBeanServer {
   private Map objectsByObjectName = Collections.synchronizedMap(new HashMap());
   private MBeanServer wrappedMBeanServer;
   private WLSMBeanServerInterceptor interceptor;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXRuntime");
   private FirstAccessCallback firstAccessCallback;
   private boolean firstAccessStarted;
   private boolean firstAccessFinished;
   private String defaultDomainName;
   private static final boolean globalMBeansVisibleToPartitions = Boolean.getBoolean("GlobalMBeansVisibleToPartitions");
   private static final String PARTITION_KEY;

   MBeanServerConnection getInterceptor() {
      return (MBeanServerConnection)(this.interceptor != null ? this.interceptor : this.wrappedMBeanServer);
   }

   public MBeanServer getMBeanServer() {
      return this.wrappedMBeanServer;
   }

   private void triggerAccessCallback() {
      if (!this.firstAccessFinished) {
         synchronized(this) {
            if (!this.firstAccessStarted) {
               this.firstAccessStarted = true;
               long st = System.currentTimeMillis();
               if (this.firstAccessCallback != null) {
                  this.firstAccessCallback.accessed(this.wrappedMBeanServer);
               }

               if (debug.isDebugEnabled()) {
                  long end = System.currentTimeMillis();
                  debug.debug("WLSMBeanServer: first access callback for " + this + " took " + (end - st) + " ms");
               }

               this.firstAccessFinished = true;
               this.firstAccessCallback = null;
            }
         }
      }

   }

   public void setFirstAccessCallback(FirstAccessCallback callback) {
      this.firstAccessStarted = false;
      this.firstAccessFinished = false;
      this.firstAccessCallback = callback;
   }

   void setWrappedMBeanServer(MBeanServer wrapped) {
      this.wrappedMBeanServer = wrapped;
   }

   public void addInterceptor(WLSMBeanServerInterceptor newInterceptor) {
      if (this.interceptor == null) {
         this.interceptor = newInterceptor;
         newInterceptor.setNextMBeanServerConnection(this.wrappedMBeanServer);
      } else {
         WLSMBeanServerInterceptor lastInterceptor = this.interceptor;

         WLSMBeanServerInterceptor nextInterceptor;
         for(MBeanServerConnection nextConnection = this.interceptor.getNextMBeanServerConnection(); nextConnection instanceof WLSMBeanServerInterceptor; nextConnection = nextInterceptor.getNextMBeanServerConnection()) {
            nextInterceptor = (WLSMBeanServerInterceptor)nextConnection;
            lastInterceptor = (WLSMBeanServerInterceptor)nextConnection;
         }

         newInterceptor.setNextMBeanServerConnection(this.wrappedMBeanServer);
         lastInterceptor.setNextMBeanServerConnection(newInterceptor);
      }

   }

   public ObjectInstance createMBean(String s, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().createMBean(s, objectName);
      } catch (IOException var4) {
         throw new MBeanException(var4);
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().createMBean(s, objectName, objectName1);
      } catch (IOException var5) {
         throw new MBeanException(var5);
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().createMBean(s, objectName, objects, strings);
      } catch (IOException var6) {
         throw new MBeanException(var6);
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().createMBean(s, objectName, objectName1, objects, strings);
      } catch (IOException var7) {
         throw new MBeanException(var7);
      }
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().unregisterMBean(objectName);
         ObjectName noPartObjectName = removePartitionFromObjectName(objectName);
         this.objectsByObjectName.remove(noPartObjectName.getCanonicalName());
      } catch (IOException var3) {
         throw new InstanceNotFoundException();
      }
   }

   public void internalUnregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException {
      this.wrappedMBeanServer.unregisterMBean(objectName);
      ObjectName noPartObjectName = removePartitionFromObjectName(objectName);
      this.objectsByObjectName.remove(noPartObjectName.getCanonicalName());
   }

   public ObjectInstance getObjectInstance(ObjectName objectName) throws InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().getObjectInstance(objectName);
      } catch (IOException var3) {
         throw new InstanceNotFoundException(objectName.toString());
      }
   }

   public Set queryMBeans(ObjectName objectName, QueryExp queryExp) {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().queryMBeans(objectName, queryExp);
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }

   public Set queryNames(ObjectName objectName, QueryExp queryExp) {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().queryNames(objectName, queryExp);
      } catch (IOException var4) {
         throw new Error(var4);
      }
   }

   public boolean isRegistered(ObjectName objectName) {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().isRegistered(objectName);
      } catch (IOException var3) {
         throw new Error(var3);
      }
   }

   public Integer getMBeanCount() {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().getMBeanCount();
      } catch (IOException var2) {
         throw new Error(var2);
      }
   }

   public Object getAttribute(ObjectName objectName, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      this.triggerAccessCallback();

      try {
         return this.isPlatformMBean(objectName) ? this.getMBeanServer().getAttribute(objectName, s) : this.getInterceptor().getAttribute(objectName, s);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException {
      this.triggerAccessCallback();

      try {
         return this.isPlatformMBean(objectName) ? this.getMBeanServer().getAttributes(objectName, strings) : this.getInterceptor().getAttributes(objectName, strings);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().setAttribute(objectName, attribute);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().setAttributes(objectName, attributeList);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().invoke(objectName, s, objects, strings);
      } catch (IOException var6) {
         throw new MBeanException(var6);
      }
   }

   public String getDefaultDomain() {
      this.triggerAccessCallback();

      try {
         return this.defaultDomainName != null ? this.defaultDomainName : this.getInterceptor().getDefaultDomain();
      } catch (IOException var2) {
         throw new Error(var2);
      }
   }

   public String[] getDomains() {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().getDomains();
      } catch (IOException var2) {
         throw new Error(var2);
      }
   }

   public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().addNotificationListener(objectName, notificationListener, notificationFilter, o);
      } catch (IOException var6) {
         throw new InstanceNotFoundException();
      }
   }

   public void addNotificationListener(ObjectName objectName, ObjectName objectName1, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().addNotificationListener(objectName, objectName1, notificationFilter, o);
      } catch (IOException var6) {
         throw new InstanceNotFoundException();
      }
   }

   public void removeNotificationListener(ObjectName objectName, ObjectName objectName1) throws InstanceNotFoundException, ListenerNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().removeNotificationListener(objectName, objectName1);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public void removeNotificationListener(ObjectName objectName, ObjectName objectName1, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().removeNotificationListener(objectName, objectName1, notificationFilter, o);
      } catch (IOException var6) {
         throw new InstanceNotFoundException();
      }
   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().removeNotificationListener(objectName, notificationListener);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException {
      this.triggerAccessCallback();

      try {
         this.getInterceptor().removeNotificationListener(objectName, notificationListener, notificationFilter, o);
      } catch (IOException var6) {
         throw new InstanceNotFoundException();
      }
   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().getMBeanInfo(objectName);
      } catch (IOException var3) {
         throw new InstanceNotFoundException();
      }
   }

   public boolean isInstanceOf(ObjectName objectName, String s) throws InstanceNotFoundException {
      this.triggerAccessCallback();

      try {
         return this.getInterceptor().isInstanceOf(objectName, s);
      } catch (IOException var4) {
         throw new InstanceNotFoundException();
      }
   }

   public Object lookupObject(ObjectName name) {
      if (name == null) {
         return null;
      } else {
         Object instance = this.objectsByObjectName.get(name.getCanonicalName());
         return instance;
      }
   }

   public ObjectInstance registerMBean(Object object, ObjectName name) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      this.triggerAccessCallback();
      ObjectInstance instance = null;
      if (this.getInterceptor() instanceof MBeanServer) {
         instance = ((MBeanServer)this.getInterceptor()).registerMBean(object, name);
      } else {
         instance = ((WLSMBeanServerInterceptor)this.getInterceptor()).registerMBean(object, name);
      }

      this.objectsByObjectName.put(instance.getObjectName().getCanonicalName(), object);
      return instance;
   }

   public Object instantiate(String className) throws ReflectionException, MBeanException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.instantiate(className);
   }

   public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.instantiate(className, loaderName);
   }

   public Object instantiate(String className, Object[] params, String[] signature) throws ReflectionException, MBeanException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.instantiate(className, params, signature);
   }

   public Object instantiate(String className, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.instantiate(className, loaderName, params, signature);
   }

   public ObjectInputStream deserialize(ObjectName name, byte[] data) throws InstanceNotFoundException, OperationsException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.deserialize(name, data);
   }

   public ObjectInputStream deserialize(String className, byte[] data) throws OperationsException, ReflectionException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.deserialize(className, data);
   }

   public ObjectInputStream deserialize(String className, ObjectName loaderName, byte[] data) throws InstanceNotFoundException, OperationsException, ReflectionException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.deserialize(className, loaderName, data);
   }

   public ClassLoader getClassLoaderFor(ObjectName mbeanName) throws InstanceNotFoundException {
      this.triggerAccessCallback();
      return this.interceptor != null ? this.interceptor.getClassLoaderFor(mbeanName) : this.wrappedMBeanServer.getClassLoaderFor(mbeanName);
   }

   public ClassLoader getClassLoader(ObjectName loaderName) throws InstanceNotFoundException {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.getClassLoader(loaderName);
   }

   public ClassLoaderRepository getClassLoaderRepository() {
      this.triggerAccessCallback();
      return this.wrappedMBeanServer.getClassLoaderRepository();
   }

   public void setDefaultDomainName(String name) {
      this.defaultDomainName = name;
   }

   private boolean isPlatformMBean(ObjectName objectName) {
      if (objectName == null) {
         return false;
      } else {
         String domainName = objectName.getDomain();
         if (domainName != null && (domainName.equals("java.lang") || domainName.equals("com.sun.management"))) {
            return objectName.getKeyProperty("Location") == null;
         } else {
            return false;
         }
      }
   }

   private static ObjectName removePartitionFromObjectName(ObjectName objectName) {
      if (objectName == null) {
         return objectName;
      } else if (objectName.getKeyProperty(PARTITION_KEY) != null) {
         String keyList = objectName.getKeyPropertyListString();
         StringBuilder newName = new StringBuilder();
         int begin = keyList.indexOf(PARTITION_KEY + "=");
         newName.append(objectName.getDomain());
         newName.append(":");
         newName.append(keyList.substring(0, begin));
         keyList = keyList.substring(begin + PARTITION_KEY.length() + 1, keyList.length());
         if (keyList.indexOf(",") != -1) {
            newName.append(keyList.substring(keyList.indexOf(",") + 1, keyList.length()));
         }

         if (newName.lastIndexOf(",") == newName.length() - 1) {
            newName = newName.deleteCharAt(newName.lastIndexOf(","));
         }

         try {
            return new ObjectName(newName.toString());
         } catch (MalformedObjectNameException var5) {
            throw new AssertionError(var5);
         }
      } else {
         return objectName;
      }
   }

   static {
      if (globalMBeansVisibleToPartitions) {
         PARTITION_KEY = "_partitionName_";
      } else {
         PARTITION_KEY = "Partition";
      }

   }

   public interface FirstAccessCallback {
      void accessed(MBeanServer var1);
   }
}
