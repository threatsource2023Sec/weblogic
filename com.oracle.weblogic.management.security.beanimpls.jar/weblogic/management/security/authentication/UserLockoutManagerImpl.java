package weblogic.management.security.authentication;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.security.BaseMBeanImpl;
import weblogic.management.security.RealmMBean;
import weblogic.security.internal.UserLockoutManagerMBeanCustomizerImpl;

public class UserLockoutManagerImpl extends BaseMBeanImpl {
   private UserLockoutManagerMBeanCustomizerImpl customizer;

   public UserLockoutManagerImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected UserLockoutManagerImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }

   private UserLockoutManagerMBean getUserLockoutManager() {
      try {
         return (UserLockoutManagerMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }

   public String getCompatibilityObjectName() {
      String prefix = "Security:Name=";
      String result = prefix + this.getRealm().getName() + this.getUserLockoutManager().getName();
      return result;
   }

   private synchronized UserLockoutManagerMBeanCustomizerImpl getCustomizer() {
      if (this.customizer == null) {
         this.customizer = new UserLockoutManagerMBeanCustomizerImpl(this.getUserLockoutManager());
      }

      return this.customizer;
   }

   public RealmMBean getRealm() {
      DescriptorBean parent = this.getUserLockoutManager().getParentBean();
      return parent instanceof RealmMBean ? (RealmMBean)parent : null;
   }

   public long getUserLockoutTotalCount() throws MBeanException {
      return this.getCustomizer().getUserLockoutTotalCount();
   }

   public long getInvalidLoginAttemptsTotalCount() throws MBeanException {
      return this.getCustomizer().getInvalidLoginAttemptsTotalCount();
   }

   public long getLoginAttemptsWhileLockedTotalCount() throws MBeanException {
      return this.getCustomizer().getLoginAttemptsWhileLockedTotalCount();
   }

   public long getInvalidLoginUsersHighCount() throws MBeanException {
      return this.getCustomizer().getInvalidLoginUsersHighCount();
   }

   public long getUnlockedUsersTotalCount() throws MBeanException {
      return this.getCustomizer().getUnlockedUsersTotalCount();
   }

   public long getLockedUsersCurrentCount() throws MBeanException {
      return this.getCustomizer().getLockedUsersCurrentCount();
   }

   public boolean isLockedOut(String userName) throws MBeanException {
      return this.getCustomizer().isLockedOut(userName);
   }

   public long getLastLoginFailure(String userName) throws MBeanException {
      return this.getCustomizer().getLastLoginFailure(userName);
   }

   public long getLoginFailureCount(String userName) throws MBeanException {
      return this.getCustomizer().getLoginFailureCount(userName);
   }

   public void clearLockout(String userName) throws MBeanException {
      this.getCustomizer().clearLockout(userName);
   }
}
