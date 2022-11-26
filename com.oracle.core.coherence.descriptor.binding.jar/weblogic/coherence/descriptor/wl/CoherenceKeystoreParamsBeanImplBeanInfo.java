package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceKeystoreParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceKeystoreParamsBean.class;

   public CoherenceKeystoreParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceKeystoreParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("<p>Information needed to access key material for Coherence Identity from the keystore.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CoherenceIdentityAlias")) {
         getterName = "getCoherenceIdentityAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceIdentityAlias";
         }

         currentResult = new PropertyDescriptor("CoherenceIdentityAlias", CoherenceKeystoreParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceIdentityAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the Coherence Identity private key in the keystore. This private key is associated with the Coherence Identity digital certificate. If this alias is null, the Server SSL identity alias will be used.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherencePrivateKeyPassPhrase")) {
         getterName = "getCoherencePrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherencePrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("CoherencePrivateKeyPassPhrase", CoherenceKeystoreParamsBean.class, getterName, setterName);
         descriptors.put("CoherencePrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the private key for the Coherence Identity specified in the server configured keystore. This passphrase is assigned to the private key when the private key is generated. </p> Note that when you get the value of this attribute, WebLogic Server does the following: <ol> <li>Retrieves the value of the <code>CoherencePrivateKeyPassPhrase</code> attribute.</li> <li>Decrypts the value and returns the unencrypted passphrase.</li> </ol> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setCoherencePrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherencePrivateKeyPassPhraseEncrypted")) {
         getterName = "getCoherencePrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherencePrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("CoherencePrivateKeyPassPhraseEncrypted", CoherenceKeystoreParamsBean.class, getterName, setterName);
         descriptors.put("CoherencePrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value of the passphrase.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
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
