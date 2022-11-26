package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ForeignConnectionFactoryOverrideMBeanImplBeanInfo extends ForeignDestinationOverrideMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ForeignConnectionFactoryOverrideMBean.class;

   public ForeignConnectionFactoryOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ForeignConnectionFactoryOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ForeignConnectionFactoryOverrideMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("For each configuration entity that supports per-partition-override via a MBean, we have two MBeans, the original configure MBean and the corresponding override MBean, that eventually determine the effective settings of the configured resource.  We mark an attribute of an override MBean \"dynamic true\" even though the original configure MBean masks it \"dynamic false\". This is to make sure that the overriding settings take effect on partition restart. Otherwise a server restart is required.  <p>Defines partition-specific overrides for the most commonly overridden attributes in a ForeignConnectionFactory MBean. It overrides some of the settings in a same-named ForeignConnectionFactory MBean instance in the same multi-tenant scope to which this override MBean belongs. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ForeignConnectionFactoryOverrideMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", ForeignConnectionFactoryOverrideMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password used in conjunction with the user name specified in the <code>Username</code> parameter to access the remote connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "No-Override");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", ForeignConnectionFactoryOverrideMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value of the password.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Username")) {
         getterName = "getUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsername";
         }

         currentResult = new PropertyDescriptor("Username", ForeignConnectionFactoryOverrideMBean.class, getterName, setterName);
         descriptors.put("Username", currentResult);
         currentResult.setValue("description", "<p>The user name that is passed when opening a connection to the remote server (represented by this foreign connection factory).</p> ");
         setPropertyDescriptorDefault(currentResult, "No-Override");
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
