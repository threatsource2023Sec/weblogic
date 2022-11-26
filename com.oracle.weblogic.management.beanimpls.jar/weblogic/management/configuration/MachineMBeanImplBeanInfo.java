package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MachineMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MachineMBean.class;

   public MachineMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MachineMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.MachineMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents a machine on which servers may be booted. A server is bound to a machine by calling to <code>ServerMBean.setMachine()</code>. Although it is typical that one <code>MachineMBean</code> refers to one physical machine and vice versa, it is possible to have a multihomed machine represented by multiple <code>MachineMBeans</code>. The only restriction is that each <code>MachineMBean</code> be configured with non-overlapping addresses. A configuration may contain one or more of <code>MachineMBeans</code> which may be looked up by their logical names.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.MachineMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Addresses")) {
         getterName = "getAddresses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAddresses";
         }

         currentResult = new PropertyDescriptor("Addresses", MachineMBean.class, getterName, setterName);
         descriptors.put("Addresses", currentResult);
         currentResult.setValue("description", "<p>The addresses by which this machine is known. May be either host names or literal IP addresses.</p> ");
         currentResult.setValue("deprecated", "8.1.0.0 Replaced by {@link ServerMBean#getListenAddress()} ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NodeManager")) {
         getterName = "getNodeManager";
         setterName = null;
         currentResult = new PropertyDescriptor("NodeManager", MachineMBean.class, getterName, setterName);
         descriptors.put("NodeManager", currentResult);
         currentResult.setValue("description", "<p>Returns the NodeManager Mbean that defines the configuration of the Node Manager instance that runs on the machine.</p> ");
         currentResult.setValue("relationship", "containment");
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
