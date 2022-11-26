package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UserPasswordCredentialMapExtendedReaderMBeanImplBeanInfo extends UserPasswordCredentialMapReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserPasswordCredentialMapExtendedReaderMBean.class;

   public UserPasswordCredentialMapExtendedReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserPasswordCredentialMapExtendedReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.UserPasswordCredentialMapExtendedReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for reading the credential mappings. Credential mappings match WebLogic users to remote usernames and passwords. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.UserPasswordCredentialMapExtendedReaderMBean");
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
      Method mth = UserPasswordCredentialMapExtendedReaderMBean.class.getMethod("listMappingsByPattern", String.class, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceIdPattern", "- Resource Id string pattern, that can contain '*' - wild card, and '' - escape character. "), createParameterDescriptor("maxToReturn", "- Maximum number of returned mappings ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a list of all credential mappings with the resource id matching the specified resource id pattern. Returns a cursor as a string. Use the <code>getCurrentMappingWLSUserName</code> and <code>getCurrentMappingRemoteUserName()</code> and <code>getCurrentMappingResourceID()</code> methods to return the WebLogic username and external user name and the current resource ID for the current item in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapExtendedReaderMBean.class.getMethod("getCurrentMappingResourceID", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method and the <code>listMappingsByPattern</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an resource id from a credentials map that has been returned from the <code>listMappingsByPattern</code> method. This method returns the resource id that corresponds to current location in the list. ");
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
