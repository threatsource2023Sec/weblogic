package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class OverloadProtectionMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = OverloadProtectionMBean.class;

   public OverloadProtectionMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OverloadProtectionMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.OverloadProtectionMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This Mbean has attributes concerning server overload protection. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.OverloadProtectionMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FailureAction")) {
         getterName = "getFailureAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailureAction";
         }

         currentResult = new PropertyDescriptor("FailureAction", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("FailureAction", currentResult);
         currentResult.setValue("description", "Enable automatic forceshutdown of the server on failed state. The server self-health monitoring detects fatal failures and mark the server as failed. The server can be restarted using NodeManager or a HA agent. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "no-action");
         currentResult.setValue("legalValues", new Object[]{"no-action", "force-shutdown", "admin-state"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreeMemoryPercentHighThreshold")) {
         getterName = "getFreeMemoryPercentHighThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFreeMemoryPercentHighThreshold";
         }

         currentResult = new PropertyDescriptor("FreeMemoryPercentHighThreshold", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("FreeMemoryPercentHighThreshold", currentResult);
         currentResult.setValue("description", "Percentage free memory after which the server overload condition is cleared. WorkManagers stop performing overloadActions and start regular execution once the overload condition is cleared. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreeMemoryPercentLowThreshold")) {
         getterName = "getFreeMemoryPercentLowThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFreeMemoryPercentLowThreshold";
         }

         currentResult = new PropertyDescriptor("FreeMemoryPercentLowThreshold", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("FreeMemoryPercentLowThreshold", currentResult);
         currentResult.setValue("description", "Percentage free memory below which the server is considered overloaded. WorkManagers perform overloadAction once the server is marked as overloaded. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PanicAction")) {
         getterName = "getPanicAction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPanicAction";
         }

         currentResult = new PropertyDescriptor("PanicAction", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("PanicAction", currentResult);
         currentResult.setValue("description", "Exit the server process when the kernel encounters a panic condition like an unhandled OOME. An unhandled OOME could lead to inconsistent state and a server restart is advisable if backed by node manager or a HA agent. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "system-exit");
         currentResult.setValue("legalValues", new Object[]{"no-action", "system-exit"});
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerFailureTrigger")) {
         getterName = "getServerFailureTrigger";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerFailureTrigger", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("ServerFailureTrigger", currentResult);
         currentResult.setValue("description", "Configure a trigger that marks the server as failed when the condition is met. A failed server in turn can be configured to shutdown or go into admin state. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServerFailureTrigger");
         currentResult.setValue("destroyer", "destroyServerFailureTrigger");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("SharedCapacityForWorkManagers")) {
         getterName = "getSharedCapacityForWorkManagers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSharedCapacityForWorkManagers";
         }

         currentResult = new PropertyDescriptor("SharedCapacityForWorkManagers", OverloadProtectionMBean.class, getterName, setterName);
         descriptors.put("SharedCapacityForWorkManagers", currentResult);
         currentResult.setValue("description", "Total number of requests that can be present in the server. This includes requests that are enqueued and those under execution. <p> The server performs a differentiated denial of service on reaching the shared capacity. A request with higher priority will be accepted in place of a lower priority request already in the queue. The lower priority request is kept waiting in the queue till all high priority requests are executed. Further enqueues of the low priority requests are rejected right away. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(65536));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = OverloadProtectionMBean.class.getMethod("createServerFailureTrigger");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Configure a trigger that marks the server as failed when the condition is met. A failed server in turn can be configured to shutdown or go into admin state. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ServerFailureTrigger");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = OverloadProtectionMBean.class.getMethod("destroyServerFailureTrigger");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ServerFailureTrigger");
            currentResult.setValue("since", "9.0.0.0");
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
