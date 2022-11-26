package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UserPasswordCredentialMapExtendedV2ReaderMBeanImplBeanInfo extends UserPasswordCredentialMapExtendedReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserPasswordCredentialMapExtendedV2ReaderMBean.class;

   public UserPasswordCredentialMapExtendedV2ReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserPasswordCredentialMapExtendedV2ReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedV2ReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for reading the credential mappings. Credential mappings match WebLogic users to remote usernames and passwords. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.UserPasswordCredentialMapExtendedV2ReaderMBean");
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
      Method mth = UserPasswordCredentialMapExtendedV2ReaderMBean.class.getMethod("getRemoteUserName", String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource on which the mapping between the local WebLogic user and the remote user was created. "), createParameterDescriptor("wlsUserName", "- The local WebLogic username. "), createParameterDescriptor("identityDomain", "- The identity domain of the WebLogic user. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the external username that is mapped to a local WebLogic username for the specified resource. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapExtendedV2ReaderMBean.class.getMethod("getCurrentMappingWLSUserIdentityDomain", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an identity domain associated with the WLS user from a credentials mapping that has been returned from the <code>listMappings</code> method. This method returns the identity domain associated with a username that corresponds to current location in the list. ");
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
