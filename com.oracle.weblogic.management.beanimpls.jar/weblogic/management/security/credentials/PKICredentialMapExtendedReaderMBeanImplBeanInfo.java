package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class PKICredentialMapExtendedReaderMBeanImplBeanInfo extends PKICredentialMapReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PKICredentialMapExtendedReaderMBean.class;

   public PKICredentialMapExtendedReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PKICredentialMapExtendedReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.PKICredentialMapExtendedReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for reading a credential map that matches users and resources to aliases and their corresponding passwords that can then be used to retrieve key information or public certificate information from the configured keystores. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.PKICredentialMapExtendedReaderMBean");
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
      Method mth = PKICredentialMapExtendedReaderMBean.class.getMethod("getKeystoreAlias", String.class, String.class, String.class, Boolean.TYPE, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource id that is used to map user names to keystore alias and password. A resource object such as <code>weblogic.security.service.ResourceManager</code> assigns IDs to external sources. "), createParameterDescriptor("userName", "- The username that is mapped to the alias and password. "), createParameterDescriptor("identityDomain", "- The identity domain of the user. "), createParameterDescriptor("isInitiatorUserName", "- Set true if the initiator name passed in is the username. False otherwise. "), createParameterDescriptor("credAction", "- The credential action for which the mapping is created for. "), createParameterDescriptor("credType", "- The credential type. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException - This exception is thrown if                             the keystore alias is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the keystore alias that is mapped to a username for a particular resource and credential action. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapExtendedReaderMBean.class.getMethod("getCurrentInitiatorIdentityDomain", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method or the <code>listMappingsByPattern</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an identity domain associated with the user from a list that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern()</code> method. This method returns the identity domain associated with the username that corresponds to current location in the list. ");
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
