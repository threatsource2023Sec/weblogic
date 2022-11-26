package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ForeignConnectionFactoryBeanImplBeanInfo extends ForeignJNDIObjectBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ForeignConnectionFactoryBean.class;

   public ForeignConnectionFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ForeignConnectionFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("This bean represents a connection factory from a remote JNDI context. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionHealthChecking")) {
         getterName = "getConnectionHealthChecking";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionHealthChecking";
         }

         currentResult = new PropertyDescriptor("ConnectionHealthChecking", ForeignConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("ConnectionHealthChecking", currentResult);
         currentResult.setValue("description", "<p>Controls connection health checking for JMS resource reference pools that access this JMS foreign connection factory mapping. The default value is <code>Enabled</code>.</p> <p>Oracle recommends selecting <code>Disabled</code> only when directed to do so by customer support.</p> ");
         setPropertyDescriptorDefault(currentResult, "enabled");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", ForeignConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password used in conjunction with the user name specified in the <code>Username</code> parameter to access the remote connection factory.</p> ");
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

         currentResult = new PropertyDescriptor("PasswordEncrypted", ForeignConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value of the password.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
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

         currentResult = new PropertyDescriptor("Username", ForeignConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Username", currentResult);
         currentResult.setValue("description", "<p>The user name that is passed when opening a connection to the remote server (represented by this foreign connection factory).</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
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
