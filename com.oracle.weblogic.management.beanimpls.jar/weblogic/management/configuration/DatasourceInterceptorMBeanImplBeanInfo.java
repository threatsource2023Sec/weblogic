package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class DatasourceInterceptorMBeanImplBeanInfo extends InterceptorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DatasourceInterceptorMBean.class;

   public DatasourceInterceptorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DatasourceInterceptorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DatasourceInterceptorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface describes the configuration for a data source interceptor used to intercept the elastic scale up and scale down operations. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DatasourceInterceptorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionQuota")) {
         getterName = "getConnectionQuota";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionQuota";
         }

         currentResult = new PropertyDescriptor("ConnectionQuota", DatasourceInterceptorMBean.class, getterName, setterName);
         descriptors.put("ConnectionQuota", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionUrlsPattern")) {
         getterName = "getConnectionUrlsPattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionUrlsPattern";
         }

         currentResult = new PropertyDescriptor("ConnectionUrlsPattern", DatasourceInterceptorMBean.class, getterName, setterName);
         descriptors.put("ConnectionUrlsPattern", currentResult);
         currentResult.setValue("description", "Returns the regex pattern for the connection urls. The url pattern may cover multiple databases in case such databases are actually running on the same database machine. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptedTargetKey")) {
         getterName = "getInterceptedTargetKey";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptedTargetKey";
         }

         currentResult = new PropertyDescriptor("InterceptedTargetKey", DatasourceInterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptedTargetKey", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "ElasticServiceManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterceptorTypeName")) {
         getterName = "getInterceptorTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterceptorTypeName";
         }

         currentResult = new PropertyDescriptor("InterceptorTypeName", DatasourceInterceptorMBean.class, getterName, setterName);
         descriptors.put("InterceptorTypeName", currentResult);
         currentResult.setValue("description", "The HK2 name of the interceptor class. ");
         setPropertyDescriptorDefault(currentResult, "DatasourceInterceptor");
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
