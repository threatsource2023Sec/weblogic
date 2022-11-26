package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ResponseTimeRequestClassMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResponseTimeRequestClassMBean.class;

   public ResponseTimeRequestClassMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResponseTimeRequestClassMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ResponseTimeRequestClassMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean defines the response time goal for this request class.  <p> Response time goals can be used to differentiate request classes. We do not try to meet a response time goal for an individual request. Rather we compute a tolerable waiting time for a request class by subtracting the observed average thread use time. Then we schedule requests so that the average wait for each request class is in proportion to their tolerable waiting time. For example, consider we only have two request classes, A and B, with response time goals 2000ms and 5000ms, respectively, where the time an individual request uses a thread is much smaller. During a period in which both request classes are sufficiently requested, say, zero think time and more clients than threads, we schedule to keep the average response time in the ratio 2:5 so that it is a common fraction or multiple of the stated goal. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ResponseTimeRequestClassMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("GoalMs")) {
         String getterName = "getGoalMs";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setGoalMs";
         }

         currentResult = new PropertyDescriptor("GoalMs", ResponseTimeRequestClassMBean.class, getterName, setterName);
         descriptors.put("GoalMs", currentResult);
         currentResult.setValue("description", "<p> A response time goal in milliseconds. </p> <p> You can either define a response time goal or keep the -1 default value, which acts as a placeholder for the response time goal. By doing so, you have not defined the response time goal at run time but can define one later. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
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
