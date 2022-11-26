package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class LocalJMXBeanImplBeanInfo extends JMXBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LocalJMXBean.class;

   public LocalJMXBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LocalJMXBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.LocalJMXBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.LocalJMXBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EnableLogMBean")) {
         getterName = "getEnableLogMBean";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableLogMBean";
         }

         currentResult = new PropertyDescriptor("EnableLogMBean", LocalJMXBean.class, getterName, setterName);
         descriptors.put("EnableLogMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableRuntimeMBean")) {
         getterName = "getEnableRuntimeMBean";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableRuntimeMBean";
         }

         currentResult = new PropertyDescriptor("EnableRuntimeMBean", LocalJMXBean.class, getterName, setterName);
         descriptors.put("EnableRuntimeMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MBeanServerStrategy")) {
         getterName = "getMBeanServerStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMBeanServerStrategy";
         }

         currentResult = new PropertyDescriptor("MBeanServerStrategy", LocalJMXBean.class, getterName, setterName);
         descriptors.put("MBeanServerStrategy", currentResult);
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
