package weblogic.connector.monitoring.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean;

public class ConnectorWorkManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorWorkManagerRuntimeMBean.class;

   public ConnectorWorkManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectorWorkManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.work.ConnectorWorkManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.connector.monitoring.work");
      String description = (new String("This class is used for monitoring Connector Work Manager of resource adapters. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveLongRunningRequests")) {
         getterName = "getActiveLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveLongRunningRequests", ConnectorWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p>Return The number of current active long-running work requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedLongRunningRequests")) {
         getterName = "getCompletedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedLongRunningRequests", ConnectorWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p>The number of completed long-running work requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConcurrentLongRunningRequests")) {
         getterName = "getMaxConcurrentLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxConcurrentLongRunningRequests", ConnectorWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxConcurrentLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p>The maximum number of allowed concurrent long-running work requests. New work submission will be rejected if current running long-running work requests exceed the limit.</p> ");
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
