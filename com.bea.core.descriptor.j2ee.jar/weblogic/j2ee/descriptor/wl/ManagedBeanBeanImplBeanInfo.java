package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InterceptorMethodsBeanImplBeanInfo;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ManagedBeanBeanImplBeanInfo extends InterceptorMethodsBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedBeanBean.class;

   public ManagedBeanBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedBeanBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ManagedBeanBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ManagedBeanBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AdministeredObjects")) {
         getterName = "getAdministeredObjects";
         setterName = null;
         currentResult = new PropertyDescriptor("AdministeredObjects", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("AdministeredObjects", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAdministeredObjectBean");
         currentResult.setValue("destroyer", "destroyAdministeredObject");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("AroundInvokes")) {
         getterName = "getAroundInvokes";
         setterName = null;
         currentResult = new PropertyDescriptor("AroundInvokes", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("AroundInvokes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAroundInvoke");
         currentResult.setValue("creator", "createAroundInvoke");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AroundTimeouts")) {
         getterName = "getAroundTimeouts";
         setterName = null;
         currentResult = new PropertyDescriptor("AroundTimeouts", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("AroundTimeouts", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAroundTimeout");
         currentResult.setValue("creator", "createAroundTimeout");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactories", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConnectionFactory");
         currentResult.setValue("creator", "createConnectionFactoryResourceBean");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DataSources")) {
         getterName = "getDataSources";
         setterName = null;
         currentResult = new PropertyDescriptor("DataSources", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("DataSources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDataSource");
         currentResult.setValue("destroyer", "destroyDataSource");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbLocalRefs")) {
         getterName = "getEjbLocalRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbLocalRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("EjbLocalRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbLocalRef");
         currentResult.setValue("destroyer", "destroyEjbLocalRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbRefs")) {
         getterName = "getEjbRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("EjbRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbRef");
         currentResult.setValue("destroyer", "destroyEjbRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnvEntries")) {
         getterName = "getEnvEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("EnvEntries", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("EnvEntries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEnvEntry");
         currentResult.setValue("destroyer", "destroyEnvEntry");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsConnectionFactories")) {
         getterName = "getJmsConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsConnectionFactories", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("JmsConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJmsConnectionFactory");
         currentResult.setValue("destroyer", "destroyJmsConnectionFactory");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsDestinations")) {
         getterName = "getJmsDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsDestinations", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("JmsDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJmsDestination");
         currentResult.setValue("creator", "createJmsDestination");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MailSessions")) {
         getterName = "getMailSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessions", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("MailSessions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMailSession");
         currentResult.setValue("creator", "createMailSession");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ManagedBeanClass")) {
         getterName = "getManagedBeanClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagedBeanClass";
         }

         currentResult = new PropertyDescriptor("ManagedBeanClass", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ManagedBeanClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedBeanName")) {
         getterName = "getManagedBeanName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagedBeanName";
         }

         currentResult = new PropertyDescriptor("ManagedBeanName", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ManagedBeanName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinationRefs")) {
         getterName = "getMessageDestinationRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("MessageDestinationRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMessageDestinationRef");
         currentResult.setValue("creator", "createMessageDestinationRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceContextRefs")) {
         getterName = "getPersistenceContextRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceContextRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("PersistenceContextRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceContextRef");
         currentResult.setValue("creator", "createPersistenceContextRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitRefs")) {
         getterName = "getPersistenceUnitRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("PersistenceUnitRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceUnitRef");
         currentResult.setValue("creator", "createPersistenceUnitRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostConstructs")) {
         getterName = "getPostConstructs";
         setterName = null;
         currentResult = new PropertyDescriptor("PostConstructs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("PostConstructs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPostConstruct");
         currentResult.setValue("destroyer", "destroyPostConstruct");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreDestroys")) {
         getterName = "getPreDestroys";
         setterName = null;
         currentResult = new PropertyDescriptor("PreDestroys", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("PreDestroys", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPreDestroy");
         currentResult.setValue("destroyer", "destroyPreDestroy");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvRefs")) {
         getterName = "getResourceEnvRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ResourceEnvRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceEnvRef");
         currentResult.setValue("destroyer", "destroyResourceEnvRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceRefs")) {
         getterName = "getResourceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ResourceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyResourceRef");
         currentResult.setValue("creator", "createResourceRef");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceRefs")) {
         getterName = "getServiceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceRefs", ManagedBeanBean.class, getterName, setterName);
         descriptors.put("ServiceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceRef");
         currentResult.setValue("destroyer", "destroyServiceRef");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ManagedBeanBean.class.getMethod("createAroundInvoke");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundInvokes");
      }

      mth = ManagedBeanBean.class.getMethod("destroyAroundInvoke", AroundInvokeBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundInvokes");
      }

      mth = ManagedBeanBean.class.getMethod("createAroundTimeout");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundTimeouts");
      }

      mth = ManagedBeanBean.class.getMethod("destroyAroundTimeout", AroundTimeoutBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundTimeouts");
      }

      mth = ManagedBeanBean.class.getMethod("createEjbLocalRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbLocalRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyEjbLocalRef", EjbLocalRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbLocalRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createPersistenceContextRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceContextRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyPersistenceContextRef", PersistenceContextRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceContextRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createEnvEntry");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EnvEntries");
      }

      mth = ManagedBeanBean.class.getMethod("destroyEnvEntry", EnvEntryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EnvEntries");
      }

      mth = ManagedBeanBean.class.getMethod("createEjbRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyEjbRef", EjbRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createServiceRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyServiceRef", ServiceRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createResourceRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyResourceRef", ResourceRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createResourceEnvRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyResourceEnvRef", ResourceEnvRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createMessageDestinationRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyMessageDestinationRef", MessageDestinationRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createPersistenceUnitRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceUnitRefs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyPersistenceUnitRef", PersistenceUnitRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceUnitRefs");
      }

      mth = ManagedBeanBean.class.getMethod("createPostConstruct");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostConstructs");
      }

      mth = ManagedBeanBean.class.getMethod("destroyPostConstruct", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostConstructs");
      }

      mth = ManagedBeanBean.class.getMethod("createPreDestroy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreDestroys");
      }

      mth = ManagedBeanBean.class.getMethod("destroyPreDestroy", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreDestroys");
      }

      mth = ManagedBeanBean.class.getMethod("createDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataSources");
      }

      mth = ManagedBeanBean.class.getMethod("destroyDataSource", DataSourceBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataSources");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("createJmsConnectionFactory");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("destroyJmsConnectionFactory", JmsConnectionFactoryBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("createJmsDestination");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsDestinations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("destroyJmsDestination", JmsDestinationBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsDestinations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("createMailSession");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MailSessions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("destroyMailSession", MailSessionBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MailSessions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("createConnectionFactoryResourceBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("destroyConnectionFactory", ConnectionFactoryResourceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("createAdministeredObjectBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdministeredObjects");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ManagedBeanBean.class.getMethod("destroyAdministeredObject", AdministeredObjectBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdministeredObjects");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
