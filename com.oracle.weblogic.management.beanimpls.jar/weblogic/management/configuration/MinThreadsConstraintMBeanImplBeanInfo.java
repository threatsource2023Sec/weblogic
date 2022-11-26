package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MinThreadsConstraintMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MinThreadsConstraintMBean.class;

   public MinThreadsConstraintMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MinThreadsConstraintMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MinThreadsConstraintMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean defines the minimum number of concurrent threads that should allocated to this constraint provided there are enough pending requests.</p>  <p> Multiple WorkManagers can share a common MinThreadsConstraint. MinThreadsConstraint perform their own bookkeeping and demand a thread when the constraint is not met and there are enough requests. </p>  <p> MinThreadsConstraint should be used to tell the WebLogic Server that a certain number of minimum threads are necessary to prevent server to server deadlocks. Although the server is self-tuning, specifying MinThreadsConstraint ensures that the server guarantees minimum threads to avoid the distributed deadlocks. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MinThreadsConstraintMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Count")) {
         String getterName = "getCount";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setCount";
         }

         currentResult = new PropertyDescriptor("Count", MinThreadsConstraintMBean.class, getterName, setterName);
         descriptors.put("Count", currentResult);
         currentResult.setValue("description", "Minimum number of concurrent threads executing requests that share this constraint. <p> A count of 0 or -1 is treated as if the constraint is not present. This means that the constraint is ignored for these two values. A count > 0 can be dynamically changed to 0 to indicate that constraint enforcement is no longer needed. </p> ");
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
