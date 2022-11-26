package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CertRevocCaMBeanImpl extends ConfigurationMBeanImpl implements CertRevocCaMBean, Serializable {
   private boolean _CheckingDisabled;
   private boolean _CrlDpBackgroundDownloadEnabled;
   private long _CrlDpDownloadTimeout;
   private boolean _CrlDpEnabled;
   private String _CrlDpUrl;
   private String _CrlDpUrlUsage;
   private String _DistinguishedName;
   private boolean _FailOnUnknownRevocStatus;
   private String _MethodOrder;
   private boolean _OcspNonceEnabled;
   private String _OcspResponderCertIssuerName;
   private String _OcspResponderCertSerialNumber;
   private String _OcspResponderCertSubjectName;
   private String _OcspResponderExplicitTrustMethod;
   private String _OcspResponderUrl;
   private String _OcspResponderUrlUsage;
   private boolean _OcspResponseCacheEnabled;
   private long _OcspResponseTimeout;
   private int _OcspTimeTolerance;
   private static SchemaHelper2 _schemaHelper;

   public CertRevocCaMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CertRevocCaMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CertRevocCaMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDistinguishedName() {
      return this._DistinguishedName;
   }

   public boolean isDistinguishedNameInherited() {
      return false;
   }

   public boolean isDistinguishedNameSet() {
      return this._isSet(10);
   }

   public void setDistinguishedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateX500PrincipalDN(param0);
      String _oldVal = this._DistinguishedName;
      this._DistinguishedName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isCheckingDisabled() {
      return this._CheckingDisabled;
   }

   public boolean isCheckingDisabledInherited() {
      return false;
   }

   public boolean isCheckingDisabledSet() {
      return this._isSet(11);
   }

   public void setCheckingDisabled(boolean param0) {
      boolean _oldVal = this._CheckingDisabled;
      this._CheckingDisabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isFailOnUnknownRevocStatus() {
      if (!this._isSet(12)) {
         try {
            return ((CertRevocMBean)this.getParent()).isFailOnUnknownRevocStatus();
         } catch (NullPointerException var2) {
         }
      }

      return this._FailOnUnknownRevocStatus;
   }

   public boolean isFailOnUnknownRevocStatusInherited() {
      return false;
   }

   public boolean isFailOnUnknownRevocStatusSet() {
      return this._isSet(12);
   }

   public void setFailOnUnknownRevocStatus(boolean param0) {
      boolean _oldVal = this._FailOnUnknownRevocStatus;
      this._FailOnUnknownRevocStatus = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getMethodOrder() {
      if (!this._isSet(13)) {
         try {
            return ((CertRevocMBean)this.getParent()).getMethodOrder();
         } catch (NullPointerException var2) {
         }
      }

      return this._MethodOrder;
   }

   public boolean isMethodOrderInherited() {
      return false;
   }

   public boolean isMethodOrderSet() {
      return this._isSet(13);
   }

   public void setMethodOrder(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocMBean.METHOD_OCSP, CertRevocMBean.METHOD_CRL, CertRevocMBean.METHOD_OCSP_THEN_CRL, CertRevocMBean.METHOD_CRL_THEN_OCSP};
      param0 = LegalChecks.checkInEnum("MethodOrder", param0, _set);
      String _oldVal = this._MethodOrder;
      this._MethodOrder = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getOcspResponderUrl() {
      return this._OcspResponderUrl;
   }

   public boolean isOcspResponderUrlInherited() {
      return false;
   }

   public boolean isOcspResponderUrlSet() {
      return this._isSet(14);
   }

   public void setOcspResponderUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateURL(param0);
      String _oldVal = this._OcspResponderUrl;
      this._OcspResponderUrl = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getOcspResponderUrlUsage() {
      return this._OcspResponderUrlUsage;
   }

   public boolean isOcspResponderUrlUsageInherited() {
      return false;
   }

   public boolean isOcspResponderUrlUsageSet() {
      return this._isSet(15);
   }

   public void setOcspResponderUrlUsage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE};
      param0 = LegalChecks.checkInEnum("OcspResponderUrlUsage", param0, _set);
      String _oldVal = this._OcspResponderUrlUsage;
      this._OcspResponderUrlUsage = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getOcspResponderExplicitTrustMethod() {
      return this._OcspResponderExplicitTrustMethod;
   }

   public boolean isOcspResponderExplicitTrustMethodInherited() {
      return false;
   }

   public boolean isOcspResponderExplicitTrustMethodSet() {
      return this._isSet(16);
   }

   public void setOcspResponderExplicitTrustMethod(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_NONE, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_SUBJECT, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_ISSUER_SERIAL_NUMBER};
      param0 = LegalChecks.checkInEnum("OcspResponderExplicitTrustMethod", param0, _set);
      String _oldVal = this._OcspResponderExplicitTrustMethod;
      this._OcspResponderExplicitTrustMethod = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getOcspResponderCertSubjectName() {
      return this._OcspResponderCertSubjectName;
   }

   public boolean isOcspResponderCertSubjectNameInherited() {
      return false;
   }

   public boolean isOcspResponderCertSubjectNameSet() {
      return this._isSet(17);
   }

   public void setOcspResponderCertSubjectName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateX500PrincipalDN(param0);
      String _oldVal = this._OcspResponderCertSubjectName;
      this._OcspResponderCertSubjectName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getOcspResponderCertIssuerName() {
      return this._OcspResponderCertIssuerName;
   }

   public boolean isOcspResponderCertIssuerNameInherited() {
      return false;
   }

   public boolean isOcspResponderCertIssuerNameSet() {
      return this._isSet(18);
   }

   public void setOcspResponderCertIssuerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateX500PrincipalDN(param0);
      String _oldVal = this._OcspResponderCertIssuerName;
      this._OcspResponderCertIssuerName = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getOcspResponderCertSerialNumber() {
      return this._OcspResponderCertSerialNumber;
   }

   public boolean isOcspResponderCertSerialNumberInherited() {
      return false;
   }

   public boolean isOcspResponderCertSerialNumberSet() {
      return this._isSet(19);
   }

   public void setOcspResponderCertSerialNumber(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateCertSerialNumber(param0);
      String _oldVal = this._OcspResponderCertSerialNumber;
      this._OcspResponderCertSerialNumber = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isOcspNonceEnabled() {
      if (!this._isSet(20)) {
         try {
            return ((CertRevocMBean)this.getParent()).isOcspNonceEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._OcspNonceEnabled;
   }

   public boolean isOcspNonceEnabledInherited() {
      return false;
   }

   public boolean isOcspNonceEnabledSet() {
      return this._isSet(20);
   }

   public void setOcspNonceEnabled(boolean param0) {
      boolean _oldVal = this._OcspNonceEnabled;
      this._OcspNonceEnabled = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isOcspResponseCacheEnabled() {
      if (!this._isSet(21)) {
         try {
            return ((CertRevocMBean)this.getParent()).isOcspResponseCacheEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._OcspResponseCacheEnabled;
   }

   public boolean isOcspResponseCacheEnabledInherited() {
      return false;
   }

   public boolean isOcspResponseCacheEnabledSet() {
      return this._isSet(21);
   }

   public void setOcspResponseCacheEnabled(boolean param0) {
      boolean _oldVal = this._OcspResponseCacheEnabled;
      this._OcspResponseCacheEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public long getOcspResponseTimeout() {
      if (!this._isSet(22)) {
         try {
            return ((CertRevocMBean)this.getParent()).getOcspResponseTimeout();
         } catch (NullPointerException var2) {
         }
      }

      return this._OcspResponseTimeout;
   }

   public boolean isOcspResponseTimeoutInherited() {
      return false;
   }

   public boolean isOcspResponseTimeoutSet() {
      return this._isSet(22);
   }

   public void setOcspResponseTimeout(long param0) {
      LegalChecks.checkInRange("OcspResponseTimeout", param0, 1L, 300L);
      long _oldVal = this._OcspResponseTimeout;
      this._OcspResponseTimeout = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getOcspTimeTolerance() {
      if (!this._isSet(23)) {
         try {
            return ((CertRevocMBean)this.getParent()).getOcspTimeTolerance();
         } catch (NullPointerException var2) {
         }
      }

      return this._OcspTimeTolerance;
   }

   public boolean isOcspTimeToleranceInherited() {
      return false;
   }

   public boolean isOcspTimeToleranceSet() {
      return this._isSet(23);
   }

   public void setOcspTimeTolerance(int param0) {
      LegalChecks.checkInRange("OcspTimeTolerance", (long)param0, 0L, 900L);
      int _oldVal = this._OcspTimeTolerance;
      this._OcspTimeTolerance = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isCrlDpEnabled() {
      if (!this._isSet(24)) {
         try {
            return ((CertRevocMBean)this.getParent()).isCrlDpEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._CrlDpEnabled;
   }

   public boolean isCrlDpEnabledInherited() {
      return false;
   }

   public boolean isCrlDpEnabledSet() {
      return this._isSet(24);
   }

   public void setCrlDpEnabled(boolean param0) {
      boolean _oldVal = this._CrlDpEnabled;
      this._CrlDpEnabled = param0;
      this._postSet(24, _oldVal, param0);
   }

   public long getCrlDpDownloadTimeout() {
      if (!this._isSet(25)) {
         try {
            return ((CertRevocMBean)this.getParent()).getCrlDpDownloadTimeout();
         } catch (NullPointerException var2) {
         }
      }

      return this._CrlDpDownloadTimeout;
   }

   public boolean isCrlDpDownloadTimeoutInherited() {
      return false;
   }

   public boolean isCrlDpDownloadTimeoutSet() {
      return this._isSet(25);
   }

   public void setCrlDpDownloadTimeout(long param0) {
      LegalChecks.checkInRange("CrlDpDownloadTimeout", param0, 1L, 300L);
      long _oldVal = this._CrlDpDownloadTimeout;
      this._CrlDpDownloadTimeout = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean isCrlDpBackgroundDownloadEnabled() {
      if (!this._isSet(26)) {
         try {
            return ((CertRevocMBean)this.getParent()).isCrlDpBackgroundDownloadEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._CrlDpBackgroundDownloadEnabled;
   }

   public boolean isCrlDpBackgroundDownloadEnabledInherited() {
      return false;
   }

   public boolean isCrlDpBackgroundDownloadEnabledSet() {
      return this._isSet(26);
   }

   public void setCrlDpBackgroundDownloadEnabled(boolean param0) {
      boolean _oldVal = this._CrlDpBackgroundDownloadEnabled;
      this._CrlDpBackgroundDownloadEnabled = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getCrlDpUrl() {
      return this._CrlDpUrl;
   }

   public boolean isCrlDpUrlInherited() {
      return false;
   }

   public boolean isCrlDpUrlSet() {
      return this._isSet(27);
   }

   public void setCrlDpUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      CertRevocValidator.validateURL(param0);
      String _oldVal = this._CrlDpUrl;
      this._CrlDpUrl = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getCrlDpUrlUsage() {
      return this._CrlDpUrlUsage;
   }

   public boolean isCrlDpUrlUsageInherited() {
      return false;
   }

   public boolean isCrlDpUrlUsageSet() {
      return this._isSet(28);
   }

   public void setCrlDpUrlUsage(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE};
      param0 = LegalChecks.checkInEnum("CrlDpUrlUsage", param0, _set);
      String _oldVal = this._CrlDpUrlUsage;
      this._CrlDpUrlUsage = param0;
      this._postSet(28, _oldVal, param0);
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
         idx = 25;
      }

      try {
         switch (idx) {
            case 25:
               this._CrlDpDownloadTimeout = 0L;
               if (initOne) {
                  break;
               }
            case 27:
               this._CrlDpUrl = CertRevocCaMBean.DEFAULT_CRL_DP_URL;
               if (initOne) {
                  break;
               }
            case 28:
               this._CrlDpUrlUsage = CertRevocCaMBean.DEFAULT_CRL_DP_URL_USAGE;
               if (initOne) {
                  break;
               }
            case 10:
               this._DistinguishedName = CertRevocCaMBean.DEFAULT_DISTINGUISHED_NAME;
               if (initOne) {
                  break;
               }
            case 13:
               this._MethodOrder = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._OcspResponderCertIssuerName = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
               if (initOne) {
                  break;
               }
            case 19:
               this._OcspResponderCertSerialNumber = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
               if (initOne) {
                  break;
               }
            case 17:
               this._OcspResponderCertSubjectName = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
               if (initOne) {
                  break;
               }
            case 16:
               this._OcspResponderExplicitTrustMethod = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;
               if (initOne) {
                  break;
               }
            case 14:
               this._OcspResponderUrl = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_URL;
               if (initOne) {
                  break;
               }
            case 15:
               this._OcspResponderUrlUsage = CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_URL_USAGE;
               if (initOne) {
                  break;
               }
            case 22:
               this._OcspResponseTimeout = 0L;
               if (initOne) {
                  break;
               }
            case 23:
               this._OcspTimeTolerance = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._CheckingDisabled = CertRevocCaMBean.DEFAULT_CHECKING_DISABLED;
               if (initOne) {
                  break;
               }
            case 26:
               this._CrlDpBackgroundDownloadEnabled = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._CrlDpEnabled = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._FailOnUnknownRevocStatus = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._OcspNonceEnabled = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._OcspResponseCacheEnabled = false;
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
      return "CertRevocCa";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CheckingDisabled")) {
         oldVal = this._CheckingDisabled;
         this._CheckingDisabled = (Boolean)v;
         this._postSet(11, oldVal, this._CheckingDisabled);
      } else if (name.equals("CrlDpBackgroundDownloadEnabled")) {
         oldVal = this._CrlDpBackgroundDownloadEnabled;
         this._CrlDpBackgroundDownloadEnabled = (Boolean)v;
         this._postSet(26, oldVal, this._CrlDpBackgroundDownloadEnabled);
      } else {
         long oldVal;
         if (name.equals("CrlDpDownloadTimeout")) {
            oldVal = this._CrlDpDownloadTimeout;
            this._CrlDpDownloadTimeout = (Long)v;
            this._postSet(25, oldVal, this._CrlDpDownloadTimeout);
         } else if (name.equals("CrlDpEnabled")) {
            oldVal = this._CrlDpEnabled;
            this._CrlDpEnabled = (Boolean)v;
            this._postSet(24, oldVal, this._CrlDpEnabled);
         } else {
            String oldVal;
            if (name.equals("CrlDpUrl")) {
               oldVal = this._CrlDpUrl;
               this._CrlDpUrl = (String)v;
               this._postSet(27, oldVal, this._CrlDpUrl);
            } else if (name.equals("CrlDpUrlUsage")) {
               oldVal = this._CrlDpUrlUsage;
               this._CrlDpUrlUsage = (String)v;
               this._postSet(28, oldVal, this._CrlDpUrlUsage);
            } else if (name.equals("DistinguishedName")) {
               oldVal = this._DistinguishedName;
               this._DistinguishedName = (String)v;
               this._postSet(10, oldVal, this._DistinguishedName);
            } else if (name.equals("FailOnUnknownRevocStatus")) {
               oldVal = this._FailOnUnknownRevocStatus;
               this._FailOnUnknownRevocStatus = (Boolean)v;
               this._postSet(12, oldVal, this._FailOnUnknownRevocStatus);
            } else if (name.equals("MethodOrder")) {
               oldVal = this._MethodOrder;
               this._MethodOrder = (String)v;
               this._postSet(13, oldVal, this._MethodOrder);
            } else if (name.equals("OcspNonceEnabled")) {
               oldVal = this._OcspNonceEnabled;
               this._OcspNonceEnabled = (Boolean)v;
               this._postSet(20, oldVal, this._OcspNonceEnabled);
            } else if (name.equals("OcspResponderCertIssuerName")) {
               oldVal = this._OcspResponderCertIssuerName;
               this._OcspResponderCertIssuerName = (String)v;
               this._postSet(18, oldVal, this._OcspResponderCertIssuerName);
            } else if (name.equals("OcspResponderCertSerialNumber")) {
               oldVal = this._OcspResponderCertSerialNumber;
               this._OcspResponderCertSerialNumber = (String)v;
               this._postSet(19, oldVal, this._OcspResponderCertSerialNumber);
            } else if (name.equals("OcspResponderCertSubjectName")) {
               oldVal = this._OcspResponderCertSubjectName;
               this._OcspResponderCertSubjectName = (String)v;
               this._postSet(17, oldVal, this._OcspResponderCertSubjectName);
            } else if (name.equals("OcspResponderExplicitTrustMethod")) {
               oldVal = this._OcspResponderExplicitTrustMethod;
               this._OcspResponderExplicitTrustMethod = (String)v;
               this._postSet(16, oldVal, this._OcspResponderExplicitTrustMethod);
            } else if (name.equals("OcspResponderUrl")) {
               oldVal = this._OcspResponderUrl;
               this._OcspResponderUrl = (String)v;
               this._postSet(14, oldVal, this._OcspResponderUrl);
            } else if (name.equals("OcspResponderUrlUsage")) {
               oldVal = this._OcspResponderUrlUsage;
               this._OcspResponderUrlUsage = (String)v;
               this._postSet(15, oldVal, this._OcspResponderUrlUsage);
            } else if (name.equals("OcspResponseCacheEnabled")) {
               oldVal = this._OcspResponseCacheEnabled;
               this._OcspResponseCacheEnabled = (Boolean)v;
               this._postSet(21, oldVal, this._OcspResponseCacheEnabled);
            } else if (name.equals("OcspResponseTimeout")) {
               oldVal = this._OcspResponseTimeout;
               this._OcspResponseTimeout = (Long)v;
               this._postSet(22, oldVal, this._OcspResponseTimeout);
            } else if (name.equals("OcspTimeTolerance")) {
               int oldVal = this._OcspTimeTolerance;
               this._OcspTimeTolerance = (Integer)v;
               this._postSet(23, oldVal, this._OcspTimeTolerance);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CheckingDisabled")) {
         return new Boolean(this._CheckingDisabled);
      } else if (name.equals("CrlDpBackgroundDownloadEnabled")) {
         return new Boolean(this._CrlDpBackgroundDownloadEnabled);
      } else if (name.equals("CrlDpDownloadTimeout")) {
         return new Long(this._CrlDpDownloadTimeout);
      } else if (name.equals("CrlDpEnabled")) {
         return new Boolean(this._CrlDpEnabled);
      } else if (name.equals("CrlDpUrl")) {
         return this._CrlDpUrl;
      } else if (name.equals("CrlDpUrlUsage")) {
         return this._CrlDpUrlUsage;
      } else if (name.equals("DistinguishedName")) {
         return this._DistinguishedName;
      } else if (name.equals("FailOnUnknownRevocStatus")) {
         return new Boolean(this._FailOnUnknownRevocStatus);
      } else if (name.equals("MethodOrder")) {
         return this._MethodOrder;
      } else if (name.equals("OcspNonceEnabled")) {
         return new Boolean(this._OcspNonceEnabled);
      } else if (name.equals("OcspResponderCertIssuerName")) {
         return this._OcspResponderCertIssuerName;
      } else if (name.equals("OcspResponderCertSerialNumber")) {
         return this._OcspResponderCertSerialNumber;
      } else if (name.equals("OcspResponderCertSubjectName")) {
         return this._OcspResponderCertSubjectName;
      } else if (name.equals("OcspResponderExplicitTrustMethod")) {
         return this._OcspResponderExplicitTrustMethod;
      } else if (name.equals("OcspResponderUrl")) {
         return this._OcspResponderUrl;
      } else if (name.equals("OcspResponderUrlUsage")) {
         return this._OcspResponderUrlUsage;
      } else if (name.equals("OcspResponseCacheEnabled")) {
         return new Boolean(this._OcspResponseCacheEnabled);
      } else if (name.equals("OcspResponseTimeout")) {
         return new Long(this._OcspResponseTimeout);
      } else {
         return name.equals("OcspTimeTolerance") ? new Integer(this._OcspTimeTolerance) : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      String[] _set;
      try {
         _set = new String[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE};
         LegalChecks.checkInEnum("CrlDpUrlUsage", CertRevocCaMBean.DEFAULT_CRL_DP_URL_USAGE, _set);
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property CrlDpUrlUsage in CertRevocCaMBean" + var3.getMessage());
      }

      try {
         _set = new String[]{CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_NONE, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_SUBJECT, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_ISSUER_SERIAL_NUMBER};
         LegalChecks.checkInEnum("OcspResponderExplicitTrustMethod", CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD, _set);
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property OcspResponderExplicitTrustMethod in CertRevocCaMBean" + var2.getMessage());
      }

      try {
         _set = new String[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE};
         LegalChecks.checkInEnum("OcspResponderUrlUsage", CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_URL_USAGE, _set);
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property OcspResponderUrlUsage in CertRevocCaMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("crl-dp-url")) {
                  return 27;
               }
            case 11:
            case 13:
            case 15:
            case 20:
            case 22:
            case 25:
            case 26:
            case 29:
            case 30:
            case 35:
            default:
               break;
            case 12:
               if (s.equals("method-order")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("crl-dp-enabled")) {
                  return 24;
               }
               break;
            case 16:
               if (s.equals("crl-dp-url-usage")) {
                  return 28;
               }
               break;
            case 17:
               if (s.equals("checking-disabled")) {
                  return 11;
               }
               break;
            case 18:
               if (s.equals("distinguished-name")) {
                  return 10;
               }

               if (s.equals("ocsp-responder-url")) {
                  return 14;
               }

               if (s.equals("ocsp-nonce-enabled")) {
                  return 20;
               }
               break;
            case 19:
               if (s.equals("ocsp-time-tolerance")) {
                  return 23;
               }
               break;
            case 21:
               if (s.equals("ocsp-response-timeout")) {
                  return 22;
               }
               break;
            case 23:
               if (s.equals("crl-dp-download-timeout")) {
                  return 25;
               }
               break;
            case 24:
               if (s.equals("ocsp-responder-url-usage")) {
                  return 15;
               }
               break;
            case 27:
               if (s.equals("ocsp-response-cache-enabled")) {
                  return 21;
               }
               break;
            case 28:
               if (s.equals("fail-on-unknown-revoc-status")) {
                  return 12;
               }
               break;
            case 31:
               if (s.equals("ocsp-responder-cert-issuer-name")) {
                  return 18;
               }
               break;
            case 32:
               if (s.equals("ocsp-responder-cert-subject-name")) {
                  return 17;
               }
               break;
            case 33:
               if (s.equals("ocsp-responder-cert-serial-number")) {
                  return 19;
               }
               break;
            case 34:
               if (s.equals("crl-dp-background-download-enabled")) {
                  return 26;
               }
               break;
            case 36:
               if (s.equals("ocsp-responder-explicit-trust-method")) {
                  return 16;
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
               return "distinguished-name";
            case 11:
               return "checking-disabled";
            case 12:
               return "fail-on-unknown-revoc-status";
            case 13:
               return "method-order";
            case 14:
               return "ocsp-responder-url";
            case 15:
               return "ocsp-responder-url-usage";
            case 16:
               return "ocsp-responder-explicit-trust-method";
            case 17:
               return "ocsp-responder-cert-subject-name";
            case 18:
               return "ocsp-responder-cert-issuer-name";
            case 19:
               return "ocsp-responder-cert-serial-number";
            case 20:
               return "ocsp-nonce-enabled";
            case 21:
               return "ocsp-response-cache-enabled";
            case 22:
               return "ocsp-response-timeout";
            case 23:
               return "ocsp-time-tolerance";
            case 24:
               return "crl-dp-enabled";
            case 25:
               return "crl-dp-download-timeout";
            case 26:
               return "crl-dp-background-download-enabled";
            case 27:
               return "crl-dp-url";
            case 28:
               return "crl-dp-url-usage";
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
      private CertRevocCaMBeanImpl bean;

      protected Helper(CertRevocCaMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "DistinguishedName";
            case 11:
               return "CheckingDisabled";
            case 12:
               return "FailOnUnknownRevocStatus";
            case 13:
               return "MethodOrder";
            case 14:
               return "OcspResponderUrl";
            case 15:
               return "OcspResponderUrlUsage";
            case 16:
               return "OcspResponderExplicitTrustMethod";
            case 17:
               return "OcspResponderCertSubjectName";
            case 18:
               return "OcspResponderCertIssuerName";
            case 19:
               return "OcspResponderCertSerialNumber";
            case 20:
               return "OcspNonceEnabled";
            case 21:
               return "OcspResponseCacheEnabled";
            case 22:
               return "OcspResponseTimeout";
            case 23:
               return "OcspTimeTolerance";
            case 24:
               return "CrlDpEnabled";
            case 25:
               return "CrlDpDownloadTimeout";
            case 26:
               return "CrlDpBackgroundDownloadEnabled";
            case 27:
               return "CrlDpUrl";
            case 28:
               return "CrlDpUrlUsage";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CrlDpDownloadTimeout")) {
            return 25;
         } else if (propName.equals("CrlDpUrl")) {
            return 27;
         } else if (propName.equals("CrlDpUrlUsage")) {
            return 28;
         } else if (propName.equals("DistinguishedName")) {
            return 10;
         } else if (propName.equals("MethodOrder")) {
            return 13;
         } else if (propName.equals("OcspResponderCertIssuerName")) {
            return 18;
         } else if (propName.equals("OcspResponderCertSerialNumber")) {
            return 19;
         } else if (propName.equals("OcspResponderCertSubjectName")) {
            return 17;
         } else if (propName.equals("OcspResponderExplicitTrustMethod")) {
            return 16;
         } else if (propName.equals("OcspResponderUrl")) {
            return 14;
         } else if (propName.equals("OcspResponderUrlUsage")) {
            return 15;
         } else if (propName.equals("OcspResponseTimeout")) {
            return 22;
         } else if (propName.equals("OcspTimeTolerance")) {
            return 23;
         } else if (propName.equals("CheckingDisabled")) {
            return 11;
         } else if (propName.equals("CrlDpBackgroundDownloadEnabled")) {
            return 26;
         } else if (propName.equals("CrlDpEnabled")) {
            return 24;
         } else if (propName.equals("FailOnUnknownRevocStatus")) {
            return 12;
         } else if (propName.equals("OcspNonceEnabled")) {
            return 20;
         } else {
            return propName.equals("OcspResponseCacheEnabled") ? 21 : super.getPropertyIndex(propName);
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
            if (this.bean.isCrlDpDownloadTimeoutSet()) {
               buf.append("CrlDpDownloadTimeout");
               buf.append(String.valueOf(this.bean.getCrlDpDownloadTimeout()));
            }

            if (this.bean.isCrlDpUrlSet()) {
               buf.append("CrlDpUrl");
               buf.append(String.valueOf(this.bean.getCrlDpUrl()));
            }

            if (this.bean.isCrlDpUrlUsageSet()) {
               buf.append("CrlDpUrlUsage");
               buf.append(String.valueOf(this.bean.getCrlDpUrlUsage()));
            }

            if (this.bean.isDistinguishedNameSet()) {
               buf.append("DistinguishedName");
               buf.append(String.valueOf(this.bean.getDistinguishedName()));
            }

            if (this.bean.isMethodOrderSet()) {
               buf.append("MethodOrder");
               buf.append(String.valueOf(this.bean.getMethodOrder()));
            }

            if (this.bean.isOcspResponderCertIssuerNameSet()) {
               buf.append("OcspResponderCertIssuerName");
               buf.append(String.valueOf(this.bean.getOcspResponderCertIssuerName()));
            }

            if (this.bean.isOcspResponderCertSerialNumberSet()) {
               buf.append("OcspResponderCertSerialNumber");
               buf.append(String.valueOf(this.bean.getOcspResponderCertSerialNumber()));
            }

            if (this.bean.isOcspResponderCertSubjectNameSet()) {
               buf.append("OcspResponderCertSubjectName");
               buf.append(String.valueOf(this.bean.getOcspResponderCertSubjectName()));
            }

            if (this.bean.isOcspResponderExplicitTrustMethodSet()) {
               buf.append("OcspResponderExplicitTrustMethod");
               buf.append(String.valueOf(this.bean.getOcspResponderExplicitTrustMethod()));
            }

            if (this.bean.isOcspResponderUrlSet()) {
               buf.append("OcspResponderUrl");
               buf.append(String.valueOf(this.bean.getOcspResponderUrl()));
            }

            if (this.bean.isOcspResponderUrlUsageSet()) {
               buf.append("OcspResponderUrlUsage");
               buf.append(String.valueOf(this.bean.getOcspResponderUrlUsage()));
            }

            if (this.bean.isOcspResponseTimeoutSet()) {
               buf.append("OcspResponseTimeout");
               buf.append(String.valueOf(this.bean.getOcspResponseTimeout()));
            }

            if (this.bean.isOcspTimeToleranceSet()) {
               buf.append("OcspTimeTolerance");
               buf.append(String.valueOf(this.bean.getOcspTimeTolerance()));
            }

            if (this.bean.isCheckingDisabledSet()) {
               buf.append("CheckingDisabled");
               buf.append(String.valueOf(this.bean.isCheckingDisabled()));
            }

            if (this.bean.isCrlDpBackgroundDownloadEnabledSet()) {
               buf.append("CrlDpBackgroundDownloadEnabled");
               buf.append(String.valueOf(this.bean.isCrlDpBackgroundDownloadEnabled()));
            }

            if (this.bean.isCrlDpEnabledSet()) {
               buf.append("CrlDpEnabled");
               buf.append(String.valueOf(this.bean.isCrlDpEnabled()));
            }

            if (this.bean.isFailOnUnknownRevocStatusSet()) {
               buf.append("FailOnUnknownRevocStatus");
               buf.append(String.valueOf(this.bean.isFailOnUnknownRevocStatus()));
            }

            if (this.bean.isOcspNonceEnabledSet()) {
               buf.append("OcspNonceEnabled");
               buf.append(String.valueOf(this.bean.isOcspNonceEnabled()));
            }

            if (this.bean.isOcspResponseCacheEnabledSet()) {
               buf.append("OcspResponseCacheEnabled");
               buf.append(String.valueOf(this.bean.isOcspResponseCacheEnabled()));
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
            CertRevocCaMBeanImpl otherTyped = (CertRevocCaMBeanImpl)other;
            this.computeDiff("CrlDpDownloadTimeout", this.bean.getCrlDpDownloadTimeout(), otherTyped.getCrlDpDownloadTimeout(), true);
            this.computeDiff("CrlDpUrl", this.bean.getCrlDpUrl(), otherTyped.getCrlDpUrl(), true);
            this.computeDiff("CrlDpUrlUsage", this.bean.getCrlDpUrlUsage(), otherTyped.getCrlDpUrlUsage(), true);
            this.computeDiff("DistinguishedName", this.bean.getDistinguishedName(), otherTyped.getDistinguishedName(), true);
            this.computeDiff("MethodOrder", this.bean.getMethodOrder(), otherTyped.getMethodOrder(), true);
            this.computeDiff("OcspResponderCertIssuerName", this.bean.getOcspResponderCertIssuerName(), otherTyped.getOcspResponderCertIssuerName(), true);
            this.computeDiff("OcspResponderCertSerialNumber", this.bean.getOcspResponderCertSerialNumber(), otherTyped.getOcspResponderCertSerialNumber(), true);
            this.computeDiff("OcspResponderCertSubjectName", this.bean.getOcspResponderCertSubjectName(), otherTyped.getOcspResponderCertSubjectName(), true);
            this.computeDiff("OcspResponderExplicitTrustMethod", this.bean.getOcspResponderExplicitTrustMethod(), otherTyped.getOcspResponderExplicitTrustMethod(), true);
            this.computeDiff("OcspResponderUrl", this.bean.getOcspResponderUrl(), otherTyped.getOcspResponderUrl(), true);
            this.computeDiff("OcspResponderUrlUsage", this.bean.getOcspResponderUrlUsage(), otherTyped.getOcspResponderUrlUsage(), true);
            this.computeDiff("OcspResponseTimeout", this.bean.getOcspResponseTimeout(), otherTyped.getOcspResponseTimeout(), true);
            this.computeDiff("OcspTimeTolerance", this.bean.getOcspTimeTolerance(), otherTyped.getOcspTimeTolerance(), true);
            this.computeDiff("CheckingDisabled", this.bean.isCheckingDisabled(), otherTyped.isCheckingDisabled(), true);
            this.computeDiff("CrlDpBackgroundDownloadEnabled", this.bean.isCrlDpBackgroundDownloadEnabled(), otherTyped.isCrlDpBackgroundDownloadEnabled(), true);
            this.computeDiff("CrlDpEnabled", this.bean.isCrlDpEnabled(), otherTyped.isCrlDpEnabled(), true);
            this.computeDiff("FailOnUnknownRevocStatus", this.bean.isFailOnUnknownRevocStatus(), otherTyped.isFailOnUnknownRevocStatus(), true);
            this.computeDiff("OcspNonceEnabled", this.bean.isOcspNonceEnabled(), otherTyped.isOcspNonceEnabled(), true);
            this.computeDiff("OcspResponseCacheEnabled", this.bean.isOcspResponseCacheEnabled(), otherTyped.isOcspResponseCacheEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CertRevocCaMBeanImpl original = (CertRevocCaMBeanImpl)event.getSourceBean();
            CertRevocCaMBeanImpl proposed = (CertRevocCaMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CrlDpDownloadTimeout")) {
                  original.setCrlDpDownloadTimeout(proposed.getCrlDpDownloadTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("CrlDpUrl")) {
                  original.setCrlDpUrl(proposed.getCrlDpUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("CrlDpUrlUsage")) {
                  original.setCrlDpUrlUsage(proposed.getCrlDpUrlUsage());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("DistinguishedName")) {
                  original.setDistinguishedName(proposed.getDistinguishedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MethodOrder")) {
                  original.setMethodOrder(proposed.getMethodOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("OcspResponderCertIssuerName")) {
                  original.setOcspResponderCertIssuerName(proposed.getOcspResponderCertIssuerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("OcspResponderCertSerialNumber")) {
                  original.setOcspResponderCertSerialNumber(proposed.getOcspResponderCertSerialNumber());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("OcspResponderCertSubjectName")) {
                  original.setOcspResponderCertSubjectName(proposed.getOcspResponderCertSubjectName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("OcspResponderExplicitTrustMethod")) {
                  original.setOcspResponderExplicitTrustMethod(proposed.getOcspResponderExplicitTrustMethod());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("OcspResponderUrl")) {
                  original.setOcspResponderUrl(proposed.getOcspResponderUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("OcspResponderUrlUsage")) {
                  original.setOcspResponderUrlUsage(proposed.getOcspResponderUrlUsage());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("OcspResponseTimeout")) {
                  original.setOcspResponseTimeout(proposed.getOcspResponseTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("OcspTimeTolerance")) {
                  original.setOcspTimeTolerance(proposed.getOcspTimeTolerance());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("CheckingDisabled")) {
                  original.setCheckingDisabled(proposed.isCheckingDisabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("CrlDpBackgroundDownloadEnabled")) {
                  original.setCrlDpBackgroundDownloadEnabled(proposed.isCrlDpBackgroundDownloadEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("CrlDpEnabled")) {
                  original.setCrlDpEnabled(proposed.isCrlDpEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("FailOnUnknownRevocStatus")) {
                  original.setFailOnUnknownRevocStatus(proposed.isFailOnUnknownRevocStatus());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("OcspNonceEnabled")) {
                  original.setOcspNonceEnabled(proposed.isOcspNonceEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("OcspResponseCacheEnabled")) {
                  original.setOcspResponseCacheEnabled(proposed.isOcspResponseCacheEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
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
            CertRevocCaMBeanImpl copy = (CertRevocCaMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CrlDpDownloadTimeout")) && this.bean.isCrlDpDownloadTimeoutSet()) {
               copy.setCrlDpDownloadTimeout(this.bean.getCrlDpDownloadTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlDpUrl")) && this.bean.isCrlDpUrlSet()) {
               copy.setCrlDpUrl(this.bean.getCrlDpUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlDpUrlUsage")) && this.bean.isCrlDpUrlUsageSet()) {
               copy.setCrlDpUrlUsage(this.bean.getCrlDpUrlUsage());
            }

            if ((excludeProps == null || !excludeProps.contains("DistinguishedName")) && this.bean.isDistinguishedNameSet()) {
               copy.setDistinguishedName(this.bean.getDistinguishedName());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodOrder")) && this.bean.isMethodOrderSet()) {
               copy.setMethodOrder(this.bean.getMethodOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderCertIssuerName")) && this.bean.isOcspResponderCertIssuerNameSet()) {
               copy.setOcspResponderCertIssuerName(this.bean.getOcspResponderCertIssuerName());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderCertSerialNumber")) && this.bean.isOcspResponderCertSerialNumberSet()) {
               copy.setOcspResponderCertSerialNumber(this.bean.getOcspResponderCertSerialNumber());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderCertSubjectName")) && this.bean.isOcspResponderCertSubjectNameSet()) {
               copy.setOcspResponderCertSubjectName(this.bean.getOcspResponderCertSubjectName());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderExplicitTrustMethod")) && this.bean.isOcspResponderExplicitTrustMethodSet()) {
               copy.setOcspResponderExplicitTrustMethod(this.bean.getOcspResponderExplicitTrustMethod());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderUrl")) && this.bean.isOcspResponderUrlSet()) {
               copy.setOcspResponderUrl(this.bean.getOcspResponderUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponderUrlUsage")) && this.bean.isOcspResponderUrlUsageSet()) {
               copy.setOcspResponderUrlUsage(this.bean.getOcspResponderUrlUsage());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponseTimeout")) && this.bean.isOcspResponseTimeoutSet()) {
               copy.setOcspResponseTimeout(this.bean.getOcspResponseTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspTimeTolerance")) && this.bean.isOcspTimeToleranceSet()) {
               copy.setOcspTimeTolerance(this.bean.getOcspTimeTolerance());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckingDisabled")) && this.bean.isCheckingDisabledSet()) {
               copy.setCheckingDisabled(this.bean.isCheckingDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlDpBackgroundDownloadEnabled")) && this.bean.isCrlDpBackgroundDownloadEnabledSet()) {
               copy.setCrlDpBackgroundDownloadEnabled(this.bean.isCrlDpBackgroundDownloadEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlDpEnabled")) && this.bean.isCrlDpEnabledSet()) {
               copy.setCrlDpEnabled(this.bean.isCrlDpEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("FailOnUnknownRevocStatus")) && this.bean.isFailOnUnknownRevocStatusSet()) {
               copy.setFailOnUnknownRevocStatus(this.bean.isFailOnUnknownRevocStatus());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspNonceEnabled")) && this.bean.isOcspNonceEnabledSet()) {
               copy.setOcspNonceEnabled(this.bean.isOcspNonceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponseCacheEnabled")) && this.bean.isOcspResponseCacheEnabledSet()) {
               copy.setOcspResponseCacheEnabled(this.bean.isOcspResponseCacheEnabled());
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
