package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SNMPAttributeChangeMBeanImplBeanInfo extends SNMPTrapSourceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SNMPAttributeChangeMBean.class;

   public SNMPAttributeChangeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SNMPAttributeChangeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SNMPAttributeChangeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class describes the settings to receive MBean-attribute change notification.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SNMPAttributeChangeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AttributeMBeanName")) {
         getterName = "getAttributeMBeanName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttributeMBeanName";
         }

         currentResult = new PropertyDescriptor("AttributeMBeanName", SNMPAttributeChangeMBean.class, getterName, setterName);
         descriptors.put("AttributeMBeanName", currentResult);
         currentResult.setValue("description", "<p>The name of the MBean instance that you want to monitor. If you leave the name undefined, WebLogic Server monitors all instances of the MBean type that you specify in Monitored MBean Type.</p>  <p>Do not enter the full JMX object name of the MBean instance. Instead, enter only the value that you provided when you created the instance. To create unique MBean object names, WebLogic Server encodes several name-value pairs into each object name. One of these pairs is <code>Name=<i>name</i></code>, and this is the value that you enter for MBean Name. For example:</p>  <p><code>\"MedRec:<b>Name=MedRecServer</b>,</code><br> <code>Type=ServerRuntime\"</code></p>  <p>In the previous example, specify <code>MedRecServer</code> as the name of the MBean instance.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AttributeMBeanType")) {
         getterName = "getAttributeMBeanType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttributeMBeanType";
         }

         currentResult = new PropertyDescriptor("AttributeMBeanType", SNMPAttributeChangeMBean.class, getterName, setterName);
         descriptors.put("AttributeMBeanType", currentResult);
         currentResult.setValue("description", "<p>The MBean type that defines the attribute you want to monitor. Do not include the <code>MBean</code> suffix. For example, specify <code>Server</code> to monitor a ServerMBean.</p>  <p>WebLogic Server does not support using Attribute Change notifications to monitor run-time attributes. Runtime MBeans always include the word <code>Runtime</code> in their names. For example, the <code>ServerRuntime</code> MBean provides access to runtime attributes while the <code>Server</code> MBean provides access to configuration attributes. To monitor changes in an MBean that includes <code>Runtime</code> in its name, use a String Monitor, Gauge Monitor, or Counter Monitor.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AttributeName")) {
         getterName = "getAttributeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttributeName";
         }

         currentResult = new PropertyDescriptor("AttributeName", SNMPAttributeChangeMBean.class, getterName, setterName);
         descriptors.put("AttributeName", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute that you want to monitor. This attribute must be in the WebLogic Server MIB.</p> ");
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
