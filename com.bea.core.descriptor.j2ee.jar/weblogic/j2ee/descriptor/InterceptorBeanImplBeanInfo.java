package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class InterceptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = InterceptorBean.class;

   public InterceptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public InterceptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.InterceptorBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.InterceptorBean");
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
         currentResult = new PropertyDescriptor("AdministeredObjects", InterceptorBean.class, getterName, setterName);
         descriptors.put("AdministeredObjects", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAdministeredObjectBean");
         currentResult.setValue("destroyer", "destroyAdministeredObject");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AroundConstructs")) {
         getterName = "getAroundConstructs";
         setterName = null;
         currentResult = new PropertyDescriptor("AroundConstructs", InterceptorBean.class, getterName, setterName);
         descriptors.put("AroundConstructs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAroundConstruct");
         currentResult.setValue("destroyer", "destroyAroundConstruct");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("AroundInvokes")) {
         getterName = "getAroundInvokes";
         setterName = null;
         currentResult = new PropertyDescriptor("AroundInvokes", InterceptorBean.class, getterName, setterName);
         descriptors.put("AroundInvokes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAroundInvoke");
         currentResult.setValue("creator", "createAroundInvoke");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AroundTimeouts")) {
         getterName = "getAroundTimeouts";
         setterName = null;
         currentResult = new PropertyDescriptor("AroundTimeouts", InterceptorBean.class, getterName, setterName);
         descriptors.put("AroundTimeouts", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAroundTimeout");
         currentResult.setValue("creator", "createAroundTimeout");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactories", InterceptorBean.class, getterName, setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConnectionFactory");
         currentResult.setValue("creator", "createConnectionFactoryResourceBean");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DataSources")) {
         getterName = "getDataSources";
         setterName = null;
         currentResult = new PropertyDescriptor("DataSources", InterceptorBean.class, getterName, setterName);
         descriptors.put("DataSources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDataSource");
         currentResult.setValue("destroyer", "destroyDataSource");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Descriptions")) {
         getterName = "getDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("Descriptions", InterceptorBean.class, getterName, setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbLocalRefs")) {
         getterName = "getEjbLocalRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbLocalRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("EjbLocalRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbLocalRef");
         currentResult.setValue("destroyer", "destroyEjbLocalRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbRefs")) {
         getterName = "getEjbRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("EjbRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEjbRef");
         currentResult.setValue("destroyer", "destroyEjbRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnvEntries")) {
         getterName = "getEnvEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("EnvEntries", InterceptorBean.class, getterName, setterName);
         descriptors.put("EnvEntries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEnvEntry");
         currentResult.setValue("destroyer", "destroyEnvEntry");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", InterceptorBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptorClass")) {
         getterName = "getInterceptorClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptorClass";
         }

         currentResult = new PropertyDescriptor("InterceptorClass", InterceptorBean.class, getterName, setterName);
         descriptors.put("InterceptorClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsConnectionFactories")) {
         getterName = "getJmsConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsConnectionFactories", InterceptorBean.class, getterName, setterName);
         descriptors.put("JmsConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJmsConnectionFactory");
         currentResult.setValue("destroyer", "destroyJmsConnectionFactory");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsDestinations")) {
         getterName = "getJmsDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsDestinations", InterceptorBean.class, getterName, setterName);
         descriptors.put("JmsDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJmsDestination");
         currentResult.setValue("creator", "createJmsDestination");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MailSessions")) {
         getterName = "getMailSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessions", InterceptorBean.class, getterName, setterName);
         descriptors.put("MailSessions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMailSession");
         currentResult.setValue("creator", "createMailSession");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessageDestinationRefs")) {
         getterName = "getMessageDestinationRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("MessageDestinationRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMessageDestinationRef");
         currentResult.setValue("creator", "createMessageDestinationRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceContextRefs")) {
         getterName = "getPersistenceContextRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceContextRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("PersistenceContextRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceContextRef");
         currentResult.setValue("creator", "createPersistenceContextRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitRefs")) {
         getterName = "getPersistenceUnitRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("PersistenceUnitRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceUnitRef");
         currentResult.setValue("creator", "createPersistenceUnitRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostActivates")) {
         getterName = "getPostActivates";
         setterName = null;
         currentResult = new PropertyDescriptor("PostActivates", InterceptorBean.class, getterName, setterName);
         descriptors.put("PostActivates", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPostActivate");
         currentResult.setValue("creator", "createPostActivate");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostConstructs")) {
         getterName = "getPostConstructs";
         setterName = null;
         currentResult = new PropertyDescriptor("PostConstructs", InterceptorBean.class, getterName, setterName);
         descriptors.put("PostConstructs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPostConstruct");
         currentResult.setValue("destroyer", "destroyPostConstruct");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreDestroys")) {
         getterName = "getPreDestroys";
         setterName = null;
         currentResult = new PropertyDescriptor("PreDestroys", InterceptorBean.class, getterName, setterName);
         descriptors.put("PreDestroys", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPreDestroy");
         currentResult.setValue("destroyer", "destroyPreDestroy");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrePassivates")) {
         getterName = "getPrePassivates";
         setterName = null;
         currentResult = new PropertyDescriptor("PrePassivates", InterceptorBean.class, getterName, setterName);
         descriptors.put("PrePassivates", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPrePassivate");
         currentResult.setValue("creator", "createPrePassivate");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvRefs")) {
         getterName = "getResourceEnvRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("ResourceEnvRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceEnvRef");
         currentResult.setValue("destroyer", "destroyResourceEnvRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceRefs")) {
         getterName = "getResourceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("ResourceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyResourceRef");
         currentResult.setValue("creator", "createResourceRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceRefs")) {
         getterName = "getServiceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceRefs", InterceptorBean.class, getterName, setterName);
         descriptors.put("ServiceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServiceRef");
         currentResult.setValue("destroyer", "destroyServiceRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = InterceptorBean.class.getMethod("createAroundInvoke");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundInvokes");
      }

      mth = InterceptorBean.class.getMethod("destroyAroundInvoke", AroundInvokeBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundInvokes");
      }

      mth = InterceptorBean.class.getMethod("createAroundTimeout");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundTimeouts");
      }

      mth = InterceptorBean.class.getMethod("destroyAroundTimeout", AroundTimeoutBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AroundTimeouts");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createAroundConstruct");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AroundConstructs");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyAroundConstruct", LifecycleCallbackBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AroundConstructs");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = InterceptorBean.class.getMethod("createEnvEntry");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EnvEntries");
      }

      mth = InterceptorBean.class.getMethod("destroyEnvEntry", EnvEntryBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EnvEntries");
      }

      mth = InterceptorBean.class.getMethod("createEjbRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyEjbRef", EjbRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbRefs");
      }

      mth = InterceptorBean.class.getMethod("createEjbLocalRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbLocalRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyEjbLocalRef", EjbLocalRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EjbLocalRefs");
      }

      mth = InterceptorBean.class.getMethod("createServiceRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyServiceRef", ServiceRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceRefs");
      }

      mth = InterceptorBean.class.getMethod("createResourceRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyResourceRef", ResourceRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceRefs");
      }

      mth = InterceptorBean.class.getMethod("createResourceEnvRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyResourceEnvRef", ResourceEnvRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ResourceEnvRefs");
      }

      mth = InterceptorBean.class.getMethod("createMessageDestinationRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyMessageDestinationRef", MessageDestinationRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationRefs");
      }

      mth = InterceptorBean.class.getMethod("createPersistenceContextRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceContextRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyPersistenceContextRef", PersistenceContextRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceContextRefs");
      }

      mth = InterceptorBean.class.getMethod("createPersistenceUnitRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceUnitRefs");
      }

      mth = InterceptorBean.class.getMethod("destroyPersistenceUnitRef", PersistenceUnitRefBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceUnitRefs");
      }

      mth = InterceptorBean.class.getMethod("createPostConstruct");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostConstructs");
      }

      mth = InterceptorBean.class.getMethod("destroyPostConstruct", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostConstructs");
      }

      mth = InterceptorBean.class.getMethod("createPreDestroy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreDestroys");
      }

      mth = InterceptorBean.class.getMethod("destroyPreDestroy", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PreDestroys");
      }

      mth = InterceptorBean.class.getMethod("createDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataSources");
      }

      mth = InterceptorBean.class.getMethod("destroyDataSource", DataSourceBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataSources");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createJmsConnectionFactory");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyJmsConnectionFactory", JmsConnectionFactoryBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createJmsDestination");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsDestinations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyJmsDestination", JmsDestinationBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JmsDestinations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createMailSession");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MailSessions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyMailSession", MailSessionBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MailSessions");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createConnectionFactoryResourceBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyConnectionFactory", ConnectionFactoryResourceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectionFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("createAdministeredObjectBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdministeredObjects");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = InterceptorBean.class.getMethod("destroyAdministeredObject", AdministeredObjectBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdministeredObjects");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = InterceptorBean.class.getMethod("createPostActivate");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostActivates");
      }

      mth = InterceptorBean.class.getMethod("destroyPostActivate", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostActivates");
      }

      mth = InterceptorBean.class.getMethod("createPrePassivate");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PrePassivates");
      }

      mth = InterceptorBean.class.getMethod("destroyPrePassivate", LifecycleCallbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PrePassivates");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = InterceptorBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = InterceptorBean.class.getMethod("removeDescription", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

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
