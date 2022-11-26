package weblogic.kodo.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.persistence.PersistenceUnitRuntimeMBeanImplBeanInfo;

public class KodoPersistenceUnitRuntimeMBeanImplBeanInfo extends PersistenceUnitRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoPersistenceUnitRuntimeMBean.class;

   public KodoPersistenceUnitRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoPersistenceUnitRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.kodo.monitoring.KodoPersistenceUnitRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.0.0.0");
      beanDescriptor.setValue("package", "weblogic.kodo.monitoring");
      String description = (new String("Base class for all runtime mbeans that provide status of running modules. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DataCacheRuntimes")) {
         getterName = "getDataCacheRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("DataCacheRuntimes", KodoPersistenceUnitRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DataCacheRuntimes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceUnitName")) {
         getterName = "getPersistenceUnitName";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceUnitName", KodoPersistenceUnitRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PersistenceUnitName", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("QueryCacheRuntimes")) {
         getterName = "getQueryCacheRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryCacheRuntimes", KodoPersistenceUnitRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QueryCacheRuntimes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueryCompilationCacheRuntime")) {
         getterName = "getQueryCompilationCacheRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryCompilationCacheRuntime", KodoPersistenceUnitRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QueryCompilationCacheRuntime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
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
