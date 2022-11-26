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

public class TLSMBeanImpl extends ConfigurationMBeanImpl implements TLSMBean, Serializable {
   private boolean _AllowUnencryptedNullCipher;
   private String[] _Ciphersuites;
   private boolean _ClientCertificateEnforced;
   private boolean _HostnameVerificationIgnored;
   private String _HostnameVerifier;
   private String _IdentityKeyStoreFileName;
   private String _IdentityKeyStorePassPhrase;
   private byte[] _IdentityKeyStorePassPhraseEncrypted;
   private String _IdentityKeyStoreType;
   private String _IdentityPrivateKeyAlias;
   private String _IdentityPrivateKeyPassPhrase;
   private byte[] _IdentityPrivateKeyPassPhraseEncrypted;
   private String _InboundCertificateValidation;
   private String _MinimumTLSProtocolVersion;
   private String _Name;
   private String _OutboundCertificateValidation;
   private boolean _SSLv2HelloEnabled;
   private String _TrustKeyStoreFileName;
   private String _TrustKeyStorePassPhrase;
   private byte[] _TrustKeyStorePassPhraseEncrypted;
   private String _TrustKeyStoreType;
   private boolean _TwoWaySSLEnabled;
   private String _Usage;
   private static SchemaHelper2 _schemaHelper;

   public TLSMBeanImpl() {
      this._initializeProperty(-1);
   }

   public TLSMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TLSMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getUsage() {
      return this._Usage;
   }

   public boolean isUsageInherited() {
      return false;
   }

   public boolean isUsageSet() {
      return this._isSet(10);
   }

   public void setUsage(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ForServer", "ForClient", "ForClientOrServer"};
      param0 = LegalChecks.checkInEnum("Usage", param0, _set);
      String _oldVal = this._Usage;
      this._Usage = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getIdentityPrivateKeyAlias() {
      return this._IdentityPrivateKeyAlias;
   }

   public boolean isIdentityPrivateKeyAliasInherited() {
      return false;
   }

   public boolean isIdentityPrivateKeyAliasSet() {
      return this._isSet(11);
   }

   public void setIdentityPrivateKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IdentityPrivateKeyAlias;
      this._IdentityPrivateKeyAlias = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getIdentityPrivateKeyPassPhrase() {
      byte[] bEncrypted = this.getIdentityPrivateKeyPassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("IdentityPrivateKeyPassPhrase", bEncrypted);
   }

   public boolean isIdentityPrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isIdentityPrivateKeyPassPhraseSet() {
      return this.isIdentityPrivateKeyPassPhraseEncryptedSet();
   }

   public void setIdentityPrivateKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setIdentityPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("IdentityPrivateKeyPassPhrase", param0));
   }

   public byte[] getIdentityPrivateKeyPassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._IdentityPrivateKeyPassPhraseEncrypted);
   }

   public String getIdentityPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getIdentityPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isIdentityPrivateKeyPassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isIdentityPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(13);
   }

   public void setIdentityPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setIdentityPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getIdentityKeyStoreFileName() {
      return this._IdentityKeyStoreFileName;
   }

   public boolean isIdentityKeyStoreFileNameInherited() {
      return false;
   }

   public boolean isIdentityKeyStoreFileNameSet() {
      return this._isSet(14);
   }

   public void setIdentityKeyStoreFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IdentityKeyStoreFileName;
      this._IdentityKeyStoreFileName = param0;
      this._postSet(14, _oldVal, param0);
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
      return this._isSet(16);
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

   public String getIdentityKeyStoreType() {
      return this._IdentityKeyStoreType;
   }

   public boolean isIdentityKeyStoreTypeInherited() {
      return false;
   }

   public boolean isIdentityKeyStoreTypeSet() {
      return this._isSet(17);
   }

   public void setIdentityKeyStoreType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._IdentityKeyStoreType;
      this._IdentityKeyStoreType = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isHostnameVerificationIgnored() {
      return this._HostnameVerificationIgnored;
   }

   public boolean isHostnameVerificationIgnoredInherited() {
      return false;
   }

   public boolean isHostnameVerificationIgnoredSet() {
      return this._isSet(18);
   }

   public void setHostnameVerificationIgnored(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._HostnameVerificationIgnored;
      this._HostnameVerificationIgnored = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getHostnameVerifier() {
      return this._HostnameVerifier;
   }

   public boolean isHostnameVerifierInherited() {
      return false;
   }

   public boolean isHostnameVerifierSet() {
      return this._isSet(19);
   }

   public void setHostnameVerifier(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HostnameVerifier;
      this._HostnameVerifier = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isTwoWaySSLEnabled() {
      return this._TwoWaySSLEnabled;
   }

   public boolean isTwoWaySSLEnabledInherited() {
      return false;
   }

   public boolean isTwoWaySSLEnabledSet() {
      return this._isSet(20);
   }

   public void setTwoWaySSLEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._TwoWaySSLEnabled;
      this._TwoWaySSLEnabled = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isClientCertificateEnforced() {
      return this._ClientCertificateEnforced;
   }

   public boolean isClientCertificateEnforcedInherited() {
      return false;
   }

   public boolean isClientCertificateEnforcedSet() {
      return this._isSet(21);
   }

   public void setClientCertificateEnforced(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._ClientCertificateEnforced;
      this._ClientCertificateEnforced = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String[] getCiphersuites() {
      return this._Ciphersuites;
   }

   public boolean isCiphersuitesInherited() {
      return false;
   }

   public boolean isCiphersuitesSet() {
      return this._isSet(22);
   }

   public void setCiphersuites(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Ciphersuites;
      this._Ciphersuites = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isAllowUnencryptedNullCipher() {
      return this._AllowUnencryptedNullCipher;
   }

   public boolean isAllowUnencryptedNullCipherInherited() {
      return false;
   }

   public boolean isAllowUnencryptedNullCipherSet() {
      return this._isSet(23);
   }

   public void setAllowUnencryptedNullCipher(boolean param0) {
      boolean _oldVal = this._AllowUnencryptedNullCipher;
      this._AllowUnencryptedNullCipher = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getTrustKeyStoreFileName() {
      return this._TrustKeyStoreFileName;
   }

   public boolean isTrustKeyStoreFileNameInherited() {
      return false;
   }

   public boolean isTrustKeyStoreFileNameSet() {
      return this._isSet(24);
   }

   public void setTrustKeyStoreFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrustKeyStoreFileName;
      this._TrustKeyStoreFileName = param0;
      this._postSet(24, _oldVal, param0);
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
      return this._isSet(26);
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

   public String getTrustKeyStoreType() {
      return this._TrustKeyStoreType;
   }

   public boolean isTrustKeyStoreTypeInherited() {
      return false;
   }

   public boolean isTrustKeyStoreTypeSet() {
      return this._isSet(27);
   }

   public void setTrustKeyStoreType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrustKeyStoreType;
      this._TrustKeyStoreType = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getInboundCertificateValidation() {
      return this._InboundCertificateValidation;
   }

   public boolean isInboundCertificateValidationInherited() {
      return false;
   }

   public boolean isInboundCertificateValidationSet() {
      return this._isSet(28);
   }

   public void setInboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("InboundCertificateValidation", param0, _set);
      String _oldVal = this._InboundCertificateValidation;
      this._InboundCertificateValidation = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getOutboundCertificateValidation() {
      return this._OutboundCertificateValidation;
   }

   public boolean isOutboundCertificateValidationInherited() {
      return false;
   }

   public boolean isOutboundCertificateValidationSet() {
      return this._isSet(29);
   }

   public void setOutboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("OutboundCertificateValidation", param0, _set);
      String _oldVal = this._OutboundCertificateValidation;
      this._OutboundCertificateValidation = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getMinimumTLSProtocolVersion() {
      return this._MinimumTLSProtocolVersion;
   }

   public boolean isMinimumTLSProtocolVersionInherited() {
      return false;
   }

   public boolean isMinimumTLSProtocolVersionSet() {
      return this._isSet(30);
   }

   public void setMinimumTLSProtocolVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      ServerLegalHelper.validateMinimumSSLProtocol(param0);
      String _oldVal = this._MinimumTLSProtocolVersion;
      this._MinimumTLSProtocolVersion = param0;
      this._postSet(30, _oldVal, param0);
   }

   public boolean isSSLv2HelloEnabled() {
      return this._SSLv2HelloEnabled;
   }

   public boolean isSSLv2HelloEnabledInherited() {
      return false;
   }

   public boolean isSSLv2HelloEnabledSet() {
      return this._isSet(31);
   }

   public void setSSLv2HelloEnabled(boolean param0) {
      boolean _oldVal = this._SSLv2HelloEnabled;
      this._SSLv2HelloEnabled = param0;
      this._postSet(31, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setIdentityKeyStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._IdentityKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: IdentityKeyStorePassPhraseEncrypted of TLSMBean");
      } else {
         this._getHelper()._clearArray(this._IdentityKeyStorePassPhraseEncrypted);
         this._IdentityKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(16, _oldVal, param0);
      }
   }

   public void setIdentityPrivateKeyPassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._IdentityPrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: IdentityPrivateKeyPassPhraseEncrypted of TLSMBean");
      } else {
         this._getHelper()._clearArray(this._IdentityPrivateKeyPassPhraseEncrypted);
         this._IdentityPrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(13, _oldVal, param0);
      }
   }

   public void setTrustKeyStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._TrustKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: TrustKeyStorePassPhraseEncrypted of TLSMBean");
      } else {
         this._getHelper()._clearArray(this._TrustKeyStorePassPhraseEncrypted);
         this._TrustKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(26, _oldVal, param0);
      }
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 15) {
            this._markSet(16, false);
         }

         if (idx == 12) {
            this._markSet(13, false);
         }

         if (idx == 25) {
            this._markSet(26, false);
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
         idx = 22;
      }

      try {
         switch (idx) {
            case 22:
               this._Ciphersuites = new String[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._HostnameVerifier = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._IdentityKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._IdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._IdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._IdentityKeyStoreType = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._IdentityPrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._IdentityPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._IdentityPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._InboundCertificateValidation = "BuiltinSSLValidationOnly";
               if (initOne) {
                  break;
               }
            case 30:
               this._MinimumTLSProtocolVersion = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = "<unknown>";
               if (initOne) {
                  break;
               }
            case 29:
               this._OutboundCertificateValidation = "BuiltinSSLValidationOnly";
               if (initOne) {
                  break;
               }
            case 24:
               this._TrustKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._TrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._TrustKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._TrustKeyStoreType = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Usage = "ForClientOrServer";
               if (initOne) {
                  break;
               }
            case 23:
               this._AllowUnencryptedNullCipher = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._ClientCertificateEnforced = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._HostnameVerificationIgnored = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._SSLv2HelloEnabled = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._TwoWaySSLEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
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
      return "TLS";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AllowUnencryptedNullCipher")) {
         oldVal = this._AllowUnencryptedNullCipher;
         this._AllowUnencryptedNullCipher = (Boolean)v;
         this._postSet(23, oldVal, this._AllowUnencryptedNullCipher);
      } else if (name.equals("Ciphersuites")) {
         String[] oldVal = this._Ciphersuites;
         this._Ciphersuites = (String[])((String[])v);
         this._postSet(22, oldVal, this._Ciphersuites);
      } else if (name.equals("ClientCertificateEnforced")) {
         oldVal = this._ClientCertificateEnforced;
         this._ClientCertificateEnforced = (Boolean)v;
         this._postSet(21, oldVal, this._ClientCertificateEnforced);
      } else if (name.equals("HostnameVerificationIgnored")) {
         oldVal = this._HostnameVerificationIgnored;
         this._HostnameVerificationIgnored = (Boolean)v;
         this._postSet(18, oldVal, this._HostnameVerificationIgnored);
      } else {
         String oldVal;
         if (name.equals("HostnameVerifier")) {
            oldVal = this._HostnameVerifier;
            this._HostnameVerifier = (String)v;
            this._postSet(19, oldVal, this._HostnameVerifier);
         } else if (name.equals("IdentityKeyStoreFileName")) {
            oldVal = this._IdentityKeyStoreFileName;
            this._IdentityKeyStoreFileName = (String)v;
            this._postSet(14, oldVal, this._IdentityKeyStoreFileName);
         } else if (name.equals("IdentityKeyStorePassPhrase")) {
            oldVal = this._IdentityKeyStorePassPhrase;
            this._IdentityKeyStorePassPhrase = (String)v;
            this._postSet(15, oldVal, this._IdentityKeyStorePassPhrase);
         } else {
            byte[] oldVal;
            if (name.equals("IdentityKeyStorePassPhraseEncrypted")) {
               oldVal = this._IdentityKeyStorePassPhraseEncrypted;
               this._IdentityKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
               this._postSet(16, oldVal, this._IdentityKeyStorePassPhraseEncrypted);
            } else if (name.equals("IdentityKeyStoreType")) {
               oldVal = this._IdentityKeyStoreType;
               this._IdentityKeyStoreType = (String)v;
               this._postSet(17, oldVal, this._IdentityKeyStoreType);
            } else if (name.equals("IdentityPrivateKeyAlias")) {
               oldVal = this._IdentityPrivateKeyAlias;
               this._IdentityPrivateKeyAlias = (String)v;
               this._postSet(11, oldVal, this._IdentityPrivateKeyAlias);
            } else if (name.equals("IdentityPrivateKeyPassPhrase")) {
               oldVal = this._IdentityPrivateKeyPassPhrase;
               this._IdentityPrivateKeyPassPhrase = (String)v;
               this._postSet(12, oldVal, this._IdentityPrivateKeyPassPhrase);
            } else if (name.equals("IdentityPrivateKeyPassPhraseEncrypted")) {
               oldVal = this._IdentityPrivateKeyPassPhraseEncrypted;
               this._IdentityPrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
               this._postSet(13, oldVal, this._IdentityPrivateKeyPassPhraseEncrypted);
            } else if (name.equals("InboundCertificateValidation")) {
               oldVal = this._InboundCertificateValidation;
               this._InboundCertificateValidation = (String)v;
               this._postSet(28, oldVal, this._InboundCertificateValidation);
            } else if (name.equals("MinimumTLSProtocolVersion")) {
               oldVal = this._MinimumTLSProtocolVersion;
               this._MinimumTLSProtocolVersion = (String)v;
               this._postSet(30, oldVal, this._MinimumTLSProtocolVersion);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("OutboundCertificateValidation")) {
               oldVal = this._OutboundCertificateValidation;
               this._OutboundCertificateValidation = (String)v;
               this._postSet(29, oldVal, this._OutboundCertificateValidation);
            } else if (name.equals("SSLv2HelloEnabled")) {
               oldVal = this._SSLv2HelloEnabled;
               this._SSLv2HelloEnabled = (Boolean)v;
               this._postSet(31, oldVal, this._SSLv2HelloEnabled);
            } else if (name.equals("TrustKeyStoreFileName")) {
               oldVal = this._TrustKeyStoreFileName;
               this._TrustKeyStoreFileName = (String)v;
               this._postSet(24, oldVal, this._TrustKeyStoreFileName);
            } else if (name.equals("TrustKeyStorePassPhrase")) {
               oldVal = this._TrustKeyStorePassPhrase;
               this._TrustKeyStorePassPhrase = (String)v;
               this._postSet(25, oldVal, this._TrustKeyStorePassPhrase);
            } else if (name.equals("TrustKeyStorePassPhraseEncrypted")) {
               oldVal = this._TrustKeyStorePassPhraseEncrypted;
               this._TrustKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
               this._postSet(26, oldVal, this._TrustKeyStorePassPhraseEncrypted);
            } else if (name.equals("TrustKeyStoreType")) {
               oldVal = this._TrustKeyStoreType;
               this._TrustKeyStoreType = (String)v;
               this._postSet(27, oldVal, this._TrustKeyStoreType);
            } else if (name.equals("TwoWaySSLEnabled")) {
               oldVal = this._TwoWaySSLEnabled;
               this._TwoWaySSLEnabled = (Boolean)v;
               this._postSet(20, oldVal, this._TwoWaySSLEnabled);
            } else if (name.equals("Usage")) {
               oldVal = this._Usage;
               this._Usage = (String)v;
               this._postSet(10, oldVal, this._Usage);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowUnencryptedNullCipher")) {
         return new Boolean(this._AllowUnencryptedNullCipher);
      } else if (name.equals("Ciphersuites")) {
         return this._Ciphersuites;
      } else if (name.equals("ClientCertificateEnforced")) {
         return new Boolean(this._ClientCertificateEnforced);
      } else if (name.equals("HostnameVerificationIgnored")) {
         return new Boolean(this._HostnameVerificationIgnored);
      } else if (name.equals("HostnameVerifier")) {
         return this._HostnameVerifier;
      } else if (name.equals("IdentityKeyStoreFileName")) {
         return this._IdentityKeyStoreFileName;
      } else if (name.equals("IdentityKeyStorePassPhrase")) {
         return this._IdentityKeyStorePassPhrase;
      } else if (name.equals("IdentityKeyStorePassPhraseEncrypted")) {
         return this._IdentityKeyStorePassPhraseEncrypted;
      } else if (name.equals("IdentityKeyStoreType")) {
         return this._IdentityKeyStoreType;
      } else if (name.equals("IdentityPrivateKeyAlias")) {
         return this._IdentityPrivateKeyAlias;
      } else if (name.equals("IdentityPrivateKeyPassPhrase")) {
         return this._IdentityPrivateKeyPassPhrase;
      } else if (name.equals("IdentityPrivateKeyPassPhraseEncrypted")) {
         return this._IdentityPrivateKeyPassPhraseEncrypted;
      } else if (name.equals("InboundCertificateValidation")) {
         return this._InboundCertificateValidation;
      } else if (name.equals("MinimumTLSProtocolVersion")) {
         return this._MinimumTLSProtocolVersion;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutboundCertificateValidation")) {
         return this._OutboundCertificateValidation;
      } else if (name.equals("SSLv2HelloEnabled")) {
         return new Boolean(this._SSLv2HelloEnabled);
      } else if (name.equals("TrustKeyStoreFileName")) {
         return this._TrustKeyStoreFileName;
      } else if (name.equals("TrustKeyStorePassPhrase")) {
         return this._TrustKeyStorePassPhrase;
      } else if (name.equals("TrustKeyStorePassPhraseEncrypted")) {
         return this._TrustKeyStorePassPhraseEncrypted;
      } else if (name.equals("TrustKeyStoreType")) {
         return this._TrustKeyStoreType;
      } else if (name.equals("TwoWaySSLEnabled")) {
         return new Boolean(this._TwoWaySSLEnabled);
      } else {
         return name.equals("Usage") ? this._Usage : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("usage")) {
                  return 10;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 19:
            case 21:
            case 22:
            case 24:
            case 33:
            case 34:
            case 35:
            case 36:
            case 38:
            case 39:
            case 41:
            default:
               break;
            case 11:
               if (s.equals("ciphersuite")) {
                  return 22;
               }
               break;
            case 17:
               if (s.equals("hostname-verifier")) {
                  return 19;
               }
               break;
            case 18:
               if (s.equals("two-wayssl-enabled")) {
                  return 20;
               }
               break;
            case 20:
               if (s.equals("trust-key-store-type")) {
                  return 27;
               }

               if (s.equals("ss-lv2-hello-enabled")) {
                  return 31;
               }
               break;
            case 23:
               if (s.equals("identity-key-store-type")) {
                  return 17;
               }
               break;
            case 25:
               if (s.equals("trust-key-store-file-name")) {
                  return 24;
               }
               break;
            case 26:
               if (s.equals("identity-private-key-alias")) {
                  return 11;
               }
               break;
            case 27:
               if (s.equals("minimumtls-protocol-version")) {
                  return 30;
               }

               if (s.equals("trust-key-store-pass-phrase")) {
                  return 25;
               }

               if (s.equals("client-certificate-enforced")) {
                  return 21;
               }
               break;
            case 28:
               if (s.equals("identity-key-store-file-name")) {
                  return 14;
               }
               break;
            case 29:
               if (s.equals("allow-unencrypted-null-cipher")) {
                  return 23;
               }

               if (s.equals("hostname-verification-ignored")) {
                  return 18;
               }
               break;
            case 30:
               if (s.equals("identity-key-store-pass-phrase")) {
                  return 15;
               }

               if (s.equals("inbound-certificate-validation")) {
                  return 28;
               }
               break;
            case 31:
               if (s.equals("outbound-certificate-validation")) {
                  return 29;
               }
               break;
            case 32:
               if (s.equals("identity-private-key-pass-phrase")) {
                  return 12;
               }
               break;
            case 37:
               if (s.equals("trust-key-store-pass-phrase-encrypted")) {
                  return 26;
               }
               break;
            case 40:
               if (s.equals("identity-key-store-pass-phrase-encrypted")) {
                  return 16;
               }
               break;
            case 42:
               if (s.equals("identity-private-key-pass-phrase-encrypted")) {
                  return 13;
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
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
               return super.getElementName(propIndex);
            case 10:
               return "usage";
            case 11:
               return "identity-private-key-alias";
            case 12:
               return "identity-private-key-pass-phrase";
            case 13:
               return "identity-private-key-pass-phrase-encrypted";
            case 14:
               return "identity-key-store-file-name";
            case 15:
               return "identity-key-store-pass-phrase";
            case 16:
               return "identity-key-store-pass-phrase-encrypted";
            case 17:
               return "identity-key-store-type";
            case 18:
               return "hostname-verification-ignored";
            case 19:
               return "hostname-verifier";
            case 20:
               return "two-wayssl-enabled";
            case 21:
               return "client-certificate-enforced";
            case 22:
               return "ciphersuite";
            case 23:
               return "allow-unencrypted-null-cipher";
            case 24:
               return "trust-key-store-file-name";
            case 25:
               return "trust-key-store-pass-phrase";
            case 26:
               return "trust-key-store-pass-phrase-encrypted";
            case 27:
               return "trust-key-store-type";
            case 28:
               return "inbound-certificate-validation";
            case 29:
               return "outbound-certificate-validation";
            case 30:
               return "minimumtls-protocol-version";
            case 31:
               return "ss-lv2-hello-enabled";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 22:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private TLSMBeanImpl bean;

      protected Helper(TLSMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
               return super.getPropertyName(propIndex);
            case 10:
               return "Usage";
            case 11:
               return "IdentityPrivateKeyAlias";
            case 12:
               return "IdentityPrivateKeyPassPhrase";
            case 13:
               return "IdentityPrivateKeyPassPhraseEncrypted";
            case 14:
               return "IdentityKeyStoreFileName";
            case 15:
               return "IdentityKeyStorePassPhrase";
            case 16:
               return "IdentityKeyStorePassPhraseEncrypted";
            case 17:
               return "IdentityKeyStoreType";
            case 18:
               return "HostnameVerificationIgnored";
            case 19:
               return "HostnameVerifier";
            case 20:
               return "TwoWaySSLEnabled";
            case 21:
               return "ClientCertificateEnforced";
            case 22:
               return "Ciphersuites";
            case 23:
               return "AllowUnencryptedNullCipher";
            case 24:
               return "TrustKeyStoreFileName";
            case 25:
               return "TrustKeyStorePassPhrase";
            case 26:
               return "TrustKeyStorePassPhraseEncrypted";
            case 27:
               return "TrustKeyStoreType";
            case 28:
               return "InboundCertificateValidation";
            case 29:
               return "OutboundCertificateValidation";
            case 30:
               return "MinimumTLSProtocolVersion";
            case 31:
               return "SSLv2HelloEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Ciphersuites")) {
            return 22;
         } else if (propName.equals("HostnameVerifier")) {
            return 19;
         } else if (propName.equals("IdentityKeyStoreFileName")) {
            return 14;
         } else if (propName.equals("IdentityKeyStorePassPhrase")) {
            return 15;
         } else if (propName.equals("IdentityKeyStorePassPhraseEncrypted")) {
            return 16;
         } else if (propName.equals("IdentityKeyStoreType")) {
            return 17;
         } else if (propName.equals("IdentityPrivateKeyAlias")) {
            return 11;
         } else if (propName.equals("IdentityPrivateKeyPassPhrase")) {
            return 12;
         } else if (propName.equals("IdentityPrivateKeyPassPhraseEncrypted")) {
            return 13;
         } else if (propName.equals("InboundCertificateValidation")) {
            return 28;
         } else if (propName.equals("MinimumTLSProtocolVersion")) {
            return 30;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OutboundCertificateValidation")) {
            return 29;
         } else if (propName.equals("TrustKeyStoreFileName")) {
            return 24;
         } else if (propName.equals("TrustKeyStorePassPhrase")) {
            return 25;
         } else if (propName.equals("TrustKeyStorePassPhraseEncrypted")) {
            return 26;
         } else if (propName.equals("TrustKeyStoreType")) {
            return 27;
         } else if (propName.equals("Usage")) {
            return 10;
         } else if (propName.equals("AllowUnencryptedNullCipher")) {
            return 23;
         } else if (propName.equals("ClientCertificateEnforced")) {
            return 21;
         } else if (propName.equals("HostnameVerificationIgnored")) {
            return 18;
         } else if (propName.equals("SSLv2HelloEnabled")) {
            return 31;
         } else {
            return propName.equals("TwoWaySSLEnabled") ? 20 : super.getPropertyIndex(propName);
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
            if (this.bean.isCiphersuitesSet()) {
               buf.append("Ciphersuites");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCiphersuites())));
            }

            if (this.bean.isHostnameVerifierSet()) {
               buf.append("HostnameVerifier");
               buf.append(String.valueOf(this.bean.getHostnameVerifier()));
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

            if (this.bean.isIdentityKeyStoreTypeSet()) {
               buf.append("IdentityKeyStoreType");
               buf.append(String.valueOf(this.bean.getIdentityKeyStoreType()));
            }

            if (this.bean.isIdentityPrivateKeyAliasSet()) {
               buf.append("IdentityPrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getIdentityPrivateKeyAlias()));
            }

            if (this.bean.isIdentityPrivateKeyPassPhraseSet()) {
               buf.append("IdentityPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getIdentityPrivateKeyPassPhrase()));
            }

            if (this.bean.isIdentityPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("IdentityPrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIdentityPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isInboundCertificateValidationSet()) {
               buf.append("InboundCertificateValidation");
               buf.append(String.valueOf(this.bean.getInboundCertificateValidation()));
            }

            if (this.bean.isMinimumTLSProtocolVersionSet()) {
               buf.append("MinimumTLSProtocolVersion");
               buf.append(String.valueOf(this.bean.getMinimumTLSProtocolVersion()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOutboundCertificateValidationSet()) {
               buf.append("OutboundCertificateValidation");
               buf.append(String.valueOf(this.bean.getOutboundCertificateValidation()));
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

            if (this.bean.isTrustKeyStoreTypeSet()) {
               buf.append("TrustKeyStoreType");
               buf.append(String.valueOf(this.bean.getTrustKeyStoreType()));
            }

            if (this.bean.isUsageSet()) {
               buf.append("Usage");
               buf.append(String.valueOf(this.bean.getUsage()));
            }

            if (this.bean.isAllowUnencryptedNullCipherSet()) {
               buf.append("AllowUnencryptedNullCipher");
               buf.append(String.valueOf(this.bean.isAllowUnencryptedNullCipher()));
            }

            if (this.bean.isClientCertificateEnforcedSet()) {
               buf.append("ClientCertificateEnforced");
               buf.append(String.valueOf(this.bean.isClientCertificateEnforced()));
            }

            if (this.bean.isHostnameVerificationIgnoredSet()) {
               buf.append("HostnameVerificationIgnored");
               buf.append(String.valueOf(this.bean.isHostnameVerificationIgnored()));
            }

            if (this.bean.isSSLv2HelloEnabledSet()) {
               buf.append("SSLv2HelloEnabled");
               buf.append(String.valueOf(this.bean.isSSLv2HelloEnabled()));
            }

            if (this.bean.isTwoWaySSLEnabledSet()) {
               buf.append("TwoWaySSLEnabled");
               buf.append(String.valueOf(this.bean.isTwoWaySSLEnabled()));
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
            TLSMBeanImpl otherTyped = (TLSMBeanImpl)other;
            this.computeDiff("Ciphersuites", this.bean.getCiphersuites(), otherTyped.getCiphersuites(), true);
            this.computeDiff("HostnameVerifier", this.bean.getHostnameVerifier(), otherTyped.getHostnameVerifier(), true);
            this.computeDiff("IdentityKeyStoreFileName", this.bean.getIdentityKeyStoreFileName(), otherTyped.getIdentityKeyStoreFileName(), true);
            this.computeDiff("IdentityKeyStorePassPhraseEncrypted", this.bean.getIdentityKeyStorePassPhraseEncrypted(), otherTyped.getIdentityKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("IdentityKeyStoreType", this.bean.getIdentityKeyStoreType(), otherTyped.getIdentityKeyStoreType(), true);
            this.computeDiff("IdentityPrivateKeyAlias", this.bean.getIdentityPrivateKeyAlias(), otherTyped.getIdentityPrivateKeyAlias(), true);
            this.computeDiff("IdentityPrivateKeyPassPhraseEncrypted", this.bean.getIdentityPrivateKeyPassPhraseEncrypted(), otherTyped.getIdentityPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("InboundCertificateValidation", this.bean.getInboundCertificateValidation(), otherTyped.getInboundCertificateValidation(), true);
            this.computeDiff("MinimumTLSProtocolVersion", this.bean.getMinimumTLSProtocolVersion(), otherTyped.getMinimumTLSProtocolVersion(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("OutboundCertificateValidation", this.bean.getOutboundCertificateValidation(), otherTyped.getOutboundCertificateValidation(), true);
            this.computeDiff("TrustKeyStoreFileName", this.bean.getTrustKeyStoreFileName(), otherTyped.getTrustKeyStoreFileName(), true);
            this.computeDiff("TrustKeyStorePassPhraseEncrypted", this.bean.getTrustKeyStorePassPhraseEncrypted(), otherTyped.getTrustKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("TrustKeyStoreType", this.bean.getTrustKeyStoreType(), otherTyped.getTrustKeyStoreType(), true);
            this.computeDiff("Usage", this.bean.getUsage(), otherTyped.getUsage(), false);
            this.computeDiff("AllowUnencryptedNullCipher", this.bean.isAllowUnencryptedNullCipher(), otherTyped.isAllowUnencryptedNullCipher(), true);
            this.computeDiff("ClientCertificateEnforced", this.bean.isClientCertificateEnforced(), otherTyped.isClientCertificateEnforced(), true);
            this.computeDiff("HostnameVerificationIgnored", this.bean.isHostnameVerificationIgnored(), otherTyped.isHostnameVerificationIgnored(), true);
            this.computeDiff("SSLv2HelloEnabled", this.bean.isSSLv2HelloEnabled(), otherTyped.isSSLv2HelloEnabled(), true);
            this.computeDiff("TwoWaySSLEnabled", this.bean.isTwoWaySSLEnabled(), otherTyped.isTwoWaySSLEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TLSMBeanImpl original = (TLSMBeanImpl)event.getSourceBean();
            TLSMBeanImpl proposed = (TLSMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Ciphersuites")) {
                  original.setCiphersuites(proposed.getCiphersuites());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("HostnameVerifier")) {
                  original.setHostnameVerifier(proposed.getHostnameVerifier());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("IdentityKeyStoreFileName")) {
                  original.setIdentityKeyStoreFileName(proposed.getIdentityKeyStoreFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (!prop.equals("IdentityKeyStorePassPhrase")) {
                  if (prop.equals("IdentityKeyStorePassPhraseEncrypted")) {
                     original.setIdentityKeyStorePassPhraseEncrypted(proposed.getIdentityKeyStorePassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("IdentityKeyStoreType")) {
                     original.setIdentityKeyStoreType(proposed.getIdentityKeyStoreType());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("IdentityPrivateKeyAlias")) {
                     original.setIdentityPrivateKeyAlias(proposed.getIdentityPrivateKeyAlias());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (!prop.equals("IdentityPrivateKeyPassPhrase")) {
                     if (prop.equals("IdentityPrivateKeyPassPhraseEncrypted")) {
                        original.setIdentityPrivateKeyPassPhraseEncrypted(proposed.getIdentityPrivateKeyPassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     } else if (prop.equals("InboundCertificateValidation")) {
                        original.setInboundCertificateValidation(proposed.getInboundCertificateValidation());
                        original._conditionalUnset(update.isUnsetUpdate(), 28);
                     } else if (prop.equals("MinimumTLSProtocolVersion")) {
                        original.setMinimumTLSProtocolVersion(proposed.getMinimumTLSProtocolVersion());
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("OutboundCertificateValidation")) {
                        original.setOutboundCertificateValidation(proposed.getOutboundCertificateValidation());
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("TrustKeyStoreFileName")) {
                        original.setTrustKeyStoreFileName(proposed.getTrustKeyStoreFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (!prop.equals("TrustKeyStorePassPhrase")) {
                        if (prop.equals("TrustKeyStorePassPhraseEncrypted")) {
                           original.setTrustKeyStorePassPhraseEncrypted(proposed.getTrustKeyStorePassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 26);
                        } else if (prop.equals("TrustKeyStoreType")) {
                           original.setTrustKeyStoreType(proposed.getTrustKeyStoreType());
                           original._conditionalUnset(update.isUnsetUpdate(), 27);
                        } else if (prop.equals("Usage")) {
                           original.setUsage(proposed.getUsage());
                           original._conditionalUnset(update.isUnsetUpdate(), 10);
                        } else if (prop.equals("AllowUnencryptedNullCipher")) {
                           original.setAllowUnencryptedNullCipher(proposed.isAllowUnencryptedNullCipher());
                           original._conditionalUnset(update.isUnsetUpdate(), 23);
                        } else if (prop.equals("ClientCertificateEnforced")) {
                           original.setClientCertificateEnforced(proposed.isClientCertificateEnforced());
                           original._conditionalUnset(update.isUnsetUpdate(), 21);
                        } else if (prop.equals("HostnameVerificationIgnored")) {
                           original.setHostnameVerificationIgnored(proposed.isHostnameVerificationIgnored());
                           original._conditionalUnset(update.isUnsetUpdate(), 18);
                        } else if (prop.equals("SSLv2HelloEnabled")) {
                           original.setSSLv2HelloEnabled(proposed.isSSLv2HelloEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 31);
                        } else if (prop.equals("TwoWaySSLEnabled")) {
                           original.setTwoWaySSLEnabled(proposed.isTwoWaySSLEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
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
            TLSMBeanImpl copy = (TLSMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Ciphersuites")) && this.bean.isCiphersuitesSet()) {
               Object o = this.bean.getCiphersuites();
               copy.setCiphersuites(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerifier")) && this.bean.isHostnameVerifierSet()) {
               copy.setHostnameVerifier(this.bean.getHostnameVerifier());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityKeyStoreFileName")) && this.bean.isIdentityKeyStoreFileNameSet()) {
               copy.setIdentityKeyStoreFileName(this.bean.getIdentityKeyStoreFileName());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("IdentityKeyStorePassPhraseEncrypted")) && this.bean.isIdentityKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getIdentityKeyStorePassPhraseEncrypted();
               copy.setIdentityKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityKeyStoreType")) && this.bean.isIdentityKeyStoreTypeSet()) {
               copy.setIdentityKeyStoreType(this.bean.getIdentityKeyStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityPrivateKeyAlias")) && this.bean.isIdentityPrivateKeyAliasSet()) {
               copy.setIdentityPrivateKeyAlias(this.bean.getIdentityPrivateKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityPrivateKeyPassPhraseEncrypted")) && this.bean.isIdentityPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getIdentityPrivateKeyPassPhraseEncrypted();
               copy.setIdentityPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("InboundCertificateValidation")) && this.bean.isInboundCertificateValidationSet()) {
               copy.setInboundCertificateValidation(this.bean.getInboundCertificateValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumTLSProtocolVersion")) && this.bean.isMinimumTLSProtocolVersionSet()) {
               copy.setMinimumTLSProtocolVersion(this.bean.getMinimumTLSProtocolVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundCertificateValidation")) && this.bean.isOutboundCertificateValidationSet()) {
               copy.setOutboundCertificateValidation(this.bean.getOutboundCertificateValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustKeyStoreFileName")) && this.bean.isTrustKeyStoreFileNameSet()) {
               copy.setTrustKeyStoreFileName(this.bean.getTrustKeyStoreFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustKeyStorePassPhraseEncrypted")) && this.bean.isTrustKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getTrustKeyStorePassPhraseEncrypted();
               copy.setTrustKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TrustKeyStoreType")) && this.bean.isTrustKeyStoreTypeSet()) {
               copy.setTrustKeyStoreType(this.bean.getTrustKeyStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("Usage")) && this.bean.isUsageSet()) {
               copy.setUsage(this.bean.getUsage());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowUnencryptedNullCipher")) && this.bean.isAllowUnencryptedNullCipherSet()) {
               copy.setAllowUnencryptedNullCipher(this.bean.isAllowUnencryptedNullCipher());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertificateEnforced")) && this.bean.isClientCertificateEnforcedSet()) {
               copy.setClientCertificateEnforced(this.bean.isClientCertificateEnforced());
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerificationIgnored")) && this.bean.isHostnameVerificationIgnoredSet()) {
               copy.setHostnameVerificationIgnored(this.bean.isHostnameVerificationIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLv2HelloEnabled")) && this.bean.isSSLv2HelloEnabledSet()) {
               copy.setSSLv2HelloEnabled(this.bean.isSSLv2HelloEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TwoWaySSLEnabled")) && this.bean.isTwoWaySSLEnabledSet()) {
               copy.setTwoWaySSLEnabled(this.bean.isTwoWaySSLEnabled());
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
