package com.solarmetric.manage.jmx.remote.jboss322;

import com.solarmetric.manage.ManageRuntimeException;
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
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.jboss.jmx.connector.RemoteMBeanServer;
import org.jboss.jmx.connector.rmi.RMIConnectorImpl;

public class JBossRemoteMBeanServerProxy implements MBeanServer {
   private RemoteMBeanServer _rs;

   public JBossRemoteMBeanServerProxy(RMIAdaptor rmiAdaptor) {
      this._rs = new RMIConnectorImpl(rmiAdaptor);
   }

   public void addNotificationListener(ObjectName observed, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      this._rs.addNotificationListener(observed, listener, filter, handback);
   }

   public void addNotificationListener(ObjectName observed, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, NotificationListener listener) throws InstanceNotFoundException, ListenerNotFoundException {
      this._rs.removeNotificationListener(observed, listener);
   }

   public void removeNotificationListener(ObjectName observed, ObjectName listener) throws InstanceNotFoundException, ListenerNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void removeNotificationListener(ObjectName observed, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      throw new ManageRuntimeException("Unsupported operation");
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
      return this._rs.createMBean(className, objectName);
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      return this._rs.createMBean(className, objectName, loaderName);
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, Object[] args, String[] parameters) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      return this._rs.createMBean(className, objectName, args, parameters);
   }

   public ObjectInstance createMBean(String className, ObjectName objectName, ObjectName loaderName, Object[] args, String[] parameters) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      return this._rs.createMBean(className, objectName, loaderName, args, parameters);
   }

   public ObjectInstance registerMBean(Object mbean, ObjectName objectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      throw new ManageRuntimeException("Unsupported operation");
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException {
      this._rs.unregisterMBean(objectName);
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
      return this._rs.getAttribute(objectName, attribute);
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      this._rs.setAttribute(objectName, attribute);
   }

   public AttributeList getAttributes(ObjectName objectName, String[] attributes) throws InstanceNotFoundException, ReflectionException {
      return this._rs.getAttributes(objectName, attributes);
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributes) throws InstanceNotFoundException, ReflectionException {
      return this._rs.setAttributes(objectName, attributes);
   }

   public Object invoke(ObjectName objectName, String methodName, Object[] args, String[] parameters) throws InstanceNotFoundException, MBeanException, ReflectionException {
      return this._rs.invoke(objectName, methodName, args, parameters);
   }

   public String getDefaultDomain() {
      return this._rs.getDefaultDomain();
   }

   public Integer getMBeanCount() {
      return this._rs.getMBeanCount();
   }

   public boolean isRegistered(ObjectName objectname) {
      return this._rs.isRegistered(objectname);
   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      return this._rs.getMBeanInfo(objectName);
   }

   public ObjectInstance getObjectInstance(ObjectName objectName) throws InstanceNotFoundException {
      return this._rs.getObjectInstance(objectName);
   }

   public boolean isInstanceOf(ObjectName objectName, String className) throws InstanceNotFoundException {
      return this._rs.isInstanceOf(objectName, className);
   }

   public Set queryMBeans(ObjectName patternName, QueryExp filter) {
      return this._rs.queryMBeans(patternName, filter);
   }

   public Set queryNames(ObjectName patternName, QueryExp filter) {
      return this._rs.queryNames(patternName, filter);
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
