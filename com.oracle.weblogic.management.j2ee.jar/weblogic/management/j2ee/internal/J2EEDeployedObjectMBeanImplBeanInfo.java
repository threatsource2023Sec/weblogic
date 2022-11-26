package weblogic.management.j2ee.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.j2ee.J2EEDeployedObjectMBean;

public class J2EEDeployedObjectMBeanImplBeanInfo extends J2EEManagedObjectMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = J2EEDeployedObjectMBean.class;

   public J2EEDeployedObjectMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public J2EEDeployedObjectMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.j2ee.internal.J2EEDeployedObjectMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.j2ee.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.j2ee.J2EEDeployedObjectMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("deploymentDescriptor")) {
         getterName = "getdeploymentDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("deploymentDescriptor", J2EEDeployedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("deploymentDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("productSpecificDeploymentDescriptor")) {
         getterName = "getproductSpecificDeploymentDescriptor";
         setterName = null;
         currentResult = new PropertyDescriptor("productSpecificDeploymentDescriptor", J2EEDeployedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("productSpecificDeploymentDescriptor", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("server")) {
         getterName = "getserver";
         setterName = null;
         currentResult = new PropertyDescriptor("server", J2EEDeployedObjectMBean.class, getterName, (String)setterName);
         descriptors.put("server", currentResult);
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
