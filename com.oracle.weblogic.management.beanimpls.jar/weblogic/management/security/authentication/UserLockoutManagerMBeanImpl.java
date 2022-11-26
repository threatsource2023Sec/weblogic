package weblogic.management.security.authentication;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.management.security.RealmMBean;
import weblogic.utils.collections.CombinedIterator;

public class UserLockoutManagerMBeanImpl extends AbstractCommoConfigurationBean implements UserLockoutManagerMBean, Serializable {
   private String _CompatibilityObjectName;
   private long _InvalidLoginAttemptsTotalCount;
   private long _InvalidLoginUsersHighCount;
   private long _LockedUsersCurrentCount;
   private long _LockoutCacheSize;
   private long _LockoutDuration;
   private boolean _LockoutEnabled;
   private long _LockoutGCThreshold;
   private long _LockoutResetDuration;
   private long _LockoutThreshold;
   private long _LoginAttemptsWhileLockedTotalCount;
   private String _Name;
   private RealmMBean _Realm;
   private long _UnlockedUsersTotalCount;
   private long _UserLockoutTotalCount;
   private transient UserLockoutManagerImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public UserLockoutManagerMBeanImpl() {
      try {
         this._customizer = new UserLockoutManagerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public UserLockoutManagerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new UserLockoutManagerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public UserLockoutManagerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new UserLockoutManagerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public long getUserLockoutTotalCount() {
      try {
         return this._customizer.getUserLockoutTotalCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isUserLockoutTotalCountInherited() {
      return false;
   }

   public boolean isUserLockoutTotalCountSet() {
      return this._isSet(2);
   }

   public void setUserLockoutTotalCount(long param0) throws InvalidAttributeValueException {
      this._UserLockoutTotalCount = param0;
   }

   public long getInvalidLoginAttemptsTotalCount() {
      try {
         return this._customizer.getInvalidLoginAttemptsTotalCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isInvalidLoginAttemptsTotalCountInherited() {
      return false;
   }

   public boolean isInvalidLoginAttemptsTotalCountSet() {
      return this._isSet(3);
   }

   public void setInvalidLoginAttemptsTotalCount(long param0) throws InvalidAttributeValueException {
      this._InvalidLoginAttemptsTotalCount = param0;
   }

   public long getLoginAttemptsWhileLockedTotalCount() {
      try {
         return this._customizer.getLoginAttemptsWhileLockedTotalCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isLoginAttemptsWhileLockedTotalCountInherited() {
      return false;
   }

   public boolean isLoginAttemptsWhileLockedTotalCountSet() {
      return this._isSet(4);
   }

   public void setLoginAttemptsWhileLockedTotalCount(long param0) throws InvalidAttributeValueException {
      this._LoginAttemptsWhileLockedTotalCount = param0;
   }

   public long getInvalidLoginUsersHighCount() {
      try {
         return this._customizer.getInvalidLoginUsersHighCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isInvalidLoginUsersHighCountInherited() {
      return false;
   }

   public boolean isInvalidLoginUsersHighCountSet() {
      return this._isSet(5);
   }

   public void setInvalidLoginUsersHighCount(long param0) throws InvalidAttributeValueException {
      this._InvalidLoginUsersHighCount = param0;
   }

   public long getUnlockedUsersTotalCount() {
      try {
         return this._customizer.getUnlockedUsersTotalCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isUnlockedUsersTotalCountInherited() {
      return false;
   }

   public boolean isUnlockedUsersTotalCountSet() {
      return this._isSet(6);
   }

   public void setUnlockedUsersTotalCount(long param0) throws InvalidAttributeValueException {
      this._UnlockedUsersTotalCount = param0;
   }

   public long getLockedUsersCurrentCount() {
      try {
         return this._customizer.getLockedUsersCurrentCount();
      } catch (MBeanException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isLockedUsersCurrentCountInherited() {
      return false;
   }

   public boolean isLockedUsersCurrentCountSet() {
      return this._isSet(7);
   }

   public void setLockedUsersCurrentCount(long param0) throws InvalidAttributeValueException {
      this._LockedUsersCurrentCount = param0;
   }

   public RealmMBean getRealm() {
      return this._customizer.getRealm();
   }

   public boolean isRealmInherited() {
      return false;
   }

   public boolean isRealmSet() {
      return this._isSet(8);
   }

   public void setRealm(RealmMBean param0) throws InvalidAttributeValueException {
      this._Realm = param0;
   }

   public boolean isLockoutEnabled() {
      return this._LockoutEnabled;
   }

   public boolean isLockoutEnabledInherited() {
      return false;
   }

   public boolean isLockoutEnabledSet() {
      return this._isSet(9);
   }

   public void setLockoutEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._LockoutEnabled;
      this._LockoutEnabled = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getLockoutThreshold() {
      return this._LockoutThreshold;
   }

   public boolean isLockoutThresholdInherited() {
      return false;
   }

   public boolean isLockoutThresholdSet() {
      return this._isSet(10);
   }

   public void setLockoutThreshold(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LockoutThreshold", param0, 1L, 2147483647L);
      long _oldVal = this._LockoutThreshold;
      this._LockoutThreshold = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getLockoutDuration() {
      return this._LockoutDuration;
   }

   public boolean isLockoutDurationInherited() {
      return false;
   }

   public boolean isLockoutDurationSet() {
      return this._isSet(11);
   }

   public void setLockoutDuration(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LockoutDuration", param0, 0L, 2147483647L);
      long _oldVal = this._LockoutDuration;
      this._LockoutDuration = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getLockoutResetDuration() {
      return this._LockoutResetDuration;
   }

   public boolean isLockoutResetDurationInherited() {
      return false;
   }

   public boolean isLockoutResetDurationSet() {
      return this._isSet(12);
   }

   public void setLockoutResetDuration(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LockoutResetDuration", param0, 1L, 2147483647L);
      long _oldVal = this._LockoutResetDuration;
      this._LockoutResetDuration = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getLockoutCacheSize() {
      return this._LockoutCacheSize;
   }

   public boolean isLockoutCacheSizeInherited() {
      return false;
   }

   public boolean isLockoutCacheSizeSet() {
      return this._isSet(13);
   }

   public void setLockoutCacheSize(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LockoutCacheSize", param0, 0L, 2147483647L);
      long _oldVal = this._LockoutCacheSize;
      this._LockoutCacheSize = param0;
      this._postSet(13, _oldVal, param0);
   }

   public long getLockoutGCThreshold() {
      return this._LockoutGCThreshold;
   }

   public boolean isLockoutGCThresholdInherited() {
      return false;
   }

   public boolean isLockoutGCThresholdSet() {
      return this._isSet(14);
   }

   public void setLockoutGCThreshold(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LockoutGCThreshold", param0, 0L, 2147483647L);
      long _oldVal = this._LockoutGCThreshold;
      this._LockoutGCThreshold = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isLockedOut(String param0) {
      try {
         return this._customizer.isLockedOut(param0);
      } catch (MBeanException var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public void clearLockout(String param0) {
      try {
         this._customizer.clearLockout(param0);
      } catch (MBeanException var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public long getLastLoginFailure(String param0) {
      try {
         return this._customizer.getLastLoginFailure(param0);
      } catch (MBeanException var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public long getLoginFailureCount(String param0) {
      try {
         return this._customizer.getLoginFailureCount(param0);
      } catch (MBeanException var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(15);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getCompatibilityObjectName() {
      return this._customizer.getCompatibilityObjectName();
   }

   public boolean isCompatibilityObjectNameInherited() {
      return false;
   }

   public boolean isCompatibilityObjectNameSet() {
      return this._isSet(16);
   }

   public void setCompatibilityObjectName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityObjectName;
      this._CompatibilityObjectName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._CompatibilityObjectName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._InvalidLoginAttemptsTotalCount = 0L;
               if (initOne) {
                  break;
               }
            case 5:
               this._InvalidLoginUsersHighCount = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._LockedUsersCurrentCount = 0L;
               if (initOne) {
                  break;
               }
            case 13:
               this._LockoutCacheSize = 5L;
               if (initOne) {
                  break;
               }
            case 11:
               this._LockoutDuration = 30L;
               if (initOne) {
                  break;
               }
            case 14:
               this._LockoutGCThreshold = 400L;
               if (initOne) {
                  break;
               }
            case 12:
               this._LockoutResetDuration = 5L;
               if (initOne) {
                  break;
               }
            case 10:
               this._LockoutThreshold = 5L;
               if (initOne) {
                  break;
               }
            case 4:
               this._LoginAttemptsWhileLockedTotalCount = 0L;
               if (initOne) {
                  break;
               }
            case 15:
               this._Name = "UserLockoutManager";
               if (initOne) {
                  break;
               }
            case 8:
               this._Realm = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._UnlockedUsersTotalCount = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._UserLockoutTotalCount = 0L;
               if (initOne) {
                  break;
               }
            case 9:
               this._LockoutEnabled = true;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.authentication.UserLockoutManagerMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 15;
               }
               break;
            case 5:
               if (s.equals("realm")) {
                  return 8;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 20:
            case 21:
            case 23:
            case 27:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            default:
               break;
            case 15:
               if (s.equals("lockout-enabled")) {
                  return 9;
               }
               break;
            case 16:
               if (s.equals("lockout-duration")) {
                  return 11;
               }
               break;
            case 17:
               if (s.equals("lockout-threshold")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("lockout-cache-size")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("lockoutgc-threshold")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("lockout-reset-duration")) {
                  return 12;
               }
               break;
            case 24:
               if (s.equals("user-lockout-total-count")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("compatibility-object-name")) {
                  return 16;
               }
               break;
            case 26:
               if (s.equals("locked-users-current-count")) {
                  return 7;
               }

               if (s.equals("unlocked-users-total-count")) {
                  return 6;
               }
               break;
            case 30:
               if (s.equals("invalid-login-users-high-count")) {
                  return 5;
               }
               break;
            case 34:
               if (s.equals("invalid-login-attempts-total-count")) {
                  return 3;
               }
               break;
            case 39:
               if (s.equals("login-attempts-while-locked-total-count")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "user-lockout-total-count";
            case 3:
               return "invalid-login-attempts-total-count";
            case 4:
               return "login-attempts-while-locked-total-count";
            case 5:
               return "invalid-login-users-high-count";
            case 6:
               return "unlocked-users-total-count";
            case 7:
               return "locked-users-current-count";
            case 8:
               return "realm";
            case 9:
               return "lockout-enabled";
            case 10:
               return "lockout-threshold";
            case 11:
               return "lockout-duration";
            case 12:
               return "lockout-reset-duration";
            case 13:
               return "lockout-cache-size";
            case 14:
               return "lockoutgc-threshold";
            case 15:
               return "name";
            case 16:
               return "compatibility-object-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private UserLockoutManagerMBeanImpl bean;

      protected Helper(UserLockoutManagerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "UserLockoutTotalCount";
            case 3:
               return "InvalidLoginAttemptsTotalCount";
            case 4:
               return "LoginAttemptsWhileLockedTotalCount";
            case 5:
               return "InvalidLoginUsersHighCount";
            case 6:
               return "UnlockedUsersTotalCount";
            case 7:
               return "LockedUsersCurrentCount";
            case 8:
               return "Realm";
            case 9:
               return "LockoutEnabled";
            case 10:
               return "LockoutThreshold";
            case 11:
               return "LockoutDuration";
            case 12:
               return "LockoutResetDuration";
            case 13:
               return "LockoutCacheSize";
            case 14:
               return "LockoutGCThreshold";
            case 15:
               return "Name";
            case 16:
               return "CompatibilityObjectName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompatibilityObjectName")) {
            return 16;
         } else if (propName.equals("InvalidLoginAttemptsTotalCount")) {
            return 3;
         } else if (propName.equals("InvalidLoginUsersHighCount")) {
            return 5;
         } else if (propName.equals("LockedUsersCurrentCount")) {
            return 7;
         } else if (propName.equals("LockoutCacheSize")) {
            return 13;
         } else if (propName.equals("LockoutDuration")) {
            return 11;
         } else if (propName.equals("LockoutGCThreshold")) {
            return 14;
         } else if (propName.equals("LockoutResetDuration")) {
            return 12;
         } else if (propName.equals("LockoutThreshold")) {
            return 10;
         } else if (propName.equals("LoginAttemptsWhileLockedTotalCount")) {
            return 4;
         } else if (propName.equals("Name")) {
            return 15;
         } else if (propName.equals("Realm")) {
            return 8;
         } else if (propName.equals("UnlockedUsersTotalCount")) {
            return 6;
         } else if (propName.equals("UserLockoutTotalCount")) {
            return 2;
         } else {
            return propName.equals("LockoutEnabled") ? 9 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isCompatibilityObjectNameSet()) {
               buf.append("CompatibilityObjectName");
               buf.append(String.valueOf(this.bean.getCompatibilityObjectName()));
            }

            if (this.bean.isInvalidLoginAttemptsTotalCountSet()) {
               buf.append("InvalidLoginAttemptsTotalCount");
               buf.append(String.valueOf(this.bean.getInvalidLoginAttemptsTotalCount()));
            }

            if (this.bean.isInvalidLoginUsersHighCountSet()) {
               buf.append("InvalidLoginUsersHighCount");
               buf.append(String.valueOf(this.bean.getInvalidLoginUsersHighCount()));
            }

            if (this.bean.isLockedUsersCurrentCountSet()) {
               buf.append("LockedUsersCurrentCount");
               buf.append(String.valueOf(this.bean.getLockedUsersCurrentCount()));
            }

            if (this.bean.isLockoutCacheSizeSet()) {
               buf.append("LockoutCacheSize");
               buf.append(String.valueOf(this.bean.getLockoutCacheSize()));
            }

            if (this.bean.isLockoutDurationSet()) {
               buf.append("LockoutDuration");
               buf.append(String.valueOf(this.bean.getLockoutDuration()));
            }

            if (this.bean.isLockoutGCThresholdSet()) {
               buf.append("LockoutGCThreshold");
               buf.append(String.valueOf(this.bean.getLockoutGCThreshold()));
            }

            if (this.bean.isLockoutResetDurationSet()) {
               buf.append("LockoutResetDuration");
               buf.append(String.valueOf(this.bean.getLockoutResetDuration()));
            }

            if (this.bean.isLockoutThresholdSet()) {
               buf.append("LockoutThreshold");
               buf.append(String.valueOf(this.bean.getLockoutThreshold()));
            }

            if (this.bean.isLoginAttemptsWhileLockedTotalCountSet()) {
               buf.append("LoginAttemptsWhileLockedTotalCount");
               buf.append(String.valueOf(this.bean.getLoginAttemptsWhileLockedTotalCount()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isRealmSet()) {
               buf.append("Realm");
               buf.append(String.valueOf(this.bean.getRealm()));
            }

            if (this.bean.isUnlockedUsersTotalCountSet()) {
               buf.append("UnlockedUsersTotalCount");
               buf.append(String.valueOf(this.bean.getUnlockedUsersTotalCount()));
            }

            if (this.bean.isUserLockoutTotalCountSet()) {
               buf.append("UserLockoutTotalCount");
               buf.append(String.valueOf(this.bean.getUserLockoutTotalCount()));
            }

            if (this.bean.isLockoutEnabledSet()) {
               buf.append("LockoutEnabled");
               buf.append(String.valueOf(this.bean.isLockoutEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            UserLockoutManagerMBeanImpl otherTyped = (UserLockoutManagerMBeanImpl)other;
            this.computeDiff("CompatibilityObjectName", this.bean.getCompatibilityObjectName(), otherTyped.getCompatibilityObjectName(), false);
            this.computeDiff("LockoutCacheSize", this.bean.getLockoutCacheSize(), otherTyped.getLockoutCacheSize(), false);
            this.computeDiff("LockoutDuration", this.bean.getLockoutDuration(), otherTyped.getLockoutDuration(), false);
            this.computeDiff("LockoutGCThreshold", this.bean.getLockoutGCThreshold(), otherTyped.getLockoutGCThreshold(), false);
            this.computeDiff("LockoutResetDuration", this.bean.getLockoutResetDuration(), otherTyped.getLockoutResetDuration(), false);
            this.computeDiff("LockoutThreshold", this.bean.getLockoutThreshold(), otherTyped.getLockoutThreshold(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("LockoutEnabled", this.bean.isLockoutEnabled(), otherTyped.isLockoutEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            UserLockoutManagerMBeanImpl original = (UserLockoutManagerMBeanImpl)event.getSourceBean();
            UserLockoutManagerMBeanImpl proposed = (UserLockoutManagerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompatibilityObjectName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (!prop.equals("InvalidLoginAttemptsTotalCount") && !prop.equals("InvalidLoginUsersHighCount") && !prop.equals("LockedUsersCurrentCount")) {
                  if (prop.equals("LockoutCacheSize")) {
                     original.setLockoutCacheSize(proposed.getLockoutCacheSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("LockoutDuration")) {
                     original.setLockoutDuration(proposed.getLockoutDuration());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("LockoutGCThreshold")) {
                     original.setLockoutGCThreshold(proposed.getLockoutGCThreshold());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("LockoutResetDuration")) {
                     original.setLockoutResetDuration(proposed.getLockoutResetDuration());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("LockoutThreshold")) {
                     original.setLockoutThreshold(proposed.getLockoutThreshold());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (!prop.equals("LoginAttemptsWhileLockedTotalCount")) {
                     if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (!prop.equals("Realm") && !prop.equals("UnlockedUsersTotalCount") && !prop.equals("UserLockoutTotalCount")) {
                        if (prop.equals("LockoutEnabled")) {
                           original.setLockoutEnabled(proposed.isLockoutEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 9);
                        } else {
                           super.applyPropertyUpdate(event, update);
                        }
                     }
                  }
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            UserLockoutManagerMBeanImpl copy = (UserLockoutManagerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompatibilityObjectName")) && this.bean.isCompatibilityObjectNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutCacheSize")) && this.bean.isLockoutCacheSizeSet()) {
               copy.setLockoutCacheSize(this.bean.getLockoutCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutDuration")) && this.bean.isLockoutDurationSet()) {
               copy.setLockoutDuration(this.bean.getLockoutDuration());
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutGCThreshold")) && this.bean.isLockoutGCThresholdSet()) {
               copy.setLockoutGCThreshold(this.bean.getLockoutGCThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutResetDuration")) && this.bean.isLockoutResetDurationSet()) {
               copy.setLockoutResetDuration(this.bean.getLockoutResetDuration());
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutThreshold")) && this.bean.isLockoutThresholdSet()) {
               copy.setLockoutThreshold(this.bean.getLockoutThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("LockoutEnabled")) && this.bean.isLockoutEnabledSet()) {
               copy.setLockoutEnabled(this.bean.isLockoutEnabled());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getRealm(), clazz, annotation);
      }
   }
}
