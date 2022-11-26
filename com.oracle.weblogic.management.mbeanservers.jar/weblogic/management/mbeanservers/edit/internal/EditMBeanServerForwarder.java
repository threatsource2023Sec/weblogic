package weblogic.management.mbeanservers.edit.internal;

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
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.provider.internal.EditSessionServerManager;

public final class EditMBeanServerForwarder implements MBeanServer {
   private final WLSMBeanServer defaultServer;
   private final EditSessionServerManager serverManager;

   public EditMBeanServerForwarder(WLSMBeanServer defaultServer, EditSessionServerManager serverManager) {
      this.defaultServer = defaultServer;
      this.serverManager = serverManager;
   }

   private MBeanServer getDelegate() {
      String pn = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return (MBeanServer)(pn != null && !"DOMAIN".equals(pn) ? this.serverManager.getPartitionDefaultMBeanServer(pn) : this.defaultServer);
   }

   public WLSMBeanServer getDefaultServer() {
      return this.defaultServer;
   }

   public ObjectInstance createMBean(String className, ObjectName name) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      return this.getDelegate().createMBean(className, name);
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      return this.getDelegate().createMBean(className, name, loaderName);
   }

   public ObjectInstance createMBean(String className, ObjectName name, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
      return this.getDelegate().createMBean(className, name, params, signature);
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
      return this.getDelegate().createMBean(className, name, loaderName, params, signature);
   }

   public ObjectInstance registerMBean(Object object, ObjectName name) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      return this.getDelegate().registerMBean(object, name);
   }

   public void unregisterMBean(ObjectName name) throws InstanceNotFoundException, MBeanRegistrationException {
      this.getDelegate().unregisterMBean(name);
   }

   public ObjectInstance getObjectInstance(ObjectName name) throws InstanceNotFoundException {
      return this.getDelegate().getObjectInstance(name);
   }

   public Set queryMBeans(ObjectName name, QueryExp query) {
      return this.getDelegate().queryMBeans(name, query);
   }

   public Set queryNames(ObjectName name, QueryExp query) {
      return this.getDelegate().queryNames(name, query);
   }

   public boolean isRegistered(ObjectName name) {
      return this.getDelegate().isRegistered(name);
   }

   public Integer getMBeanCount() {
      return this.getDelegate().getMBeanCount();
   }

   public Object getAttribute(ObjectName name, String attribute) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      return this.getDelegate().getAttribute(name, attribute);
   }

   public AttributeList getAttributes(ObjectName name, String[] attributes) throws InstanceNotFoundException, ReflectionException {
      return this.getDelegate().getAttributes(name, attributes);
   }

   public void setAttribute(ObjectName name, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      this.getDelegate().setAttribute(name, attribute);
   }

   public AttributeList setAttributes(ObjectName name, AttributeList attributes) throws InstanceNotFoundException, ReflectionException {
      return this.getDelegate().setAttributes(name, attributes);
   }

   public Object invoke(ObjectName name, String operationName, Object[] params, String[] signature) throws InstanceNotFoundException, MBeanException, ReflectionException {
      return this.getDelegate().invoke(name, operationName, params, signature);
   }

   public String getDefaultDomain() {
      return this.getDelegate().getDefaultDomain();
   }

   public String[] getDomains() {
      return this.getDelegate().getDomains();
   }

   public void addNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      this.getDelegate().addNotificationListener(name, listener, filter, handback);
   }

   public void addNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException {
      this.getDelegate().addNotificationListener(name, listener, filter, handback);
   }

   public void removeNotificationListener(ObjectName name, ObjectName listener) throws InstanceNotFoundException, ListenerNotFoundException {
      this.getDelegate().removeNotificationListener(name, listener);
   }

   public void removeNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      this.getDelegate().removeNotificationListener(name, listener, filter, handback);
   }

   public void removeNotificationListener(ObjectName name, NotificationListener listener) throws InstanceNotFoundException, ListenerNotFoundException {
      this.getDelegate().removeNotificationListener(name, listener);
   }

   public void removeNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException {
      this.getDelegate().removeNotificationListener(name, listener, filter, handback);
   }

   public MBeanInfo getMBeanInfo(ObjectName name) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
      return this.getDelegate().getMBeanInfo(name);
   }

   public boolean isInstanceOf(ObjectName name, String className) throws InstanceNotFoundException {
      return this.getDelegate().isInstanceOf(name, className);
   }

   public Object instantiate(String className) throws ReflectionException, MBeanException {
      return this.getDelegate().instantiate(className);
   }

   public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      return this.getDelegate().instantiate(className, loaderName);
   }

   public Object instantiate(String className, Object[] params, String[] signature) throws ReflectionException, MBeanException {
      return this.getDelegate().instantiate(className, params, signature);
   }

   public Object instantiate(String className, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      return this.getDelegate().instantiate(className, loaderName, params, signature);
   }

   /** @deprecated */
   @Deprecated
   public ObjectInputStream deserialize(ObjectName name, byte[] data) throws InstanceNotFoundException, OperationsException {
      return this.getDelegate().deserialize(name, data);
   }

   /** @deprecated */
   @Deprecated
   public ObjectInputStream deserialize(String className, byte[] data) throws OperationsException, ReflectionException {
      return this.getDelegate().deserialize(className, data);
   }

   /** @deprecated */
   @Deprecated
   public ObjectInputStream deserialize(String className, ObjectName loaderName, byte[] data) throws InstanceNotFoundException, OperationsException, ReflectionException {
      return this.getDelegate().deserialize(className, loaderName, data);
   }

   public ClassLoader getClassLoaderFor(ObjectName mbeanName) throws InstanceNotFoundException {
      return this.getDelegate().getClassLoaderFor(mbeanName);
   }

   public ClassLoader getClassLoader(ObjectName loaderName) throws InstanceNotFoundException {
      return this.getDelegate().getClassLoader(loaderName);
   }

   public ClassLoaderRepository getClassLoaderRepository() {
      return this.getDelegate().getClassLoaderRepository();
   }
}
