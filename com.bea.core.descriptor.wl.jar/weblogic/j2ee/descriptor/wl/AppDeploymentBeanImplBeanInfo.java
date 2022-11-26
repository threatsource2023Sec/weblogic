package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class AppDeploymentBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = AppDeploymentBean.class;

   public AppDeploymentBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AppDeploymentBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.AppDeploymentBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.AppDeploymentBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("GeneratedVersion")) {
         getterName = "getGeneratedVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGeneratedVersion";
         }

         currentResult = new PropertyDescriptor("GeneratedVersion", AppDeploymentBean.class, getterName, setterName);
         descriptors.put("GeneratedVersion", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", AppDeploymentBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetireTimeout")) {
         getterName = "getRetireTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetireTimeout";
         }

         currentResult = new PropertyDescriptor("RetireTimeout", AppDeploymentBean.class, getterName, setterName);
         descriptors.put("RetireTimeout", currentResult);
         currentResult.setValue("description", "This parameter governs how much time the managed server will wait before retiring that version (the one being replaced). A value of 0 indicates immediate retirement. The value is in seconds and must be 0 or more ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourcePath")) {
         getterName = "getSourcePath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourcePath";
         }

         currentResult = new PropertyDescriptor("SourcePath", AppDeploymentBean.class, getterName, setterName);
         descriptors.put("SourcePath", currentResult);
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
