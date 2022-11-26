package weblogic.ejb.container.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.ComponentConcurrentRuntimeMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EJBComponentRuntimeMBean;

public class EJBComponentRuntimeMBeanImplBeanInfo extends ComponentConcurrentRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBComponentRuntimeMBean.class;

   public EJBComponentRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBComponentRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.internal.EJBComponentRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.internal");
      String description = (new String("This is the top level interface for all runtime information collected for an EJB module. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBComponentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceClusterRuntime")) {
         getterName = "getCoherenceClusterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterRuntime", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceClusterRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the Coherence Cluster related runtime mbean for this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("EJBComponent")) {
         getterName = "getEJBComponent";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBComponent", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBComponent", currentResult);
         currentResult.setValue("description", "<p>Provides the associated EJBComponentMBean for this EJB module.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("restRelationship", "reference");
      }

      if (!descriptors.containsKey("EJBRuntimes")) {
         getterName = "getEJBRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EJBRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of EJBRuntimeMBean objects for this EJB module. The EJBRuntimeMBean instances can be cast to their appropriate subclass (EntityEJBRuntimeMBean, StatelessEJBRuntimeMBean, StatefulEJBRuntimeMBean or MessageDrivenEJBRuntimeMBean) to access additional runtime information for the particular EJB.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoPersistenceUnitRuntimes")) {
         getterName = "getKodoPersistenceUnitRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPersistenceUnitRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("KodoPersistenceUnitRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides an array of KodoPersistenceUnitRuntimeMBean objects for this EJB module. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedExecutorServiceRuntimes")) {
         getterName = "getManagedExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServiceRuntimes")) {
         getterName = "getManagedScheduledExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedScheduledExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedScheduledExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ManagedThreadFactoryRuntimes")) {
         getterName = "getManagedThreadFactoryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedThreadFactoryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedThreadFactorys defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("SpringRuntimeMBean")) {
         getterName = "getSpringRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringRuntimeMBean", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringRuntimeMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("WseeClientConfigurationRuntimes")) {
         getterName = "getWseeClientConfigurationRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClientConfigurationRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeClientConfigurationRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service client reference configuration runtime instances that are contained in this EJB within an Enterprise application.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WseeClientRuntimes")) {
         getterName = "getWseeClientRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClientRuntimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeClientRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service client runtime instances that are contained in this Enterprise JavaBean component. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WseeV2Runtimes")) {
         getterName = "getWseeV2Runtimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeV2Runtimes", EJBComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseeV2Runtimes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of Web Service runtime instances that are contained in this EJB within an Enterprise application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EJBComponentRuntimeMBean.class.getMethod("lookupWseeClientRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("rawClientId", "The raw client ID of the client to lookup. This ID does not contain the application/component qualifiers that are prepended to the full client ID for the client. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service client runtime instances that is contained in this Enterprise JavaBean component. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeClientRuntimes");
      }

      mth = EJBComponentRuntimeMBean.class.getMethod("lookupWseeV2Runtime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The web service description name of the web service to look up. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service runtime instance that is contained in this EJB within an Enterprise application. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeV2Runtimes");
      }

      mth = EJBComponentRuntimeMBean.class.getMethod("lookupWseeClientConfigurationRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The web service client reference name to look up. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a named Web Service client reference configuration runtime instance that is contained in this EJB within an Enterprise application.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WseeClientConfigurationRuntimes");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EJBComponentRuntimeMBean.class.getMethod("getEJBRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("ejbName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the EJBRuntimeMBean for the EJB with the specified name.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = EJBComponentRuntimeMBean.class.getMethod("getKodoPersistenceUnitRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("unitName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the KodoPersistenceUnitRuntimeMBean for the EJB with the specified name.</p> ");
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
