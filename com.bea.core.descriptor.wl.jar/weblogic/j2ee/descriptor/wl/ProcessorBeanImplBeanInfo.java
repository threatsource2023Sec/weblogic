package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ProcessorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ProcessorBean.class;

   public ProcessorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ProcessorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ProcessorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ProcessorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Metadata")) {
         getterName = "getMetadata";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMetadata";
         }

         currentResult = new PropertyDescriptor("Metadata", ProcessorBean.class, getterName, setterName);
         descriptors.put("Metadata", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ProcessorBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setType";
         }

         currentResult = new PropertyDescriptor("Type", ProcessorBean.class, getterName, setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
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
