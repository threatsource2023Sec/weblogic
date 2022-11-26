package weblogic.management.j2ee.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.j2ee.J2EEServerMBean;

public class J2EEServerMBeanImplBeanInfo extends J2EEManagedObjectMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = J2EEServerMBean.class;

   public J2EEServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public J2EEServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.j2ee.internal.J2EEServerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.j2ee.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.j2ee.J2EEServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("deployedObjects")) {
         getterName = "getdeployedObjects";
         setterName = null;
         currentResult = new PropertyDescriptor("deployedObjects", J2EEServerMBean.class, getterName, (String)setterName);
         descriptors.put("deployedObjects", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("javaVMs")) {
         getterName = "getjavaVMs";
         setterName = null;
         currentResult = new PropertyDescriptor("javaVMs", J2EEServerMBean.class, getterName, (String)setterName);
         descriptors.put("javaVMs", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("resources")) {
         getterName = "getresources";
         setterName = null;
         currentResult = new PropertyDescriptor("resources", J2EEServerMBean.class, getterName, (String)setterName);
         descriptors.put("resources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("serverVendor")) {
         getterName = "getserverVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("serverVendor", J2EEServerMBean.class, getterName, (String)setterName);
         descriptors.put("serverVendor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("serverVersion")) {
         getterName = "getserverVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("serverVersion", J2EEServerMBean.class, getterName, (String)setterName);
         descriptors.put("serverVersion", currentResult);
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
