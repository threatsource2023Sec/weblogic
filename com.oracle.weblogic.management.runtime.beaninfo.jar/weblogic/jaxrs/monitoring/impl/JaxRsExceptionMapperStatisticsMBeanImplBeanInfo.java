package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsExceptionMapperStatisticsRuntimeMBean;

public class JaxRsExceptionMapperStatisticsMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsExceptionMapperStatisticsRuntimeMBean.class;

   public JaxRsExceptionMapperStatisticsMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsExceptionMapperStatisticsMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsExceptionMapperStatisticsMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("Monitoring statistics of exception mapper executions. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsExceptionMapperStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ExceptionMapperCount")) {
         getterName = "getExceptionMapperCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExceptionMapperCount", JaxRsExceptionMapperStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExceptionMapperCount", currentResult);
         currentResult.setValue("description", "Get the statistics of execution of exception mappers. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<String,Long>");
      }

      if (!descriptors.containsKey("SuccessfulMappings")) {
         getterName = "getSuccessfulMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessfulMappings", JaxRsExceptionMapperStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessfulMappings", currentResult);
         currentResult.setValue("description", "Get count of all successful exception mappings. Successful exception mapping occurs when any exception mapper returns a valid response (even if response contains non-successful response status code). ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalMappings")) {
         getterName = "getTotalMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalMappings", JaxRsExceptionMapperStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalMappings", currentResult);
         currentResult.setValue("description", "Get count of exception mappings that were performed on exceptions. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnsuccessfulMappings")) {
         getterName = "getUnsuccessfulMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("UnsuccessfulMappings", JaxRsExceptionMapperStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnsuccessfulMappings", currentResult);
         currentResult.setValue("description", "Get count of all unsuccessful exception mappings. Unsuccessful exception mapping occurs when any exception mapping process does not produce a valid response. The reason can be that the exception mapper is not found, or is found but throws an exception. ");
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
