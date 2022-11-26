package weblogic.jaxrs.monitoring.impl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JaxRsResponseStatisticsRuntimeMBean;

public class JaxRsResponseStatisticsMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JaxRsResponseStatisticsRuntimeMBean.class;

   public JaxRsResponseStatisticsMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JaxRsResponseStatisticsMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jaxrs.monitoring.impl.JaxRsResponseStatisticsMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.jaxrs.monitoring.impl");
      String description = (new String("Monitoring statistics of responses produced by application. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JaxRsResponseStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("LastResponseCode")) {
         getterName = "getLastResponseCode";
         setterName = null;
         currentResult = new PropertyDescriptor("LastResponseCode", JaxRsResponseStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastResponseCode", currentResult);
         currentResult.setValue("description", "Returns the HTTP response code of the last response written by the application. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseCodes")) {
         getterName = "getResponseCodes";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseCodes", JaxRsResponseStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResponseCodes", currentResult);
         currentResult.setValue("description", "Returns statistics of HTTP response codes produced by the application. Keys of a returned map are response status codes and values is the the count of responses with these status count. Values are measured since start of the application. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map<Integer,Long>");
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
