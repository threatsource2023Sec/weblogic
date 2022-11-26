package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPProxyMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPProxyMBean.class;

   public SNMPProxyMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPProxyMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPProxyMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean represents an SNMP agent that is proxied by a WebLogic Server SNMP agent. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPProxyMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Community")) {
         getterName = "getCommunity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCommunity";
         }

         currentResult = new PropertyDescriptor("Community", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("Community", currentResult);
         currentResult.setValue("description", "<p>The community name to be passed on for all SNMPv1 requests to this proxied SNMP agent.</p>  <p>If you specify a <i>security name</i> for this proxied agent, the WebLogic SNMP agent ignores this community name. Instead, the agent encodes the security name in an SNMPv3 request and forwards the SNMPv3 request to this proxied agent.</p> ");
         setPropertyDescriptorDefault(currentResult, "public");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The address on which the external SNMP agent for which the WebLogic SNMP Agent is acting as a proxy is listening for incoming requests from SNMP managers that use the UDP protocol.</p>  <p>If not specified the Server's ListenAddress is used as the ListenAddress of the external SNMPAgent.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("OidRoot")) {
         getterName = "getOidRoot";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOidRoot";
         }

         currentResult = new PropertyDescriptor("OidRoot", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("OidRoot", currentResult);
         currentResult.setValue("description", "<p>The root of the object identifier (OID) tree that this proxied SNMP agent controls.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "<p>The port number on which this proxied SNMP agent is listening.</p> ");
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityLevel")) {
         getterName = "getSecurityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityLevel";
         }

         currentResult = new PropertyDescriptor("SecurityLevel", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("SecurityLevel", currentResult);
         currentResult.setValue("description", "The security level that the proxied SNMP agent expects for the specified security name. ");
         currentResult.setValue("restProductionModeDefault", "authNoPriv");
         setPropertyDescriptorDefault(currentResult, "noAuthNoPriv");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"noAuthNoPriv", "authNoPriv", "authPriv"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityName")) {
         getterName = "getSecurityName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityName";
         }

         currentResult = new PropertyDescriptor("SecurityName", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("SecurityName", currentResult);
         currentResult.setValue("description", "<p>The user name on whose behalf the WebLogic SNMP agent forwards v3 requests.  If not specified, the request is forwarded as a v1 request.</p>  <p>If you specify a security name, you must also specify a security level that is equal to or lower than the security level that is configured for communication between the WebLogic SNMP agent and SNMP managers. For example, if the WebLogic SNMP agent requires incoming SNMPv3 requests to use the authentication protocol but no privacy protocol, the security level for this proxy must be either Authentication Only or None.  Note that if you want to use the authorization  or privacy protocols, you must configure credential mapping in the WebLogic Server security realm.</p>  <p>The WebLogic SNMP agent cannot forward or pass through the credentials that are contained in SNMPv3 requests from SNMP managers. Instead, the agent authenticates and performs other security operations on incoming requests, and then constructs a new request to forward to a proxied agent.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSecurityLevel()"), BeanInfoHelper.encodeEntities("#getCommunity()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timeout")) {
         getterName = "getTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeout";
         }

         currentResult = new PropertyDescriptor("Timeout", SNMPProxyMBean.class, getterName, setterName);
         descriptors.put("Timeout", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds that the WebLogic Server SNMP agent waits for a response to requests that it forwards to this proxy agent.</p>  <p>If the interval elapses without a response, the WebLogic SNMP agent sends an error to the requesting manager.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(5000L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
