package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CacheTransactionMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CacheTransactionMBean.class;

   public CacheTransactionMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CacheTransactionMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CacheTransactionMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CacheTransactionMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Concurrency")) {
         getterName = "getConcurrency";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcurrency";
         }

         currentResult = new PropertyDescriptor("Concurrency", CacheTransactionMBean.class, getterName, setterName);
         descriptors.put("Concurrency", currentResult);
         currentResult.setValue("description", "Setting this property to something other than none will make this cache transactional ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalValues", new Object[]{"Pessimistic", "Optimistic", "None"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IsolationLevel")) {
         getterName = "getIsolationLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIsolationLevel";
         }

         currentResult = new PropertyDescriptor("IsolationLevel", CacheTransactionMBean.class, getterName, setterName);
         descriptors.put("IsolationLevel", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "RepeatableRead");
         currentResult.setValue("legalValues", new Object[]{"ReadUncommitted", "ReadCommitted", "RepeatableRead"});
         currentResult.setValue("dynamic", Boolean.TRUE);
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
