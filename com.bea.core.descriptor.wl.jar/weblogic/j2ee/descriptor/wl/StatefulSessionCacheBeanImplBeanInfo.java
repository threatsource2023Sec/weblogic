package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class StatefulSessionCacheBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = StatefulSessionCacheBean.class;

   public StatefulSessionCacheBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public StatefulSessionCacheBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionCacheBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.StatefulSessionCacheBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheType")) {
         getterName = "getCacheType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheType";
         }

         currentResult = new PropertyDescriptor("CacheType", StatefulSessionCacheBean.class, getterName, setterName);
         descriptors.put("CacheType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NRU");
         currentResult.setValue("legalValues", new Object[]{"NRU", "LRU"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", StatefulSessionCacheBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("IdleTimeoutSeconds", StatefulSessionCacheBean.class, getterName, setterName);
         descriptors.put("IdleTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMin", new Integer(0));
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

         currentResult = new PropertyDescriptor("MaxBeansInCache", StatefulSessionCacheBean.class, getterName, setterName);
         descriptors.put("MaxBeansInCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionTimeoutSeconds")) {
         getterName = "getSessionTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("SessionTimeoutSeconds", StatefulSessionCacheBean.class, getterName, setterName);
         descriptors.put("SessionTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(600));
         currentResult.setValue("legalMin", new Integer(0));
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
