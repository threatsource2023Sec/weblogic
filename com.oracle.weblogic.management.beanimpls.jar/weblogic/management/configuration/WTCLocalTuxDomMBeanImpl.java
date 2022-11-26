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
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WTCLocalTuxDomMBeanImpl extends ConfigurationMBeanImpl implements WTCLocalTuxDomMBean, Serializable {
   private String _AccessPoint;
   private String _AccessPointId;
   private long _BlockTime;
   private int _CmpLimit;
   private String _ConnPrincipalName;
   private String _ConnectionPolicy;
   private String _IdentityKeyStoreFileName;
   private String _IdentityKeyStorePassPhrase;
   private byte[] _IdentityKeyStorePassPhraseEncrypted;
   private String _Interoperate;
   private int _KeepAlive;
   private int _KeepAliveWait;
   private String _KeyStoresLocation;
   private String _MaxEncryptBits;
   private long _MaxRetries;
   private String _MinEncryptBits;
   private String _NWAddr;
   private String _PrivateKeyAlias;
   private String _PrivateKeyPassPhrase;
   private byte[] _PrivateKeyPassPhraseEncrypted;
   private long _RetryInterval;
   private String _SSLProtocolVersion;
   private String _Security;
   private String _TrustKeyStoreFileName;
   private String _TrustKeyStorePassPhrase;
   private byte[] _TrustKeyStorePassPhraseEncrypted;
   private String _UseSSL;
   private static SchemaHelper2 _schemaHelper;

   public WTCLocalTuxDomMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCLocalTuxDomMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCLocalTuxDomMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("AccessPoint", param0);
      String _oldVal = this._AccessPoint;
      this._AccessPoint = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getAccessPoint() {
      return this._AccessPoint;
   }

   public boolean isAccessPointInherited() {
      return false;
   }

   public boolean isAccessPointSet() {
      return this._isSet(10);
   }

   public void setAccessPointId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("AccessPointId", param0);
      String _oldVal = this._AccessPointId;
      this._AccessPointId = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getAccessPointId() {
      return this._AccessPointId;
   }

   public boolean isAccessPointIdInherited() {
      return false;
   }

   public boolean isAccessPointIdSet() {
      return this._isSet(11);
   }

   public void setSecurity(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NONE", "APP_PW", "DM_PW"};
      param0 = LegalChecks.checkInEnum("Security", param0, _set);
      String _oldVal = this._Security;
      this._Security = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getSecurity() {
      if (!this._isSet(12)) {
         return this._isSecureModeEnabled() ? "DM_PW" : "NONE";
      } else {
         return this._Security;
      }
   }

   public boolean isSecurityInherited() {
      return false;
   }

   public boolean isSecuritySet() {
      return this._isSet(12);
   }

   public void setConnectionPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ON_DEMAND", "ON_STARTUP", "INCOMING_ONLY"};
      param0 = LegalChecks.checkInEnum("ConnectionPolicy", param0, _set);
      String _oldVal = this._ConnectionPolicy;
      this._ConnectionPolicy = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getConnectionPolicy() {
      return this._ConnectionPolicy;
   }

   public boolean isConnectionPolicyInherited() {
      return false;
   }

   public boolean isConnectionPolicySet() {
      return this._isSet(13);
   }

   public void setConnPrincipalName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnPrincipalName;
      this._ConnPrincipalName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getConnPrincipalName() {
      return this._ConnPrincipalName;
   }

   public boolean isConnPrincipalNameInherited() {
      return false;
   }

   public boolean isConnPrincipalNameSet() {
      return this._isSet(14);
   }

   public void setRetryInterval(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RetryInterval", param0, 0L, 2147483647L);
      long _oldVal = this._RetryInterval;
      this._RetryInterval = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getRetryInterval() {
      return this._RetryInterval;
   }

   public boolean isRetryIntervalInherited() {
      return false;
   }

   public boolean isRetryIntervalSet() {
      return this._isSet(15);
   }

   public void setMaxRetries(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MaxRetries", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._MaxRetries;
      this._MaxRetries = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getMaxRetries() {
      return this._MaxRetries;
   }

   public boolean isMaxRetriesInherited() {
      return false;
   }

   public boolean isMaxRetriesSet() {
      return this._isSet(16);
   }

   public void setBlockTime(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("BlockTime", param0, 0L, 2147483647L);
      long _oldVal = this._BlockTime;
      this._BlockTime = param0;
      this._postSet(17, _oldVal, param0);
   }

   public long getBlockTime() {
      return this._BlockTime;
   }

   public boolean isBlockTimeInherited() {
      return false;
   }

   public boolean isBlockTimeSet() {
      return this._isSet(17);
   }

   public void setNWAddr(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NWAddr;
      this._NWAddr = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getNWAddr() {
      return this._NWAddr;
   }

   public boolean isNWAddrInherited() {
      return false;
   }

   public boolean isNWAddrSet() {
      return this._isSet(18);
   }

   public void setCmpLimit(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("CmpLimit", (long)param0, 0L, 2147483647L);
      int _oldVal = this._CmpLimit;
      this._CmpLimit = param0;
      this._postSet(19, _oldVal, param0);
   }

   public int getCmpLimit() {
      return this._CmpLimit;
   }

   public boolean isCmpLimitInherited() {
      return false;
   }

   public boolean isCmpLimitSet() {
      return this._isSet(19);
   }

   public void setMinEncryptBits(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"0", "40", "56", "128", "256"};
      param0 = LegalChecks.checkInEnum("MinEncryptBits", param0, _set);
      String _oldVal = this._MinEncryptBits;
      this._MinEncryptBits = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getMinEncryptBits() {
      if (!this._isSet(20)) {
         return this._isSecureModeEnabled() ? "40" : "0";
      } else {
         return this._MinEncryptBits;
      }
   }

   public boolean isMinEncryptBitsInherited() {
      return false;
   }

   public boolean isMinEncryptBitsSet() {
      return this._isSet(20);
   }

   public void setMaxEncryptBits(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"0", "40", "56", "128", "256"};
      param0 = LegalChecks.checkInEnum("MaxEncryptBits", param0, _set);
      String _oldVal = this._MaxEncryptBits;
      this._MaxEncryptBits = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getMaxEncryptBits() {
      return this._MaxEncryptBits;
   }

   public boolean isMaxEncryptBitsInherited() {
      return false;
   }

   public boolean isMaxEncryptBitsSet() {
      return this._isSet(21);
   }

   public void setInteroperate(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Yes", "No"};
      param0 = LegalChecks.checkInEnum("Interoperate", param0, _set);
      String _oldVal = this._Interoperate;
      this._Interoperate = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getInteroperate() {
      return this._Interoperate;
   }

   public boolean isInteroperateInherited() {
      return false;
   }

   public boolean isInteroperateSet() {
      return this._isSet(22);
   }

   public void setKeepAlive(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("KeepAlive", (long)param0, 0L, 2147483647L);
      int _oldVal = this._KeepAlive;
      this._KeepAlive = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getKeepAlive() {
      return this._KeepAlive;
   }

   public boolean isKeepAliveInherited() {
      return false;
   }

   public boolean isKeepAliveSet() {
      return this._isSet(23);
   }

   public void setKeepAliveWait(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("KeepAliveWait", (long)param0, 0L, 2147483647L);
      int _oldVal = this._KeepAliveWait;
      this._KeepAliveWait = param0;
      this._postSet(24, _oldVal, param0);
   }

   public int getKeepAliveWait() {
      return this._KeepAliveWait;
   }

   public boolean isKeepAliveWaitInherited() {
      return false;
   }

   public boolean isKeepAliveWaitSet() {
      return this._isSet(24);
   }

   public String getUseSSL() {
      return this._UseSSL;
   }

   public boolean isUseSSLInherited() {
      return false;
   }

   public boolean isUseSSLSet() {
      return this._isSet(25);
   }

   public void setUseSSL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Off", "TwoWay", "OneWay"};
      param0 = LegalChecks.checkInEnum("UseSSL", param0, _set);
      String _oldVal = this._UseSSL;
      this._UseSSL = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getKeyStoresLocation() {
      return this._KeyStoresLocation;
   }

   public boolean isKeyStoresLocationInherited() {
      return false;
   }

   public boolean isKeyStoresLocationSet() {
      return this._isSet(26);
   }

   public void setKeyStoresLocation(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"WLS Stores", "Custom Stores"};
      param0 = LegalChecks.checkInEnum("KeyStoresLocation", param0, _set);
      String _oldVal = this._KeyStoresLocation;
      this._KeyStoresLocation = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getIdentityKeyStoreFileName() {
      return this._IdentityKeyStoreFileName;
   }

   public boolean isIdentityKeyStoreFileNameInherited() {
      return false;
   }

   public boolean isIdentityKeyStoreFileNameSet() {
      return this._isSet(27);
   }

   public void setIdentityKeyStoreFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IdentityKeyStoreFileName;
      this._IdentityKeyStoreFileName = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getIdentityKeyStorePassPhrase() {
      byte[] bEncrypted = this.getIdentityKeyStorePassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("IdentityKeyStorePassPhrase", bEncrypted);
   }

   public boolean isIdentityKeyStorePassPhraseInherited() {
      return false;
   }

   public boolean isIdentityKeyStorePassPhraseSet() {
      return this.isIdentityKeyStorePassPhraseEncryptedSet();
   }

   public void setIdentityKeyStorePassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setIdentityKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("IdentityKeyStorePassPhrase", param0));
   }

   public byte[] getIdentityKeyStorePassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._IdentityKeyStorePassPhraseEncrypted);
   }

   public String getIdentityKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getIdentityKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isIdentityKeyStorePassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isIdentityKeyStorePassPhraseEncryptedSet() {
      return this._isSet(29);
   }

   public void setIdentityKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setIdentityKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getPrivateKeyAlias() {
      return this._PrivateKeyAlias;
   }

   public boolean isPrivateKeyAliasInherited() {
      return false;
   }

   public boolean isPrivateKeyAliasSet() {
      return this._isSet(30);
   }

   public void setPrivateKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrivateKeyAlias;
      this._PrivateKeyAlias = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getPrivateKeyPassPhrase() {
      byte[] bEncrypted = this.getPrivateKeyPassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("PrivateKeyPassPhrase", bEncrypted);
   }

   public boolean isPrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isPrivateKeyPassPhraseSet() {
      return this.isPrivateKeyPassPhraseEncryptedSet();
   }

   public void setPrivateKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("PrivateKeyPassPhrase", param0));
   }

   public byte[] getPrivateKeyPassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._PrivateKeyPassPhraseEncrypted);
   }

   public String getPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPrivateKeyPassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(32);
   }

   public void setPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getTrustKeyStoreFileName() {
      return this._TrustKeyStoreFileName;
   }

   public boolean isTrustKeyStoreFileNameInherited() {
      return false;
   }

   public boolean isTrustKeyStoreFileNameSet() {
      return this._isSet(33);
   }

   public void setTrustKeyStoreFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrustKeyStoreFileName;
      this._TrustKeyStoreFileName = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getTrustKeyStorePassPhrase() {
      byte[] bEncrypted = this.getTrustKeyStorePassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("TrustKeyStorePassPhrase", bEncrypted);
   }

   public boolean isTrustKeyStorePassPhraseInherited() {
      return false;
   }

   public boolean isTrustKeyStorePassPhraseSet() {
      return this.isTrustKeyStorePassPhraseEncryptedSet();
   }

   public void setTrustKeyStorePassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setTrustKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("TrustKeyStorePassPhrase", param0));
   }

   public byte[] getTrustKeyStorePassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._TrustKeyStorePassPhraseEncrypted);
   }

   public String getTrustKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getTrustKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isTrustKeyStorePassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isTrustKeyStorePassPhraseEncryptedSet() {
      return this._isSet(35);
   }

   public void setTrustKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setTrustKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setSSLProtocolVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"TLSv1.0", "TLSv1.1", "TLSv1.2"};
      param0 = LegalChecks.checkInEnum("SSLProtocolVersion", param0, _set);
      String _oldVal = this._SSLProtocolVersion;
      this._SSLProtocolVersion = param0;
      this._postSet(36, _oldVal, param0);
   }

   public String getSSLProtocolVersion() {
      return this._SSLProtocolVersion;
   }

   public boolean isSSLProtocolVersionInherited() {
      return false;
   }

   public boolean isSSLProtocolVersionSet() {
      return this._isSet(36);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WTCLegalHelper.validateWTCLocalTuxDom(this);
   }

   public void setIdentityKeyStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._IdentityKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: IdentityKeyStorePassPhraseEncrypted of WTCLocalTuxDomMBean");
      } else {
         this._getHelper()._clearArray(this._IdentityKeyStorePassPhraseEncrypted);
         this._IdentityKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(29, _oldVal, param0);
      }
   }

   public void setPrivateKeyPassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._PrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PrivateKeyPassPhraseEncrypted of WTCLocalTuxDomMBean");
      } else {
         this._getHelper()._clearArray(this._PrivateKeyPassPhraseEncrypted);
         this._PrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(32, _oldVal, param0);
      }
   }

   public void setTrustKeyStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._TrustKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: TrustKeyStorePassPhraseEncrypted of WTCLocalTuxDomMBean");
      } else {
         this._getHelper()._clearArray(this._TrustKeyStorePassPhraseEncrypted);
         this._TrustKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(35, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 28) {
            this._markSet(29, false);
         }

         if (idx == 31) {
            this._markSet(32, false);
         }

         if (idx == 34) {
            this._markSet(35, false);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._AccessPoint = "myLAP";
               if (initOne) {
                  break;
               }
            case 11:
               this._AccessPointId = "myLAPId";
               if (initOne) {
                  break;
               }
            case 17:
               this._BlockTime = 60L;
               if (initOne) {
                  break;
               }
            case 19:
               this._CmpLimit = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 14:
               this._ConnPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ConnectionPolicy = "ON_DEMAND";
               if (initOne) {
                  break;
               }
            case 27:
               this._IdentityKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._IdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._IdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._Interoperate = "No";
               if (initOne) {
                  break;
               }
            case 23:
               this._KeepAlive = 0;
               if (initOne) {
                  break;
               }
            case 24:
               this._KeepAliveWait = 0;
               if (initOne) {
                  break;
               }
            case 26:
               this._KeyStoresLocation = "Custom Stores";
               if (initOne) {
                  break;
               }
            case 21:
               this._MaxEncryptBits = "128";
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxRetries = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 20:
               this._MinEncryptBits = "0";
               if (initOne) {
                  break;
               }
            case 18:
               this._NWAddr = "//localhost:8901";
               if (initOne) {
                  break;
               }
            case 30:
               this._PrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._PrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._PrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._RetryInterval = 60L;
               if (initOne) {
                  break;
               }
            case 36:
               this._SSLProtocolVersion = "TLSv1.2";
               if (initOne) {
                  break;
               }
            case 12:
               this._Security = "NONE";
               if (initOne) {
                  break;
               }
            case 33:
               this._TrustKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._TrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._TrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._UseSSL = "Off";
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
      return "WTCLocalTuxDom";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AccessPoint")) {
         oldVal = this._AccessPoint;
         this._AccessPoint = (String)v;
         this._postSet(10, oldVal, this._AccessPoint);
      } else if (name.equals("AccessPointId")) {
         oldVal = this._AccessPointId;
         this._AccessPointId = (String)v;
         this._postSet(11, oldVal, this._AccessPointId);
      } else {
         long oldVal;
         if (name.equals("BlockTime")) {
            oldVal = this._BlockTime;
            this._BlockTime = (Long)v;
            this._postSet(17, oldVal, this._BlockTime);
         } else {
            int oldVal;
            if (name.equals("CmpLimit")) {
               oldVal = this._CmpLimit;
               this._CmpLimit = (Integer)v;
               this._postSet(19, oldVal, this._CmpLimit);
            } else if (name.equals("ConnPrincipalName")) {
               oldVal = this._ConnPrincipalName;
               this._ConnPrincipalName = (String)v;
               this._postSet(14, oldVal, this._ConnPrincipalName);
            } else if (name.equals("ConnectionPolicy")) {
               oldVal = this._ConnectionPolicy;
               this._ConnectionPolicy = (String)v;
               this._postSet(13, oldVal, this._ConnectionPolicy);
            } else if (name.equals("IdentityKeyStoreFileName")) {
               oldVal = this._IdentityKeyStoreFileName;
               this._IdentityKeyStoreFileName = (String)v;
               this._postSet(27, oldVal, this._IdentityKeyStoreFileName);
            } else if (name.equals("IdentityKeyStorePassPhrase")) {
               oldVal = this._IdentityKeyStorePassPhrase;
               this._IdentityKeyStorePassPhrase = (String)v;
               this._postSet(28, oldVal, this._IdentityKeyStorePassPhrase);
            } else {
               byte[] oldVal;
               if (name.equals("IdentityKeyStorePassPhraseEncrypted")) {
                  oldVal = this._IdentityKeyStorePassPhraseEncrypted;
                  this._IdentityKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(29, oldVal, this._IdentityKeyStorePassPhraseEncrypted);
               } else if (name.equals("Interoperate")) {
                  oldVal = this._Interoperate;
                  this._Interoperate = (String)v;
                  this._postSet(22, oldVal, this._Interoperate);
               } else if (name.equals("KeepAlive")) {
                  oldVal = this._KeepAlive;
                  this._KeepAlive = (Integer)v;
                  this._postSet(23, oldVal, this._KeepAlive);
               } else if (name.equals("KeepAliveWait")) {
                  oldVal = this._KeepAliveWait;
                  this._KeepAliveWait = (Integer)v;
                  this._postSet(24, oldVal, this._KeepAliveWait);
               } else if (name.equals("KeyStoresLocation")) {
                  oldVal = this._KeyStoresLocation;
                  this._KeyStoresLocation = (String)v;
                  this._postSet(26, oldVal, this._KeyStoresLocation);
               } else if (name.equals("MaxEncryptBits")) {
                  oldVal = this._MaxEncryptBits;
                  this._MaxEncryptBits = (String)v;
                  this._postSet(21, oldVal, this._MaxEncryptBits);
               } else if (name.equals("MaxRetries")) {
                  oldVal = this._MaxRetries;
                  this._MaxRetries = (Long)v;
                  this._postSet(16, oldVal, this._MaxRetries);
               } else if (name.equals("MinEncryptBits")) {
                  oldVal = this._MinEncryptBits;
                  this._MinEncryptBits = (String)v;
                  this._postSet(20, oldVal, this._MinEncryptBits);
               } else if (name.equals("NWAddr")) {
                  oldVal = this._NWAddr;
                  this._NWAddr = (String)v;
                  this._postSet(18, oldVal, this._NWAddr);
               } else if (name.equals("PrivateKeyAlias")) {
                  oldVal = this._PrivateKeyAlias;
                  this._PrivateKeyAlias = (String)v;
                  this._postSet(30, oldVal, this._PrivateKeyAlias);
               } else if (name.equals("PrivateKeyPassPhrase")) {
                  oldVal = this._PrivateKeyPassPhrase;
                  this._PrivateKeyPassPhrase = (String)v;
                  this._postSet(31, oldVal, this._PrivateKeyPassPhrase);
               } else if (name.equals("PrivateKeyPassPhraseEncrypted")) {
                  oldVal = this._PrivateKeyPassPhraseEncrypted;
                  this._PrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(32, oldVal, this._PrivateKeyPassPhraseEncrypted);
               } else if (name.equals("RetryInterval")) {
                  oldVal = this._RetryInterval;
                  this._RetryInterval = (Long)v;
                  this._postSet(15, oldVal, this._RetryInterval);
               } else if (name.equals("SSLProtocolVersion")) {
                  oldVal = this._SSLProtocolVersion;
                  this._SSLProtocolVersion = (String)v;
                  this._postSet(36, oldVal, this._SSLProtocolVersion);
               } else if (name.equals("Security")) {
                  oldVal = this._Security;
                  this._Security = (String)v;
                  this._postSet(12, oldVal, this._Security);
               } else if (name.equals("TrustKeyStoreFileName")) {
                  oldVal = this._TrustKeyStoreFileName;
                  this._TrustKeyStoreFileName = (String)v;
                  this._postSet(33, oldVal, this._TrustKeyStoreFileName);
               } else if (name.equals("TrustKeyStorePassPhrase")) {
                  oldVal = this._TrustKeyStorePassPhrase;
                  this._TrustKeyStorePassPhrase = (String)v;
                  this._postSet(34, oldVal, this._TrustKeyStorePassPhrase);
               } else if (name.equals("TrustKeyStorePassPhraseEncrypted")) {
                  oldVal = this._TrustKeyStorePassPhraseEncrypted;
                  this._TrustKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(35, oldVal, this._TrustKeyStorePassPhraseEncrypted);
               } else if (name.equals("UseSSL")) {
                  oldVal = this._UseSSL;
                  this._UseSSL = (String)v;
                  this._postSet(25, oldVal, this._UseSSL);
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AccessPoint")) {
         return this._AccessPoint;
      } else if (name.equals("AccessPointId")) {
         return this._AccessPointId;
      } else if (name.equals("BlockTime")) {
         return new Long(this._BlockTime);
      } else if (name.equals("CmpLimit")) {
         return new Integer(this._CmpLimit);
      } else if (name.equals("ConnPrincipalName")) {
         return this._ConnPrincipalName;
      } else if (name.equals("ConnectionPolicy")) {
         return this._ConnectionPolicy;
      } else if (name.equals("IdentityKeyStoreFileName")) {
         return this._IdentityKeyStoreFileName;
      } else if (name.equals("IdentityKeyStorePassPhrase")) {
         return this._IdentityKeyStorePassPhrase;
      } else if (name.equals("IdentityKeyStorePassPhraseEncrypted")) {
         return this._IdentityKeyStorePassPhraseEncrypted;
      } else if (name.equals("Interoperate")) {
         return this._Interoperate;
      } else if (name.equals("KeepAlive")) {
         return new Integer(this._KeepAlive);
      } else if (name.equals("KeepAliveWait")) {
         return new Integer(this._KeepAliveWait);
      } else if (name.equals("KeyStoresLocation")) {
         return this._KeyStoresLocation;
      } else if (name.equals("MaxEncryptBits")) {
         return this._MaxEncryptBits;
      } else if (name.equals("MaxRetries")) {
         return new Long(this._MaxRetries);
      } else if (name.equals("MinEncryptBits")) {
         return this._MinEncryptBits;
      } else if (name.equals("NWAddr")) {
         return this._NWAddr;
      } else if (name.equals("PrivateKeyAlias")) {
         return this._PrivateKeyAlias;
      } else if (name.equals("PrivateKeyPassPhrase")) {
         return this._PrivateKeyPassPhrase;
      } else if (name.equals("PrivateKeyPassPhraseEncrypted")) {
         return this._PrivateKeyPassPhraseEncrypted;
      } else if (name.equals("RetryInterval")) {
         return new Long(this._RetryInterval);
      } else if (name.equals("SSLProtocolVersion")) {
         return this._SSLProtocolVersion;
      } else if (name.equals("Security")) {
         return this._Security;
      } else if (name.equals("TrustKeyStoreFileName")) {
         return this._TrustKeyStoreFileName;
      } else if (name.equals("TrustKeyStorePassPhrase")) {
         return this._TrustKeyStorePassPhrase;
      } else if (name.equals("TrustKeyStorePassPhraseEncrypted")) {
         return this._TrustKeyStorePassPhraseEncrypted;
      } else {
         return name.equals("UseSSL") ? this._UseSSL : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("AccessPoint", "myLAP");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AccessPoint in WTCLocalTuxDomMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("AccessPointId", "myLAPId");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AccessPointId in WTCLocalTuxDomMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("usessl")) {
                  return 25;
               }
               break;
            case 7:
               if (s.equals("nw-addr")) {
                  return 18;
               }
               break;
            case 8:
               if (s.equals("security")) {
                  return 12;
               }
               break;
            case 9:
               if (s.equals("cmp-limit")) {
                  return 19;
               }
               break;
            case 10:
               if (s.equals("block-time")) {
                  return 17;
               }

               if (s.equals("keep-alive")) {
                  return 23;
               }
               break;
            case 11:
               if (s.equals("max-retries")) {
                  return 16;
               }
               break;
            case 12:
               if (s.equals("access-point")) {
                  return 10;
               }

               if (s.equals("interoperate")) {
                  return 22;
               }
            case 13:
            case 18:
            case 21:
            case 22:
            case 24:
            case 26:
            case 29:
            case 31:
            case 32:
            case 34:
            case 35:
            case 36:
            case 38:
            case 39:
            default:
               break;
            case 14:
               if (s.equals("retry-interval")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("access-point-id")) {
                  return 11;
               }

               if (s.equals("keep-alive-wait")) {
                  return 24;
               }
               break;
            case 16:
               if (s.equals("max-encrypt-bits")) {
                  return 21;
               }

               if (s.equals("min-encrypt-bits")) {
                  return 20;
               }
               break;
            case 17:
               if (s.equals("connection-policy")) {
                  return 13;
               }

               if (s.equals("private-key-alias")) {
                  return 30;
               }
               break;
            case 19:
               if (s.equals("conn-principal-name")) {
                  return 14;
               }

               if (s.equals("key-stores-location")) {
                  return 26;
               }
               break;
            case 20:
               if (s.equals("ssl-protocol-version")) {
                  return 36;
               }
               break;
            case 23:
               if (s.equals("private-key-pass-phrase")) {
                  return 31;
               }
               break;
            case 25:
               if (s.equals("trust-key-store-file-name")) {
                  return 33;
               }
               break;
            case 27:
               if (s.equals("trust-key-store-pass-phrase")) {
                  return 34;
               }
               break;
            case 28:
               if (s.equals("identity-key-store-file-name")) {
                  return 27;
               }
               break;
            case 30:
               if (s.equals("identity-key-store-pass-phrase")) {
                  return 28;
               }
               break;
            case 33:
               if (s.equals("private-key-pass-phrase-encrypted")) {
                  return 32;
               }
               break;
            case 37:
               if (s.equals("trust-key-store-pass-phrase-encrypted")) {
                  return 35;
               }
               break;
            case 40:
               if (s.equals("identity-key-store-pass-phrase-encrypted")) {
                  return 29;
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
               return "access-point";
            case 11:
               return "access-point-id";
            case 12:
               return "security";
            case 13:
               return "connection-policy";
            case 14:
               return "conn-principal-name";
            case 15:
               return "retry-interval";
            case 16:
               return "max-retries";
            case 17:
               return "block-time";
            case 18:
               return "nw-addr";
            case 19:
               return "cmp-limit";
            case 20:
               return "min-encrypt-bits";
            case 21:
               return "max-encrypt-bits";
            case 22:
               return "interoperate";
            case 23:
               return "keep-alive";
            case 24:
               return "keep-alive-wait";
            case 25:
               return "usessl";
            case 26:
               return "key-stores-location";
            case 27:
               return "identity-key-store-file-name";
            case 28:
               return "identity-key-store-pass-phrase";
            case 29:
               return "identity-key-store-pass-phrase-encrypted";
            case 30:
               return "private-key-alias";
            case 31:
               return "private-key-pass-phrase";
            case 32:
               return "private-key-pass-phrase-encrypted";
            case 33:
               return "trust-key-store-file-name";
            case 34:
               return "trust-key-store-pass-phrase";
            case 35:
               return "trust-key-store-pass-phrase-encrypted";
            case 36:
               return "ssl-protocol-version";
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
      private WTCLocalTuxDomMBeanImpl bean;

      protected Helper(WTCLocalTuxDomMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "AccessPoint";
            case 11:
               return "AccessPointId";
            case 12:
               return "Security";
            case 13:
               return "ConnectionPolicy";
            case 14:
               return "ConnPrincipalName";
            case 15:
               return "RetryInterval";
            case 16:
               return "MaxRetries";
            case 17:
               return "BlockTime";
            case 18:
               return "NWAddr";
            case 19:
               return "CmpLimit";
            case 20:
               return "MinEncryptBits";
            case 21:
               return "MaxEncryptBits";
            case 22:
               return "Interoperate";
            case 23:
               return "KeepAlive";
            case 24:
               return "KeepAliveWait";
            case 25:
               return "UseSSL";
            case 26:
               return "KeyStoresLocation";
            case 27:
               return "IdentityKeyStoreFileName";
            case 28:
               return "IdentityKeyStorePassPhrase";
            case 29:
               return "IdentityKeyStorePassPhraseEncrypted";
            case 30:
               return "PrivateKeyAlias";
            case 31:
               return "PrivateKeyPassPhrase";
            case 32:
               return "PrivateKeyPassPhraseEncrypted";
            case 33:
               return "TrustKeyStoreFileName";
            case 34:
               return "TrustKeyStorePassPhrase";
            case 35:
               return "TrustKeyStorePassPhraseEncrypted";
            case 36:
               return "SSLProtocolVersion";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AccessPoint")) {
            return 10;
         } else if (propName.equals("AccessPointId")) {
            return 11;
         } else if (propName.equals("BlockTime")) {
            return 17;
         } else if (propName.equals("CmpLimit")) {
            return 19;
         } else if (propName.equals("ConnPrincipalName")) {
            return 14;
         } else if (propName.equals("ConnectionPolicy")) {
            return 13;
         } else if (propName.equals("IdentityKeyStoreFileName")) {
            return 27;
         } else if (propName.equals("IdentityKeyStorePassPhrase")) {
            return 28;
         } else if (propName.equals("IdentityKeyStorePassPhraseEncrypted")) {
            return 29;
         } else if (propName.equals("Interoperate")) {
            return 22;
         } else if (propName.equals("KeepAlive")) {
            return 23;
         } else if (propName.equals("KeepAliveWait")) {
            return 24;
         } else if (propName.equals("KeyStoresLocation")) {
            return 26;
         } else if (propName.equals("MaxEncryptBits")) {
            return 21;
         } else if (propName.equals("MaxRetries")) {
            return 16;
         } else if (propName.equals("MinEncryptBits")) {
            return 20;
         } else if (propName.equals("NWAddr")) {
            return 18;
         } else if (propName.equals("PrivateKeyAlias")) {
            return 30;
         } else if (propName.equals("PrivateKeyPassPhrase")) {
            return 31;
         } else if (propName.equals("PrivateKeyPassPhraseEncrypted")) {
            return 32;
         } else if (propName.equals("RetryInterval")) {
            return 15;
         } else if (propName.equals("SSLProtocolVersion")) {
            return 36;
         } else if (propName.equals("Security")) {
            return 12;
         } else if (propName.equals("TrustKeyStoreFileName")) {
            return 33;
         } else if (propName.equals("TrustKeyStorePassPhrase")) {
            return 34;
         } else if (propName.equals("TrustKeyStorePassPhraseEncrypted")) {
            return 35;
         } else {
            return propName.equals("UseSSL") ? 25 : super.getPropertyIndex(propName);
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
            if (this.bean.isAccessPointSet()) {
               buf.append("AccessPoint");
               buf.append(String.valueOf(this.bean.getAccessPoint()));
            }

            if (this.bean.isAccessPointIdSet()) {
               buf.append("AccessPointId");
               buf.append(String.valueOf(this.bean.getAccessPointId()));
            }

            if (this.bean.isBlockTimeSet()) {
               buf.append("BlockTime");
               buf.append(String.valueOf(this.bean.getBlockTime()));
            }

            if (this.bean.isCmpLimitSet()) {
               buf.append("CmpLimit");
               buf.append(String.valueOf(this.bean.getCmpLimit()));
            }

            if (this.bean.isConnPrincipalNameSet()) {
               buf.append("ConnPrincipalName");
               buf.append(String.valueOf(this.bean.getConnPrincipalName()));
            }

            if (this.bean.isConnectionPolicySet()) {
               buf.append("ConnectionPolicy");
               buf.append(String.valueOf(this.bean.getConnectionPolicy()));
            }

            if (this.bean.isIdentityKeyStoreFileNameSet()) {
               buf.append("IdentityKeyStoreFileName");
               buf.append(String.valueOf(this.bean.getIdentityKeyStoreFileName()));
            }

            if (this.bean.isIdentityKeyStorePassPhraseSet()) {
               buf.append("IdentityKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getIdentityKeyStorePassPhrase()));
            }

            if (this.bean.isIdentityKeyStorePassPhraseEncryptedSet()) {
               buf.append("IdentityKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIdentityKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isInteroperateSet()) {
               buf.append("Interoperate");
               buf.append(String.valueOf(this.bean.getInteroperate()));
            }

            if (this.bean.isKeepAliveSet()) {
               buf.append("KeepAlive");
               buf.append(String.valueOf(this.bean.getKeepAlive()));
            }

            if (this.bean.isKeepAliveWaitSet()) {
               buf.append("KeepAliveWait");
               buf.append(String.valueOf(this.bean.getKeepAliveWait()));
            }

            if (this.bean.isKeyStoresLocationSet()) {
               buf.append("KeyStoresLocation");
               buf.append(String.valueOf(this.bean.getKeyStoresLocation()));
            }

            if (this.bean.isMaxEncryptBitsSet()) {
               buf.append("MaxEncryptBits");
               buf.append(String.valueOf(this.bean.getMaxEncryptBits()));
            }

            if (this.bean.isMaxRetriesSet()) {
               buf.append("MaxRetries");
               buf.append(String.valueOf(this.bean.getMaxRetries()));
            }

            if (this.bean.isMinEncryptBitsSet()) {
               buf.append("MinEncryptBits");
               buf.append(String.valueOf(this.bean.getMinEncryptBits()));
            }

            if (this.bean.isNWAddrSet()) {
               buf.append("NWAddr");
               buf.append(String.valueOf(this.bean.getNWAddr()));
            }

            if (this.bean.isPrivateKeyAliasSet()) {
               buf.append("PrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getPrivateKeyAlias()));
            }

            if (this.bean.isPrivateKeyPassPhraseSet()) {
               buf.append("PrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getPrivateKeyPassPhrase()));
            }

            if (this.bean.isPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("PrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isRetryIntervalSet()) {
               buf.append("RetryInterval");
               buf.append(String.valueOf(this.bean.getRetryInterval()));
            }

            if (this.bean.isSSLProtocolVersionSet()) {
               buf.append("SSLProtocolVersion");
               buf.append(String.valueOf(this.bean.getSSLProtocolVersion()));
            }

            if (this.bean.isSecuritySet()) {
               buf.append("Security");
               buf.append(String.valueOf(this.bean.getSecurity()));
            }

            if (this.bean.isTrustKeyStoreFileNameSet()) {
               buf.append("TrustKeyStoreFileName");
               buf.append(String.valueOf(this.bean.getTrustKeyStoreFileName()));
            }

            if (this.bean.isTrustKeyStorePassPhraseSet()) {
               buf.append("TrustKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getTrustKeyStorePassPhrase()));
            }

            if (this.bean.isTrustKeyStorePassPhraseEncryptedSet()) {
               buf.append("TrustKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTrustKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isUseSSLSet()) {
               buf.append("UseSSL");
               buf.append(String.valueOf(this.bean.getUseSSL()));
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
            WTCLocalTuxDomMBeanImpl otherTyped = (WTCLocalTuxDomMBeanImpl)other;
            this.computeDiff("AccessPoint", this.bean.getAccessPoint(), otherTyped.getAccessPoint(), true);
            this.computeDiff("AccessPointId", this.bean.getAccessPointId(), otherTyped.getAccessPointId(), true);
            this.computeDiff("BlockTime", this.bean.getBlockTime(), otherTyped.getBlockTime(), true);
            this.computeDiff("CmpLimit", this.bean.getCmpLimit(), otherTyped.getCmpLimit(), true);
            this.computeDiff("ConnPrincipalName", this.bean.getConnPrincipalName(), otherTyped.getConnPrincipalName(), true);
            this.computeDiff("ConnectionPolicy", this.bean.getConnectionPolicy(), otherTyped.getConnectionPolicy(), true);
            this.computeDiff("IdentityKeyStoreFileName", this.bean.getIdentityKeyStoreFileName(), otherTyped.getIdentityKeyStoreFileName(), true);
            this.computeDiff("IdentityKeyStorePassPhraseEncrypted", this.bean.getIdentityKeyStorePassPhraseEncrypted(), otherTyped.getIdentityKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("Interoperate", this.bean.getInteroperate(), otherTyped.getInteroperate(), true);
            this.computeDiff("KeepAlive", this.bean.getKeepAlive(), otherTyped.getKeepAlive(), true);
            this.computeDiff("KeepAliveWait", this.bean.getKeepAliveWait(), otherTyped.getKeepAliveWait(), true);
            this.computeDiff("KeyStoresLocation", this.bean.getKeyStoresLocation(), otherTyped.getKeyStoresLocation(), true);
            this.computeDiff("MaxEncryptBits", this.bean.getMaxEncryptBits(), otherTyped.getMaxEncryptBits(), true);
            this.computeDiff("MaxRetries", this.bean.getMaxRetries(), otherTyped.getMaxRetries(), true);
            this.computeDiff("MinEncryptBits", this.bean.getMinEncryptBits(), otherTyped.getMinEncryptBits(), true);
            this.computeDiff("NWAddr", this.bean.getNWAddr(), otherTyped.getNWAddr(), true);
            this.computeDiff("PrivateKeyAlias", this.bean.getPrivateKeyAlias(), otherTyped.getPrivateKeyAlias(), true);
            this.computeDiff("PrivateKeyPassPhraseEncrypted", this.bean.getPrivateKeyPassPhraseEncrypted(), otherTyped.getPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("RetryInterval", this.bean.getRetryInterval(), otherTyped.getRetryInterval(), true);
            this.computeDiff("SSLProtocolVersion", this.bean.getSSLProtocolVersion(), otherTyped.getSSLProtocolVersion(), true);
            this.computeDiff("Security", this.bean.getSecurity(), otherTyped.getSecurity(), true);
            this.computeDiff("TrustKeyStoreFileName", this.bean.getTrustKeyStoreFileName(), otherTyped.getTrustKeyStoreFileName(), true);
            this.computeDiff("TrustKeyStorePassPhraseEncrypted", this.bean.getTrustKeyStorePassPhraseEncrypted(), otherTyped.getTrustKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("UseSSL", this.bean.getUseSSL(), otherTyped.getUseSSL(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCLocalTuxDomMBeanImpl original = (WTCLocalTuxDomMBeanImpl)event.getSourceBean();
            WTCLocalTuxDomMBeanImpl proposed = (WTCLocalTuxDomMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AccessPoint")) {
                  original.setAccessPoint(proposed.getAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("AccessPointId")) {
                  original.setAccessPointId(proposed.getAccessPointId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("BlockTime")) {
                  original.setBlockTime(proposed.getBlockTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("CmpLimit")) {
                  original.setCmpLimit(proposed.getCmpLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ConnPrincipalName")) {
                  original.setConnPrincipalName(proposed.getConnPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ConnectionPolicy")) {
                  original.setConnectionPolicy(proposed.getConnectionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("IdentityKeyStoreFileName")) {
                  original.setIdentityKeyStoreFileName(proposed.getIdentityKeyStoreFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (!prop.equals("IdentityKeyStorePassPhrase")) {
                  if (prop.equals("IdentityKeyStorePassPhraseEncrypted")) {
                     original.setIdentityKeyStorePassPhraseEncrypted(proposed.getIdentityKeyStorePassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (prop.equals("Interoperate")) {
                     original.setInteroperate(proposed.getInteroperate());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("KeepAlive")) {
                     original.setKeepAlive(proposed.getKeepAlive());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("KeepAliveWait")) {
                     original.setKeepAliveWait(proposed.getKeepAliveWait());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("KeyStoresLocation")) {
                     original.setKeyStoresLocation(proposed.getKeyStoresLocation());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("MaxEncryptBits")) {
                     original.setMaxEncryptBits(proposed.getMaxEncryptBits());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("MaxRetries")) {
                     original.setMaxRetries(proposed.getMaxRetries());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("MinEncryptBits")) {
                     original.setMinEncryptBits(proposed.getMinEncryptBits());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("NWAddr")) {
                     original.setNWAddr(proposed.getNWAddr());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("PrivateKeyAlias")) {
                     original.setPrivateKeyAlias(proposed.getPrivateKeyAlias());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else if (!prop.equals("PrivateKeyPassPhrase")) {
                     if (prop.equals("PrivateKeyPassPhraseEncrypted")) {
                        original.setPrivateKeyPassPhraseEncrypted(proposed.getPrivateKeyPassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("RetryInterval")) {
                        original.setRetryInterval(proposed.getRetryInterval());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (prop.equals("SSLProtocolVersion")) {
                        original.setSSLProtocolVersion(proposed.getSSLProtocolVersion());
                        original._conditionalUnset(update.isUnsetUpdate(), 36);
                     } else if (prop.equals("Security")) {
                        original.setSecurity(proposed.getSecurity());
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     } else if (prop.equals("TrustKeyStoreFileName")) {
                        original.setTrustKeyStoreFileName(proposed.getTrustKeyStoreFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (!prop.equals("TrustKeyStorePassPhrase")) {
                        if (prop.equals("TrustKeyStorePassPhraseEncrypted")) {
                           original.setTrustKeyStorePassPhraseEncrypted(proposed.getTrustKeyStorePassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 35);
                        } else if (prop.equals("UseSSL")) {
                           original.setUseSSL(proposed.getUseSSL());
                           original._conditionalUnset(update.isUnsetUpdate(), 25);
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
            WTCLocalTuxDomMBeanImpl copy = (WTCLocalTuxDomMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AccessPoint")) && this.bean.isAccessPointSet()) {
               copy.setAccessPoint(this.bean.getAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("AccessPointId")) && this.bean.isAccessPointIdSet()) {
               copy.setAccessPointId(this.bean.getAccessPointId());
            }

            if ((excludeProps == null || !excludeProps.contains("BlockTime")) && this.bean.isBlockTimeSet()) {
               copy.setBlockTime(this.bean.getBlockTime());
            }

            if ((excludeProps == null || !excludeProps.contains("CmpLimit")) && this.bean.isCmpLimitSet()) {
               copy.setCmpLimit(this.bean.getCmpLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnPrincipalName")) && this.bean.isConnPrincipalNameSet()) {
               copy.setConnPrincipalName(this.bean.getConnPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionPolicy")) && this.bean.isConnectionPolicySet()) {
               copy.setConnectionPolicy(this.bean.getConnectionPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityKeyStoreFileName")) && this.bean.isIdentityKeyStoreFileNameSet()) {
               copy.setIdentityKeyStoreFileName(this.bean.getIdentityKeyStoreFileName());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("IdentityKeyStorePassPhraseEncrypted")) && this.bean.isIdentityKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getIdentityKeyStorePassPhraseEncrypted();
               copy.setIdentityKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Interoperate")) && this.bean.isInteroperateSet()) {
               copy.setInteroperate(this.bean.getInteroperate());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAlive")) && this.bean.isKeepAliveSet()) {
               copy.setKeepAlive(this.bean.getKeepAlive());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAliveWait")) && this.bean.isKeepAliveWaitSet()) {
               copy.setKeepAliveWait(this.bean.getKeepAliveWait());
            }

            if ((excludeProps == null || !excludeProps.contains("KeyStoresLocation")) && this.bean.isKeyStoresLocationSet()) {
               copy.setKeyStoresLocation(this.bean.getKeyStoresLocation());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxEncryptBits")) && this.bean.isMaxEncryptBitsSet()) {
               copy.setMaxEncryptBits(this.bean.getMaxEncryptBits());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetries")) && this.bean.isMaxRetriesSet()) {
               copy.setMaxRetries(this.bean.getMaxRetries());
            }

            if ((excludeProps == null || !excludeProps.contains("MinEncryptBits")) && this.bean.isMinEncryptBitsSet()) {
               copy.setMinEncryptBits(this.bean.getMinEncryptBits());
            }

            if ((excludeProps == null || !excludeProps.contains("NWAddr")) && this.bean.isNWAddrSet()) {
               copy.setNWAddr(this.bean.getNWAddr());
            }

            if ((excludeProps == null || !excludeProps.contains("PrivateKeyAlias")) && this.bean.isPrivateKeyAliasSet()) {
               copy.setPrivateKeyAlias(this.bean.getPrivateKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("PrivateKeyPassPhraseEncrypted")) && this.bean.isPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getPrivateKeyPassPhraseEncrypted();
               copy.setPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RetryInterval")) && this.bean.isRetryIntervalSet()) {
               copy.setRetryInterval(this.bean.getRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLProtocolVersion")) && this.bean.isSSLProtocolVersionSet()) {
               copy.setSSLProtocolVersion(this.bean.getSSLProtocolVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Security")) && this.bean.isSecuritySet()) {
               copy.setSecurity(this.bean.getSecurity());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustKeyStoreFileName")) && this.bean.isTrustKeyStoreFileNameSet()) {
               copy.setTrustKeyStoreFileName(this.bean.getTrustKeyStoreFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustKeyStorePassPhraseEncrypted")) && this.bean.isTrustKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getTrustKeyStorePassPhraseEncrypted();
               copy.setTrustKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UseSSL")) && this.bean.isUseSSLSet()) {
               copy.setUseSSL(this.bean.getUseSSL());
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
