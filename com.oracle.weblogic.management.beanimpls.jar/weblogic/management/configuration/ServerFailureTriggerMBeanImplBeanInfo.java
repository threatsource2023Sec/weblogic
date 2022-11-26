package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ServerFailureTriggerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerFailureTriggerMBean.class;

   public ServerFailureTriggerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerFailureTriggerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ServerFailureTriggerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configuration to mark the server as failed when threads are stuck. A failed server in turn can be configured to shutdown or go into admin state. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ServerFailureTriggerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MaxStuckThreadTime")) {
         getterName = "getMaxStuckThreadTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxStuckThreadTime";
         }

         currentResult = new PropertyDescriptor("MaxStuckThreadTime", ServerFailureTriggerMBean.class, getterName, setterName);
         descriptors.put("MaxStuckThreadTime", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that a thread must be continually working before this server diagnoses the thread as being stuck.</p>  <p>For example, if you set this to 600 seconds, WebLogic Server considers a thread to be \"stuck\" after 600 seconds of continuous use.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StuckThreadCount")) {
         getterName = "getStuckThreadCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStuckThreadCount";
         }

         currentResult = new PropertyDescriptor("StuckThreadCount", ServerFailureTriggerMBean.class, getterName, setterName);
         descriptors.put("StuckThreadCount", currentResult);
         currentResult.setValue("description", "<p>The number of stuck threads after which the server is transitioned into FAILED state. There are options in OverloadProtectionMBean to suspend and shutdown a FAILED server. By default, the server continues to run in FAILED state.</p>  <p>If the StuckThreadCount value is set to zero then the server never transitions into FAILED server irrespective of the number of stuck threads. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
