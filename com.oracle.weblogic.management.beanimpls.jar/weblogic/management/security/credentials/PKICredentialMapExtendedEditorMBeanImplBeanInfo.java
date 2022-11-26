package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class PKICredentialMapExtendedEditorMBeanImplBeanInfo extends PKICredentialMapExtendedReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PKICredentialMapExtendedEditorMBean.class;

   public PKICredentialMapExtendedEditorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PKICredentialMapExtendedEditorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for creating, editing, and removing a credential map that matches users, resources and credential action to keystore aliases and the corresponding passwords. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.PKICredentialMapExtendedEditorMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PKICredentialMapExtendedEditorMBean.class.getMethod("setKeypairCredential", String.class, String.class, String.class, Boolean.TYPE, String.class, String.class, char[].class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the user name is mapped to the keystore alias and password. "), createParameterDescriptor("principalName", "-  The principalName used in the credential mapping. "), createParameterDescriptor("identityDomain", "- The identity domain associated with the principal name. "), createParameterDescriptor("isInitiatorUserName", "- True if the initiator name is a user name. False if it is a  group. "), createParameterDescriptor("credAction", "- The credential action. "), createParameterDescriptor("keystoreAlias", "- The keystore alias. "), createParameterDescriptor("password", "- The password for the keystore entry. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for char[]");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException - Exception thrown if the keystore alias                               does not point to a keypair entry or if the                               password supplied here is not correct.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a keypair mapping between the principalName, resourceid and credential action to the keystore alias and the corresponding password. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for char[]");
         currentResult.setValue("wls:auditProtectedArgs", "6");
      }

      mth = PKICredentialMapExtendedEditorMBean.class.getMethod("setCertificateCredential", String.class, String.class, String.class, Boolean.TYPE, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the user name is mapped to the keystore alias. "), createParameterDescriptor("principalName", "-  The principalName used in the credential mapping. "), createParameterDescriptor("identityDomain", "- The identity domain associated with the principal name. "), createParameterDescriptor("isInitiatorUserName", "- True if the initiator name is a user name. False if it is a  group. "), createParameterDescriptor("credAction", "- The credential action. "), createParameterDescriptor("keystoreAlias", "- The keystore alias. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException -    Exception thrown if the keystore alias                               does not point to a certificate entry.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a public certificate mapping between the principalName, resourceid and credential action to the keystore alias. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapExtendedEditorMBean.class.getMethod("removePKICredentialMapping", String.class, String.class, String.class, Boolean.TYPE, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the user name is mapped to the keystore alias and password. "), createParameterDescriptor("principalName", "- The principalName used in the credential mapping. "), createParameterDescriptor("identityDomain", "- The identity domain associated with the principal name. "), createParameterDescriptor("isInitiatorUserName", "- True if the initiator name is a user name. False if it is a  group. "), createParameterDescriptor("credAction", "- The credential action. "), createParameterDescriptor("credType", "- The credential type. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the mapping between the principalName, resourceid and credential action to the keystore alias. ");
         currentResult.setValue("role", "operation");
      }

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
