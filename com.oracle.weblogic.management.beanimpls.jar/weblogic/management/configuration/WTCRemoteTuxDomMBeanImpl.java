package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.utils.collections.CombinedIterator;

public class WTCRemoteTuxDomMBeanImpl extends ConfigurationMBeanImpl implements WTCRemoteTuxDomMBean, Serializable {
   private String _AccessPoint;
   private String _AccessPointId;
   private String _AclPolicy;
   private boolean _AllowAnonymous;
   private String _AppKey;
   private int _CmpLimit;
   private String _ConnPrincipalName;
   private String _ConnectionPolicy;
   private String _CredentialPolicy;
   private String _CustomAppKeyClass;
   private String _CustomAppKeyClassParam;
   private int _DefaultAppKey;
   private String _FederationName;
   private String _FederationURL;
   private int _KeepAlive;
   private int _KeepAliveWait;
   private String _LocalAccessPoint;
   private String _MaxEncryptBits;
   private long _MaxRetries;
   private String _MinEncryptBits;
   private String _NWAddr;
   private long _RetryInterval;
   private String _SSLProtocolVersion;
   private String _TpUsrFile;
   private String _TuxedoGidKw;
   private String _TuxedoUidKw;
   private static SchemaHelper2 _schemaHelper;

   public WTCRemoteTuxDomMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCRemoteTuxDomMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCRemoteTuxDomMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public void setConnectionPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ON_DEMAND", "ON_STARTUP", "INCOMING_ONLY", "LOCAL"};
      param0 = LegalChecks.checkInEnum("ConnectionPolicy", param0, _set);
      String _oldVal = this._ConnectionPolicy;
      this._ConnectionPolicy = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getConnectionPolicy() {
      return this._ConnectionPolicy;
   }

   public boolean isConnectionPolicyInherited() {
      return false;
   }

   public boolean isConnectionPolicySet() {
      return this._isSet(12);
   }

   public void setAclPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"GLOBAL", "LOCAL"};
      param0 = LegalChecks.checkInEnum("AclPolicy", param0, _set);
      String _oldVal = this._AclPolicy;
      this._AclPolicy = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getAclPolicy() {
      return this._AclPolicy;
   }

   public boolean isAclPolicyInherited() {
      return false;
   }

   public boolean isAclPolicySet() {
      return this._isSet(13);
   }

   public void setCredentialPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"GLOBAL", "LOCAL"};
      param0 = LegalChecks.checkInEnum("CredentialPolicy", param0, _set);
      String _oldVal = this._CredentialPolicy;
      this._CredentialPolicy = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getCredentialPolicy() {
      return this._CredentialPolicy;
   }

   public boolean isCredentialPolicyInherited() {
      return false;
   }

   public boolean isCredentialPolicySet() {
      return this._isSet(14);
   }

   public void setTpUsrFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TpUsrFile;
      this._TpUsrFile = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getTpUsrFile() {
      return this._TpUsrFile;
   }

   public boolean isTpUsrFileInherited() {
      return false;
   }

   public boolean isTpUsrFileSet() {
      return this._isSet(15);
   }

   public void setLocalAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalAccessPoint", param0);
      String _oldVal = this._LocalAccessPoint;
      this._LocalAccessPoint = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getLocalAccessPoint() {
      return this._LocalAccessPoint;
   }

   public boolean isLocalAccessPointInherited() {
      return false;
   }

   public boolean isLocalAccessPointSet() {
      return this._isSet(16);
   }

   public void setConnPrincipalName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnPrincipalName;
      this._ConnPrincipalName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getConnPrincipalName() {
      return this._ConnPrincipalName;
   }

   public boolean isConnPrincipalNameInherited() {
      return false;
   }

   public boolean isConnPrincipalNameSet() {
      return this._isSet(17);
   }

   public void setRetryInterval(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RetryInterval", param0, -1L, 2147483647L);
      long _oldVal = this._RetryInterval;
      this._RetryInterval = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getRetryInterval() {
      return this._RetryInterval;
   }

   public boolean isRetryIntervalInherited() {
      return false;
   }

   public boolean isRetryIntervalSet() {
      return this._isSet(18);
   }

   public void setMaxRetries(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MaxRetries", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._MaxRetries;
      this._MaxRetries = param0;
      this._postSet(19, _oldVal, param0);
   }

   public long getMaxRetries() {
      return this._MaxRetries;
   }

   public boolean isMaxRetriesInherited() {
      return false;
   }

   public boolean isMaxRetriesSet() {
      return this._isSet(19);
   }

   public void setNWAddr(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NWAddr;
      this._NWAddr = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getNWAddr() {
      return this._NWAddr;
   }

   public boolean isNWAddrInherited() {
      return false;
   }

   public boolean isNWAddrSet() {
      return this._isSet(20);
   }

   public void setFederationURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FederationURL;
      this._FederationURL = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getFederationURL() {
      return this._FederationURL;
   }

   public boolean isFederationURLInherited() {
      return false;
   }

   public boolean isFederationURLSet() {
      return this._isSet(21);
   }

   public void setFederationName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FederationName;
      this._FederationName = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getFederationName() {
      return this._FederationName;
   }

   public boolean isFederationNameInherited() {
      return false;
   }

   public boolean isFederationNameSet() {
      return this._isSet(22);
   }

   public void setCmpLimit(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("CmpLimit", (long)param0, 0L, 2147483647L);
      int _oldVal = this._CmpLimit;
      this._CmpLimit = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getCmpLimit() {
      return this._CmpLimit;
   }

   public boolean isCmpLimitInherited() {
      return false;
   }

   public boolean isCmpLimitSet() {
      return this._isSet(23);
   }

   public void setMinEncryptBits(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"0", "40", "56", "128"};
      param0 = LegalChecks.checkInEnum("MinEncryptBits", param0, _set);
      String _oldVal = this._MinEncryptBits;
      this._MinEncryptBits = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getMinEncryptBits() {
      if (!this._isSet(24)) {
         return this._isSecureModeEnabled() ? "40" : "0";
      } else {
         return this._MinEncryptBits;
      }
   }

   public boolean isMinEncryptBitsInherited() {
      return false;
   }

   public boolean isMinEncryptBitsSet() {
      return this._isSet(24);
   }

   public void setMaxEncryptBits(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"0", "40", "56", "128"};
      param0 = LegalChecks.checkInEnum("MaxEncryptBits", param0, _set);
      String _oldVal = this._MaxEncryptBits;
      this._MaxEncryptBits = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getMaxEncryptBits() {
      return this._MaxEncryptBits;
   }

   public boolean isMaxEncryptBitsInherited() {
      return false;
   }

   public boolean isMaxEncryptBitsSet() {
      return this._isSet(25);
   }

   public void setSSLProtocolVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"TLSv1.0", "TLSv1.1", "TLSv1.2"};
      param0 = LegalChecks.checkInEnum("SSLProtocolVersion", param0, _set);
      String _oldVal = this._SSLProtocolVersion;
      this._SSLProtocolVersion = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getSSLProtocolVersion() {
      return this._SSLProtocolVersion;
   }

   public boolean isSSLProtocolVersionInherited() {
      return false;
   }

   public boolean isSSLProtocolVersionSet() {
      return this._isSet(26);
   }

   public void setAppKey(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"TpUsrFile", "LDAP", "Custom"};
      param0 = LegalChecks.checkInEnum("AppKey", param0, _set);
      String _oldVal = this._AppKey;
      this._AppKey = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getAppKey() {
      return this._AppKey;
   }

   public boolean isAppKeyInherited() {
      return false;
   }

   public boolean isAppKeySet() {
      return this._isSet(27);
   }

   public void setAllowAnonymous(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._AllowAnonymous;
      this._AllowAnonymous = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean getAllowAnonymous() {
      return this._AllowAnonymous;
   }

   public boolean isAllowAnonymousInherited() {
      return false;
   }

   public boolean isAllowAnonymousSet() {
      return this._isSet(28);
   }

   public void setDefaultAppKey(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._DefaultAppKey;
      this._DefaultAppKey = param0;
      this._postSet(29, _oldVal, param0);
   }

   public int getDefaultAppKey() {
      return this._DefaultAppKey;
   }

   public boolean isDefaultAppKeyInherited() {
      return false;
   }

   public boolean isDefaultAppKeySet() {
      return this._isSet(29);
   }

   public void setTuxedoUidKw(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TuxedoUidKw;
      this._TuxedoUidKw = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getTuxedoUidKw() {
      return this._TuxedoUidKw;
   }

   public boolean isTuxedoUidKwInherited() {
      return false;
   }

   public boolean isTuxedoUidKwSet() {
      return this._isSet(30);
   }

   public void setTuxedoGidKw(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TuxedoGidKw;
      this._TuxedoGidKw = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getTuxedoGidKw() {
      return this._TuxedoGidKw;
   }

   public boolean isTuxedoGidKwInherited() {
      return false;
   }

   public boolean isTuxedoGidKwSet() {
      return this._isSet(31);
   }

   public void setCustomAppKeyClass(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CustomAppKeyClass;
      this._CustomAppKeyClass = param0;
      this._postSet(32, _oldVal, param0);
   }

   public String getCustomAppKeyClass() {
      return this._CustomAppKeyClass;
   }

   public boolean isCustomAppKeyClassInherited() {
      return false;
   }

   public boolean isCustomAppKeyClassSet() {
      return this._isSet(32);
   }

   public void setCustomAppKeyClassParam(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CustomAppKeyClassParam;
      this._CustomAppKeyClassParam = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getCustomAppKeyClassParam() {
      return this._CustomAppKeyClassParam;
   }

   public boolean isCustomAppKeyClassParamInherited() {
      return false;
   }

   public boolean isCustomAppKeyClassParamSet() {
      return this._isSet(33);
   }

   public void setKeepAlive(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("KeepAlive", (long)param0, -1L, 2147483647L);
      int _oldVal = this._KeepAlive;
      this._KeepAlive = param0;
      this._postSet(34, _oldVal, param0);
   }

   public int getKeepAlive() {
      return this._KeepAlive;
   }

   public boolean isKeepAliveInherited() {
      return false;
   }

   public boolean isKeepAliveSet() {
      return this._isSet(34);
   }

   public void setKeepAliveWait(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("KeepAliveWait", (long)param0, 0L, 2147483647L);
      int _oldVal = this._KeepAliveWait;
      this._KeepAliveWait = param0;
      this._postSet(35, _oldVal, param0);
   }

   public int getKeepAliveWait() {
      return this._KeepAliveWait;
   }

   public boolean isKeepAliveWaitInherited() {
      return false;
   }

   public boolean isKeepAliveWaitSet() {
      return this._isSet(35);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WTCLegalHelper.validateWTCRemoteTuxDom(this);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._AccessPoint = "myRAP";
               if (initOne) {
                  break;
               }
            case 11:
               this._AccessPointId = "myRAPId";
               if (initOne) {
                  break;
               }
            case 13:
               this._AclPolicy = "LOCAL";
               if (initOne) {
                  break;
               }
            case 28:
               this._AllowAnonymous = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._AppKey = "TpUsrFile";
               if (initOne) {
                  break;
               }
            case 23:
               this._CmpLimit = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 17:
               this._ConnPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ConnectionPolicy = "LOCAL";
               if (initOne) {
                  break;
               }
            case 14:
               this._CredentialPolicy = "LOCAL";
               if (initOne) {
                  break;
               }
            case 32:
               this._CustomAppKeyClass = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._CustomAppKeyClassParam = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._DefaultAppKey = -1;
               if (initOne) {
                  break;
               }
            case 22:
               this._FederationName = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._FederationURL = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._KeepAlive = 0;
               if (initOne) {
                  break;
               }
            case 35:
               this._KeepAliveWait = 0;
               if (initOne) {
                  break;
               }
            case 16:
               this._LocalAccessPoint = "myLAP";
               if (initOne) {
                  break;
               }
            case 25:
               this._MaxEncryptBits = "128";
               if (initOne) {
                  break;
               }
            case 19:
               this._MaxRetries = -1L;
               if (initOne) {
                  break;
               }
            case 24:
               this._MinEncryptBits = "0";
               if (initOne) {
                  break;
               }
            case 20:
               this._NWAddr = "//localhost:8902";
               if (initOne) {
                  break;
               }
            case 18:
               this._RetryInterval = -1L;
               if (initOne) {
                  break;
               }
            case 26:
               this._SSLProtocolVersion = "TLSv1.2";
               if (initOne) {
                  break;
               }
            case 15:
               this._TpUsrFile = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._TuxedoGidKw = "TUXEDO_GID";
               if (initOne) {
                  break;
               }
            case 30:
               this._TuxedoUidKw = "TUXEDO_UID";
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
      return "WTCRemoteTuxDom";
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
      } else if (name.equals("AclPolicy")) {
         oldVal = this._AclPolicy;
         this._AclPolicy = (String)v;
         this._postSet(13, oldVal, this._AclPolicy);
      } else if (name.equals("AllowAnonymous")) {
         boolean oldVal = this._AllowAnonymous;
         this._AllowAnonymous = (Boolean)v;
         this._postSet(28, oldVal, this._AllowAnonymous);
      } else if (name.equals("AppKey")) {
         oldVal = this._AppKey;
         this._AppKey = (String)v;
         this._postSet(27, oldVal, this._AppKey);
      } else {
         int oldVal;
         if (name.equals("CmpLimit")) {
            oldVal = this._CmpLimit;
            this._CmpLimit = (Integer)v;
            this._postSet(23, oldVal, this._CmpLimit);
         } else if (name.equals("ConnPrincipalName")) {
            oldVal = this._ConnPrincipalName;
            this._ConnPrincipalName = (String)v;
            this._postSet(17, oldVal, this._ConnPrincipalName);
         } else if (name.equals("ConnectionPolicy")) {
            oldVal = this._ConnectionPolicy;
            this._ConnectionPolicy = (String)v;
            this._postSet(12, oldVal, this._ConnectionPolicy);
         } else if (name.equals("CredentialPolicy")) {
            oldVal = this._CredentialPolicy;
            this._CredentialPolicy = (String)v;
            this._postSet(14, oldVal, this._CredentialPolicy);
         } else if (name.equals("CustomAppKeyClass")) {
            oldVal = this._CustomAppKeyClass;
            this._CustomAppKeyClass = (String)v;
            this._postSet(32, oldVal, this._CustomAppKeyClass);
         } else if (name.equals("CustomAppKeyClassParam")) {
            oldVal = this._CustomAppKeyClassParam;
            this._CustomAppKeyClassParam = (String)v;
            this._postSet(33, oldVal, this._CustomAppKeyClassParam);
         } else if (name.equals("DefaultAppKey")) {
            oldVal = this._DefaultAppKey;
            this._DefaultAppKey = (Integer)v;
            this._postSet(29, oldVal, this._DefaultAppKey);
         } else if (name.equals("FederationName")) {
            oldVal = this._FederationName;
            this._FederationName = (String)v;
            this._postSet(22, oldVal, this._FederationName);
         } else if (name.equals("FederationURL")) {
            oldVal = this._FederationURL;
            this._FederationURL = (String)v;
            this._postSet(21, oldVal, this._FederationURL);
         } else if (name.equals("KeepAlive")) {
            oldVal = this._KeepAlive;
            this._KeepAlive = (Integer)v;
            this._postSet(34, oldVal, this._KeepAlive);
         } else if (name.equals("KeepAliveWait")) {
            oldVal = this._KeepAliveWait;
            this._KeepAliveWait = (Integer)v;
            this._postSet(35, oldVal, this._KeepAliveWait);
         } else if (name.equals("LocalAccessPoint")) {
            oldVal = this._LocalAccessPoint;
            this._LocalAccessPoint = (String)v;
            this._postSet(16, oldVal, this._LocalAccessPoint);
         } else if (name.equals("MaxEncryptBits")) {
            oldVal = this._MaxEncryptBits;
            this._MaxEncryptBits = (String)v;
            this._postSet(25, oldVal, this._MaxEncryptBits);
         } else {
            long oldVal;
            if (name.equals("MaxRetries")) {
               oldVal = this._MaxRetries;
               this._MaxRetries = (Long)v;
               this._postSet(19, oldVal, this._MaxRetries);
            } else if (name.equals("MinEncryptBits")) {
               oldVal = this._MinEncryptBits;
               this._MinEncryptBits = (String)v;
               this._postSet(24, oldVal, this._MinEncryptBits);
            } else if (name.equals("NWAddr")) {
               oldVal = this._NWAddr;
               this._NWAddr = (String)v;
               this._postSet(20, oldVal, this._NWAddr);
            } else if (name.equals("RetryInterval")) {
               oldVal = this._RetryInterval;
               this._RetryInterval = (Long)v;
               this._postSet(18, oldVal, this._RetryInterval);
            } else if (name.equals("SSLProtocolVersion")) {
               oldVal = this._SSLProtocolVersion;
               this._SSLProtocolVersion = (String)v;
               this._postSet(26, oldVal, this._SSLProtocolVersion);
            } else if (name.equals("TpUsrFile")) {
               oldVal = this._TpUsrFile;
               this._TpUsrFile = (String)v;
               this._postSet(15, oldVal, this._TpUsrFile);
            } else if (name.equals("TuxedoGidKw")) {
               oldVal = this._TuxedoGidKw;
               this._TuxedoGidKw = (String)v;
               this._postSet(31, oldVal, this._TuxedoGidKw);
            } else if (name.equals("TuxedoUidKw")) {
               oldVal = this._TuxedoUidKw;
               this._TuxedoUidKw = (String)v;
               this._postSet(30, oldVal, this._TuxedoUidKw);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AccessPoint")) {
         return this._AccessPoint;
      } else if (name.equals("AccessPointId")) {
         return this._AccessPointId;
      } else if (name.equals("AclPolicy")) {
         return this._AclPolicy;
      } else if (name.equals("AllowAnonymous")) {
         return new Boolean(this._AllowAnonymous);
      } else if (name.equals("AppKey")) {
         return this._AppKey;
      } else if (name.equals("CmpLimit")) {
         return new Integer(this._CmpLimit);
      } else if (name.equals("ConnPrincipalName")) {
         return this._ConnPrincipalName;
      } else if (name.equals("ConnectionPolicy")) {
         return this._ConnectionPolicy;
      } else if (name.equals("CredentialPolicy")) {
         return this._CredentialPolicy;
      } else if (name.equals("CustomAppKeyClass")) {
         return this._CustomAppKeyClass;
      } else if (name.equals("CustomAppKeyClassParam")) {
         return this._CustomAppKeyClassParam;
      } else if (name.equals("DefaultAppKey")) {
         return new Integer(this._DefaultAppKey);
      } else if (name.equals("FederationName")) {
         return this._FederationName;
      } else if (name.equals("FederationURL")) {
         return this._FederationURL;
      } else if (name.equals("KeepAlive")) {
         return new Integer(this._KeepAlive);
      } else if (name.equals("KeepAliveWait")) {
         return new Integer(this._KeepAliveWait);
      } else if (name.equals("LocalAccessPoint")) {
         return this._LocalAccessPoint;
      } else if (name.equals("MaxEncryptBits")) {
         return this._MaxEncryptBits;
      } else if (name.equals("MaxRetries")) {
         return new Long(this._MaxRetries);
      } else if (name.equals("MinEncryptBits")) {
         return this._MinEncryptBits;
      } else if (name.equals("NWAddr")) {
         return this._NWAddr;
      } else if (name.equals("RetryInterval")) {
         return new Long(this._RetryInterval);
      } else if (name.equals("SSLProtocolVersion")) {
         return this._SSLProtocolVersion;
      } else if (name.equals("TpUsrFile")) {
         return this._TpUsrFile;
      } else if (name.equals("TuxedoGidKw")) {
         return this._TuxedoGidKw;
      } else {
         return name.equals("TuxedoUidKw") ? this._TuxedoUidKw : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("AccessPoint", "myRAP");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AccessPoint in WTCRemoteTuxDomMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("AccessPointId", "myRAPId");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AccessPointId in WTCRemoteTuxDomMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("LocalAccessPoint", "myLAP");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalAccessPoint in WTCRemoteTuxDomMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("app-key")) {
                  return 27;
               }

               if (s.equals("nw-addr")) {
                  return 20;
               }
            case 8:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 9:
               if (s.equals("cmp-limit")) {
                  return 23;
               }
               break;
            case 10:
               if (s.equals("acl-policy")) {
                  return 13;
               }

               if (s.equals("keep-alive")) {
                  return 34;
               }
               break;
            case 11:
               if (s.equals("max-retries")) {
                  return 19;
               }

               if (s.equals("tp-usr-file")) {
                  return 15;
               }
               break;
            case 12:
               if (s.equals("access-point")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("tuxedo-gid-kw")) {
                  return 31;
               }

               if (s.equals("tuxedo-uid-kw")) {
                  return 30;
               }
               break;
            case 14:
               if (s.equals("federation-url")) {
                  return 21;
               }

               if (s.equals("retry-interval")) {
                  return 18;
               }
               break;
            case 15:
               if (s.equals("access-point-id")) {
                  return 11;
               }

               if (s.equals("allow-anonymous")) {
                  return 28;
               }

               if (s.equals("default-app-key")) {
                  return 29;
               }

               if (s.equals("federation-name")) {
                  return 22;
               }

               if (s.equals("keep-alive-wait")) {
                  return 35;
               }
               break;
            case 16:
               if (s.equals("max-encrypt-bits")) {
                  return 25;
               }

               if (s.equals("min-encrypt-bits")) {
                  return 24;
               }
               break;
            case 17:
               if (s.equals("connection-policy")) {
                  return 12;
               }

               if (s.equals("credential-policy")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("local-access-point")) {
                  return 16;
               }
               break;
            case 19:
               if (s.equals("conn-principal-name")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("custom-app-key-class")) {
                  return 32;
               }

               if (s.equals("ssl-protocol-version")) {
                  return 26;
               }
               break;
            case 26:
               if (s.equals("custom-app-key-class-param")) {
                  return 33;
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
               return "connection-policy";
            case 13:
               return "acl-policy";
            case 14:
               return "credential-policy";
            case 15:
               return "tp-usr-file";
            case 16:
               return "local-access-point";
            case 17:
               return "conn-principal-name";
            case 18:
               return "retry-interval";
            case 19:
               return "max-retries";
            case 20:
               return "nw-addr";
            case 21:
               return "federation-url";
            case 22:
               return "federation-name";
            case 23:
               return "cmp-limit";
            case 24:
               return "min-encrypt-bits";
            case 25:
               return "max-encrypt-bits";
            case 26:
               return "ssl-protocol-version";
            case 27:
               return "app-key";
            case 28:
               return "allow-anonymous";
            case 29:
               return "default-app-key";
            case 30:
               return "tuxedo-uid-kw";
            case 31:
               return "tuxedo-gid-kw";
            case 32:
               return "custom-app-key-class";
            case 33:
               return "custom-app-key-class-param";
            case 34:
               return "keep-alive";
            case 35:
               return "keep-alive-wait";
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
      private WTCRemoteTuxDomMBeanImpl bean;

      protected Helper(WTCRemoteTuxDomMBeanImpl bean) {
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
               return "ConnectionPolicy";
            case 13:
               return "AclPolicy";
            case 14:
               return "CredentialPolicy";
            case 15:
               return "TpUsrFile";
            case 16:
               return "LocalAccessPoint";
            case 17:
               return "ConnPrincipalName";
            case 18:
               return "RetryInterval";
            case 19:
               return "MaxRetries";
            case 20:
               return "NWAddr";
            case 21:
               return "FederationURL";
            case 22:
               return "FederationName";
            case 23:
               return "CmpLimit";
            case 24:
               return "MinEncryptBits";
            case 25:
               return "MaxEncryptBits";
            case 26:
               return "SSLProtocolVersion";
            case 27:
               return "AppKey";
            case 28:
               return "AllowAnonymous";
            case 29:
               return "DefaultAppKey";
            case 30:
               return "TuxedoUidKw";
            case 31:
               return "TuxedoGidKw";
            case 32:
               return "CustomAppKeyClass";
            case 33:
               return "CustomAppKeyClassParam";
            case 34:
               return "KeepAlive";
            case 35:
               return "KeepAliveWait";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AccessPoint")) {
            return 10;
         } else if (propName.equals("AccessPointId")) {
            return 11;
         } else if (propName.equals("AclPolicy")) {
            return 13;
         } else if (propName.equals("AllowAnonymous")) {
            return 28;
         } else if (propName.equals("AppKey")) {
            return 27;
         } else if (propName.equals("CmpLimit")) {
            return 23;
         } else if (propName.equals("ConnPrincipalName")) {
            return 17;
         } else if (propName.equals("ConnectionPolicy")) {
            return 12;
         } else if (propName.equals("CredentialPolicy")) {
            return 14;
         } else if (propName.equals("CustomAppKeyClass")) {
            return 32;
         } else if (propName.equals("CustomAppKeyClassParam")) {
            return 33;
         } else if (propName.equals("DefaultAppKey")) {
            return 29;
         } else if (propName.equals("FederationName")) {
            return 22;
         } else if (propName.equals("FederationURL")) {
            return 21;
         } else if (propName.equals("KeepAlive")) {
            return 34;
         } else if (propName.equals("KeepAliveWait")) {
            return 35;
         } else if (propName.equals("LocalAccessPoint")) {
            return 16;
         } else if (propName.equals("MaxEncryptBits")) {
            return 25;
         } else if (propName.equals("MaxRetries")) {
            return 19;
         } else if (propName.equals("MinEncryptBits")) {
            return 24;
         } else if (propName.equals("NWAddr")) {
            return 20;
         } else if (propName.equals("RetryInterval")) {
            return 18;
         } else if (propName.equals("SSLProtocolVersion")) {
            return 26;
         } else if (propName.equals("TpUsrFile")) {
            return 15;
         } else if (propName.equals("TuxedoGidKw")) {
            return 31;
         } else {
            return propName.equals("TuxedoUidKw") ? 30 : super.getPropertyIndex(propName);
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

            if (this.bean.isAclPolicySet()) {
               buf.append("AclPolicy");
               buf.append(String.valueOf(this.bean.getAclPolicy()));
            }

            if (this.bean.isAllowAnonymousSet()) {
               buf.append("AllowAnonymous");
               buf.append(String.valueOf(this.bean.getAllowAnonymous()));
            }

            if (this.bean.isAppKeySet()) {
               buf.append("AppKey");
               buf.append(String.valueOf(this.bean.getAppKey()));
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

            if (this.bean.isCredentialPolicySet()) {
               buf.append("CredentialPolicy");
               buf.append(String.valueOf(this.bean.getCredentialPolicy()));
            }

            if (this.bean.isCustomAppKeyClassSet()) {
               buf.append("CustomAppKeyClass");
               buf.append(String.valueOf(this.bean.getCustomAppKeyClass()));
            }

            if (this.bean.isCustomAppKeyClassParamSet()) {
               buf.append("CustomAppKeyClassParam");
               buf.append(String.valueOf(this.bean.getCustomAppKeyClassParam()));
            }

            if (this.bean.isDefaultAppKeySet()) {
               buf.append("DefaultAppKey");
               buf.append(String.valueOf(this.bean.getDefaultAppKey()));
            }

            if (this.bean.isFederationNameSet()) {
               buf.append("FederationName");
               buf.append(String.valueOf(this.bean.getFederationName()));
            }

            if (this.bean.isFederationURLSet()) {
               buf.append("FederationURL");
               buf.append(String.valueOf(this.bean.getFederationURL()));
            }

            if (this.bean.isKeepAliveSet()) {
               buf.append("KeepAlive");
               buf.append(String.valueOf(this.bean.getKeepAlive()));
            }

            if (this.bean.isKeepAliveWaitSet()) {
               buf.append("KeepAliveWait");
               buf.append(String.valueOf(this.bean.getKeepAliveWait()));
            }

            if (this.bean.isLocalAccessPointSet()) {
               buf.append("LocalAccessPoint");
               buf.append(String.valueOf(this.bean.getLocalAccessPoint()));
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

            if (this.bean.isRetryIntervalSet()) {
               buf.append("RetryInterval");
               buf.append(String.valueOf(this.bean.getRetryInterval()));
            }

            if (this.bean.isSSLProtocolVersionSet()) {
               buf.append("SSLProtocolVersion");
               buf.append(String.valueOf(this.bean.getSSLProtocolVersion()));
            }

            if (this.bean.isTpUsrFileSet()) {
               buf.append("TpUsrFile");
               buf.append(String.valueOf(this.bean.getTpUsrFile()));
            }

            if (this.bean.isTuxedoGidKwSet()) {
               buf.append("TuxedoGidKw");
               buf.append(String.valueOf(this.bean.getTuxedoGidKw()));
            }

            if (this.bean.isTuxedoUidKwSet()) {
               buf.append("TuxedoUidKw");
               buf.append(String.valueOf(this.bean.getTuxedoUidKw()));
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
            WTCRemoteTuxDomMBeanImpl otherTyped = (WTCRemoteTuxDomMBeanImpl)other;
            this.computeDiff("AccessPoint", this.bean.getAccessPoint(), otherTyped.getAccessPoint(), true);
            this.computeDiff("AccessPointId", this.bean.getAccessPointId(), otherTyped.getAccessPointId(), true);
            this.computeDiff("AclPolicy", this.bean.getAclPolicy(), otherTyped.getAclPolicy(), true);
            this.computeDiff("AllowAnonymous", this.bean.getAllowAnonymous(), otherTyped.getAllowAnonymous(), true);
            this.computeDiff("AppKey", this.bean.getAppKey(), otherTyped.getAppKey(), true);
            this.computeDiff("CmpLimit", this.bean.getCmpLimit(), otherTyped.getCmpLimit(), true);
            this.computeDiff("ConnPrincipalName", this.bean.getConnPrincipalName(), otherTyped.getConnPrincipalName(), true);
            this.computeDiff("ConnectionPolicy", this.bean.getConnectionPolicy(), otherTyped.getConnectionPolicy(), true);
            this.computeDiff("CredentialPolicy", this.bean.getCredentialPolicy(), otherTyped.getCredentialPolicy(), true);
            this.computeDiff("CustomAppKeyClass", this.bean.getCustomAppKeyClass(), otherTyped.getCustomAppKeyClass(), true);
            this.computeDiff("CustomAppKeyClassParam", this.bean.getCustomAppKeyClassParam(), otherTyped.getCustomAppKeyClassParam(), true);
            this.computeDiff("DefaultAppKey", this.bean.getDefaultAppKey(), otherTyped.getDefaultAppKey(), true);
            this.computeDiff("FederationName", this.bean.getFederationName(), otherTyped.getFederationName(), true);
            this.computeDiff("FederationURL", this.bean.getFederationURL(), otherTyped.getFederationURL(), true);
            this.computeDiff("KeepAlive", this.bean.getKeepAlive(), otherTyped.getKeepAlive(), true);
            this.computeDiff("KeepAliveWait", this.bean.getKeepAliveWait(), otherTyped.getKeepAliveWait(), true);
            this.computeDiff("LocalAccessPoint", this.bean.getLocalAccessPoint(), otherTyped.getLocalAccessPoint(), true);
            this.computeDiff("MaxEncryptBits", this.bean.getMaxEncryptBits(), otherTyped.getMaxEncryptBits(), true);
            this.computeDiff("MaxRetries", this.bean.getMaxRetries(), otherTyped.getMaxRetries(), true);
            this.computeDiff("MinEncryptBits", this.bean.getMinEncryptBits(), otherTyped.getMinEncryptBits(), true);
            this.computeDiff("NWAddr", this.bean.getNWAddr(), otherTyped.getNWAddr(), true);
            this.computeDiff("RetryInterval", this.bean.getRetryInterval(), otherTyped.getRetryInterval(), true);
            this.computeDiff("SSLProtocolVersion", this.bean.getSSLProtocolVersion(), otherTyped.getSSLProtocolVersion(), true);
            this.computeDiff("TpUsrFile", this.bean.getTpUsrFile(), otherTyped.getTpUsrFile(), true);
            this.computeDiff("TuxedoGidKw", this.bean.getTuxedoGidKw(), otherTyped.getTuxedoGidKw(), true);
            this.computeDiff("TuxedoUidKw", this.bean.getTuxedoUidKw(), otherTyped.getTuxedoUidKw(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCRemoteTuxDomMBeanImpl original = (WTCRemoteTuxDomMBeanImpl)event.getSourceBean();
            WTCRemoteTuxDomMBeanImpl proposed = (WTCRemoteTuxDomMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AccessPoint")) {
                  original.setAccessPoint(proposed.getAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("AccessPointId")) {
                  original.setAccessPointId(proposed.getAccessPointId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("AclPolicy")) {
                  original.setAclPolicy(proposed.getAclPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("AllowAnonymous")) {
                  original.setAllowAnonymous(proposed.getAllowAnonymous());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("AppKey")) {
                  original.setAppKey(proposed.getAppKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("CmpLimit")) {
                  original.setCmpLimit(proposed.getCmpLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ConnPrincipalName")) {
                  original.setConnPrincipalName(proposed.getConnPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ConnectionPolicy")) {
                  original.setConnectionPolicy(proposed.getConnectionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CredentialPolicy")) {
                  original.setCredentialPolicy(proposed.getCredentialPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CustomAppKeyClass")) {
                  original.setCustomAppKeyClass(proposed.getCustomAppKeyClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("CustomAppKeyClassParam")) {
                  original.setCustomAppKeyClassParam(proposed.getCustomAppKeyClassParam());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("DefaultAppKey")) {
                  original.setDefaultAppKey(proposed.getDefaultAppKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("FederationName")) {
                  original.setFederationName(proposed.getFederationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("FederationURL")) {
                  original.setFederationURL(proposed.getFederationURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("KeepAlive")) {
                  original.setKeepAlive(proposed.getKeepAlive());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("KeepAliveWait")) {
                  original.setKeepAliveWait(proposed.getKeepAliveWait());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("LocalAccessPoint")) {
                  original.setLocalAccessPoint(proposed.getLocalAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MaxEncryptBits")) {
                  original.setMaxEncryptBits(proposed.getMaxEncryptBits());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("MaxRetries")) {
                  original.setMaxRetries(proposed.getMaxRetries());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("MinEncryptBits")) {
                  original.setMinEncryptBits(proposed.getMinEncryptBits());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("NWAddr")) {
                  original.setNWAddr(proposed.getNWAddr());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("RetryInterval")) {
                  original.setRetryInterval(proposed.getRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("SSLProtocolVersion")) {
                  original.setSSLProtocolVersion(proposed.getSSLProtocolVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("TpUsrFile")) {
                  original.setTpUsrFile(proposed.getTpUsrFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("TuxedoGidKw")) {
                  original.setTuxedoGidKw(proposed.getTuxedoGidKw());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("TuxedoUidKw")) {
                  original.setTuxedoUidKw(proposed.getTuxedoUidKw());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else {
                  super.applyPropertyUpdate(event, update);
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
            WTCRemoteTuxDomMBeanImpl copy = (WTCRemoteTuxDomMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AccessPoint")) && this.bean.isAccessPointSet()) {
               copy.setAccessPoint(this.bean.getAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("AccessPointId")) && this.bean.isAccessPointIdSet()) {
               copy.setAccessPointId(this.bean.getAccessPointId());
            }

            if ((excludeProps == null || !excludeProps.contains("AclPolicy")) && this.bean.isAclPolicySet()) {
               copy.setAclPolicy(this.bean.getAclPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowAnonymous")) && this.bean.isAllowAnonymousSet()) {
               copy.setAllowAnonymous(this.bean.getAllowAnonymous());
            }

            if ((excludeProps == null || !excludeProps.contains("AppKey")) && this.bean.isAppKeySet()) {
               copy.setAppKey(this.bean.getAppKey());
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

            if ((excludeProps == null || !excludeProps.contains("CredentialPolicy")) && this.bean.isCredentialPolicySet()) {
               copy.setCredentialPolicy(this.bean.getCredentialPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomAppKeyClass")) && this.bean.isCustomAppKeyClassSet()) {
               copy.setCustomAppKeyClass(this.bean.getCustomAppKeyClass());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomAppKeyClassParam")) && this.bean.isCustomAppKeyClassParamSet()) {
               copy.setCustomAppKeyClassParam(this.bean.getCustomAppKeyClassParam());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultAppKey")) && this.bean.isDefaultAppKeySet()) {
               copy.setDefaultAppKey(this.bean.getDefaultAppKey());
            }

            if ((excludeProps == null || !excludeProps.contains("FederationName")) && this.bean.isFederationNameSet()) {
               copy.setFederationName(this.bean.getFederationName());
            }

            if ((excludeProps == null || !excludeProps.contains("FederationURL")) && this.bean.isFederationURLSet()) {
               copy.setFederationURL(this.bean.getFederationURL());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAlive")) && this.bean.isKeepAliveSet()) {
               copy.setKeepAlive(this.bean.getKeepAlive());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAliveWait")) && this.bean.isKeepAliveWaitSet()) {
               copy.setKeepAliveWait(this.bean.getKeepAliveWait());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalAccessPoint")) && this.bean.isLocalAccessPointSet()) {
               copy.setLocalAccessPoint(this.bean.getLocalAccessPoint());
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

            if ((excludeProps == null || !excludeProps.contains("RetryInterval")) && this.bean.isRetryIntervalSet()) {
               copy.setRetryInterval(this.bean.getRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLProtocolVersion")) && this.bean.isSSLProtocolVersionSet()) {
               copy.setSSLProtocolVersion(this.bean.getSSLProtocolVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("TpUsrFile")) && this.bean.isTpUsrFileSet()) {
               copy.setTpUsrFile(this.bean.getTpUsrFile());
            }

            if ((excludeProps == null || !excludeProps.contains("TuxedoGidKw")) && this.bean.isTuxedoGidKwSet()) {
               copy.setTuxedoGidKw(this.bean.getTuxedoGidKw());
            }

            if ((excludeProps == null || !excludeProps.contains("TuxedoUidKw")) && this.bean.isTuxedoUidKwSet()) {
               copy.setTuxedoUidKw(this.bean.getTuxedoUidKw());
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
