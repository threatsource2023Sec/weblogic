package weblogic.spring.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SpringTransactionManagerRuntimeMBean;

public class SpringTransactionManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SpringTransactionManagerRuntimeMBean.class;

   public SpringTransactionManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SpringTransactionManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.spring.monitoring.SpringTransactionManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.1.0");
      beanDescriptor.setValue("package", "weblogic.spring.monitoring");
      String description = (new String("This MBean represents statistics gathered for org.springframework.transaction.support.AbstractPlatformTransactionManager ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SpringTransactionManagerRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ApplicationContextDisplayName", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationContextDisplayName", currentResult);
         currentResult.setValue("description", "<p>The display name of the Application Context that this bean is from</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeanId")) {
         getterName = "getBeanId";
         setterName = null;
         currentResult = new PropertyDescriptor("BeanId", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeanId", currentResult);
         currentResult.setValue("description", "<p>Name of the Spring bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CommitCount")) {
         getterName = "getCommitCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CommitCount", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CommitCount", currentResult);
         currentResult.setValue("description", "<p>This returns the number of time commit was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResumeCount")) {
         getterName = "getResumeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResumeCount", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResumeCount", currentResult);
         currentResult.setValue("description", "<p>This returns the number of time resume was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("RollbackCount")) {
         getterName = "getRollbackCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RollbackCount", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RollbackCount", currentResult);
         currentResult.setValue("description", "<p>This returns the number of time rollback was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SuspendCount")) {
         getterName = "getSuspendCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuspendCount", SpringTransactionManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuspendCount", currentResult);
         currentResult.setValue("description", "<p>This returns the number of time suspend was called</p> ");
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
