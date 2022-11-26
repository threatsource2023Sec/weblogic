package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class LogFactoryImplBeanImplBeanInfo extends LogBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LogFactoryImplBean.class;

   public LogFactoryImplBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LogFactoryImplBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.LogFactoryImplBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.LogFactoryImplBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultLevel")) {
         getterName = "getDefaultLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultLevel";
         }

         currentResult = new PropertyDescriptor("DefaultLevel", LogFactoryImplBean.class, getterName, setterName);
         descriptors.put("DefaultLevel", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "3");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DiagnosticContext")) {
         getterName = "getDiagnosticContext";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDiagnosticContext";
         }

         currentResult = new PropertyDescriptor("DiagnosticContext", LogFactoryImplBean.class, getterName, setterName);
         descriptors.put("DiagnosticContext", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("File")) {
         getterName = "getFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFile";
         }

         currentResult = new PropertyDescriptor("File", LogFactoryImplBean.class, getterName, setterName);
         descriptors.put("File", currentResult);
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
