package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseePortPolicyRuntimeMBean;

public class WseePortPolicyRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseePortPolicyRuntimeMBean.class;

   public WseePortPolicyRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseePortPolicyRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseePortPolicyRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Describes the Web service security policy state of a particular Web service port.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseePortPolicyRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AuthenticationSuccesses")) {
         getterName = "getAuthenticationSuccesses";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthenticationSuccesses", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AuthenticationSuccesses", currentResult);
         currentResult.setValue("description", "<p> Total number of authentication successes detected for this port. Only incoming message processing can add to the success count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthenticationViolations")) {
         getterName = "getAuthenticationViolations";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthenticationViolations", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AuthenticationViolations", currentResult);
         currentResult.setValue("description", "<p> Total number of authentication violations generated for this port. Only incoming message processing can add to the violation count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthorizationSuccesses")) {
         getterName = "getAuthorizationSuccesses";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthorizationSuccesses", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AuthorizationSuccesses", currentResult);
         currentResult.setValue("description", "<p> Total number of authorization successes detected for this port. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AuthorizationViolations")) {
         getterName = "getAuthorizationViolations";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthorizationViolations", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AuthorizationViolations", currentResult);
         currentResult.setValue("description", "<p> Total number of authorization violations generated for this port. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfidentialitySuccesses")) {
         getterName = "getConfidentialitySuccesses";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfidentialitySuccesses", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfidentialitySuccesses", currentResult);
         currentResult.setValue("description", "<p> Total number of confidentiality successes generated for this port. Both outgoing and incoming message processing can add to the success count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfidentialityViolations")) {
         getterName = "getConfidentialityViolations";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfidentialityViolations", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfidentialityViolations", currentResult);
         currentResult.setValue("description", "<p> Total number of confidentiality violations generated for this port. Both outgoing and incoming message processing can add to the violation count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegritySuccesses")) {
         getterName = "getIntegritySuccesses";
         setterName = null;
         currentResult = new PropertyDescriptor("IntegritySuccesses", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IntegritySuccesses", currentResult);
         currentResult.setValue("description", "<p> Total number of integrity successes generated for this port. Both outgoing and incoming message processing can add to the success count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegrityViolations")) {
         getterName = "getIntegrityViolations";
         setterName = null;
         currentResult = new PropertyDescriptor("IntegrityViolations", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IntegrityViolations", currentResult);
         currentResult.setValue("description", "<p> Total number of integrity violations generated for this port. Both outgoing and incoming message processing can add to the violation count. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicyFaults")) {
         getterName = "getPolicyFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyFaults", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyFaults", currentResult);
         currentResult.setValue("description", "<p> Total number of policy faults generated for this port. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalFaults")) {
         getterName = "getTotalFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFaults", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFaults", currentResult);
         currentResult.setValue("description", "<p> Total number of security faults and violations generated by this port. </p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSecurityFaults")) {
         getterName = "getTotalSecurityFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSecurityFaults", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSecurityFaults", currentResult);
         currentResult.setValue("description", "<p> Total number of security faults and violations generated for this port. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalViolations")) {
         getterName = "getTotalViolations";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalViolations", WseePortPolicyRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalViolations", currentResult);
         currentResult.setValue("description", "<p> Total number of authentication, integrity, and confidentialy violations. Both outgoing and incoming message processing can add to the violation count. </p> ");
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
