package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseePortConfigurationRuntimeMBean;

public class WseePortConfigurationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseePortConfigurationRuntimeMBean.class;

   public WseePortConfigurationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseePortConfigurationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseePortConfigurationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p> Encapsulates runtime policy subject information about a particular Port. The subject name attribute of this MBean will be the value of the local part of port QName. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseePortConfigurationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Operations")) {
         getterName = "getOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("Operations", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Operations", currentResult);
         currentResult.setValue("description", "Specifies the array of operation configurations that are associated with this port. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicyAttachmentSupport")) {
         getterName = "getPolicyAttachmentSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyAttachmentSupport", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyAttachmentSupport", currentResult);
         currentResult.setValue("description", "Get attachment support for this operation MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectAbsolutePortableExpression")) {
         getterName = "getPolicySubjectAbsolutePortableExpression";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectAbsolutePortableExpression", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectAbsolutePortableExpression", currentResult);
         currentResult.setValue("description", "The policySubject AbsolutePortableExpression This is the WSM ResourcePattern AbsolutePortableExpression string ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectName")) {
         getterName = "getPolicySubjectName";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectName", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectName", currentResult);
         currentResult.setValue("description", "Get subject name for this operation MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectResourcePattern")) {
         getterName = "getPolicySubjectResourcePattern";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectResourcePattern", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectResourcePattern", currentResult);
         currentResult.setValue("description", "The policySubject parameter must uniquely identify what application, module, service, and port (port or operation for WLS Policy) is targeted. The syntax currently used by JRF for J2EE WebService Endpoints will be used: /{domain}/{instance}/{app}/WEBs|EJBs/{module }/WEBSERVICECLIENTs/{service-ref-name}/PORTs/{port} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectType")) {
         getterName = "getPolicySubjectType";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectType", WseePortConfigurationRuntimeMBean.class, getterName, (String)setterName);
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
