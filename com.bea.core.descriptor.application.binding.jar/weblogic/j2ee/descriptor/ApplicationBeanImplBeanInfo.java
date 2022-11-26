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

public class ApplicationBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ApplicationBean.class;

   public ApplicationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ApplicationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.ApplicationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.ApplicationBean");
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
         currentResult = new PropertyDescriptor("AdministeredObjects", ApplicationBean.class, getterName, setterName);
         descriptors.put("AdministeredObjects", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", ApplicationBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactories", ApplicationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DataSources")) {
         getterName = "getDataSources";
         setterName = null;
         currentResult = new PropertyDescriptor("DataSources", ApplicationBean.class, getterName, setterName);
         descriptors.put("DataSources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Descriptions")) {
         getterName = "getDescriptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescriptions";
         }

         currentResult = new PropertyDescriptor("Descriptions", ApplicationBean.class, getterName, setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisplayNames")) {
         getterName = "getDisplayNames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisplayNames";
         }

         currentResult = new PropertyDescriptor("DisplayNames", ApplicationBean.class, getterName, setterName);
         descriptors.put("DisplayNames", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbLocalRefs")) {
         getterName = "getEjbLocalRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbLocalRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("EjbLocalRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbRefs")) {
         getterName = "getEjbRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("EjbRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnvEntries")) {
         getterName = "getEnvEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("EnvEntries", ApplicationBean.class, getterName, setterName);
         descriptors.put("EnvEntries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Icons")) {
         getterName = "getIcons";
         setterName = null;
         currentResult = new PropertyDescriptor("Icons", ApplicationBean.class, getterName, setterName);
         descriptors.put("Icons", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createIcon");
         currentResult.setValue("destroyer", "destroyIcon");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ApplicationBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitializeInOrder")) {
         getterName = "getInitializeInOrder";
         setterName = null;
         currentResult = new PropertyDescriptor("InitializeInOrder", ApplicationBean.class, getterName, setterName);
         descriptors.put("InitializeInOrder", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsConnectionFactories")) {
         getterName = "getJmsConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsConnectionFactories", ApplicationBean.class, getterName, setterName);
         descriptors.put("JmsConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JmsDestinations")) {
         getterName = "getJmsDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsDestinations", ApplicationBean.class, getterName, setterName);
         descriptors.put("JmsDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("LibraryDirectory")) {
         getterName = "getLibraryDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLibraryDirectory";
         }

         currentResult = new PropertyDescriptor("LibraryDirectory", ApplicationBean.class, getterName, setterName);
         descriptors.put("LibraryDirectory", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "lib");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MailSessions")) {
         getterName = "getMailSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessions", ApplicationBean.class, getterName, setterName);
         descriptors.put("MailSessions", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessageDestinationRefs")) {
         getterName = "getMessageDestinationRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("MessageDestinationRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageDestinations")) {
         getterName = "getMessageDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinations", ApplicationBean.class, getterName, setterName);
         descriptors.put("MessageDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Modules")) {
         getterName = "getModules";
         setterName = null;
         currentResult = new PropertyDescriptor("Modules", ApplicationBean.class, getterName, setterName);
         descriptors.put("Modules", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyModule");
         currentResult.setValue("creator", "createModule");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceContextRefs")) {
         getterName = "getPersistenceContextRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceContextRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("PersistenceContextRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitRefs")) {
         getterName = "getPersistenceUnitRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("PersistenceUnitRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceEnvRefs")) {
         getterName = "getResourceEnvRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceEnvRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("ResourceEnvRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceRefs")) {
         getterName = "getResourceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("ResourceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityRoles")) {
         getterName = "getSecurityRoles";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityRoles", ApplicationBean.class, getterName, setterName);
         descriptors.put("SecurityRoles", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSecurityRole");
         currentResult.setValue("destroyer", "destroySecurityRole");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceRefs")) {
         getterName = "getServiceRefs";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceRefs", ApplicationBean.class, getterName, setterName);
         descriptors.put("ServiceRefs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", ApplicationBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "8");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationBean.class.getMethod("createIcon");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icons");
      }

      mth = ApplicationBean.class.getMethod("destroyIcon", IconBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Icons");
      }

      mth = ApplicationBean.class.getMethod("createModule");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Modules");
      }

      mth = ApplicationBean.class.getMethod("destroyModule", ModuleBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Modules");
      }

      mth = ApplicationBean.class.getMethod("createSecurityRole");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoles");
      }

      mth = ApplicationBean.class.getMethod("destroySecurityRole", SecurityRoleBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoles");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationBean.class.getMethod("addDescription", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = ApplicationBean.class.getMethod("removeDescription", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Descriptions");
      }

      mth = ApplicationBean.class.getMethod("addDisplayName", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisplayNames");
      }

      mth = ApplicationBean.class.getMethod("removeDisplayName", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisplayNames");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationBean.class.getMethod("convertToJ2eeEnvironmentBean");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

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
