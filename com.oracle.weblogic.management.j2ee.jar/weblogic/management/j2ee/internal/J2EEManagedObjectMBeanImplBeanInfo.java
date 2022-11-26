package weblogic.management.j2ee.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;
import weblogic.management.j2ee.J2EEManagedObjectMBean;

public class J2EEManagedObjectMBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = J2EEManagedObjectMBean.class;

   public J2EEManagedObjectMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public J2EEManagedObjectMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.j2ee.internal.J2EEManagedObjectMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.j2ee.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.j2ee.J2EEManagedObjectMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("objectName")) {
         getterName = "getobjectName";
         setterName = null;
         currentResult = new PropertyDescriptor("objectName", J2EEManagedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("objectName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("eventProvider")) {
         getterName = "iseventProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("eventProvider", J2EEManagedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("eventProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("stateManageable")) {
         getterName = "isstateManageable";
         setterName = null;
         currentResult = new PropertyDescriptor("stateManageable", J2EEManagedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("stateManageable", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("statisticsProvider")) {
         getterName = "isstatisticsProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("statisticsProvider", J2EEManagedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("statisticsProvider", currentResult);
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
