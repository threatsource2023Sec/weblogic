package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPAgentDeploymentMBeanImplBeanInfo extends SNMPAgentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPAgentDeploymentMBean.class;

   public SNMPAgentDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPAgentDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPAgentDeploymentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean is an SNMP agent that can be targeted to instances of WebLogic Server. </p> <p>With SNMP, you configure <b>agents</b> to gather and send data about managed resources in response to a request from <b>managers</b>. You can also configure agents to issue unsolicited reports to managers when they detect predefined thresholds or conditions on a managed resource.</p> <p>In a WebLogic Server domain, you can choose a centralized or de-centralized model for SNMP monitoring and communication:</p> <ul><li>In a centralized model, you create an instance of this MBean and target it only to the Administration Server. This agent communicates with all Managed Servers in the domain. SNMP managers communicate only with the SNMP agent on the Administration Server. This model is convenient but introduces performance overhead in WebLogic Server. In addition, if the Administration Server is unavailable, you cannot monitor the domain through SNMP.</li> <li>In a de-centralized model, you create an instance of this MBean and target it to each Managed Server that you want to monitor. (Alternatively, you can create multiple instances of this MBean and target each instance to an individual Managed Server.) Your SNMP manager must communicate with the agents on individual Managed Servers.</li></ul> <p>To support domains that were created with WebLogic Server release 9.2 and earlier, a domain also hosts a singleton SNMP agent whose scope is the entire domain (see {@link SNMPAgentMBean}). SNMPAgentMBean offers the same features as an instance of this MBean (SNMPAgentDeploymentMBean) that you have targeted as described in the centralized model above. However, the underlying implementation of SNMPAgentMBean is different and it will eventually be deprecated. SNMPAgentMBean is overridden if you target an instance of this MBean to the Administration Server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPAgentDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DeploymentOrder")) {
         getterName = "getDeploymentOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentOrder";
         }

         currentResult = new PropertyDescriptor("DeploymentOrder", SNMPAgentDeploymentMBean.class, getterName, setterName);
         descriptors.put("DeploymentOrder", currentResult);
         currentResult.setValue("description", "<p>A priority that the server uses to determine when it deploys an item. The priority is relative to other deployable items of the same type.</p>  <p>For example, the server prioritizes and deploys all EJBs before it prioritizes and deploys startup classes.</p>  <p>Items with the lowest Deployment Order value are deployed first. There is no guarantee on the order of deployments with equal Deployment Order values. There is no guarantee of ordering across clusters.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", SNMPAgentDeploymentMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SNMPAgentDeploymentMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = SNMPAgentDeploymentMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
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
