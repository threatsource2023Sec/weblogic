package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class EmbeddedLDAPMBeanImpl extends ConfigurationMBeanImpl implements EmbeddedLDAPMBean, Serializable {
   private boolean _AnonymousBindAllowed;
   private int _BackupCopies;
   private int _BackupHour;
   private int _BackupMinute;
   private boolean _CacheEnabled;
   private int _CacheSize;
   private int _CacheTTL;
   private String _Credential;
   private byte[] _CredentialEncrypted;
   private boolean _KeepAliveEnabled;
   private boolean _MasterFirst;
   private boolean _RefreshReplicaAtStartup;
   private int _Timeout;
   private static SchemaHelper2 _schemaHelper;

   public EmbeddedLDAPMBeanImpl() {
      this._initializeProperty(-1);
   }

   public EmbeddedLDAPMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EmbeddedLDAPMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCredential() {
      byte[] bEncrypted = this.getCredentialEncrypted();
      return bEncrypted == null ? null : this._decrypt("Credential", bEncrypted);
   }

   public boolean isCredentialInherited() {
      return false;
   }

   public boolean isCredentialSet() {
      return this.isCredentialEncryptedSet();
   }

   public void setCredential(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setCredentialEncrypted(param0 == null ? null : this._encrypt("Credential", param0));
   }

   public byte[] getCredentialEncrypted() {
      return this._getHelper()._cloneArray(this._CredentialEncrypted);
   }

   public String getCredentialEncryptedAsString() {
      byte[] obj = this.getCredentialEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCredentialEncryptedInherited() {
      return false;
   }

   public boolean isCredentialEncryptedSet() {
      return this._isSet(11);
   }

   public void setCredentialEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCredentialEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public int getBackupHour() {
      return this._BackupHour;
   }

   public boolean isBackupHourInherited() {
      return false;
   }

   public boolean isBackupHourSet() {
      return this._isSet(12);
   }

   public void setBackupHour(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("BackupHour", (long)param0, 0L, 23L);
      int _oldVal = this._BackupHour;
      this._BackupHour = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getBackupMinute() {
      return this._BackupMinute;
   }

   public boolean isBackupMinuteInherited() {
      return false;
   }

   public boolean isBackupMinuteSet() {
      return this._isSet(13);
   }

   public void setBackupMinute(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("BackupMinute", (long)param0, 0L, 59L);
      int _oldVal = this._BackupMinute;
      this._BackupMinute = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getBackupCopies() {
      return this._BackupCopies;
   }

   public boolean isBackupCopiesInherited() {
      return false;
   }

   public boolean isBackupCopiesSet() {
      return this._isSet(14);
   }

   public void setBackupCopies(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("BackupCopies", (long)param0, 0L, 65534L);
      int _oldVal = this._BackupCopies;
      this._BackupCopies = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isCacheEnabled() {
      return this._CacheEnabled;
   }

   public boolean isCacheEnabledInherited() {
      return false;
   }

   public boolean isCacheEnabledSet() {
      return this._isSet(15);
   }

   public void setCacheEnabled(boolean param0) {
      boolean _oldVal = this._CacheEnabled;
      this._CacheEnabled = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getCacheSize() {
      return this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return false;
   }

   public boolean isCacheSizeSet() {
      return this._isSet(16);
   }

   public void setCacheSize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheSize", param0, 0);
      int _oldVal = this._CacheSize;
      this._CacheSize = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getCacheTTL() {
      return this._CacheTTL;
   }

   public boolean isCacheTTLInherited() {
      return false;
   }

   public boolean isCacheTTLSet() {
      return this._isSet(17);
   }

   public void setCacheTTL(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheTTL", param0, 0);
      int _oldVal = this._CacheTTL;
      this._CacheTTL = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isRefreshReplicaAtStartup() {
      return this._RefreshReplicaAtStartup;
   }

   public boolean isRefreshReplicaAtStartupInherited() {
      return false;
   }

   public boolean isRefreshReplicaAtStartupSet() {
      return this._isSet(18);
   }

   public void setRefreshReplicaAtStartup(boolean param0) {
      boolean _oldVal = this._RefreshReplicaAtStartup;
      this._RefreshReplicaAtStartup = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isMasterFirst() {
      return this._MasterFirst;
   }

   public boolean isMasterFirstInherited() {
      return false;
   }

   public boolean isMasterFirstSet() {
      return this._isSet(19);
   }

   public void setMasterFirst(boolean param0) {
      boolean _oldVal = this._MasterFirst;
      this._MasterFirst = param0;
      this._postSet(19, _oldVal, param0);
   }

   public int getTimeout() {
      return this._Timeout;
   }

   public boolean isTimeoutInherited() {
      return false;
   }

   public boolean isTimeoutSet() {
      return this._isSet(20);
   }

   public void setTimeout(int param0) {
      LegalChecks.checkMin("Timeout", param0, 0);
      int _oldVal = this._Timeout;
      this._Timeout = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isKeepAliveEnabled() {
      return this._KeepAliveEnabled;
   }

   public boolean isKeepAliveEnabledInherited() {
      return false;
   }

   public boolean isKeepAliveEnabledSet() {
      return this._isSet(21);
   }

   public void setKeepAliveEnabled(boolean param0) {
      boolean _oldVal = this._KeepAliveEnabled;
      this._KeepAliveEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isAnonymousBindAllowed() {
      return this._AnonymousBindAllowed;
   }

   public boolean isAnonymousBindAllowedInherited() {
      return false;
   }

   public boolean isAnonymousBindAllowedSet() {
      return this._isSet(22);
   }

   public void setAnonymousBindAllowed(boolean param0) {
      boolean _oldVal = this._AnonymousBindAllowed;
      this._AnonymousBindAllowed = param0;
      this._postSet(22, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setCredentialEncrypted(byte[] param0) {
      byte[] _oldVal = this._CredentialEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CredentialEncrypted of EmbeddedLDAPMBean");
      } else {
         this._getHelper()._clearArray(this._CredentialEncrypted);
         this._CredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(11, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 10) {
            this._markSet(11, false);
         }
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._BackupCopies = 7;
               if (initOne) {
                  break;
               }
            case 12:
               this._BackupHour = 23;
               if (initOne) {
                  break;
               }
            case 13:
               this._BackupMinute = 5;
               if (initOne) {
                  break;
               }
            case 16:
               this._CacheSize = 32;
               if (initOne) {
                  break;
               }
            case 17:
               this._CacheTTL = 60;
               if (initOne) {
                  break;
               }
            case 10:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._Timeout = 0;
               if (initOne) {
                  break;
               }
            case 22:
               this._AnonymousBindAllowed = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._CacheEnabled = true;
               if (initOne) {
                  break;
               }
            case 21:
               this._KeepAliveEnabled = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._MasterFirst = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._RefreshReplicaAtStartup = false;
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "EmbeddedLDAP";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AnonymousBindAllowed")) {
         oldVal = this._AnonymousBindAllowed;
         this._AnonymousBindAllowed = (Boolean)v;
         this._postSet(22, oldVal, this._AnonymousBindAllowed);
      } else {
         int oldVal;
         if (name.equals("BackupCopies")) {
            oldVal = this._BackupCopies;
            this._BackupCopies = (Integer)v;
            this._postSet(14, oldVal, this._BackupCopies);
         } else if (name.equals("BackupHour")) {
            oldVal = this._BackupHour;
            this._BackupHour = (Integer)v;
            this._postSet(12, oldVal, this._BackupHour);
         } else if (name.equals("BackupMinute")) {
            oldVal = this._BackupMinute;
            this._BackupMinute = (Integer)v;
            this._postSet(13, oldVal, this._BackupMinute);
         } else if (name.equals("CacheEnabled")) {
            oldVal = this._CacheEnabled;
            this._CacheEnabled = (Boolean)v;
            this._postSet(15, oldVal, this._CacheEnabled);
         } else if (name.equals("CacheSize")) {
            oldVal = this._CacheSize;
            this._CacheSize = (Integer)v;
            this._postSet(16, oldVal, this._CacheSize);
         } else if (name.equals("CacheTTL")) {
            oldVal = this._CacheTTL;
            this._CacheTTL = (Integer)v;
            this._postSet(17, oldVal, this._CacheTTL);
         } else if (name.equals("Credential")) {
            String oldVal = this._Credential;
            this._Credential = (String)v;
            this._postSet(10, oldVal, this._Credential);
         } else if (name.equals("CredentialEncrypted")) {
            byte[] oldVal = this._CredentialEncrypted;
            this._CredentialEncrypted = (byte[])((byte[])v);
            this._postSet(11, oldVal, this._CredentialEncrypted);
         } else if (name.equals("KeepAliveEnabled")) {
            oldVal = this._KeepAliveEnabled;
            this._KeepAliveEnabled = (Boolean)v;
            this._postSet(21, oldVal, this._KeepAliveEnabled);
         } else if (name.equals("MasterFirst")) {
            oldVal = this._MasterFirst;
            this._MasterFirst = (Boolean)v;
            this._postSet(19, oldVal, this._MasterFirst);
         } else if (name.equals("RefreshReplicaAtStartup")) {
            oldVal = this._RefreshReplicaAtStartup;
            this._RefreshReplicaAtStartup = (Boolean)v;
            this._postSet(18, oldVal, this._RefreshReplicaAtStartup);
         } else if (name.equals("Timeout")) {
            oldVal = this._Timeout;
            this._Timeout = (Integer)v;
            this._postSet(20, oldVal, this._Timeout);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AnonymousBindAllowed")) {
         return new Boolean(this._AnonymousBindAllowed);
      } else if (name.equals("BackupCopies")) {
         return new Integer(this._BackupCopies);
      } else if (name.equals("BackupHour")) {
         return new Integer(this._BackupHour);
      } else if (name.equals("BackupMinute")) {
         return new Integer(this._BackupMinute);
      } else if (name.equals("CacheEnabled")) {
         return new Boolean(this._CacheEnabled);
      } else if (name.equals("CacheSize")) {
         return new Integer(this._CacheSize);
      } else if (name.equals("CacheTTL")) {
         return new Integer(this._CacheTTL);
      } else if (name.equals("Credential")) {
         return this._Credential;
      } else if (name.equals("CredentialEncrypted")) {
         return this._CredentialEncrypted;
      } else if (name.equals("KeepAliveEnabled")) {
         return new Boolean(this._KeepAliveEnabled);
      } else if (name.equals("MasterFirst")) {
         return new Boolean(this._MasterFirst);
      } else if (name.equals("RefreshReplicaAtStartup")) {
         return new Boolean(this._RefreshReplicaAtStartup);
      } else {
         return name.equals("Timeout") ? new Integer(this._Timeout) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("timeout")) {
                  return 20;
               }
               break;
            case 8:
               if (s.equals("cachettl")) {
                  return 17;
               }
            case 9:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 10:
               if (s.equals("cache-size")) {
                  return 16;
               }

               if (s.equals("credential")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("backup-hour")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("master-first")) {
                  return 19;
               }
               break;
            case 13:
               if (s.equals("backup-copies")) {
                  return 14;
               }

               if (s.equals("backup-minute")) {
                  return 13;
               }

               if (s.equals("cache-enabled")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("keep-alive-enabled")) {
                  return 21;
               }
               break;
            case 20:
               if (s.equals("credential-encrypted")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("anonymous-bind-allowed")) {
                  return 22;
               }
               break;
            case 26:
               if (s.equals("refresh-replica-at-startup")) {
                  return 18;
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
            case 10:
               return "credential";
            case 11:
               return "credential-encrypted";
            case 12:
               return "backup-hour";
            case 13:
               return "backup-minute";
            case 14:
               return "backup-copies";
            case 15:
               return "cache-enabled";
            case 16:
               return "cache-size";
            case 17:
               return "cachettl";
            case 18:
               return "refresh-replica-at-startup";
            case 19:
               return "master-first";
            case 20:
               return "timeout";
            case 21:
               return "keep-alive-enabled";
            case 22:
               return "anonymous-bind-allowed";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private EmbeddedLDAPMBeanImpl bean;

      protected Helper(EmbeddedLDAPMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Credential";
            case 11:
               return "CredentialEncrypted";
            case 12:
               return "BackupHour";
            case 13:
               return "BackupMinute";
            case 14:
               return "BackupCopies";
            case 15:
               return "CacheEnabled";
            case 16:
               return "CacheSize";
            case 17:
               return "CacheTTL";
            case 18:
               return "RefreshReplicaAtStartup";
            case 19:
               return "MasterFirst";
            case 20:
               return "Timeout";
            case 21:
               return "KeepAliveEnabled";
            case 22:
               return "AnonymousBindAllowed";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BackupCopies")) {
            return 14;
         } else if (propName.equals("BackupHour")) {
            return 12;
         } else if (propName.equals("BackupMinute")) {
            return 13;
         } else if (propName.equals("CacheSize")) {
            return 16;
         } else if (propName.equals("CacheTTL")) {
            return 17;
         } else if (propName.equals("Credential")) {
            return 10;
         } else if (propName.equals("CredentialEncrypted")) {
            return 11;
         } else if (propName.equals("Timeout")) {
            return 20;
         } else if (propName.equals("AnonymousBindAllowed")) {
            return 22;
         } else if (propName.equals("CacheEnabled")) {
            return 15;
         } else if (propName.equals("KeepAliveEnabled")) {
            return 21;
         } else if (propName.equals("MasterFirst")) {
            return 19;
         } else {
            return propName.equals("RefreshReplicaAtStartup") ? 18 : super.getPropertyIndex(propName);
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
            if (this.bean.isBackupCopiesSet()) {
               buf.append("BackupCopies");
               buf.append(String.valueOf(this.bean.getBackupCopies()));
            }

            if (this.bean.isBackupHourSet()) {
               buf.append("BackupHour");
               buf.append(String.valueOf(this.bean.getBackupHour()));
            }

            if (this.bean.isBackupMinuteSet()) {
               buf.append("BackupMinute");
               buf.append(String.valueOf(this.bean.getBackupMinute()));
            }

            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isCacheTTLSet()) {
               buf.append("CacheTTL");
               buf.append(String.valueOf(this.bean.getCacheTTL()));
            }

            if (this.bean.isCredentialSet()) {
               buf.append("Credential");
               buf.append(String.valueOf(this.bean.getCredential()));
            }

            if (this.bean.isCredentialEncryptedSet()) {
               buf.append("CredentialEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCredentialEncrypted())));
            }

            if (this.bean.isTimeoutSet()) {
               buf.append("Timeout");
               buf.append(String.valueOf(this.bean.getTimeout()));
            }

            if (this.bean.isAnonymousBindAllowedSet()) {
               buf.append("AnonymousBindAllowed");
               buf.append(String.valueOf(this.bean.isAnonymousBindAllowed()));
            }

            if (this.bean.isCacheEnabledSet()) {
               buf.append("CacheEnabled");
               buf.append(String.valueOf(this.bean.isCacheEnabled()));
            }

            if (this.bean.isKeepAliveEnabledSet()) {
               buf.append("KeepAliveEnabled");
               buf.append(String.valueOf(this.bean.isKeepAliveEnabled()));
            }

            if (this.bean.isMasterFirstSet()) {
               buf.append("MasterFirst");
               buf.append(String.valueOf(this.bean.isMasterFirst()));
            }

            if (this.bean.isRefreshReplicaAtStartupSet()) {
               buf.append("RefreshReplicaAtStartup");
               buf.append(String.valueOf(this.bean.isRefreshReplicaAtStartup()));
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
            EmbeddedLDAPMBeanImpl otherTyped = (EmbeddedLDAPMBeanImpl)other;
            this.computeDiff("BackupCopies", this.bean.getBackupCopies(), otherTyped.getBackupCopies(), false);
            this.computeDiff("BackupHour", this.bean.getBackupHour(), otherTyped.getBackupHour(), false);
            this.computeDiff("BackupMinute", this.bean.getBackupMinute(), otherTyped.getBackupMinute(), false);
            this.computeDiff("CacheSize", this.bean.getCacheSize(), otherTyped.getCacheSize(), false);
            this.computeDiff("CacheTTL", this.bean.getCacheTTL(), otherTyped.getCacheTTL(), false);
            this.computeDiff("CredentialEncrypted", this.bean.getCredentialEncrypted(), otherTyped.getCredentialEncrypted(), false);
            this.computeDiff("Timeout", this.bean.getTimeout(), otherTyped.getTimeout(), false);
            this.computeDiff("AnonymousBindAllowed", this.bean.isAnonymousBindAllowed(), otherTyped.isAnonymousBindAllowed(), false);
            this.computeDiff("CacheEnabled", this.bean.isCacheEnabled(), otherTyped.isCacheEnabled(), false);
            this.computeDiff("KeepAliveEnabled", this.bean.isKeepAliveEnabled(), otherTyped.isKeepAliveEnabled(), false);
            this.computeDiff("MasterFirst", this.bean.isMasterFirst(), otherTyped.isMasterFirst(), false);
            this.computeDiff("RefreshReplicaAtStartup", this.bean.isRefreshReplicaAtStartup(), otherTyped.isRefreshReplicaAtStartup(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EmbeddedLDAPMBeanImpl original = (EmbeddedLDAPMBeanImpl)event.getSourceBean();
            EmbeddedLDAPMBeanImpl proposed = (EmbeddedLDAPMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BackupCopies")) {
                  original.setBackupCopies(proposed.getBackupCopies());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("BackupHour")) {
                  original.setBackupHour(proposed.getBackupHour());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("BackupMinute")) {
                  original.setBackupMinute(proposed.getBackupMinute());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CacheSize")) {
                  original.setCacheSize(proposed.getCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("CacheTTL")) {
                  original.setCacheTTL(proposed.getCacheTTL());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (!prop.equals("Credential")) {
                  if (prop.equals("CredentialEncrypted")) {
                     original.setCredentialEncrypted(proposed.getCredentialEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("Timeout")) {
                     original.setTimeout(proposed.getTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("AnonymousBindAllowed")) {
                     original.setAnonymousBindAllowed(proposed.isAnonymousBindAllowed());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("CacheEnabled")) {
                     original.setCacheEnabled(proposed.isCacheEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("KeepAliveEnabled")) {
                     original.setKeepAliveEnabled(proposed.isKeepAliveEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("MasterFirst")) {
                     original.setMasterFirst(proposed.isMasterFirst());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("RefreshReplicaAtStartup")) {
                     original.setRefreshReplicaAtStartup(proposed.isRefreshReplicaAtStartup());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else {
                     super.applyPropertyUpdate(event, update);
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
            EmbeddedLDAPMBeanImpl copy = (EmbeddedLDAPMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BackupCopies")) && this.bean.isBackupCopiesSet()) {
               copy.setBackupCopies(this.bean.getBackupCopies());
            }

            if ((excludeProps == null || !excludeProps.contains("BackupHour")) && this.bean.isBackupHourSet()) {
               copy.setBackupHour(this.bean.getBackupHour());
            }

            if ((excludeProps == null || !excludeProps.contains("BackupMinute")) && this.bean.isBackupMinuteSet()) {
               copy.setBackupMinute(this.bean.getBackupMinute());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheSize")) && this.bean.isCacheSizeSet()) {
               copy.setCacheSize(this.bean.getCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheTTL")) && this.bean.isCacheTTLSet()) {
               copy.setCacheTTL(this.bean.getCacheTTL());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialEncrypted")) && this.bean.isCredentialEncryptedSet()) {
               Object o = this.bean.getCredentialEncrypted();
               copy.setCredentialEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Timeout")) && this.bean.isTimeoutSet()) {
               copy.setTimeout(this.bean.getTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("AnonymousBindAllowed")) && this.bean.isAnonymousBindAllowedSet()) {
               copy.setAnonymousBindAllowed(this.bean.isAnonymousBindAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheEnabled")) && this.bean.isCacheEnabledSet()) {
               copy.setCacheEnabled(this.bean.isCacheEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAliveEnabled")) && this.bean.isKeepAliveEnabledSet()) {
               copy.setKeepAliveEnabled(this.bean.isKeepAliveEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("MasterFirst")) && this.bean.isMasterFirstSet()) {
               copy.setMasterFirst(this.bean.isMasterFirst());
            }

            if ((excludeProps == null || !excludeProps.contains("RefreshReplicaAtStartup")) && this.bean.isRefreshReplicaAtStartupSet()) {
               copy.setRefreshReplicaAtStartup(this.bean.isRefreshReplicaAtStartup());
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
      }
   }
}
