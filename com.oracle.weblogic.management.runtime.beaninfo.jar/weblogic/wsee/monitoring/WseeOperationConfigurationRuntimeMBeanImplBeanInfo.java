package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseeOperationConfigurationRuntimeMBean;

public class WseeOperationConfigurationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeOperationConfigurationRuntimeMBean.class;

   public WseeOperationConfigurationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeOperationConfigurationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeOperationConfigurationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p> Encapsulates runtime information about a particular operation. The subject name attribute of this MBean will be the value of the local part of operation QName. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeOperationConfigurationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("PolicyAttachmentSupport")) {
         getterName = "getPolicyAttachmentSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyAttachmentSupport", WseeOperationConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyAttachmentSupport", currentResult);
         currentResult.setValue("description", "Get attachment support for this operation MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectName")) {
         getterName = "getPolicySubjectName";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectName", WseeOperationConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectName", currentResult);
         currentResult.setValue("description", "Get subject name for this operation MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectResourcePattern")) {
         getterName = "getPolicySubjectResourcePattern";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectResourcePattern", WseeOperationConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectResourcePattern", currentResult);
         currentResult.setValue("description", "The policySubject parameter must uniquely identify what application, module, service, and port (port or operation for WLS Policy) is targeted. The syntax currently used by JRF for Java EE Webservice Endpoints will be used: /{domain}/{instance}/{app}/WEBs|EJBs/{module }/WEBSERVICECLIENTs/{service-ref-name}/PORTs/{port}/OPERATIONs/{operation} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectType")) {
         getterName = "getPolicySubjectType";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectType", WseeOperationConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectType", currentResult);
         currentResult.setValue("description", "Get subject type for this operation MBean. ");
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
