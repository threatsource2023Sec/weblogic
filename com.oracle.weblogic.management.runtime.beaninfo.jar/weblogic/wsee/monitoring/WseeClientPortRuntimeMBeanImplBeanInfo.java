package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.WseeClientPortRuntimeMBean;

public class WseeClientPortRuntimeMBeanImplBeanInfo extends WseeBasePortRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeClientPortRuntimeMBean.class;

   public WseeClientPortRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeClientPortRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeClientPortRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Describes the state of a particular Web Service port.  </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeClientPortRuntimeMBean");
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
         currentResult = new PropertyDescriptor("AggregatedBaseOperations", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AggregatedBaseOperations", currentResult);
         currentResult.setValue("description", "Return operation information aggregated across the base operations contained by this port. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("BaseOperations")) {
         getterName = "getBaseOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("BaseOperations", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BaseOperations", currentResult);
         currentResult.setValue("description", "Return the base operations contained by this port. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ClusterRouting")) {
         getterName = "getClusterRouting";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterRouting", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterRouting", currentResult);
         currentResult.setValue("description", "Get statistics related to routing of messages in a cluster. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Handlers")) {
         getterName = "getHandlers";
         setterName = null;
         currentResult = new PropertyDescriptor("Handlers", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Handlers", currentResult);
         currentResult.setValue("description", "<p>List of SOAP message handlers that are associated with this Web service.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Mc")) {
         getterName = "getMc";
         setterName = null;
         currentResult = new PropertyDescriptor("Mc", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Mc", currentResult);
         currentResult.setValue("description", "Get information related to MakeConnection anonymous ids. ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Operations")) {
         getterName = "getOperations";
         setterName = null;
         currentResult = new PropertyDescriptor("Operations", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Operations", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of public operations exposed by this Web Service.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PolicyFaults")) {
         getterName = "getPolicyFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("PolicyFaults", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PolicyFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of policy faults.</p> ");
      }

      if (!descriptors.containsKey("PoolCapacity")) {
         getterName = "getPoolCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolCapacity", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolCapacity", currentResult);
         currentResult.setValue("description", "Capacity of the pool of client instances for this client port runtime, or 0 if no pool has been initialized for it. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolFreeCount")) {
         getterName = "getPoolFreeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolFreeCount", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolFreeCount", currentResult);
         currentResult.setValue("description", "Number of free client instances in the pool for this client port runtime, or 0 if no pool has been initialized for it. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolTakenCount")) {
         getterName = "getPoolTakenCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolTakenCount", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolTakenCount", currentResult);
         currentResult.setValue("description", "Number of taken client instances in the pool for this client port runtime, or 0 if no pool has been initialized for it. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolTotalConversationalClientTakeCount")) {
         getterName = "getPoolTotalConversationalClientTakeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolTotalConversationalClientTakeCount", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolTotalConversationalClientTakeCount", currentResult);
         currentResult.setValue("description", "Total number of conversational client instances that have been taken from the pool over the lifetime of the pool for this client port runtime, or 0 if no pool has been initialized for it. Note, conversational client instances are managed separately from simple pooled client instances, and are not subject to the capacity setting for this pool. Thus, using conversational client instances won't increase the values you see for getPoolFreeCount() or getPoolTakenCount(). ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolTotalPooledClientTakeCount")) {
         getterName = "getPoolTotalPooledClientTakeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolTotalPooledClientTakeCount", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolTotalPooledClientTakeCount", currentResult);
         currentResult.setValue("description", "Total number of client instances that have been taken from the pool over the lifetime of the pool for this client port runtime, or 0 if no pool has been initialized for it. Note, this value can exceed the capacity of the pool because client instances are released back into the pool to be taken again later. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolTotalSimpleClientCreateCount")) {
         getterName = "getPoolTotalSimpleClientCreateCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolTotalSimpleClientCreateCount", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolTotalSimpleClientCreateCount", currentResult);
         currentResult.setValue("description", "Total number of simple (non-pooled, non-conversational) client instances that have been created over the life of this client port runtime (i.e. using the same client identity and port name). Note, if a non-zero capacity is given for the pool associated with the client identity for this client port runtime, then all client instances retrieved for it will come from the pool, and the simple client create count will be 0. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortName")) {
         getterName = "getPortName";
         setterName = null;
         currentResult = new PropertyDescriptor("PortName", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PortName", currentResult);
         currentResult.setValue("description", "<p>Name of this port.</p>  <p>This attribute corresponds to the \"name\" attribute of the \"port\" element in the WSDL that describes the Web service.  Programmers specify the name of the port using the portName attribute of the &#64;WLXXXTransport annotation, where XXX refers to the type of transport (HTTP, HTTPS, or JMS).</p>  <p>Programmers can also use the WLXXXTransport child element of the jwsc Ant task to specify this attribute.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PortPolicy")) {
         getterName = "getPortPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("PortPolicy", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PortPolicy", currentResult);
         currentResult.setValue("description", "<p> Get statistics related to Web service security policy. </p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("StartTime")) {
         getterName = "getStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("StartTime", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartTime", currentResult);
         currentResult.setValue("description", "<p>Date and time that the Web service endpoint started.</p> ");
      }

      if (!descriptors.containsKey("TotalFaults")) {
         getterName = "getTotalFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalFaults", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of security faults and violations.</p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
      }

      if (!descriptors.containsKey("TotalSecurityFaults")) {
         getterName = "getTotalSecurityFaults";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSecurityFaults", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSecurityFaults", currentResult);
         currentResult.setValue("description", "<p>Total number of security faults and violations.</p> ");
      }

      if (!descriptors.containsKey("TransportProtocolType")) {
         getterName = "getTransportProtocolType";
         setterName = null;
         currentResult = new PropertyDescriptor("TransportProtocolType", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransportProtocolType", currentResult);
         currentResult.setValue("description", "<p>Transport protocol used to invoke this Web service, such as HTTP, HTTPS, or JMS.</p>  <p>This attribute determines the transport that is published in the endpoint address section of the WSDL of the Web Service. Programmers specify the transport by the type of &#64;WLXXXTransport JWS annotation they specify, where XXX refers to the type of transport (HTTP, HTTPS, or JMS).</p>  <p>Programmers can also use the WLXXXTransport child element of the jwsc Ant task to specify this attribute.</p> ");
      }

      if (!descriptors.containsKey("WseePortConfigurationRuntimeMBean")) {
         getterName = "getWseePortConfigurationRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("WseePortConfigurationRuntimeMBean", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WseePortConfigurationRuntimeMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Wsrm")) {
         getterName = "getWsrm";
         setterName = null;
         currentResult = new PropertyDescriptor("Wsrm", WseeClientPortRuntimeMBean.class, getterName, (String)setterName);
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
