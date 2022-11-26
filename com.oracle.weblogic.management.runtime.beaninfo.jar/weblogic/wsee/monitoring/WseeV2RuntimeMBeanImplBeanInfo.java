package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.WseeV2RuntimeMBean;

public class WseeV2RuntimeMBeanImplBeanInfo extends WseeBaseRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeV2RuntimeMBean.class;

   public WseeV2RuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeV2RuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeV2RuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Encapsulates runtime information about a particular Web Service. The name attribute of this MBean will be the value of the webservice-description-name element in webservices.xml</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeV2RuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ContextPath")) {
         getterName = "getContextPath";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextPath", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ContextPath", currentResult);
         currentResult.setValue("description", "<p>Specifies the context path of this web service.</p> <p>This attribute corresponds to the initial part of the endpoint address in the WSDL that describes the Web Service.  It is specified at development time in JAX-RPC services using the contextPath attribute of the &#64;WLXXXTransport JWS annotation, where XXX refers to the transport, such as HTTP, HTTPS, or JMS. For JAX-WS services the context path is set either during compilation of the service, or by the module (web or EJB) hosting the service.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConversationInstanceCount")) {
         getterName = "getConversationInstanceCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConversationInstanceCount", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConversationInstanceCount", currentResult);
         currentResult.setValue("description", "<p>Lists the instance count of conversations for this app + version</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ImplementationType")) {
         getterName = "getImplementationType";
         setterName = null;
         currentResult = new PropertyDescriptor("ImplementationType", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImplementationType", currentResult);
         currentResult.setValue("description", "<p>Specifies the implementation type of this service.</p>  <p>Valid values include: JAX-WS 2.0 JAX-RPC 1.1</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("OwsmSecurityPolicyRuntime")) {
         getterName = "getOwsmSecurityPolicyRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("OwsmSecurityPolicyRuntime", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OwsmSecurityPolicyRuntime", currentResult);
         currentResult.setValue("description", "<p>Lists the Oracle WSM security policy files that are attached to this Web service.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PolicyFaults")) {
         getterName = "getPolicyFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyFaults", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of faults generated by security policy handler.</p> ");
      }

      if (!descriptors.containsKey("PolicyRuntime")) {
         getterName = "getPolicyRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyRuntime", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyRuntime", currentResult);
         currentResult.setValue("description", "<p>Lists the WS-Policy files that are associated with this Web Service.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Ports")) {
         getterName = "getPorts";
         setterName = null;
         currentResult = new PropertyDescriptor("Ports", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Ports", currentResult);
         currentResult.setValue("description", "<p>Lists the ports that are associated with this Web service.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ServiceName")) {
         getterName = "getServiceName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceName", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServiceName", currentResult);
         currentResult.setValue("description", "<p>Specifies the qualified name of this Web service.</p>  <p>This attribute is calculated based on actual MBean type and of the form:</p>  <p><code>WseeRuntimeMBean</code>: &lt;app name&gt;#&lt;app version&gt;!&lt;service name&gt;.</p> <p><code>WseeV2RuntimeMBean</code>: &lt;app name&gt;#&lt;app version&gt;!&lt;web WAR name or EJB JAR name&gt;!&lt;service name&gt;.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>Date and time that the Web service starts.</p> ");
      }

      if (!descriptors.containsKey("TotalFaults")) {
         getterName = "getTotalFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFaults", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of faults generated by this endpoint.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
      }

      if (!descriptors.containsKey("TotalSecurityFaults")) {
         getterName = "getTotalSecurityFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSecurityFaults", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSecurityFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of faults generated by this endpoint.</p> ");
      }

      if (!descriptors.containsKey("URI")) {
         getterName = "getURI";
         setterName = null;
         currentResult = new PropertyDescriptor("URI", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("URI", currentResult);
         currentResult.setValue("description", "<p>Specifies the URI of this Web service.</p>  <p>This attribute corresponds to the final part of the endpoint address in the WSDL that describes the Web Services. It is specified at development time for JAX-RPC services using the serviceURI attribute of the &#64;WLXXXTransport JWS annotation, where XXX refers to the transport, such as HTTP, HTTPS, or JMS. For JAX-WS services, this can be set during compilation.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("WebserviceDescriptionName")) {
         getterName = "getWebserviceDescriptionName";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceDescriptionName", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WebserviceDescriptionName", currentResult);
         currentResult.setValue("description", "<p>webservice description name for this webservice</p> ");
      }

      if (!descriptors.containsKey("WsType")) {
         getterName = "getWsType";
         setterName = null;
         currentResult = new PropertyDescriptor("WsType", WseeV2RuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WsType", currentResult);
         currentResult.setValue("description", "<p>Returns the type of this web service, either JAX-WS or JAX-RPC.</p> ");
         currentResult.setValue("excludeFromRest", "No default REST mapping for WseeBaseRuntimeMBean.Type");
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
