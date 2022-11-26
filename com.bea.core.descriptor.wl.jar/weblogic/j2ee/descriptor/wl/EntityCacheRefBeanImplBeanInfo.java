package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class EntityCacheRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = EntityCacheRefBean.class;

   public EntityCacheRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EntityCacheRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.EntityCacheRefBean");
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

         currentResult = new PropertyDescriptor("ConcurrencyStrategy", EntityCacheRefBean.class, getterName, setterName);
         descriptors.put("ConcurrencyStrategy", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Database");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityCacheName")) {
         getterName = "getEntityCacheName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityCacheName";
         }

         currentResult = new PropertyDescriptor("EntityCacheName", EntityCacheRefBean.class, getterName, setterName);
         descriptors.put("EntityCacheName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EstimatedBeanSize")) {
         getterName = "getEstimatedBeanSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEstimatedBeanSize";
         }

         currentResult = new PropertyDescriptor("EstimatedBeanSize", EntityCacheRefBean.class, getterName, setterName);
         descriptors.put("EstimatedBeanSize", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", EntityCacheRefBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("IdleTimeoutSeconds", EntityCacheRefBean.class, getterName, setterName);
         descriptors.put("IdleTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadTimeoutSeconds")) {
         getterName = "getReadTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReadTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ReadTimeoutSeconds", EntityCacheRefBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("CacheBetweenTransactions", EntityCacheRefBean.class, getterName, setterName);
         descriptors.put("CacheBetweenTransactions", currentResult);
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
