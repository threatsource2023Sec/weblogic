package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ShareableBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ShareableBean.class;

   public ShareableBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ShareableBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ShareableBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Temporary location while the bean generation is sorted out in build. This will be relocated to weblogic.j2ee.descriptor.wl Copyright (c) 2014,2015, by Oracle Inc. All Rights Reserved. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ShareableBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Dir")) {
         getterName = "getDir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDir";
         }

         currentResult = new PropertyDescriptor("Dir", ShareableBean.class, getterName, setterName);
         descriptors.put("Dir", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("legalValues", new Object[]{"", "APP-INF-LIB", "LIB-DIR", "APP-INF-CLASSES", "WEB-INF-LIB", "WEB-INF-CLASSES"});
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Excludes")) {
         getterName = "getExcludes";
         setterName = null;
         currentResult = new PropertyDescriptor("Excludes", ShareableBean.class, getterName, setterName);
         descriptors.put("Excludes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Includes")) {
         getterName = "getIncludes";
         setterName = null;
         currentResult = new PropertyDescriptor("Includes", ShareableBean.class, getterName, setterName);
         descriptors.put("Includes", currentResult);
         currentResult.setValue("description", " ");
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
