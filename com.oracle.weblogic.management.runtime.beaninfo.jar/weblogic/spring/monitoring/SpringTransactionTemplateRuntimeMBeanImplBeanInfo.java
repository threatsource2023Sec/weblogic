package weblogic.spring.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SpringTransactionTemplateRuntimeMBean;

public class SpringTransactionTemplateRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SpringTransactionTemplateRuntimeMBean.class;

   public SpringTransactionTemplateRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SpringTransactionTemplateRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.spring.monitoring.SpringTransactionTemplateRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.1.0");
      beanDescriptor.setValue("package", "weblogic.spring.monitoring");
      String description = (new String("This MBean represents statistics gathered for org.springframework.transaction.support.TransactionTemplate ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SpringTransactionTemplateRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationContextDisplayName")) {
         getterName = "getApplicationContextDisplayName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationContextDisplayName", SpringTransactionTemplateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationContextDisplayName", currentResult);
         currentResult.setValue("description", "<p>The display name of the Application Context that this bean is from</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageExecuteTime")) {
         getterName = "getAverageExecuteTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageExecuteTime", SpringTransactionTemplateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageExecuteTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required to execute</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("BeanId")) {
         getterName = "getBeanId";
         setterName = null;
         currentResult = new PropertyDescriptor("BeanId", SpringTransactionTemplateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeanId", currentResult);
         currentResult.setValue("description", "<p>Name of the Spring bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExecuteCount")) {
         getterName = "getExecuteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteCount", SpringTransactionTemplateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteCount", currentResult);
         currentResult.setValue("description", "<p>This returns the number of time execute was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExecuteFailedCount")) {
         getterName = "getExecuteFailedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteFailedCount", SpringTransactionTemplateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteFailedCount", currentResult);
         currentResult.setValue("description", "<p>The number of executes that failed</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
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
