package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.utils.ListerMBeanImplBeanInfo;

public class UserPasswordCredentialMapReaderMBeanImplBeanInfo extends ListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserPasswordCredentialMapReaderMBean.class;

   public UserPasswordCredentialMapReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserPasswordCredentialMapReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for reading credentials and credential mappings. Credential mappings match WebLogic users to remote usernames and passwords.   A Credential Mapping-provider MBean can optionally extend this MBean. The WebLogic Server Administration Console detects when a Credential Mapping provider extends this MBean and automatically provides a GUI for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBean");
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
      Method mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getRemoteUserName", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource on which the mapping between the local weblogic user and the remote user was created. "), createParameterDescriptor("wlsUserName", "- The local weblogic username. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the external username that is mapped to a local webLogic username for the specified resource. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getRemotePassword", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential was created. "), createParameterDescriptor("remoteUserName", "- The external username. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the remote password corresponding to the remote username in the credential created for the specified resource. Deprecated in WLS 9.0 ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("listCredentials", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credentials are to be returned. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a list of credentials mapped to the resource. Returns a cursor as a string. Use the <code>getCurrentCredentialRemoteUserName</code> and <code>getCurrentCredentialRemotePassword</code> methods to get the username and password for the current item in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getCurrentCredentialRemoteUserName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listCredentials</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a remote username from a list that has been returned from the <code>listCredentials</code> method. This method returns the remote username that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getCurrentCredentialRemotePassword", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listCredentials</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a password from a list that has been returned from the <code>listCredentials</code> method. This method returns the password that corresponds to current location in the list. Deprecated in WLS 9.0 ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("listMappings", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential mappings are to be returned. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a list of credential mappings created for the given resource id. Returns a cursor as a string. Use the <code>getCurrentMappingWLSUserName</code> and <code>getCurrentMappingRemoteUserName</code> methods to return the webLogic username and remote user name for the current item in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getCurrentMappingWLSUserName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a webLogic username from a credentials mapping that has been returned from the <code>listMappings</code> method. This method returns the local webLogic username that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapReaderMBean.class.getMethod("getCurrentMappingRemoteUserName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an remote username from a credentials mapping that has been returned from the <code>listMappings</code> method. This method returns the remote username that corresponds to current location in the list. ");
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
