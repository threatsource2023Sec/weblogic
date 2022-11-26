package weblogic.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.UserLockoutManagerRuntimeMBean;

public class UserLockoutManagerRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserLockoutManagerRuntimeMBean.class;

   public UserLockoutManagerRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserLockoutManagerRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.security.UserLockoutManagerRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.security");
      String description = (new String("<p>This class is used to monitor and manage per security realm user lockout information.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.UserLockoutManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("InvalidLoginAttemptsTotalCount")) {
         getterName = "getInvalidLoginAttemptsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvalidLoginAttemptsTotalCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvalidLoginAttemptsTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of invalid logins attempted since this server has been started and lockouts have been enabled.  In a cluster, this method returns the number of invalid logins attempted that have occured since the cluster has been started because all servers share login failure information. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvalidLoginUsersHighCount")) {
         getterName = "getInvalidLoginUsersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvalidLoginUsersHighCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InvalidLoginUsersHighCount", currentResult);
         currentResult.setValue("description", "Returns the highest number of users with concurrent unexpired or uncleared invalid login attempts.  Invalid login attempts expire as specified by <code>LockoutResetDuration</code>. This count is useful in determining whether the <code>LockoutCacheSize</code> needs to be modified. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockedUsersCurrentCount")) {
         getterName = "getLockedUsersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LockedUsersCurrentCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LockedUsersCurrentCount", currentResult);
         currentResult.setValue("description", "Returns the number of users that are currently locked out of this server. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginAttemptsWhileLockedTotalCount")) {
         getterName = "getLoginAttemptsWhileLockedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LoginAttemptsWhileLockedTotalCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LoginAttemptsWhileLockedTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of invalid logins attempted since this server has been started and lockouts have been enabled. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnlockedUsersTotalCount")) {
         getterName = "getUnlockedUsersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UnlockedUsersTotalCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnlockedUsersTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number times users have been unlocked since this server has been started. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserLockoutTotalCount")) {
         getterName = "getUserLockoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UserLockoutTotalCount", UserLockoutManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserLockoutTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of user lockouts that have occured since this server has been started.  In a cluster, this method returns the number of user lockouts that have occured since the cluster has been started because all servers share login failure information. ");
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
      Method mth = UserLockoutManagerRuntimeMBean.class.getMethod("isLockedOut", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates whether a user is locked out. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerRuntimeMBean.class.getMethod("clearLockout", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Unlocks a user account. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerRuntimeMBean.class.getMethod("getLastLoginFailure", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a string that indicates the time of the last invalid login for this user. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerRuntimeMBean.class.getMethod("getLoginFailureCount", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the current count of login failures for a specific user. This value returns to ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = UserLockoutManagerRuntimeMBean.class.getMethod("isLockedOut", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. "), createParameterDescriptor("identityDomain", "- The identity domain of the user. May be null. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Indicates whether a user is locked out. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = UserLockoutManagerRuntimeMBean.class.getMethod("clearLockout", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. "), createParameterDescriptor("identityDomain", "- The identity domain of the user. May be null. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Unlocks a user account. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = UserLockoutManagerRuntimeMBean.class.getMethod("getLastLoginFailure", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. "), createParameterDescriptor("identityDomain", "- The identity domain of the user. May be null. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns a string that indicates the time of the last invalid login for this user. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = UserLockoutManagerRuntimeMBean.class.getMethod("getLoginFailureCount", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. "), createParameterDescriptor("identityDomain", "- The identity domain of the user. May be null. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Returns the current count of login failures for a specific user. This value returns to ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
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
