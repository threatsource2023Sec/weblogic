package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CertRevocMBeanImpl extends ConfigurationMBeanImpl implements CertRevocMBean, Serializable {
   private CertRevocCaMBean[] _CertRevocCas;
   private boolean _CheckingEnabled;
   private int _CrlCacheRefreshPeriodPercent;
   private String _CrlCacheType;
   private String _CrlCacheTypeLdapHostname;
   private int _CrlCacheTypeLdapPort;
   private int _CrlCacheTypeLdapSearchTimeout;
   private boolean _CrlDpBackgroundDownloadEnabled;
   private long _CrlDpDownloadTimeout;
   private boolean _CrlDpEnabled;
   private boolean _FailOnUnknownRevocStatus;
   private String _MethodOrder;
   private boolean _OcspNonceEnabled;
   private int _OcspResponseCacheCapacity;
   private boolean _OcspResponseCacheEnabled;
   private int _OcspResponseCacheRefreshPeriodPercent;
   private long _OcspResponseTimeout;
   private int _OcspTimeTolerance;
   private static SchemaHelper2 _schemaHelper;

   public CertRevocMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CertRevocMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CertRevocMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isCheckingEnabled() {
      return this._CheckingEnabled;
   }

   public boolean isCheckingEnabledInherited() {
      return false;
   }

   public boolean isCheckingEnabledSet() {
      return this._isSet(10);
   }

   public void setCheckingEnabled(boolean param0) {
      boolean _oldVal = this._CheckingEnabled;
      this._CheckingEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isFailOnUnknownRevocStatus() {
      return this._FailOnUnknownRevocStatus;
   }

   public boolean isFailOnUnknownRevocStatusInherited() {
      return false;
   }

   public boolean isFailOnUnknownRevocStatusSet() {
      return this._isSet(11);
   }

   public void setFailOnUnknownRevocStatus(boolean param0) {
      boolean _oldVal = this._FailOnUnknownRevocStatus;
      this._FailOnUnknownRevocStatus = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getMethodOrder() {
      return this._MethodOrder;
   }

   public boolean isMethodOrderInherited() {
      return false;
   }

   public boolean isMethodOrderSet() {
      return this._isSet(12);
   }

   public void setMethodOrder(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocMBean.METHOD_OCSP, CertRevocMBean.METHOD_CRL, CertRevocMBean.METHOD_OCSP_THEN_CRL, CertRevocMBean.METHOD_CRL_THEN_OCSP};
      param0 = LegalChecks.checkInEnum("MethodOrder", param0, _set);
      String _oldVal = this._MethodOrder;
      this._MethodOrder = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isOcspNonceEnabled() {
      return this._OcspNonceEnabled;
   }

   public boolean isOcspNonceEnabledInherited() {
      return false;
   }

   public boolean isOcspNonceEnabledSet() {
      return this._isSet(13);
   }

   public void setOcspNonceEnabled(boolean param0) {
      boolean _oldVal = this._OcspNonceEnabled;
      this._OcspNonceEnabled = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isOcspResponseCacheEnabled() {
      return this._OcspResponseCacheEnabled;
   }

   public boolean isOcspResponseCacheEnabledInherited() {
      return false;
   }

   public boolean isOcspResponseCacheEnabledSet() {
      return this._isSet(14);
   }

   public void setOcspResponseCacheEnabled(boolean param0) {
      boolean _oldVal = this._OcspResponseCacheEnabled;
      this._OcspResponseCacheEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getOcspResponseCacheCapacity() {
      return this._OcspResponseCacheCapacity;
   }

   public boolean isOcspResponseCacheCapacityInherited() {
      return false;
   }

   public boolean isOcspResponseCacheCapacitySet() {
      return this._isSet(15);
   }

   public void setOcspResponseCacheCapacity(int param0) {
      LegalChecks.checkInRange("OcspResponseCacheCapacity", (long)param0, 1L, 2147483647L);
      int _oldVal = this._OcspResponseCacheCapacity;
      this._OcspResponseCacheCapacity = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getOcspResponseCacheRefreshPeriodPercent() {
      return this._OcspResponseCacheRefreshPeriodPercent;
   }

   public boolean isOcspResponseCacheRefreshPeriodPercentInherited() {
      return false;
   }

   public boolean isOcspResponseCacheRefreshPeriodPercentSet() {
      return this._isSet(16);
   }

   public void setOcspResponseCacheRefreshPeriodPercent(int param0) {
      LegalChecks.checkInRange("OcspResponseCacheRefreshPeriodPercent", (long)param0, 1L, 100L);
      int _oldVal = this._OcspResponseCacheRefreshPeriodPercent;
      this._OcspResponseCacheRefreshPeriodPercent = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getOcspResponseTimeout() {
      return this._OcspResponseTimeout;
   }

   public boolean isOcspResponseTimeoutInherited() {
      return false;
   }

   public boolean isOcspResponseTimeoutSet() {
      return this._isSet(17);
   }

   public void setOcspResponseTimeout(long param0) {
      LegalChecks.checkInRange("OcspResponseTimeout", param0, 1L, 300L);
      long _oldVal = this._OcspResponseTimeout;
      this._OcspResponseTimeout = param0;
      this._postSet(17, _oldVal, param0);
   }

   public int getOcspTimeTolerance() {
      return this._OcspTimeTolerance;
   }

   public boolean isOcspTimeToleranceInherited() {
      return false;
   }

   public boolean isOcspTimeToleranceSet() {
      return this._isSet(18);
   }

   public void setOcspTimeTolerance(int param0) {
      LegalChecks.checkInRange("OcspTimeTolerance", (long)param0, 0L, 900L);
      int _oldVal = this._OcspTimeTolerance;
      this._OcspTimeTolerance = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getCrlCacheType() {
      return this._CrlCacheType;
   }

   public boolean isCrlCacheTypeInherited() {
      return false;
   }

   public boolean isCrlCacheTypeSet() {
      return this._isSet(19);
   }

   public void setCrlCacheType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{CertRevocMBean.CRL_CACHE_TYPE_FILE, CertRevocMBean.CRL_CACHE_TYPE_LDAP};
      param0 = LegalChecks.checkInEnum("CrlCacheType", param0, _set);
      String _oldVal = this._CrlCacheType;
      this._CrlCacheType = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getCrlCacheTypeLdapHostname() {
      return this._CrlCacheTypeLdapHostname;
   }

   public boolean isCrlCacheTypeLdapHostnameInherited() {
      return false;
   }

   public boolean isCrlCacheTypeLdapHostnameSet() {
      return this._isSet(20);
   }

   public void setCrlCacheTypeLdapHostname(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CrlCacheTypeLdapHostname;
      this._CrlCacheTypeLdapHostname = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getCrlCacheTypeLdapPort() {
      return this._CrlCacheTypeLdapPort;
   }

   public boolean isCrlCacheTypeLdapPortInherited() {
      return false;
   }

   public boolean isCrlCacheTypeLdapPortSet() {
      return this._isSet(21);
   }

   public void setCrlCacheTypeLdapPort(int param0) {
      CertRevocValidator.validatePort(param0);
      int _oldVal = this._CrlCacheTypeLdapPort;
      this._CrlCacheTypeLdapPort = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getCrlCacheTypeLdapSearchTimeout() {
      return this._CrlCacheTypeLdapSearchTimeout;
   }

   public boolean isCrlCacheTypeLdapSearchTimeoutInherited() {
      return false;
   }

   public boolean isCrlCacheTypeLdapSearchTimeoutSet() {
      return this._isSet(22);
   }

   public void setCrlCacheTypeLdapSearchTimeout(int param0) {
      LegalChecks.checkInRange("CrlCacheTypeLdapSearchTimeout", (long)param0, 1L, 300L);
      int _oldVal = this._CrlCacheTypeLdapSearchTimeout;
      this._CrlCacheTypeLdapSearchTimeout = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getCrlCacheRefreshPeriodPercent() {
      return this._CrlCacheRefreshPeriodPercent;
   }

   public boolean isCrlCacheRefreshPeriodPercentInherited() {
      return false;
   }

   public boolean isCrlCacheRefreshPeriodPercentSet() {
      return this._isSet(23);
   }

   public void setCrlCacheRefreshPeriodPercent(int param0) {
      LegalChecks.checkInRange("CrlCacheRefreshPeriodPercent", (long)param0, 1L, 100L);
      int _oldVal = this._CrlCacheRefreshPeriodPercent;
      this._CrlCacheRefreshPeriodPercent = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isCrlDpEnabled() {
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

   public void addCertRevocCa(CertRevocCaMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         CertRevocCaMBean[] _new;
         if (this._isSet(27)) {
            _new = (CertRevocCaMBean[])((CertRevocCaMBean[])this._getHelper()._extendArray(this.getCertRevocCas(), CertRevocCaMBean.class, param0));
         } else {
            _new = new CertRevocCaMBean[]{param0};
         }

         try {
            this.setCertRevocCas(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CertRevocCaMBean[] getCertRevocCas() {
      return this._CertRevocCas;
   }

   public boolean isCertRevocCasInherited() {
      return false;
   }

   public boolean isCertRevocCasSet() {
      return this._isSet(27);
   }

   public void removeCertRevocCa(CertRevocCaMBean param0) {
      this.destroyCertRevocCa(param0);
   }

   public void setCertRevocCas(CertRevocCaMBean[] param0) throws InvalidAttributeValueException {
      CertRevocCaMBean[] param0 = param0 == null ? new CertRevocCaMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CertRevocCaMBean[] _oldVal = this._CertRevocCas;
      this._CertRevocCas = (CertRevocCaMBean[])param0;
      this._postSet(27, _oldVal, param0);
   }

   public CertRevocCaMBean createCertRevocCa(String param0) {
      CertRevocCaMBeanImpl lookup = (CertRevocCaMBeanImpl)this.lookupCertRevocCa(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CertRevocCaMBeanImpl _val = new CertRevocCaMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCertRevocCa(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCertRevocCa(CertRevocCaMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         CertRevocCaMBean[] _old = this.getCertRevocCas();
         CertRevocCaMBean[] _new = (CertRevocCaMBean[])((CertRevocCaMBean[])this._getHelper()._removeElement(_old, CertRevocCaMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCertRevocCas(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public CertRevocCaMBean lookupCertRevocCa(String param0) {
      Object[] aary = (Object[])this._CertRevocCas;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CertRevocCaMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CertRevocCaMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      CertRevocValidator.validateCertRevoc(this);
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
         idx = 27;
      }

      try {
         switch (idx) {
            case 27:
               this._CertRevocCas = new CertRevocCaMBean[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._CrlCacheRefreshPeriodPercent = CertRevocMBean.DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT;
               if (initOne) {
                  break;
               }
            case 19:
               this._CrlCacheType = CertRevocMBean.DEFAULT_CRL_CACHE_TYPE;
               if (initOne) {
                  break;
               }
            case 20:
               this._CrlCacheTypeLdapHostname = CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME;
               if (initOne) {
                  break;
               }
            case 21:
               this._CrlCacheTypeLdapPort = CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_PORT;
               if (initOne) {
                  break;
               }
            case 22:
               this._CrlCacheTypeLdapSearchTimeout = CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT;
               if (initOne) {
                  break;
               }
            case 25:
               this._CrlDpDownloadTimeout = CertRevocMBean.DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT;
               if (initOne) {
                  break;
               }
            case 12:
               this._MethodOrder = CertRevocMBean.DEFAULT_METHOD_ORDER;
               if (initOne) {
                  break;
               }
            case 15:
               this._OcspResponseCacheCapacity = CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY;
               if (initOne) {
                  break;
               }
            case 16:
               this._OcspResponseCacheRefreshPeriodPercent = CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT;
               if (initOne) {
                  break;
               }
            case 17:
               this._OcspResponseTimeout = CertRevocMBean.DEFAULT_OCSP_RESPONSE_TIMEOUT;
               if (initOne) {
                  break;
               }
            case 18:
               this._OcspTimeTolerance = CertRevocMBean.DEFAULT_OCSP_TIME_TOLERANCE;
               if (initOne) {
                  break;
               }
            case 10:
               this._CheckingEnabled = CertRevocMBean.DEFAULT_CHECKING_ENABLED;
               if (initOne) {
                  break;
               }
            case 26:
               this._CrlDpBackgroundDownloadEnabled = CertRevocMBean.DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED;
               if (initOne) {
                  break;
               }
            case 24:
               this._CrlDpEnabled = CertRevocMBean.DEFAULT_CRL_DP_ENABLED;
               if (initOne) {
                  break;
               }
            case 11:
               this._FailOnUnknownRevocStatus = CertRevocMBean.DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS;
               if (initOne) {
                  break;
               }
            case 13:
               this._OcspNonceEnabled = CertRevocMBean.DEFAULT_OCSP_NONCE_ENABLED;
               if (initOne) {
                  break;
               }
            case 14:
               this._OcspResponseCacheEnabled = CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_ENABLED;
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
      return "CertRevoc";
   }

   public void putValue(String name, Object v) {
      if (name.equals("CertRevocCas")) {
         CertRevocCaMBean[] oldVal = this._CertRevocCas;
         this._CertRevocCas = (CertRevocCaMBean[])((CertRevocCaMBean[])v);
         this._postSet(27, oldVal, this._CertRevocCas);
      } else {
         boolean oldVal;
         if (name.equals("CheckingEnabled")) {
            oldVal = this._CheckingEnabled;
            this._CheckingEnabled = (Boolean)v;
            this._postSet(10, oldVal, this._CheckingEnabled);
         } else {
            int oldVal;
            if (name.equals("CrlCacheRefreshPeriodPercent")) {
               oldVal = this._CrlCacheRefreshPeriodPercent;
               this._CrlCacheRefreshPeriodPercent = (Integer)v;
               this._postSet(23, oldVal, this._CrlCacheRefreshPeriodPercent);
            } else {
               String oldVal;
               if (name.equals("CrlCacheType")) {
                  oldVal = this._CrlCacheType;
                  this._CrlCacheType = (String)v;
                  this._postSet(19, oldVal, this._CrlCacheType);
               } else if (name.equals("CrlCacheTypeLdapHostname")) {
                  oldVal = this._CrlCacheTypeLdapHostname;
                  this._CrlCacheTypeLdapHostname = (String)v;
                  this._postSet(20, oldVal, this._CrlCacheTypeLdapHostname);
               } else if (name.equals("CrlCacheTypeLdapPort")) {
                  oldVal = this._CrlCacheTypeLdapPort;
                  this._CrlCacheTypeLdapPort = (Integer)v;
                  this._postSet(21, oldVal, this._CrlCacheTypeLdapPort);
               } else if (name.equals("CrlCacheTypeLdapSearchTimeout")) {
                  oldVal = this._CrlCacheTypeLdapSearchTimeout;
                  this._CrlCacheTypeLdapSearchTimeout = (Integer)v;
                  this._postSet(22, oldVal, this._CrlCacheTypeLdapSearchTimeout);
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
                  } else if (name.equals("FailOnUnknownRevocStatus")) {
                     oldVal = this._FailOnUnknownRevocStatus;
                     this._FailOnUnknownRevocStatus = (Boolean)v;
                     this._postSet(11, oldVal, this._FailOnUnknownRevocStatus);
                  } else if (name.equals("MethodOrder")) {
                     oldVal = this._MethodOrder;
                     this._MethodOrder = (String)v;
                     this._postSet(12, oldVal, this._MethodOrder);
                  } else if (name.equals("OcspNonceEnabled")) {
                     oldVal = this._OcspNonceEnabled;
                     this._OcspNonceEnabled = (Boolean)v;
                     this._postSet(13, oldVal, this._OcspNonceEnabled);
                  } else if (name.equals("OcspResponseCacheCapacity")) {
                     oldVal = this._OcspResponseCacheCapacity;
                     this._OcspResponseCacheCapacity = (Integer)v;
                     this._postSet(15, oldVal, this._OcspResponseCacheCapacity);
                  } else if (name.equals("OcspResponseCacheEnabled")) {
                     oldVal = this._OcspResponseCacheEnabled;
                     this._OcspResponseCacheEnabled = (Boolean)v;
                     this._postSet(14, oldVal, this._OcspResponseCacheEnabled);
                  } else if (name.equals("OcspResponseCacheRefreshPeriodPercent")) {
                     oldVal = this._OcspResponseCacheRefreshPeriodPercent;
                     this._OcspResponseCacheRefreshPeriodPercent = (Integer)v;
                     this._postSet(16, oldVal, this._OcspResponseCacheRefreshPeriodPercent);
                  } else if (name.equals("OcspResponseTimeout")) {
                     oldVal = this._OcspResponseTimeout;
                     this._OcspResponseTimeout = (Long)v;
                     this._postSet(17, oldVal, this._OcspResponseTimeout);
                  } else if (name.equals("OcspTimeTolerance")) {
                     oldVal = this._OcspTimeTolerance;
                     this._OcspTimeTolerance = (Integer)v;
                     this._postSet(18, oldVal, this._OcspTimeTolerance);
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CertRevocCas")) {
         return this._CertRevocCas;
      } else if (name.equals("CheckingEnabled")) {
         return new Boolean(this._CheckingEnabled);
      } else if (name.equals("CrlCacheRefreshPeriodPercent")) {
         return new Integer(this._CrlCacheRefreshPeriodPercent);
      } else if (name.equals("CrlCacheType")) {
         return this._CrlCacheType;
      } else if (name.equals("CrlCacheTypeLdapHostname")) {
         return this._CrlCacheTypeLdapHostname;
      } else if (name.equals("CrlCacheTypeLdapPort")) {
         return new Integer(this._CrlCacheTypeLdapPort);
      } else if (name.equals("CrlCacheTypeLdapSearchTimeout")) {
         return new Integer(this._CrlCacheTypeLdapSearchTimeout);
      } else if (name.equals("CrlDpBackgroundDownloadEnabled")) {
         return new Boolean(this._CrlDpBackgroundDownloadEnabled);
      } else if (name.equals("CrlDpDownloadTimeout")) {
         return new Long(this._CrlDpDownloadTimeout);
      } else if (name.equals("CrlDpEnabled")) {
         return new Boolean(this._CrlDpEnabled);
      } else if (name.equals("FailOnUnknownRevocStatus")) {
         return new Boolean(this._FailOnUnknownRevocStatus);
      } else if (name.equals("MethodOrder")) {
         return this._MethodOrder;
      } else if (name.equals("OcspNonceEnabled")) {
         return new Boolean(this._OcspNonceEnabled);
      } else if (name.equals("OcspResponseCacheCapacity")) {
         return new Integer(this._OcspResponseCacheCapacity);
      } else if (name.equals("OcspResponseCacheEnabled")) {
         return new Boolean(this._OcspResponseCacheEnabled);
      } else if (name.equals("OcspResponseCacheRefreshPeriodPercent")) {
         return new Integer(this._OcspResponseCacheRefreshPeriodPercent);
      } else if (name.equals("OcspResponseTimeout")) {
         return new Long(this._OcspResponseTimeout);
      } else {
         return name.equals("OcspTimeTolerance") ? new Integer(this._OcspTimeTolerance) : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      String[] _set;
      try {
         _set = new String[]{CertRevocMBean.CRL_CACHE_TYPE_FILE, CertRevocMBean.CRL_CACHE_TYPE_LDAP};
         LegalChecks.checkInEnum("CrlCacheType", CertRevocMBean.DEFAULT_CRL_CACHE_TYPE, _set);
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property CrlCacheType in CertRevocMBean" + var2.getMessage());
      }

      try {
         _set = new String[]{CertRevocMBean.METHOD_OCSP, CertRevocMBean.METHOD_CRL, CertRevocMBean.METHOD_OCSP_THEN_CRL, CertRevocMBean.METHOD_CRL_THEN_OCSP};
         LegalChecks.checkInEnum("MethodOrder", CertRevocMBean.DEFAULT_METHOD_ORDER, _set);
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property MethodOrder in CertRevocMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("method-order")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("cert-revoc-ca")) {
                  return 27;
               }
               break;
            case 14:
               if (s.equals("crl-cache-type")) {
                  return 19;
               }

               if (s.equals("crl-dp-enabled")) {
                  return 24;
               }
            case 15:
            case 17:
            case 20:
            case 22:
            case 25:
            case 26:
            case 29:
            case 30:
            case 31:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            default:
               break;
            case 16:
               if (s.equals("checking-enabled")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("ocsp-nonce-enabled")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("ocsp-time-tolerance")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("ocsp-response-timeout")) {
                  return 17;
               }
               break;
            case 23:
               if (s.equals("crl-dp-download-timeout")) {
                  return 25;
               }
               break;
            case 24:
               if (s.equals("crl-cache-type-ldap-port")) {
                  return 21;
               }
               break;
            case 27:
               if (s.equals("ocsp-response-cache-enabled")) {
                  return 14;
               }
               break;
            case 28:
               if (s.equals("crl-cache-type-ldap-hostname")) {
                  return 20;
               }

               if (s.equals("ocsp-response-cache-capacity")) {
                  return 15;
               }

               if (s.equals("fail-on-unknown-revoc-status")) {
                  return 11;
               }
               break;
            case 32:
               if (s.equals("crl-cache-refresh-period-percent")) {
                  return 23;
               }
               break;
            case 34:
               if (s.equals("crl-cache-type-ldap-search-timeout")) {
                  return 22;
               }

               if (s.equals("crl-dp-background-download-enabled")) {
                  return 26;
               }
               break;
            case 42:
               if (s.equals("ocsp-response-cache-refresh-period-percent")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 27:
               return new CertRevocCaMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "checking-enabled";
            case 11:
               return "fail-on-unknown-revoc-status";
            case 12:
               return "method-order";
            case 13:
               return "ocsp-nonce-enabled";
            case 14:
               return "ocsp-response-cache-enabled";
            case 15:
               return "ocsp-response-cache-capacity";
            case 16:
               return "ocsp-response-cache-refresh-period-percent";
            case 17:
               return "ocsp-response-timeout";
            case 18:
               return "ocsp-time-tolerance";
            case 19:
               return "crl-cache-type";
            case 20:
               return "crl-cache-type-ldap-hostname";
            case 21:
               return "crl-cache-type-ldap-port";
            case 22:
               return "crl-cache-type-ldap-search-timeout";
            case 23:
               return "crl-cache-refresh-period-percent";
            case 24:
               return "crl-dp-enabled";
            case 25:
               return "crl-dp-download-timeout";
            case 26:
               return "crl-dp-background-download-enabled";
            case 27:
               return "cert-revoc-ca";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 27:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 27:
               return true;
            default:
               return super.isBean(propIndex);
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
      private CertRevocMBeanImpl bean;

      protected Helper(CertRevocMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "CheckingEnabled";
            case 11:
               return "FailOnUnknownRevocStatus";
            case 12:
               return "MethodOrder";
            case 13:
               return "OcspNonceEnabled";
            case 14:
               return "OcspResponseCacheEnabled";
            case 15:
               return "OcspResponseCacheCapacity";
            case 16:
               return "OcspResponseCacheRefreshPeriodPercent";
            case 17:
               return "OcspResponseTimeout";
            case 18:
               return "OcspTimeTolerance";
            case 19:
               return "CrlCacheType";
            case 20:
               return "CrlCacheTypeLdapHostname";
            case 21:
               return "CrlCacheTypeLdapPort";
            case 22:
               return "CrlCacheTypeLdapSearchTimeout";
            case 23:
               return "CrlCacheRefreshPeriodPercent";
            case 24:
               return "CrlDpEnabled";
            case 25:
               return "CrlDpDownloadTimeout";
            case 26:
               return "CrlDpBackgroundDownloadEnabled";
            case 27:
               return "CertRevocCas";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CertRevocCas")) {
            return 27;
         } else if (propName.equals("CrlCacheRefreshPeriodPercent")) {
            return 23;
         } else if (propName.equals("CrlCacheType")) {
            return 19;
         } else if (propName.equals("CrlCacheTypeLdapHostname")) {
            return 20;
         } else if (propName.equals("CrlCacheTypeLdapPort")) {
            return 21;
         } else if (propName.equals("CrlCacheTypeLdapSearchTimeout")) {
            return 22;
         } else if (propName.equals("CrlDpDownloadTimeout")) {
            return 25;
         } else if (propName.equals("MethodOrder")) {
            return 12;
         } else if (propName.equals("OcspResponseCacheCapacity")) {
            return 15;
         } else if (propName.equals("OcspResponseCacheRefreshPeriodPercent")) {
            return 16;
         } else if (propName.equals("OcspResponseTimeout")) {
            return 17;
         } else if (propName.equals("OcspTimeTolerance")) {
            return 18;
         } else if (propName.equals("CheckingEnabled")) {
            return 10;
         } else if (propName.equals("CrlDpBackgroundDownloadEnabled")) {
            return 26;
         } else if (propName.equals("CrlDpEnabled")) {
            return 24;
         } else if (propName.equals("FailOnUnknownRevocStatus")) {
            return 11;
         } else if (propName.equals("OcspNonceEnabled")) {
            return 13;
         } else {
            return propName.equals("OcspResponseCacheEnabled") ? 14 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCertRevocCas()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getCertRevocCas().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCertRevocCas()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCrlCacheRefreshPeriodPercentSet()) {
               buf.append("CrlCacheRefreshPeriodPercent");
               buf.append(String.valueOf(this.bean.getCrlCacheRefreshPeriodPercent()));
            }

            if (this.bean.isCrlCacheTypeSet()) {
               buf.append("CrlCacheType");
               buf.append(String.valueOf(this.bean.getCrlCacheType()));
            }

            if (this.bean.isCrlCacheTypeLdapHostnameSet()) {
               buf.append("CrlCacheTypeLdapHostname");
               buf.append(String.valueOf(this.bean.getCrlCacheTypeLdapHostname()));
            }

            if (this.bean.isCrlCacheTypeLdapPortSet()) {
               buf.append("CrlCacheTypeLdapPort");
               buf.append(String.valueOf(this.bean.getCrlCacheTypeLdapPort()));
            }

            if (this.bean.isCrlCacheTypeLdapSearchTimeoutSet()) {
               buf.append("CrlCacheTypeLdapSearchTimeout");
               buf.append(String.valueOf(this.bean.getCrlCacheTypeLdapSearchTimeout()));
            }

            if (this.bean.isCrlDpDownloadTimeoutSet()) {
               buf.append("CrlDpDownloadTimeout");
               buf.append(String.valueOf(this.bean.getCrlDpDownloadTimeout()));
            }

            if (this.bean.isMethodOrderSet()) {
               buf.append("MethodOrder");
               buf.append(String.valueOf(this.bean.getMethodOrder()));
            }

            if (this.bean.isOcspResponseCacheCapacitySet()) {
               buf.append("OcspResponseCacheCapacity");
               buf.append(String.valueOf(this.bean.getOcspResponseCacheCapacity()));
            }

            if (this.bean.isOcspResponseCacheRefreshPeriodPercentSet()) {
               buf.append("OcspResponseCacheRefreshPeriodPercent");
               buf.append(String.valueOf(this.bean.getOcspResponseCacheRefreshPeriodPercent()));
            }

            if (this.bean.isOcspResponseTimeoutSet()) {
               buf.append("OcspResponseTimeout");
               buf.append(String.valueOf(this.bean.getOcspResponseTimeout()));
            }

            if (this.bean.isOcspTimeToleranceSet()) {
               buf.append("OcspTimeTolerance");
               buf.append(String.valueOf(this.bean.getOcspTimeTolerance()));
            }

            if (this.bean.isCheckingEnabledSet()) {
               buf.append("CheckingEnabled");
               buf.append(String.valueOf(this.bean.isCheckingEnabled()));
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
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            CertRevocMBeanImpl otherTyped = (CertRevocMBeanImpl)other;
            this.computeChildDiff("CertRevocCas", this.bean.getCertRevocCas(), otherTyped.getCertRevocCas(), true);
            this.computeDiff("CrlCacheRefreshPeriodPercent", this.bean.getCrlCacheRefreshPeriodPercent(), otherTyped.getCrlCacheRefreshPeriodPercent(), true);
            this.computeDiff("CrlCacheType", this.bean.getCrlCacheType(), otherTyped.getCrlCacheType(), false);
            this.computeDiff("CrlCacheTypeLdapHostname", this.bean.getCrlCacheTypeLdapHostname(), otherTyped.getCrlCacheTypeLdapHostname(), false);
            this.computeDiff("CrlCacheTypeLdapPort", this.bean.getCrlCacheTypeLdapPort(), otherTyped.getCrlCacheTypeLdapPort(), false);
            this.computeDiff("CrlCacheTypeLdapSearchTimeout", this.bean.getCrlCacheTypeLdapSearchTimeout(), otherTyped.getCrlCacheTypeLdapSearchTimeout(), true);
            this.computeDiff("CrlDpDownloadTimeout", this.bean.getCrlDpDownloadTimeout(), otherTyped.getCrlDpDownloadTimeout(), true);
            this.computeDiff("MethodOrder", this.bean.getMethodOrder(), otherTyped.getMethodOrder(), true);
            this.computeDiff("OcspResponseCacheCapacity", this.bean.getOcspResponseCacheCapacity(), otherTyped.getOcspResponseCacheCapacity(), true);
            this.computeDiff("OcspResponseCacheRefreshPeriodPercent", this.bean.getOcspResponseCacheRefreshPeriodPercent(), otherTyped.getOcspResponseCacheRefreshPeriodPercent(), true);
            this.computeDiff("OcspResponseTimeout", this.bean.getOcspResponseTimeout(), otherTyped.getOcspResponseTimeout(), true);
            this.computeDiff("OcspTimeTolerance", this.bean.getOcspTimeTolerance(), otherTyped.getOcspTimeTolerance(), true);
            this.computeDiff("CheckingEnabled", this.bean.isCheckingEnabled(), otherTyped.isCheckingEnabled(), true);
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
            CertRevocMBeanImpl original = (CertRevocMBeanImpl)event.getSourceBean();
            CertRevocMBeanImpl proposed = (CertRevocMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CertRevocCas")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCertRevocCa((CertRevocCaMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCertRevocCa((CertRevocCaMBean)update.getRemovedObject());
                  }

                  if (original.getCertRevocCas() == null || original.getCertRevocCas().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("CrlCacheRefreshPeriodPercent")) {
                  original.setCrlCacheRefreshPeriodPercent(proposed.getCrlCacheRefreshPeriodPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("CrlCacheType")) {
                  original.setCrlCacheType(proposed.getCrlCacheType());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("CrlCacheTypeLdapHostname")) {
                  original.setCrlCacheTypeLdapHostname(proposed.getCrlCacheTypeLdapHostname());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("CrlCacheTypeLdapPort")) {
                  original.setCrlCacheTypeLdapPort(proposed.getCrlCacheTypeLdapPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("CrlCacheTypeLdapSearchTimeout")) {
                  original.setCrlCacheTypeLdapSearchTimeout(proposed.getCrlCacheTypeLdapSearchTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("CrlDpDownloadTimeout")) {
                  original.setCrlDpDownloadTimeout(proposed.getCrlDpDownloadTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("MethodOrder")) {
                  original.setMethodOrder(proposed.getMethodOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("OcspResponseCacheCapacity")) {
                  original.setOcspResponseCacheCapacity(proposed.getOcspResponseCacheCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("OcspResponseCacheRefreshPeriodPercent")) {
                  original.setOcspResponseCacheRefreshPeriodPercent(proposed.getOcspResponseCacheRefreshPeriodPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("OcspResponseTimeout")) {
                  original.setOcspResponseTimeout(proposed.getOcspResponseTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("OcspTimeTolerance")) {
                  original.setOcspTimeTolerance(proposed.getOcspTimeTolerance());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CheckingEnabled")) {
                  original.setCheckingEnabled(proposed.isCheckingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("CrlDpBackgroundDownloadEnabled")) {
                  original.setCrlDpBackgroundDownloadEnabled(proposed.isCrlDpBackgroundDownloadEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("CrlDpEnabled")) {
                  original.setCrlDpEnabled(proposed.isCrlDpEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("FailOnUnknownRevocStatus")) {
                  original.setFailOnUnknownRevocStatus(proposed.isFailOnUnknownRevocStatus());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("OcspNonceEnabled")) {
                  original.setOcspNonceEnabled(proposed.isOcspNonceEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("OcspResponseCacheEnabled")) {
                  original.setOcspResponseCacheEnabled(proposed.isOcspResponseCacheEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            CertRevocMBeanImpl copy = (CertRevocMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CertRevocCas")) && this.bean.isCertRevocCasSet() && !copy._isSet(27)) {
               CertRevocCaMBean[] oldCertRevocCas = this.bean.getCertRevocCas();
               CertRevocCaMBean[] newCertRevocCas = new CertRevocCaMBean[oldCertRevocCas.length];

               for(int i = 0; i < newCertRevocCas.length; ++i) {
                  newCertRevocCas[i] = (CertRevocCaMBean)((CertRevocCaMBean)this.createCopy((AbstractDescriptorBean)oldCertRevocCas[i], includeObsolete));
               }

               copy.setCertRevocCas(newCertRevocCas);
            }

            if ((excludeProps == null || !excludeProps.contains("CrlCacheRefreshPeriodPercent")) && this.bean.isCrlCacheRefreshPeriodPercentSet()) {
               copy.setCrlCacheRefreshPeriodPercent(this.bean.getCrlCacheRefreshPeriodPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlCacheType")) && this.bean.isCrlCacheTypeSet()) {
               copy.setCrlCacheType(this.bean.getCrlCacheType());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlCacheTypeLdapHostname")) && this.bean.isCrlCacheTypeLdapHostnameSet()) {
               copy.setCrlCacheTypeLdapHostname(this.bean.getCrlCacheTypeLdapHostname());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlCacheTypeLdapPort")) && this.bean.isCrlCacheTypeLdapPortSet()) {
               copy.setCrlCacheTypeLdapPort(this.bean.getCrlCacheTypeLdapPort());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlCacheTypeLdapSearchTimeout")) && this.bean.isCrlCacheTypeLdapSearchTimeoutSet()) {
               copy.setCrlCacheTypeLdapSearchTimeout(this.bean.getCrlCacheTypeLdapSearchTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CrlDpDownloadTimeout")) && this.bean.isCrlDpDownloadTimeoutSet()) {
               copy.setCrlDpDownloadTimeout(this.bean.getCrlDpDownloadTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodOrder")) && this.bean.isMethodOrderSet()) {
               copy.setMethodOrder(this.bean.getMethodOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponseCacheCapacity")) && this.bean.isOcspResponseCacheCapacitySet()) {
               copy.setOcspResponseCacheCapacity(this.bean.getOcspResponseCacheCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponseCacheRefreshPeriodPercent")) && this.bean.isOcspResponseCacheRefreshPeriodPercentSet()) {
               copy.setOcspResponseCacheRefreshPeriodPercent(this.bean.getOcspResponseCacheRefreshPeriodPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspResponseTimeout")) && this.bean.isOcspResponseTimeoutSet()) {
               copy.setOcspResponseTimeout(this.bean.getOcspResponseTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("OcspTimeTolerance")) && this.bean.isOcspTimeToleranceSet()) {
               copy.setOcspTimeTolerance(this.bean.getOcspTimeTolerance());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckingEnabled")) && this.bean.isCheckingEnabledSet()) {
               copy.setCheckingEnabled(this.bean.isCheckingEnabled());
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
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getCertRevocCas(), clazz, annotation);
      }
   }
}
