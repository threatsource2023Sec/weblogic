package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UserPasswordEditorMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserPasswordEditorMBean.class;

   public UserPasswordEditorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserPasswordEditorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.UserPasswordEditorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("<p>Provides two methods for changing a user's password. An Authentication provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Authentication provider implements this MBean and automatically provides a tab for using these methods.</p>  <p>CSS v4 introduced a new Password Validaton Service to check password against a set of rules when doing changing password operations with authentication provider MBeans such as <code>createUser</code>, <code>changeUserPassword</code> and <code>resetUserPassword</code>. The rules can be specified through configuring Password Validation Provider into the security realm, for further information, see <code>weblogic.management.security.RealmMBean</code>.</p>  <p>All OOTB authentication providers in CSS will automatically call the Password Validation Service if their MBeans inherit <code>UserPasswordEditorMBean</code> interface. The service is also available for all those customized authentication providers whose MBeans inherit <code>UserPasswordEditorMBean</code>, to introduce the Password Validation Service into a customized authentication proivder, the following approach must be met:</p> <ol> <li> <p>In the <code>initialize</code> method of a customized provider implementation, must retrieve the Password Validation Service and register the service into a helper class such as <code>weblogic.security.provider.authentication.AuthenticationSecurityHelper</code>, the code might like as below:</p>  <p><code> import com.bea.common.security.service.PasswordValidationService;<br> import com.bea.common.security.legacy.ExtendedSecurityServices;<br> import com.bea.common.security.internal.legacy.helper.PasswordValidationServiceConfigHelper;<br> import weblogic.security.provider.authentication.AuthenticationSecurityHelper;<br> ......<br> ExtendedSecurityServices extendedSecurityServices = (ExtendedSecurityServices)securityServices;<br> PasswordValidationService serivce = (PasswordValidationService)extendedSecurityServices.getServices().getService(PasswordValidationServiceConfigHelper.getServiceName(providerMBean.getRealm()));<br> AuthenticationSecurityHelper.getInstance(providerMBean).registerPasswordValidationService(service);<br> ......</code></p> </li> <li> <p>In the <code>createUser</code>, <code>changeUserPassword</code> and(or) <code>resetUserPassword</code> methods of a customized authentication provider MBean, call the helper class to validate the new password to determine if the new password is valid. The code might be:</p>  <p><code> import weblogic.security.provider.authentication.AuthenticationSecurityHelper;<br> .....<br> AuthenticationSecurityHelper.getInstance(providerMBean).validatePassword(userName,password);<br> .....</code></p> </li> </ol> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.UserPasswordEditorMBean");
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
      Method mth = UserPasswordEditorMBean.class.getMethod("changeUserPassword", String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- The name of an existing user. "), createParameterDescriptor("oldPassword", "- The current password for the user. "), createParameterDescriptor("newPassword", "- The new password for the user. The Authentication provider determines the syntax requirements for passwords. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Used by a user to change his or her password. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("wls:auditProtectedArgs", "2,3");
      }

      mth = UserPasswordEditorMBean.class.getMethod("resetUserPassword", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- The name of an existing user. "), createParameterDescriptor("newPassword", "- The new password for the user. The Authentication provider determines the syntax requirements for passwords. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Used by an administrator to change a user's password. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("wls:auditProtectedArgs", "2");
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
