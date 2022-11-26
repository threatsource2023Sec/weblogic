package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SNMPJMXMonitorMBeanImplBeanInfo extends SNMPTrapSourceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPJMXMonitorMBean.class;

   public SNMPJMXMonitorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPJMXMonitorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPJMXMonitorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This is a base class for Monitor based trap configuration MBeans : SNMPCounterMonitorMBean, SNMPStringMonitorMBean and SNMPGaugeMonitorMBean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPJMXMonitorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MonitoredAttributeName")) {
         getterName = "getMonitoredAttributeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonitoredAttributeName";
         }

         currentResult = new PropertyDescriptor("MonitoredAttributeName", SNMPJMXMonitorMBean.class, getterName, setterName);
         descriptors.put("MonitoredAttributeName", currentResult);
         currentResult.setValue("description", "<p>The name of an MBean attribute to monitor. This attribute must be in the WebLogic Server MIB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MonitoredMBeanName")) {
         getterName = "getMonitoredMBeanName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonitoredMBeanName";
         }

         currentResult = new PropertyDescriptor("MonitoredMBeanName", SNMPJMXMonitorMBean.class, getterName, setterName);
         descriptors.put("MonitoredMBeanName", currentResult);
         currentResult.setValue("description", "<p>The name of the MBean instance that you want to monitor. If you leave the name undefined, WebLogic Server monitors all instances of the MBean type that you specify in Monitored MBean Type.</p>  <p>If you target SNMP agents to individual Managed Servers, make sure that the MBean instance you specify is active on the Managed Servers you have targeted. For example, if you specify <code>MServer1</code> as the name of a ServerRuntimeMBean instance, this monitor will only function if you target an SNMP agent either to the Administration Server or to a Managed Server named <code>MServer1</code>.</p>  <p>Do not enter the full JMX object name of the MBean instance. Instead, enter only the value of the object name's <code>Name=<i>name</i></code> name-value pair. To create unique MBean object names, WebLogic Server encodes several name-value pairs into each object name. One of these pairs is <code>Name=<i>name</i></code>. For example:</p>  <p><code>\"MedRec:<b>Name=MedRecServer</b>,</code><br> <code>Type=ServerRuntime\"</code></p>  <p>In the previous example, specify <code>MedRecServer</code> as the name of the MBean instance.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MonitoredMBeanType")) {
         getterName = "getMonitoredMBeanType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonitoredMBeanType";
         }

         currentResult = new PropertyDescriptor("MonitoredMBeanType", SNMPJMXMonitorMBean.class, getterName, setterName);
         descriptors.put("MonitoredMBeanType", currentResult);
         currentResult.setValue("description", "<p>The MBean type that defines the attribute you want to monitor. Do not include the <code>MBean</code> suffix. For example, <code>ServerRuntime</code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PollingInterval")) {
         getterName = "getPollingInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPollingInterval";
         }

         currentResult = new PropertyDescriptor("PollingInterval", SNMPJMXMonitorMBean.class, getterName, setterName);
         descriptors.put("PollingInterval", currentResult);
         currentResult.setValue("description", "<p>The frequency (in seconds) that WebLogic Server checks the attribute value.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
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
