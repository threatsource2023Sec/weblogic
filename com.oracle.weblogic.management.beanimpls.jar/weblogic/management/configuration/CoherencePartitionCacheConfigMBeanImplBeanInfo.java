package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherencePartitionCacheConfigMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherencePartitionCacheConfigMBean.class;

   public CoherencePartitionCacheConfigMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherencePartitionCacheConfigMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherencePartitionCacheConfigMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>The CoherencePartitionCacheConfigMBean is used to define which Coherence caches are shared/not-shared in an MT environment as well as specific cache properties that apply to deployments underneath a partition.</p>  <p>Note: the Name attribute is inherited from ConfigurationMBean and is a key only.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherencePartitionCacheConfigMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationName";
         }

         currentResult = new PropertyDescriptor("ApplicationName", CoherencePartitionCacheConfigMBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "The name of the GAR application that this setting should apply to. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheName")) {
         getterName = "getCacheName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheName";
         }

         currentResult = new PropertyDescriptor("CacheName", CoherencePartitionCacheConfigMBean.class, getterName, setterName);
         descriptors.put("CacheName", currentResult);
         currentResult.setValue("description", "The name of the cache that the settings should apply to. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherencePartitionCacheProperties")) {
         getterName = "getCoherencePartitionCacheProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherencePartitionCacheProperties", CoherencePartitionCacheConfigMBean.class, getterName, setterName);
         descriptors.put("CoherencePartitionCacheProperties", currentResult);
         currentResult.setValue("description", "Return an array of all the Coherence Partition Cache Properties ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherencePartitionCacheProperty");
         currentResult.setValue("destroyer", "destroyCoherencePartitionCacheProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Shared")) {
         getterName = "isShared";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShared";
         }

         currentResult = new PropertyDescriptor("Shared", CoherencePartitionCacheConfigMBean.class, getterName, setterName);
         descriptors.put("Shared", currentResult);
         currentResult.setValue("description", "Specifies whether you want this cache to be shared. ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherencePartitionCacheConfigMBean.class.getMethod("createCoherencePartitionCacheProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the property ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Factory method for creating Coherence Partition Cache Property beans ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherencePartitionCacheProperties");
      }

      mth = CoherencePartitionCacheConfigMBean.class.getMethod("destroyCoherencePartitionCacheProperty", CoherencePartitionCachePropertyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("param", "the Coherence Cache Property bean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy and remove the Coherence Cache Property bean ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherencePartitionCacheProperties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherencePartitionCacheConfigMBean.class.getMethod("lookupCoherencePartitionCacheProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the property ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup the named Coherence Cache Property ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherencePartitionCacheProperties");
      }

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
