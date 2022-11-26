package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class EntityCacheBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = EntityCacheBean.class;

   public EntityCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.EntityCacheBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConcurrencyStrategy")) {
         getterName = "getConcurrencyStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcurrencyStrategy";
         }

         currentResult = new PropertyDescriptor("ConcurrencyStrategy", EntityCacheBean.class, getterName, setterName);
         descriptors.put("ConcurrencyStrategy", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Database");
         currentResult.setValue("legalValues", new Object[]{"Exclusive", "Database", "ReadOnly", "Optimistic", "ReadOnlyExclusive"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", EntityCacheBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleTimeoutSeconds")) {
         getterName = "getIdleTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("IdleTimeoutSeconds", EntityCacheBean.class, getterName, setterName);
         descriptors.put("IdleTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxBeansInCache")) {
         getterName = "getMaxBeansInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxBeansInCache";
         }

         currentResult = new PropertyDescriptor("MaxBeansInCache", EntityCacheBean.class, getterName, setterName);
         descriptors.put("MaxBeansInCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxQueriesInCache")) {
         getterName = "getMaxQueriesInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxQueriesInCache";
         }

         currentResult = new PropertyDescriptor("MaxQueriesInCache", EntityCacheBean.class, getterName, setterName);
         descriptors.put("MaxQueriesInCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadTimeoutSeconds")) {
         getterName = "getReadTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReadTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ReadTimeoutSeconds", EntityCacheBean.class, getterName, setterName);
         descriptors.put("ReadTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheBetweenTransactions")) {
         getterName = "isCacheBetweenTransactions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheBetweenTransactions";
         }

         currentResult = new PropertyDescriptor("CacheBetweenTransactions", EntityCacheBean.class, getterName, setterName);
         descriptors.put("CacheBetweenTransactions", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisableReadyInstances")) {
         getterName = "isDisableReadyInstances";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisableReadyInstances";
         }

         currentResult = new PropertyDescriptor("DisableReadyInstances", EntityCacheBean.class, getterName, setterName);
         descriptors.put("DisableReadyInstances", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
