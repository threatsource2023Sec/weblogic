package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class MaxThreadsConstraintMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MaxThreadsConstraintMBean.class;

   public MaxThreadsConstraintMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MaxThreadsConstraintMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MaxThreadsConstraintMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean defines the max number of concurrent threads that can execute requests sharing this max constraint.</p> <p> MaxThreadsConstraint can be used to tell the server that the requests are constrained by an external resource like a database and allocating more threads that the external resource limit is not going to help since the extra threads are just going to wait. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MaxThreadsConstraintMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionPoolName")) {
         getterName = "getConnectionPoolName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPoolName";
         }

         currentResult = new PropertyDescriptor("ConnectionPoolName", MaxThreadsConstraintMBean.class, getterName, setterName);
         descriptors.put("ConnectionPoolName", currentResult);
         currentResult.setValue("description", "<p>Name of the connection pool whose size is taken as the max constraint.</p> <p> This can be the name of a JDBC data source. The max capacity of the data source is used as the constraint. </p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Count")) {
         getterName = "getCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCount";
         }

         currentResult = new PropertyDescriptor("Count", MaxThreadsConstraintMBean.class, getterName, setterName);
         descriptors.put("Count", currentResult);
         currentResult.setValue("description", "<p>Maximum number of concurrent threads that can execute requests sharing this constraint.</p> <p> A count of 0 or -1 is treated as if the constraint is not present. This means that no constraint is enforced for these two values. A count > 0 can be dynamically changed to 0 to indicate that constraint enforcement is no longer needed. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("QueueSize")) {
         getterName = "getQueueSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQueueSize";
         }

         currentResult = new PropertyDescriptor("QueueSize", MaxThreadsConstraintMBean.class, getterName, setterName);
         descriptors.put("QueueSize", currentResult);
         currentResult.setValue("description", "<p>Desired size of the MaxThreadsConstraint queue for requests pending execution.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(8192));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(256));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
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
