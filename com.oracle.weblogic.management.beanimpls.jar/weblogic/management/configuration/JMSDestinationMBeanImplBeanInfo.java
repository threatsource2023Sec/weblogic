package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSDestinationMBeanImplBeanInfo extends JMSDestCommonMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSDestinationMBean.class;

   public JMSDestinationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSDestinationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSDestinationMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DestinationBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents a JMS destination, which identifies a queue (Point-To-Point) or a topic (Pub/Sub) for a JMS server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSDestinationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BytesPagingEnabled")) {
         getterName = "getBytesPagingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesPagingEnabled";
         }

         currentResult = new PropertyDescriptor("BytesPagingEnabled", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("BytesPagingEnabled", currentResult);
         currentResult.setValue("description", "<p>This parameter has been deprecated. Paging is always enabled. The \"MessageBufferSize\" parameter on JMSServerMBean controls how much memory is used before paging kicks in.</p> ");
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("legalValues", new Object[]{"default", "false", "true"});
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.configuration.JMSServerMBean#getMessageBufferSize() getMessageBufferSize} ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name used to look up the destination within the JNDI namespace. If not specified, the destination name is not advertised through the JNDI namespace and cannot be looked up and used.</p>  <p><i>Note:</i> This attribute is not dynamically configurable.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesPagingEnabled")) {
         getterName = "getMessagesPagingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesPagingEnabled";
         }

         currentResult = new PropertyDescriptor("MessagesPagingEnabled", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("MessagesPagingEnabled", currentResult);
         currentResult.setValue("description", "<p>This parameter has been deprecated. Paging is always enabled. The \"MessageBufferSize\" parameter on JMSServerMBean controls how much memory is used before paging kicks in.</p> ");
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("legalValues", new Object[]{"default", "false", "true"});
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.configuration.JMSServerMBean#getMessageBufferSize() getMessageBufferSize} ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StoreEnabled")) {
         getterName = "getStoreEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStoreEnabled";
         }

         currentResult = new PropertyDescriptor("StoreEnabled", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("StoreEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the destination supports persistent messaging by using the JMS store specified by the JMS server.</p>  <p>Supported values are:</p>  <ul> <li><b>default</b> <p>- The destination uses the JMS store defined for the JMS server, if one is defined, and supports persistent messaging. However, if a JMS store is not defined for the JMS server, then persistent messages are automatically downgraded to non-persistent.</p> </li>  <li><b>false</b> <p>- The destination does not support persistent messaging.</p> </li>  <li><b>true</b> <p>- The destination does support persistent messaging. However, if a JMS store is not defined for the JMS server, then the configuration will fail and the JMS server will not boot.</p> </li> </ul>  <p><i>Note:</i> This attribute is not dynamically configurable.</p> ");
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("legalValues", new Object[]{"default", "false", "true"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Template")) {
         getterName = "getTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemplate";
         }

         currentResult = new PropertyDescriptor("Template", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("Template", currentResult);
         currentResult.setValue("description", "<p>The JMS template from which the destination is derived. If a JMS template is specified, destination attributes that are set to their default values will inherit their values from the JMS template at run time. However, if this attribute is not defined, then the attributes for the destination must be specified as part of the destination.</p>  <p><i>Note:</i> The Template attribute setting per destination is static. The JMS template's attributes, however, can be modified dynamically.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDINameReplicated")) {
         getterName = "isJNDINameReplicated";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDINameReplicated";
         }

         currentResult = new PropertyDescriptor("JNDINameReplicated", JMSDestinationMBean.class, getterName, setterName);
         descriptors.put("JNDINameReplicated", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the JNDI name is replicated across the cluster. If JNDINameReplicated is set to true, then the JNDI name for the destination (if present) is replicated across the cluster. If JNDINameReplicated is set to false, then the JNDI name for the destination (if present) is only visible from the server of which this destination is a part.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
