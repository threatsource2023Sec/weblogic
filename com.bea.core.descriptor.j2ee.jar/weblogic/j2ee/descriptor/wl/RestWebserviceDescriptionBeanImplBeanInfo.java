package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class RestWebserviceDescriptionBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = RestWebserviceDescriptionBean.class;

   public RestWebserviceDescriptionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RestWebserviceDescriptionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("RestWebservicesBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Description bean holding basic info about a REST WebService available in (web) application.  It contains info about: <ul> <li>JAX-RS Application class name,</li> <li>servlet or filter name,</li> <li>array of base URIs the service is registered at.</li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationBaseUris")) {
         getterName = "getApplicationBaseUris";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationBaseUris";
         }

         currentResult = new PropertyDescriptor("ApplicationBaseUris", RestWebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("ApplicationBaseUris", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationClassName")) {
         getterName = "getApplicationClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationClassName";
         }

         currentResult = new PropertyDescriptor("ApplicationClassName", RestWebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("ApplicationClassName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FilterName")) {
         getterName = "getFilterName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFilterName";
         }

         currentResult = new PropertyDescriptor("FilterName", RestWebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("FilterName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServletName")) {
         getterName = "getServletName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServletName";
         }

         currentResult = new PropertyDescriptor("ServletName", RestWebserviceDescriptionBean.class, getterName, setterName);
         descriptors.put("ServletName", currentResult);
         currentResult.setValue("description", " ");
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
