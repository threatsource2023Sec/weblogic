package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class BridgeDestinationMBeanImplBeanInfo extends BridgeDestinationCommonMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = BridgeDestinationMBean.class;

   public BridgeDestinationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BridgeDestinationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.BridgeDestinationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced with nothing (upgrade to JMSBridgeDestination if it maps to a JMS destination). ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents a bridge destination for non-JMS messaging products. Each messaging bridge instance consists of the following destination types:</p>  <ul> <li> <p>Source: The message producing destination. A bridge instance consumes messages from the source destination.</p> </li>  <li> <p>Target: The destination where a bridge instance forwards messages produced by the source destination.</p> </li> </ul>  <b>Note:</b> <p>Although WebLogic JMS includes a \"General Bridge Destination\" framework for accessing non-JMS messaging products, WebLogic Server does not provide supported adapters for such products. Therefore, you need to obtain a custom adapter from a third-party OEM vendor or contact Oracle.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.BridgeDestinationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("Properties")) {
         String getterName = "getProperties";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setProperties";
         }

         currentResult = new PropertyDescriptor("Properties", BridgeDestinationMBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p>Specifies all the properties of the bridge destination. The destination properties are string values that must be separated by a semicolon (;).</p>  <p>The following properties are required for all JMS implementations:</p>  <dl> <dt><tt>ConnectionURL=</tt></dt>  <dd> <p>The URL used to establish a connection to the destination.</p> </dd>  <dt><tt>ConnectionFactoryJNDIName=</tt></dt>  <dd> <p>The JNDI name of the JMS connection factory used to create a connection for the actual destination being mapped to the general bridge destination.</p> </dd>  <dt><tt>DestinationJNDIName=</tt></dt>  <dd> <p>The JNDI name of the actual destination being mapped to the general bridge destination.</p> </dd>  <dt><tt>DestinationType=</tt></dt>  <dd> <p>Specifies whether the destination type is either a Queue or Topic.</p> </dd>  <dt><tt>InitialContextFactory=</tt></dt>  <dd> <p>The factory used to get the JNDI context.</p> </dd> </dl> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
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
