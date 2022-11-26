package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.FederationServices;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class FederationServicesMBeanImpl extends ConfigurationMBeanImpl implements FederationServicesMBean, Serializable {
   private boolean _ACSRequiresSSL;
   private boolean _ARSRequiresSSL;
   private boolean _ARSRequiresTwoWaySSL;
   private String[] _AllowedTargetHosts;
   private String[] _AssertionConsumerURIs;
   private String[] _AssertionRetrievalURIs;
   private String _AssertionStoreClassName;
   private Properties _AssertionStoreProperties;
   private boolean _DestinationSiteEnabled;
   private boolean _DynamicallyCreated;
   private boolean _ITSRequiresSSL;
   private String[] _IntersiteTransferURIs;
   private String _Name;
   private boolean _POSTOneUseCheckEnabled;
   private boolean _POSTRecipientCheckEnabled;
   private String _SSLClientIdentityAlias;
   private String _SSLClientIdentityPassPhrase;
   private byte[] _SSLClientIdentityPassPhraseEncrypted;
   private String _SigningKeyAlias;
   private String _SigningKeyPassPhrase;
   private byte[] _SigningKeyPassPhraseEncrypted;
   private String _SourceIdBase64;
   private String _SourceIdHex;
   private boolean _SourceSiteEnabled;
   private String _SourceSiteURL;
   private String[] _Tags;
   private String _UsedAssertionCacheClassName;
   private Properties _UsedAssertionCacheProperties;
   private transient FederationServices _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private FederationServicesMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(FederationServicesMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(FederationServicesMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public FederationServicesMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(FederationServicesMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      FederationServicesMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public FederationServicesMBeanImpl() {
      try {
         this._customizer = new FederationServices(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public FederationServicesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new FederationServices(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public FederationServicesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new FederationServices(this);
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

   public boolean isSourceSiteEnabled() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().isSourceSiteEnabled() : this._SourceSiteEnabled;
   }

   public boolean isSourceSiteEnabledInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isSourceSiteEnabledSet() {
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
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSourceSiteEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      boolean _oldVal = this._SourceSiteEnabled;
      this._SourceSiteEnabled = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSourceSiteURL() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getSourceSiteURL(), this) : this._SourceSiteURL;
   }

   public boolean isSourceSiteURLInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isSourceSiteURLSet() {
      return this._isSet(11);
   }

   public void setSourceSiteURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._SourceSiteURL;
      this._SourceSiteURL = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSourceIdHex() {
      return this._customizer.getSourceIdHex();
   }

   public boolean isSourceIdHexInherited() {
      return false;
   }

   public boolean isSourceIdHexSet() {
      return this._isSet(12);
   }

   public void setSourceIdHex(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SourceIdHex = param0;
   }

   public String getSourceIdBase64() {
      return this._customizer.getSourceIdBase64();
   }

   public boolean isSourceIdBase64Inherited() {
      return false;
   }

   public boolean isSourceIdBase64Set() {
      return this._isSet(13);
   }

   public void setSourceIdBase64(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SourceIdBase64 = param0;
   }

   public String[] getIntersiteTransferURIs() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getIntersiteTransferURIs() : this._IntersiteTransferURIs;
   }

   public boolean isIntersiteTransferURIsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isIntersiteTransferURIsSet() {
      return this._isSet(14);
   }

   public void setIntersiteTransferURIs(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String[] _oldVal = this._IntersiteTransferURIs;
      this._IntersiteTransferURIs = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isITSRequiresSSL() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().isITSRequiresSSL() : this._ITSRequiresSSL;
   }

   public boolean isITSRequiresSSLInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isITSRequiresSSLSet() {
      return this._isSet(15);
   }

   public void setITSRequiresSSL(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._ITSRequiresSSL;
      this._ITSRequiresSSL = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getAssertionRetrievalURIs() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getAssertionRetrievalURIs() : this._AssertionRetrievalURIs;
   }

   public boolean isAssertionRetrievalURIsInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isAssertionRetrievalURIsSet() {
      return this._isSet(16);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setAssertionRetrievalURIs(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String[] _oldVal = this._AssertionRetrievalURIs;
      this._AssertionRetrievalURIs = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isARSRequiresSSL() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().isARSRequiresSSL() : this._ARSRequiresSSL;
   }

   public boolean isARSRequiresSSLInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isARSRequiresSSLSet() {
      return this._isSet(17);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setARSRequiresSSL(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      boolean _oldVal = this._ARSRequiresSSL;
      this._ARSRequiresSSL = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isARSRequiresTwoWaySSL() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().isARSRequiresTwoWaySSL() : this._ARSRequiresTwoWaySSL;
   }

   public boolean isARSRequiresTwoWaySSLInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isARSRequiresTwoWaySSLSet() {
      return this._isSet(18);
   }

   public void setARSRequiresTwoWaySSL(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      boolean _oldVal = this._ARSRequiresTwoWaySSL;
      this._ARSRequiresTwoWaySSL = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAssertionStoreClassName() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getAssertionStoreClassName(), this) : this._AssertionStoreClassName;
   }

   public boolean isAssertionStoreClassNameInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isAssertionStoreClassNameSet() {
      return this._isSet(19);
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

   public void setAssertionStoreClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      String _oldVal = this._AssertionStoreClassName;
      this._AssertionStoreClassName = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public Properties getAssertionStoreProperties() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getAssertionStoreProperties() : this._AssertionStoreProperties;
   }

   public String getAssertionStorePropertiesAsString() {
      return StringHelper.objectToString(this.getAssertionStoreProperties());
   }

   public boolean isAssertionStorePropertiesInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isAssertionStorePropertiesSet() {
      return this._isSet(20);
   }

   public void setAssertionStorePropertiesAsString(String param0) {
      try {
         this.setAssertionStoreProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
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

   public void setAssertionStoreProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      Properties _oldVal = this._AssertionStoreProperties;
      this._AssertionStoreProperties = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSigningKeyAlias() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._performMacroSubstitution(this._getDelegateBean().getSigningKeyAlias(), this) : this._SigningKeyAlias;
   }

   public boolean isSigningKeyAliasInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isSigningKeyAliasSet() {
      return this._isSet(21);
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
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
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

   public void setSigningKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      String _oldVal = this._SigningKeyAlias;
      this._SigningKeyAlias = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSigningKeyPassPhrase() {
      if (!this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22)) {
         return this._performMacroSubstitution(this._getDelegateBean().getSigningKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getSigningKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("SigningKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isSigningKeyPassPhraseInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isSigningKeyPassPhraseSet() {
      return this.isSigningKeyPassPhraseEncryptedSet();
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

   public void setSigningKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setSigningKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("SigningKeyPassPhrase", param0));
   }

   public byte[] getSigningKeyPassPhraseEncrypted() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getSigningKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._SigningKeyPassPhraseEncrypted);
   }

   public String getSigningKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getSigningKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSigningKeyPassPhraseEncryptedInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isSigningKeyPassPhraseEncryptedSet() {
      return this._isSet(23);
   }

   public void setSigningKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSigningKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isDestinationSiteEnabled() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().isDestinationSiteEnabled() : this._DestinationSiteEnabled;
   }

   public boolean isDestinationSiteEnabledInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isDestinationSiteEnabledSet() {
      return this._isSet(24);
   }

   public void setDestinationSiteEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._DestinationSiteEnabled;
      this._DestinationSiteEnabled = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getAssertionConsumerURIs() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getAssertionConsumerURIs() : this._AssertionConsumerURIs;
   }

   public boolean isAssertionConsumerURIsInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isAssertionConsumerURIsSet() {
      return this._isSet(25);
   }

   public void setAssertionConsumerURIs(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      String[] _oldVal = this._AssertionConsumerURIs;
      this._AssertionConsumerURIs = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isACSRequiresSSL() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isACSRequiresSSL() : this._ACSRequiresSSL;
   }

   public boolean isACSRequiresSSLInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isACSRequiresSSLSet() {
      return this._isSet(26);
   }

   public void setACSRequiresSSL(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._ACSRequiresSSL;
      this._ACSRequiresSSL = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPOSTRecipientCheckEnabled() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isPOSTRecipientCheckEnabled() : this._POSTRecipientCheckEnabled;
   }

   public boolean isPOSTRecipientCheckEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isPOSTRecipientCheckEnabledSet() {
      return this._isSet(27);
   }

   public void setPOSTRecipientCheckEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._POSTRecipientCheckEnabled;
      this._POSTRecipientCheckEnabled = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPOSTOneUseCheckEnabled() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().isPOSTOneUseCheckEnabled() : this._POSTOneUseCheckEnabled;
   }

   public boolean isPOSTOneUseCheckEnabledInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isPOSTOneUseCheckEnabledSet() {
      return this._isSet(28);
   }

   public void setPOSTOneUseCheckEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      boolean _oldVal = this._POSTOneUseCheckEnabled;
      this._POSTOneUseCheckEnabled = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUsedAssertionCacheClassName() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._performMacroSubstitution(this._getDelegateBean().getUsedAssertionCacheClassName(), this) : this._UsedAssertionCacheClassName;
   }

   public boolean isUsedAssertionCacheClassNameInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isUsedAssertionCacheClassNameSet() {
      return this._isSet(29);
   }

   public void setUsedAssertionCacheClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      String _oldVal = this._UsedAssertionCacheClassName;
      this._UsedAssertionCacheClassName = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getUsedAssertionCacheProperties() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getUsedAssertionCacheProperties() : this._UsedAssertionCacheProperties;
   }

   public String getUsedAssertionCachePropertiesAsString() {
      return StringHelper.objectToString(this.getUsedAssertionCacheProperties());
   }

   public boolean isUsedAssertionCachePropertiesInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isUsedAssertionCachePropertiesSet() {
      return this._isSet(30);
   }

   public void setUsedAssertionCachePropertiesAsString(String param0) {
      try {
         this.setUsedAssertionCacheProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setUsedAssertionCacheProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      Properties _oldVal = this._UsedAssertionCacheProperties;
      this._UsedAssertionCacheProperties = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSSLClientIdentityAlias() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._performMacroSubstitution(this._getDelegateBean().getSSLClientIdentityAlias(), this) : this._SSLClientIdentityAlias;
   }

   public boolean isSSLClientIdentityAliasInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isSSLClientIdentityAliasSet() {
      return this._isSet(31);
   }

   public void setSSLClientIdentityAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      String _oldVal = this._SSLClientIdentityAlias;
      this._SSLClientIdentityAlias = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSSLClientIdentityPassPhrase() {
      if (!this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32)) {
         return this._performMacroSubstitution(this._getDelegateBean().getSSLClientIdentityPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getSSLClientIdentityPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("SSLClientIdentityPassPhrase", bEncrypted);
      }
   }

   public boolean isSSLClientIdentityPassPhraseInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isSSLClientIdentityPassPhraseSet() {
      return this.isSSLClientIdentityPassPhraseEncryptedSet();
   }

   public void setSSLClientIdentityPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setSSLClientIdentityPassPhraseEncrypted(param0 == null ? null : this._encrypt("SSLClientIdentityPassPhrase", param0));
   }

   public byte[] getSSLClientIdentityPassPhraseEncrypted() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getSSLClientIdentityPassPhraseEncrypted() : this._getHelper()._cloneArray(this._SSLClientIdentityPassPhraseEncrypted);
   }

   public String getSSLClientIdentityPassPhraseEncryptedAsString() {
      byte[] obj = this.getSSLClientIdentityPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSSLClientIdentityPassPhraseEncryptedInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isSSLClientIdentityPassPhraseEncryptedSet() {
      return this._isSet(33);
   }

   public void setSSLClientIdentityPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSSLClientIdentityPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String[] getAllowedTargetHosts() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getAllowedTargetHosts() : this._AllowedTargetHosts;
   }

   public boolean isAllowedTargetHostsInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isAllowedTargetHostsSet() {
      return this._isSet(34);
   }

   public void setAllowedTargetHosts(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      String[] _oldVal = this._AllowedTargetHosts;
      this._AllowedTargetHosts = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ServerLegalHelper.validateFederationServices(this);
   }

   public void setSSLClientIdentityPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      byte[] _oldVal = this._SSLClientIdentityPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SSLClientIdentityPassPhraseEncrypted of FederationServicesMBean");
      } else {
         this._getHelper()._clearArray(this._SSLClientIdentityPassPhraseEncrypted);
         this._SSLClientIdentityPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(33, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(33)) {
               source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setSigningKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      byte[] _oldVal = this._SigningKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SigningKeyPassPhraseEncrypted of FederationServicesMBean");
      } else {
         this._getHelper()._clearArray(this._SigningKeyPassPhraseEncrypted);
         this._SigningKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(23, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            FederationServicesMBeanImpl source = (FederationServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(23)) {
               source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
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
         if (idx == 32) {
            this._markSet(33, false);
         }

         if (idx == 22) {
            this._markSet(23, false);
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
         idx = 34;
      }

      try {
         switch (idx) {
            case 34:
               this._AllowedTargetHosts = new String[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._AssertionConsumerURIs = StringHelper.split("/samlacs/acs");
               if (initOne) {
                  break;
               }
            case 16:
               this._AssertionRetrievalURIs = StringHelper.split("/samlars/ars");
               if (initOne) {
                  break;
               }
            case 19:
               this._AssertionStoreClassName = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._AssertionStoreProperties = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._IntersiteTransferURIs = new String[]{"/samlits_ba/its", "/samlits_ba/its/post", "/samlits_ba/its/artifact", "/samlits_cc/its", "/samlits_cc/its/post", "/samlits_cc/its/artifact"};
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 31:
               this._SSLClientIdentityAlias = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._SSLClientIdentityPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._SSLClientIdentityPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._SigningKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._SigningKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._SigningKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._SourceIdBase64 = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._SourceIdHex = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SourceSiteURL = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 29:
               this._UsedAssertionCacheClassName = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._UsedAssertionCacheProperties = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._ACSRequiresSSL = true;
               if (initOne) {
                  break;
               }
            case 17:
               this._ARSRequiresSSL = true;
               if (initOne) {
                  break;
               }
            case 18:
               this._ARSRequiresTwoWaySSL = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._DestinationSiteEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._ITSRequiresSSL = true;
               if (initOne) {
                  break;
               }
            case 28:
               this._POSTOneUseCheckEnabled = true;
               if (initOne) {
                  break;
               }
            case 27:
               this._POSTRecipientCheckEnabled = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._SourceSiteEnabled = false;
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
      return "FederationServices";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("ACSRequiresSSL")) {
         oldVal = this._ACSRequiresSSL;
         this._ACSRequiresSSL = (Boolean)v;
         this._postSet(26, oldVal, this._ACSRequiresSSL);
      } else if (name.equals("ARSRequiresSSL")) {
         oldVal = this._ARSRequiresSSL;
         this._ARSRequiresSSL = (Boolean)v;
         this._postSet(17, oldVal, this._ARSRequiresSSL);
      } else if (name.equals("ARSRequiresTwoWaySSL")) {
         oldVal = this._ARSRequiresTwoWaySSL;
         this._ARSRequiresTwoWaySSL = (Boolean)v;
         this._postSet(18, oldVal, this._ARSRequiresTwoWaySSL);
      } else {
         String[] oldVal;
         if (name.equals("AllowedTargetHosts")) {
            oldVal = this._AllowedTargetHosts;
            this._AllowedTargetHosts = (String[])((String[])v);
            this._postSet(34, oldVal, this._AllowedTargetHosts);
         } else if (name.equals("AssertionConsumerURIs")) {
            oldVal = this._AssertionConsumerURIs;
            this._AssertionConsumerURIs = (String[])((String[])v);
            this._postSet(25, oldVal, this._AssertionConsumerURIs);
         } else if (name.equals("AssertionRetrievalURIs")) {
            oldVal = this._AssertionRetrievalURIs;
            this._AssertionRetrievalURIs = (String[])((String[])v);
            this._postSet(16, oldVal, this._AssertionRetrievalURIs);
         } else {
            String oldVal;
            if (name.equals("AssertionStoreClassName")) {
               oldVal = this._AssertionStoreClassName;
               this._AssertionStoreClassName = (String)v;
               this._postSet(19, oldVal, this._AssertionStoreClassName);
            } else {
               Properties oldVal;
               if (name.equals("AssertionStoreProperties")) {
                  oldVal = this._AssertionStoreProperties;
                  this._AssertionStoreProperties = (Properties)v;
                  this._postSet(20, oldVal, this._AssertionStoreProperties);
               } else if (name.equals("DestinationSiteEnabled")) {
                  oldVal = this._DestinationSiteEnabled;
                  this._DestinationSiteEnabled = (Boolean)v;
                  this._postSet(24, oldVal, this._DestinationSiteEnabled);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("ITSRequiresSSL")) {
                  oldVal = this._ITSRequiresSSL;
                  this._ITSRequiresSSL = (Boolean)v;
                  this._postSet(15, oldVal, this._ITSRequiresSSL);
               } else if (name.equals("IntersiteTransferURIs")) {
                  oldVal = this._IntersiteTransferURIs;
                  this._IntersiteTransferURIs = (String[])((String[])v);
                  this._postSet(14, oldVal, this._IntersiteTransferURIs);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("POSTOneUseCheckEnabled")) {
                  oldVal = this._POSTOneUseCheckEnabled;
                  this._POSTOneUseCheckEnabled = (Boolean)v;
                  this._postSet(28, oldVal, this._POSTOneUseCheckEnabled);
               } else if (name.equals("POSTRecipientCheckEnabled")) {
                  oldVal = this._POSTRecipientCheckEnabled;
                  this._POSTRecipientCheckEnabled = (Boolean)v;
                  this._postSet(27, oldVal, this._POSTRecipientCheckEnabled);
               } else if (name.equals("SSLClientIdentityAlias")) {
                  oldVal = this._SSLClientIdentityAlias;
                  this._SSLClientIdentityAlias = (String)v;
                  this._postSet(31, oldVal, this._SSLClientIdentityAlias);
               } else if (name.equals("SSLClientIdentityPassPhrase")) {
                  oldVal = this._SSLClientIdentityPassPhrase;
                  this._SSLClientIdentityPassPhrase = (String)v;
                  this._postSet(32, oldVal, this._SSLClientIdentityPassPhrase);
               } else {
                  byte[] oldVal;
                  if (name.equals("SSLClientIdentityPassPhraseEncrypted")) {
                     oldVal = this._SSLClientIdentityPassPhraseEncrypted;
                     this._SSLClientIdentityPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(33, oldVal, this._SSLClientIdentityPassPhraseEncrypted);
                  } else if (name.equals("SigningKeyAlias")) {
                     oldVal = this._SigningKeyAlias;
                     this._SigningKeyAlias = (String)v;
                     this._postSet(21, oldVal, this._SigningKeyAlias);
                  } else if (name.equals("SigningKeyPassPhrase")) {
                     oldVal = this._SigningKeyPassPhrase;
                     this._SigningKeyPassPhrase = (String)v;
                     this._postSet(22, oldVal, this._SigningKeyPassPhrase);
                  } else if (name.equals("SigningKeyPassPhraseEncrypted")) {
                     oldVal = this._SigningKeyPassPhraseEncrypted;
                     this._SigningKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(23, oldVal, this._SigningKeyPassPhraseEncrypted);
                  } else if (name.equals("SourceIdBase64")) {
                     oldVal = this._SourceIdBase64;
                     this._SourceIdBase64 = (String)v;
                     this._postSet(13, oldVal, this._SourceIdBase64);
                  } else if (name.equals("SourceIdHex")) {
                     oldVal = this._SourceIdHex;
                     this._SourceIdHex = (String)v;
                     this._postSet(12, oldVal, this._SourceIdHex);
                  } else if (name.equals("SourceSiteEnabled")) {
                     oldVal = this._SourceSiteEnabled;
                     this._SourceSiteEnabled = (Boolean)v;
                     this._postSet(10, oldVal, this._SourceSiteEnabled);
                  } else if (name.equals("SourceSiteURL")) {
                     oldVal = this._SourceSiteURL;
                     this._SourceSiteURL = (String)v;
                     this._postSet(11, oldVal, this._SourceSiteURL);
                  } else if (name.equals("Tags")) {
                     oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("UsedAssertionCacheClassName")) {
                     oldVal = this._UsedAssertionCacheClassName;
                     this._UsedAssertionCacheClassName = (String)v;
                     this._postSet(29, oldVal, this._UsedAssertionCacheClassName);
                  } else if (name.equals("UsedAssertionCacheProperties")) {
                     oldVal = this._UsedAssertionCacheProperties;
                     this._UsedAssertionCacheProperties = (Properties)v;
                     this._postSet(30, oldVal, this._UsedAssertionCacheProperties);
                  } else if (name.equals("customizer")) {
                     FederationServices oldVal = this._customizer;
                     this._customizer = (FederationServices)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ACSRequiresSSL")) {
         return new Boolean(this._ACSRequiresSSL);
      } else if (name.equals("ARSRequiresSSL")) {
         return new Boolean(this._ARSRequiresSSL);
      } else if (name.equals("ARSRequiresTwoWaySSL")) {
         return new Boolean(this._ARSRequiresTwoWaySSL);
      } else if (name.equals("AllowedTargetHosts")) {
         return this._AllowedTargetHosts;
      } else if (name.equals("AssertionConsumerURIs")) {
         return this._AssertionConsumerURIs;
      } else if (name.equals("AssertionRetrievalURIs")) {
         return this._AssertionRetrievalURIs;
      } else if (name.equals("AssertionStoreClassName")) {
         return this._AssertionStoreClassName;
      } else if (name.equals("AssertionStoreProperties")) {
         return this._AssertionStoreProperties;
      } else if (name.equals("DestinationSiteEnabled")) {
         return new Boolean(this._DestinationSiteEnabled);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ITSRequiresSSL")) {
         return new Boolean(this._ITSRequiresSSL);
      } else if (name.equals("IntersiteTransferURIs")) {
         return this._IntersiteTransferURIs;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("POSTOneUseCheckEnabled")) {
         return new Boolean(this._POSTOneUseCheckEnabled);
      } else if (name.equals("POSTRecipientCheckEnabled")) {
         return new Boolean(this._POSTRecipientCheckEnabled);
      } else if (name.equals("SSLClientIdentityAlias")) {
         return this._SSLClientIdentityAlias;
      } else if (name.equals("SSLClientIdentityPassPhrase")) {
         return this._SSLClientIdentityPassPhrase;
      } else if (name.equals("SSLClientIdentityPassPhraseEncrypted")) {
         return this._SSLClientIdentityPassPhraseEncrypted;
      } else if (name.equals("SigningKeyAlias")) {
         return this._SigningKeyAlias;
      } else if (name.equals("SigningKeyPassPhrase")) {
         return this._SigningKeyPassPhrase;
      } else if (name.equals("SigningKeyPassPhraseEncrypted")) {
         return this._SigningKeyPassPhraseEncrypted;
      } else if (name.equals("SourceIdBase64")) {
         return this._SourceIdBase64;
      } else if (name.equals("SourceIdHex")) {
         return this._SourceIdHex;
      } else if (name.equals("SourceSiteEnabled")) {
         return new Boolean(this._SourceSiteEnabled);
      } else if (name.equals("SourceSiteURL")) {
         return this._SourceSiteURL;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UsedAssertionCacheClassName")) {
         return this._UsedAssertionCacheClassName;
      } else if (name.equals("UsedAssertionCacheProperties")) {
         return this._UsedAssertionCacheProperties;
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
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 18:
            case 20:
            case 21:
            case 27:
            case 29:
            case 30:
            case 32:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            default:
               break;
            case 13:
               if (s.equals("source-id-hex")) {
                  return 12;
               }
               break;
            case 15:
               if (s.equals("source-site-url")) {
                  return 11;
               }

               if (s.equals("acs-requiresssl")) {
                  return 26;
               }

               if (s.equals("ars-requiresssl")) {
                  return 17;
               }

               if (s.equals("its-requiresssl")) {
                  return 15;
               }
               break;
            case 16:
               if (s.equals("source-id-base64")) {
                  return 13;
               }
               break;
            case 17:
               if (s.equals("signing-key-alias")) {
                  return 21;
               }
               break;
            case 19:
               if (s.equals("allowed-target-host")) {
                  return 34;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("source-site-enabled")) {
                  return 10;
               }
               break;
            case 22:
               if (s.equals("assertion-consumer-uri")) {
                  return 25;
               }

               if (s.equals("intersite-transfer-uri")) {
                  return 14;
               }
               break;
            case 23:
               if (s.equals("assertion-retrieval-uri")) {
                  return 16;
               }

               if (s.equals("signing-key-pass-phrase")) {
                  return 22;
               }

               if (s.equals("ars-requires-two-wayssl")) {
                  return 18;
               }
               break;
            case 24:
               if (s.equals("destination-site-enabled")) {
                  return 24;
               }
               break;
            case 25:
               if (s.equals("ssl-client-identity-alias")) {
                  return 31;
               }
               break;
            case 26:
               if (s.equals("assertion-store-class-name")) {
                  return 19;
               }

               if (s.equals("assertion-store-properties")) {
                  return 20;
               }

               if (s.equals("post-one-use-check-enabled")) {
                  return 28;
               }
               break;
            case 28:
               if (s.equals("post-recipient-check-enabled")) {
                  return 27;
               }
               break;
            case 31:
               if (s.equals("ssl-client-identity-pass-phrase")) {
                  return 32;
               }

               if (s.equals("used-assertion-cache-class-name")) {
                  return 29;
               }

               if (s.equals("used-assertion-cache-properties")) {
                  return 30;
               }
               break;
            case 33:
               if (s.equals("signing-key-pass-phrase-encrypted")) {
                  return 23;
               }
               break;
            case 41:
               if (s.equals("ssl-client-identity-pass-phrase-encrypted")) {
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
               return "source-site-enabled";
            case 11:
               return "source-site-url";
            case 12:
               return "source-id-hex";
            case 13:
               return "source-id-base64";
            case 14:
               return "intersite-transfer-uri";
            case 15:
               return "its-requiresssl";
            case 16:
               return "assertion-retrieval-uri";
            case 17:
               return "ars-requiresssl";
            case 18:
               return "ars-requires-two-wayssl";
            case 19:
               return "assertion-store-class-name";
            case 20:
               return "assertion-store-properties";
            case 21:
               return "signing-key-alias";
            case 22:
               return "signing-key-pass-phrase";
            case 23:
               return "signing-key-pass-phrase-encrypted";
            case 24:
               return "destination-site-enabled";
            case 25:
               return "assertion-consumer-uri";
            case 26:
               return "acs-requiresssl";
            case 27:
               return "post-recipient-check-enabled";
            case 28:
               return "post-one-use-check-enabled";
            case 29:
               return "used-assertion-cache-class-name";
            case 30:
               return "used-assertion-cache-properties";
            case 31:
               return "ssl-client-identity-alias";
            case 32:
               return "ssl-client-identity-pass-phrase";
            case 33:
               return "ssl-client-identity-pass-phrase-encrypted";
            case 34:
               return "allowed-target-host";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 14:
               return true;
            case 16:
               return true;
            case 25:
               return true;
            case 34:
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
      private FederationServicesMBeanImpl bean;

      protected Helper(FederationServicesMBeanImpl bean) {
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
               return "SourceSiteEnabled";
            case 11:
               return "SourceSiteURL";
            case 12:
               return "SourceIdHex";
            case 13:
               return "SourceIdBase64";
            case 14:
               return "IntersiteTransferURIs";
            case 15:
               return "ITSRequiresSSL";
            case 16:
               return "AssertionRetrievalURIs";
            case 17:
               return "ARSRequiresSSL";
            case 18:
               return "ARSRequiresTwoWaySSL";
            case 19:
               return "AssertionStoreClassName";
            case 20:
               return "AssertionStoreProperties";
            case 21:
               return "SigningKeyAlias";
            case 22:
               return "SigningKeyPassPhrase";
            case 23:
               return "SigningKeyPassPhraseEncrypted";
            case 24:
               return "DestinationSiteEnabled";
            case 25:
               return "AssertionConsumerURIs";
            case 26:
               return "ACSRequiresSSL";
            case 27:
               return "POSTRecipientCheckEnabled";
            case 28:
               return "POSTOneUseCheckEnabled";
            case 29:
               return "UsedAssertionCacheClassName";
            case 30:
               return "UsedAssertionCacheProperties";
            case 31:
               return "SSLClientIdentityAlias";
            case 32:
               return "SSLClientIdentityPassPhrase";
            case 33:
               return "SSLClientIdentityPassPhraseEncrypted";
            case 34:
               return "AllowedTargetHosts";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowedTargetHosts")) {
            return 34;
         } else if (propName.equals("AssertionConsumerURIs")) {
            return 25;
         } else if (propName.equals("AssertionRetrievalURIs")) {
            return 16;
         } else if (propName.equals("AssertionStoreClassName")) {
            return 19;
         } else if (propName.equals("AssertionStoreProperties")) {
            return 20;
         } else if (propName.equals("IntersiteTransferURIs")) {
            return 14;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SSLClientIdentityAlias")) {
            return 31;
         } else if (propName.equals("SSLClientIdentityPassPhrase")) {
            return 32;
         } else if (propName.equals("SSLClientIdentityPassPhraseEncrypted")) {
            return 33;
         } else if (propName.equals("SigningKeyAlias")) {
            return 21;
         } else if (propName.equals("SigningKeyPassPhrase")) {
            return 22;
         } else if (propName.equals("SigningKeyPassPhraseEncrypted")) {
            return 23;
         } else if (propName.equals("SourceIdBase64")) {
            return 13;
         } else if (propName.equals("SourceIdHex")) {
            return 12;
         } else if (propName.equals("SourceSiteURL")) {
            return 11;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UsedAssertionCacheClassName")) {
            return 29;
         } else if (propName.equals("UsedAssertionCacheProperties")) {
            return 30;
         } else if (propName.equals("ACSRequiresSSL")) {
            return 26;
         } else if (propName.equals("ARSRequiresSSL")) {
            return 17;
         } else if (propName.equals("ARSRequiresTwoWaySSL")) {
            return 18;
         } else if (propName.equals("DestinationSiteEnabled")) {
            return 24;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("ITSRequiresSSL")) {
            return 15;
         } else if (propName.equals("POSTOneUseCheckEnabled")) {
            return 28;
         } else if (propName.equals("POSTRecipientCheckEnabled")) {
            return 27;
         } else {
            return propName.equals("SourceSiteEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isAllowedTargetHostsSet()) {
               buf.append("AllowedTargetHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAllowedTargetHosts())));
            }

            if (this.bean.isAssertionConsumerURIsSet()) {
               buf.append("AssertionConsumerURIs");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAssertionConsumerURIs())));
            }

            if (this.bean.isAssertionRetrievalURIsSet()) {
               buf.append("AssertionRetrievalURIs");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAssertionRetrievalURIs())));
            }

            if (this.bean.isAssertionStoreClassNameSet()) {
               buf.append("AssertionStoreClassName");
               buf.append(String.valueOf(this.bean.getAssertionStoreClassName()));
            }

            if (this.bean.isAssertionStorePropertiesSet()) {
               buf.append("AssertionStoreProperties");
               buf.append(String.valueOf(this.bean.getAssertionStoreProperties()));
            }

            if (this.bean.isIntersiteTransferURIsSet()) {
               buf.append("IntersiteTransferURIs");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIntersiteTransferURIs())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isSSLClientIdentityAliasSet()) {
               buf.append("SSLClientIdentityAlias");
               buf.append(String.valueOf(this.bean.getSSLClientIdentityAlias()));
            }

            if (this.bean.isSSLClientIdentityPassPhraseSet()) {
               buf.append("SSLClientIdentityPassPhrase");
               buf.append(String.valueOf(this.bean.getSSLClientIdentityPassPhrase()));
            }

            if (this.bean.isSSLClientIdentityPassPhraseEncryptedSet()) {
               buf.append("SSLClientIdentityPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSSLClientIdentityPassPhraseEncrypted())));
            }

            if (this.bean.isSigningKeyAliasSet()) {
               buf.append("SigningKeyAlias");
               buf.append(String.valueOf(this.bean.getSigningKeyAlias()));
            }

            if (this.bean.isSigningKeyPassPhraseSet()) {
               buf.append("SigningKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getSigningKeyPassPhrase()));
            }

            if (this.bean.isSigningKeyPassPhraseEncryptedSet()) {
               buf.append("SigningKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSigningKeyPassPhraseEncrypted())));
            }

            if (this.bean.isSourceIdBase64Set()) {
               buf.append("SourceIdBase64");
               buf.append(String.valueOf(this.bean.getSourceIdBase64()));
            }

            if (this.bean.isSourceIdHexSet()) {
               buf.append("SourceIdHex");
               buf.append(String.valueOf(this.bean.getSourceIdHex()));
            }

            if (this.bean.isSourceSiteURLSet()) {
               buf.append("SourceSiteURL");
               buf.append(String.valueOf(this.bean.getSourceSiteURL()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUsedAssertionCacheClassNameSet()) {
               buf.append("UsedAssertionCacheClassName");
               buf.append(String.valueOf(this.bean.getUsedAssertionCacheClassName()));
            }

            if (this.bean.isUsedAssertionCachePropertiesSet()) {
               buf.append("UsedAssertionCacheProperties");
               buf.append(String.valueOf(this.bean.getUsedAssertionCacheProperties()));
            }

            if (this.bean.isACSRequiresSSLSet()) {
               buf.append("ACSRequiresSSL");
               buf.append(String.valueOf(this.bean.isACSRequiresSSL()));
            }

            if (this.bean.isARSRequiresSSLSet()) {
               buf.append("ARSRequiresSSL");
               buf.append(String.valueOf(this.bean.isARSRequiresSSL()));
            }

            if (this.bean.isARSRequiresTwoWaySSLSet()) {
               buf.append("ARSRequiresTwoWaySSL");
               buf.append(String.valueOf(this.bean.isARSRequiresTwoWaySSL()));
            }

            if (this.bean.isDestinationSiteEnabledSet()) {
               buf.append("DestinationSiteEnabled");
               buf.append(String.valueOf(this.bean.isDestinationSiteEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isITSRequiresSSLSet()) {
               buf.append("ITSRequiresSSL");
               buf.append(String.valueOf(this.bean.isITSRequiresSSL()));
            }

            if (this.bean.isPOSTOneUseCheckEnabledSet()) {
               buf.append("POSTOneUseCheckEnabled");
               buf.append(String.valueOf(this.bean.isPOSTOneUseCheckEnabled()));
            }

            if (this.bean.isPOSTRecipientCheckEnabledSet()) {
               buf.append("POSTRecipientCheckEnabled");
               buf.append(String.valueOf(this.bean.isPOSTRecipientCheckEnabled()));
            }

            if (this.bean.isSourceSiteEnabledSet()) {
               buf.append("SourceSiteEnabled");
               buf.append(String.valueOf(this.bean.isSourceSiteEnabled()));
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
            FederationServicesMBeanImpl otherTyped = (FederationServicesMBeanImpl)other;
            this.computeDiff("AllowedTargetHosts", this.bean.getAllowedTargetHosts(), otherTyped.getAllowedTargetHosts(), true);
            this.computeDiff("AssertionConsumerURIs", this.bean.getAssertionConsumerURIs(), otherTyped.getAssertionConsumerURIs(), false);
            this.computeDiff("AssertionRetrievalURIs", this.bean.getAssertionRetrievalURIs(), otherTyped.getAssertionRetrievalURIs(), false);
            this.computeDiff("AssertionStoreClassName", this.bean.getAssertionStoreClassName(), otherTyped.getAssertionStoreClassName(), false);
            this.computeDiff("AssertionStoreProperties", this.bean.getAssertionStoreProperties(), otherTyped.getAssertionStoreProperties(), false);
            this.computeDiff("IntersiteTransferURIs", this.bean.getIntersiteTransferURIs(), otherTyped.getIntersiteTransferURIs(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SSLClientIdentityAlias", this.bean.getSSLClientIdentityAlias(), otherTyped.getSSLClientIdentityAlias(), true);
            this.computeDiff("SSLClientIdentityPassPhraseEncrypted", this.bean.getSSLClientIdentityPassPhraseEncrypted(), otherTyped.getSSLClientIdentityPassPhraseEncrypted(), true);
            this.computeDiff("SigningKeyAlias", this.bean.getSigningKeyAlias(), otherTyped.getSigningKeyAlias(), true);
            this.computeDiff("SigningKeyPassPhraseEncrypted", this.bean.getSigningKeyPassPhraseEncrypted(), otherTyped.getSigningKeyPassPhraseEncrypted(), true);
            this.computeDiff("SourceSiteURL", this.bean.getSourceSiteURL(), otherTyped.getSourceSiteURL(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UsedAssertionCacheClassName", this.bean.getUsedAssertionCacheClassName(), otherTyped.getUsedAssertionCacheClassName(), false);
            this.computeDiff("UsedAssertionCacheProperties", this.bean.getUsedAssertionCacheProperties(), otherTyped.getUsedAssertionCacheProperties(), false);
            this.computeDiff("ACSRequiresSSL", this.bean.isACSRequiresSSL(), otherTyped.isACSRequiresSSL(), true);
            this.computeDiff("ARSRequiresSSL", this.bean.isARSRequiresSSL(), otherTyped.isARSRequiresSSL(), true);
            this.computeDiff("ARSRequiresTwoWaySSL", this.bean.isARSRequiresTwoWaySSL(), otherTyped.isARSRequiresTwoWaySSL(), true);
            this.computeDiff("DestinationSiteEnabled", this.bean.isDestinationSiteEnabled(), otherTyped.isDestinationSiteEnabled(), false);
            this.computeDiff("ITSRequiresSSL", this.bean.isITSRequiresSSL(), otherTyped.isITSRequiresSSL(), true);
            this.computeDiff("POSTOneUseCheckEnabled", this.bean.isPOSTOneUseCheckEnabled(), otherTyped.isPOSTOneUseCheckEnabled(), true);
            this.computeDiff("POSTRecipientCheckEnabled", this.bean.isPOSTRecipientCheckEnabled(), otherTyped.isPOSTRecipientCheckEnabled(), true);
            this.computeDiff("SourceSiteEnabled", this.bean.isSourceSiteEnabled(), otherTyped.isSourceSiteEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FederationServicesMBeanImpl original = (FederationServicesMBeanImpl)event.getSourceBean();
            FederationServicesMBeanImpl proposed = (FederationServicesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowedTargetHosts")) {
                  original.setAllowedTargetHosts(proposed.getAllowedTargetHosts());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("AssertionConsumerURIs")) {
                  original.setAssertionConsumerURIs(proposed.getAssertionConsumerURIs());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("AssertionRetrievalURIs")) {
                  original.setAssertionRetrievalURIs(proposed.getAssertionRetrievalURIs());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("AssertionStoreClassName")) {
                  original.setAssertionStoreClassName(proposed.getAssertionStoreClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("AssertionStoreProperties")) {
                  original.setAssertionStoreProperties(proposed.getAssertionStoreProperties() == null ? null : (Properties)proposed.getAssertionStoreProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("IntersiteTransferURIs")) {
                  original.setIntersiteTransferURIs(proposed.getIntersiteTransferURIs());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SSLClientIdentityAlias")) {
                  original.setSSLClientIdentityAlias(proposed.getSSLClientIdentityAlias());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (!prop.equals("SSLClientIdentityPassPhrase")) {
                  if (prop.equals("SSLClientIdentityPassPhraseEncrypted")) {
                     original.setSSLClientIdentityPassPhraseEncrypted(proposed.getSSLClientIdentityPassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (prop.equals("SigningKeyAlias")) {
                     original.setSigningKeyAlias(proposed.getSigningKeyAlias());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (!prop.equals("SigningKeyPassPhrase")) {
                     if (prop.equals("SigningKeyPassPhraseEncrypted")) {
                        original.setSigningKeyPassPhraseEncrypted(proposed.getSigningKeyPassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 23);
                     } else if (!prop.equals("SourceIdBase64") && !prop.equals("SourceIdHex")) {
                        if (prop.equals("SourceSiteURL")) {
                           original.setSourceSiteURL(proposed.getSourceSiteURL());
                           original._conditionalUnset(update.isUnsetUpdate(), 11);
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
                        } else if (prop.equals("UsedAssertionCacheClassName")) {
                           original.setUsedAssertionCacheClassName(proposed.getUsedAssertionCacheClassName());
                           original._conditionalUnset(update.isUnsetUpdate(), 29);
                        } else if (prop.equals("UsedAssertionCacheProperties")) {
                           original.setUsedAssertionCacheProperties(proposed.getUsedAssertionCacheProperties() == null ? null : (Properties)proposed.getUsedAssertionCacheProperties().clone());
                           original._conditionalUnset(update.isUnsetUpdate(), 30);
                        } else if (prop.equals("ACSRequiresSSL")) {
                           original.setACSRequiresSSL(proposed.isACSRequiresSSL());
                           original._conditionalUnset(update.isUnsetUpdate(), 26);
                        } else if (prop.equals("ARSRequiresSSL")) {
                           original.setARSRequiresSSL(proposed.isARSRequiresSSL());
                           original._conditionalUnset(update.isUnsetUpdate(), 17);
                        } else if (prop.equals("ARSRequiresTwoWaySSL")) {
                           original.setARSRequiresTwoWaySSL(proposed.isARSRequiresTwoWaySSL());
                           original._conditionalUnset(update.isUnsetUpdate(), 18);
                        } else if (prop.equals("DestinationSiteEnabled")) {
                           original.setDestinationSiteEnabled(proposed.isDestinationSiteEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 24);
                        } else if (!prop.equals("DynamicallyCreated")) {
                           if (prop.equals("ITSRequiresSSL")) {
                              original.setITSRequiresSSL(proposed.isITSRequiresSSL());
                              original._conditionalUnset(update.isUnsetUpdate(), 15);
                           } else if (prop.equals("POSTOneUseCheckEnabled")) {
                              original.setPOSTOneUseCheckEnabled(proposed.isPOSTOneUseCheckEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 28);
                           } else if (prop.equals("POSTRecipientCheckEnabled")) {
                              original.setPOSTRecipientCheckEnabled(proposed.isPOSTRecipientCheckEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 27);
                           } else if (prop.equals("SourceSiteEnabled")) {
                              original.setSourceSiteEnabled(proposed.isSourceSiteEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 10);
                           } else {
                              super.applyPropertyUpdate(event, update);
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
            FederationServicesMBeanImpl copy = (FederationServicesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("AllowedTargetHosts")) && this.bean.isAllowedTargetHostsSet()) {
               o = this.bean.getAllowedTargetHosts();
               copy.setAllowedTargetHosts(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionConsumerURIs")) && this.bean.isAssertionConsumerURIsSet()) {
               o = this.bean.getAssertionConsumerURIs();
               copy.setAssertionConsumerURIs(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionRetrievalURIs")) && this.bean.isAssertionRetrievalURIsSet()) {
               o = this.bean.getAssertionRetrievalURIs();
               copy.setAssertionRetrievalURIs(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionStoreClassName")) && this.bean.isAssertionStoreClassNameSet()) {
               copy.setAssertionStoreClassName(this.bean.getAssertionStoreClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionStoreProperties")) && this.bean.isAssertionStorePropertiesSet()) {
               copy.setAssertionStoreProperties(this.bean.getAssertionStoreProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("IntersiteTransferURIs")) && this.bean.isIntersiteTransferURIsSet()) {
               o = this.bean.getIntersiteTransferURIs();
               copy.setIntersiteTransferURIs(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLClientIdentityAlias")) && this.bean.isSSLClientIdentityAliasSet()) {
               copy.setSSLClientIdentityAlias(this.bean.getSSLClientIdentityAlias());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("SSLClientIdentityPassPhraseEncrypted")) && this.bean.isSSLClientIdentityPassPhraseEncryptedSet()) {
               o = this.bean.getSSLClientIdentityPassPhraseEncrypted();
               copy.setSSLClientIdentityPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SigningKeyAlias")) && this.bean.isSigningKeyAliasSet()) {
               copy.setSigningKeyAlias(this.bean.getSigningKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("SigningKeyPassPhraseEncrypted")) && this.bean.isSigningKeyPassPhraseEncryptedSet()) {
               o = this.bean.getSigningKeyPassPhraseEncrypted();
               copy.setSigningKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SourceSiteURL")) && this.bean.isSourceSiteURLSet()) {
               copy.setSourceSiteURL(this.bean.getSourceSiteURL());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UsedAssertionCacheClassName")) && this.bean.isUsedAssertionCacheClassNameSet()) {
               copy.setUsedAssertionCacheClassName(this.bean.getUsedAssertionCacheClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("UsedAssertionCacheProperties")) && this.bean.isUsedAssertionCachePropertiesSet()) {
               copy.setUsedAssertionCacheProperties(this.bean.getUsedAssertionCacheProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("ACSRequiresSSL")) && this.bean.isACSRequiresSSLSet()) {
               copy.setACSRequiresSSL(this.bean.isACSRequiresSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("ARSRequiresSSL")) && this.bean.isARSRequiresSSLSet()) {
               copy.setARSRequiresSSL(this.bean.isARSRequiresSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("ARSRequiresTwoWaySSL")) && this.bean.isARSRequiresTwoWaySSLSet()) {
               copy.setARSRequiresTwoWaySSL(this.bean.isARSRequiresTwoWaySSL());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationSiteEnabled")) && this.bean.isDestinationSiteEnabledSet()) {
               copy.setDestinationSiteEnabled(this.bean.isDestinationSiteEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ITSRequiresSSL")) && this.bean.isITSRequiresSSLSet()) {
               copy.setITSRequiresSSL(this.bean.isITSRequiresSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("POSTOneUseCheckEnabled")) && this.bean.isPOSTOneUseCheckEnabledSet()) {
               copy.setPOSTOneUseCheckEnabled(this.bean.isPOSTOneUseCheckEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("POSTRecipientCheckEnabled")) && this.bean.isPOSTRecipientCheckEnabledSet()) {
               copy.setPOSTRecipientCheckEnabled(this.bean.isPOSTRecipientCheckEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceSiteEnabled")) && this.bean.isSourceSiteEnabledSet()) {
               copy.setSourceSiteEnabled(this.bean.isSourceSiteEnabled());
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
