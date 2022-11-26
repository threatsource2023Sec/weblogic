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

public class UserLockoutManagerMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserLockoutManagerMBean.class;

   public UserLockoutManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserLockoutManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.UserLockoutManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Lists and manages lockouts on user accounts. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.UserLockoutManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("InvalidLoginAttemptsTotalCount")) {
         getterName = "getInvalidLoginAttemptsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvalidLoginAttemptsTotalCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("InvalidLoginAttemptsTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of invalid logins attempted since this server has been started and lockouts have been enabled.  In a cluster, this method returns the number of invalid logins attempted that have occured since the cluster has been started because all servers share login failure information. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getInvalidLoginAttemptsTotalCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InvalidLoginUsersHighCount")) {
         getterName = "getInvalidLoginUsersHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InvalidLoginUsersHighCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("InvalidLoginUsersHighCount", currentResult);
         currentResult.setValue("description", "Returns the highest number of users with concurrent unexpired or uncleared invalid login attempts.  Invalid login attempts expire as specified by <code>LockoutResetDuration</code>. This count is useful in determining whether the <code>LockoutCacheSize</code> needs to be modified. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getInvalidLoginUsersHighCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockedUsersCurrentCount")) {
         getterName = "getLockedUsersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LockedUsersCurrentCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockedUsersCurrentCount", currentResult);
         currentResult.setValue("description", "Returns the number of users that are currently locked out of this server. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getLockedUsersCurrentCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutCacheSize")) {
         getterName = "getLockoutCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutCacheSize";
         }

         currentResult = new PropertyDescriptor("LockoutCacheSize", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutCacheSize", currentResult);
         currentResult.setValue("description", "Returns the number of invalid login records that the server places in a cache. The server creates one record for each invalid login. ");
         setPropertyDescriptorDefault(currentResult, new Long(5L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutDuration")) {
         getterName = "getLockoutDuration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutDuration";
         }

         currentResult = new PropertyDescriptor("LockoutDuration", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutDuration", currentResult);
         currentResult.setValue("description", "Returns the number of minutes that a user account is locked out. ");
         setPropertyDescriptorDefault(currentResult, new Long(30L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutGCThreshold")) {
         getterName = "getLockoutGCThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutGCThreshold";
         }

         currentResult = new PropertyDescriptor("LockoutGCThreshold", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutGCThreshold", currentResult);
         currentResult.setValue("description", "<p>Returns the maximum number of invalid login records that the server keeps in memory.</p>  <p>If the number of invalid login records is equal to or greater than this value, the server's garbage collection purges the records that have expired. A record expires when the user associated with the record has been locked out.</p>  <p>The lower the threshold, the more often the server uses its resources to collect garbage.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(400L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutResetDuration")) {
         getterName = "getLockoutResetDuration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutResetDuration";
         }

         currentResult = new PropertyDescriptor("LockoutResetDuration", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutResetDuration", currentResult);
         currentResult.setValue("description", "The number of minutes within which consecutive invalid login attempts cause the user account to be locked out. ");
         setPropertyDescriptorDefault(currentResult, new Long(5L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutThreshold")) {
         getterName = "getLockoutThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutThreshold";
         }

         currentResult = new PropertyDescriptor("LockoutThreshold", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutThreshold", currentResult);
         currentResult.setValue("description", "Returns the maximum number of consecutive invalid login attempts before account is locked out.  When the number of invalid logins within a specified period of time is greater than <code>LockoutThreshold</code>value, the user is locked out. For example, with the default setting of <code>1</code>, the user is locked out on the second consecutive invalid login. With a setting of <code>2</code>, the user is locked out on the third consecutive invalid login. ");
         setPropertyDescriptorDefault(currentResult, new Long(5L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginAttemptsWhileLockedTotalCount")) {
         getterName = "getLoginAttemptsWhileLockedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LoginAttemptsWhileLockedTotalCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LoginAttemptsWhileLockedTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of invalid logins attempted since this server has been started and lockouts have been enabled. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getLoginAttemptsWhileLockedTotalCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration. ");
         setPropertyDescriptorDefault(currentResult, "UserLockoutManager");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("legal", "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Realm")) {
         getterName = "getRealm";
         setterName = null;
         currentResult = new PropertyDescriptor("Realm", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("Realm", currentResult);
         currentResult.setValue("description", "Returns the realm that contains this user lockout manager. Returns null if this security provider is not contained by a realm. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnlockedUsersTotalCount")) {
         getterName = "getUnlockedUsersTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UnlockedUsersTotalCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("UnlockedUsersTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number times users have been unlocked since this server has been started. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getUnlockedUsersTotalCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserLockoutTotalCount")) {
         getterName = "getUserLockoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UserLockoutTotalCount", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("UserLockoutTotalCount", currentResult);
         currentResult.setValue("description", "Returns the number of user lockouts that have occured since this server has been started.  In a cluster, this method returns the number of user lockouts that have occured since the cluster has been started because all servers share login failure information. ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getUserLockoutTotalCount()} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockoutEnabled")) {
         getterName = "isLockoutEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockoutEnabled";
         }

         currentResult = new PropertyDescriptor("LockoutEnabled", UserLockoutManagerMBean.class, getterName, setterName);
         descriptors.put("LockoutEnabled", currentResult);
         currentResult.setValue("description", "Returns whether the server locks out users when there are invalid login attempts.  A <code>true</code> value for this attribute causes the server to consider the other attributes of this MBean. A <code>false</code> value causes the server to ignore the other attributes of this MBean.\" ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
      Method mth = UserLockoutManagerMBean.class.getMethod("isLockedOut", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#isLockedOut(String)} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates whether a user is locked out. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerMBean.class.getMethod("clearLockout", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#clearLockout(String)} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Unlocks a user account. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerMBean.class.getMethod("getLastLoginFailure", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getLastLoginFailure(String)} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a string that indicates the time of the last invalid login for this user. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserLockoutManagerMBean.class.getMethod("getLoginFailureCount", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- A user name. If the user does not exist, this method returns <code>false</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.management.runtime.UserLockoutManagerRuntimeMBean#getLoginFailureCount(String)} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the current count of login failures for a specific user. This value returns to ");
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
