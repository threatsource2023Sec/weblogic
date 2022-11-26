package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class PersistenceUnitBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PersistenceUnitBean.class;

   public PersistenceUnitBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistenceUnitBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.PersistenceUnitBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.PersistenceUnitBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Classes")) {
         getterName = "getClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("Classes", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("Classes", currentResult);
         currentResult.setValue("description", "The list of persistent classes used at runtime. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "The description of this unit. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExcludeUnlistedClasses")) {
         getterName = "getExcludeUnlistedClasses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludeUnlistedClasses";
         }

         currentResult = new PropertyDescriptor("ExcludeUnlistedClasses", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("ExcludeUnlistedClasses", currentResult);
         currentResult.setValue("description", "Controls whether to exclude classes unlisted by jar, file, or class. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JarFiles")) {
         getterName = "getJarFiles";
         setterName = null;
         currentResult = new PropertyDescriptor("JarFiles", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("JarFiles", currentResult);
         currentResult.setValue("description", "A list of jars to scan for persistent classes. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JtaDataSource")) {
         getterName = "getJtaDataSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJtaDataSource";
         }

         currentResult = new PropertyDescriptor("JtaDataSource", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("JtaDataSource", currentResult);
         currentResult.setValue("description", "The JNDI name of the DataSource enlisted in the current JTA transaction. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappingFiles")) {
         getterName = "getMappingFiles";
         setterName = null;
         currentResult = new PropertyDescriptor("MappingFiles", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("MappingFiles", currentResult);
         currentResult.setValue("description", "The list of mapping files used at runtime. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name used to reference this given configuration. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonJtaDataSource")) {
         getterName = "getNonJtaDataSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonJtaDataSource";
         }

         currentResult = new PropertyDescriptor("NonJtaDataSource", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("NonJtaDataSource", currentResult);
         currentResult.setValue("description", "The JNDI name for an unmanaged DataSource. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Additional implementation specific properties to store with this persistence unit. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Provider")) {
         getterName = "getProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("Provider", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("Provider", currentResult);
         currentResult.setValue("description", "The provider class to provide persistence services. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SharedCacheMode")) {
         getterName = "getSharedCacheMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSharedCacheMode";
         }

         currentResult = new PropertyDescriptor("SharedCacheMode", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("SharedCacheMode", currentResult);
         currentResult.setValue("description", "The caching type for the persistence unit ");
         setPropertyDescriptorDefault(currentResult, "UNSPECIFIED");
         currentResult.setValue("legalValues", new Object[]{"ALL", "NONE", "ENABLE_SELECTIVE", "DISABLE_SELECTIVE", "UNSPECIFIED"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionType")) {
         getterName = "getTransactionType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionType";
         }

         currentResult = new PropertyDescriptor("TransactionType", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("TransactionType", currentResult);
         currentResult.setValue("description", "The transaction type for the given entity manager. ");
         setPropertyDescriptorDefault(currentResult, "JTA");
         currentResult.setValue("legalValues", new Object[]{"JTA", "RESOURCE_LOCAL"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationMode")) {
         getterName = "getValidationMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationMode";
         }

         currentResult = new PropertyDescriptor("ValidationMode", PersistenceUnitBean.class, getterName, setterName);
         descriptors.put("ValidationMode", currentResult);
         currentResult.setValue("description", "The validation mode to be used for the persistence unit. ");
         setPropertyDescriptorDefault(currentResult, "AUTO");
         currentResult.setValue("legalValues", new Object[]{"AUTO", "CALLBACK", "NONE"});
         currentResult.setValue("exclude", Boolean.TRUE);
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
