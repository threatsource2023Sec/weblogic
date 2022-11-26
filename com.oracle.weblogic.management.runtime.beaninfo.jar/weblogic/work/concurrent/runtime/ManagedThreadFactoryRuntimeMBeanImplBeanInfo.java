package weblogic.work.concurrent.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean;

public class ManagedThreadFactoryRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedThreadFactoryRuntimeMBean.class;

   public ManagedThreadFactoryRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedThreadFactoryRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.concurrent.runtime.ManagedThreadFactoryRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work.concurrent.runtime");
      String description = (new String("ManagedThreadFactory Runtime information. It can be the information of a partition level MTF, an application level MTF or a regular JSR236 MTF. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the application this ManagedThreadFactory is associated with</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedThreadsCount")) {
         getterName = "getCompletedThreadsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedThreadsCount", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedThreadsCount", currentResult);
         currentResult.setValue("description", "<p>The number of completed threads.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleName", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the module this ManagedThreadFactory is associated with</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the partition this ManagedThreadFactory is associated with</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("RejectedNewThreadRequests")) {
         getterName = "getRejectedNewThreadRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedNewThreadRequests", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedNewThreadRequests", currentResult);
         currentResult.setValue("description", "<p>The number of newThread method invocations that have been rejected because the limit of running threads was exceeded. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunningThreadsCount")) {
         getterName = "getRunningThreadsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RunningThreadsCount", ManagedThreadFactoryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RunningThreadsCount", currentResult);
         currentResult.setValue("description", "<p>The number of running threads.</p> ");
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
