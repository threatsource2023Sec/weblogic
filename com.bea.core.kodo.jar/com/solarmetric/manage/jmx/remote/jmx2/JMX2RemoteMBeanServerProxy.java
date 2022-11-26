package com.solarmetric.manage.jmx.remote.jmx2;

import com.solarmetric.manage.ManageRuntimeException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class JMX2RemoteMBeanServerProxy implements MBeanServer {
   private static Localizer s_loc = Localizer.forPackage(JMX2RemoteMBeanServerProxy.class);
   private MBeanServerConnection _rs;
   private Log _log;

   public JMX2RemoteMBeanServerProxy(MBeanServerConnection rs, Log log) {
      this._rs = rs;
      this._log = log;
   }

   public void addNotificationListener(ObjectName observed, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      try {
         this._rs.addNotificationListener(observed, listener, filter, handback);
      } catch (IOException var6) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var6);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var6);
      }
   }

   public void addNotificationListener(ObjectName observed, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, NotificationListener listener) throws InstanceNotFoundException, ListenerNotFoundException {
      try {
         this._rs.removeNotificationListener(observed, listener, (NotificationFilter)null, (Object)null);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public void removeNotificationListener(ObjectName observed, ObjectName listener) throws InstanceNotFoundException, ListenerNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      try {
         this._rs.removeNotificationListener(observed, listener, filter, handback);
      } catch (IOException var6) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var6);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var6);
      }
   }

   public Object instantiate(String className) throws ReflectionException, MBeanException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public Object instantiate(String className, ObjectName loaderName, Object[] args, String[] parameters) throws ReflectionException, MBeanException, InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public Object instantiate(String className, Object[] args, String[] parameters) throws ReflectionException, MBeanException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public ObjectInstance createMBean(String className, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      try {
         return this._rs.createMBean(className, objectName);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      try {
         return this._rs.createMBean(className, objectName, loaderName);
      } catch (IOException var5) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var5);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var5);
      }
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, Object[] args, String[] parameters) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      try {
         return this._rs.createMBean(className, objectName, args, parameters);
      } catch (IOException var6) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var6);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var6);
      }
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, ObjectName loaderName, Object[] args, String[] parameters) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      try {
         return this._rs.createMBean(className, objectName, loaderName, args, parameters);
      } catch (IOException var7) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var7);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var7);
      }
   }

   public ObjectInstance registerMBean(Object mbean, ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException {
      try {
         this._rs.unregisterMBean(objectName);
      } catch (IOException var3) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var3);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var3);
      }
   }

   public ObjectInputStream deserialize(String className, ObjectName loaderName, byte[] bytes) throws InstanceNotFoundException, OperationsException, ReflectionException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public ObjectInputStream deserialize(String className, byte[] bytes) throws OperationsException, ReflectionException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public ObjectInputStream deserialize(ObjectName objectName, byte[] bytes) throws InstanceNotFoundException, OperationsException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public Object getAttribute(ObjectName objectName, String attribute) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      try {
         return this._rs.getAttribute(objectName, attribute);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      try {
         this._rs.setAttribute(objectName, attribute);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public AttributeList getAttributes(ObjectName objectName, String[] attributes) throws InstanceNotFoundException, ReflectionException {
      try {
         return this._rs.getAttributes(objectName, attributes);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributes) throws InstanceNotFoundException, ReflectionException {
      try {
         return this._rs.setAttributes(objectName, attributes);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public Object invoke(ObjectName objectName, String methodName, Object[] args, String[] parameters) throws InstanceNotFoundException, MBeanException, ReflectionException {
      try {
         return this._rs.invoke(objectName, methodName, args, parameters);
      } catch (IOException var6) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var6);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var6);
      }
   }

   public String getDefaultDomain() {
      try {
         return this._rs.getDefaultDomain();
      } catch (IOException var2) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var2);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var2);
      }
   }

   public Integer getMBeanCount() {
      try {
         return this._rs.getMBeanCount();
      } catch (IOException var2) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var2);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var2);
      }
   }

   public boolean isRegistered(ObjectName objectname) {
      try {
         return this._rs.isRegistered(objectname);
      } catch (IOException var3) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var3);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var3);
      }
   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      try {
         return this._rs.getMBeanInfo(objectName);
      } catch (IOException var3) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var3);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var3);
      }
   }

   public ObjectInstance getObjectInstance(ObjectName objectName) throws InstanceNotFoundException {
      try {
         return this._rs.getObjectInstance(objectName);
      } catch (IOException var3) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var3);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var3);
      }
   }

   public boolean isInstanceOf(ObjectName objectName, String className) throws InstanceNotFoundException {
      try {
         return this._rs.isInstanceOf(objectName, className);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public Set queryMBeans(ObjectName patternName, QueryExp filter) {
      try {
         return this._rs.queryMBeans(patternName, filter);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public Set queryNames(ObjectName patternName, QueryExp filter) {
      try {
         return this._rs.queryNames(patternName, filter);
      } catch (IOException var4) {
         this._log.error(s_loc.get("rmi-error"));
         this._log.trace(s_loc.get("rmi-error"), var4);
         throw new ManageRuntimeException(s_loc.get("rmi-error"), var4);
      }
   }

   public ClassLoaderRepository getClassLoaderRepository() {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public ClassLoader getClassLoader(ObjectName loaderName) throws InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public ClassLoader getClassLoaderFor(ObjectName mbeanName) throws InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public String[] getDomains() {
      throw new ManageRuntimeException("Unsupported operation");
   }
}
