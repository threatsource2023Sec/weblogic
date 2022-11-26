package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServicePhysicalStoreMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServicePhysicalStoreMBean.class;

   public WebServicePhysicalStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServicePhysicalStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServicePhysicalStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents a physical store for web services. Used only for non-WLS containers (e.g. other app-server or standalone client).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServicePhysicalStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Location")) {
         getterName = "getLocation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocation";
         }

         currentResult = new PropertyDescriptor("Location", WebServicePhysicalStoreMBean.class, getterName, setterName);
         descriptors.put("Location", currentResult);
         currentResult.setValue("description", "For file stores, specifies the directory that will hold all files related to the store. The actual file names are controlled internally by the file store implementation. For other types of stores, this location may be a URL or URI, or other description string. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebServicePhysicalStoreMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Get the name of this physical store. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreType")) {
         getterName = "getStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreType";
         }

         currentResult = new PropertyDescriptor("StoreType", WebServicePhysicalStoreMBean.class, getterName, setterName);
         descriptors.put("StoreType", currentResult);
         currentResult.setValue("description", "Get the type of this physical store. ");
         setPropertyDescriptorDefault(currentResult, "FILE");
         currentResult.setValue("legalValues", new Object[]{"FILE", "JDBC"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SynchronousWritePolicy")) {
         getterName = "getSynchronousWritePolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSynchronousWritePolicy";
         }

         currentResult = new PropertyDescriptor("SynchronousWritePolicy", WebServicePhysicalStoreMBean.class, getterName, setterName);
         descriptors.put("SynchronousWritePolicy", currentResult);
         currentResult.setValue("description", "Specifies the algorithm used when performing synchronous writes to the physical store. ");
         setPropertyDescriptorDefault(currentResult, "CACHE_FLUSH");
         currentResult.setValue("legalValues", new Object[]{"DISABLED", "CACHE_FLUSH", "DIRECT_WRITE"});
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
