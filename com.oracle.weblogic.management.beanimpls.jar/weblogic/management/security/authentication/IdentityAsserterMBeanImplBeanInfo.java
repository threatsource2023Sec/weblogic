package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class IdentityAsserterMBeanImplBeanInfo extends AuthenticationProviderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = IdentityAsserterMBean.class;

   public IdentityAsserterMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public IdentityAsserterMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.IdentityAsserterMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("The SSPI MBean that all Identity Assertion providers must extend. This MBean enables an Identity Assertion provider to specify the token types for which it is capable of asserting identity. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.IdentityAsserterMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActiveTypes")) {
         getterName = "getActiveTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActiveTypes";
         }

         currentResult = new PropertyDescriptor("ActiveTypes", IdentityAsserterMBean.class, getterName, setterName);
         descriptors.put("ActiveTypes", currentResult);
         currentResult.setValue("description", "Returns the token types that the Identity Assertion provider is currently configured to process. ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Base64DecodingRequired")) {
         getterName = "getBase64DecodingRequired";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBase64DecodingRequired";
         }

         currentResult = new PropertyDescriptor("Base64DecodingRequired", IdentityAsserterMBean.class, getterName, setterName);
         descriptors.put("Base64DecodingRequired", currentResult);
         currentResult.setValue("description", "Returns whether the tokens that are passed to the Identity Assertion provider will be base64 decoded first.  If <code>false</code> then the server will not base64 decode the token before passing it to the identity asserter. This defaults to <code>true</code> for backwards compatibility but most providers will probably want to set this to <code>false</code>. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Realm")) {
         getterName = "getRealm";
         setterName = null;
         currentResult = new PropertyDescriptor("Realm", IdentityAsserterMBean.class, getterName, setterName);
         descriptors.put("Realm", currentResult);
         currentResult.setValue("description", "Returns the realm that contains this security provider. Returns null if this security provider is not contained by a realm. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("SupportedTypes")) {
         getterName = "getSupportedTypes";
         setterName = null;
         currentResult = new PropertyDescriptor("SupportedTypes", IdentityAsserterMBean.class, getterName, setterName);
         descriptors.put("SupportedTypes", currentResult);
         currentResult.setValue("description", "<p>Returns the list of token types supported by the Identity Assertion provider.</p>  <p>To see a list of default token types, refer the Javadoc for <code>weblogic.security.spi.IdentityAsserter</code></p> ");
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
