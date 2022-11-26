package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.WseePortRuntimeMBean;

public class WseePortRuntimeMBeanImplBeanInfo extends WseeBasePortRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseePortRuntimeMBean.class;

   public WseePortRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseePortRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseePortRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Describes the state of a particular Web Service port.  </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseePortRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AggregatedBaseOperations")) {
         getterName = "getAggregatedBaseOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("AggregatedBaseOperations", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AggregatedBaseOperations", currentResult);
         currentResult.setValue("description", "Return operation information aggregated across the base operations contained by this port. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("BaseOperations")) {
         getterName = "getBaseOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("BaseOperations", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BaseOperations", currentResult);
         currentResult.setValue("description", "Return the base operations contained by this port. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ClusterRouting")) {
         getterName = "getClusterRouting";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterRouting", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterRouting", currentResult);
         currentResult.setValue("description", "Get statistics related to routing of messages in a cluster. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Handlers")) {
         getterName = "getHandlers";
         setterName = null;
         currentResult = new PropertyDescriptor("Handlers", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Handlers", currentResult);
         currentResult.setValue("description", "<p>List of SOAP message handlers that are associated with this Web service.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Mc")) {
         getterName = "getMc";
         setterName = null;
         currentResult = new PropertyDescriptor("Mc", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Mc", currentResult);
         currentResult.setValue("description", "Get information related to MakeConnection anonymous ids. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Operations")) {
         getterName = "getOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("Operations", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Operations", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of public operations exposed by this Web Service.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicyAttachmentSupport")) {
         getterName = "getPolicyAttachmentSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyAttachmentSupport", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyAttachmentSupport", currentResult);
         currentResult.setValue("description", "Get attachment support for this port MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicyFaults")) {
         getterName = "getPolicyFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyFaults", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of policy faults.</p> ");
      }

      if (!descriptors.containsKey("PolicySubjectAbsolutePortableExpression")) {
         getterName = "getPolicySubjectAbsolutePortableExpression";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectAbsolutePortableExpression", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectAbsolutePortableExpression", currentResult);
         currentResult.setValue("description", "The policySubject AbsolutePortableExpression This is the WSM ResourcePattern AbsolutePortableExpression string ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectName")) {
         getterName = "getPolicySubjectName";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectName", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectName", currentResult);
         currentResult.setValue("description", "Get subject name for this port MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectResourcePattern")) {
         getterName = "getPolicySubjectResourcePattern";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectResourcePattern", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectResourcePattern", currentResult);
         currentResult.setValue("description", "The policySubject parameter must uniquely identify what application, module, service or reference-name, and port (port or operation for WLS Policy) is targeted. The syntax currently used by JRF (see [JRF ATTACHMENT POINT]) for J2EE Webservice Endpoints will be used: /{domain}/{instance}/{app}/WEBs|EJBs/{module}/WEBSERVICEs/{service}|{reference-name}/PORTs/{port} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicySubjectType")) {
         getterName = "getPolicySubjectType";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicySubjectType", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicySubjectType", currentResult);
         currentResult.setValue("description", "Get subject type for this port MBean. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortName")) {
         getterName = "getPortName";
         setterName = null;
         currentResult = new PropertyDescriptor("PortName", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PortName", currentResult);
         currentResult.setValue("description", "<p>Name of this port.</p>  <p>This attribute corresponds to the \"name\" attribute of the \"port\" element in the WSDL that describes the Web service.  Programmers specify the name of the port using the portName attribute of the &#64;WLXXXTransport annotation, where XXX refers to the type of transport (HTTP, HTTPS, or JMS).</p>  <p>Programmers can also use the WLXXXTransport child element of the jwsc Ant task to specify this attribute.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PortPolicy")) {
         getterName = "getPortPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("PortPolicy", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PortPolicy", currentResult);
         currentResult.setValue("description", "<p> Get statistics related to Web service security policy. </p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>Date and time that the Web service endpoint started.</p> ");
      }

      if (!descriptors.containsKey("TotalFaults")) {
         getterName = "getTotalFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFaults", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of security faults and violations.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
      }

      if (!descriptors.containsKey("TotalSecurityFaults")) {
         getterName = "getTotalSecurityFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSecurityFaults", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSecurityFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of security faults and violations.</p> ");
      }

      if (!descriptors.containsKey("TransportProtocolType")) {
         getterName = "getTransportProtocolType";
         setterName = null;
         currentResult = new PropertyDescriptor("TransportProtocolType", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransportProtocolType", currentResult);
         currentResult.setValue("description", "<p>Transport protocol used to invoke this Web service, such as HTTP, HTTPS, or JMS.</p>  <p>This attribute determines the transport that is published in the endpoint address section of the WSDL of the Web Service. Programmers specify the transport by the type of &#64;WLXXXTransport JWS annotation they specify, where XXX refers to the type of transport (HTTP, HTTPS, or JMS).</p>  <p>Programmers can also use the WLXXXTransport child element of the jwsc Ant task to specify this attribute.</p> ");
      }

      if (!descriptors.containsKey("Wsrm")) {
         getterName = "getWsrm";
         setterName = null;
         currentResult = new PropertyDescriptor("Wsrm", WseePortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Wsrm", currentResult);
         currentResult.setValue("description", "Get statistics related to reliable messaging. ");
         currentResult.setValue("relationship", "containment");
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
