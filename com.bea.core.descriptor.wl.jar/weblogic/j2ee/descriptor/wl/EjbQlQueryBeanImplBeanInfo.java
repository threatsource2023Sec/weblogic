package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class EjbQlQueryBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = EjbQlQueryBean.class;

   public EjbQlQueryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EjbQlQueryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.EjbQlQueryBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML ejb-ql-queryType(@http://www.bea.com/ns/weblogic/90).  This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.EjbQlQueryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CachingName")) {
         getterName = "getCachingName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCachingName";
         }

         currentResult = new PropertyDescriptor("CachingName", EjbQlQueryBean.class, getterName, setterName);
         descriptors.put("CachingName", currentResult);
         currentResult.setValue("description", "Gets the \"caching-name\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupName")) {
         getterName = "getGroupName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGroupName";
         }

         currentResult = new PropertyDescriptor("GroupName", EjbQlQueryBean.class, getterName, setterName);
         descriptors.put("GroupName", currentResult);
         currentResult.setValue("description", "Gets the \"group-name\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicQl")) {
         getterName = "getWeblogicQl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWeblogicQl";
         }

         currentResult = new PropertyDescriptor("WeblogicQl", EjbQlQueryBean.class, getterName, setterName);
         descriptors.put("WeblogicQl", currentResult);
         currentResult.setValue("description", "Gets the \"weblogic-ql\" element ");
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
