package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SNMPTrapDestinationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPTrapDestinationMBean.class;

   public SNMPTrapDestinationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPTrapDestinationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPTrapDestinationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean describes a destination to which an SNMP agent sends SNMP TRAP and INFORM notifications. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPTrapDestinationMBean");
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

         currentResult = new PropertyDescriptor("Community", SNMPTrapDestinationMBean.class, getterName, setterName);
         descriptors.put("Community", currentResult);
         currentResult.setValue("description", "<p>The password (community name) that a WebLogic Server SNMP agent sends to the SNMP manager when the agent generates SNMPv1 or SNMPv2 notifications.</p> <p>The community name that you enter in this trap destination must match the name that the SNMP manager defines.</p> ");
         setPropertyDescriptorDefault(currentResult, "public");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "12.2.1.4 There is no replacement for this attribute as SNMPv1 and v2 support will be removed. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Host")) {
         getterName = "getHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHost";
         }

         currentResult = new PropertyDescriptor("Host", SNMPTrapDestinationMBean.class, getterName, setterName);
         descriptors.put("Host", currentResult);
         currentResult.setValue("description", "<p>The DNS name or IP address of the computer on which the SNMP manager is running.</p>  <p>The WebLogic SNMP agent sends trap notifications to the host and port that you specify.</p> ");
         setPropertyDescriptorDefault(currentResult, "localhost");
         currentResult.setValue("secureValue", "127.0.0.1 or ::1");
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Port")) {
         getterName = "getPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPort";
         }

         currentResult = new PropertyDescriptor("Port", SNMPTrapDestinationMBean.class, getterName, setterName);
         descriptors.put("Port", currentResult);
         currentResult.setValue("description", "<p>The UDP port on which the SNMP manager is listening.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(162));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SecurityLevel")) {
         getterName = "getSecurityLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityLevel";
         }

         currentResult = new PropertyDescriptor("SecurityLevel", SNMPTrapDestinationMBean.class, getterName, setterName);
         descriptors.put("SecurityLevel", currentResult);
         currentResult.setValue("description", "<p>Specifies the security protocols that the SNMP agent uses when sending SNMPv3 responses or notifications to the SNMP manager that this trap destination represents. Requires you to specify a security name for this trap destination.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSecurityName()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "authNoPriv");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"noAuthNoPriv", "authNoPriv", "authPriv"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SecurityName")) {
         getterName = "getSecurityName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityName";
         }

         currentResult = new PropertyDescriptor("SecurityName", SNMPTrapDestinationMBean.class, getterName, setterName);
         descriptors.put("SecurityName", currentResult);
         currentResult.setValue("description", "<p>Specifies the user name that the WebLogic Server SNMP agent encodes into SNMPv3 responses or notifications. Requires you to create a credential map for this user name in the WebLogic Server security realm.</p> <p>The credential map contains an authentication password and an optional privacy password for this user.</p> <p>The user name and passwords must match the credentials required by the SNMP manager that this trap destination represents.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
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
