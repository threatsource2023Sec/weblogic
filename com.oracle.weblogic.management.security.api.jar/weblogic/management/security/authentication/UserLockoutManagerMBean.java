package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.RealmMBean;

public interface UserLockoutManagerMBean extends StandardInterface, DescriptorBean {
   /** @deprecated */
   @Deprecated
   long getUserLockoutTotalCount();

   /** @deprecated */
   @Deprecated
   long getInvalidLoginAttemptsTotalCount();

   /** @deprecated */
   @Deprecated
   long getLoginAttemptsWhileLockedTotalCount();

   /** @deprecated */
   @Deprecated
   long getInvalidLoginUsersHighCount();

   /** @deprecated */
   @Deprecated
   long getUnlockedUsersTotalCount();

   /** @deprecated */
   @Deprecated
   long getLockedUsersCurrentCount();

   RealmMBean getRealm();

   boolean isLockoutEnabled();

   void setLockoutEnabled(boolean var1) throws InvalidAttributeValueException;

   long getLockoutThreshold();

   void setLockoutThreshold(long var1) throws InvalidAttributeValueException;

   long getLockoutDuration();

   void setLockoutDuration(long var1) throws InvalidAttributeValueException;

   long getLockoutResetDuration();

   void setLockoutResetDuration(long var1) throws InvalidAttributeValueException;

   long getLockoutCacheSize();

   void setLockoutCacheSize(long var1) throws InvalidAttributeValueException;

   long getLockoutGCThreshold();

   void setLockoutGCThreshold(long var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isLockedOut(String var1);

   /** @deprecated */
   @Deprecated
   void clearLockout(String var1);

   /** @deprecated */
   @Deprecated
   long getLastLoginFailure(String var1);

   /** @deprecated */
   @Deprecated
   long getLoginFailureCount(String var1);

   String getName();

   void setName(String var1) throws InvalidAttributeValueException;

   String getCompatibilityObjectName();
}
