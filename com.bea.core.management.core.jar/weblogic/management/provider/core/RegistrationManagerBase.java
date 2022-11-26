package weblogic.management.provider.core;

import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.management.ObjectName;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.provider.BaseServiceImpl;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.RegistrationManager;
import weblogic.management.provider.Service;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RegistrationManagerBase implements RegistrationManager {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String PARTITION_DELIMETER = "$";
   private final Map runtimeToConfigBean = new ConcurrentHashMap();
   private final Map configToRuntimeBean = new ConcurrentHashMap();
   private final List registrationHandlers = new CopyOnWriteArrayList();
   private final Map customMBeansByObjectName = new ConcurrentHashMap();
   private final Map serviceMBeans = new ConcurrentHashMap();
   private static final DescriptorBean NULL_DESCRIPTOR_BEAN = new DescriptorBean() {
      public void addBeanUpdateListener(BeanUpdateListener arg0) {
      }

      public void addPropertyChangeListener(PropertyChangeListener arg0) {
      }

      public DescriptorBean createChildCopy(String arg0, DescriptorBean arg1) throws IllegalArgumentException, BeanAlreadyExistsException {
         return null;
      }

      public DescriptorBean createChildCopyIncludingObsolete(String arg0, DescriptorBean arg1) throws IllegalArgumentException, BeanAlreadyExistsException {
         return null;
      }

      public Descriptor getDescriptor() {
         return null;
      }

      public DescriptorBean getParentBean() {
         return null;
      }

      public boolean isEditable() {
         return false;
      }

      public void removeBeanUpdateListener(BeanUpdateListener arg0) {
      }

      public void removePropertyChangeListener(PropertyChangeListener arg0) {
      }

      public boolean isSet(String propertyName) throws IllegalArgumentException {
         return false;
      }

      public void unSet(String propertyName) throws IllegalArgumentException {
      }
   };

   public void registerCustomBean(ObjectName oname, Object instance) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Registration Mgr: register custom mbean " + oname);
      }

      this.invokeRegistrationHandlers(oname, instance);
      this.customMBeansByObjectName.put(oname, instance);
   }

   public void unregisterCustomBean(ObjectName oname) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Registration Mgr: unregister custom mbean " + oname);
      }

      this.customMBeansByObjectName.remove(oname);
      this.invokeRegistrationHandlers(oname);
   }

   public ObjectName[] getCustomMBeans() {
      Set keySet = this.customMBeansByObjectName.keySet();
      return (ObjectName[])((ObjectName[])keySet.toArray(new ObjectName[keySet.size()]));
   }

   public Object lookupCustomMBean(ObjectName oname) {
      return oname == null ? null : this.customMBeansByObjectName.get(oname);
   }

   public void register(RuntimeMBean rt, DescriptorBean relation) {
      this.registerBeanRelationship(rt, relation);
   }

   public void unregister(RuntimeMBean rt) {
      this.unregisterBeanRelationship(rt);
   }

   public void registerBeanRelationship(RuntimeMBean rt, DescriptorBean relation) {
      if (rt != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: register rt mbean " + rt);
         }

         if (relation == null) {
            this.runtimeToConfigBean.put(rt, NULL_DESCRIPTOR_BEAN);
         } else {
            this.runtimeToConfigBean.put(rt, relation);
         }

         if (relation != null) {
            this.configToRuntimeBean.put(relation, rt);
         }

         this.invokeRegistrationHandlers(rt, relation);
      }
   }

   public void unregisterBeanRelationship(RuntimeMBean rt) {
      if (rt != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: unregister rt mbean " + rt);
         }

         Object value = this.runtimeToConfigBean.remove(rt);
         if (value != null) {
            this.configToRuntimeBean.remove(value);
         }

         this.invokeRegistrationHandlers(rt);
      }
   }

   public void registerService(Service serviceBean) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(this.KERNEL_ID).getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      BaseServiceImpl service;
      if (partitionName.equals("DOMAIN")) {
         service = new BaseServiceImpl(serviceBean.getName(), serviceBean.getType(), serviceBean.getParentService(), serviceBean.getParentAttribute());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: register service mbean " + serviceBean + ", name = " + serviceBean.getName() + ", type = " + serviceBean.getType() + " in partition CIC " + cic);
         }
      } else {
         service = new BaseServiceImpl(this.buildNameInPartition(serviceBean.getName(), partitionName), serviceBean.getType(), serviceBean.getParentService(), serviceBean.getParentAttribute());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: register service mbean " + serviceBean + ", name = " + this.buildNameInPartition(serviceBean.getName(), partitionName) + ", type = " + serviceBean.getType() + " in partition CIC " + cic);
         }
      }

      this.serviceMBeans.put(service, serviceBean);
      this.invokeRegistrationHandlers(serviceBean);
   }

   public void unregisterService(Service serviceBean) {
      String partitionName = ComponentInvocationContextManager.getInstance(this.KERNEL_ID).getCurrentComponentInvocationContext().getPartitionName();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Registration Mgr: unregister service bean " + serviceBean + " in partition " + partitionName);
      }

      if (partitionName.equals("DOMAIN")) {
         this.serviceMBeans.remove(new BaseServiceImpl(serviceBean.getName(), serviceBean.getType(), serviceBean.getParentService(), serviceBean.getParentAttribute()));
      } else {
         this.serviceMBeans.remove(new BaseServiceImpl(this.buildNameInPartition(serviceBean.getName(), partitionName), serviceBean.getType(), serviceBean.getParentService(), serviceBean.getParentAttribute()));
      }

      this.invokeUnregistrationHandlers(serviceBean);
   }

   public Service findService(String name, String type) {
      String partitionName = ComponentInvocationContextManager.getInstance(this.KERNEL_ID).getCurrentComponentInvocationContext().getPartitionName();
      Service service;
      if (partitionName.equals("DOMAIN")) {
         service = (Service)this.serviceMBeans.get(new BaseServiceImpl(name, type, (Service)null, (String)null));
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: findService(): partition: " + partitionName + " returned service: " + service);
         }

         return service;
      } else {
         service = (Service)this.serviceMBeans.get(new BaseServiceImpl(this.buildNameInPartition(name, partitionName), type, (Service)null, (String)null));
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registration Mgr: findService() in partition: " + partitionName + " returned service: " + service);
         }

         return service;
      }
   }

   public Service[] getServices() {
      return (Service[])((Service[])this.serviceMBeans.values().toArray(new Service[this.serviceMBeans.size()]));
   }

   public Service[] getRootServices() {
      Iterator beans = this.serviceMBeans.values().iterator();
      List sbeans = new ArrayList();

      while(beans.hasNext()) {
         Service svc = (Service)beans.next();
         if (svc.getParentService() == null) {
            sbeans.add(svc);
         }
      }

      return (Service[])((Service[])sbeans.toArray(new Service[sbeans.size()]));
   }

   private String buildNameInPartition(String serviceName, String partitionName) {
      return new String(serviceName + "$" + partitionName);
   }

   private void invokeRegistrationHandlers(RuntimeMBean runtime, DescriptorBean descriptor) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).registered(runtime, descriptor);
      }

   }

   private void invokeRegistrationHandlers(RuntimeMBean runtime) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).unregistered(runtime);
      }

   }

   private void invokeRegistrationHandlers(ObjectName oname, Object custom) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).registeredCustom(oname, custom);
      }

   }

   private void invokeRegistrationHandlers(ObjectName custom) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).unregisteredCustom(custom);
      }

   }

   private void invokeRegistrationHandlers(Service service) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).registered(service);
      }

   }

   private void invokeUnregistrationHandlers(Service service) {
      Iterator iter = this.registrationHandlers.iterator();

      while(iter.hasNext()) {
         ((RegistrationHandler)iter.next()).unregistered(service);
      }

   }

   public void addRegistrationHandler(RegistrationHandler handler) {
      this.registrationHandlers.add(handler);
   }

   public void initiateRegistrationHandler(RegistrationHandler handler) {
      this.registrationHandlers.add(handler);

      Map.Entry entry;
      DescriptorBean value;
      for(Iterator runtimeIterator = this.runtimeToConfigBean.entrySet().iterator(); runtimeIterator.hasNext(); handler.registered((RuntimeMBean)entry.getKey(), value)) {
         entry = (Map.Entry)runtimeIterator.next();
         value = (DescriptorBean)entry.getValue();
         if (value == NULL_DESCRIPTOR_BEAN) {
            value = null;
         }
      }

      Iterator customIterator = this.customMBeansByObjectName.entrySet().iterator();

      while(customIterator.hasNext()) {
         Map.Entry entry = (Map.Entry)customIterator.next();
         handler.registeredCustom((ObjectName)entry.getKey(), entry.getValue());
      }

      Iterator serviceIterator = this.serviceMBeans.values().iterator();

      while(serviceIterator.hasNext()) {
         handler.registered((Service)serviceIterator.next());
      }

   }

   public void removeRegistrationHandler(RegistrationHandler handler) {
      this.registrationHandlers.remove(handler);
   }

   public RuntimeMBean[] getRuntimeMBeans() {
      Set runtimeMBeans = this.runtimeToConfigBean.keySet();
      return (RuntimeMBean[])((RuntimeMBean[])runtimeMBeans.toArray(new RuntimeMBean[runtimeMBeans.size()]));
   }

   public RuntimeMBean lookupRuntime(DescriptorBean cfg) {
      return cfg == null ? null : (RuntimeMBean)this.configToRuntimeBean.get(cfg);
   }

   public DescriptorBean lookupConfigurationBean(RuntimeMBean rt) {
      return rt == null ? null : (DescriptorBean)this.runtimeToConfigBean.get(rt);
   }
}
