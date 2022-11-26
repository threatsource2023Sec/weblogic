package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class SoapjmsServiceEndpointAddressBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = SoapjmsServiceEndpointAddressBean.class;

   public SoapjmsServiceEndpointAddressBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SoapjmsServiceEndpointAddressBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML soapjmsConfigType. This is configuration used by SOAP JMS transport on the server-side. (@http://www.bea.com/ns/weblogic/weblogic-webservices). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SoapjmsServiceEndpointAddressBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActivationConfig")) {
         getterName = "getActivationConfig";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActivationConfig";
         }

         currentResult = new PropertyDescriptor("ActivationConfig", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("ActivationConfig", currentResult);
         currentResult.setValue("description", "String value. Specify activation configuration properties passed to the JMS provider. Each property is specified using name-value pairs, separated by semicolons (;).  For example: name1=value1;...;nameN=valueN ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BindingVersion")) {
         getterName = "getBindingVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBindingVersion";
         }

         currentResult = new PropertyDescriptor("BindingVersion", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("BindingVersion", currentResult);
         currentResult.setValue("description", "String value. SOAP/JMS Binding Version. ");
         setPropertyDescriptorDefault(currentResult, "1.0");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeliveryMode")) {
         getterName = "getDeliveryMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeliveryMode";
         }

         currentResult = new PropertyDescriptor("DeliveryMode", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("DeliveryMode", currentResult);
         currentResult.setValue("description", "String value. The delivery mode indicates whether the request message is persistent. Valid values are PERSISTENT or NON_PERSISTENT. ");
         setPropertyDescriptorDefault(currentResult, "PERSISTENT");
         currentResult.setValue("legalValues", new Object[]{"PERSISTENT", "NON_PERSISTENT"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationName")) {
         getterName = "getDestinationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationName";
         }

         currentResult = new PropertyDescriptor("DestinationName", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("DestinationName", currentResult);
         currentResult.setValue("description", "String value. destinationName, specifying the jndi name of the destination queue or topic. ");
         setPropertyDescriptorDefault(currentResult, "com.oracle.webservices.jms.RequestQueue");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationType")) {
         getterName = "getDestinationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationType";
         }

         currentResult = new PropertyDescriptor("DestinationType", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("DestinationType", currentResult);
         currentResult.setValue("description", "String value. Destination Type, specifying the destination type. Valid values include QUEUE(default) and TOPIC. ");
         setPropertyDescriptorDefault(currentResult, "QUEUE");
         currentResult.setValue("legalValues", new Object[]{"QUEUE", "TOPIC"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsMessageHeader")) {
         getterName = "getJmsMessageHeader";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsMessageHeader";
         }

         currentResult = new PropertyDescriptor("JmsMessageHeader", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JmsMessageHeader", currentResult);
         currentResult.setValue("description", "String value. Specify JMS message headers ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsMessageProperty")) {
         getterName = "getJmsMessageProperty";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsMessageProperty";
         }

         currentResult = new PropertyDescriptor("JmsMessageProperty", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JmsMessageProperty", currentResult);
         currentResult.setValue("description", "String value. Specify JMS message properties ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiConnectionFactoryName")) {
         getterName = "getJndiConnectionFactoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJndiConnectionFactoryName";
         }

         currentResult = new PropertyDescriptor("JndiConnectionFactoryName", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JndiConnectionFactoryName", currentResult);
         currentResult.setValue("description", "String value that specifies the JNDI name of the connection factory that is used for establishing a JMS connection. ");
         setPropertyDescriptorDefault(currentResult, "com.oracle.webservices.jms.ConnectionFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiContextParameter")) {
         getterName = "getJndiContextParameter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJndiContextParameter";
         }

         currentResult = new PropertyDescriptor("JndiContextParameter", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JndiContextParameter", currentResult);
         currentResult.setValue("description", "String value. jndiContextParameter, specifying additional JNDI properties. Each property is specified using name-value pairs, separated by semicolons (;). For example: name1=value1;...;nameN=valueN. The properties are added to the java.util.Hashtable sent to the InitialContext constructor for the JNDI provider. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiInitialContextFactory")) {
         getterName = "getJndiInitialContextFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJndiInitialContextFactory";
         }

         currentResult = new PropertyDescriptor("JndiInitialContextFactory", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JndiInitialContextFactory", currentResult);
         currentResult.setValue("description", "String value. JNDI InitialContextFactory, specifying the name of the initial context factory class used for JNDI lookup. This value maps to the java.naming.factory.initial property. ");
         setPropertyDescriptorDefault(currentResult, "weblogic.jndi.WLInitialContextFactory");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiUrl")) {
         getterName = "getJndiUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJndiUrl";
         }

         currentResult = new PropertyDescriptor("JndiUrl", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("JndiUrl", currentResult);
         currentResult.setValue("description", "String value. The JNDI provider URL. ");
         setPropertyDescriptorDefault(currentResult, "t3://localhost:7001");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LookupVariant")) {
         getterName = "getLookupVariant";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLookupVariant";
         }

         currentResult = new PropertyDescriptor("LookupVariant", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("LookupVariant", currentResult);
         currentResult.setValue("description", "String value that specifies the method used for looking up the specified destination name. This value must be set to jndi to support JMS transport in this implementation. ");
         setPropertyDescriptorDefault(currentResult, "jndi");
         currentResult.setValue("legalValues", new Object[]{"jndi"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageType")) {
         getterName = "getMessageType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageType";
         }

         currentResult = new PropertyDescriptor("MessageType", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("MessageType", currentResult);
         currentResult.setValue("description", "String value. Specify the message type to use with the request message. Valid values are BYTES and TEXT. ");
         setPropertyDescriptorDefault(currentResult, "BYTES");
         currentResult.setValue("legalValues", new Object[]{"BYTES", "TEXT"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Priority")) {
         getterName = "getPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPriority";
         }

         currentResult = new PropertyDescriptor("Priority", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("Priority", currentResult);
         currentResult.setValue("description", "Integer value. JMS priority associated with the request and response message. Specify this value as a positive Integer from 0, the lowest priority, to 9, the highest priority. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(9));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplyToName")) {
         getterName = "getReplyToName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplyToName";
         }

         currentResult = new PropertyDescriptor("ReplyToName", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("ReplyToName", currentResult);
         currentResult.setValue("description", "String value. Specify the JNDI name of the JMS destination to which the response message is sent.  For a two-way operation, a temporary response queue is generated by default. Using the default temporary response queue minimizes the configuration that is required. However, in the event of a server failure, the response message may be lost. This preoperty enables the client to use a previously defined, \"permanent\" queue or topic rather than use the default temporary queue or topic, for receiving replies. The value maps to the JMSReplyTo JMS header in the request message. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunAsPrincipal")) {
         getterName = "getRunAsPrincipal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRunAsPrincipal";
         }

         currentResult = new PropertyDescriptor("RunAsPrincipal", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("RunAsPrincipal", currentResult);
         currentResult.setValue("description", "String value. Specify the principal to run the listening MDB. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunAsRole")) {
         getterName = "getRunAsRole";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRunAsRole";
         }

         currentResult = new PropertyDescriptor("RunAsRole", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("RunAsRole", currentResult);
         currentResult.setValue("description", "String value. Specify the role to run the listening MDB. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetService")) {
         getterName = "getTargetService";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetService";
         }

         currentResult = new PropertyDescriptor("TargetService", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("TargetService", currentResult);
         currentResult.setValue("description", "String value. Specify the port component name of the Web service. This value is used by the service implementation to dispatch the service request. If not specified, the service name from the WSDL or @javax.jms.WebService annotation is used. This value maps to the SOAPJMS_targetService JMS message property. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLive")) {
         getterName = "getTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLive";
         }

         currentResult = new PropertyDescriptor("TimeToLive", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("TimeToLive", currentResult);
         currentResult.setValue("description", "String value. Specify the lifetime, in milliseconds, of the request message. A value of 0 indicates an infinite lifetime. On the service side, timeToLive also specifies the expiration time for each MDB transaction. ");
         setPropertyDescriptorDefault(currentResult, new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableHttpWsdlAccess")) {
         getterName = "isEnableHttpWsdlAccess";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableHttpWsdlAccess";
         }

         currentResult = new PropertyDescriptor("EnableHttpWsdlAccess", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("EnableHttpWsdlAccess", currentResult);
         currentResult.setValue("description", "Boolean value. enable the HTTP WSDL accessibility. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MdbPerDestination")) {
         getterName = "isMdbPerDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMdbPerDestination";
         }

         currentResult = new PropertyDescriptor("MdbPerDestination", SoapjmsServiceEndpointAddressBean.class, getterName, setterName);
         descriptors.put("MdbPerDestination", currentResult);
         currentResult.setValue("description", "Boolean value. One MDB instance for each destination - true One MDB instance for each destination - false One MDB instance for each service. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
