package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WeblogicEjbJarBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicEjbJarBean.class;

   public WeblogicEjbJarBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicEjbJarBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CdiDescriptor")) {
         getterName = "getCdiDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("CdiDescriptor", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("CdiDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("CoherenceClusterRef")) {
         getterName = "getCoherenceClusterRef";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterRef", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterRef", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherenceClusterRef");
         currentResult.setValue("destroyer", "destroyCoherenceClusterRef");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ComponentFactoryClassName")) {
         getterName = "getComponentFactoryClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setComponentFactoryClassName";
         }

         currentResult = new PropertyDescriptor("ComponentFactoryClassName", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("ComponentFactoryClassName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisableWarnings")) {
         getterName = "getDisableWarnings";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisableWarnings";
         }

         currentResult = new PropertyDescriptor("DisableWarnings", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("DisableWarnings", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdempotentMethods")) {
         getterName = "getIdempotentMethods";
         setterName = null;
         currentResult = new PropertyDescriptor("IdempotentMethods", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("IdempotentMethods", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createIdempotentMethods");
         currentResult.setValue("destroyer", "destroyIdempotentMethods");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedExecutorServices")) {
         getterName = "getManagedExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServices", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServices", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedExecutorService");
         currentResult.setValue("creator", "createManagedExecutorService");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedScheduledExecutorServices")) {
         getterName = "getManagedScheduledExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServices", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServices", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorService");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorService");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedThreadFactories")) {
         getterName = "getManagedThreadFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactories", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedThreadFactory");
         currentResult.setValue("creator", "createManagedThreadFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessageDestinationDescriptors")) {
         getterName = "getMessageDestinationDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageDestinationDescriptors", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("MessageDestinationDescriptors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMessageDestinationDescriptor");
         currentResult.setValue("destroyer", "destroyMessageDestinationDescriptor");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryMethodsOnRollbacks")) {
         getterName = "getRetryMethodsOnRollbacks";
         setterName = null;
         currentResult = new PropertyDescriptor("RetryMethodsOnRollbacks", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("RetryMethodsOnRollbacks", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyRetryMethodsOnRollback");
         currentResult.setValue("creator", "createRetryMethodsOnRollback");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunAsRoleAssignments")) {
         getterName = "getRunAsRoleAssignments";
         setterName = null;
         currentResult = new PropertyDescriptor("RunAsRoleAssignments", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("RunAsRoleAssignments", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createRunAsRoleAssignment");
         currentResult.setValue("destroyer", "destroyRunAsRoleAssignment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityPermission")) {
         getterName = "getSecurityPermission";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityPermission", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("SecurityPermission", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySecurityPermission");
         currentResult.setValue("creator", "createSecurityPermission");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityRoleAssignments")) {
         getterName = "getSecurityRoleAssignments";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityRoleAssignments", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("SecurityRoleAssignments", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSecurityRoleAssignment");
         currentResult.setValue("destroyer", "destroySecurityRoleAssignment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SkipStateReplicationMethods")) {
         getterName = "getSkipStateReplicationMethods";
         setterName = null;
         currentResult = new PropertyDescriptor("SkipStateReplicationMethods", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("SkipStateReplicationMethods", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySkipStateReplicationMethods");
         currentResult.setValue("creator", "createSkipStateReplicationMethods");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimerImplementation")) {
         getterName = "getTimerImplementation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimerImplementation";
         }

         currentResult = new PropertyDescriptor("TimerImplementation", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("TimerImplementation", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Local");
         currentResult.setValue("legalValues", new Object[]{"Local", "Clustered"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionIsolations")) {
         getterName = "getTransactionIsolations";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionIsolations", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("TransactionIsolations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTransactionIsolation");
         currentResult.setValue("destroyer", "destroyTransactionIsolation");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicCompatibility")) {
         getterName = "getWeblogicCompatibility";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicCompatibility", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("WeblogicCompatibility", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicEnterpriseBeans")) {
         getterName = "getWeblogicEnterpriseBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicEnterpriseBeans", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("WeblogicEnterpriseBeans", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWeblogicEnterpriseBean");
         currentResult.setValue("creator", "createWeblogicEnterpriseBean");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManagers")) {
         getterName = "getWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagers", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("WorkManagers", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWorkManager");
         currentResult.setValue("creator", "createWorkManager");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableBeanClassRedeploy")) {
         getterName = "isEnableBeanClassRedeploy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableBeanClassRedeploy";
         }

         currentResult = new PropertyDescriptor("EnableBeanClassRedeploy", WeblogicEjbJarBean.class, getterName, setterName);
         descriptors.put("EnableBeanClassRedeploy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicEjbJarBean.class.getMethod("createWeblogicEnterpriseBean");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicEnterpriseBeans");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyWeblogicEnterpriseBean", WeblogicEnterpriseBeanBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WeblogicEnterpriseBeans");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createSecurityRoleAssignment");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoleAssignments");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroySecurityRoleAssignment", SecurityRoleAssignmentBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityRoleAssignments");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createRunAsRoleAssignment");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunAsRoleAssignments");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyRunAsRoleAssignment", RunAsRoleAssignmentBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RunAsRoleAssignments");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createSecurityPermission");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityPermission");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroySecurityPermission", SecurityPermissionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SecurityPermission");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createTransactionIsolation");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionIsolations");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyTransactionIsolation", TransactionIsolationBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionIsolations");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createMessageDestinationDescriptor");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationDescriptors");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyMessageDestinationDescriptor", MessageDestinationDescriptorBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageDestinationDescriptors");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createIdempotentMethods");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "IdempotentMethods");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyIdempotentMethods", IdempotentMethodsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "IdempotentMethods");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createSkipStateReplicationMethods");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SkipStateReplicationMethods");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroySkipStateReplicationMethods", SkipStateReplicationMethodsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SkipStateReplicationMethods");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createRetryMethodsOnRollback");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RetryMethodsOnRollbacks");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyRetryMethodsOnRollback", RetryMethodsOnRollbackBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "RetryMethodsOnRollbacks");
      }

      mth = WeblogicEjbJarBean.class.getMethod("createWorkManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManagers");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyWorkManager", WorkManagerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManagers");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("createManagedExecutorService");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("destroyManagedExecutorService", ManagedExecutorServiceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("createManagedScheduledExecutorService");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("destroyManagedScheduledExecutorService", ManagedScheduledExecutorServiceBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("createManagedThreadFactory");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WeblogicEjbJarBean.class.getMethod("destroyManagedThreadFactory", ManagedThreadFactoryBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = WeblogicEjbJarBean.class.getMethod("createCoherenceClusterRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterRef");
      }

      mth = WeblogicEjbJarBean.class.getMethod("destroyCoherenceClusterRef");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterRef");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicEjbJarBean.class.getMethod("addDisableWarning", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisableWarnings");
      }

      mth = WeblogicEjbJarBean.class.getMethod("removeDisableWarning", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DisableWarnings");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicEjbJarBean.class.getMethod("lookupWeblogicEnterpriseBean", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WeblogicEnterpriseBeans");
      }

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
