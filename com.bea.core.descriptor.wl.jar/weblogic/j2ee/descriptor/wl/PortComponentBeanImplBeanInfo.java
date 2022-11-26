package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class PortComponentBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PortComponentBean.class;

   public PortComponentBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortComponentBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PortComponentBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML port-componentType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PortComponentBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AuthConstraint")) {
         getterName = "getAuthConstraint";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthConstraint", PortComponentBean.class, getterName, setterName);
         descriptors.put("AuthConstraint", currentResult);
         currentResult.setValue("description", "Gets the \"auth-constraint\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAuthConstraint");
         currentResult.setValue("creator", "createAuthConstraint");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BufferingConfig")) {
         getterName = "getBufferingConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("BufferingConfig", PortComponentBean.class, getterName, setterName);
         descriptors.put("BufferingConfig", currentResult);
         currentResult.setValue("description", "Gets the buffering configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createBufferingConfig");
         currentResult.setValue("destroyer", "destroyBufferingConfig");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CallbackProtocol")) {
         getterName = "getCallbackProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCallbackProtocol";
         }

         currentResult = new PropertyDescriptor("CallbackProtocol", PortComponentBean.class, getterName, setterName);
         descriptors.put("CallbackProtocol", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "http");
         currentResult.setValue("legalValues", new Object[]{"http", "https", "jms"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentListenerList")) {
         getterName = "getDeploymentListenerList";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentListenerList", PortComponentBean.class, getterName, setterName);
         descriptors.put("DeploymentListenerList", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDeploymentListenerList");
         currentResult.setValue("destroyer", "destroyDeploymentListenerList");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpResponseBuffersize")) {
         getterName = "getHttpResponseBuffersize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpResponseBuffersize";
         }

         currentResult = new PropertyDescriptor("HttpResponseBuffersize", PortComponentBean.class, getterName, setterName);
         descriptors.put("HttpResponseBuffersize", currentResult);
         currentResult.setValue("description", "Gets the \"http-response-buffersize\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoggingLevel")) {
         getterName = "getLoggingLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoggingLevel";
         }

         currentResult = new PropertyDescriptor("LoggingLevel", PortComponentBean.class, getterName, setterName);
         descriptors.put("LoggingLevel", currentResult);
         currentResult.setValue("description", "Gets the \"logging-level\" element ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginConfig")) {
         getterName = "getLoginConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("LoginConfig", PortComponentBean.class, getterName, setterName);
         descriptors.put("LoginConfig", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLoginConfig");
         currentResult.setValue("creator", "createLoginConfig");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Operations")) {
         getterName = "getOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("Operations", PortComponentBean.class, getterName, setterName);
         descriptors.put("Operations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOperation");
         currentResult.setValue("creator", "createOperation");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceConfig")) {
         getterName = "getPersistenceConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceConfig", PortComponentBean.class, getterName, setterName);
         descriptors.put("PersistenceConfig", currentResult);
         currentResult.setValue("description", "Gets the persistence configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPersistenceConfig");
         currentResult.setValue("destroyer", "destroyPersistenceConfig");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortComponentName")) {
         getterName = "getPortComponentName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortComponentName";
         }

         currentResult = new PropertyDescriptor("PortComponentName", PortComponentBean.class, getterName, setterName);
         descriptors.put("PortComponentName", currentResult);
         currentResult.setValue("description", "Gets the \"port-component-name\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReliabilityConfig")) {
         getterName = "getReliabilityConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("ReliabilityConfig", PortComponentBean.class, getterName, setterName);
         descriptors.put("ReliabilityConfig", currentResult);
         currentResult.setValue("description", "Gets the reliability configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyReliabilityConfig");
         currentResult.setValue("creator", "createReliabilityConfig");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceEndpointAddress")) {
         getterName = "getServiceEndpointAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceEndpointAddress", PortComponentBean.class, getterName, setterName);
         descriptors.put("ServiceEndpointAddress", currentResult);
         currentResult.setValue("description", "Gets the \"service-endpoint-address\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyServiceEndpointAddress");
         currentResult.setValue("creator", "createServiceEndpointAddress");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SoapjmsServiceEndpointAddress")) {
         getterName = "getSoapjmsServiceEndpointAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("SoapjmsServiceEndpointAddress", PortComponentBean.class, getterName, setterName);
         descriptors.put("SoapjmsServiceEndpointAddress", currentResult);
         currentResult.setValue("description", "Gets the SOAP/JMS transport configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSoapjmsServiceEndpointAddress");
         currentResult.setValue("destroyer", "destroySoapjmsServiceEndpointAddress");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StreamAttachments")) {
         getterName = "getStreamAttachments";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStreamAttachments";
         }

         currentResult = new PropertyDescriptor("StreamAttachments", PortComponentBean.class, getterName, setterName);
         descriptors.put("StreamAttachments", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionTimeout")) {
         getterName = "getTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("TransactionTimeout", PortComponentBean.class, getterName, setterName);
         descriptors.put("TransactionTimeout", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransportGuarantee")) {
         getterName = "getTransportGuarantee";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransportGuarantee";
         }

         currentResult = new PropertyDescriptor("TransportGuarantee", PortComponentBean.class, getterName, setterName);
         descriptors.put("TransportGuarantee", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "NONE");
         currentResult.setValue("legalValues", new Object[]{"NONE", "INTEGRAL", "CONFIDENTIAL"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WSATConfig")) {
         getterName = "getWSATConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("WSATConfig", PortComponentBean.class, getterName, setterName);
         descriptors.put("WSATConfig", currentResult);
         currentResult.setValue("description", "Gets the WS-AT configuration for this web service port component. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWSATConfig");
         currentResult.setValue("destroyer", "destroyWSATConfig");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Wsdl")) {
         getterName = "getWsdl";
         setterName = null;
         currentResult = new PropertyDescriptor("Wsdl", PortComponentBean.class, getterName, setterName);
         descriptors.put("Wsdl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWsdl");
         currentResult.setValue("creator", "createWsdl");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FastInfoset")) {
         getterName = "isFastInfoset";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFastInfoset";
         }

         currentResult = new PropertyDescriptor("FastInfoset", PortComponentBean.class, getterName, setterName);
         descriptors.put("FastInfoset", currentResult);
         currentResult.setValue("description", "Gets the \"fast-infoset\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpFlushResponse")) {
         getterName = "isHttpFlushResponse";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpFlushResponse";
         }

         currentResult = new PropertyDescriptor("HttpFlushResponse", PortComponentBean.class, getterName, setterName);
         descriptors.put("HttpFlushResponse", currentResult);
         currentResult.setValue("description", "Gets the \"http-flush-response\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidateRequest")) {
         getterName = "isValidateRequest";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidateRequest";
         }

         currentResult = new PropertyDescriptor("ValidateRequest", PortComponentBean.class, getterName, setterName);
         descriptors.put("ValidateRequest", currentResult);
         currentResult.setValue("description", "Gets the \"validate-request\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortComponentBean.class.getMethod("createServiceEndpointAddress");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceEndpointAddress");
      }

      mth = PortComponentBean.class.getMethod("destroyServiceEndpointAddress", WebserviceAddressBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServiceEndpointAddress");
      }

      mth = PortComponentBean.class.getMethod("createAuthConstraint");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthConstraint");
      }

      mth = PortComponentBean.class.getMethod("destroyAuthConstraint", AuthConstraintBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthConstraint");
      }

      mth = PortComponentBean.class.getMethod("createLoginConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LoginConfig");
      }

      mth = PortComponentBean.class.getMethod("destroyLoginConfig", LoginConfigBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LoginConfig");
      }

      mth = PortComponentBean.class.getMethod("createDeploymentListenerList");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeploymentListenerList");
      }

      mth = PortComponentBean.class.getMethod("destroyDeploymentListenerList", DeploymentListenerListBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeploymentListenerList");
      }

      mth = PortComponentBean.class.getMethod("createWsdl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Wsdl");
      }

      mth = PortComponentBean.class.getMethod("destroyWsdl", WsdlBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Wsdl");
      }

      mth = PortComponentBean.class.getMethod("createReliabilityConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton ReliabilityConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ReliabilityConfig");
      }

      mth = PortComponentBean.class.getMethod("destroyReliabilityConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton ReliabilityConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ReliabilityConfig");
      }

      mth = PortComponentBean.class.getMethod("createPersistenceConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton PersistenceConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceConfig");
      }

      mth = PortComponentBean.class.getMethod("destroyPersistenceConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton PersistenceConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceConfig");
      }

      mth = PortComponentBean.class.getMethod("createBufferingConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton BufferingConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BufferingConfig");
      }

      mth = PortComponentBean.class.getMethod("destroyBufferingConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton BufferingConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BufferingConfig");
      }

      mth = PortComponentBean.class.getMethod("createWSATConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton WSATConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSATConfig");
      }

      mth = PortComponentBean.class.getMethod("destroyWSATConfig");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton ConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSATConfig");
      }

      mth = PortComponentBean.class.getMethod("createOperation");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "add an Operation instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Operations");
      }

      mth = PortComponentBean.class.getMethod("destroyOperation", OperationComponentBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the Operation instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Operations");
      }

      mth = PortComponentBean.class.getMethod("createSoapjmsServiceEndpointAddress");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates the singleton SoapjmsConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SoapjmsServiceEndpointAddress");
      }

      mth = PortComponentBean.class.getMethod("destroySoapjmsServiceEndpointAddress");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the singleton ReliabilityConfigBean instance on this port. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SoapjmsServiceEndpointAddress");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PortComponentBean.class.getMethod("lookupOperation", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Operations");
      }

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
