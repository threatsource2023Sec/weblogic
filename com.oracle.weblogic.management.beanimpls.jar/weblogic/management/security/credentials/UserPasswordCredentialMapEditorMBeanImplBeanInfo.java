package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UserPasswordCredentialMapEditorMBeanImplBeanInfo extends UserPasswordCredentialMapReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserPasswordCredentialMapEditorMBean.class;

   public UserPasswordCredentialMapEditorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserPasswordCredentialMapEditorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for creating, editing, and removing a credential map that matches WebLogic users to remote usernames and their corresponding passwords. A Credential Mapping-provider MBean can optionally extend this MBean. The WebLogic Server Administration Console detects when a Credential Mapping provider extends this MBean and automatically provides a GUI for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.UserPasswordCredentialMapEditorMBean");
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
      Method mth = UserPasswordCredentialMapEditorMBean.class.getMethod("setUserPasswordCredential", String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential is created. "), createParameterDescriptor("remoteUserName", "- The username for the credential. "), createParameterDescriptor("remotePassword", "- The password for the credential. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Sets a remote user-password credential for the specified resource. If a new new username is specified, this method creates a new user-password credential. If you specify an existing username, this method replaces the user's password. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("wls:auditProtectedArgs", "3");
      }

      mth = UserPasswordCredentialMapEditorMBean.class.getMethod("setUserPasswordCredentialMapping", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential mapping is created. "), createParameterDescriptor("wlsUserName", "- The user name of the webLogic user. "), createParameterDescriptor("remoteUserName", "- The name of remote user the mapping is being created for. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a mapping from a webLogic username to a remote username-password credential for the specified resource. The credential for the remoteusername for the specified resource should be created before this mapping is created. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapEditorMBean.class.getMethod("removeUserPasswordCredential", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential is to be removed. "), createParameterDescriptor("remoteUserName", "- The name of remote user. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the credential that is created on the specified resource and remote username. If you created a credential map that specifies this username, the map becomes invalid. Users must remove any credential mappings created for the credential and the specified resource before removing the credential. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserPasswordCredentialMapEditorMBean.class.getMethod("removeUserPasswordCredentialMapping", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource for which the credential mapping is removed. "), createParameterDescriptor("wlsUserName", "- The user name of the webLogic user. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes the mapping from a webLogic username to a remote username-password credential for the specified resource. ");
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
