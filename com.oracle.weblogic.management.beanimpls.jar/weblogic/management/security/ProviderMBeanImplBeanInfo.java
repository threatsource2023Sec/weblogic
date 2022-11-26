package weblogic.management.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;

public class ProviderMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ProviderMBean.class;

   public ProviderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ProviderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.ProviderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security");
      String description = (new String("The base MBean for all security providers. <p> It includes attributes common to all security providers. Every security provider must implement an MBean that extends this MBean.</p> <p> If the security provider supports management methods, the management methods cannot be called until the validate method of realm in which the security provider is configured successfully returns. That is, the administrator must completely configure the realm before using the management methods (for example, adding a user). </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.ProviderMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", ProviderMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Returns a description of this security provider. <p> Each security provider's MBean should set the default value of this read-only attribute to a string that describes the provider.  In other words, each security provider's MBean hard-wires its description.  There are no conventions governing the contents of the description.  It should be a human readable string that gives a brief description of the security provider. </p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ProviderMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration. ");
         setPropertyDescriptorDefault(currentResult, "Provider");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("legal", "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Realm")) {
         getterName = "getRealm";
         setterName = null;
         currentResult = new PropertyDescriptor("Realm", ProviderMBean.class, getterName, (String)setterName);
         descriptors.put("Realm", currentResult);
         currentResult.setValue("description", "Returns the realm that contains this security provider. Returns null if this security provider is not contained by a realm. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("Version", ProviderMBean.class, getterName, (String)setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Returns this security provider's version. <p> Each security provider's MBean should set the default value of this read-only attribute to a string that specifies the version of the provider (e.g. 7.3.04).  In other words, each security provider's MBean hard-wires its version.  There are no conventions governing the contents of the version string. </p> ");
         currentResult.setValue("transient", Boolean.TRUE);
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
