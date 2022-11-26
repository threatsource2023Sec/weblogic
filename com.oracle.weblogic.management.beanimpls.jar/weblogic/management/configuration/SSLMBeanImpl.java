package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SSL;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SSLMBeanImpl extends ConfigurationMBeanImpl implements SSLMBean, Serializable {
   private boolean _AcceptKSSDemoCertsEnabled;
   private boolean _AllowUnencryptedNullCipher;
   private String _CertAuthenticator;
   private int _CertificateCacheSize;
   private String[] _Ciphersuites;
   private String _ClientCertAlias;
   private String _ClientCertPrivateKeyPassPhrase;
   private byte[] _ClientCertPrivateKeyPassPhraseEncrypted;
   private boolean _ClientCertificateEnforced;
   private boolean _ClientInitSecureRenegotiationAccepted;
   private boolean _DynamicallyCreated;
   private boolean _Enabled;
   private String[] _ExcludedCiphersuites;
   private int _ExportKeyLifespan;
   private boolean _HandlerEnabled;
   private boolean _HostnameVerificationIgnored;
   private String _HostnameVerifier;
   private String _IdentityAndTrustLocations;
   private String _InboundCertificateValidation;
   private boolean _JSSEEnabled;
   private boolean _KeyEncrypted;
   private int _ListenPort;
   private boolean _ListenPortEnabled;
   private int _LoginTimeoutMillis;
   private String _MinimumTLSProtocolVersion;
   private String _Name;
   private String _OutboundCertificateValidation;
   private String _OutboundPrivateKeyAlias;
   private String _OutboundPrivateKeyPassPhrase;
   private byte[] _OutboundPrivateKeyPassPhraseEncrypted;
   private boolean _SSLRejectionLoggingEnabled;
   private boolean _SSLv2HelloEnabled;
   private String _ServerCertificateChainFileName;
   private String _ServerCertificateFileName;
   private String _ServerKeyFileName;
   private String _ServerPrivateKeyAlias;
   private String _ServerPrivateKeyPassPhrase;
   private byte[] _ServerPrivateKeyPassPhraseEncrypted;
   private String[] _Tags;
   private String _TrustedCAFileName;
   private boolean _TwoWaySSLEnabled;
   private boolean _UseClientCertForOutbound;
   private boolean _UseJava;
   private boolean _UseServerCerts;
   private transient SSL _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private SSLMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(SSLMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(SSLMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public SSLMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(SSLMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      SSLMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public SSLMBeanImpl() {
      try {
         this._customizer = new SSL(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SSLMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SSL(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SSLMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SSL(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isUseJava() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().isUseJava() : this._UseJava;
   }

   public boolean isUseJavaInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isUseJavaSet() {
      return this._isSet(10);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setUseJava(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      boolean _oldVal = this._UseJava;
      this._UseJava = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isEnabled() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._getDelegateBean().isEnabled();
      } else if (!this._isSet(11)) {
         return this._isSecureModeEnabled();
      } else {
         return this._Enabled;
      }
   }

   public boolean isEnabledInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isEnabledSet() {
      return this._isSet(11);
   }

   public void setEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getCiphersuites() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getCiphersuites() : this._Ciphersuites;
   }

   public boolean isCiphersuitesInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isCiphersuitesSet() {
      return this._isSet(12);
   }

   public void setCiphersuites(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String[] _oldVal = this._Ciphersuites;
      this._Ciphersuites = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getExcludedCiphersuites() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._getDelegateBean().getExcludedCiphersuites();
      } else {
         if (!this._isSet(13)) {
            try {
               return ServerLegalHelper.getDerivedExcludedCiphersuites(this.getMinimumTLSProtocolVersion());
            } catch (NullPointerException var2) {
            }
         }

         return this._ExcludedCiphersuites;
      }
   }

   public boolean isExcludedCiphersuitesInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isExcludedCiphersuitesSet() {
      return this._isSet(13);
   }

   public void setExcludedCiphersuites(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String[] _oldVal = this._ExcludedCiphersuites;
      this._ExcludedCiphersuites = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCertAuthenticator() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getCertAuthenticator(), this) : this._CertAuthenticator;
   }

   public boolean isCertAuthenticatorInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isCertAuthenticatorSet() {
      return this._isSet(14);
   }

   public void setCertAuthenticator(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._CertAuthenticator;
      this._CertAuthenticator = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getHostnameVerifier() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getHostnameVerifier(), this) : this._HostnameVerifier;
   }

   public boolean isHostnameVerifierInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isHostnameVerifierSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setHostnameVerifier(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      String _oldVal = this._HostnameVerifier;
      this._HostnameVerifier = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHostnameVerificationIgnored() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().isHostnameVerificationIgnored() : this._HostnameVerificationIgnored;
   }

   public boolean isHostnameVerificationIgnoredInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isHostnameVerificationIgnoredSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setHostnameVerificationIgnored(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._HostnameVerificationIgnored;
      this._HostnameVerificationIgnored = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTrustedCAFileName() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getTrustedCAFileName(), this) : this._TrustedCAFileName;
   }

   public boolean isTrustedCAFileNameInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isTrustedCAFileNameSet() {
      return this._isSet(17);
   }

   public void setTrustedCAFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._TrustedCAFileName;
      this._TrustedCAFileName = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isKeyEncrypted() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().isKeyEncrypted() : this._KeyEncrypted;
   }

   public boolean isKeyEncryptedInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isKeyEncryptedSet() {
      return this._isSet(18);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setKeyEncrypted(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      boolean _oldVal = this._KeyEncrypted;
      this._KeyEncrypted = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAcceptKSSDemoCertsEnabled() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().isAcceptKSSDemoCertsEnabled() : this._AcceptKSSDemoCertsEnabled;
   }

   public boolean isAcceptKSSDemoCertsEnabledInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isAcceptKSSDemoCertsEnabledSet() {
      return this._isSet(19);
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setAcceptKSSDemoCertsEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._AcceptKSSDemoCertsEnabled;
      this._AcceptKSSDemoCertsEnabled = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public int getExportKeyLifespan() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getExportKeyLifespan() : this._ExportKeyLifespan;
   }

   public boolean isExportKeyLifespanInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isExportKeyLifespanSet() {
      return this._isSet(20);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setExportKeyLifespan(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ExportKeyLifespan", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(20);
      int _oldVal = this._ExportKeyLifespan;
      this._ExportKeyLifespan = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClientCertificateEnforced() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().isClientCertificateEnforced() : this._ClientCertificateEnforced;
   }

   public boolean isClientCertificateEnforcedInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isClientCertificateEnforcedSet() {
      return this._isSet(21);
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setClientCertificateEnforced(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._ClientCertificateEnforced;
      this._ClientCertificateEnforced = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServerCertificateFileName() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getServerCertificateFileName(), this) : this._ServerCertificateFileName;
   }

   public boolean isServerCertificateFileNameInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isServerCertificateFileNameSet() {
      return this._isSet(22);
   }

   public void setServerCertificateFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._ServerCertificateFileName;
      this._ServerCertificateFileName = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public int getListenPort() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getListenPort() : this._ListenPort;
   }

   public boolean isListenPortInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isListenPortSet() {
      return this._isSet(23);
   }

   public void setListenPort(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ListenPort", (long)param0, 1L, 65535L);
      boolean wasSet = this._isSet(23);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isListenPortEnabled() {
      return this._customizer.isListenPortEnabled();
   }

   public boolean isListenPortEnabledInherited() {
      return false;
   }

   public boolean isListenPortEnabledSet() {
      return this._isSet(24);
   }

   public void setListenPortEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ListenPortEnabled = param0;
   }

   public String getServerCertificateChainFileName() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getServerCertificateChainFileName(), this) : this._ServerCertificateChainFileName;
   }

   public boolean isServerCertificateChainFileNameInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isServerCertificateChainFileNameSet() {
      return this._isSet(25);
   }

   public void setServerCertificateChainFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      String _oldVal = this._ServerCertificateChainFileName;
      this._ServerCertificateChainFileName = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCertificateCacheSize() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getCertificateCacheSize() : this._CertificateCacheSize;
   }

   public boolean isCertificateCacheSizeInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isCertificateCacheSizeSet() {
      return this._isSet(26);
   }

   public void setCertificateCacheSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CertificateCacheSize", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(26);
      int _oldVal = this._CertificateCacheSize;
      this._CertificateCacheSize = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHandlerEnabled() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isHandlerEnabled() : this._HandlerEnabled;
   }

   public boolean isHandlerEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isHandlerEnabledSet() {
      return this._isSet(27);
   }

   public void setHandlerEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._HandlerEnabled;
      this._HandlerEnabled = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoginTimeoutMillis() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getLoginTimeoutMillis() : this._LoginTimeoutMillis;
   }

   public boolean isLoginTimeoutMillisInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isLoginTimeoutMillisSet() {
      return this._isSet(28);
   }

   public void setLoginTimeoutMillis(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LoginTimeoutMillis", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(28);
      int _oldVal = this._LoginTimeoutMillis;
      this._LoginTimeoutMillis = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServerKeyFileName() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._performMacroSubstitution(this._getDelegateBean().getServerKeyFileName(), this) : this._ServerKeyFileName;
   }

   public boolean isServerKeyFileNameInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isServerKeyFileNameSet() {
      return this._isSet(29);
   }

   public void setServerKeyFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      String _oldVal = this._ServerKeyFileName;
      this._ServerKeyFileName = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isTwoWaySSLEnabled() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isTwoWaySSLEnabled() : this._TwoWaySSLEnabled;
   }

   public boolean isTwoWaySSLEnabledInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isTwoWaySSLEnabledSet() {
      return this._isSet(30);
   }

   public void setTwoWaySSLEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._TwoWaySSLEnabled;
      this._TwoWaySSLEnabled = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServerPrivateKeyAlias() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._performMacroSubstitution(this._getDelegateBean().getServerPrivateKeyAlias(), this) : this._ServerPrivateKeyAlias;
   }

   public boolean isServerPrivateKeyAliasInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isServerPrivateKeyAliasSet() {
      return this._isSet(31);
   }

   public void setServerPrivateKeyAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      String _oldVal = this._ServerPrivateKeyAlias;
      this._ServerPrivateKeyAlias = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServerPrivateKeyPassPhrase() {
      if (!this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32)) {
         return this._performMacroSubstitution(this._getDelegateBean().getServerPrivateKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getServerPrivateKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("ServerPrivateKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isServerPrivateKeyPassPhraseInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isServerPrivateKeyPassPhraseSet() {
      return this.isServerPrivateKeyPassPhraseEncryptedSet();
   }

   public void setServerPrivateKeyPassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setServerPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("ServerPrivateKeyPassPhrase", param0));
   }

   public byte[] getServerPrivateKeyPassPhraseEncrypted() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getServerPrivateKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._ServerPrivateKeyPassPhraseEncrypted);
   }

   public String getServerPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getServerPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isServerPrivateKeyPassPhraseEncryptedInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isServerPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(33);
   }

   public void setServerPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setServerPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isSSLRejectionLoggingEnabled() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().isSSLRejectionLoggingEnabled() : this._SSLRejectionLoggingEnabled;
   }

   public boolean isSSLRejectionLoggingEnabledInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isSSLRejectionLoggingEnabledSet() {
      return this._isSet(34);
   }

   public void setSSLRejectionLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      boolean _oldVal = this._SSLRejectionLoggingEnabled;
      this._SSLRejectionLoggingEnabled = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public String getIdentityAndTrustLocations() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._performMacroSubstitution(this._getDelegateBean().getIdentityAndTrustLocations(), this) : this._IdentityAndTrustLocations;
   }

   public boolean isIdentityAndTrustLocationsInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isIdentityAndTrustLocationsSet() {
      return this._isSet(35);
   }

   public void setIdentityAndTrustLocations(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"KeyStores", "FilesOrKeyStoreProviders"};
      param0 = LegalChecks.checkInEnum("IdentityAndTrustLocations", param0, _set);
      boolean wasSet = this._isSet(35);
      String _oldVal = this._IdentityAndTrustLocations;
      this._IdentityAndTrustLocations = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var5.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInboundCertificateValidation() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._performMacroSubstitution(this._getDelegateBean().getInboundCertificateValidation(), this) : this._InboundCertificateValidation;
   }

   public boolean isInboundCertificateValidationInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isInboundCertificateValidationSet() {
      return this._isSet(36);
   }

   public void setInboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("InboundCertificateValidation", param0, _set);
      boolean wasSet = this._isSet(36);
      String _oldVal = this._InboundCertificateValidation;
      this._InboundCertificateValidation = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var5.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutboundCertificateValidation() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._performMacroSubstitution(this._getDelegateBean().getOutboundCertificateValidation(), this) : this._OutboundCertificateValidation;
   }

   public boolean isOutboundCertificateValidationInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isOutboundCertificateValidationSet() {
      return this._isSet(37);
   }

   public void setOutboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("OutboundCertificateValidation", param0, _set);
      boolean wasSet = this._isSet(37);
      String _oldVal = this._OutboundCertificateValidation;
      this._OutboundCertificateValidation = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var5.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public void setAllowUnencryptedNullCipher(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(38);
      boolean _oldVal = this._AllowUnencryptedNullCipher;
      this._AllowUnencryptedNullCipher = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAllowUnencryptedNullCipher() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().isAllowUnencryptedNullCipher() : this._AllowUnencryptedNullCipher;
   }

   public boolean isAllowUnencryptedNullCipherInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isAllowUnencryptedNullCipherSet() {
      return this._isSet(38);
   }

   public boolean isUseServerCerts() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().isUseServerCerts() : this._UseServerCerts;
   }

   public boolean isUseServerCertsInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isUseServerCertsSet() {
      return this._isSet(39);
   }

   public void setUseServerCerts(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      boolean _oldVal = this._UseServerCerts;
      this._UseServerCerts = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public void setJSSEEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(40);
      boolean _oldVal = this._JSSEEnabled;
      this._JSSEEnabled = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isJSSEEnabled() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().isJSSEEnabled() : this._JSSEEnabled;
   }

   public boolean isJSSEEnabledInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isJSSEEnabledSet() {
      return this._isSet(40);
   }

   public void setUseClientCertForOutbound(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(41);
      boolean _oldVal = this._UseClientCertForOutbound;
      this._UseClientCertForOutbound = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseClientCertForOutbound() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().isUseClientCertForOutbound() : this._UseClientCertForOutbound;
   }

   public boolean isUseClientCertForOutboundInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isUseClientCertForOutboundSet() {
      return this._isSet(41);
   }

   public void setClientCertAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(42);
      String _oldVal = this._ClientCertAlias;
      this._ClientCertAlias = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public String getClientCertAlias() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._performMacroSubstitution(this._getDelegateBean().getClientCertAlias(), this) : this._ClientCertAlias;
   }

   public boolean isClientCertAliasInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isClientCertAliasSet() {
      return this._isSet(42);
   }

   public String getClientCertPrivateKeyPassPhrase() {
      if (!this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43)) {
         return this._performMacroSubstitution(this._getDelegateBean().getClientCertPrivateKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getClientCertPrivateKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("ClientCertPrivateKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isClientCertPrivateKeyPassPhraseInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isClientCertPrivateKeyPassPhraseSet() {
      return this.isClientCertPrivateKeyPassPhraseEncryptedSet();
   }

   public void setClientCertPrivateKeyPassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setClientCertPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("ClientCertPrivateKeyPassPhrase", param0));
   }

   public byte[] getClientCertPrivateKeyPassPhraseEncrypted() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._getDelegateBean().getClientCertPrivateKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._ClientCertPrivateKeyPassPhraseEncrypted);
   }

   public String getClientCertPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getClientCertPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isClientCertPrivateKeyPassPhraseEncryptedInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isClientCertPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(44);
   }

   public void setClientCertPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setClientCertPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getOutboundPrivateKeyAlias() {
      if (!this._isSet(45)) {
         try {
            return this.isUseClientCertForOutbound() ? this.getClientCertAlias() : this.getServerPrivateKeyAlias();
         } catch (NullPointerException var2) {
         }
      }

      return this._OutboundPrivateKeyAlias;
   }

   public boolean isOutboundPrivateKeyAliasInherited() {
      return false;
   }

   public boolean isOutboundPrivateKeyAliasSet() {
      return this._isSet(45);
   }

   public void setOutboundPrivateKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      String _oldVal = this._OutboundPrivateKeyAlias;
      this._OutboundPrivateKeyAlias = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutboundPrivateKeyPassPhrase() {
      if (!this._isSet(46)) {
         try {
            return this.isUseClientCertForOutbound() ? this.getClientCertPrivateKeyPassPhrase() : this.getServerPrivateKeyPassPhrase();
         } catch (NullPointerException var2) {
         }
      }

      byte[] bEncrypted = this.getOutboundPrivateKeyPassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("OutboundPrivateKeyPassPhrase", bEncrypted);
   }

   public boolean isOutboundPrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isOutboundPrivateKeyPassPhraseSet() {
      return this.isOutboundPrivateKeyPassPhraseEncryptedSet();
   }

   public void setOutboundPrivateKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setOutboundPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("OutboundPrivateKeyPassPhrase", param0));
   }

   public byte[] getOutboundPrivateKeyPassPhraseEncrypted() {
      if (!this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47)) {
         return this._getDelegateBean().getOutboundPrivateKeyPassPhraseEncrypted();
      } else {
         if (!this._isSet(47)) {
            try {
               return this.isUseClientCertForOutbound() ? this.getClientCertPrivateKeyPassPhraseEncrypted() : this.getServerPrivateKeyPassPhraseEncrypted();
            } catch (NullPointerException var2) {
            }
         }

         return this._getHelper()._cloneArray(this._OutboundPrivateKeyPassPhraseEncrypted);
      }
   }

   public String getOutboundPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getOutboundPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isOutboundPrivateKeyPassPhraseEncryptedInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isOutboundPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(47);
   }

   public void setOutboundPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setOutboundPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setOutboundPrivateKeyPassPhraseEncrypted(byte[] param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(47);
      byte[] _oldVal = this._OutboundPrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: OutboundPrivateKeyPassPhraseEncrypted of SSLMBean");
      } else {
         this._getHelper()._clearArray(this._OutboundPrivateKeyPassPhraseEncrypted);
         this._OutboundPrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(47, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
            if (source != null && !source._isSet(47)) {
               source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public String getMinimumTLSProtocolVersion() {
      if (!this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48)) {
         return this._performMacroSubstitution(this._getDelegateBean().getMinimumTLSProtocolVersion(), this);
      } else {
         if (!this._isSet(48)) {
            try {
               return ServerLegalHelper.getDerivedMinimumTLSProtocol();
            } catch (NullPointerException var2) {
            }
         }

         return this._MinimumTLSProtocolVersion;
      }
   }

   public boolean isMinimumTLSProtocolVersionInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isMinimumTLSProtocolVersionSet() {
      return this._isSet(48);
   }

   public void setMinimumTLSProtocolVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ServerLegalHelper.validateMinimumSSLProtocol(param0);
      boolean wasSet = this._isSet(48);
      String _oldVal = this._MinimumTLSProtocolVersion;
      this._MinimumTLSProtocolVersion = param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSSLv2HelloEnabled() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().isSSLv2HelloEnabled() : this._SSLv2HelloEnabled;
   }

   public boolean isSSLv2HelloEnabledInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isSSLv2HelloEnabledSet() {
      return this._isSet(49);
   }

   public void setSSLv2HelloEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(49);
      boolean _oldVal = this._SSLv2HelloEnabled;
      this._SSLv2HelloEnabled = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().isClientInitSecureRenegotiationAccepted() : this._ClientInitSecureRenegotiationAccepted;
   }

   public boolean isClientInitSecureRenegotiationAcceptedInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isClientInitSecureRenegotiationAcceptedSet() {
      return this._isSet(50);
   }

   public void setClientInitSecureRenegotiationAccepted(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(50);
      boolean _oldVal = this._ClientInitSecureRenegotiationAccepted;
      this._ClientInitSecureRenegotiationAccepted = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ServerLegalHelper.validateSSL(this);
   }

   public void setClientCertPrivateKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(44);
      byte[] _oldVal = this._ClientCertPrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: ClientCertPrivateKeyPassPhraseEncrypted of SSLMBean");
      } else {
         this._getHelper()._clearArray(this._ClientCertPrivateKeyPassPhraseEncrypted);
         this._ClientCertPrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(44, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
            if (source != null && !source._isSet(44)) {
               source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setServerPrivateKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      byte[] _oldVal = this._ServerPrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: ServerPrivateKeyPassPhraseEncrypted of SSLMBean");
      } else {
         this._getHelper()._clearArray(this._ServerPrivateKeyPassPhraseEncrypted);
         this._ServerPrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(33, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SSLMBeanImpl source = (SSLMBeanImpl)var4.next();
            if (source != null && !source._isSet(33)) {
               source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
            }
         }

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
         if (idx == 43) {
            this._markSet(44, false);
         }

         if (idx == 46) {
            this._markSet(47, false);
         }

         if (idx == 32) {
            this._markSet(33, false);
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
               this._CertAuthenticator = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._CertificateCacheSize = 3;
               if (initOne) {
                  break;
               }
            case 12:
               this._Ciphersuites = new String[0];
               if (initOne) {
                  break;
               }
            case 42:
               this._ClientCertAlias = null;
               if (initOne) {
                  break;
               }
            case 43:
               this._ClientCertPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._ClientCertPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ExcludedCiphersuites = new String[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ExportKeyLifespan = 500;
               if (initOne) {
                  break;
               }
            case 15:
               this._HostnameVerifier = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._IdentityAndTrustLocations = "KeyStores";
               if (initOne) {
                  break;
               }
            case 36:
               this._InboundCertificateValidation = "BuiltinSSLValidationOnly";
               if (initOne) {
                  break;
               }
            case 23:
               this._ListenPort = 7002;
               if (initOne) {
                  break;
               }
            case 28:
               this._LoginTimeoutMillis = 25000;
               if (initOne) {
                  break;
               }
            case 48:
               this._MinimumTLSProtocolVersion = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 37:
               this._OutboundCertificateValidation = "BuiltinSSLValidationOnly";
               if (initOne) {
                  break;
               }
            case 45:
               this._OutboundPrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 46:
               this._OutboundPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._OutboundPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ServerCertificateChainFileName = "server-certchain.pem";
               if (initOne) {
                  break;
               }
            case 22:
               this._ServerCertificateFileName = "server-cert.der";
               if (initOne) {
                  break;
               }
            case 29:
               this._ServerKeyFileName = "server-key.der";
               if (initOne) {
                  break;
               }
            case 31:
               this._ServerPrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._ServerPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._ServerPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 17:
               this._TrustedCAFileName = "trusted-ca.pem";
               if (initOne) {
                  break;
               }
            case 19:
               this._AcceptKSSDemoCertsEnabled = true;
               if (initOne) {
                  break;
               }
            case 38:
               this._AllowUnencryptedNullCipher = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._ClientCertificateEnforced = false;
               if (initOne) {
                  break;
               }
            case 50:
               this._ClientInitSecureRenegotiationAccepted = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._HandlerEnabled = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._HostnameVerificationIgnored = false;
               if (initOne) {
                  break;
               }
            case 40:
               this._JSSEEnabled = true;
               if (initOne) {
                  break;
               }
            case 18:
               this._KeyEncrypted = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._ListenPortEnabled = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._SSLRejectionLoggingEnabled = true;
               if (initOne) {
                  break;
               }
            case 49:
               this._SSLv2HelloEnabled = true;
               if (initOne) {
                  break;
               }
            case 30:
               this._TwoWaySSLEnabled = false;
               if (initOne) {
                  break;
               }
            case 41:
               this._UseClientCertForOutbound = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._UseJava = true;
               if (initOne) {
                  break;
               }
            case 39:
               this._UseServerCerts = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "SSL";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AcceptKSSDemoCertsEnabled")) {
         oldVal = this._AcceptKSSDemoCertsEnabled;
         this._AcceptKSSDemoCertsEnabled = (Boolean)v;
         this._postSet(19, oldVal, this._AcceptKSSDemoCertsEnabled);
      } else if (name.equals("AllowUnencryptedNullCipher")) {
         oldVal = this._AllowUnencryptedNullCipher;
         this._AllowUnencryptedNullCipher = (Boolean)v;
         this._postSet(38, oldVal, this._AllowUnencryptedNullCipher);
      } else {
         String oldVal;
         if (name.equals("CertAuthenticator")) {
            oldVal = this._CertAuthenticator;
            this._CertAuthenticator = (String)v;
            this._postSet(14, oldVal, this._CertAuthenticator);
         } else {
            int oldVal;
            if (name.equals("CertificateCacheSize")) {
               oldVal = this._CertificateCacheSize;
               this._CertificateCacheSize = (Integer)v;
               this._postSet(26, oldVal, this._CertificateCacheSize);
            } else {
               String[] oldVal;
               if (name.equals("Ciphersuites")) {
                  oldVal = this._Ciphersuites;
                  this._Ciphersuites = (String[])((String[])v);
                  this._postSet(12, oldVal, this._Ciphersuites);
               } else if (name.equals("ClientCertAlias")) {
                  oldVal = this._ClientCertAlias;
                  this._ClientCertAlias = (String)v;
                  this._postSet(42, oldVal, this._ClientCertAlias);
               } else if (name.equals("ClientCertPrivateKeyPassPhrase")) {
                  oldVal = this._ClientCertPrivateKeyPassPhrase;
                  this._ClientCertPrivateKeyPassPhrase = (String)v;
                  this._postSet(43, oldVal, this._ClientCertPrivateKeyPassPhrase);
               } else {
                  byte[] oldVal;
                  if (name.equals("ClientCertPrivateKeyPassPhraseEncrypted")) {
                     oldVal = this._ClientCertPrivateKeyPassPhraseEncrypted;
                     this._ClientCertPrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(44, oldVal, this._ClientCertPrivateKeyPassPhraseEncrypted);
                  } else if (name.equals("ClientCertificateEnforced")) {
                     oldVal = this._ClientCertificateEnforced;
                     this._ClientCertificateEnforced = (Boolean)v;
                     this._postSet(21, oldVal, this._ClientCertificateEnforced);
                  } else if (name.equals("ClientInitSecureRenegotiationAccepted")) {
                     oldVal = this._ClientInitSecureRenegotiationAccepted;
                     this._ClientInitSecureRenegotiationAccepted = (Boolean)v;
                     this._postSet(50, oldVal, this._ClientInitSecureRenegotiationAccepted);
                  } else if (name.equals("DynamicallyCreated")) {
                     oldVal = this._DynamicallyCreated;
                     this._DynamicallyCreated = (Boolean)v;
                     this._postSet(7, oldVal, this._DynamicallyCreated);
                  } else if (name.equals("Enabled")) {
                     oldVal = this._Enabled;
                     this._Enabled = (Boolean)v;
                     this._postSet(11, oldVal, this._Enabled);
                  } else if (name.equals("ExcludedCiphersuites")) {
                     oldVal = this._ExcludedCiphersuites;
                     this._ExcludedCiphersuites = (String[])((String[])v);
                     this._postSet(13, oldVal, this._ExcludedCiphersuites);
                  } else if (name.equals("ExportKeyLifespan")) {
                     oldVal = this._ExportKeyLifespan;
                     this._ExportKeyLifespan = (Integer)v;
                     this._postSet(20, oldVal, this._ExportKeyLifespan);
                  } else if (name.equals("HandlerEnabled")) {
                     oldVal = this._HandlerEnabled;
                     this._HandlerEnabled = (Boolean)v;
                     this._postSet(27, oldVal, this._HandlerEnabled);
                  } else if (name.equals("HostnameVerificationIgnored")) {
                     oldVal = this._HostnameVerificationIgnored;
                     this._HostnameVerificationIgnored = (Boolean)v;
                     this._postSet(16, oldVal, this._HostnameVerificationIgnored);
                  } else if (name.equals("HostnameVerifier")) {
                     oldVal = this._HostnameVerifier;
                     this._HostnameVerifier = (String)v;
                     this._postSet(15, oldVal, this._HostnameVerifier);
                  } else if (name.equals("IdentityAndTrustLocations")) {
                     oldVal = this._IdentityAndTrustLocations;
                     this._IdentityAndTrustLocations = (String)v;
                     this._postSet(35, oldVal, this._IdentityAndTrustLocations);
                  } else if (name.equals("InboundCertificateValidation")) {
                     oldVal = this._InboundCertificateValidation;
                     this._InboundCertificateValidation = (String)v;
                     this._postSet(36, oldVal, this._InboundCertificateValidation);
                  } else if (name.equals("JSSEEnabled")) {
                     oldVal = this._JSSEEnabled;
                     this._JSSEEnabled = (Boolean)v;
                     this._postSet(40, oldVal, this._JSSEEnabled);
                  } else if (name.equals("KeyEncrypted")) {
                     oldVal = this._KeyEncrypted;
                     this._KeyEncrypted = (Boolean)v;
                     this._postSet(18, oldVal, this._KeyEncrypted);
                  } else if (name.equals("ListenPort")) {
                     oldVal = this._ListenPort;
                     this._ListenPort = (Integer)v;
                     this._postSet(23, oldVal, this._ListenPort);
                  } else if (name.equals("ListenPortEnabled")) {
                     oldVal = this._ListenPortEnabled;
                     this._ListenPortEnabled = (Boolean)v;
                     this._postSet(24, oldVal, this._ListenPortEnabled);
                  } else if (name.equals("LoginTimeoutMillis")) {
                     oldVal = this._LoginTimeoutMillis;
                     this._LoginTimeoutMillis = (Integer)v;
                     this._postSet(28, oldVal, this._LoginTimeoutMillis);
                  } else if (name.equals("MinimumTLSProtocolVersion")) {
                     oldVal = this._MinimumTLSProtocolVersion;
                     this._MinimumTLSProtocolVersion = (String)v;
                     this._postSet(48, oldVal, this._MinimumTLSProtocolVersion);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("OutboundCertificateValidation")) {
                     oldVal = this._OutboundCertificateValidation;
                     this._OutboundCertificateValidation = (String)v;
                     this._postSet(37, oldVal, this._OutboundCertificateValidation);
                  } else if (name.equals("OutboundPrivateKeyAlias")) {
                     oldVal = this._OutboundPrivateKeyAlias;
                     this._OutboundPrivateKeyAlias = (String)v;
                     this._postSet(45, oldVal, this._OutboundPrivateKeyAlias);
                  } else if (name.equals("OutboundPrivateKeyPassPhrase")) {
                     oldVal = this._OutboundPrivateKeyPassPhrase;
                     this._OutboundPrivateKeyPassPhrase = (String)v;
                     this._postSet(46, oldVal, this._OutboundPrivateKeyPassPhrase);
                  } else if (name.equals("OutboundPrivateKeyPassPhraseEncrypted")) {
                     oldVal = this._OutboundPrivateKeyPassPhraseEncrypted;
                     this._OutboundPrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(47, oldVal, this._OutboundPrivateKeyPassPhraseEncrypted);
                  } else if (name.equals("SSLRejectionLoggingEnabled")) {
                     oldVal = this._SSLRejectionLoggingEnabled;
                     this._SSLRejectionLoggingEnabled = (Boolean)v;
                     this._postSet(34, oldVal, this._SSLRejectionLoggingEnabled);
                  } else if (name.equals("SSLv2HelloEnabled")) {
                     oldVal = this._SSLv2HelloEnabled;
                     this._SSLv2HelloEnabled = (Boolean)v;
                     this._postSet(49, oldVal, this._SSLv2HelloEnabled);
                  } else if (name.equals("ServerCertificateChainFileName")) {
                     oldVal = this._ServerCertificateChainFileName;
                     this._ServerCertificateChainFileName = (String)v;
                     this._postSet(25, oldVal, this._ServerCertificateChainFileName);
                  } else if (name.equals("ServerCertificateFileName")) {
                     oldVal = this._ServerCertificateFileName;
                     this._ServerCertificateFileName = (String)v;
                     this._postSet(22, oldVal, this._ServerCertificateFileName);
                  } else if (name.equals("ServerKeyFileName")) {
                     oldVal = this._ServerKeyFileName;
                     this._ServerKeyFileName = (String)v;
                     this._postSet(29, oldVal, this._ServerKeyFileName);
                  } else if (name.equals("ServerPrivateKeyAlias")) {
                     oldVal = this._ServerPrivateKeyAlias;
                     this._ServerPrivateKeyAlias = (String)v;
                     this._postSet(31, oldVal, this._ServerPrivateKeyAlias);
                  } else if (name.equals("ServerPrivateKeyPassPhrase")) {
                     oldVal = this._ServerPrivateKeyPassPhrase;
                     this._ServerPrivateKeyPassPhrase = (String)v;
                     this._postSet(32, oldVal, this._ServerPrivateKeyPassPhrase);
                  } else if (name.equals("ServerPrivateKeyPassPhraseEncrypted")) {
                     oldVal = this._ServerPrivateKeyPassPhraseEncrypted;
                     this._ServerPrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(33, oldVal, this._ServerPrivateKeyPassPhraseEncrypted);
                  } else if (name.equals("Tags")) {
                     oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("TrustedCAFileName")) {
                     oldVal = this._TrustedCAFileName;
                     this._TrustedCAFileName = (String)v;
                     this._postSet(17, oldVal, this._TrustedCAFileName);
                  } else if (name.equals("TwoWaySSLEnabled")) {
                     oldVal = this._TwoWaySSLEnabled;
                     this._TwoWaySSLEnabled = (Boolean)v;
                     this._postSet(30, oldVal, this._TwoWaySSLEnabled);
                  } else if (name.equals("UseClientCertForOutbound")) {
                     oldVal = this._UseClientCertForOutbound;
                     this._UseClientCertForOutbound = (Boolean)v;
                     this._postSet(41, oldVal, this._UseClientCertForOutbound);
                  } else if (name.equals("UseJava")) {
                     oldVal = this._UseJava;
                     this._UseJava = (Boolean)v;
                     this._postSet(10, oldVal, this._UseJava);
                  } else if (name.equals("UseServerCerts")) {
                     oldVal = this._UseServerCerts;
                     this._UseServerCerts = (Boolean)v;
                     this._postSet(39, oldVal, this._UseServerCerts);
                  } else if (name.equals("customizer")) {
                     SSL oldVal = this._customizer;
                     this._customizer = (SSL)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcceptKSSDemoCertsEnabled")) {
         return new Boolean(this._AcceptKSSDemoCertsEnabled);
      } else if (name.equals("AllowUnencryptedNullCipher")) {
         return new Boolean(this._AllowUnencryptedNullCipher);
      } else if (name.equals("CertAuthenticator")) {
         return this._CertAuthenticator;
      } else if (name.equals("CertificateCacheSize")) {
         return new Integer(this._CertificateCacheSize);
      } else if (name.equals("Ciphersuites")) {
         return this._Ciphersuites;
      } else if (name.equals("ClientCertAlias")) {
         return this._ClientCertAlias;
      } else if (name.equals("ClientCertPrivateKeyPassPhrase")) {
         return this._ClientCertPrivateKeyPassPhrase;
      } else if (name.equals("ClientCertPrivateKeyPassPhraseEncrypted")) {
         return this._ClientCertPrivateKeyPassPhraseEncrypted;
      } else if (name.equals("ClientCertificateEnforced")) {
         return new Boolean(this._ClientCertificateEnforced);
      } else if (name.equals("ClientInitSecureRenegotiationAccepted")) {
         return new Boolean(this._ClientInitSecureRenegotiationAccepted);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("ExcludedCiphersuites")) {
         return this._ExcludedCiphersuites;
      } else if (name.equals("ExportKeyLifespan")) {
         return new Integer(this._ExportKeyLifespan);
      } else if (name.equals("HandlerEnabled")) {
         return new Boolean(this._HandlerEnabled);
      } else if (name.equals("HostnameVerificationIgnored")) {
         return new Boolean(this._HostnameVerificationIgnored);
      } else if (name.equals("HostnameVerifier")) {
         return this._HostnameVerifier;
      } else if (name.equals("IdentityAndTrustLocations")) {
         return this._IdentityAndTrustLocations;
      } else if (name.equals("InboundCertificateValidation")) {
         return this._InboundCertificateValidation;
      } else if (name.equals("JSSEEnabled")) {
         return new Boolean(this._JSSEEnabled);
      } else if (name.equals("KeyEncrypted")) {
         return new Boolean(this._KeyEncrypted);
      } else if (name.equals("ListenPort")) {
         return new Integer(this._ListenPort);
      } else if (name.equals("ListenPortEnabled")) {
         return new Boolean(this._ListenPortEnabled);
      } else if (name.equals("LoginTimeoutMillis")) {
         return new Integer(this._LoginTimeoutMillis);
      } else if (name.equals("MinimumTLSProtocolVersion")) {
         return this._MinimumTLSProtocolVersion;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutboundCertificateValidation")) {
         return this._OutboundCertificateValidation;
      } else if (name.equals("OutboundPrivateKeyAlias")) {
         return this._OutboundPrivateKeyAlias;
      } else if (name.equals("OutboundPrivateKeyPassPhrase")) {
         return this._OutboundPrivateKeyPassPhrase;
      } else if (name.equals("OutboundPrivateKeyPassPhraseEncrypted")) {
         return this._OutboundPrivateKeyPassPhraseEncrypted;
      } else if (name.equals("SSLRejectionLoggingEnabled")) {
         return new Boolean(this._SSLRejectionLoggingEnabled);
      } else if (name.equals("SSLv2HelloEnabled")) {
         return new Boolean(this._SSLv2HelloEnabled);
      } else if (name.equals("ServerCertificateChainFileName")) {
         return this._ServerCertificateChainFileName;
      } else if (name.equals("ServerCertificateFileName")) {
         return this._ServerCertificateFileName;
      } else if (name.equals("ServerKeyFileName")) {
         return this._ServerKeyFileName;
      } else if (name.equals("ServerPrivateKeyAlias")) {
         return this._ServerPrivateKeyAlias;
      } else if (name.equals("ServerPrivateKeyPassPhrase")) {
         return this._ServerPrivateKeyPassPhrase;
      } else if (name.equals("ServerPrivateKeyPassPhraseEncrypted")) {
         return this._ServerPrivateKeyPassPhraseEncrypted;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TrustedCAFileName")) {
         return this._TrustedCAFileName;
      } else if (name.equals("TwoWaySSLEnabled")) {
         return new Boolean(this._TwoWaySSLEnabled);
      } else if (name.equals("UseClientCertForOutbound")) {
         return new Boolean(this._UseClientCertForOutbound);
      } else if (name.equals("UseJava")) {
         return new Boolean(this._UseJava);
      } else if (name.equals("UseServerCerts")) {
         return new Boolean(this._UseServerCerts);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 9:
            case 10:
            case 14:
            case 21:
            case 23:
            case 25:
            case 33:
            case 36:
            case 37:
            case 38:
            case 39:
            case 43:
            case 44:
            default:
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 11;
               }
               break;
            case 8:
               if (s.equals("use-java")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("ciphersuite")) {
                  return 12;
               }

               if (s.equals("listen-port")) {
                  return 23;
               }
               break;
            case 12:
               if (s.equals("jsse-enabled")) {
                  return 40;
               }
               break;
            case 13:
               if (s.equals("key-encrypted")) {
                  return 18;
               }
               break;
            case 15:
               if (s.equals("handler-enabled")) {
                  return 27;
               }
               break;
            case 16:
               if (s.equals("use-server-certs")) {
                  return 39;
               }
               break;
            case 17:
               if (s.equals("client-cert-alias")) {
                  return 42;
               }

               if (s.equals("hostname-verifier")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("cert-authenticator")) {
                  return 14;
               }

               if (s.equals("two-wayssl-enabled")) {
                  return 30;
               }
               break;
            case 19:
               if (s.equals("export-key-lifespan")) {
                  return 20;
               }

               if (s.equals("trustedca-file-name")) {
                  return 17;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("listen-port-enabled")) {
                  return 24;
               }
               break;
            case 20:
               if (s.equals("excluded-ciphersuite")) {
                  return 13;
               }

               if (s.equals("login-timeout-millis")) {
                  return 28;
               }

               if (s.equals("server-key-file-name")) {
                  return 29;
               }

               if (s.equals("ss-lv2-hello-enabled")) {
                  return 49;
               }
               break;
            case 22:
               if (s.equals("certificate-cache-size")) {
                  return 26;
               }
               break;
            case 24:
               if (s.equals("server-private-key-alias")) {
                  return 31;
               }
               break;
            case 26:
               if (s.equals("outbound-private-key-alias")) {
                  return 45;
               }
               break;
            case 27:
               if (s.equals("minimumtls-protocol-version")) {
                  return 48;
               }

               if (s.equals("client-certificate-enforced")) {
                  return 21;
               }
               break;
            case 28:
               if (s.equals("identity-and-trust-locations")) {
                  return 35;
               }

               if (s.equals("server-certificate-file-name")) {
                  return 22;
               }

               if (s.equals("acceptkss-demo-certs-enabled")) {
                  return 19;
               }

               if (s.equals("use-client-cert-for-outbound")) {
                  return 41;
               }
               break;
            case 29:
               if (s.equals("allow-unencrypted-null-cipher")) {
                  return 38;
               }

               if (s.equals("hostname-verification-ignored")) {
                  return 16;
               }

               if (s.equals("ssl-rejection-logging-enabled")) {
                  return 34;
               }
               break;
            case 30:
               if (s.equals("inbound-certificate-validation")) {
                  return 36;
               }

               if (s.equals("server-private-key-pass-phrase")) {
                  return 32;
               }
               break;
            case 31:
               if (s.equals("outbound-certificate-validation")) {
                  return 37;
               }
               break;
            case 32:
               if (s.equals("outbound-private-key-pass-phrase")) {
                  return 46;
               }
               break;
            case 34:
               if (s.equals("server-certificate-chain-file-name")) {
                  return 25;
               }
               break;
            case 35:
               if (s.equals("client-cert-private-key-pass-phrase")) {
                  return 43;
               }
               break;
            case 40:
               if (s.equals("server-private-key-pass-phrase-encrypted")) {
                  return 33;
               }
               break;
            case 41:
               if (s.equals("client-init-secure-renegotiation-accepted")) {
                  return 50;
               }
               break;
            case 42:
               if (s.equals("outbound-private-key-pass-phrase-encrypted")) {
                  return 47;
               }
               break;
            case 45:
               if (s.equals("client-cert-private-key-pass-phrase-encrypted")) {
                  return 44;
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
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "use-java";
            case 11:
               return "enabled";
            case 12:
               return "ciphersuite";
            case 13:
               return "excluded-ciphersuite";
            case 14:
               return "cert-authenticator";
            case 15:
               return "hostname-verifier";
            case 16:
               return "hostname-verification-ignored";
            case 17:
               return "trustedca-file-name";
            case 18:
               return "key-encrypted";
            case 19:
               return "acceptkss-demo-certs-enabled";
            case 20:
               return "export-key-lifespan";
            case 21:
               return "client-certificate-enforced";
            case 22:
               return "server-certificate-file-name";
            case 23:
               return "listen-port";
            case 24:
               return "listen-port-enabled";
            case 25:
               return "server-certificate-chain-file-name";
            case 26:
               return "certificate-cache-size";
            case 27:
               return "handler-enabled";
            case 28:
               return "login-timeout-millis";
            case 29:
               return "server-key-file-name";
            case 30:
               return "two-wayssl-enabled";
            case 31:
               return "server-private-key-alias";
            case 32:
               return "server-private-key-pass-phrase";
            case 33:
               return "server-private-key-pass-phrase-encrypted";
            case 34:
               return "ssl-rejection-logging-enabled";
            case 35:
               return "identity-and-trust-locations";
            case 36:
               return "inbound-certificate-validation";
            case 37:
               return "outbound-certificate-validation";
            case 38:
               return "allow-unencrypted-null-cipher";
            case 39:
               return "use-server-certs";
            case 40:
               return "jsse-enabled";
            case 41:
               return "use-client-cert-for-outbound";
            case 42:
               return "client-cert-alias";
            case 43:
               return "client-cert-private-key-pass-phrase";
            case 44:
               return "client-cert-private-key-pass-phrase-encrypted";
            case 45:
               return "outbound-private-key-alias";
            case 46:
               return "outbound-private-key-pass-phrase";
            case 47:
               return "outbound-private-key-pass-phrase-encrypted";
            case 48:
               return "minimumtls-protocol-version";
            case 49:
               return "ss-lv2-hello-enabled";
            case 50:
               return "client-init-secure-renegotiation-accepted";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 11:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
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
      private SSLMBeanImpl bean;

      protected Helper(SSLMBeanImpl bean) {
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
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "UseJava";
            case 11:
               return "Enabled";
            case 12:
               return "Ciphersuites";
            case 13:
               return "ExcludedCiphersuites";
            case 14:
               return "CertAuthenticator";
            case 15:
               return "HostnameVerifier";
            case 16:
               return "HostnameVerificationIgnored";
            case 17:
               return "TrustedCAFileName";
            case 18:
               return "KeyEncrypted";
            case 19:
               return "AcceptKSSDemoCertsEnabled";
            case 20:
               return "ExportKeyLifespan";
            case 21:
               return "ClientCertificateEnforced";
            case 22:
               return "ServerCertificateFileName";
            case 23:
               return "ListenPort";
            case 24:
               return "ListenPortEnabled";
            case 25:
               return "ServerCertificateChainFileName";
            case 26:
               return "CertificateCacheSize";
            case 27:
               return "HandlerEnabled";
            case 28:
               return "LoginTimeoutMillis";
            case 29:
               return "ServerKeyFileName";
            case 30:
               return "TwoWaySSLEnabled";
            case 31:
               return "ServerPrivateKeyAlias";
            case 32:
               return "ServerPrivateKeyPassPhrase";
            case 33:
               return "ServerPrivateKeyPassPhraseEncrypted";
            case 34:
               return "SSLRejectionLoggingEnabled";
            case 35:
               return "IdentityAndTrustLocations";
            case 36:
               return "InboundCertificateValidation";
            case 37:
               return "OutboundCertificateValidation";
            case 38:
               return "AllowUnencryptedNullCipher";
            case 39:
               return "UseServerCerts";
            case 40:
               return "JSSEEnabled";
            case 41:
               return "UseClientCertForOutbound";
            case 42:
               return "ClientCertAlias";
            case 43:
               return "ClientCertPrivateKeyPassPhrase";
            case 44:
               return "ClientCertPrivateKeyPassPhraseEncrypted";
            case 45:
               return "OutboundPrivateKeyAlias";
            case 46:
               return "OutboundPrivateKeyPassPhrase";
            case 47:
               return "OutboundPrivateKeyPassPhraseEncrypted";
            case 48:
               return "MinimumTLSProtocolVersion";
            case 49:
               return "SSLv2HelloEnabled";
            case 50:
               return "ClientInitSecureRenegotiationAccepted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CertAuthenticator")) {
            return 14;
         } else if (propName.equals("CertificateCacheSize")) {
            return 26;
         } else if (propName.equals("Ciphersuites")) {
            return 12;
         } else if (propName.equals("ClientCertAlias")) {
            return 42;
         } else if (propName.equals("ClientCertPrivateKeyPassPhrase")) {
            return 43;
         } else if (propName.equals("ClientCertPrivateKeyPassPhraseEncrypted")) {
            return 44;
         } else if (propName.equals("ExcludedCiphersuites")) {
            return 13;
         } else if (propName.equals("ExportKeyLifespan")) {
            return 20;
         } else if (propName.equals("HostnameVerifier")) {
            return 15;
         } else if (propName.equals("IdentityAndTrustLocations")) {
            return 35;
         } else if (propName.equals("InboundCertificateValidation")) {
            return 36;
         } else if (propName.equals("ListenPort")) {
            return 23;
         } else if (propName.equals("LoginTimeoutMillis")) {
            return 28;
         } else if (propName.equals("MinimumTLSProtocolVersion")) {
            return 48;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OutboundCertificateValidation")) {
            return 37;
         } else if (propName.equals("OutboundPrivateKeyAlias")) {
            return 45;
         } else if (propName.equals("OutboundPrivateKeyPassPhrase")) {
            return 46;
         } else if (propName.equals("OutboundPrivateKeyPassPhraseEncrypted")) {
            return 47;
         } else if (propName.equals("ServerCertificateChainFileName")) {
            return 25;
         } else if (propName.equals("ServerCertificateFileName")) {
            return 22;
         } else if (propName.equals("ServerKeyFileName")) {
            return 29;
         } else if (propName.equals("ServerPrivateKeyAlias")) {
            return 31;
         } else if (propName.equals("ServerPrivateKeyPassPhrase")) {
            return 32;
         } else if (propName.equals("ServerPrivateKeyPassPhraseEncrypted")) {
            return 33;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TrustedCAFileName")) {
            return 17;
         } else if (propName.equals("AcceptKSSDemoCertsEnabled")) {
            return 19;
         } else if (propName.equals("AllowUnencryptedNullCipher")) {
            return 38;
         } else if (propName.equals("ClientCertificateEnforced")) {
            return 21;
         } else if (propName.equals("ClientInitSecureRenegotiationAccepted")) {
            return 50;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("Enabled")) {
            return 11;
         } else if (propName.equals("HandlerEnabled")) {
            return 27;
         } else if (propName.equals("HostnameVerificationIgnored")) {
            return 16;
         } else if (propName.equals("JSSEEnabled")) {
            return 40;
         } else if (propName.equals("KeyEncrypted")) {
            return 18;
         } else if (propName.equals("ListenPortEnabled")) {
            return 24;
         } else if (propName.equals("SSLRejectionLoggingEnabled")) {
            return 34;
         } else if (propName.equals("SSLv2HelloEnabled")) {
            return 49;
         } else if (propName.equals("TwoWaySSLEnabled")) {
            return 30;
         } else if (propName.equals("UseClientCertForOutbound")) {
            return 41;
         } else if (propName.equals("UseJava")) {
            return 10;
         } else {
            return propName.equals("UseServerCerts") ? 39 : super.getPropertyIndex(propName);
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
            if (this.bean.isCertAuthenticatorSet()) {
               buf.append("CertAuthenticator");
               buf.append(String.valueOf(this.bean.getCertAuthenticator()));
            }

            if (this.bean.isCertificateCacheSizeSet()) {
               buf.append("CertificateCacheSize");
               buf.append(String.valueOf(this.bean.getCertificateCacheSize()));
            }

            if (this.bean.isCiphersuitesSet()) {
               buf.append("Ciphersuites");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCiphersuites())));
            }

            if (this.bean.isClientCertAliasSet()) {
               buf.append("ClientCertAlias");
               buf.append(String.valueOf(this.bean.getClientCertAlias()));
            }

            if (this.bean.isClientCertPrivateKeyPassPhraseSet()) {
               buf.append("ClientCertPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getClientCertPrivateKeyPassPhrase()));
            }

            if (this.bean.isClientCertPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("ClientCertPrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getClientCertPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isExcludedCiphersuitesSet()) {
               buf.append("ExcludedCiphersuites");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludedCiphersuites())));
            }

            if (this.bean.isExportKeyLifespanSet()) {
               buf.append("ExportKeyLifespan");
               buf.append(String.valueOf(this.bean.getExportKeyLifespan()));
            }

            if (this.bean.isHostnameVerifierSet()) {
               buf.append("HostnameVerifier");
               buf.append(String.valueOf(this.bean.getHostnameVerifier()));
            }

            if (this.bean.isIdentityAndTrustLocationsSet()) {
               buf.append("IdentityAndTrustLocations");
               buf.append(String.valueOf(this.bean.getIdentityAndTrustLocations()));
            }

            if (this.bean.isInboundCertificateValidationSet()) {
               buf.append("InboundCertificateValidation");
               buf.append(String.valueOf(this.bean.getInboundCertificateValidation()));
            }

            if (this.bean.isListenPortSet()) {
               buf.append("ListenPort");
               buf.append(String.valueOf(this.bean.getListenPort()));
            }

            if (this.bean.isLoginTimeoutMillisSet()) {
               buf.append("LoginTimeoutMillis");
               buf.append(String.valueOf(this.bean.getLoginTimeoutMillis()));
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

            if (this.bean.isOutboundPrivateKeyAliasSet()) {
               buf.append("OutboundPrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getOutboundPrivateKeyAlias()));
            }

            if (this.bean.isOutboundPrivateKeyPassPhraseSet()) {
               buf.append("OutboundPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getOutboundPrivateKeyPassPhrase()));
            }

            if (this.bean.isOutboundPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("OutboundPrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getOutboundPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isServerCertificateChainFileNameSet()) {
               buf.append("ServerCertificateChainFileName");
               buf.append(String.valueOf(this.bean.getServerCertificateChainFileName()));
            }

            if (this.bean.isServerCertificateFileNameSet()) {
               buf.append("ServerCertificateFileName");
               buf.append(String.valueOf(this.bean.getServerCertificateFileName()));
            }

            if (this.bean.isServerKeyFileNameSet()) {
               buf.append("ServerKeyFileName");
               buf.append(String.valueOf(this.bean.getServerKeyFileName()));
            }

            if (this.bean.isServerPrivateKeyAliasSet()) {
               buf.append("ServerPrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getServerPrivateKeyAlias()));
            }

            if (this.bean.isServerPrivateKeyPassPhraseSet()) {
               buf.append("ServerPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getServerPrivateKeyPassPhrase()));
            }

            if (this.bean.isServerPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("ServerPrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getServerPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTrustedCAFileNameSet()) {
               buf.append("TrustedCAFileName");
               buf.append(String.valueOf(this.bean.getTrustedCAFileName()));
            }

            if (this.bean.isAcceptKSSDemoCertsEnabledSet()) {
               buf.append("AcceptKSSDemoCertsEnabled");
               buf.append(String.valueOf(this.bean.isAcceptKSSDemoCertsEnabled()));
            }

            if (this.bean.isAllowUnencryptedNullCipherSet()) {
               buf.append("AllowUnencryptedNullCipher");
               buf.append(String.valueOf(this.bean.isAllowUnencryptedNullCipher()));
            }

            if (this.bean.isClientCertificateEnforcedSet()) {
               buf.append("ClientCertificateEnforced");
               buf.append(String.valueOf(this.bean.isClientCertificateEnforced()));
            }

            if (this.bean.isClientInitSecureRenegotiationAcceptedSet()) {
               buf.append("ClientInitSecureRenegotiationAccepted");
               buf.append(String.valueOf(this.bean.isClientInitSecureRenegotiationAccepted()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isHandlerEnabledSet()) {
               buf.append("HandlerEnabled");
               buf.append(String.valueOf(this.bean.isHandlerEnabled()));
            }

            if (this.bean.isHostnameVerificationIgnoredSet()) {
               buf.append("HostnameVerificationIgnored");
               buf.append(String.valueOf(this.bean.isHostnameVerificationIgnored()));
            }

            if (this.bean.isJSSEEnabledSet()) {
               buf.append("JSSEEnabled");
               buf.append(String.valueOf(this.bean.isJSSEEnabled()));
            }

            if (this.bean.isKeyEncryptedSet()) {
               buf.append("KeyEncrypted");
               buf.append(String.valueOf(this.bean.isKeyEncrypted()));
            }

            if (this.bean.isListenPortEnabledSet()) {
               buf.append("ListenPortEnabled");
               buf.append(String.valueOf(this.bean.isListenPortEnabled()));
            }

            if (this.bean.isSSLRejectionLoggingEnabledSet()) {
               buf.append("SSLRejectionLoggingEnabled");
               buf.append(String.valueOf(this.bean.isSSLRejectionLoggingEnabled()));
            }

            if (this.bean.isSSLv2HelloEnabledSet()) {
               buf.append("SSLv2HelloEnabled");
               buf.append(String.valueOf(this.bean.isSSLv2HelloEnabled()));
            }

            if (this.bean.isTwoWaySSLEnabledSet()) {
               buf.append("TwoWaySSLEnabled");
               buf.append(String.valueOf(this.bean.isTwoWaySSLEnabled()));
            }

            if (this.bean.isUseClientCertForOutboundSet()) {
               buf.append("UseClientCertForOutbound");
               buf.append(String.valueOf(this.bean.isUseClientCertForOutbound()));
            }

            if (this.bean.isUseJavaSet()) {
               buf.append("UseJava");
               buf.append(String.valueOf(this.bean.isUseJava()));
            }

            if (this.bean.isUseServerCertsSet()) {
               buf.append("UseServerCerts");
               buf.append(String.valueOf(this.bean.isUseServerCerts()));
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
            SSLMBeanImpl otherTyped = (SSLMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("CertAuthenticator", this.bean.getCertAuthenticator(), otherTyped.getCertAuthenticator(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("CertificateCacheSize", this.bean.getCertificateCacheSize(), otherTyped.getCertificateCacheSize(), false);
            }

            this.computeDiff("Ciphersuites", this.bean.getCiphersuites(), otherTyped.getCiphersuites(), true);
            this.computeDiff("ClientCertAlias", this.bean.getClientCertAlias(), otherTyped.getClientCertAlias(), true);
            this.computeDiff("ClientCertPrivateKeyPassPhraseEncrypted", this.bean.getClientCertPrivateKeyPassPhraseEncrypted(), otherTyped.getClientCertPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("ExcludedCiphersuites", this.bean.getExcludedCiphersuites(), otherTyped.getExcludedCiphersuites(), true);
            this.computeDiff("ExportKeyLifespan", this.bean.getExportKeyLifespan(), otherTyped.getExportKeyLifespan(), true);
            this.computeDiff("HostnameVerifier", this.bean.getHostnameVerifier(), otherTyped.getHostnameVerifier(), true);
            this.computeDiff("IdentityAndTrustLocations", this.bean.getIdentityAndTrustLocations(), otherTyped.getIdentityAndTrustLocations(), true);
            this.computeDiff("InboundCertificateValidation", this.bean.getInboundCertificateValidation(), otherTyped.getInboundCertificateValidation(), true);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), true);
            this.computeDiff("LoginTimeoutMillis", this.bean.getLoginTimeoutMillis(), otherTyped.getLoginTimeoutMillis(), true);
            this.computeDiff("MinimumTLSProtocolVersion", this.bean.getMinimumTLSProtocolVersion(), otherTyped.getMinimumTLSProtocolVersion(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("OutboundCertificateValidation", this.bean.getOutboundCertificateValidation(), otherTyped.getOutboundCertificateValidation(), true);
            this.computeDiff("OutboundPrivateKeyAlias", this.bean.getOutboundPrivateKeyAlias(), otherTyped.getOutboundPrivateKeyAlias(), true);
            this.computeDiff("OutboundPrivateKeyPassPhraseEncrypted", this.bean.getOutboundPrivateKeyPassPhraseEncrypted(), otherTyped.getOutboundPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("ServerCertificateChainFileName", this.bean.getServerCertificateChainFileName(), otherTyped.getServerCertificateChainFileName(), true);
            this.computeDiff("ServerCertificateFileName", this.bean.getServerCertificateFileName(), otherTyped.getServerCertificateFileName(), true);
            this.computeDiff("ServerKeyFileName", this.bean.getServerKeyFileName(), otherTyped.getServerKeyFileName(), true);
            this.computeDiff("ServerPrivateKeyAlias", this.bean.getServerPrivateKeyAlias(), otherTyped.getServerPrivateKeyAlias(), true);
            this.computeDiff("ServerPrivateKeyPassPhraseEncrypted", this.bean.getServerPrivateKeyPassPhraseEncrypted(), otherTyped.getServerPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("TrustedCAFileName", this.bean.getTrustedCAFileName(), otherTyped.getTrustedCAFileName(), true);
            this.computeDiff("AcceptKSSDemoCertsEnabled", this.bean.isAcceptKSSDemoCertsEnabled(), otherTyped.isAcceptKSSDemoCertsEnabled(), true);
            this.computeDiff("AllowUnencryptedNullCipher", this.bean.isAllowUnencryptedNullCipher(), otherTyped.isAllowUnencryptedNullCipher(), true);
            this.computeDiff("ClientCertificateEnforced", this.bean.isClientCertificateEnforced(), otherTyped.isClientCertificateEnforced(), true);
            this.computeDiff("ClientInitSecureRenegotiationAccepted", this.bean.isClientInitSecureRenegotiationAccepted(), otherTyped.isClientInitSecureRenegotiationAccepted(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("HandlerEnabled", this.bean.isHandlerEnabled(), otherTyped.isHandlerEnabled(), false);
            }

            this.computeDiff("HostnameVerificationIgnored", this.bean.isHostnameVerificationIgnored(), otherTyped.isHostnameVerificationIgnored(), true);
            this.computeDiff("JSSEEnabled", this.bean.isJSSEEnabled(), otherTyped.isJSSEEnabled(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("KeyEncrypted", this.bean.isKeyEncrypted(), otherTyped.isKeyEncrypted(), false);
            }

            this.computeDiff("SSLRejectionLoggingEnabled", this.bean.isSSLRejectionLoggingEnabled(), otherTyped.isSSLRejectionLoggingEnabled(), true);
            this.computeDiff("SSLv2HelloEnabled", this.bean.isSSLv2HelloEnabled(), otherTyped.isSSLv2HelloEnabled(), true);
            this.computeDiff("TwoWaySSLEnabled", this.bean.isTwoWaySSLEnabled(), otherTyped.isTwoWaySSLEnabled(), true);
            this.computeDiff("UseClientCertForOutbound", this.bean.isUseClientCertForOutbound(), otherTyped.isUseClientCertForOutbound(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("UseJava", this.bean.isUseJava(), otherTyped.isUseJava(), false);
            }

            this.computeDiff("UseServerCerts", this.bean.isUseServerCerts(), otherTyped.isUseServerCerts(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SSLMBeanImpl original = (SSLMBeanImpl)event.getSourceBean();
            SSLMBeanImpl proposed = (SSLMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CertAuthenticator")) {
                  original.setCertAuthenticator(proposed.getCertAuthenticator());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CertificateCacheSize")) {
                  original.setCertificateCacheSize(proposed.getCertificateCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("Ciphersuites")) {
                  original.setCiphersuites(proposed.getCiphersuites());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ClientCertAlias")) {
                  original.setClientCertAlias(proposed.getClientCertAlias());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (!prop.equals("ClientCertPrivateKeyPassPhrase")) {
                  if (prop.equals("ClientCertPrivateKeyPassPhraseEncrypted")) {
                     original.setClientCertPrivateKeyPassPhraseEncrypted(proposed.getClientCertPrivateKeyPassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 44);
                  } else if (prop.equals("ExcludedCiphersuites")) {
                     original.setExcludedCiphersuites(proposed.getExcludedCiphersuites());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("ExportKeyLifespan")) {
                     original.setExportKeyLifespan(proposed.getExportKeyLifespan());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("HostnameVerifier")) {
                     original.setHostnameVerifier(proposed.getHostnameVerifier());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("IdentityAndTrustLocations")) {
                     original.setIdentityAndTrustLocations(proposed.getIdentityAndTrustLocations());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("InboundCertificateValidation")) {
                     original.setInboundCertificateValidation(proposed.getInboundCertificateValidation());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("ListenPort")) {
                     original.setListenPort(proposed.getListenPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("LoginTimeoutMillis")) {
                     original.setLoginTimeoutMillis(proposed.getLoginTimeoutMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (prop.equals("MinimumTLSProtocolVersion")) {
                     original.setMinimumTLSProtocolVersion(proposed.getMinimumTLSProtocolVersion());
                     original._conditionalUnset(update.isUnsetUpdate(), 48);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("OutboundCertificateValidation")) {
                     original.setOutboundCertificateValidation(proposed.getOutboundCertificateValidation());
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  } else if (prop.equals("OutboundPrivateKeyAlias")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
                  } else if (!prop.equals("OutboundPrivateKeyPassPhrase")) {
                     if (prop.equals("OutboundPrivateKeyPassPhraseEncrypted")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 47);
                     } else if (prop.equals("ServerCertificateChainFileName")) {
                        original.setServerCertificateChainFileName(proposed.getServerCertificateChainFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 25);
                     } else if (prop.equals("ServerCertificateFileName")) {
                        original.setServerCertificateFileName(proposed.getServerCertificateFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("ServerKeyFileName")) {
                        original.setServerKeyFileName(proposed.getServerKeyFileName());
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("ServerPrivateKeyAlias")) {
                        original.setServerPrivateKeyAlias(proposed.getServerPrivateKeyAlias());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (!prop.equals("ServerPrivateKeyPassPhrase")) {
                        if (prop.equals("ServerPrivateKeyPassPhraseEncrypted")) {
                           original.setServerPrivateKeyPassPhraseEncrypted(proposed.getServerPrivateKeyPassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 33);
                        } else if (prop.equals("Tags")) {
                           if (type == 2) {
                              update.resetAddedObject(update.getAddedObject());
                              original.addTag((String)update.getAddedObject());
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeTag((String)update.getRemovedObject());
                           }

                           if (original.getTags() == null || original.getTags().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 9);
                           }
                        } else if (prop.equals("TrustedCAFileName")) {
                           original.setTrustedCAFileName(proposed.getTrustedCAFileName());
                           original._conditionalUnset(update.isUnsetUpdate(), 17);
                        } else if (prop.equals("AcceptKSSDemoCertsEnabled")) {
                           original.setAcceptKSSDemoCertsEnabled(proposed.isAcceptKSSDemoCertsEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        } else if (prop.equals("AllowUnencryptedNullCipher")) {
                           original.setAllowUnencryptedNullCipher(proposed.isAllowUnencryptedNullCipher());
                           original._conditionalUnset(update.isUnsetUpdate(), 38);
                        } else if (prop.equals("ClientCertificateEnforced")) {
                           original.setClientCertificateEnforced(proposed.isClientCertificateEnforced());
                           original._conditionalUnset(update.isUnsetUpdate(), 21);
                        } else if (prop.equals("ClientInitSecureRenegotiationAccepted")) {
                           original.setClientInitSecureRenegotiationAccepted(proposed.isClientInitSecureRenegotiationAccepted());
                           original._conditionalUnset(update.isUnsetUpdate(), 50);
                        } else if (!prop.equals("DynamicallyCreated")) {
                           if (prop.equals("Enabled")) {
                              original.setEnabled(proposed.isEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 11);
                           } else if (prop.equals("HandlerEnabled")) {
                              original.setHandlerEnabled(proposed.isHandlerEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 27);
                           } else if (prop.equals("HostnameVerificationIgnored")) {
                              original.setHostnameVerificationIgnored(proposed.isHostnameVerificationIgnored());
                              original._conditionalUnset(update.isUnsetUpdate(), 16);
                           } else if (prop.equals("JSSEEnabled")) {
                              original.setJSSEEnabled(proposed.isJSSEEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 40);
                           } else if (prop.equals("KeyEncrypted")) {
                              original.setKeyEncrypted(proposed.isKeyEncrypted());
                              original._conditionalUnset(update.isUnsetUpdate(), 18);
                           } else if (!prop.equals("ListenPortEnabled")) {
                              if (prop.equals("SSLRejectionLoggingEnabled")) {
                                 original.setSSLRejectionLoggingEnabled(proposed.isSSLRejectionLoggingEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 34);
                              } else if (prop.equals("SSLv2HelloEnabled")) {
                                 original.setSSLv2HelloEnabled(proposed.isSSLv2HelloEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 49);
                              } else if (prop.equals("TwoWaySSLEnabled")) {
                                 original.setTwoWaySSLEnabled(proposed.isTwoWaySSLEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 30);
                              } else if (prop.equals("UseClientCertForOutbound")) {
                                 original.setUseClientCertForOutbound(proposed.isUseClientCertForOutbound());
                                 original._conditionalUnset(update.isUnsetUpdate(), 41);
                              } else if (prop.equals("UseJava")) {
                                 original.setUseJava(proposed.isUseJava());
                                 original._conditionalUnset(update.isUnsetUpdate(), 10);
                              } else if (prop.equals("UseServerCerts")) {
                                 original.setUseServerCerts(proposed.isUseServerCerts());
                                 original._conditionalUnset(update.isUnsetUpdate(), 39);
                              } else {
                                 super.applyPropertyUpdate(event, update);
                              }
                           }
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
            SSLMBeanImpl copy = (SSLMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("CertAuthenticator")) && this.bean.isCertAuthenticatorSet()) {
               copy.setCertAuthenticator(this.bean.getCertAuthenticator());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("CertificateCacheSize")) && this.bean.isCertificateCacheSizeSet()) {
               copy.setCertificateCacheSize(this.bean.getCertificateCacheSize());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Ciphersuites")) && this.bean.isCiphersuitesSet()) {
               o = this.bean.getCiphersuites();
               copy.setCiphersuites(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertAlias")) && this.bean.isClientCertAliasSet()) {
               copy.setClientCertAlias(this.bean.getClientCertAlias());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("ClientCertPrivateKeyPassPhraseEncrypted")) && this.bean.isClientCertPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getClientCertPrivateKeyPassPhraseEncrypted();
               copy.setClientCertPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludedCiphersuites")) && this.bean.isExcludedCiphersuitesSet()) {
               o = this.bean.getExcludedCiphersuites();
               copy.setExcludedCiphersuites(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ExportKeyLifespan")) && this.bean.isExportKeyLifespanSet()) {
               copy.setExportKeyLifespan(this.bean.getExportKeyLifespan());
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerifier")) && this.bean.isHostnameVerifierSet()) {
               copy.setHostnameVerifier(this.bean.getHostnameVerifier());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityAndTrustLocations")) && this.bean.isIdentityAndTrustLocationsSet()) {
               copy.setIdentityAndTrustLocations(this.bean.getIdentityAndTrustLocations());
            }

            if ((excludeProps == null || !excludeProps.contains("InboundCertificateValidation")) && this.bean.isInboundCertificateValidationSet()) {
               copy.setInboundCertificateValidation(this.bean.getInboundCertificateValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPort")) && this.bean.isListenPortSet()) {
               copy.setListenPort(this.bean.getListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeoutMillis")) && this.bean.isLoginTimeoutMillisSet()) {
               copy.setLoginTimeoutMillis(this.bean.getLoginTimeoutMillis());
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

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyAlias")) && this.bean.isOutboundPrivateKeyAliasSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyPassPhraseEncrypted")) && this.bean.isOutboundPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getOutboundPrivateKeyPassPhraseEncrypted();
               copy.setOutboundPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerCertificateChainFileName")) && this.bean.isServerCertificateChainFileNameSet()) {
               copy.setServerCertificateChainFileName(this.bean.getServerCertificateChainFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerCertificateFileName")) && this.bean.isServerCertificateFileNameSet()) {
               copy.setServerCertificateFileName(this.bean.getServerCertificateFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerKeyFileName")) && this.bean.isServerKeyFileNameSet()) {
               copy.setServerKeyFileName(this.bean.getServerKeyFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerPrivateKeyAlias")) && this.bean.isServerPrivateKeyAliasSet()) {
               copy.setServerPrivateKeyAlias(this.bean.getServerPrivateKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerPrivateKeyPassPhraseEncrypted")) && this.bean.isServerPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getServerPrivateKeyPassPhraseEncrypted();
               copy.setServerPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TrustedCAFileName")) && this.bean.isTrustedCAFileNameSet()) {
               copy.setTrustedCAFileName(this.bean.getTrustedCAFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("AcceptKSSDemoCertsEnabled")) && this.bean.isAcceptKSSDemoCertsEnabledSet()) {
               copy.setAcceptKSSDemoCertsEnabled(this.bean.isAcceptKSSDemoCertsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowUnencryptedNullCipher")) && this.bean.isAllowUnencryptedNullCipherSet()) {
               copy.setAllowUnencryptedNullCipher(this.bean.isAllowUnencryptedNullCipher());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertificateEnforced")) && this.bean.isClientCertificateEnforcedSet()) {
               copy.setClientCertificateEnforced(this.bean.isClientCertificateEnforced());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientInitSecureRenegotiationAccepted")) && this.bean.isClientInitSecureRenegotiationAcceptedSet()) {
               copy.setClientInitSecureRenegotiationAccepted(this.bean.isClientInitSecureRenegotiationAccepted());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("HandlerEnabled")) && this.bean.isHandlerEnabledSet()) {
               copy.setHandlerEnabled(this.bean.isHandlerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerificationIgnored")) && this.bean.isHostnameVerificationIgnoredSet()) {
               copy.setHostnameVerificationIgnored(this.bean.isHostnameVerificationIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("JSSEEnabled")) && this.bean.isJSSEEnabledSet()) {
               copy.setJSSEEnabled(this.bean.isJSSEEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("KeyEncrypted")) && this.bean.isKeyEncryptedSet()) {
               copy.setKeyEncrypted(this.bean.isKeyEncrypted());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLRejectionLoggingEnabled")) && this.bean.isSSLRejectionLoggingEnabledSet()) {
               copy.setSSLRejectionLoggingEnabled(this.bean.isSSLRejectionLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLv2HelloEnabled")) && this.bean.isSSLv2HelloEnabledSet()) {
               copy.setSSLv2HelloEnabled(this.bean.isSSLv2HelloEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TwoWaySSLEnabled")) && this.bean.isTwoWaySSLEnabledSet()) {
               copy.setTwoWaySSLEnabled(this.bean.isTwoWaySSLEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UseClientCertForOutbound")) && this.bean.isUseClientCertForOutboundSet()) {
               copy.setUseClientCertForOutbound(this.bean.isUseClientCertForOutbound());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("UseJava")) && this.bean.isUseJavaSet()) {
               copy.setUseJava(this.bean.isUseJava());
            }

            if ((excludeProps == null || !excludeProps.contains("UseServerCerts")) && this.bean.isUseServerCertsSet()) {
               copy.setUseServerCerts(this.bean.isUseServerCerts());
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
